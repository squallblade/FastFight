package net.minecraft.src;

import java.util.List;
import java.util.Random;

import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockVitreColor extends Block
{
    public BlockVitreColor()
    {
        super(210, 441, Material.glassColor);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setTextureFile("/terrain1.png");
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public int getRenderBlockPass()
    {
        return 1;
    }


    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par2 == 0)
        {
            return this.blockIndexInTexture;
        }
        else
        {
            par2 = ~(par2 & 15);
            return 369 + ((par2 & 8) >> 3) + (par2 & 7) * 16;
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int par1)
    {
        return par1;
    }

    /**
     * Takes a dye damage value and returns the block damage value to match
     */
    public static int getBlockFromDye(int par0)
    {
        return ~par0 & 15;
    }

    /**
     * Takes a block damage value and returns the dye damage value to match
     */
    public static int getDyeFromBlock(int par0)
    {
        return ~par0 & 15;
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 16; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
    protected boolean canSilkHarvest()
    {
        return true;
    }
        
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
    
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    { 
    	if(par5EntityPlayer.getCurrentEquippedItem() == null)
    		return true;

    	if(par5EntityPlayer.getCurrentEquippedItem().getItem() == new ItemStack(Item.dyePowder).getItem()){
    		int[] list = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
    		for(int x = 0;x<16;x++){
    			if(par5EntityPlayer.getCurrentEquippedItem().getItemDamage() == x){
    				par1World.setBlockAndMetadata(par2, par3, par4, Block.Color_Glass.blockID, list[x]);
    				int bla = par5EntityPlayer.getCurrentEquippedItem().stackSize;
    				--bla;
    				if(bla < 0)
    					bla = 0;
    				par5EntityPlayer.getCurrentEquippedItem().stackSize = bla;
    				return true;
    			}
    		}
    	}
    		

    	return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }
    
}
