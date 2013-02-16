package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CraftingManager
{
	public static boolean boost;
	
    /** The static instance of this class */
    private static final CraftingManager instance = new CraftingManager();

    /** A list of all the recipes added */
    private List recipes = new ArrayList();

    /**
     * Returns the static instance of this class
     */
    public static final CraftingManager getInstance()
    {
        return instance;
    }

    private CraftingManager()
    {
        (new RecipesTools()).addRecipes(this);
        (new RecipesWeapons()).addRecipes(this);
        (new RecipesIngots()).addRecipes(this);
        (new RecipesFood()).addRecipes(this);
        (new RecipesCrafting()).addRecipes(this);
        (new RecipesArmor()).addRecipes(this);
        (new RecipesDyes()).addRecipes(this);
        this.recipes.add(new RecipesArmorDyes());
        this.recipes.add(new RecipesMapCloning());
        this.recipes.add(new RecipesMapExtending());
        this.addRecipe(new ItemStack(Item.paper, 3), new Object[] {"###", '#', Item.reed});
        this.addShapelessRecipe(new ItemStack(Item.book, 1), new Object[] {Item.paper, Item.paper, Item.paper, Item.leather});
        this.addShapelessRecipe(new ItemStack(Item.writableBook, 1), new Object[] {Item.book, new ItemStack(Item.dyePowder, 1, 0), Item.feather});
        this.addRecipe(new ItemStack(Block.fence, 2), new Object[] {"###", "###", '#', Item.stick});
        this.addRecipe(new ItemStack(Block.cobblestoneWall, 6, 0), new Object[] {"###", "###", '#', Block.cobblestone});
        this.addRecipe(new ItemStack(Block.cobblestoneWall, 6, 1), new Object[] {"###", "###", '#', Block.cobblestoneMossy});
        this.addRecipe(new ItemStack(Block.netherFence, 6), new Object[] {"###", "###", '#', Block.netherBrick});
        this.addRecipe(new ItemStack(Block.fenceGate, 1), new Object[] {"#W#", "#W#", '#', Item.stick, 'W', Block.planks});
        this.addRecipe(new ItemStack(Block.jukebox, 1), new Object[] {"###", "#X#", "###", '#', Block.planks, 'X', Item.diamond});
        this.addRecipe(new ItemStack(Block.music, 1), new Object[] {"###", "#X#", "###", '#', Block.planks, 'X', Item.redstone});
        this.addRecipe(new ItemStack(Block.bookShelf, 1), new Object[] {"###", "XXX", "###", '#', Block.planks, 'X', Item.book});
        this.addRecipe(new ItemStack(Block.blockSnow, 1), new Object[] {"##", "##", '#', Item.snowball});
        this.addRecipe(new ItemStack(Block.blockClay, 1), new Object[] {"##", "##", '#', Item.clay});
        this.addRecipe(new ItemStack(Block.brick, 1), new Object[] {"##", "##", '#', Item.brick});
        this.addRecipe(new ItemStack(Block.glowStone, 1), new Object[] {"##", "##", '#', Item.lightStoneDust});
        this.addRecipe(new ItemStack(Block.cloth, 1), new Object[] {"##", "##", '#', Item.silk});
        this.addRecipe(new ItemStack(Block.tnt, 1), new Object[] {"X#X", "#X#", "X#X", 'X', Item.gunpowder, '#', Block.sand});
        this.addRecipe(new ItemStack(Block.stoneSingleSlab, 6, 3), new Object[] {"###", '#', Block.cobblestone});
        this.addRecipe(new ItemStack(Block.stoneSingleSlab, 6, 0), new Object[] {"###", '#', Block.stone});
        this.addRecipe(new ItemStack(Block.stoneSingleSlab, 6, 1), new Object[] {"###", '#', Block.sandStone});
        this.addRecipe(new ItemStack(Block.stoneSingleSlab, 6, 4), new Object[] {"###", '#', Block.brick});
        this.addRecipe(new ItemStack(Block.stoneSingleSlab, 6, 5), new Object[] {"###", '#', Block.stoneBrick});
        this.addRecipe(new ItemStack(Block.woodSingleSlab, 6, 0), new Object[] {"###", '#', new ItemStack(Block.planks, 1, 0)});
        this.addRecipe(new ItemStack(Block.woodSingleSlab, 6, 2), new Object[] {"###", '#', new ItemStack(Block.planks, 1, 2)});
        this.addRecipe(new ItemStack(Block.woodSingleSlab, 6, 1), new Object[] {"###", '#', new ItemStack(Block.planks, 1, 1)});
        this.addRecipe(new ItemStack(Block.woodSingleSlab, 6, 3), new Object[] {"###", '#', new ItemStack(Block.planks, 1, 3)});
        this.addRecipe(new ItemStack(Block.ladder, 3), new Object[] {"# #", "###", "# #", '#', Item.stick});
        this.addRecipe(new ItemStack(Item.doorWood, 1), new Object[] {"##", "##", "##", '#', Block.planks});
        this.addRecipe(new ItemStack(Block.trapdoor, 2), new Object[] {"###", "###", '#', Block.planks});
        this.addRecipe(new ItemStack(Item.doorSteel, 1), new Object[] {"##", "##", "##", '#', Item.ingotIron});
        this.addRecipe(new ItemStack(Item.sign, 3), new Object[] {"###", "###", " X ", '#', Block.planks, 'X', Item.stick});
        this.addRecipe(new ItemStack(Item.cake, 1), new Object[] {"AAA", "BEB", "CCC", 'A', Item.bucketMilk, 'B', Item.sugar, 'C', Item.wheat, 'E', Item.egg});
        this.addRecipe(new ItemStack(Item.sugar, 1), new Object[] {"#", '#', Item.reed});
        this.addRecipe(new ItemStack(Block.planks, 4, 0), new Object[] {"#", '#', new ItemStack(Block.wood, 1, 0)});
        this.addRecipe(new ItemStack(Block.planks, 4, 1), new Object[] {"#", '#', new ItemStack(Block.wood, 1, 1)});
        this.addRecipe(new ItemStack(Block.planks, 4, 2), new Object[] {"#", '#', new ItemStack(Block.wood, 1, 2)});
        this.addRecipe(new ItemStack(Block.planks, 4, 3), new Object[] {"#", '#', new ItemStack(Block.wood, 1, 3)});
        this.addRecipe(new ItemStack(Item.stick, 4), new Object[] {"#", "#", '#', Block.planks});
        this.addRecipe(new ItemStack(Block.torchWood, 4), new Object[] {"X", "#", 'X', Item.coal, '#', Item.stick});
        this.addRecipe(new ItemStack(Block.torchWood, 4), new Object[] {"X", "#", 'X', new ItemStack(Item.coal, 1, 1), '#', Item.stick});
        this.addRecipe(new ItemStack(Item.bowlEmpty, 4), new Object[] {"# #", " # ", '#', Block.planks});
        this.addRecipe(new ItemStack(Item.glassBottle, 3), new Object[] {"# #", " # ", '#', Block.glass});
        this.addRecipe(new ItemStack(Block.rail, 16), new Object[] {"X X", "X#X", "X X", 'X', Item.ingotIron, '#', Item.stick});
        this.addRecipe(new ItemStack(Block.railPowered, 6), new Object[] {"X X", "X#X", "XRX", 'X', Item.ingotGold, 'R', Item.redstone, '#', Item.stick});
        this.addRecipe(new ItemStack(Block.railDetector, 6), new Object[] {"X X", "X#X", "XRX", 'X', Item.ingotIron, 'R', Item.redstone, '#', Block.pressurePlateStone});
        this.addRecipe(new ItemStack(Item.minecartEmpty, 1), new Object[] {"# #", "###", '#', Item.ingotIron});
        this.addRecipe(new ItemStack(Item.cauldron, 1), new Object[] {"# #", "# #", "###", '#', Item.ingotIron});
        this.addRecipe(new ItemStack(Item.brewingStand, 1), new Object[] {" B ", "###", '#', Block.cobblestone, 'B', Item.blazeRod});
        this.addRecipe(new ItemStack(Block.pumpkinLantern, 1), new Object[] {"A", "B", 'A', Block.pumpkin, 'B', Block.torchWood});
        this.addRecipe(new ItemStack(Item.minecartCrate, 1), new Object[] {"A", "B", 'A', Block.chest, 'B', Item.minecartEmpty});
        this.addRecipe(new ItemStack(Item.minecartPowered, 1), new Object[] {"A", "B", 'A', Block.stoneOvenIdle, 'B', Item.minecartEmpty});
        this.addRecipe(new ItemStack(Item.boat, 1), new Object[] {"# #", "###", '#', Block.planks});
        this.addRecipe(new ItemStack(Item.bucketEmpty, 1), new Object[] {"# #", " # ", '#', Item.ingotIron});
        this.addRecipe(new ItemStack(Item.flowerPot, 1), new Object[] {"# #", " # ", '#', Item.brick});
        this.addRecipe(new ItemStack(Item.flintAndSteel, 1), new Object[] {"A ", " B", 'A', Item.ingotIron, 'B', Item.flint});
        this.addRecipe(new ItemStack(Item.bread, 1), new Object[] {"###", '#', Item.wheat});
        this.addRecipe(new ItemStack(Block.stairCompactPlanks, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.planks, 1, 0)});
        this.addRecipe(new ItemStack(Block.stairsWoodBirch, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.planks, 1, 2)});
        this.addRecipe(new ItemStack(Block.stairsWoodSpruce, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.planks, 1, 1)});
        this.addRecipe(new ItemStack(Block.stairsWoodJungle, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.planks, 1, 3)});
        this.addRecipe(new ItemStack(Item.fishingRod, 1), new Object[] {"  #", " #X", "# X", '#', Item.stick, 'X', Item.silk});
        this.addRecipe(new ItemStack(Item.carrotOnAStick, 1), new Object[] {"# ", " X", '#', Item.fishingRod, 'X', Item.carrot});
        this.addRecipe(new ItemStack(Block.stairCompactCobblestone, 4), new Object[] {"#  ", "## ", "###", '#', Block.cobblestone});
        this.addRecipe(new ItemStack(Block.stairsBrick, 4), new Object[] {"#  ", "## ", "###", '#', Block.brick});
        this.addRecipe(new ItemStack(Block.stairsStoneBrickSmooth, 4), new Object[] {"#  ", "## ", "###", '#', Block.stoneBrick});
        this.addRecipe(new ItemStack(Block.stairsNetherBrick, 4), new Object[] {"#  ", "## ", "###", '#', Block.netherBrick});
        this.addRecipe(new ItemStack(Block.stairsSandStone, 4), new Object[] {"#  ", "## ", "###", '#', Block.sandStone});
        this.addRecipe(new ItemStack(Item.painting, 1), new Object[] {"###", "#X#", "###", '#', Item.stick, 'X', Block.cloth});
        this.addRecipe(new ItemStack(Item.itemFrame, 1), new Object[] {"###", "#X#", "###", '#', Item.stick, 'X', Item.leather});
        this.addRecipe(new ItemStack(Item.appleGold, 1, 0), new Object[] {"###", "#X#", "###", '#', Item.goldNugget, 'X', Item.appleRed});
        this.addRecipe(new ItemStack(Item.appleGold, 1, 1), new Object[] {"###", "#X#", "###", '#', Block.blockGold, 'X', Item.appleRed});
        this.addRecipe(new ItemStack(Item.goldenCarrot, 1, 0), new Object[] {"###", "#X#", "###", '#', Item.goldNugget, 'X', Item.carrot});
        this.addRecipe(new ItemStack(Block.lever, 1), new Object[] {"X", "#", '#', Block.cobblestone, 'X', Item.stick});
        this.addRecipe(new ItemStack(Block.tripWireSource, 2), new Object[] {"I", "S", "#", '#', Block.planks, 'S', Item.stick, 'I', Item.ingotIron});
        this.addRecipe(new ItemStack(Block.torchRedstoneActive, 1), new Object[] {"X", "#", '#', Item.stick, 'X', Item.redstone});
        this.addRecipe(new ItemStack(Item.redstoneRepeater, 1), new Object[] {"#X#", "III", '#', Block.torchRedstoneActive, 'X', Item.redstone, 'I', Block.stone});
        this.addRecipe(new ItemStack(Item.pocketSundial, 1), new Object[] {" # ", "#X#", " # ", '#', Item.ingotGold, 'X', Item.redstone});
        this.addRecipe(new ItemStack(Item.compass, 1), new Object[] {" # ", "#X#", " # ", '#', Item.ingotIron, 'X', Item.redstone});
        this.addRecipe(new ItemStack(Item.emptyMap, 1), new Object[] {"###", "#X#", "###", '#', Item.paper, 'X', Item.compass});
        this.addRecipe(new ItemStack(Block.stoneButton, 1), new Object[] {"#", '#', Block.stone});
        this.addRecipe(new ItemStack(Block.woodenButton, 1), new Object[] {"#", '#', Block.planks});
        this.addRecipe(new ItemStack(Block.pressurePlateStone, 1), new Object[] {"##", '#', Block.stone});
        this.addRecipe(new ItemStack(Block.pressurePlatePlanks, 1), new Object[] {"##", '#', Block.planks});
        this.addRecipe(new ItemStack(Block.dispenser, 1), new Object[] {"###", "#X#", "#R#", '#', Block.cobblestone, 'X', Item.bow, 'R', Item.redstone});
        this.addRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"TTT", "#X#", "#R#", '#', Block.cobblestone, 'X', Item.ingotIron, 'R', Item.redstone, 'T', Block.planks});
        this.addRecipe(new ItemStack(Block.pistonStickyBase, 1), new Object[] {"S", "P", 'S', Item.slimeBall, 'P', Block.pistonBase});
        this.addRecipe(new ItemStack(Item.bed, 1), new Object[] {"###", "XXX", '#', Block.cloth, 'X', Block.planks});
        this.addRecipe(new ItemStack(Block.enchantmentTable, 1), new Object[] {" B ", "D#D", "###", '#', Block.obsidian, 'B', Item.book, 'D', Item.diamond});
        this.addRecipe(new ItemStack(Block.anvil, 1), new Object[] {"III", " i ", "iii", 'I', Block.blockSteel, 'i', Item.ingotIron});
        this.addShapelessRecipe(new ItemStack(Item.eyeOfEnder, 1), new Object[] {Item.enderPearl, Item.blazePowder});
        this.addShapelessRecipe(new ItemStack(Item.fireballCharge, 3), new Object[] {Item.gunpowder, Item.blazePowder, Item.coal});
        this.addShapelessRecipe(new ItemStack(Item.fireballCharge, 3), new Object[] {Item.gunpowder, Item.blazePowder, new ItemStack(Item.coal, 1, 1)});
        
        //start craft custom
        int[] dye = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};//ajout
        
        /*Speeder*/
