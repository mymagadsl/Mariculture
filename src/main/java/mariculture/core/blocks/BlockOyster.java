package mariculture.core.blocks;

import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.core.Core;
import mariculture.core.handlers.PearlGenHandler;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.util.Rand;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOyster extends BlockMachine {
	private final Random rand = new Random();
	public static final int NET = 4;

	public BlockOyster() {
		super(Material.water);
		setTickRandomly(true);
	}
	
	@Override
	public String getToolType(int meta) {
		return meta == NET? "axe": "pickaxe";
	}

	@Override
	public int getToolLevel(int meta) {
		return meta == NET? 0: 2;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) != NET)
			return blockHardness;
		return 0.05F;
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
		return RenderIds.BLOCK_SINGLE;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) == NET) {
			return true;
		} else {
			if (world.getBlock(x, y + 1, z).getMaterial() != Material.water) {
				//func_147480_a = destroyBlock
				world.func_147480_a(x, y, z, true);
				return false;
			}
	
			if (!world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
				world.func_147480_a(x, y, z, true);
				return false;
			}
		}

		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(world.getBlockMetadata(x, y, z) != NET) {
			if (!this.canBlockStay(world, x, y, z)) {
				if (rand.nextInt(10) == 0) {
					this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
				}
	
				world.setBlockToAir(x, y, z);
			}
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		if(block.getBlockMetadata(x, y, z) != NET)
			setBlockBounds(0F, 0F, 0F, 1F, 0.1F, 1F);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.015625F, 1.0F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		if (world.getBlockMetadata(x, y, z) != NET)
			return super.getCollisionBoundingBoxFromPool(world, x, y, z);
		return AxisAlignedBB.getAABBPool().getAABB((double) x + this.minX, (double) y + this.minY,		
			(double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
	}
	
	@Override
	public Item getItemDropped(int meta, Random random, int j) {
		if(meta == NET)
			return Fishery.net;
		return super.getItemDropped(meta, random, j);
    }

	@Override
	public int damageDropped(int i) {
		return 0;
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if(stack.getItemDamage() == 0) {
			int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
	
			if (facing == 0) {
				world.setBlockMetadataWithNotify(x, y, z, 0, 2);
			}
	
			if (facing == 1) {
				world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			}
	
			if (facing == 2) {
				world.setBlockMetadataWithNotify(x, y, z, 1, 2);
			}
	
			if (facing == 3) {
				world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			}
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (!Extra.DEBUG_ON) {
			if(!BlockHelper.isWater(world, x, y + 1, z))
				return false;
		}

		return world.isAirBlock(x, y, z) || world.getBlock(x, y, z).getMaterial().isReplaceable();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tile_entity = world.getTileEntity(x, y, z);

		if (tile_entity == null || (player.isSneaking() && !Extra.DEBUG_ON)) {
			return false;
		}

		if (tile_entity instanceof TileOldOyster) {
			TileOldOyster oyster = (TileOldOyster) tile_entity;
			
			//Spawn in the Jewelry Book on first collection of a pearl
			if(Extra.SPAWN_BOOKS) {
				ItemStack stack = oyster.getStackInSlot(0);
				if(stack != null && stack.getItem() != Item.getItemFromBlock(Blocks.sand)) {
					if(!player.getEntityData().hasKey("EnchantsBook")) {
						player.getEntityData().setBoolean("EnchantsBook", true);
						ItemStack book = new ItemStack(Core.guides, 1, GuideMeta.ENCHANTS);
						if (!player.inventory.addItemStackToInventory(book)) {
							if(!world.isRemote) {
								SpawnItemHelper.spawnItem(world, x, y + 1, z, book);
							}
						}
					}
				}
			}

			if (!player.isSneaking()) {
				if (!world.isRemote) {
					if (player.getCurrentEquippedItem() != null
							&& player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.sand)) {
						if (!oyster.hasContents()) {
							oyster.setInventorySlotContents(0, new ItemStack(Blocks.sand));
							if (!player.capabilities.isCreativeMode) {
								player.inventory.decrStackSize(player.inventory.currentItem, 1);
							}
							
							return true;
						}
					}
					
					BlockHelper.dropItems(world, x, y, z);
					oyster.setInventorySlotContents(0, null);
					return true;

				}
			}

			if (player.isSneaking() && Extra.DEBUG_ON) {
				player.openGui(Mariculture.instance, GuiIds.OYSTER, world, x, y, z);

				return true;
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta != NET)
			return new TileOldOyster();
		return null;
	}

	@Override
	public String getName(ItemStack stack) {
		if(stack.getItemDamage() != NET)
			return "oyster";
		return "net";
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {		
		if(world.getBlockMetadata(x, y, z) != NET) {
			TileOldOyster oyster = (TileOldOyster) world.getTileEntity(x, y, z);
			if(!world.isRemote) {
				if(oyster.hasSand() && BlockHelper.isWater(world, x, y + 1, z)) {
					if(rand.nextInt(Extra.PEARL_GEN_CHANCE) == 0) {
						if (world.getBlock(x, y - 1, z) == Core.pearlBlock) {
							oyster.setInventorySlotContents(0, new ItemStack(Core.pearls, 1, world.getBlockMetadata(x, y - 1, z)));
						} else {
							oyster.setInventorySlotContents(0, PearlGenHandler.getRandomPearl(rand));
						}
					}
				}
			}
		} else {
			if(Rand.nextInt(MachineSpeeds.getNetSpeed())) {
				ItemStack loot = Fishing.loot.getLoot(rand, EnumRodQuality.OLD, world, x, y, z);
				if (loot != null && loot.getItem() instanceof ItemFishy) {
					SpawnItemHelper.spawnItem(world, x, y, z, loot, true, OreDictionary.WILDCARD_VALUE);
				}
			}
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta < NET)
			return icons[0];
		return icons[1];
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) == NET)
			return 0;
		return 1;
    }
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) == NET && Modules.fishery.isActive()) {
			return new ItemStack(Fishery.net, 1, 0);
		}
		
		return new ItemStack(this);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[2];
		icons[0] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, 0)));
		icons[1] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, NET)));
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public boolean isActive(int meta) {
		return true;
	}
}
