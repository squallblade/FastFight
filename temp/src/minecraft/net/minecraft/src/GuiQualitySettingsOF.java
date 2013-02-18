package net.minecraft.src;

import net.minecraft.src.EnumOptions;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlider;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;

public class GuiQualitySettingsOF extends GuiScreen {

   private GuiScreen prevScreen;
   protected String title = "Quality Settings";
   private GameSettings settings;
   private static EnumOptions[] enumOptions = new EnumOptions[]{EnumOptions.MIPMAP_LEVEL, EnumOptions.MIPMAP_TYPE, EnumOptions.AF_LEVEL, EnumOptions.AA_LEVEL, EnumOptions.CLEAR_WATER, EnumOptions.RANDOM_MOBS, EnumOptions.BETTER_GRASS, EnumOptions.BETTER_SNOW, EnumOptions.CUSTOM_FONTS, EnumOptions.CUSTOM_COLORS, EnumOptions.SWAMP_COLORS, EnumOptions.SMOOTH_BIOMES, EnumOptions.CONNECTED_TEXTURES, EnumOptions.NATURAL_TEXTURES};
   private int lastMouseX = 0;
   private int lastMouseY = 0;
   private long mouseStillTime = 0L;


   public GuiQualitySettingsOF(GuiScreen var1, GameSettings var2) {
      this.prevScreen = var1;
      this.settings = var2;
   }

   public void func_73866_w_() {
      StringTranslate var1 = StringTranslate.func_74808_a();
      int var2 = 0;
      EnumOptions[] var3 = enumOptions;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnumOptions var6 = var3[var5];
         int var7 = this.field_73880_f / 2 - 155 + var2 % 2 * 160;
         int var8 = this.field_73881_g / 6 + 21 * (var2 / 2) - 10;
         if(!var6.func_74380_a()) {
            this.field_73887_h.add(new GuiSmallButton(var6.func_74381_c(), var7, var8, var6, this.settings.func_74297_c(var6)));
         } else {
            this.field_73887_h.add(new GuiSlider(var6.func_74381_c(), var7, var8, var6, this.settings.func_74297_c(var6), this.settings.func_74296_a(var6)));
         }

         ++var2;
      }