//        this.addRecipe(new ItemStack(Block.Speeder, 1), new Object[] {"XXX", "XZX", "XXX", 'Z', Item.diamond, 'X', new ItemStack(Block.stone, 1)});//ajout
        
        /*Jumper*/
//        this.addRecipe(new ItemStack(Block.Jumper, 1), new Object[] {"XXX", "XZX", "XXX", 'Z', Item.emerald, 'X', new ItemStack(Block.stone, 1)});//ajout
        
        /*coloredGlass*/
        for (int x=0;x<16;x++)
        	this.addRecipe(new ItemStack(Block.Color_Glass, 1, x), new Object[] {"X", "#", '#', Block.glass, 'X', new ItemStack(Item.dyePowder, 1, dye[x])});//ajout
        for (int x=0;x<16;x++)
        	this.addRecipe(new ItemStack(Block.Color_Glass, 1, x), new Object[] {"X", "#", '#', Block.Color_Glass, 'X', new ItemStack(Item.dyePowder, 1, dye[x])});//ajout
        
        /*Freezer*/
//        this.addRecipe(new ItemStack(Block.freezera), new Object[] {"###", "#X#", "###", '#', new ItemStack(Item.ingotIron), 'X', new ItemStack(Item.redstone)});//ajout
        
        /*Moquette*/
        for (int x=0;x<16;x++)
        	this.addRecipe(new ItemStack(Block.moquette, 1, x), new Object[] {"X", "#", '#', Block.moquette, 'X', new ItemStack(Item.dyePowder, 1, dye[x])});//ajout
        for (int x=0;x<16;x++)
        	this.addRecipe(new ItemStack(Block.moquette, 1, x), new Object[] {"XXX", 'X', new ItemStack(Block.cloth, 1, x)});//ajout
        
        /*ColoredGlowStone*/
        for (int x=0;x<16;x++)
        	this.addRecipe(new ItemStack(Block.coloredGlowStone, 1, x), new Object[] {"X", "#", '#', Block.glowStone, 'X', new ItemStack(Item.dyePowder, 1, dye[x])});//ajout
        for (int x=0;x<16;x++)
        	this.addRecipe(new ItemStack(Block.coloredGlowStone, 1, x), new Object[] {"X", "#", '#', Block.coloredGlowStone, 'X', new ItemStack(Item.dyePowder, 1, dye[x])});//ajout
       
        /*Fut*/
