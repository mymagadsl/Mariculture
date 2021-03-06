package mariculture.plugins.compatibility;

import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Modules;
import mariculture.core.util.FluidDictionary;
import net.minecraftforge.fluids.FluidRegistry;

import org.apache.logging.log4j.Level;

public class Compat {
	public static void preInit() {
		CompatBooks.preInit();
		
		if(FluidRegistry.getFluid("milk") != null) {
			FluidDictionary.instance.addFluid("milk", FluidRegistry.getFluid("milk"));
		}
	}
	
	public static void init() {
		if(Modules.fishery.isActive()) {
			try {
				CompatBait.init();
			} catch (Exception e) {
				LogHandler.log(Level.WARN, "Mariculture - Something went wrong when loading the Bait Compatibility Config");
			}
		}
		
		CompatFluids.init();
		CompatBooks.init();
	}
}