      this.field_73887_h.add(new GuiButton(200, this.field_73880_f / 2 - 100, this.field_73881_g / 6 + 168 + 11, var1.func_74805_b("gui.done")));
   }

   protected void func_73875_a(GuiButton var1) {
      if(var1.field_73742_g) {
         if(var1.field_73741_f < 100 && var1 instanceof GuiSmallButton) {
            this.settings.func_74306_a(((GuiSmallButton)var1).func_73753_a(), 1);
            var1.field_73744_e = this.settings.func_74297_c(EnumOptions.func_74379_a(var1.field_73741_f));
         }

         if(var1.field_73741_f == 200) {
            this.field_73882_e.field_71474_y.func_74303_b();
            this.field_73882_e.func_71373_a(this.prevScreen);
         }

         if(var1.field_73741_f != EnumOptions.CLOUD_HEIGHT.ordinal()) {
            ScaledResolution var2 = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
            int var3 = var2.func_78326_a();
            int var4 = var2.func_78328_b();
            this.func_73872_a(this.field_73882_e, var3, var4);
         }

      }
   }

   public void func_73863_a(int var1, int var2, float var3) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, this.title, this.field_73880_f / 2, 20, 16777215);
      super.func_73863_a(var1, var2, var3);
      if(Math.abs(var1 - this.lastMouseX) <= 5 && Math.abs(var2 - this.lastMouseY) <= 5) {
         short var4 = 700;
         if(System.currentTimeMillis() >= this.mouseStillTime + (long)var4) {
            int var5 = this.field_73880_f / 2 - 150;
            int var6 = this.field_73881_g / 6 - 5;
            if(var2 <= var6 + 98) {
               var6 += 105;
            }

            int var7 = var5 + 150 + 150;
            int var8 = var6 + 84 + 10;
            GuiButton var9 = this.getSelectedButton(var1, var2);
            if(var9 != null) {
               String var10 = this.getButtonName(var9.field_73744_e);
               String[] var11 = this.getTooltipLines(var10);
               if(var11 == null) {
                  return;
               }

               this.func_73733_a(var5, var6, var7, var8, -536870912, -536870912);

               for(int var12 = 0; var12 < var11.length; ++var12) {
                  String var13 = var11[var12];
                  int var14 = 14540253;
                  if(var13.endsWith("!")) {
                     var14 = 16719904;
                  }

                  this.field_73886_k.func_78261_a(var13, var5 + 5, var6 + 5 + var12 * 11, var14);
               }
            }

         }
      } else {
         this.lastMouseX = var1;
         this.lastMouseY = var2;
         this.mouseStillTime = System.currentTimeMillis();
      }
   }

   private String[] getTooltipLines(String var1) {
      return var1.equals("Mipmap Level")?new String[]{"Visual effect which makes distant objects look better", "by smoothing the texture details", "  OFF - no smoothing", "  1 - minimum smoothing", "  4 - maximum smoothing", "This option usually does not affect the performance."}:(var1.equals("Mipmap Type")?new String[]{"Visual effect which makes distant objects look better", "by smoothing the texture details", "  Nearest - rough smoothing", "  Linear - fine smoothing", "This option usually does not affect the performance."}:(var1.equals("Anisotropic Filtering")?new String[]{"Anisotropic Filtering", " OFF - (default) standard texture detail (faster)", " 2-16 - finer details in mipmapped textures (slower)", "The Anisotropic Filtering restores details in mipmapped", "textures. Higher values may decrease the FPS."}:(var1.equals("Antialiasing")?new String[]{"Antialiasing", " OFF - (default) no antialiasing (faster)", " 2-16 - antialiased lines and edges (slower)", "The Antialiasing smooths jagged lines and ", "sharp color transitions.", "Higher values may substantially decrease the FPS.", "Not all levels are supported by all graphics cards.", "Effective after a RESTART!"}:(var1.equals("Clear Water")?new String[]{"Clear Water", "  ON - clear, transparent water", "  OFF - default water"}:(var1.equals("Better Grass")?new String[]{"Better Grass", "  OFF - default side grass texture, fastest", "  Fast - full side grass texture, slower", "  Fancy - dynamic side grass texture, slowest"}:(var1.equals("Better Snow")?new String[]{"Better Snow", "  OFF - default snow, faster", "  ON - better snow, slower", "Shows snow under transparent blocks (fence, tall grass)", "when bordering with snow blocks"}:(var1.equals("Random Mobs")?new String[]{"Random Mobs", "  OFF - no random mobs, faster", "  ON - random mobs, slower", "Random mobs uses random textures for the game creatures.", "It needs a texture pack which has multiple mob textures."}:(var1.equals("Swamp Colors")?new String[]{"Swamp Colors", "  ON - use swamp colors (default), slower", "  OFF - do not use swamp colors, faster", "The swamp colors affect grass, leaves, vines and water."}:(var1.equals("Smooth Biomes")?new String[]{"Smooth Biomes", "  ON - smoothing of biome borders (default), slower", "  OFF - no smoothing of biome borders, faster", "The smoothing of biome borders is done by sampling and", "averaging the color of all surrounding blocks.", "Affected are grass, leaves, vines and water."}:(var1.equals("Custom Fonts")?new String[]{"Custom Fonts", "  ON - uses custom fonts (default), slower", "  OFF - uses default font, faster", "The custom fonts are supplied by the current", "texture pack"}:(var1.equals("Custom Colors")?new String[]{"Custom Colors", "  ON - uses custom colors (default), slower", "  OFF - uses default colors, faster", "The custom colors are supplied by the current", "texture pack"}:(var1.equals("Show Capes")?new String[]{"Show Capes", "  ON - show player capes (default)", "  OFF - do not show player capes"}:(var1.equals("Connected Textures")?new String[]{"Connected Textures", "  OFF - no connected textures (default)", "  Fast - fast connected textures", "  Fancy - fancy connected textures", "Connected textures joins the textures of glass,", "sandstone and bookshelves when placed next to", "each other. The connected textures are supplied", "by the current texture pack."}:(var1.equals("Far View")?new String[]{"Far View", " OFF - (default) standard view distance", " ON - 3x view distance", "Far View is very resource demanding!", "3x view distance => 9x chunks to be loaded => FPS / 9", "Standard view distances: 32, 64, 128, 256", "Far view distances: 96, 192, 384, 512"}:(var1.equals("Natural Textures")?new String[]{"Natural Textures", "  OFF - no natural textures (default)", "  ON - use natural textures", "Natural textures remove the gridlike pattern", "created by repeating blocks of the same type.", "It uses rotated and flipped variants of the base", "block texture. The configuration for the natural", "textures is supplied by the current texture pack"}:null)))))))))))))));
   }

   private String getButtonName(String var1) {
      int var2 = var1.indexOf(58);
      return var2 < 0?var1:var1.substring(0, var2);
   }

   private GuiButton getSelectedButton(int var1, int var2) {
      for(int var3 = 0; var3 < this.field_73887_h.size(); ++var3) {
         GuiButton var4 = (GuiButton)this.field_73887_h.get(var3);
         boolean var5 = var1 >= var4.field_73746_c && var2 >= var4.field_73743_d && var1 < var4.field_73746_c + var4.field_73747_a && var2 < var4.field_73743_d + var4.field_73745_b;
         if(var5) {
            return var4;
         }
      }

      return null;
   }

}
