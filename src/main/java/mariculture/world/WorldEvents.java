package mariculture.world;

import org.apache.logging.log4j.Level;

import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.WorldGeneration;
import mariculture.world.terrain.MapGenCavesWater;
import mariculture.world.terrain.MapGenMineshaftsDisabled;
import mariculture.world.terrain.MapGenRavineWater;
import mariculture.world.terrain.MapGenRavineWaterBOP;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents {
	@SubscribeEvent
	public void onWorldGen(InitMapGenEvent event) {
		if(WorldGeneration.NO_MINESHAFTS) {
			if(event.type == EventType.MINESHAFT) {
				try {
					event.newGen = new MapGenMineshaftsDisabled();
				} catch (Exception e) {
					LogHandler.log(Level.TRACE, "Mariculture couldn't remove mineshafts from oceans");
				}
			}
		}
			
		if(WorldGeneration.WATER_CAVES) {
			if(event.type == EventType.CAVE) {
				try {
					event.newGen = new MapGenCavesWater();
				} catch (Exception e) {
					LogHandler.log(Level.TRACE, "Mariculture couldn't add water filled caves");
				}
			}
		}
			
		if(WorldGeneration.WATER_RAVINES) {
			if(event.type == EventType.RAVINE) {
				try {
					if(Loader.isModLoaded("BiomesOPlenty")) {
						event.newGen = new MapGenRavineWaterBOP();
					} else {
						event.newGen = new MapGenRavineWater();
					}
				} catch (Exception e) {
					LogHandler.log(Level.TRACE, "Mariculture couldn't add water filled ravines");
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldDecorate(Decorate event) {
		
	}
}
