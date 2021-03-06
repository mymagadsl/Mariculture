package mariculture.fishery.fish;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishGlow extends FishSpecies {
	public FishGlow(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.NETHER;
	}

	@Override
	public int getLifeSpan() {
		return 25;
	}

	@Override
	public int getFertility() {
		return 83;
	}

	@Override
	public boolean isDominant() {
		return false;
	}

	@Override
	public int getTankLevel() {
		return 3;
	}

	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_NETHER), 7.5D);
		addProduct(new ItemStack(Items.glowstone_dust), 7.5D);
	}
	
	@Override
	public boolean isWorldCorrect(World world) {
		return world.provider.isHellWorld;
	}

	@Override
	public int getCatchChance() {
		return 15;
	}
	
	@Override
	public EnumRodQuality getRodNeeded() {
		return EnumRodQuality.GOOD;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.155;
	}

	@Override
	public int[] getChestGenChance() {
		return null;
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
}
