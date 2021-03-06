package mariculture.world;

import java.util.List;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.core.CoralRegistry;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Extra;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCoral extends Block implements IPlantable, IItemRegistry {
	private IIcon[] icons;

	protected BlockCoral() {
		super(Material.water);
		float f = 0.375F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
		this.setTickRandomly(true);
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) <= CoralMeta.KELP_MIDDLE) {
			return 0.05F;
		}
		
		return 1F;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(world.getBlockMetadata(x, y, z) == CoralMeta.KELP) {
			updateKelp(world, x, y, z, rand);
		} else if(world.getBlock(x, y - 1, z) != this) { 
			if(world.getBlockMetadata(x, y, z) == CoralMeta.KELP_MIDDLE) {
				updateMoss(world, x, y, z, rand);
			} else {
				updateCoral(world, x, y, z, rand);
			}
		}
	}

	private void updateCoral(World world, int x, int y, int z, Random rand) {
		if(rand.nextInt(Extra.CORAL_SPREAD_CHANCE) == 0) {
			int xBonus = rand.nextInt(3) - 1;
			int zBonus = rand.nextInt(3) - 1;
			spread(world, x + xBonus, y, z + zBonus, rand);
		}
	}
	
	private void updateKelp(World world, int x, int y, int z, Random rand) {
		if(rand.nextInt(Extra.KELP_GROWTH_CHANCE) == 0) {
			if (BlockHelper.isWater(world, x, y + 1, z) && BlockHelper.isWater(world, x, y + 2, z)) {
				world.setBlock(x, y + 1, z, this, 0, 2);
				onPostBlockPlaced(world, x, y + 1, z, 0);
			}
		}
	}

	private void updateMoss(World world, int x, int y, int z, Random rand) {
		if (world.getBlock(x, y - 1, z) == Blocks.mossy_cobblestone ||
				(world.getBlock(x, y - 1, z) == Blocks.stonebrick
						&& world.getBlockMetadata(x, y - 1, z) == 1))
		{
			if(rand.nextInt(Extra.KELP_SPREAD_CHANCE) == 0) {
				int x2 = x + rand.nextInt(4) - 2;
				int z2 = z + rand.nextInt(4) - 2;
								
				if(BlockHelper.isWater(world, x2, y, z2)) {
					if (world.getBlock(x2, y - 1, z2) == Blocks.cobblestone) {
						world.setBlock(x2, y - 1, z2, Blocks.mossy_cobblestone);
					}
					
					if (world.getBlock(x2, y - 1, z2) == Blocks.stonebrick
							&& world.getBlockMetadata(x2, y - 1, z2) == 0) {
						world.setBlockMetadataWithNotify(x2, y - 1, z2, 1, 2);
					}
				}
			}
		}
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess block, int x, int y, int z) {
		/*
		if(block.getBlockMetadata(x, y, z) <= CoralMeta.KELP_MIDDLE) {
			int id1 = block.getBlockId(x + 1, 16, z);
			int id2 = block.getBlockId(x, y, z + 8);
			int id3 = block.getBlockMetadata(x - 6, 25, z);
			int id4 = block.getBlockId(x, 5, z);

			int number = (int) id1 * id2 * id3 * id4 + z + x;
			number = (number <= 0)? -number: number;
			
			if(number %13 == 0) {
				return 1900371;
			} else if(number %12 == 0) {
				return 1887059;
			} else if(number %11 == 0) {
				return 1860179;
			} else if (number %10 == 0) {
				return 2970947;
			} else if (number %9 == 0) {
				return 2986563;
			} else if(number %8 == 0) {
				return 2986594;
			} else if(number %7 == 0) {
				return 16777215;
			} else if(number %6 == 0) {
				return 6396257;
			} else if (number %5 == 0) {
				return 8431445;
			} else if (number %4 == 0) {
				return 4764952;
			} else if(number %3 == 0) {
				return 16777215;
			} else if(number %2 == 0) {
				return 7719028;
			} else {
				return 5411426;
			}
		} */
		
        return 16777215;
    }

	public static void fullSpread(World world, int x, int y, int z, Random random) {
		for (int x2 = -3; x2 < 4 + 1; x2++) {
			for (int z2 = -3; z2 < 4 + 1; z2++) {
				spread(world, x + x2, y - 1, z + z2, random);
				spread(world, x + x2 + 1, y - 1, z + z2, random);
				spread(world, x + x2 - 1, y - 1, z + z2, random);
				spread(world, x + x2, y - 1, z + 1 + z2, random);
				spread(world, x + x2, y - 1, z - 1 + z2, random);
			}
		}
	}

	private static void spread(World world, int x, int y, int z, Random random) {
		for (int y2 = -2; y2 < 3; y2++) {
			if (isValidFloor(world, x, y + y2, z)) {
				if (BlockHelper.isWater(world, x, y + y2 + 1, z)) {
					if (random.nextInt(5096) == 1) {
						world.setBlock(x, y + y2 + 1, z, Blocks.sponge);
					} else {
						ItemStack coral = CoralRegistry.corals.get(random.nextInt(CoralRegistry.corals.size()));
						world.setBlock(x, y + y2 + 1, z, Block.getBlockFromItem(coral.getItem()), coral.getItemDamage(), 2);
					}
				}
			}
		}
	}
	
	private static boolean isValidFloor(World world, int x, int y, int z) {
		if(world.getBlock(x, y, z) == Core.oreBlocks && world.getBlockMetadata(x, y, z) == OresMeta.CORAL_ROCK) {
			return true;
		}
		
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if (world.getBlock(x, y - 1, z) == WorldPlus.coral
				&& world.getBlockMetadata(x, y - 1, z) == CoralMeta.KELP_MIDDLE) {
			if(world instanceof World) {
				((World)world).setBlockMetadataWithNotify(x, y - 1, z, CoralMeta.KELP, 2);
			}
		}
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plant) {
		if (world.getBlock(x, y, z).getMaterial() != Material.water) {
			return false;
		}

		if (world.getBlock(x, y, z) == WorldPlus.coral) {
			if (world.getBlockMetadata(x, y, z) <= CoralMeta.KELP_MIDDLE) {
				if (world.getBlock(x, y - 1, z) == WorldPlus.coral
						&& world.getBlockMetadata(x, y - 1, z) <= CoralMeta.KELP_MIDDLE) {
					return true;
				}
				if (world.getBlock(x, y - 1, z) == Blocks.gravel) {
					return true;
				}
				if (world.getBlock(x, y - 1, z) == Blocks.cobblestone) {
					return true;
				}
				if (world.getBlock(x, y - 1, z) == Blocks.mossy_cobblestone) {
					return true;
				}
				if (world.getBlock(x, y - 1, z) == Core.oreBlocks
						&& world.getBlockMetadata(x, y - 1, z) == OresMeta.CORAL_ROCK) {
					return true;
				}
				if (world.getBlock(x, y - 1, z) == Blocks.sand) {
					return true;
				}
			} else if (world.getBlockMetadata(x, y, z) > CoralMeta.KELP_MIDDLE) {
				if (world.getBlock(x, y - 1, z) == Blocks.cobblestone) {
					return true;
				}
				if (world.getBlock(x, y - 1, z) == Blocks.mossy_cobblestone) {
					return true;
				}
				if (world.getBlock(x, y - 1, z) == Core.oreBlocks
						&& world.getBlockMetadata(x, y - 1, z) == OresMeta.CORAL_ROCK) {
					return true;
				}
			}

		}

		return false;
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		if(world instanceof World) {
			if (!this.canBlockStay((World)world, x, y, z)) {
				this.dropBlockAsItem((World)world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				((World)world).setBlock(x, y, z, Blocks.water);
			}
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return canSustainPlant(world, x, y, z, ForgeDirection.UNKNOWN, this);
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int meta) {
		world.notifyBlocksOfNeighborChange(x, y, z, this, meta);
		
		if(BlockHelper.isWater(world, x, y - 1, z) && !world.isAirBlock(x, y + 1, z)) {
			world.setBlock(x, y, z, Blocks.water);
		}
		
		if(world.getBlockMetadata(x, y, z) <= CoralMeta.KELP_MIDDLE) {
			if (world.getBlock(x, y - 1, z) == this) {
				world.setBlockMetadataWithNotify(x, y - 1, z, CoralMeta.KELP_MIDDLE, 2);
			}
			
			if (world.getBlock(x, y + 1, z) == this) {
				world.setBlockMetadataWithNotify(x, y, z, CoralMeta.KELP_MIDDLE, 2);
			}
			
			if (world.getBlock(x, y + 1, z) != this) {
				world.setBlockMetadataWithNotify(x, y, z, CoralMeta.KELP, 2);
			}
		}
		
		world.notifyBlocksOfNeighborChange(x, y, z, this, meta);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public int damageDropped(int i) {
		if (i == CoralMeta.KELP_MIDDLE) {
			return CoralMeta.KELP;
		}

		return i;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 1;
	}
	
	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z) {
		return this;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Water;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icons[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "coral_" + getName(new ItemStack(this, 1, i)));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
		for (int meta = 0; meta < CoralMeta.COUNT; ++meta) {
			if (meta != CoralMeta.KELP_MIDDLE) {
				list.add(new ItemStack(item, 1, meta));
			}
		}
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			if (j == 1) {
				j++;
			}
			MaricultureRegistry.register("coral." + getName(new ItemStack(this, 1, j)), new ItemStack(this, 1, j));
		}
	}

	@Override
	public int getMetaCount() {
		return CoralMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		return RegistryHelper.getName(stack);
	}
}
