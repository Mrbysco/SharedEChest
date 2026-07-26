package easton.sharedechest;

import easton.sharedechest.mixin.MinecraftServerAccessor;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.io.File;
import java.io.IOException;

public class InventoryManager {
	public static SimpleContainer sharedInventory = new SimpleContainer(27);

	public static void loadSharedInv(MinecraftServer server) throws IOException {
		File overworldFile = ((MinecraftServerAccessor)server).SharedEChest$getStorageSource().getDimensionPath(ServerLevel.OVERWORLD).toFile();
		File file = new File(overworldFile, "shared_ender_chest.dat");
		if (file.exists()) {
			CompoundTag compound = NbtIo.readCompressed(file.toPath(), NbtAccounter.unlimitedHeap());
			ListTag list = compound.getList("inv", 10);
			readNbtList(list, sharedInventory, server.registryAccess());
		}
	}

	public static void saveSharedInv(MinecraftServer server) throws IOException {
		File overworldFile = ((MinecraftServerAccessor)server).SharedEChest$getStorageSource().getDimensionPath(ServerLevel.OVERWORLD).toFile();
		File file = File.createTempFile("shared_ender_chest", ".dat", overworldFile);
		CompoundTag nbtCompound = new CompoundTag();
		nbtCompound.put("inv", toNbtList(sharedInventory, server.registryAccess()));
		NbtIo.writeCompressed(nbtCompound, file.toPath());
		File file2 = new File(overworldFile, "shared_ender_chest.dat");
		File file3 = new File(overworldFile, "shared_ender_chest.dat_old");
		Util.safeReplaceFile(file2.toPath(), file.toPath(), file3.toPath());
	}

	public static void readNbtList(ListTag nbtList, SimpleContainer inv, RegistryAccess registryAccess) {
		int j;
		for(j = 0; j < inv.getContainerSize(); ++j) {
			inv.setItem(j, ItemStack.EMPTY);
		}

		for(j = 0; j < nbtList.size(); ++j) {
			CompoundTag nbtCompound = nbtList.getCompound(j);
			int k = nbtCompound.getByte("Slot") & 255;
			if (k >= 0 && k < inv.getContainerSize()) {
				inv.setItem(k, ItemStack.parseOptional(registryAccess, nbtCompound));
			}
		}
	}

	public static ListTag toNbtList(SimpleContainer inv, RegistryAccess registryAccess) {
		ListTag nbtList = new ListTag();
		for(int i = 0; i < inv.getContainerSize(); ++i) {
			ItemStack itemStack = inv.getItem(i);
			if (!itemStack.isEmpty()) {
				CompoundTag nbtCompound = new CompoundTag();
				nbtCompound.putByte("Slot", (byte)i);
				itemStack.save(registryAccess, nbtCompound);
				nbtList.add(nbtCompound);
			}
		}
		return nbtList;
	}
}
