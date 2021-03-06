package mariculture.factory.blocks;

import java.util.List;

import mariculture.core.blocks.ItemBlockMariculture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class BlockItemCustom extends ItemBlockMariculture {
	private IIcon[] icons;

	public BlockItemCustom(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	//TODO: Add on Use Method for Placing Slabs

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.hasKey("Name")) {
				return stack.stackTagCompound.getString("Name");
			}
		}

		return StatCollector.translateToLocal(stack.getUnlocalizedName());
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		addTextureInfo(stack, player, list, bool);
	}

	public static void addTextureInfo(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if (stack.hasTagCompound()) {
			addToList(list, 1, stack);
			addToList(list, 2, stack);
			addToList(list, 5, stack);
			addToList(list, 3, stack);
			addToList(list, 4, stack);
			addToList(list, 0, stack);
		}
	}

	public static void addToList(List list, int num, ItemStack stack) {
		/*
		list.add(getName(num)
				+ ": "
				+ new ItemStack(stack.stackTagCompound.getIntArray("BlockIDs")[num], 1, stack.stackTagCompound
						.getIntArray("BlockMetas")[num]).getDisplayName()); */
		//TODO: Adding the directional information to blocks
	}

	public static String getName(final int i) {
		switch (i) {
		case 0:
			return StatCollector.translateToLocal("mariculture.string.bottom");
		case 1:
			return StatCollector.translateToLocal("mariculture.string.top");
		case 2:
			return StatCollector.translateToLocal("mariculture.string.north");
		case 3:
			return StatCollector.translateToLocal("mariculture.string.south");
		case 4:
			return StatCollector.translateToLocal("mariculture.string.west");
		case 5:
			return StatCollector.translateToLocal("mariculture.string.east");
		default:
			return "";
		}
	}
	
	@Override
	public String getName(ItemStack stack) {
		return this.getUnlocalizedName().substring(5);
	}
}
