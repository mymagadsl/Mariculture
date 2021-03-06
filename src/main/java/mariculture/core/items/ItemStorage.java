package mariculture.core.items;

import java.util.ArrayList;
import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.GuiStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.gui.feature.Feature;
import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.GuiIds;
import mariculture.core.util.IItemRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStorage extends Item implements IItemRegistry {

	public int size;
	public String gui;

	public ItemStorage(int storage, String gui) {
		maxStackSize = 1;
		this.size = storage;
		this.gui = gui;
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (stack != null) {
			if (!player.isSneaking()) {
				player.openGui(Mariculture.instance, GuiIds.STORAGE, world, 0, 0, 0);
			}

			return stack;
		}

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, 0)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creative, List list) {
		list.add(new ItemStack(item, 1, 0));
	}

	@Override
	public void register() {
		MaricultureRegistry.register(getName(new ItemStack(this, 1, 0)), new ItemStack(this, 1, 0));
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return getUnlocalizedName().substring(5);
	}

	public Slot getSlot(InventoryStorage storage, int i) {
		switch(i) {
			case 0: return new Slot(storage, i, 43, 25);
		}
		
		return new Slot(storage, i, 100, 100);
	}
	
	public int getX(ItemStack stack) {
		return 66;
	}

	public boolean isItemValid(ItemStack stack) {
		return true;
	}

	public ItemStack[] load(EntityPlayer player, ItemStack stack, int size) {
		//TODO: Check if loading correctly
		NBTTagCompound loader = stack.hasTagCompound() ? stack.stackTagCompound: new NBTTagCompound();
		NBTTagList nbttaglist = loader.getTagList("Inventory", 10);

		if (nbttaglist != null) {
			ItemStack[] inventory = new ItemStack[size];
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if (byte0 >= 0 && byte0 < inventory.length) {
					inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}

			return inventory;
		}
		
		return new ItemStack[size];
	}

	public void save(EntityPlayer player, ItemStack[] inventory) {
		try {
			NBTTagList nbttaglist = new NBTTagList();
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] != null) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte) i);
					inventory[i].writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}
			if (!player.getCurrentEquippedItem().hasTagCompound()) {
				player.getCurrentEquippedItem().setTagCompound(new NBTTagCompound());
			}
			
			player.getCurrentEquippedItem().stackTagCompound.setTag("Inventory", nbttaglist);

		} catch (Exception e) {
			LogHandler.log(Level.WARN, "Mariculture had trouble saving an inventory");
		}
	}

	public void addFeatures(ArrayList<Feature> list) {
		
	}

	public Object getGUIContainer(EntityPlayer player) {
		return new ContainerStorage(player.inventory, new InventoryStorage(player, size), player.worldObj);
	}

	public Object getGUIElement(EntityPlayer player) {
		return new GuiStorage(player.inventory, new InventoryStorage(player, size), player.worldObj, gui);
	}
}