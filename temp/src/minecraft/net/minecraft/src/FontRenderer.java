package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.Bidi;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.Config;
import net.minecraft.src.GameSettings;
import net.minecraft.src.ITexturePack;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class FontRenderer {

   private float[] field_78286_d = new float[256];
   public int field_78290_a = 0;
   public int field_78288_b = 9;
   public Random field_78289_c = new Random();
   private byte[] field_78287_e = new byte[65536];
   private final int[] field_78284_f = new int[256];
   private int[] field_78285_g = new int[32];
   private int field_78297_h;
   private final RenderEngine field_78298_i;
   private float field_78295_j;
   private float field_78296_k;
   private boolean field_78293_l;
   private boolean field_78294_m;
   private float field_78291_n;
   private float field_78292_o;
   private float field_78306_p;
   private float field_78305_q;
   private GameSettings gameSettings;
   private String textureFile;
   private long lastUpdateTime = 0L;
   public boolean enabled = true;
   private int field_78304_r;
   private boolean field_78303_s = false;
   private boolean field_78302_t = false;
   private boolean field_78301_u = false;
   private boolean field_78300_v = false;
   private boolean field_78299_w = false;


   FontRenderer() {
      this.field_78298_i = null;
   }

   public FontRenderer(GameSettings p_i3067_1_, String p_i3067_2_, RenderEngine p_i3067_3_, boolean p_i3067_4_) {
      this.field_78298_i = p_i3067_3_;
      this.field_78293_l = p_i3067_4_;
      this.gameSettings = p_i3067_1_;
      this.textureFile = p_i3067_2_;
      this.init();
   }

   private void init() {
      this.field_78286_d = new float[256];
      this.field_78290_a = 0;
      this.field_78287_e = new byte[65536];

      BufferedImage var1;
      try {
         var1 = ImageIO.read(this.getFontTexturePack().func_77532_a(this.textureFile));
         InputStream var2 = this.getFontTexturePack().func_77532_a("/font/glyph_sizes.bin");
         var2.read(this.field_78287_e);
      } catch (IOException var22) {
         throw new RuntimeException(var22);
      }

      int var23 = var1.getWidth();
      int var3 = var1.getHeight();
      int var4 = var23 / 16;
      int var5 = var3 / 16;
      float var6 = (float)var23 / 128.0F;
      int[] var7 = new int[var23 * var3];
      var1.getRGB(0, 0, var23, var3, var7, 0, var23);
      int var8 = 0;

      int var9;
      int var10;
      int var12;
      int var14;
      int var15;
      int var16;
      int var25;
      while(var8 < 256) {
         var9 = var8 % 16;
         var10 = var8 / 16;
         boolean var11 = false;
         var25 = var4 - 1;

         while(true) {
            if(var25 >= 0) {
               var12 = var9 * var4 + var25;
               boolean var13 = true;

               for(var14 = 0; var14 < var5 && var13; ++var14) {
                  var15 = (var10 * var5 + var14) * var23;
                  var16 = var7[var12 + var15];
                  int var17 = var16 >> 24 & 255;
                  if(var17 > 16) {
                     var13 = false;
                  }
               }

               if(var13) {
                  --var25;
                  continue;
               }
            }

            if(var8 == 65) {
               var8 = var8;
            }

            if(var8 == 32) {
               if(var4 <= 8) {
                  var25 = (int)(2.0F * var6);
               } else {
                  var25 = (int)(1.5F * var6);
               }
            }

            this.field_78286_d[var8] = (float)(var25 + 1) / var6 + 1.0F;
            ++var8;
            break;
         }
      }

      this.readCustomCharWidths();
      RenderEngine var10000 = this.field_78298_i;
      boolean var24 = RenderEngine.useMipmaps;

      try {
         var10000 = this.field_78298_i;
         RenderEngine.useMipmaps = false;
         if(this.field_78290_a <= 0) {
            this.field_78290_a = this.field_78298_i.func_78353_a(var1);
         } else {
            this.field_78298_i.func_78351_a(var1, this.field_78290_a);
         }
      } finally {
         var10000 = this.field_78298_i;
         RenderEngine.useMipmaps = var24;
      }

      for(var9 = 0; var9 < 32; ++var9) {
         var10 = (var9 >> 3 & 1) * 85;
         var25 = (var9 >> 2 & 1) * 170 + var10;
         var12 = (var9 >> 1 & 1) * 170 + var10;
         int var26 = (var9 >> 0 & 1) * 170 + var10;
         if(var9 == 6) {
            var25 += 85;
         }

         if(this.gameSettings.field_74337_g) {
            var14 = (var25 * 30 + var12 * 59 + var26 * 11) / 100;
            var15 = (var25 * 30 + var12 * 70) / 100;
            var16 = (var25 * 30 + var26 * 70) / 100;
            var25 = var14;
            var12 = var15;
            var26 = var16;
         }

         if(var9 >= 16) {
            var25 /= 4;
            var12 /= 4;
            var26 /= 4;
         }

         this.field_78285_g[var9] = (var25 & 255) << 16 | (var12 & 255) << 8 | var26 & 255;
      }

   }

   private void readCustomCharWidths() {
      String var1 = ".png";
      if(this.textureFile.endsWith(var1)) {
         String var2 = this.textureFile.substring(0, this.textureFile.length() - var1.length()) + ".properties";
         InputStream var3 = this.getFontTexturePack().func_77532_a(var2);
         if(var3 != null) {
            try {
               Config.log("Loading " + var2);
               Properties var4 = new Properties();
               var4.load(var3);
               Set var5 = var4.keySet();
               Iterator var6 = var5.iterator();

               while(var6.hasNext()) {
                  String var7 = (String)var6.next();
                  String var8 = "width.";
                  if(var7.startsWith(var8)) {
                     String var9 = var7.substring(var8.length());
                     int var10 = Config.parseInt(var9, -1);
                     if(var10 >= 0 && var10 < this.field_78286_d.length) {
                        String var11 = var4.getProperty(var7);
                        float var12 = Config.parseFloat(var11, -1.0F);
                        if(var12 >= 0.0F) {
                           this.field_78286_d[var10] = var12;
                        }
                     }
                  }
               }
            } catch (IOException var13) {
               var13.printStackTrace();
            }

         }
      }
   }

   private ITexturePack getFontTexturePack() {
      return this.gameSettings.ofCustomFonts?this.gameSettings.field_74317_L.field_71418_C.func_77292_e():(ITexturePack)this.gameSettings.field_74317_L.field_71418_C.func_77293_d().get(0);
   }

   private void checkUpdated() {
      if(Config.getTextureUpdateTime() != this.lastUpdateTime) {
         this.lastUpdateTime = Config.getTextureUpdateTime();
         this.init();
      }
   }

   private float func_78278_a(int p_78278_1_, char p_78278_2_, boolean p_78278_3_) {
      return p_78278_2_ == 32?this.field_78286_d[p_78278_2_]:(p_78278_1_ > 0 && !this.field_78293_l?this.func_78266_a(p_78278_1_ + 32, p_78278_3_):this.func_78277_a(p_78278_2_, p_78278_3_));
   }

   private float func_78266_a(int p_78266_1_, boolean p_78266_2_) {
      float var3 = (float)(p_78266_1_ % 16 * 8);
      float var4 = (float)(p_78266_1_ / 16 * 8);
      float var5 = p_78266_2_?1.0F:0.0F;
      if(this.field_78297_h != this.field_78290_a) {
         GL11.glBindTexture(3553, this.field_78290_a);
         this.field_78297_h = this.field_78290_a;
      }

      float var6 = this.field_78286_d[p_78266_1_] - 0.01F;
      GL11.glBegin(5);
      GL11.glTexCoord2f(var3 / 128.0F, var4 / 128.0F);
      GL11.glVertex3f(this.field_78295_j + var5, this.field_78296_k, 0.0F);
      GL11.glTexCoord2f(var3 / 128.0F, (var4 + 7.99F) / 128.0F);
      GL11.glVertex3f(this.field_78295_j - var5, this.field_78296_k + 7.99F, 0.0F);
      GL11.glTexCoord2f((var3 + var6) / 128.0F, var4 / 128.0F);
      GL11.glVertex3f(this.field_78295_j + var6 + var5, this.field_78296_k, 0.0F);
      GL11.glTexCoord2f((var3 + var6) / 128.0F, (var4 + 7.99F) / 128.0F);
      GL11.glVertex3f(this.field_78295_j + var6 - var5, this.field_78296_k + 7.99F, 0.0F);
      GL11.glEnd();
      return this.field_78286_d[p_78266_1_];
   }

   private void func_78257_a(int p_78257_1_) {
      String var2 = String.format("/font/glyph_%02X.png", new Object[]{Integer.valueOf(p_78257_1_)});

      BufferedImage var3;
      try {
         var3 = ImageIO.read(RenderEngine.class.getResourceAsStream(var2));
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      this.field_78284_f[p_78257_1_] = this.field_78298_i.func_78353_a(var3);
      this.field_78297_h = this.field_78284_f[p_78257_1_];
   }

   private float func_78277_a(char p_78277_1_, boolean p_78277_2_) {
      if(this.field_78287_e[p_78277_1_] == 0) {
         return 0.0F;
      } else {
         int var3 = p_78277_1_ / 256;
         if(this.field_78284_f[var3] == 0) {
            this.func_78257_a(var3);
         }

         if(this.field_78297_h != this.field_78284_f[var3]) {
            GL11.glBindTexture(3553, this.field_78284_f[var3]);
            this.field_78297_h = this.field_78284_f[var3];
         }

         int var4 = this.field_78287_e[p_78277_1_] >>> 4;
         int var5 = this.field_78287_e[p_78277_1_] & 15;
         float var6 = (float)var4;
         float var7 = (float)(var5 + 1);
         float var8 = (float)(p_78277_1_ % 16 * 16) + var6;
         float var9 = (float)((p_78277_1_ & 255) / 16 * 16);
         float var10 = var7 - var6 - 0.02F;
         float var11 = p_78277_2_?1.0F:0.0F;
         GL11.glBegin(5);
         GL11.glTexCoord2f(var8 / 256.0F, var9 / 256.0F);
         GL11.glVertex3f(this.field_78295_j + var11, this.field_78296_k, 0.0F);
         GL11.glTexCoord2f(var8 / 256.0F, (var9 + 15.98F) / 256.0F);
         GL11.glVertex3f(this.field_78295_j - var11, this.field_78296_k + 7.99F, 0.0F);
         GL11.glTexCoord2f((var8 + var10) / 256.0F, var9 / 256.0F);
         GL11.glVertex3f(this.field_78295_j + var10 / 2.0F + var11, this.field_78296_k, 0.0F);
         GL11.glTexCoord2f((var8 + var10) / 256.0F, (var9 + 15.98F) / 256.0F);
         GL11.glVertex3f(this.field_78295_j + var10 / 2.0F - var11, this.field_78296_k + 7.99F, 0.0F);
         GL11.glEnd();
         return (var7 - var6) / 2.0F + 1.0F;
      }
   }

   public int func_78261_a(String p_78261_1_, int p_78261_2_, int p_78261_3_, int p_78261_4_) {
      return this.func_85187_a(p_78261_1_, p_78261_2_, p_78261_3_, p_78261_4_, true);
   }

   public int func_78276_b(String p_78276_1_, int p_78276_2_, int p_78276_3_, int p_78276_4_) {
      return !this.enabled?0:this.func_85187_a(p_78276_1_, p_78276_2_, p_78276_3_, p_78276_4_, false);
   }

   public int func_85187_a(String p_85187_1_, int p_85187_2_, int p_85187_3_, int p_85187_4_, boolean p_85187_5_) {
      this.func_78265_b();
      if(this.field_78294_m) {
         p_85187_1_ = this.func_78283_c(p_85187_1_);
      }

      int var6;
      if(p_85187_5_) {
         var6 = this.func_78258_a(p_85187_1_, p_85187_2_ + 1, p_85187_3_ + 1, p_85187_4_, true);
         var6 = Math.max(var6, this.func_78258_a(p_85187_1_, p_85187_2_, p_85187_3_, p_85187_4_, false));
      } else {
         var6 = this.func_78258_a(p_85187_1_, p_85187_2_, p_85187_3_, p_85187_4_, false);
      }

      return var6;
   }

   private String func_78283_c(String p_78283_1_) {
      if(p_78283_1_ != null && Bidi.requiresBidi(p_78283_1_.toCharArray(), 0, p_78283_1_.length())) {
         Bidi var2 = new Bidi(p_78283_1_, -2);
         byte[] var3 = new byte[var2.getRunCount()];
         String[] var4 = new String[var3.length];

         int var5;
         for(int var6 = 0; var6 < var3.length; ++var6) {
            int var7 = var2.getRunStart(var6);
            var5 = var2.getRunLimit(var6);
            int var8 = var2.getRunLevel(var6);
            String var9 = p_78283_1_.substring(var7, var5);
            var3[var6] = (byte)var8;
            var4[var6] = var9;
         }

         String[] var11 = (String[])((String[])var4.clone());
         Bidi.reorderVisually(var3, 0, var4, 0, var3.length);
         StringBuilder var12 = new StringBuilder();
         var5 = 0;

         while(var5 < var4.length) {
            byte var13 = var3[var5];
            int var14 = 0;

            while(true) {
               if(var14 < var11.length) {
                  if(!var11[var14].equals(var4[var5])) {
                     ++var14;
                     continue;
                  }

                  var13 = var3[var14];
               }

               if((var13 & 1) == 0) {
                  var12.append(var4[var5]);
               } else {
                  for(var14 = var4[var5].length() - 1; var14 >= 0; --var14) {
                     char var10 = var4[var5].charAt(var14);
                     if(var10 == 40) {
                        var10 = 41;
                     } else if(var10 == 41) {
                        var10 = 40;
                     }

                     var12.append(var10);
                  }
               }

               ++var5;
               break;
            }
         }

         return var12.toString();
      } else {
         return p_78283_1_;
      }
   }

   private void func_78265_b() {
      this.field_78303_s = false;
      this.field_78302_t = false;
      this.field_78301_u = false;
      this.field_78300_v = false;
      this.field_78299_w = false;
   }

   private void func_78255_a(String p_78255_1_, boolean p_78255_2_) {
      for(int var3 = 0; var3 < p_78255_1_.length(); ++var3) {
         char var4 = p_78255_1_.charAt(var3);
         int var5;
         int var6;
         if(var4 == 167 && var3 + 1 < p_78255_1_.length()) {
            var5 = "0123456789abcdefklmnor".indexOf(p_78255_1_.toLowerCase().charAt(var3 + 1));
            if(var5 < 16) {
               this.field_78303_s = false;
               this.field_78302_t = false;
               this.field_78299_w = false;
               this.field_78300_v = false;
               this.field_78301_u = false;
               if(var5 < 0 || var5 > 15) {
                  var5 = 15;
               }

               if(p_78255_2_) {
                  var5 += 16;
               }

               var6 = this.field_78285_g[var5];
               this.field_78304_r = var6;
               GL11.glColor4f((float)(var6 >> 16) / 255.0F, (float)(var6 >> 8 & 255) / 255.0F, (float)(var6 & 255) / 255.0F, this.field_78305_q);
            } else if(var5 == 16) {
               this.field_78303_s = true;
            } else if(var5 == 17) {
               this.field_78302_t = true;
            } else if(var5 == 18) {
               this.field_78299_w = true;
            } else if(var5 == 19) {
               this.field_78300_v = true;
            } else if(var5 == 20) {
               this.field_78301_u = true;
            } else if(var5 == 21) {
               this.field_78303_s = false;
               this.field_78302_t = false;
               this.field_78299_w = false;
               this.field_78300_v = false;
               this.field_78301_u = false;
               GL11.glColor4f(this.field_78291_n, this.field_78292_o, this.field_78306_p, this.field_78305_q);
            }

            ++var3;
         } else {
            var5 = ChatAllowedCharacters.field_71568_a.indexOf(var4);
            if(this.field_78303_s && var5 > 0) {
               do {
                  var6 = this.field_78289_c.nextInt(ChatAllowedCharacters.field_71568_a.length());
               } while((int)this.field_78286_d[var5 + 32] != (int)this.field_78286_d[var6 + 32]);

               var5 = var6;
            }

            float var7 = this.func_78278_a(var5, var4, this.field_78301_u);
            if(this.field_78302_t) {
               ++this.field_78295_j;
               this.func_78278_a(var5, var4, this.field_78301_u);
               --this.field_78295_j;
               ++var7;
            }

            Tessellator var8;
            if(this.field_78299_w) {
               var8 = Tessellator.field_78398_a;
               GL11.glDisable(3553);
               var8.func_78382_b();
               var8.func_78377_a((double)this.field_78295_j, (double)(this.field_78296_k + (float)(this.field_78288_b / 2)), 0.0D);
               var8.func_78377_a((double)(this.field_78295_j + var7), (double)(this.field_78296_k + (float)(this.field_78288_b / 2)), 0.0D);
               var8.func_78377_a((double)(this.field_78295_j + var7), (double)(this.field_78296_k + (float)(this.field_78288_b / 2) - 1.0F), 0.0D);
               var8.func_78377_a((double)this.field_78295_j, (double)(this.field_78296_k + (float)(this.field_78288_b / 2) - 1.0F), 0.0D);
               var8.func_78381_a();
               GL11.glEnable(3553);
            }

            if(this.field_78300_v) {
               var8 = Tessellator.field_78398_a;
               GL11.glDisable(3553);
               var8.func_78382_b();
               int var9 = this.field_78300_v?-1:0;
               var8.func_78377_a((double)(this.field_78295_j + (float)var9), (double)(this.field_78296_k + (float)this.field_78288_b), 0.0D);
               var8.func_78377_a((double)(this.field_78295_j + var7), (double)(this.field_78296_k + (float)this.field_78288_b), 0.0D);
               var8.func_78377_a((double)(this.field_78295_j + var7), (double)(this.field_78296_k + (float)this.field_78288_b - 1.0F), 0.0D);
               var8.func_78377_a((double)(this.field_78295_j + (float)var9), (double)(this.field_78296_k + (float)this.field_78288_b - 1.0F), 0.0D);
               var8.func_78381_a();
               GL11.glEnable(3553);
            }

            this.field_78295_j += var7;
         }
      }

   }

   private int func_78274_b(String p_78274_1_, int p_78274_2_, int p_78274_3_, int p_78274_4_, int p_78274_5_, boolean p_78274_6_) {
      if(this.field_78294_m) {
         p_78274_1_ = this.func_78283_c(p_78274_1_);
         int var7 = this.func_78256_a(p_78274_1_);
         p_78274_2_ = p_78274_2_ + p_78274_4_ - var7;
      }

      return this.func_78258_a(p_78274_1_, p_78274_2_, p_78274_3_, p_78274_5_, p_78274_6_);
   }

   private int func_78258_a(String p_78258_1_, int p_78258_2_, int p_78258_3_, int p_78258_4_, boolean p_78258_5_) {
      if(p_78258_1_ == null) {
         return 0;
      } else {
         this.field_78297_h = 0;
         if((p_78258_4_ & -67108864) == 0) {
            p_78258_4_ |= -16777216;
         }

         if(p_78258_5_) {
            p_78258_4_ = (p_78258_4_ & 16579836) >> 2 | p_78258_4_ & -16777216;
         }

         this.field_78291_n = (float)(p_78258_4_ >> 16 & 255) / 255.0F;
         this.field_78292_o = (float)(p_78258_4_ >> 8 & 255) / 255.0F;
         this.field_78306_p = (float)(p_78258_4_ & 255) / 255.0F;
         this.field_78305_q = (float)(p_78258_4_ >> 24 & 255) / 255.0F;
         GL11.glColor4f(this.field_78291_n, this.field_78292_o, this.field_78306_p, this.field_78305_q);
         this.field_78295_j = (float)p_78258_2_;
         this.field_78296_k = (float)p_78258_3_;
         this.func_78255_a(p_78258_1_, p_78258_5_);
         return (int)this.field_78295_j;
      }
   }

   public int func_78256_a(String p_78256_1_) {
      this.checkUpdated();
      if(p_78256_1_ == null) {
         return 0;
      } else {
         float var2 = 0.0F;
         boolean var3 = false;

         for(int var4 = 0; var4 < p_78256_1_.length(); ++var4) {
            char var5 = p_78256_1_.charAt(var4);
            float var6 = this.getCharWidthFloat(var5);
            if(var6 < 0.0F && var4 < p_78256_1_.length() - 1) {
               ++var4;
               var5 = p_78256_1_.charAt(var4);
               if(var5 != 108 && var5 != 76) {
                  if(var5 == 114 || var5 == 82) {
                     var3 = false;
                  }
               } else {
                  var3 = true;
               }

               var6 = 0.0F;
            }

            var2 += var6;
            if(var3) {
               ++var2;
            }
         }

         return (int)var2;
      }
   }

   public int func_78263_a(char p_78263_1_) {
      return Math.round(this.getCharWidthFloat(p_78263_1_));
   }

   private float getCharWidthFloat(char var1) {
      if(var1 == 167) {
         return -1.0F;
      } else if(var1 == 32) {
         return this.field_78286_d[32];
      } else {
         int var2 = ChatAllowedCharacters.field_71568_a.indexOf(var1);
         if(var2 >= 0 && !this.field_78293_l) {
            return this.field_78286_d[var2 + 32];
         } else if(this.field_78287_e[var1] != 0) {
            int var3 = this.field_78287_e[var1] >>> 4;
            int var4 = this.field_78287_e[var1] & 15;
            if(var4 > 7) {
               var4 = 15;
               var3 = 0;
            }

            ++var4;
            return (float)((var4 - var3) / 2 + 1);
         } else {
            return 0.0F;
         }
      }
   }

   public String func_78269_a(String p_78269_1_, int p_78269_2_) {
      return this.func_78262_a(p_78269_1_, p_78269_2_, false);
   }

   public String func_78262_a(String p_78262_1_, int p_78262_2_, boolean p_78262_3_) {
      StringBuilder var4 = new StringBuilder();
      float var5 = 0.0F;
      int var6 = p_78262_3_?p_78262_1_.length() - 1:0;
      int var7 = p_78262_3_?-1:1;
      boolean var8 = false;
      boolean var9 = false;

      for(int var10 = var6; var10 >= 0 && var10 < p_78262_1_.length() && var5 < (float)p_78262_2_; var10 += var7) {
         char var11 = p_78262_1_.charAt(var10);
         float var12 = this.getCharWidthFloat(var11);
         if(var8) {
            var8 = false;
            if(var11 != 108 && var11 != 76) {
               if(var11 == 114 || var11 == 82) {
                  var9 = false;
               }
            } else {
               var9 = true;
            }
         } else if(var12 < 0.0F) {
            var8 = true;
         } else {
            var5 += var12;
            if(var9) {
               ++var5;
            }
         }

         if(var5 > (float)p_78262_2_) {
            break;
         }

         if(p_78262_3_) {
            var4.insert(0, var11);
         } else {
            var4.append(var11);
         }
      }

      return var4.toString();
   }

   private String func_78273_d(String p_78273_1_) {
      while(p_78273_1_ != null && p_78273_1_.endsWith("\n")) {
         p_78273_1_ = p_78273_1_.substring(0, p_78273_1_.length() - 1);
      }

      return p_78273_1_;
   }

   public void func_78279_b(String p_78279_1_, int p_78279_2_, int p_78279_3_, int p_78279_4_, int p_78279_5_) {
      this.checkUpdated();
      this.func_78265_b();
      this.field_78304_r = p_78279_5_;
      p_78279_1_ = this.func_78273_d(p_78279_1_);
      this.func_78268_b(p_78279_1_, p_78279_2_, p_78279_3_, p_78279_4_, false);
   }

   private void func_78268_b(String p_78268_1_, int p_78268_2_, int p_78268_3_, int p_78268_4_, boolean p_78268_5_) {
      this.checkUpdated();
      List var6 = this.func_78271_c(p_78268_1_, p_78268_4_);

      for(Iterator var7 = var6.iterator(); var7.hasNext(); p_78268_3_ += this.field_78288_b) {
         String var8 = (String)var7.next();
         this.func_78274_b(var8, p_78268_2_, p_78268_3_, p_78268_4_, this.field_78304_r, p_78268_5_);
      }

   }

   public int func_78267_b(String p_78267_1_, int p_78267_2_) {
      this.checkUpdated();
      return this.field_78288_b * this.func_78271_c(p_78267_1_, p_78267_2_).size();
   }

   public void func_78264_a(boolean p_78264_1_) {
      this.field_78293_l = p_78264_1_;
   }

   public boolean func_82883_a() {
      return this.field_78293_l;
   }

   public void func_78275_b(boolean p_78275_1_) {
      this.field_78294_m = p_78275_1_;
   }

   public List func_78271_c(String p_78271_1_, int p_78271_2_) {
      return Arrays.asList(this.func_78280_d(p_78271_1_, p_78271_2_).split("\n"));
   }

   String func_78280_d(String p_78280_1_, int p_78280_2_) {
      int var3 = this.func_78259_e(p_78280_1_, p_78280_2_);
      if(p_78280_1_.length() <= var3) {
         return p_78280_1_;
      } else {
         String var4 = p_78280_1_.substring(0, var3);
         char var5 = p_78280_1_.charAt(var3);
         boolean var6 = var5 == 32 || var5 == 10;
         String var7 = func_78282_e(var4) + p_78280_1_.substring(var3 + (var6?1:0));
         return var4 + "\n" + this.func_78280_d(var7, p_78280_2_);
      }
   }

   private int func_78259_e(String p_78259_1_, int p_78259_2_) {
      int var3 = p_78259_1_.length();
      float var4 = 0.0F;
      int var5 = 0;
      int var6 = -1;

      for(boolean var7 = false; var5 < var3; ++var5) {
         char var8 = p_78259_1_.charAt(var5);
         switch(var8) {
         case 10:
            --var5;
            break;
         case 32:
            var6 = var5;
         case 167:
            if(var5 < var3 - 1) {
               ++var5;
               char var9 = p_78259_1_.charAt(var5);
               if(var9 != 108 && var9 != 76) {
                  if(var9 == 114 || var9 == 82) {
                     var7 = false;
                  }
               } else {
                  var7 = true;
               }
            }
            break;
         default:
            var4 += this.getCharWidthFloat(var8);
            if(var7) {
               ++var4;
            }
         }

         if(var8 == 10) {
            ++var5;
            var6 = var5;
            break;
         }

         if(var4 > (float)p_78259_2_) {
            break;
         }
      }

      return var5 != var3 && var6 != -1 && var6 < var5?var6:var5;
   }

   private static boolean func_78272_b(char p_78272_0_) {
      return p_78272_0_ >= 48 && p_78272_0_ <= 57 || p_78272_0_ >= 97 && p_78272_0_ <= 102 || p_78272_0_ >= 65 && p_78272_0_ <= 70;
   }

   private static boolean func_78270_c(char p_78270_0_) {
      return p_78270_0_ >= 107 && p_78270_0_ <= 111 || p_78270_0_ >= 75 && p_78270_0_ <= 79 || p_78270_0_ == 114 || p_78270_0_ == 82;
   }

   private static String func_78282_e(String p_78282_0_) {
      String var1 = "";
      int var2 = -1;
      int var3 = p_78282_0_.length();

      while((var2 = p_78282_0_.indexOf(167, var2 + 1)) != -1) {
         if(var2 < var3 - 1) {
            char var4 = p_78282_0_.charAt(var2 + 1);
            if(func_78272_b(var4)) {
               var1 = "\u00a7" + var4;
            } else if(func_78270_c(var4)) {
               var1 = var1 + "\u00a7" + var4;
            }
         }
      }

      return var1;
   }

   public boolean func_78260_a() {
      return this.field_78294_m;
   }
}
