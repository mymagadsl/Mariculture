package mariculture.fishery.items;

import java.util.List;

import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import mariculture.fishery.Fishery;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFishyFood extends Item implements IEnergyContainerItem {
	public ItemFishyFood() {
		this.setCreativeTab(MaricultureTab.tabFish);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal("fish.data.dead") + " "
				+ Fishing.fishHelper.getSpecies(stack.getItemDamage()).getName();
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		FishSpecies fish = Fishing.fishHelper.getSpecies(dmg);
		if(fish != null) {
			return fish.getIcon();
		}

		return Fishery.cod.getIcon();
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.eat;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.canEat(false))
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		stack = Fishing.fishHelper.getSpecies(stack.getItemDamage()).onRightClick(world, player, stack, itemRand);
		return stack;
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;
		Fishing.fishHelper.getSpecies(stack.getItemDamage()).onConsumed(world, player);
		return stack;
	}
	
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		return 0;
	}
	
	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		if(container.getItemDamage() == Fishery.electricRay.fishID) {
			if(!simulate) {
				if(container.stackSize <= 1) {
					container = new ItemStack(Core.materials, Fishery.electricRay.getFishMealSize(), MaterialsMeta.FISH_MEAL);
					container.stackTagCompound = null;
				} else {
					container.stackSize--;
				}
			}
			return 1000;
		}
		
		return 0;
	}
	
	@Override
	public int getEnergyStored(ItemStack container) {
		if(container.getItemDamage() == Fishery.electricRay.fishID) {
			return 1000;
		}
		
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		if(container.getItemDamage() == Fishery.electricRay.fishID) {
			return 1000;
		}
		
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creative, List list) {
		for (int i = 0; i < FishSpecies.speciesList.size(); ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
	}
}
