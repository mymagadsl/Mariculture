package mariculture.plugins;

import net.minecraft.world.World;
import mariculture.plugins.PluginBiomesOPlenty.Biome;
import mariculture.plugins.Plugins.Plugin;

public class PluginBiomesOPlenty extends Plugin {
	public static enum Biome {
		KELP, CORAL
	}
		
	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		
	}
	
	//TODO: BiomesOP Coral + Kelp Support
	public static boolean isBiome(World world, int x, int z, Biome biome) {
		return true;
	}
	
	/*
	public PluginBiomesOPlenty(String name) {
		super(name);
	}

	private static void addBiome(Optional<? extends BiomeGenBase> biome, EnumBiomeType type) {
		if (biome.isPresent()) {
			MaricultureHandlers.biomeType.addBiome(biome.get(), type);
		}
	}
	
	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {		
		addBiome(Biomes.alps, EnumBiomeType.COLD);
		addBiome(Biomes.alpsForest, EnumBiomeType.COLD);
		addBiome(Biomes.alpsBase, EnumBiomeType.COLD);
		addBiome(Biomes.arctic, EnumBiomeType.FROZEN);
		addBiome(Biomes.badlands, EnumBiomeType.HELL);
		addBiome(Biomes.bambooForest, EnumBiomeType.HOT);
		addBiome(Biomes.bayou, EnumBiomeType.HOT);
		addBiome(Biomes.beachGravel, EnumBiomeType.NORMAL);
		addBiome(Biomes.beachOvergrown, EnumBiomeType.NORMAL);
		addBiome(Biomes.birchForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.bog, EnumBiomeType.NORMAL);
		addBiome(Biomes.borealForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.brushland, EnumBiomeType.HOT);
		addBiome(Biomes.canyon, EnumBiomeType.HOT);
		addBiome(Biomes.canyonRavine, EnumBiomeType.HOT);
		addBiome(Biomes.chaparral, EnumBiomeType.HOT);
		addBiome(Biomes.cherryBlossomGrove, EnumBiomeType.NORMAL);
		addBiome(Biomes.coniferousForest, EnumBiomeType.COLD);
		addBiome(Biomes.coniferousForestSnow, EnumBiomeType.COLD);
		addBiome(Biomes.crag, EnumBiomeType.ARID);
		addBiome(Biomes.deadForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.deadForestSnow, EnumBiomeType.COLD);
		addBiome(Biomes.deadSwamp, EnumBiomeType.NORMAL);
		addBiome(Biomes.deadlands, EnumBiomeType.ARID);
		addBiome(Biomes.deciduousForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.dunes, EnumBiomeType.ARID);
		addBiome(Biomes.fen, EnumBiomeType.NORMAL);
		addBiome(Biomes.field, EnumBiomeType.NORMAL);
		addBiome(Biomes.fieldForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.frostForest, EnumBiomeType.COLD);
		addBiome(Biomes.fungiForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.garden, EnumBiomeType.NORMAL);
		addBiome(Biomes.glacier, EnumBiomeType.FROZEN);
		addBiome(Biomes.grassland, EnumBiomeType.NORMAL);
		addBiome(Biomes.grove, EnumBiomeType.NORMAL);
		addBiome(Biomes.heathland, EnumBiomeType.NORMAL);
		addBiome(Biomes.highland, EnumBiomeType.COLD);
		addBiome(Biomes.hotSprings, EnumBiomeType.NORMAL);
		addBiome(Biomes.icyHills, EnumBiomeType.COLD);
		addBiome(Biomes.jadeCliffs, EnumBiomeType.NORMAL);
		addBiome(Biomes.lushDesert, EnumBiomeType.ARID);
		addBiome(Biomes.lushSwamp, EnumBiomeType.NORMAL);
		addBiome(Biomes.mangrove, EnumBiomeType.HOT);
		addBiome(Biomes.mapleWoods, EnumBiomeType.NORMAL);
		addBiome(Biomes.marsh, EnumBiomeType.NORMAL);
		addBiome(Biomes.meadow, EnumBiomeType.NORMAL);
		addBiome(Biomes.meadowForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.mesa, EnumBiomeType.HOT);
		addBiome(Biomes.moor, EnumBiomeType.COLD);
		addBiome(Biomes.mountain, EnumBiomeType.COLD);
		addBiome(Biomes.mysticGrove, EnumBiomeType.NORMAL);
		addBiome(Biomes.netherBase, EnumBiomeType.HELL);
		addBiome(Biomes.netherGarden, EnumBiomeType.HELL);
		addBiome(Biomes.netherDesert, EnumBiomeType.HELL);
		addBiome(Biomes.netherLava, EnumBiomeType.HELL);
		addBiome(Biomes.netherBone, EnumBiomeType.HELL);
		addBiome(Biomes.oasis, EnumBiomeType.NORMAL);
		addBiome(Biomes.oceanCoral, EnumBiomeType.OCEAN);
		addBiome(Biomes.oceanKelp, EnumBiomeType.OCEAN);
		addBiome(Biomes.ominousWoods, EnumBiomeType.NORMAL);
		addBiome(Biomes.ominousWoodsThick, EnumBiomeType.NORMAL);
		addBiome(Biomes.orchard, EnumBiomeType.NORMAL);
		addBiome(Biomes.originValley, EnumBiomeType.NORMAL);
		addBiome(Biomes.outback, EnumBiomeType.HOT);
		addBiome(Biomes.overgrownGreens, EnumBiomeType.NORMAL);
		addBiome(Biomes.pasture, EnumBiomeType.NORMAL);
		addBiome(Biomes.pastureMeadow, EnumBiomeType.NORMAL);
		addBiome(Biomes.pastureThin, EnumBiomeType.NORMAL);
		addBiome(Biomes.polar, EnumBiomeType.FROZEN_OCEAN);
		addBiome(Biomes.prairie, EnumBiomeType.HOT);
		addBiome(Biomes.promisedLandForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.promisedLandPlains, EnumBiomeType.NORMAL);
		addBiome(Biomes.promisedLandSwamp, EnumBiomeType.NORMAL);
		addBiome(Biomes.quagmire, EnumBiomeType.NORMAL);
		addBiome(Biomes.rainforest, EnumBiomeType.HOT);
		addBiome(Biomes.redwoodForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.sacredSprings, EnumBiomeType.NORMAL);
		addBiome(Biomes.savanna, EnumBiomeType.HOT);
		addBiome(Biomes.savannaPlateau, EnumBiomeType.HOT);
		addBiome(Biomes.scrubland, EnumBiomeType.HOT);
		addBiome(Biomes.seasonalForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.seasonalSpruceForest, EnumBiomeType.COLD);
		addBiome(Biomes.shield, EnumBiomeType.NORMAL);
		addBiome(Biomes.shore, EnumBiomeType.NORMAL);
		addBiome(Biomes.shrubland, EnumBiomeType.NORMAL);
		addBiome(Biomes.shrublandForest, EnumBiomeType.NORMAL);
		addBiome(Biomes.silkglades, EnumBiomeType.NORMAL);
		addBiome(Biomes.sludgepit, EnumBiomeType.NORMAL);
		addBiome(Biomes.spruceWoods, EnumBiomeType.COLD);
		addBiome(Biomes.steppe, EnumBiomeType.NORMAL);
		addBiome(Biomes.temperateRainforest, EnumBiomeType.NORMAL);
		addBiome(Biomes.thicket, EnumBiomeType.NORMAL);
		addBiome(Biomes.timber, EnumBiomeType.NORMAL);
		addBiome(Biomes.timberThin, EnumBiomeType.NORMAL);
		addBiome(Biomes.tropicalRainforest, EnumBiomeType.HOT);
		addBiome(Biomes.tropics, EnumBiomeType.HOT);
		addBiome(Biomes.tundra, EnumBiomeType.COLD);
		addBiome(Biomes.volcano, EnumBiomeType.HOT);
		addBiome(Biomes.wasteland, EnumBiomeType.HOT);
		addBiome(Biomes.wetland, EnumBiomeType.NORMAL);
		addBiome(Biomes.woodland, EnumBiomeType.NORMAL);
		addBiome(Biomes.plainsNew, EnumBiomeType.NORMAL);
		addBiome(Biomes.desertNew, EnumBiomeType.ARID);
		addBiome(Biomes.extremeHillsNew, EnumBiomeType.COLD);
		addBiome(Biomes.forestNew, EnumBiomeType.NORMAL);
		addBiome(Biomes.forestHillsNew, EnumBiomeType.NORMAL);
		addBiome(Biomes.taigaNew, EnumBiomeType.COLD);
		addBiome(Biomes.taigaHillsNew, EnumBiomeType.COLD);
		addBiome(Biomes.swamplandNew, EnumBiomeType.NORMAL);
		addBiome(Biomes.jungleNew, EnumBiomeType.HOT);
		addBiome(Biomes.jungleHillsNew, EnumBiomeType.HOT);
		addBiome(Biomes.autumnHills, EnumBiomeType.NORMAL);
		addBiome(Biomes.lavenderFields, EnumBiomeType.NORMAL);
		addBiome(Biomes.oceanAbyss, EnumBiomeType.OCEAN);
		addBiome(Biomes.tropicsMountain, EnumBiomeType.HOT);
		
		if(Blocks.coral.isPresent()) {
			Block coral = Blocks.coral.get();
			OreDictionary.registerOre("plantKelp", new ItemStack(coral, 1, 3));
			OreDictionary.registerOre("coralPink", new ItemStack(coral, 1, 4));
			OreDictionary.registerOre("coralOrange", new ItemStack(coral, 1, 5));
			OreDictionary.registerOre("coralLightBlue", new ItemStack(coral, 1, 6));
			OreDictionary.registerOre("coralPurple", new ItemStack(coral, 1, 7));
		}
	}

	@Override
	public void postInit() {		
		WorldGeneration.CORAL_CHANCE /= 5;
		WorldGeneration.KELP_HEIGHT *= 2.5;
		WorldGeneration.KELP_CHANCE /= 3;
	}
	
	public static boolean isBiome(World world, int x, int z, Biome biome) {
		WorldType worldType = world.getWorldInfo().getTerrainType();
		boolean isVanilla = true;
		if(biome.equals(Biome.CORAL)) {
			for(String type: WorldGeneration.CORAL_BIOMESOP_TYPES) {
				if(worldType == WorldType.parseWorldType(type)) {
					isVanilla = false;
					break;
				}
			}
		}
		
		if(biome.equals(Biome.KELP)) {
			for(String type: WorldGeneration.KELP_BIOMESOP_TYPES) {
				if(worldType == WorldType.parseWorldType(type)) {
					isVanilla = false;
					break;
				}
			}
		}

		if(isVanilla)
			return true;
		
		if(biome.equals(Biome.KELP) && !WorldGeneration.KELP_BIOMESOP)
			return true;
		if(biome.equals(Biome.CORAL) && !WorldGeneration.CORAL_BIOMESOP)
			return true;
		
		Optional<? extends BiomeGenBase> biomeType = (biome.equals(Biome.KELP))? Biomes.oceanKelp: Biomes.oceanCoral;
		
		if(biomeType.isPresent()) {
			if(world.getWorldChunkManager().getBiomeGenAt(x, z) != biomeType.get()) {
				return false;
			}
		}

		return true;
	} */
}
