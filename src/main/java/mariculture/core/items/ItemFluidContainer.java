package mariculture.core.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.Text;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFluidContainer extends ItemMariculture {
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		if (!world.isRemote) {
			if (player.shouldHeal()) {
				player.heal(5);
			}
		}

		return stack.stackSize <= 0 ? new ItemStack(Items.glass_bottle) : stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		//TODO: Fix Drinking Fish oil for the various bottle types so it returns the correct versions + Heals more
		if (stack.getItemDamage() == FluidContainerMeta.BOTTLE_FISH_OIL && player.shouldHeal()) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		return 24;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		int meta = stack.getItemDamage();
		if(meta == FluidContainerMeta.BOTTLE_VOID) {
			list.add(Text.PURPLE + StatCollector.translateToLocal("mariculture.string.blackhole"));
			return;
		}
			
		if(meta == FluidContainerMeta.BOTTLE_EMPTY) {
			list.add(Text.YELLOW + StatCollector.translateToLocal("mariculture.string.doubleBottle"));
			return;
		}
		
		FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
		int amount = fluid == null? 0: fluid.amount;
		list.add(StringHelper.getFluidName(fluid));
		StringHelper.getFluidQty(list, fluid, -1);
	}

	@Override
	public EnumAction getItemUseAction(final ItemStack stack) {
		return EnumAction.drink;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(stack.getItemDamage() != FluidContainerMeta.BOTTLE_VOID)
			return false;
		Block block = world.getBlock(x, y, z);
		if(side == 1 && (block instanceof BlockFluidBase || block instanceof BlockLiquid)) {
			--y;
		} else if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			side = 1;
		} else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
			if (side == 0)
				--y;
			if (side == 1)
				++y;
			if (side == 2)
				--z;
			if (side == 3)
				++z;
			if (side == 4)
				--x;
			if (side == 5)
				++x;
		}

		if (stack.stackSize == 0) {
			return false;
		} else if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else if (y == 255 && block.getMaterial().isSolid()) {
			return false;
		} else if (world.canPlaceEntityOnSide(block, x, y, z, false, side, player, stack)) {
			int meta = TankMeta.BOTTLE;
			int metadata = block.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, meta);

			//TODO: func_150496_b = getPlaceSound()
			if (placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), 
				block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		Block block = Core.tankBlocks;
		if (!world.setBlock(x, y, z, block, meta, 3)) {
			return false;
		}

		if (world.getBlock(x, y, z) == block) {
			block.onBlockPlacedBy(world, x, y, z, player, stack);
			block.onPostBlockPlaced(world, x, y, z, meta);
		}

		return true;
	}

	@Override
	public int getMetaCount() {
		return FluidContainerMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case FluidContainerMeta.BOTTLE_VOID:
			return "bottleVoid";
		case FluidContainerMeta.BOTTLE_FISH_OIL:
			return "bottleFishOil";
		case FluidContainerMeta.BOTTLE_IRON:
			return "bottleIron";
		case FluidContainerMeta.BOTTLE_GOLD:
			return "bottleGold";
		case FluidContainerMeta.BOTTLE_COPPER:
			return "bottleCopper";
		case FluidContainerMeta.BOTTLE_TIN:
			return "bottleTin";
		case FluidContainerMeta.BOTTLE_SILVER:
			return "bottleSilver";
		case FluidContainerMeta.BOTTLE_LEAD:
			return "bottleLead";
		case FluidContainerMeta.BOTTLE_BRONZE:
			return "bottleBronze";
		case FluidContainerMeta.BOTTLE_STEEL:
			return "bottleSteel";
		case FluidContainerMeta.BOTTLE_ALUMINUM:
			return "bottleAluminum";
		case FluidContainerMeta.BOTTLE_TITANIUM:
			return "bottleTitanium";
		case FluidContainerMeta.BOTTLE_MAGNESIUM:
			return "bottleMagnesium";
		case FluidContainerMeta.BOTTLE_NICKEL:
			return "bottleNickel";
		case FluidContainerMeta.BOTTLE_GLASS:
			return "bottleGlass";
		case FluidContainerMeta.BOTTLE_GAS:
			return "bottleNaturalGas";
		case FluidContainerMeta.BOTTLE_FISH_FOOD:
			return "bottleFishFood";
		case FluidContainerMeta.BOTTLE_RUTILE:
			return "bottleRutile";
		case FluidContainerMeta.BOTTLE_QUICKLIME:
			return "bottleQuicklime";
		case FluidContainerMeta.BOTTLE_SALT:
			return "bottleSalt";
		case FluidContainerMeta.BOTTLE_EMPTY:
			return "bottleEmpty";
		case FluidContainerMeta.BOTTLE_ELECTRUM:
			return "bottleElectrum";
		case FluidContainerMeta.BOTTLE_WATER:
			return "bottleWater";
		case FluidContainerMeta.BOTTLE_LAVA:
			return "bottleLava";
		case FluidContainerMeta.BOTTLE_MILK:
			return "bottleMilk";
		case FluidContainerMeta.BOTTLE_CUSTARD:
			return "bottleCustard";
		case FluidContainerMeta.BOTTLE_NORMAL_CUSTARD:
			return "bottleNormalCustard";
		case FluidContainerMeta.BOTTLE_NORMAL_FISH_FOOD:
			return "bottleNormalFishFood";
		case FluidContainerMeta.BOTTLE_NORMAL_FISH_OIL:
			return "bottleNormalFishOil";
		case FluidContainerMeta.BOTTLE_NORMAL_GAS:
			return "bottleNormalNaturalGas";
		case FluidContainerMeta.BOTTLE_NORMAL_MILK:
			return "bottleNormalMilk";
		case FluidContainerMeta.BOTTLE_HP_WATER:
			return "bottleHPWater";
		default:
			return "container";
		}
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case FluidContainerMeta.BOTTLE_BRONZE:
			return OreDictionary.getOres("ingotBronze").size() > 0;
		case FluidContainerMeta.BOTTLE_LEAD:
			return OreDictionary.getOres("ingotLead").size() > 0;
		case FluidContainerMeta.BOTTLE_NICKEL:
			return OreDictionary.getOres("ingotNickel").size() > 0;
		case FluidContainerMeta.BOTTLE_SILVER:
			return OreDictionary.getOres("ingotSilver").size() > 0;
		case FluidContainerMeta.BOTTLE_STEEL:
			return OreDictionary.getOres("ingotSteel").size() > 0;
		case FluidContainerMeta.BOTTLE_TIN:
			return OreDictionary.getOres("ingotTin").size() > 0;
		case FluidContainerMeta.BOTTLE_FISH_OIL:
			return (Modules.fishery.isActive());
		case FluidContainerMeta.BOTTLE_FISH_FOOD:
			return (Modules.fishery.isActive());

		default:
			return true;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		icons = new IIcon[getMetaCount()];
		for (int i = 0; i < icons.length; i++) {
			if (isActive(i)) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)));
			}
		}
	}
}