//        this.addRecipe(new ItemStack(Block.fut, 1), new Object[] {"###", "XXX", "###", '#', new ItemStack(Item.ingotIron), 'X', new ItemStack(Item.stick)});//ajout
       
        /*VerreVide*/
        this.addRecipe(new ItemStack(Item.verreVide, 5), new Object[] {"#X#", "###", '#', new ItemStack(Block.glass)});//ajout
        
        /*IceCube*/
        this.addRecipe(new ItemStack(Item.iceCube, 4), new Object[] {"#", '#', new ItemStack(Block.ice, 2)});//ajout
        
        /*Ice*/
        this.addRecipe(new ItemStack(Block.ice), new Object[] {"##", "##", '#', new ItemStack(Item.iceCube)});//ajout
        
        /*Chaise*/
//        this.addRecipe(new ItemStack(Block.chaise, 1), new Object[] {"OO#", "XXX", "#O#", '#', new ItemStack(Block.fence, 1), 'X', new ItemStack(Block.pressurePlatePlanks, 1)});//ajout
//        this.addRecipe(new ItemStack(Block.chaise, 1), new Object[] {"#OO", "XXX", "#O#", '#', new ItemStack(Block.fence, 1), 'X', new ItemStack(Block.pressurePlatePlanks, 1)});//ajout
        
        /*ChaineModern*/
