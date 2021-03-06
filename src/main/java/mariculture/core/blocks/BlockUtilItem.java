package mariculture.core.blocks;

import mariculture.core.lib.UtilMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockUtilItem extends ItemBlockMariculture {
	public BlockUtilItem(Block block) {
		super(block);
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case UtilMeta.INCUBATOR_BASE: 
			name = "incubatorBase";
			break;
		case UtilMeta.INCUBATOR_TOP: 
			name = "incubatorTop";
			break;
		case UtilMeta.AUTOFISHER:
			name = "autoFishing";
			break;
		case UtilMeta.LIQUIFIER:
			name = "liquifier";
			break;
		case UtilMeta.BOOKSHELF:
			name = "bookshelf";
			break;
		case UtilMeta.SAWMILL:
			name = "sawmill";
			break;
		case UtilMeta.SLUICE:
			name = "sluice";
			break;
		case UtilMeta.SPONGE: 
			name = "sponge";
			break;
		case UtilMeta.DICTIONARY: 
			name = "dictionary";
			break;
		case UtilMeta.FISH_SORTER:
			name = "fishSorter";
			break;
		default:
			name = "utilBlocks";
		}

		return name;
	}
}