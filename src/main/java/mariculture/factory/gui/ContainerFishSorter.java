package mariculture.factory.gui;

import mariculture.core.gui.ContainerMachine;
import mariculture.factory.blocks.TileFishSorter;
import mariculture.fishery.gui.SlotSorter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFishSorter extends ContainerMachine {
	public ContainerFishSorter(TileFishSorter tile, InventoryPlayer playerInventory) {
		super(tile);

		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 7; ++k) {
				addSlotToContainer(new SlotSorter(tile, k + j * 7, 38 + k * 18, 21 + j * 18));
			}
		}

		addSlotToContainer(new Slot(tile, 21, 9, 25));
		bindPlayerInventory(playerInventory, 10);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		return null;
	}
}