//        this.addRecipe(new ItemStack(Block.chaiseModerne, 1), new Object[] {"OO#", "XXX", "#O#", '#', new ItemStack(Block.cloth, 1, 15), 'X', new ItemStack(Block.moquette, 1, 7)});//ajout
//        this.addRecipe(new ItemStack(Block.chaiseModerne, 1), new Object[] {"#OO", "XXX", "#O#", '#', new ItemStack(Block.cloth, 1, 15), 'X', new ItemStack(Block.moquette, 1, 7)});//ajout
        
        /*Poubelle*/
//        this.addRecipe(new ItemStack(Block.poubelle, 1), new Object[] {"###", "XoX", "XXX", '#', new ItemStack(Item.ingotIron), 'X', new ItemStack(Item.paper)});//ajout
        
        /*EtageresBois*/
//        this.addRecipe(new ItemStack(Block.etagereBois, 1), new Object[] {"XXo", "#oo", 'X', new ItemStack(Block.pressurePlatePlanks), '#', new ItemStack(Item.stick)});//ajout
//        this.addRecipe(new ItemStack(Block.etagereBois, 1), new Object[] {"oXX", "o#o", 'X', new ItemStack(Block.pressurePlatePlanks), '#', new ItemStack(Item.stick)});//ajout
//        this.addRecipe(new ItemStack(Block.etagereBois, 1), new Object[] {"XXo", "o#o", 'X', new ItemStack(Block.pressurePlatePlanks), '#', new ItemStack(Item.stick)});//ajout
//        this.addRecipe(new ItemStack(Block.etagereBois, 1), new Object[] {"oXX", "oo#", 'X', new ItemStack(Block.pressurePlatePlanks), '#', new ItemStack(Item.stick)});//ajout
        
        /*Glaces*/
        this.addRecipe(new ItemStack(Item.glaceChocolat, 1), new Object[] {"X", "#", "G", '#', new ItemStack(Item.iceCube), 'G', new ItemStack(Item.stick), 'X', new ItemStack(Item.dyePowder, 1, 3)});//ajout      
        this.addRecipe(new ItemStack(Item.glaceCherry, 1), new Object[] {"X", "#", "G", '#', new ItemStack(Item.iceCube), 'G', new ItemStack(Item.stick), 'X', new ItemStack(Item.cherry)});//ajout        
        this.addRecipe(new ItemStack(Item.glaceApple, 1), new Object[] {"X", "#", "G", '#', new ItemStack(Item.iceCube), 'G', new ItemStack(Item.stick), 'X', new ItemStack(Item.appleRed)});//ajout
        
