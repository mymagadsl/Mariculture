package mariculture.world.terrain;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenCaves;

public class MapGenCavesWater extends MapGenCaves {	
	private boolean isExceptionBiome(BiomeGenBase biome) {
        if (biome == BiomeGenBase.mushroomIsland) return true;
        if (biome == BiomeGenBase.beach) return true;
        if (biome == BiomeGenBase.desert) return true;
        return false;
    }
	
	protected boolean isOceanBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
	    if(MaricultureHandlers.biomeType.getBiomeType(biome) == EnumBiomeType.OCEAN && y < 63) {
	    	return false;
	    }
	    	
	    return super.isOceanBlock(data, index, x, y, z, chunkX, chunkZ);
	}
	
	@Override
    protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
    	BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
    	if(MaricultureHandlers.biomeType.getBiomeType(biome) == EnumBiomeType.OCEAN && y < 63) {
    		Block top    = (isExceptionBiome(biome) ? Blocks.grass : biome.topBlock);
            Block filler = (isExceptionBiome(biome) ? Blocks.dirt  : biome.fillerBlock);
            Block block  = data[index];
	
	        if (block == Blocks.stone || block == filler || block == top) {
	            if (y < 10)  {
	                data[index] = Blocks.lava;
	            } else {
	            	data[index] = Blocks.water;
	
	                if (foundTop && data[index - 1] == filler) {
	                    data[index - 1] = top;
	                }
	            }
	        }
    	} else {
    		super.digBlock(data, index, x, y, z, chunkX, chunkZ, foundTop);
    	}
    }
	
	/*
	
	@Override
    protected boolean isOceanBlock(byte[] data, int index, int x, int y, int z, int chunkX, int chunkZ) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
    	if(MaricultureHandlers.biomeType.getBiomeType(biome) == EnumBiomeType.OCEAN && y < 63) {
    		return false;
    	}
    	
    	return super.isOceanBlock(data, index, x, y, z, chunkX, chunkZ);
    }

    //Exception biomes to make sure we generate like vanilla
    private boolean isExceptionBiome(BiomeGenBase biome) {
        if (biome == BiomeGenBase.mushroomIsland) return true;
        if (biome == BiomeGenBase.beach) return true;
        if (biome == BiomeGenBase.desert) return true;
        return false;
    }

    @Override
    protected void digBlock(byte[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
    	BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
    	if(MaricultureHandlers.biomeType.getBiomeType(biome) == EnumBiomeType.OCEAN && y < 63) {
	        int top    = (isExceptionBiome(biome) ? Blocks.grass.blockID : biome.topBlock);
	        int filler = (isExceptionBiome(biome) ? Blocks.dirt.blockID  : biome.fillerBlock);
	        int block  = data[index];
	
	        if (block == Blocks.stone.blockID || block == filler || block == top) {
	            if (y < 10)  {
	                data[index] = (byte)Blocks.lavaMoving.blockID;
	            } else {
	            	data[index] = (byte)Blocks.waterMoving.blockID;
	
	                if (foundTop && data[index - 1] == filler) {
	                    data[index - 1] = (byte)top;
	                }
	            }
	        }
    	} else {
    		super.digBlock(data, index, x, y, z, chunkX, chunkZ, foundTop);
    	}
    } */
}
