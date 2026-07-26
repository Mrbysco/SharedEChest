package easton.sharedechest.menu;

import easton.sharedechest.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SharedEnderChestMenu extends AbstractContainerMenu {
	public Container inventory;
	public final Container personalInv;

	public SharedEnderChestMenu(int syncId, Inventory playerInventory) {
		this(syncId, playerInventory, new SimpleContainer(27));
	}

	public SharedEnderChestMenu(int syncId, Inventory playerInventory, Container inventory) {
		super(ModMenus.SHARED_ENDER_CHEST.get(), syncId);
		checkContainerSize(inventory, 27);
		this.inventory = inventory;
		this.personalInv = inventory;
		//some inventories do custom logic when a player opens it.
		inventory.startOpen(playerInventory.player);

		createSlots(playerInventory);
	}

	public static SharedEnderChestMenu create(final int windowId, final Inventory inventory, final FriendlyByteBuf buffer) {
		return new SharedEnderChestMenu(windowId, inventory);
	}

	public void createSlots(Inventory playerInventory) {
		this.slots.clear();
		//This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
		//This will not render the background of the slots however, this is the Screens job
		int m;
		int l;
		//Our inventory
		for (m = 0; m < 3; m++) {
			for (l = 0; l < 9; l++) {
				this.addSlot(new Slot(inventory, l + m * 9, 8 + l * 18, 18 + m * 18));
			}
		}
		//The player inventory
		for (m = 0; m < 3; ++m) {
			for (l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
			}
		}
		//The player hotbar
		for (m = 0; m < 9; ++m) {
			this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
		}
	}

	@Override
	public boolean stillValid(Player player) {
		return this.inventory.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if (slot != null && slot.hasItem()) {
			ItemStack originalStack = slot.getItem();
			newStack = originalStack.copy();
			if (invSlot < this.inventory.getContainerSize()) {
				if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return newStack;
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.personalInv.stopOpen(player);
	}
}
