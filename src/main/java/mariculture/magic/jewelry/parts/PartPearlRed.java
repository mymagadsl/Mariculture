package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.Text;
import net.minecraft.item.ItemStack;

public class PartPearlRed extends JewelryPart {
	@Override
	public boolean isVisible(int type) {
		return true;
	}
	
	@Override
	public String getPartName() {
		return "pearlRed";
	}
	
	@Override
	public String getPartLang() {
		return "item.pearls." + getPartName() + ".name";
	}

	@Override
	public String getPartType(int type) {
		return (type == Jewelry.RING)? "jewel": "material";
	}
	
	@Override
	public String getColor() {
		return Text.RED;
	}
	
	@Override
	public int getEnchantability() {
		return 4;
	}
	
	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Core.pearls, 1, PearlColor.RED);
	}
	
	@Override
	public double getDurabilityModifier(int type) {
		return 5.0D;
	}
	
	@Override
	public int getHits(int type) {
		if(type == Jewelry.RING)
			return 250;
		if(type == Jewelry.BRACELET)
			return 60;
		return 50;
	}
}