//        /*Armures lezardSkin*/
//        this.addRecipe(new ItemStack(Item.helmetLezard, 1), new Object[] {"###", "#X#", '#', new ItemStack(Item.lezardSkin)});//ajout
//        this.addRecipe(new ItemStack(Item.plateLezard, 1), new Object[] {"#X#", "###", "###", '#', new ItemStack(Item.lezardSkin)});//ajout
//        this.addRecipe(new ItemStack(Item.legsLezard, 1), new Object[] {"###", "#X#", "#X#", '#', new ItemStack(Item.lezardSkin)});//ajout
//        this.addRecipe(new ItemStack(Item.bootsLezard, 1), new Object[] {"#X#", "#X#", '#', new ItemStack(Item.lezardSkin)});//ajout
//        
//        /*Armures rubis*/
//        this.addRecipe(new ItemStack(Item.helmetRubis, 1), new Object[] {"###", "#X#", '#', new ItemStack(Item.rubis, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.plateRubis, 1), new Object[] {"#X#", "###", "###", '#', new ItemStack(Item.rubis, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.legsRubis, 1), new Object[] {"###", "#X#", "#X#", '#', new ItemStack(Item.rubis, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.bootsRubis, 1), new Object[] {"#X#", "#X#", '#', new ItemStack(Item.rubis, 1)});//ajout
//        
//        /*Armes rubis*/
//        this.addRecipe(new ItemStack(Item.pickaxeRubis, 1), new Object[] {"###", "OXO", "OXO", '#', new ItemStack(Item.rubis, 1), 'X', new ItemStack(Item.stick, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.axeRubis, 1), new Object[] {"##O", "#XO", "OXO", '#', new ItemStack(Item.rubis, 1), 'X', new ItemStack(Item.stick, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.shovelRubis, 1), new Object[] {"O#O", "OXO", "OXO", '#', new ItemStack(Item.rubis, 1), 'X', new ItemStack(Item.stick, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.hoeRubis, 1), new Object[] {"##O", "OXO", "OXO", '#', new ItemStack(Item.rubis, 1), 'X', new ItemStack(Item.stick, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.swordRubis, 1), new Object[] {"O#O", "O#O", "OXO", '#', new ItemStack(Item.rubis, 1), 'X', new ItemStack(Item.stick, 1)});//ajout
//        
//        /*Armures Emeraude*/
//        this.addRecipe(new ItemStack(Item.helmetEmeraude, 1), new Object[] {"###", "#X#", '#', new ItemStack(Item.emerald, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.plateEmeraude, 1), new Object[] {"#X#", "###", "###", '#', new ItemStack(Item.emerald, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.legsEmeraude, 1), new Object[] {"###", "#X#", "#X#", '#', new ItemStack(Item.emerald, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.bootsEmeraude, 1), new Object[] {"#X#", "#X#", '#', new ItemStack(Item.emerald, 1)});//ajout
//        
//        /*Armes Emeraude*/
//        this.addRecipe(new ItemStack(Item.pickaxeEmeraude, 1), new Object[] {"###", "OXO", "OXO", '#', new ItemStack(Item.emerald, 1), 'X', new ItemStack(Item.stick, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.axeEmeraude, 1), new Object[] {"##O", "#XO", "OXO", '#', new ItemStack(Item.emerald, 1), 'X', new ItemStack(Item.stick, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.shovelEmeraude, 1), new Object[] {"O#O", "OXO", "OXO", '#', new ItemStack(Item.emerald, 1), 'X', new ItemStack(Item.stick, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.hoeEmeraude, 1), new Object[] {"##O", "OXO", "OXO", '#', new ItemStack(Item.emerald, 1), 'X', new ItemStack(Item.stick, 1)});//ajout
//        this.addRecipe(new ItemStack(Item.swordEmeraude, 1), new Object[] {"O#O", "O#O", "OXO", '#', new ItemStack(Item.emerald, 1), 'X', new ItemStack(Item.stick, 1)});//ajout      
        
        /*ColoredWoolStairs*/
        Block ordres[] = {Block.Escalier_Wool_BLANC, Block.Escalier_Wool_ORANGE, Block.Escalier_Wool_MAGENTA, Block.Escalier_Wool_BLEUCIEL, Block.Escalier_Wool_JAUNE, Block.Escalier_Wool_VERTCLAIR, Block.Escalier_Wool_ROSE, Block.Escalier_Wool_GRIS, Block.Escalier_Wool_GRISCLAIR, Block.Escalier_Wool_AQUA, Block.Escalier_Wool_VIOLET, Block.Escalier_Wool_BLEU, Block.Escalier_Wool_MARRON, Block.Escalier_Wool_VERT, Block.Escalier_Wool_ROUGE, Block.Escalier_Wool_BLACK};
        for(int x=0; x<16;x++){
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_BLANC, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 15)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_ORANGE, 1), new Object[] {"X", "#",  '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 14)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_MAGENTA, 1), new Object[] {"X", "#",  '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 13)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_BLEUCIEL, 1), new Object[] {"X", "#",  '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 12)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_JAUNE, 1), new Object[] {"X", "#",  '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 11)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_VERTCLAIR, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 10)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_ROSE, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 9)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_GRIS, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 7)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_GRISCLAIR, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 8)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_AQUA, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 6)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_VIOLET, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 5)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_BLEU, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 4)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_MARRON, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 3)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_VERT, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 2)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_ROUGE, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 1)});
        	this.addRecipe(new ItemStack(Block.Escalier_Wool_BLACK, 1), new Object[] {"X", "#", '#', ordres[x], 'X', new ItemStack(Item.dyePowder, 1, 0)});
        }
        this.addRecipe(new ItemStack(Block.Escalier_Wool_BLANC, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 0)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_ORANGE, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 1)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_MAGENTA, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 13)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_BLEUCIEL, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 3)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_JAUNE, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 4)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_VERTCLAIR, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 5)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_ROSE, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 6)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_GRIS, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 7)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_GRISCLAIR, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 8)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_AQUA, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 9)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_VIOLET, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 10)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_BLEU, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 11)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_MARRON, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 12)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_VERT, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 2)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_ROUGE, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 14)});
        this.addRecipe(new ItemStack(Block.Escalier_Wool_BLACK, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.cloth, 0, 15)});
        this.addRecipe(new ItemStack(Block.cloth, 4, 0), new Object[] {"#", '#', Block.Escalier_Wool_BLANC});
        this.addRecipe(new ItemStack(Block.cloth, 4, 1), new Object[] {"#", '#', Block.Escalier_Wool_ORANGE});
        this.addRecipe(new ItemStack(Block.cloth, 4, 2), new Object[] {"#", '#', Block.Escalier_Wool_MAGENTA});
        this.addRecipe(new ItemStack(Block.cloth, 4, 3), new Object[] {"#", '#', Block.Escalier_Wool_BLEUCIEL});
        this.addRecipe(new ItemStack(Block.cloth, 4, 4), new Object[] {"#", '#', Block.Escalier_Wool_JAUNE});
        this.addRecipe(new ItemStack(Block.cloth, 4, 5), new Object[] {"#", '#', Block.Escalier_Wool_VERTCLAIR});
        this.addRecipe(new ItemStack(Block.cloth, 4, 6), new Object[] {"#", '#', Block.Escalier_Wool_ROSE});
        this.addRecipe(new ItemStack(Block.cloth, 4, 7), new Object[] {"#", '#', Block.Escalier_Wool_GRIS});
        this.addRecipe(new ItemStack(Block.cloth, 4, 8), new Object[] {"#", '#', Block.Escalier_Wool_GRISCLAIR});
        this.addRecipe(new ItemStack(Block.cloth, 4, 9), new Object[] {"#", '#', Block.Escalier_Wool_AQUA});
        this.addRecipe(new ItemStack(Block.cloth, 4, 10), new Object[] {"#", '#', Block.Escalier_Wool_VIOLET});
        this.addRecipe(new ItemStack(Block.cloth, 4, 11), new Object[] {"#", '#', Block.Escalier_Wool_BLEU});
        this.addRecipe(new ItemStack(Block.cloth, 4, 12), new Object[] {"#", '#', Block.Escalier_Wool_MARRON});
        this.addRecipe(new ItemStack(Block.cloth, 4, 13), new Object[] {"#", '#', Block.Escalier_Wool_VERT});
        this.addRecipe(new ItemStack(Block.cloth, 4, 14), new Object[] {"#", '#', Block.Escalier_Wool_ROUGE});;
        this.addRecipe(new ItemStack(Block.cloth, 4, 15), new Object[] {"#", '#', Block.Escalier_Wool_BLACK});
        //end craft custom

        Collections.sort(this.recipes, new RecipeSorter(this));
        System.out.println(this.recipes.size() + " recipes");
    }

    /**
     * Adds a recipe. See spreadsheet on first page for details.
     */
    void addRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj)
    {
        String var3 = "";
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;

        if (par2ArrayOfObj[var4] instanceof String[])
        {
            String[] var7 = (String[])((String[])par2ArrayOfObj[var4++]);

            for (int var8 = 0; var8 < var7.length; ++var8)
            {
                String var9 = var7[var8];
                ++var6;
                var5 = var9.length();
                var3 = var3 + var9;
            }
        }
        else
        {
            while (par2ArrayOfObj[var4] instanceof String)
            {
                String var11 = (String)par2ArrayOfObj[var4++];
                ++var6;
                var5 = var11.length();
                var3 = var3 + var11;
            }
        }

        HashMap var12;

        for (var12 = new HashMap(); var4 < par2ArrayOfObj.length; var4 += 2)
        {
            Character var13 = (Character)par2ArrayOfObj[var4];
            ItemStack var14 = null;

            if (par2ArrayOfObj[var4 + 1] instanceof Item)
            {
                var14 = new ItemStack((Item)par2ArrayOfObj[var4 + 1]);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof Block)
            {
                var14 = new ItemStack((Block)par2ArrayOfObj[var4 + 1], 1, -1);
            }
            else if (par2ArrayOfObj[var4 + 1] instanceof ItemStack)
            {
                var14 = (ItemStack)par2ArrayOfObj[var4 + 1];
            }

            var12.put(var13, var14);
        }

        ItemStack[] var15 = new ItemStack[var5 * var6];

        for (int var16 = 0; var16 < var5 * var6; ++var16)
        {
            char var10 = var3.charAt(var16);

            if (var12.containsKey(Character.valueOf(var10)))
            {
                var15[var16] = ((ItemStack)var12.get(Character.valueOf(var10))).copy();
            }
            else
            {
                var15[var16] = null;
            }
        }

        this.recipes.add(new ShapedRecipes(var5, var6, var15, par1ItemStack));
    }

    void addShapelessRecipe(ItemStack par1ItemStack, Object ... par2ArrayOfObj)
    {
        ArrayList var3 = new ArrayList();
        Object[] var4 = par2ArrayOfObj;
        int var5 = par2ArrayOfObj.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            Object var7 = var4[var6];

            if (var7 instanceof ItemStack)
            {
                var3.add(((ItemStack)var7).copy());
            }
            else if (var7 instanceof Item)
            {
                var3.add(new ItemStack((Item)var7));
            }
            else
            {
                if (!(var7 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                var3.add(new ItemStack((Block)var7));
            }
        }

        this.recipes.add(new ShapelessRecipes(par1ItemStack, var3));
    }

    public ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World)
    {
        int var3 = 0;
        ItemStack var4 = null;
        ItemStack var5 = null;
        int var6;

        for (var6 = 0; var6 < par1InventoryCrafting.getSizeInventory(); ++var6)
        {
            ItemStack var7 = par1InventoryCrafting.getStackInSlot(var6);

            if (var7 != null)
            {
                if (var3 == 0)
                {
                    var4 = var7;
                }

                if (var3 == 1)
                {
                    var5 = var7;
                }

                ++var3;
            }
        }

        if (var3 == 2 && var4.itemID == var5.itemID && var4.stackSize == 1 && var5.stackSize == 1 && Item.itemsList[var4.itemID].isDamageable())
        {
            Item var11 = Item.itemsList[var4.itemID];
            int var13 = var11.getMaxDamage() - var4.getItemDamageForDisplay();
            int var8 = var11.getMaxDamage() - var5.getItemDamageForDisplay();
            int var9 = var13 + var8 + var11.getMaxDamage() * 5 / 100;
            int var10 = var11.getMaxDamage() - var9;

            if (var10 < 0)
            {
                var10 = 0;
            }

            return new ItemStack(var4.itemID, 1, var10);
        }
        else
        {
            for (var6 = 0; var6 < this.recipes.size(); ++var6)
            {
                IRecipe var12 = (IRecipe)this.recipes.get(var6);

                if (var12.matches(par1InventoryCrafting, par2World))
                {
                    return var12.getCraftingResult(par1InventoryCrafting);
                }
            }

            return null;
        }
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipeList()
    {
        return this.recipes;
    }
}
