package mariculture.magic.jewelry;

import mariculture.core.lib.Jewelry;

public class ItemNecklace extends ItemJewelry {	
	@Override
	public int getItemEnchantability() {
		return Jewelry.BONUS_NECKLACE;
	}

	@Override
	public int getType() {
		return Jewelry.NECKLACE;
	}

	@Override
	public String getTypeString() {
		return "necklace";
	}

	@Override
	public String getPart1() {
		return Jewelry.NECKLACE_PART1;
	}

	@Override
	public String getPart2() {
		return Jewelry.NECKLACE_PART2;
	}
}
