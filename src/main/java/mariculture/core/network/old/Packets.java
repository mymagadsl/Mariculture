package mariculture.core.network.old;

import mariculture.core.gui.ContainerMariculture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;

public class Packets {
	public static void updateTile(TileEntity tile, int size, Packet packet) {
		//TODO: PacketDispatcher.sendPacketToAllAround(tile.xCoord, tile.yCoord, tile.zCoord, size, tile.worldObj.provider.dimensionId, packet);
	}
	
	public static void updatePlayer(EntityPlayer player, int size, Packet packet) {
		//TODO: PacketDispatcher.sendPacketToAllAround(player.posX, player.posY, player.posZ, size, player.worldObj.provider.dimensionId, packet);
	}
	
	public static void updateGUI(EntityPlayer player, ContainerMariculture container, int id, int val) {
		//TODO: PacketDispatcher.sendPacketToPlayer(new Packet104GUI(container.windowId, id, val).build(), (Player) player);
	}

	public static void init() {
		PacketRegistry.register(new Packet101Sponge());
		PacketRegistry.register(new Packet102AirPump());
		PacketRegistry.register(new Packet103Oyster());
		PacketRegistry.register(new Packet104GUI());
		PacketRegistry.register(new Packet105OneRing());
		PacketRegistry.register(new Packet106JewelrySwap());
		PacketRegistry.register(new Packet107FLUDD());
		PacketRegistry.register(new Packet108Teleport());
		PacketRegistry.register(new Packet109DamageJewelry());
		PacketRegistry.register(new Packet110CustomTileUpdate());
		PacketRegistry.register(new Packet111UpdateEnchants());
		PacketRegistry.register(new Packet112Enchant());
		PacketRegistry.register(new Packet113MultiInit());
		PacketRegistry.register(new Packet114RedstoneControlled());
		PacketRegistry.register(new Packet115EjectSetting());
		PacketRegistry.register(new Packet116GUIClick());
		PacketRegistry.register(new Packet117AirCompressorUpdate());
		PacketRegistry.register(new Packet118FluidUpdate());
		PacketRegistry.register(new Packet119TurbineAnimate());
		PacketRegistry.register(new Packet120ItemSync());
		PacketRegistry.register(new Packet121FishTankSync());
		PacketRegistry.register(new Packet122KeyPress());
	}
}
