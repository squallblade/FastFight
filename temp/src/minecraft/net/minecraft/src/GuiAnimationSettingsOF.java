package net.minecraft.src;

import net.minecraft.src.EnumOptions;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlider;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;

public class GuiAnimationSettingsOF extends GuiScreen {

   private GuiScreen prevScreen;
   protected String title = "Animation Settings";
   private GameSettings settings;
   private static EnumOptions[] enumOptions = new EnumOptions[]{EnumOptions.ANIMATED_WATER, EnumOptions.ANIMATED_LAVA, EnumOptions.ANIMATED_FIRE, EnumOptions.ANIMATED_PORTAL, EnumOptions.ANIMATED_REDSTONE, EnumOptions.ANIMATED_EXPLOSION, EnumOptions.ANIMATED_FLAME, EnumOptions.ANIMATED_SMOKE, EnumOptions.VOID_PARTICLES, EnumOptions.WATER_PARTICLES, EnumOptions.RAIN_SPLASH, EnumOptions.PORTAL_PARTICLES, EnumOptions.PARTICLES, EnumOptions.DRIPPING_WATER_LAVA, EnumOptions.ANIMATED_TERRAIN, EnumOptions.ANIMATED_ITEMS, EnumOptions.ANIMATED_TEXTURES};


   public GuiAnimationSettingsOF(GuiScreen var1, GameSettings var2) {
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

      this.field_73887_h.add(new GuiButton(210, this.field_73880_f / 2 - 155, this.field_73881_g / 6 + 168 + 11, 70, 20, "All ON"));
      this.field_73887_h.add(new GuiButton(211, this.field_73880_f / 2 - 155 + 80, this.field_73881_g / 6 + 168 + 11, 70, 20, "All OFF"));
      this.field_73887_h.add(new GuiSmallButton(200, this.field_73880_f / 2 + 5, this.field_73881_g / 6 + 168 + 11, var1.func_74805_b("gui.done")));
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

         if(var1.field_73741_f == 210) {
            this.field_73882_e.field_71474_y.setAllAnimations(true);
         }

         if(var1.field_73741_f == 211) {
            this.field_73882_e.field_71474_y.setAllAnimations(false);
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
   }

}
