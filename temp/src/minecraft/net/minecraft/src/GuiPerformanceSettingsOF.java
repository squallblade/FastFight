package net.minecraft.src;

import net.minecraft.src.EnumOptions;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSlider;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StringTranslate;

public class GuiPerformanceSettingsOF extends GuiScreen {

   private GuiScreen prevScreen;
   protected String title = "Performance Settings";
   private GameSettings settings;
   private static EnumOptions[] enumOptions = new EnumOptions[]{EnumOptions.SMOOTH_FPS, EnumOptions.SMOOTH_WORLD, EnumOptions.LOAD_FAR, EnumOptions.PRELOADED_CHUNKS, EnumOptions.CHUNK_UPDATES, EnumOptions.CHUNK_UPDATES_DYNAMIC, EnumOptions.LAZY_CHUNK_LOADING};
   private int lastMouseX = 0;
   private int lastMouseY = 0;
   private long mouseStillTime = 0L;


   public GuiPerformanceSettingsOF(GuiScreen var1, GameSettings var2) {
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
      return var1.equals("Smooth FPS")?new String[]{"Stabilizes FPS by flushing the graphic driver buffers", "  OFF - no stabilization, FPS may fluctuate", "  ON - FPS stabilization", "This option is graphic driver dependant and its effect", "is not always visible"}:(var1.equals("Smooth World")?new String[]{"Removes lag spikes caused by the internal server.", "  OFF - no stabilization, FPS may fluctuate", "  ON - FPS stabilization", "Stabilizes FPS by distributing the internal server load.", "Effective only for local worlds and single-core CPU."}:(var1.equals("Load Far")?new String[]{"Loads the world chunks at distance Far.", "Switching the render distance does not cause all chunks ", "to be loaded again.", "  OFF - world chunks loaded up to render distance", "  ON - world chunks loaded at distance Far, allows", "       fast render distance switching"}:(var1.equals("Preloaded Chunks")?new String[]{"Defines an area in which no chunks will be loaded", "  OFF - after 5m new chunks will be loaded", "  2 - after 32m  new chunks will be loaded", "  8 - after 128m new chunks will be loaded", "Higher values need more time to load all the chunks"}:(var1.equals("Chunk Updates")?new String[]{"Chunk updates per frame", " 1 - (default) slower world loading, higher FPS", " 3 - faster world loading, lower FPS", " 5 - fastest world loading, lowest FPS"}:(var1.equals("Dynamic Updates")?new String[]{"Dynamic chunk updates", " OFF - (default) standard chunk updates per frame", " ON - more updates while the player is standing still", "Dynamic updates force more chunk updates while", "the player is standing still to load the world faster."}:(var1.equals("Lazy Chunk Loading")?new String[]{"Lazy Chunk Loading", " OFF - default server chunk loading", " ON - lazy server chunk loading (smoother)", "Smooths the integrated server chunk loading by", "distributing the chunks over several ticks.", "Turn it OFF if parts of the world do not load correctly.", "Effective only for local worlds and single-core CPU."}:null))))));
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
