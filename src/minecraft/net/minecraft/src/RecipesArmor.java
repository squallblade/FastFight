package net.minecraft.src;

public class RecipesArmor
{
    private String[][] recipePatterns = new String[][] {{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
    private Object[][] recipeItems;

    public RecipesArmor()
    {
        this.recipeItems = new Object[][] {{Item.leather, Block.fire, Item.ingotIron, Item.diamond, Item.ingotGold, Item.Rubis, Item.emerald}, {Item.helmetLeather, Item.helmetChain, Item.helmetSteel, Item.helmetDiamond, Item.helmetGold, Item.helmetRubis, Item.helmetEmeraude}, {Item.plateLeather, Item.plateChain, Item.plateSteel, Item.plateDiamond, Item.plateGold, Item.plateRubis, Item.plateEmeraude}, {Item.legsLeather, Item.legsChain, Item.legsSteel, Item.legsDiamond, Item.legsGold, Item.legsRubis, Item.legsEmeraude}, {Item.bootsLeather, Item.bootsChain, Item.bootsSteel, Item.bootsDiamond, Item.bootsGold, Item.bootsRubis, Item.bootsEmeraude}};
    }

    /**
     * Adds the armor recipes to the CraftingManager.
     */
    public void addRecipes(CraftingManager par1CraftingManager)
    {
        for (int var2 = 0; var2 < this.recipeItems[0].length; ++var2)
        {
            Object var3 = this.recipeItems[0][var2];

            for (int var4 = 0; var4 < this.recipeItems.length - 1; ++var4)
            {
                Item var5 = (Item)this.recipeItems[var4 + 1][var2];
                par1CraftingManager.func_92103_a(new ItemStack(var5), new Object[] {this.recipePatterns[var4], 'X', var3});
            }
        }
    }
}
