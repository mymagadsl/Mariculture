package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Jewelry;
import mariculture.core.lib.Text;
import net.minecraft.item.ItemStack;

public class PartGoldThread extends JewelryPart {
	@Override
	public boolean isValid(int type) {
		return type == Jewelry.NECKLACE;
	}

	@Override
	public boolean isVisible(int type) {
		return false;
	}

	@Override
	public String getPartName() {
		return "goldThread";
	}

	@Override
	public String getPartLang() {
		return "item.craftingItems.goldenThread.name";
	}

	@Override
	public int getEnchantability() {
		return 48;
	}

	@Override
	public String getColor() {
		return Text.YELLOW;
	}

	@Override
	public String getPartType(int type) {
		return "string";
	}

	@Override
	public ItemStack getItemStack() {
		return new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD);
	}
	
	@Override
	public int getHits(int type) {
		return 150;
	}
	
	@Override
	public double getDurabilityModifier(int type) {
		return 15.0D;
	}
}
