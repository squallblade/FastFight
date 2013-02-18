package net.minecraft.src;

import net.minecraft.src.EnumOptions;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiAnimationSettingsOF;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiDetailSettingsOF;
import net.minecraft.src.GuiOtherSettingsOF;
import net.minecraft.src.GuiPerformanceSettingsOF;
import net.minecraft.src.GuiQualitySettingsOF;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlider;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.GuiTexturePacks;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;

public class GuiVideoSettings extends GuiScreen {

   private GuiScreen field_74105_b;
   protected String field_74107_a = "Video Settings";
   private GameSettings field_74106_c;
   private boolean field_74104_d = false;
   private static EnumOptions[] field_74108_m = new EnumOptions[]{EnumOptions.GRAPHICS, EnumOptions.RENDER_DISTANCE_FINE, EnumOptions.AO_LEVEL, EnumOptions.FRAMERATE_LIMIT_FINE, EnumOptions.ANAGLYPH, EnumOptions.VIEW_BOBBING, EnumOptions.GUI_SCALE, EnumOptions.ADVANCED_OPENGL, EnumOptions.GAMMA, EnumOptions.CHUNK_LOADING, EnumOptions.FOG_FANCY, EnumOptions.FOG_START};
   private int lastMouseX = 0;
   private int lastMouseY = 0;
   private long mouseStillTime = 0L;


   public GuiVideoSettings(GuiScreen p_i3034_1_, GameSettings p_i3034_2_) {
      this.field_74105_b = p_i3034_1_;
      this.field_74106_c = p_i3034_2_;
   }

   public void func_73866_w_() {
      StringTranslate var1 = StringTranslate.func_74808_a();
      this.field_74107_a = var1.func_74805_b("options.videoTitle");
      int var2 = 0;
      EnumOptions[] var3 = field_74108_m;
      int var4 = var3.length;

      int var5;
      int var7;
      for(var5 = 0; var5 < var4; ++var5) {
         EnumOptions var6 = var3[var5];
         var7 = this.field_73880_f / 2 - 155 + var5 % 2 * 160;
         int var8 = this.field_73881_g / 6 + 21 * (var5 / 2) - 10;
         if(var6.func_74380_a()) {
            this.field_73887_h.add(new GuiSlider(var6.func_74381_c(), var7, var8, var6, this.field_74106_c.func_74297_c(var6), this.field_74106_c.func_74296_a(var6)));
         } else {
            this.field_73887_h.add(new GuiSmallButton(var6.func_74381_c(), var7, var8, var6, this.field_74106_c.func_74297_c(var6)));
         }

         ++var2;
      }

      int var13 = this.field_73881_g / 6 + 21 * (var5 / 2) - 10;
      boolean var14 = false;
      var7 = this.field_73880_f / 2 - 155 + 0;
      this.field_73887_h.add(new GuiSmallButton(101, var7, var13, "Details..."));
      var7 = this.field_73880_f / 2 - 155 + 160;
      this.field_73887_h.add(new GuiSmallButton(102, var7, var13, "Quality..."));
      var13 += 21;
      var7 = this.field_73880_f / 2 - 155 + 0;
      this.field_73887_h.add(new GuiSmallButton(111, var7, var13, "Animations..."));
      var7 = this.field_73880_f / 2 - 155 + 160;
      this.field_73887_h.add(new GuiSmallButton(112, var7, var13, "Performance..."));
      var13 += 21;
      var7 = this.field_73880_f / 2 - 155 + 0;
      this.field_73887_h.add(new GuiSmallButton(121, var7, var13, "Texture Packs..."));
      var7 = this.field_73880_f / 2 - 155 + 160;
      this.field_73887_h.add(new GuiSmallButton(122, var7, var13, "Other..."));
      this.field_73887_h.add(new GuiButton(200, this.field_73880_f / 2 - 100, this.field_73881_g / 6 + 168 + 11, var1.func_74805_b("gui.done")));
      this.field_74104_d = false;
      String[] var15 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
      String[] var9 = var15;
      var5 = var15.length;

      for(int var10 = 0; var10 < var5; ++var10) {
         String var11 = var9[var10];
         String var12 = System.getProperty(var11);
         if(var12 != null && var12.contains("64")) {
            this.field_74104_d = true;
            break;
         }
      }

   }

