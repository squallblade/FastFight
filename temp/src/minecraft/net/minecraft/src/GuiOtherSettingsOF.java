package net.minecraft.src;

import net.minecraft.src.EnumOptions;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlider;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.GuiYesNo;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;

public class GuiOtherSettingsOF extends GuiScreen {

   private GuiScreen prevScreen;
   protected String title = "Other Settings";
   private GameSettings settings;
   private static EnumOptions[] enumOptions = new EnumOptions[]{EnumOptions.LAGOMETER, EnumOptions.PROFILER, EnumOptions.WEATHER, EnumOptions.TIME, EnumOptions.USE_FULLSCREEN, EnumOptions.FULLSCREEN_MODE, EnumOptions.USE_SERVER_TEXTURES, EnumOptions.AUTOSAVE_TICKS};
   private int lastMouseX = 0;
   private int lastMouseY = 0;
   private long mouseStillTime = 0L;


   public GuiOtherSettingsOF(GuiScreen var1, GameSettings var2) {
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

      this.field_73887_h.add(new GuiButton(210, this.field_73880_f / 2 - 100, this.field_73881_g / 6 + 168 + 11 - 22, "Reset Video Settings..."));
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

         if(var1.field_73741_f == 210) {
            this.field_73882_e.field_71474_y.func_74303_b();
            GuiYesNo var2 = new GuiYesNo(this, "Reset all video settings to their default values?", "", 9999);
            this.field_73882_e.func_71373_a(var2);
         }

         if(var1.field_73741_f != EnumOptions.CLOUD_HEIGHT.ordinal()) {
            ScaledResolution var5 = new ScaledResolution(this.field_73882_e.field_71474_y, this.field_73882_e.field_71443_c, this.field_73882_e.field_71440_d);
            int var3 = var5.func_78326_a();
            int var4 = var5.func_78328_b();
            this.func_73872_a(this.field_73882_e, var3, var4);
         }

      }
   }

   public void func_73878_a(boolean var1, int var2) {
      if(var1) {
         this.field_73882_e.field_71474_y.resetSettings();
      }

      this.field_73882_e.func_71373_a(this);
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
                  this.field_73886_k.func_78261_a(var13, var5 + 5, var6 + 5 + var12 * 11, 14540253);
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
      return var1.equals("Autosave")?new String[]{"Autosave interval", "Default autosave interval (2s) is NOT RECOMMENDED.", "Autosave causes the famous Lag Spike of Death."}:(var1.equals("Lagometer")?new String[]{"Lagometer", " OFF - no lagometer, faster", " ON - debug screen with lagometer, slower", "Shows the lagometer on the debug screen (F3).", "* White - tick", "* Red - chunk loading", "* Green - frame rendering + internal server"}:(var1.equals("Debug Profiler")?new String[]{"Debug Profiler", "  ON - debug profiler is active, slower", "  OFF - debug profiler is not active, faster", "The debug profiler collects and shows debug information", "when the debug screen is open (F3)"}:(var1.equals("Time")?new String[]{"Time", " Default - normal day/night cycles", " Day Only - day only", " Night Only - night only", "The time setting is only effective in CREATIVE mode."}:(var1.equals("Weather")?new String[]{"Weather", "  ON - weather is active, slower", "  OFF - weather is not active, faster", "The weather controls rain, snow and thunderstorms."}:(var1.equals("Fullscreen")?new String[]{"Fullscreen resolution", "  Default - use desktop screen resolution, slower", "  WxH - use custom screen resolution, may be faster", "The selected resolution is used in fullscreen mode (F11)."}:null)))));
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
