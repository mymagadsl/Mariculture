package mariculture.transport;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Modules.Module;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.EntityRegistry;

public class Transport extends Module {
	public static boolean isActive;
	
	@Override
	public boolean isActive() {
		return this.isActive;
	}
	
	@Override
	public void setActive(boolean active) {
		isActive = active;
	}

	public static Item speedBoat;
	
	@Override
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntitySpeedBoat.class, "speedBoat", EntityIds.SPEED_BOAT, Mariculture.instance, 250, 5, false);
	}

	@Override
	public void registerItems() {
		speedBoat = new ItemSpeedBoat().setUnlocalizedName("speedBoat");
		RegistryHelper.register(new Object[] { speedBoat });
	}

	@Override
	public void addRecipes() {
		RecipeHelper.addShapedRecipe(new ItemStack(speedBoat), new Object[] {
			"G F", "AAA", 'G', Blocks.glass_pane, 'F', new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), 'A', "ingotAluminum"
		});
	}
}