   protected void func_73875_a(GuiButton p_73875_1_) {
      if(p_73875_1_.field_73742_g) {
         int var2 = this.field_74106_c.field_74335_Z;
         if(p_73875_1_.field_73741_f < 100 && p_73875_1_ instanceof GuiSmallButton) {
            this.field_74106_c.func_74306_a(((GuiSmallButton)p_73875_1_).func_73753_a(), 1);
            p_73875_1_.field_73744_e = this.field_74106_c.func_74297_c(EnumOptions.func_74379_a(p_73875_1_.field_73741_f));
         }

         if(p_73875_1_.field_73741_f == 200) {
            this.field_73882_e.field_71474_y.func_74303_b();
            this.field_73882_e.func_71373_a(this.field_74105_b);
         }

         if(this.field_74106_c.field_74335_Z != var2) {
            ScaledResolution var3 = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
            int var4 = var3.func_78326_a();
            int var5 = var3.func_78328_b();
            this.func_73872_a(this.field_73882_e, var4, var5);
         }

         if(p_73875_1_.field_73741_f == 101) {
            this.field_73882_e.field_71474_y.func_74303_b();
            GuiDetailSettingsOF var6 = new GuiDetailSettingsOF(this, this.field_74106_c);
            this.field_73882_e.func_71373_a(var6);
         }

         if(p_73875_1_.field_73741_f == 102) {
            this.field_73882_e.field_71474_y.func_74303_b();
            GuiQualitySettingsOF var7 = new GuiQualitySettingsOF(this, this.field_74106_c);
            this.field_73882_e.func_71373_a(var7);
         }

         if(p_73875_1_.field_73741_f == 111) {
            this.field_73882_e.field_71474_y.func_74303_b();
            GuiAnimationSettingsOF var8 = new GuiAnimationSettingsOF(this, this.field_74106_c);
            this.field_73882_e.func_71373_a(var8);
         }

         if(p_73875_1_.field_73741_f == 112) {
            this.field_73882_e.field_71474_y.func_74303_b();
            GuiPerformanceSettingsOF var9 = new GuiPerformanceSettingsOF(this, this.field_74106_c);
            this.field_73882_e.func_71373_a(var9);
         }

         if(p_73875_1_.field_73741_f == 121) {
            this.field_73882_e.field_71474_y.func_74303_b();
            GuiTexturePacks var10 = new GuiTexturePacks(this);
            this.field_73882_e.func_71373_a(var10);
         }

         if(p_73875_1_.field_73741_f == 122) {
            this.field_73882_e.field_71474_y.func_74303_b();
            GuiOtherSettingsOF var11 = new GuiOtherSettingsOF(this, this.field_74106_c);
            this.field_73882_e.func_71373_a(var11);
         }

         if(p_73875_1_.field_73741_f == EnumOptions.AO_LEVEL.ordinal()) {
            return;
         }
      }

   }

   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
      this.func_73873_v_();
      this.func_73732_a(this.field_73886_k, this.field_74107_a, this.field_73880_f / 2, 20, 16777215);
      super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
      if(Math.abs(p_73863_1_ - this.lastMouseX) <= 5 && Math.abs(p_73863_2_ - this.lastMouseY) <= 5) {
         short var4 = 700;
         if(System.currentTimeMillis() >= this.mouseStillTime + (long)var4) {
            int var5 = this.field_73880_f / 2 - 150;
            int var6 = this.field_73881_g / 6 - 5;
            if(p_73863_2_ <= var6 + 98) {
               var6 += 105;
            }

            int var7 = var5 + 150 + 150;
            int var8 = var6 + 84 + 10;
            GuiButton var9 = this.getSelectedButton(p_73863_1_, p_73863_2_);
            if(var9 != null) {
               String var10 = this.getButtonName(var9.field_73744_e);
               String[] var11 = this.getTooltipLines(var10);
               if(var11 == null) {
                  return;
               }

               this.func_73733_a(var5, var6, var7, var8, -536870912, -536870912);

               for(int var12 = 0; var12 < var11.length; ++var12) {
                  String var13 = var11[var12];
                  this.field_73886_k.func_78261_a(var13, var5 + 5, var6 + 5 + var12 * 11, 14540253);
               }
            }

         }
      } else {
         this.lastMouseX = p_73863_1_;
         this.lastMouseY = p_73863_2_;
         this.mouseStillTime = System.currentTimeMillis();
      }
   }

   private String[] getTooltipLines(String var1) {
      return var1.equals("Graphics")?new String[]{"Visual quality", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Changes the appearance of clouds, leaves, water,", "shadows and grass sides."}:(var1.equals("Render Distance")?new String[]{"Visible distance", "  Tiny - 32m (fastest)", "  Short - 64m (faster)", "  Normal - 128m", "  Far - 256m (slower)", "  Extreme - 512m (slowest!)", "The Extreme view distance is very resource demanding!"}:(var1.equals("Smooth Lighting")?new String[]{"Smooth lighting", "  OFF - no smooth lighting (faster)", "  1% - light smooth lighting (slower)", "  100% - dark smooth lighting (slower)"}:(var1.equals("Performance")?new String[]{"FPS Limit", "  Max FPS - no limit (fastest)", "  Balanced - limit 120 FPS (slower)", "  Power saver - limit 40 FPS (slowest)", "  VSync - limit to monitor framerate (60, 30, 20)", "Balanced and Power saver decrease the FPS even if", "the limit value is not reached."}:(var1.equals("3D Anaglyph")?new String[]{"3D mode used with red-cyan 3D glasses."}:(var1.equals("View Bobbing")?new String[]{"More realistic movement.", "When using mipmaps set it to OFF for best results."}:(var1.equals("GUI Scale")?new String[]{"GUI Scale", "Smaller GUI might be faster"}:(var1.equals("Advanced OpenGL")?new String[]{"Detect and render only visible geometry", "  OFF - all geometry is rendered (slower)", "  Fast - only visible geometry is rendered (fastest)", "  Fancy - conservative, avoids visual artifacts (faster)", "The option is available only if it is supported by the ", "graphic card."}:(var1.equals("Fog")?new String[]{"Fog type", "  Fast - faster fog", "  Fancy - slower fog, looks better", "  OFF - no fog, fastest", "The fancy fog is available only if it is supported by the ", "graphic card."}:(var1.equals("Fog Start")?new String[]{"Fog start", "  0.2 - the fog starts near the player", "  0.8 - the fog starts far from the player", "This option usually does not affect the performance."}:(var1.equals("Brightness")?new String[]{"Increases the brightness of darker objects", "  OFF - standard brightness", "  100% - maximum brightness for darker objects", "This options does not change the brightness of ", "fully black objects"}:(var1.equals("Chunk Loading")?new String[]{"Chunk Loading", "  Default - unstable FPS when loading chunks", "  Smooth - stable FPS", "  Multi-Core - stable FPS, 3x faster world loading", "Smooth and Multi-Core remove the stuttering and freezes", "caused by chunk loading.", "Multi-Core can speed up 3x the world loading and", "increase FPS by using a second CPU core."}:null)))))))))));
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
