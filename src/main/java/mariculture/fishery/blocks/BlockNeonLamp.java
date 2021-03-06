package mariculture.fishery.blocks;

import java.util.List;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.blocks.BlockDecorative;
import mariculture.core.lib.PearlColor;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNeonLamp extends BlockDecorative {
	private final boolean powered;

	public BlockNeonLamp(boolean powered) {
		super(Material.glass);
		this.powered = powered;

		if (!powered) {
			setLightLevel(1.0F);
		}

		this.setHardness(1F);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote) {
			if (this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.scheduleBlockUpdate(x, y, z, this, 4);
			} else if (!this.powered && world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.setBlock(x, y, z, Fishery.lampsOff, world.getBlockMetadata(x, y, z), 2);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
			if (this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.scheduleBlockUpdate(x, y, z, this, 4);
			} else if (!this.powered && world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.setBlock(x, y, z, Fishery.lampsOff, world.getBlockMetadata(x, y, z), 2);
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote && this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
			world.setBlock(x, y, z, Fishery.lampsOn, world.getBlockMetadata(x, y, z), 2);
		}
	}
	
	@Override
	public Item getItemDropped(int meta, Random random, int j) {
		return Item.getItemFromBlock(Fishery.lampsOn);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(Fishery.lampsOn, 1, world.getBlockMetadata(x, y, z));
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess block, int x, int y, int z) {
		if (block.getBlock(x, y, z) == Fishery.lampsOn) {
			return 15;
		}

		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icons = new IIcon[PearlColor.COUNT];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = register.registerIcon(Mariculture.modid + ":" + (this.getUnlocalizedName().substring(5)) + i);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
		if (item == Item.getItemFromBlock(Fishery.lampsOn)) {
			for (int meta = 0; meta < PearlColor.COUNT; ++meta) {
				if (!list.contains(new ItemStack(item, 1, meta))) {
					list.add(new ItemStack(item, 1, meta));
				}
			}
		}
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register("neonLamp." + getName(new ItemStack(this, 1, j)), new ItemStack(this, 1, j));
		}
	}

	@Override
	public int getMetaCount() {
		return PearlColor.COUNT;
	}
}
