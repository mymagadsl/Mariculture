package mariculture.core.blocks;

import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.blocks.TileAirPump.Type;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.helpers.cofh.ItemHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.SingleMeta;
import mariculture.core.util.Rand;
import mariculture.factory.Factory;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileGeyser;
import mariculture.factory.blocks.TileTurbineBase;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineHand;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.fishery.blocks.TileFeeder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSingle extends BlockMachine {
	public BlockSingle() {
		super(Material.piston);
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}
	
	@Override
	public String getToolType(int meta) {
		return meta == SingleMeta.FISH_FEEDER || meta == SingleMeta.TURBINE_HAND ? "axe": "pickaxe";
	}

	@Override
	public int getToolLevel(int meta) {
		switch(meta) {
			case SingleMeta.TURBINE_GAS:
				return 2;
			case SingleMeta.FISH_FEEDER:
				return 0;
			case SingleMeta.TURBINE_HAND:
				return 0;
			default:
				return 1;
		}
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case SingleMeta.AIR_PUMP:
			return 4F;
		case SingleMeta.FISH_FEEDER:
			return 0.5F;
		case SingleMeta.TURBINE_WATER:
			return 2.5F;
		case SingleMeta.FLUDD_STAND:
			return 3F;
		case SingleMeta.TURBINE_GAS:
			return 5F;
		case SingleMeta.GEYSER:
			return 1F;
		case SingleMeta.ANVIL_1:
			return 6F;
		case SingleMeta.ANVIL_2:
			return 6F;
		case SingleMeta.ANVIL_3:
			return 6F;
		case SingleMeta.ANVIL_4:
			return 6F;
		case SingleMeta.INGOT_CASTER:
			return 1F;
		}

		return 1F;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				return ((TileTurbineBase) tile).direction.getOpposite() == side;
			}
		}
		return false;
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				return ((TileTurbineBase) tile).switchOrientation();
			}
			
			if(tile instanceof TileAirPump) {
				return ((TileAirPump) tile).rotate();
			}
			
			if(tile instanceof TileGeyser) {
				((TileGeyser)tile).orientation = BlockHelper.rotate(((TileGeyser)tile).orientation);
				//TODO: PACKET fix sending render update to geysers 
				/* Packets.updateTile(((TileGeyser)tile), 32, ((TileGeyser)tile).getDescriptionPacket());
				world.markBlockForRenderUpdate(x, y, z); */
			}
		}
		return false;
	}
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				TileTurbineBase turbine = (TileTurbineBase) tile;
				turbine.direction = ForgeDirection.UP;
				turbine.switchOrientation();
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		int facing = BlockPistonBase.determineOrientation(world, x, y, z, entity);
		if (tile != null) {
			if (tile instanceof TileFLUDDStand) {
				TileFLUDDStand fludd = (TileFLUDDStand) tile;
				fludd.orientation = ForgeDirection.getOrientation(facing);
				int water = 0;
				if (stack.hasTagCompound()) {
					water = stack.stackTagCompound.getInteger("water");
				}

				fludd.tank.setCapacity(ItemArmorFLUDD.STORAGE);
				fludd.tank.setFluidID(Core.highPressureWater.getID());
				fludd.tank.setFluidAmount(water);
				//TODO: PACKET Packets.updateTile(fludd, 32, fludd.getDescriptionPacket());
			}
			
			if(tile instanceof TileGeyser) {
				((TileGeyser)tile).orientation = ForgeDirection.getOrientation(facing);
				//TODO: PACKET Packets.updateTile(((TileGeyser)tile), 32, ((TileGeyser)tile).getDescriptionPacket());
			}
		}
		
		int meta = stack.getItemDamage();
		if(meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4) {
	        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	        int i1 = world.getBlockMetadata(x, y, z) >> 2;
	        ++l;
	        l %= 4;
	
	        if (l == 0)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, SingleMeta.ANVIL_3, 2);
	        }
	
	        if (l == 1)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, SingleMeta.ANVIL_4, 2);
	        }
	
	        if (l == 2)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, SingleMeta.ANVIL_1, 2);
	        }
	
	        if (l == 3)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, SingleMeta.ANVIL_2, 2);
	        }
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f, float g, float t) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || (player.isSneaking() && !world.isRemote)) {
			return false;
		}
		
		if (tile instanceof TileAirPump && Extra.ACTIVATE_PUMP) {	
			TileAirPump pump = (TileAirPump) tile;
			if (pump.animate == false) {
				if(Modules.diving.isActive()) {
					if(pump.updateAirArea(Type.CHECK)) {
						if(!world.isRemote)
							pump.supplyWithAir(300, 64.0D, 64.0D, 64.0D);
						pump.animate = true;
					}
				}
				if(pump.suckUpGas(1024)) {
					pump.animate = true;
				}
			}
			
			if(world.isRemote && player.isSneaking())
				((TileAirPump) tile).updateAirArea(Type.DISPLAY);
			return true;
		}
		
		if(player.isSneaking())
			return false;
		
		if(tile instanceof TileTurbineHand) {
			if(player.getDisplayName().equals("[CoFH]"))
				return false;
			if(player instanceof FakePlayer) {
				return false;
			}

            TileTurbineHand turbine = (TileTurbineHand)tile;
			
			turbine.energyStorage.modifyEnergyStored(((TileTurbineHand)tile).getEnergyGenerated());
			turbine.isCreatingPower = true;
			turbine.cooldown = 5;

            player.getFoodStats().addStats(0, (float)-world.difficultySetting.getDifficultyId() * 1.5F);

            if(turbine.produced >= 1200) {
                player.attackEntityFrom(MaricultureDamage.turbine, world.difficultySetting.getDifficultyId());
            }

			return true;
		}

		if (tile instanceof TileFLUDDStand) {
			player.openGui(Mariculture.instance, GuiIds.FLUDD_BLOCK, world, x, y, z);
			return true;
		}

		if (tile instanceof TileTurbineWater) {
			player.openGui(Mariculture.instance, GuiIds.TURBINE, world, x, y, z);
			return true;
		}
		
		if (tile instanceof TileTurbineGas) {
			player.openGui(Mariculture.instance, GuiIds.TURBINE_GAS, world, x, y, z);
			return true;
		}

		if (tile instanceof TileFeeder) {
			((TileFeeder) tile).updateTankSize();
			player.openGui(Mariculture.instance, GuiIds.FEEDER, world, x, y, z);
			return true;
		}
		
		if(tile instanceof TileAnvil) {
			if(player.getDisplayName().equals("[CoFH]"))
				return false;
			if(player instanceof FakePlayer)
				return false;
			TileAnvil anvil = (TileAnvil) tile;
			if(anvil.getStackInSlot(0) != null) {
				//TODO: PACKET ANvil Item Sycn new Packet120ItemSync(x, y, z, anvil.getInventory()).build();
				if (!player.inventory.addItemStackToInventory(anvil.getStackInSlot(0))) {
					if(!world.isRemote) {
						SpawnItemHelper.spawnItem(world, x, y + 1, z, anvil.getStackInSlot(0));
					}
				}
					
				anvil.setInventorySlotContents(0, null);
			} else if(player.getCurrentEquippedItem() != null) {
				ItemStack stack = player.getCurrentEquippedItem().copy();
				stack.stackSize = 1;
				anvil.setInventorySlotContents(0, stack);
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
			}
			
			
			return true;
		}
		
		if(tile instanceof TileIngotCaster) {
			if (!world.isRemote) {
				TileIngotCaster caster = (TileIngotCaster) tile;
				for(int i = 0; i < caster.getSizeInventory(); i++) {
					if(caster.getStackInSlot(i) != null) {
						SpawnItemHelper.spawnItem(world, x, y + 1, z, caster.getStackInSlot(i));
						caster.setInventorySlotContents(i, null);
						caster.markDirty();
					}
				}
			}
			
			return FluidHelper.handleFillOrDrain((IFluidHandler) world.getTileEntity(x, y, z), player, ForgeDirection.UP);
		}
		
		if(tile instanceof TileGeyser) {
			return FluidHelper.handleFillOrDrain((IFluidHandler) world.getTileEntity(x, y, z), player, ForgeDirection.UP);
		}

		return false;
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileAnvil && ItemHelper.isPlayerHoldingItem(Core.hammer, player)) {
			if(player.getDisplayName().equals("[CoFH]"))
				return;
			if(player instanceof FakePlayer)
				return;
			ItemStack hammer = player.getCurrentEquippedItem();
			if (((TileAnvil)tile).workItem(player, hammer)) {
				if(hammer.attemptDamageItem(1, Rand.rand))
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		int meta = block.getBlockMetadata(x, y, z);
		ForgeDirection facing;

		switch (meta) {
		case SingleMeta.AIR_PUMP:
			setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.9F, 0.8F);
			break;
		case SingleMeta.GEYSER:
			TileGeyser geyser = (TileGeyser)block.getTileEntity(x, y, z);
			if(geyser.orientation == ForgeDirection.UP)
				setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.25F, 0.9F);
			if(geyser.orientation == ForgeDirection.DOWN)
				setBlockBounds(0.1F, 0.75F, 0.1F, 0.9F, 1.0F, 0.9F);
			if(geyser.orientation == ForgeDirection.EAST)
				setBlockBounds(0.0F, 0.1F, 0.1F, 0.25F, 0.9F, 0.9F);
			if(geyser.orientation == ForgeDirection.WEST)
				setBlockBounds(0.75F, 0.1F, 0.1F, 1F, 0.9F, 0.9F);
			if(geyser.orientation == ForgeDirection.SOUTH)
				setBlockBounds(0.1F, 0.1F, 0.0F, 0.9F, 0.9F, 0.25F);
			if(geyser.orientation == ForgeDirection.NORTH)
				setBlockBounds(0.1F, 0.1F, 0.75F, 0.9F, 0.9F, 1.0F);
			break;
		case SingleMeta.ANVIL_1:
			setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
			break;
		case SingleMeta.ANVIL_2:
			setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
			break;
		case SingleMeta.ANVIL_3:
			setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
			break;
		case SingleMeta.ANVIL_4:
			setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
		default:
			setBlockBounds(0F, 0F, 0F, 1F, 0.95F, 1F);
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == SingleMeta.GEYSER || (meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4)) {
			return AxisAlignedBB.getAABBPool().getAABB((double) x + this.minX, (double) y + this.minY,
					(double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
		}

		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case SingleMeta.AIR_PUMP:
			return new TileAirPump();
		case SingleMeta.FISH_FEEDER:
			return new TileFeeder();
		case SingleMeta.TURBINE_WATER:
			return new TileTurbineWater();
		case SingleMeta.FLUDD_STAND:
			return new TileFLUDDStand();
		case SingleMeta.TURBINE_GAS:
			return new TileTurbineGas();
		case SingleMeta.TURBINE_HAND:
			return new TileTurbineHand();
		case SingleMeta.GEYSER:
			return new TileGeyser();
		case SingleMeta.INGOT_CASTER:
			return new TileIngotCaster();
		}
		
		if(meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4)
			return new TileAnvil();

		return null;
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
	public int getRenderType() {
		return RenderIds.BLOCK_SINGLE;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		BlockHelper.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	//TODO: Fludd Keep it's water contents
	/*
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		if (!world.isRemote) {
			if (world.getBlockMetadata(x, y, z) == SingleMeta.FLUDD_STAND) {
				if (!player.capabilities.isCreativeMode) {
					if (world.getTileEntity(x, y, z) instanceof TileFLUDDStand) {
						dropFLUDD(world, x, y, z);
					}
				}
			}
		}

		return world.setBlockToAir(x, y, z);
	} */

	private void dropFLUDD(World world, int x, int y, int z) {
		TileFLUDDStand tile = (TileFLUDDStand) world.getTileEntity(x, y, z);
		ItemStack drop = new ItemStack(Factory.fludd);

		if (!drop.hasTagCompound()) {
			drop.setTagCompound(new NBTTagCompound());
		}

		if (tile != null) {
			drop.stackTagCompound.setInteger("water", tile.tank.getFluidAmount());
		}

		EntityItem entityitem = new EntityItem(world, (x), (float) y + 1, (z), new ItemStack(drop.getItem(), 1,drop.getItemDamage()));

		if (drop.hasTagCompound()) {
			entityitem.getEntityItem().setTagCompound((NBTTagCompound) drop.getTagCompound().copy());
		}

		world.spawnEntityInWorld(entityitem);
	}
	
	public Item getItemDropped(int i, Random random, int j) {
		return i == SingleMeta.FLUDD_STAND? null: super.getItemDropped(i, random, j);
    }
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta == SingleMeta.GEYSER)
			return Blocks.hopper.getIcon(0, 0);
		if(meta == SingleMeta.INGOT_CASTER)
			return super.getIcon(side, meta);
		if(meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4)
			return super.getIcon(side, SingleMeta.INGOT_CASTER);
		
		return icons[meta];
	}

	@Override
	public int damageDropped(int i) {
		if(i >= SingleMeta.ANVIL_1 && i <= SingleMeta.ANVIL_4)
			return SingleMeta.ANVIL_1;
		return i;
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case SingleMeta.FISH_FEEDER:
			return Modules.fishery.isActive();
		case SingleMeta.TURBINE_WATER:
			return Modules.factory.isActive();
		case SingleMeta.FLUDD_STAND:
			return false;
		case SingleMeta.TURBINE_GAS:
			return Modules.factory.isActive();
		case SingleMeta.GEYSER:
			return Modules.factory.isActive();
		case SingleMeta.TURBINE_HAND:
			return Modules.factory.isActive();
		case SingleMeta.ANVIL_2:
			return false;
		case SingleMeta.ANVIL_3:
			return false;
		case SingleMeta.ANVIL_4:
			return false;
		default:
			return true;
		}
	}

	@Override
	public int getMetaCount() {
		return SingleMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			if(i <= SingleMeta.ANVIL_1 || i > SingleMeta.ANVIL_4) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)));
			}
		}
	}
}
