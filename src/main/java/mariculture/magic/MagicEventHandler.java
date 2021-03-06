package mariculture.magic;

import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.cofh.ItemHelper;
import mariculture.core.lib.Jewelry;
import mariculture.magic.enchantments.EnchantmentBlink;
import mariculture.magic.enchantments.EnchantmentClock;
import mariculture.magic.enchantments.EnchantmentFallDamage;
import mariculture.magic.enchantments.EnchantmentFire;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentOneRing;
import mariculture.magic.enchantments.EnchantmentPoison;
import mariculture.magic.enchantments.EnchantmentPunch;
import mariculture.magic.enchantments.EnchantmentResurrection;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.enchantments.EnchantmentSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MagicEventHandler {
	private Random rand = new Random();
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		if(event.entity instanceof EntityPlayer) {
			World world = event.entity.worldObj;
			EntityPlayer player = (EntityPlayer) event.entity;
			if(world.isRemote) {
				if(EnchantHelper.exists(Magic.glide))
					EnchantmentGlide.activate(player);
				if(EnchantHelper.exists(Magic.speed))
					EnchantmentSpeed.activate(player);
				if(EnchantHelper.exists(Magic.spider))
					EnchantmentSpider.activate(player);
			} else if(EnchantHelper.exists(Magic.oneRing)){
				EnchantmentOneRing.activate((EntityPlayer) event.entity);
			}
		}
	}

	@SubscribeEvent
	public void onEntityJump(LivingJumpEvent event) {
		if(EnchantHelper.exists(Magic.jump)) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				if (player.worldObj.isRemote) {
					EnchantmentJump.activate(player);
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityFall(LivingFallEvent event) {
		if(EnchantHelper.exists(Magic.glide) || EnchantHelper.exists(Magic.fall) || EnchantHelper.exists(Magic.flight)) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				if (!player.worldObj.isRemote) {
					EnchantmentGlide.damage(player, rand);
					EnchantmentFallDamage.activate(event);
	
					if (EnchantHelper.hasEnchantment(Magic.flight, player)) {
						event.setCanceled(true);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		if(EnchantHelper.exists(Magic.fire)) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
	
				EnchantmentFire.testForFireDamage(event);
			}
		}
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event) {
		if(EnchantHelper.exists(Magic.fire)) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				EnchantmentFire.activate(player, event);
			}
		}
	}

	@SubscribeEvent
	public void onAttackEntity(AttackEntityEvent event) {
		if(EnchantHelper.exists(Magic.fire) || EnchantHelper.exists(Magic.poison) || EnchantHelper.exists(Magic.punch)) {
			EntityPlayer player = event.entityPlayer;
			Entity target = event.target;
			if (!player.worldObj.isRemote) {
				if (MaricultureHandlers.mirror.containsEnchantedItems(player)) {
					EnchantmentFire.onAttack(player, target);
					EnchantmentPoison.onAttack(player, target);
					EnchantmentPunch.onAttack(player, target);
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event) {
		if(EnchantHelper.exists(Magic.fire) || EnchantHelper.exists(Magic.poison)) {
			EntityPlayer player = (EntityPlayer) event.entity;
			Entity target = event.target;
			if (!player.worldObj.isRemote) {
				if (MaricultureHandlers.mirror.containsEnchantedItems(player)) {
					EnchantmentFire.onRightClick(player, target);
					EnchantmentPoison.onRightClick(player, target);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(EnchantHelper.exists(Magic.blink)) {
			if (event.entityPlayer.worldObj.isRemote && event.action == Action.RIGHT_CLICK_AIR) {
				EnchantmentBlink.sendPacket(event.entityPlayer);
			}
		}
	}

	@SubscribeEvent
	public void onWorldUpdate(WorldEvent event) {
		if(EnchantHelper.exists(Magic.clock)) {
			World world = event.world;
			if (!world.isRemote && world.provider.isSurfaceWorld()) {
				if (world.isDaytime()) {
					EnchantmentClock.activate(world, Jewelry.NIGHT, 18000);
				} else {
					EnchantmentClock.activate(world, Jewelry.DAY, 6000);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		if(event.source.getSourceOfDamage() != null && event.source.getSourceOfDamage() instanceof EntityPlayer) {
			EntityLivingBase entity = event.entityLiving;
			EntityPlayer player = (EntityPlayer) event.source.getSourceOfDamage();
			if(!(entity instanceof EntityPlayer)) {
				if(ItemHelper.isPlayerHoldingItem(Magic.magnet, player)) {
					ItemStack magnet = player.getCurrentEquippedItem();
					if(!magnet.hasTagCompound()) {
						magnet.setTagCompound(new NBTTagCompound());
						magnet.stackTagCompound.setString("MobClass", entity.getClass().toString().substring(6));
						//TODO: Set Mob Name for Mob Magnet magnet.stackTagCompound.setString("MobName", entity.getEntityString());
					}
				}
			}
 		}
	}

	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event) {
		if(EnchantHelper.exists(Magic.resurrection)) {
			EnchantmentResurrection.activate(event);
		}
	}
}
