package easton.sharedechest;

import easton.sharedechest.mixin.MinecraftServerAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Util;
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
			ListTag list = compound.getListOrEmpty("inv");
			RegistryOps<Tag> ops = server.registryAccess().createSerializationContext(NbtOps.INSTANCE);
			readNbtList(list, sharedInventory, ops);
		}
	}

	public static void saveSharedInv(MinecraftServer server) throws IOException {
		RegistryOps<Tag> ops = server.registryAccess().createSerializationContext(NbtOps.INSTANCE);
		File overworldFile = ((MinecraftServerAccessor)server).SharedEChest$getStorageSource().getDimensionPath(ServerLevel.OVERWORLD).toFile();
		File file = File.createTempFile("shared_ender_chest", ".dat", overworldFile);
		CompoundTag nbtCompound = new CompoundTag();
		nbtCompound.put("inv", toNbtList(sharedInventory, ops));
		NbtIo.writeCompressed(nbtCompound, file.toPath());
		File file2 = new File(overworldFile, "shared_ender_chest.dat");
		File file3 = new File(overworldFile, "shared_ender_chest.dat_old");
		Util.safeReplaceFile(file2.toPath(), file.toPath(), file3.toPath());
	}

	public static void readNbtList(ListTag nbtList, SimpleContainer inv, RegistryOps<Tag> ops) {
		int j;
		for(j = 0; j < inv.getContainerSize(); ++j) {
			inv.setItem(j, ItemStack.EMPTY);
		}

		for(j = 0; j < nbtList.size(); ++j) {
			CompoundTag nbtCompound = nbtList.getCompoundOrEmpty(j);
			int k = nbtCompound.getByteOr("Slot", (byte)0) & 255;
			if (k >= 0 && k < inv.getContainerSize()) {
				ItemStack.CODEC.parse(ops, nbtCompound)
						.resultOrPartial(Constants.LOGGER::error)
						.ifPresent(stack -> inv.setItem(k, stack));
			}
		}
	}

	public static ListTag toNbtList(SimpleContainer inv, RegistryOps<Tag> ops) {
		ListTag nbtList = new ListTag();
		for(int i = 0; i < inv.getContainerSize(); ++i) {
			ItemStack itemStack = inv.getItem(i);
			if (!itemStack.isEmpty()) {
				CompoundTag nbtCompound = new CompoundTag();
				nbtCompound.putByte("Slot", (byte) i);
				ItemStack.CODEC.encodeStart(ops, itemStack)
						.resultOrPartial(Constants.LOGGER::error)
						.ifPresent(nbtList::add);
				nbtList.add(nbtCompound);
			}
		}
		return nbtList;
	}
}
