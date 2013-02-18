package net.minecraft.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Config;
import net.minecraft.src.ConnectedTextures;
import net.minecraft.src.CustomAnimation;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.IImageBuffer;
import net.minecraft.src.ITexturePack;
import net.minecraft.src.IntHashMap;
import net.minecraft.src.NaturalTextures;
import net.minecraft.src.RandomMobs;
import net.minecraft.src.Reflector;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureCompassFX;
import net.minecraft.src.TextureFX;
import net.minecraft.src.TextureHDCompassFX;
import net.minecraft.src.TextureHDCustomFX;
import net.minecraft.src.TextureHDFX;
import net.minecraft.src.TextureHDFlamesFX;
import net.minecraft.src.TextureHDLavaFX;
import net.minecraft.src.TextureHDLavaFlowFX;
import net.minecraft.src.TextureHDPortalFX;
import net.minecraft.src.TextureHDWatchFX;
import net.minecraft.src.TextureHDWaterFX;
import net.minecraft.src.TextureHDWaterFlowFX;
import net.minecraft.src.TexturePackDefault;
import net.minecraft.src.TexturePackImplementation;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.TextureUtils;
import net.minecraft.src.ThreadDownloadImageData;
import net.minecraft.src.WrUpdates;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

public class RenderEngine {

   private HashMap field_78362_c = new HashMap();
   private HashMap field_78359_d = new HashMap();
   private IntHashMap field_78360_e = new IntHashMap();
   private IntBuffer field_78357_f = GLAllocation.func_74527_f(1);
   private ByteBuffer field_78358_g = GLAllocation.func_74524_c(16777216);
   public List field_78367_h = new ArrayList();
   private Map field_78368_i = new HashMap();
   private GameSettings field_78365_j;
   public boolean field_78363_a = false;
   public boolean field_78361_b = false;
   public TexturePackList field_78366_k;
   private BufferedImage field_78364_l = new BufferedImage(64, 64, 2);
   public static boolean useMipmaps = true;
   public int terrainTextureId = -1;
   public int guiItemsTextureId = -1;
   private boolean hdTexturesInstalled = false;
   private Map textureDimensionsMap = new HashMap();
   private Map textureDataMap = new HashMap();
   private int tickCounter = 0;
   private ByteBuffer[] mipImageDatas;
   private boolean dynamicTexturesUpdated = false;
   private Map textureFxMap = new IdentityHashMap();
   private Map mipDataBufsMap = new HashMap();
   private boolean singleTileTexture = false;
   private Map customAnimationMap = new HashMap();
   private CustomAnimation[] textureAnimations = null;
   public static Logger log = Logger.getAnonymousLogger();


   public RenderEngine(TexturePackList p_i3192_1_, GameSettings p_i3192_2_) {
      if(Config.isMultiTexture()) {
         int var3 = Config.getAntialiasingLevel();
         Config.dbg("FSAA Samples: " + var3);

         try {
            Display.destroy();
            Display.create((new PixelFormat()).withDepthBits(24).withSamples(var3));
         } catch (LWJGLException var9) {
            Config.dbg("Error setting FSAA: " + var3 + "x");
            var9.printStackTrace();

            try {
               Display.create((new PixelFormat()).withDepthBits(24));
            } catch (LWJGLException var8) {
               var8.printStackTrace();

               try {
                  Display.create();
               } catch (LWJGLException var7) {
                  var7.printStackTrace();
               }
            }
         }
      }

      this.field_78366_k = p_i3192_1_;
      this.field_78365_j = p_i3192_2_;
      Graphics var10 = this.field_78364_l.getGraphics();
      var10.setColor(Color.WHITE);
      var10.fillRect(0, 0, 64, 64);
      var10.setColor(Color.BLACK);
      var10.drawString("missingtex", 1, 10);
      var10.dispose();
      this.allocateImageData(256, 256);
   }

   public int[] func_78346_a(String p_78346_1_) {
      ITexturePack var2 = this.field_78366_k.func_77292_e();
      int[] var3 = (int[])((int[])this.field_78359_d.get(p_78346_1_));
      if(var3 != null) {
         return var3;
      } else {
         int[] var5;
         try {
            Object var4 = null;
            if(p_78346_1_.startsWith("##")) {
               var5 = this.func_78348_b(this.func_78354_c(this.func_78345_a(var2.func_77532_a(p_78346_1_.substring(2)))));
            } else if(p_78346_1_.startsWith("%clamp%")) {
               this.field_78363_a = true;
               var5 = this.func_78348_b(this.func_78345_a(var2.func_77532_a(p_78346_1_.substring(7))));
               this.field_78363_a = false;
            } else if(p_78346_1_.startsWith("%blur%")) {
               this.field_78361_b = true;
               this.field_78363_a = true;
               var5 = this.func_78348_b(this.func_78345_a(var2.func_77532_a(p_78346_1_.substring(6))));
               this.field_78363_a = false;
               this.field_78361_b = false;
            } else {
               InputStream var6 = var2.func_77532_a(p_78346_1_);
               if(var6 == null) {
                  var5 = this.func_78348_b(this.field_78364_l);
               } else {
                  var5 = this.func_78348_b(this.func_78345_a(var6));
               }
            }

            this.field_78359_d.put(p_78346_1_, var5);
            return var5;
         } catch (IOException var7) {
            var7.printStackTrace();
            var5 = this.func_78348_b(this.field_78364_l);
            this.field_78359_d.put(p_78346_1_, var5);
            return var5;
         }
      }
   }

   private int[] func_78348_b(BufferedImage p_78348_1_) {
      int var2 = p_78348_1_.getWidth();
      int var3 = p_78348_1_.getHeight();
      int[] var4 = new int[var2 * var3];
      p_78348_1_.getRGB(0, 0, var2, var3, var4, 0, var2);
      return var4;
   }

   private int[] func_78340_a(BufferedImage p_78340_1_, int[] p_78340_2_) {
      int var3 = p_78340_1_.getWidth();
      int var4 = p_78340_1_.getHeight();
      p_78340_1_.getRGB(0, 0, var3, var4, p_78340_2_, 0, var3);
      return p_78340_2_;
   }

   public int func_78341_b(String p_78341_1_) {
      Integer var2 = (Integer)this.field_78362_c.get(p_78341_1_);
      if(var2 != null) {
         return var2.intValue();
      } else {
         ITexturePack var3 = this.field_78366_k.func_77292_e();

         try {
            if(Reflector.ForgeHooksClient.exists()) {
               Reflector.callVoid(Reflector.ForgeHooksClient_onTextureLoadPre, new Object[]{p_78341_1_});
            }

            this.field_78357_f.clear();
            GLAllocation.func_74528_a(this.field_78357_f);
            if(Tessellator.renderingWorldRenderer) {
               System.out.printf("Warning: Texture %s not preloaded, will cause render glitches!\n", new Object[]{p_78341_1_});
            }

            int var4 = this.field_78357_f.get(0);
            Config.dbg("setupTexture: \"" + p_78341_1_ + "\", id: " + var4);
            if(p_78341_1_.startsWith("##")) {
               this.func_78351_a(this.func_78354_c(this.func_78345_a(var3.func_77532_a(p_78341_1_.substring(2)))), var4);
            } else if(p_78341_1_.startsWith("%clamp%")) {
               this.field_78363_a = true;
               if(p_78341_1_.equals("%clamp%/misc/shadow.png")) {
                  useMipmaps = false;
               }

               this.func_78351_a(this.func_78345_a(var3.func_77532_a(p_78341_1_.substring(7))), var4);
               useMipmaps = true;
               this.field_78363_a = false;
            } else if(p_78341_1_.startsWith("%blur%")) {
               this.field_78361_b = true;
               this.func_78351_a(this.func_78345_a(var3.func_77532_a(p_78341_1_.substring(6))), var4);
               this.field_78361_b = false;
            } else if(p_78341_1_.startsWith("%blurclamp%")) {
               this.field_78361_b = true;
               this.field_78363_a = true;
               this.func_78351_a(this.func_78345_a(var3.func_77532_a(p_78341_1_.substring(11))), var4);
               this.field_78361_b = false;
               this.field_78363_a = false;
            } else {
               InputStream var8 = var3.func_77532_a(p_78341_1_);
               if(var8 == null) {
                  this.func_78351_a(this.field_78364_l, var4);
               } else {
                  if(p_78341_1_.equals("/terrain.png")) {
                     this.terrainTextureId = var4;
                  }

                  if(p_78341_1_.equals("/gui/items.png")) {
                     this.guiItemsTextureId = var4;
                  }

                  TextureUtils.textureCreated(p_78341_1_, var4);
                  BufferedImage var6 = this.func_78345_a(var8);
                  var6 = TextureUtils.fixTextureDimensions(p_78341_1_, var6);
                  this.func_78351_a(var6, var4);
               }
            }

            this.field_78362_c.put(p_78341_1_, Integer.valueOf(var4));
            if(Reflector.ForgeHooksClient.exists()) {
               Reflector.callVoid(Reflector.ForgeHooksClient_onTextureLoad, new Object[]{p_78341_1_, var3});
            }

            return var4;
         } catch (Exception var7) {
            var7.printStackTrace();
            GLAllocation.func_74528_a(this.field_78357_f);
            int var5 = this.field_78357_f.get(0);
            this.func_78351_a(this.field_78364_l, var5);
            this.field_78362_c.put(p_78341_1_, Integer.valueOf(var5));
            return var5;
         }
      }
   }

   private BufferedImage func_78354_c(BufferedImage p_78354_1_) {
      int var2 = p_78354_1_.getWidth() / 16;
      BufferedImage var3 = new BufferedImage(16, p_78354_1_.getHeight() * var2, 2);
      Graphics var4 = var3.getGraphics();

      for(int var5 = 0; var5 < var2; ++var5) {
         var4.drawImage(p_78354_1_, -var5 * 16, var5 * p_78354_1_.getHeight(), (ImageObserver)null);
      }

      var4.dispose();
      return var3;
   }

   public int func_78353_a(BufferedImage p_78353_1_) {
      this.field_78357_f.clear();
      GLAllocation.func_74528_a(this.field_78357_f);
      int var2 = this.field_78357_f.get(0);
      this.func_78351_a(p_78353_1_, var2);
      this.field_78360_e.func_76038_a(var2, p_78353_1_);
      return var2;
   }

   public void func_78351_a(BufferedImage p_78351_1_, int p_78351_2_) {
      GL11.glBindTexture(3553, p_78351_2_);
      boolean var3 = useMipmaps && Config.isUseMipmaps();
      int var4;
      int var5;
      if(var3 && p_78351_2_ != this.guiItemsTextureId) {
         var4 = Config.getMipmapType();
         GL11.glTexParameteri(3553, 10241, var4);
         GL11.glTexParameteri(3553, 10240, 9728);
         if(GLContext.getCapabilities().OpenGL12) {
            GL11.glTexParameteri(3553, '\u813c', 0);
            var5 = Config.getMipmapLevel();
            if(var5 >= 4) {
               int var6 = Math.min(p_78351_1_.getWidth(), p_78351_1_.getHeight());
               var5 = this.getMaxMipmapLevel(var6);
               if(!this.singleTileTexture) {
                  var5 -= 4;
               }

               if(var5 < 0) {
                  var5 = 0;
               }
            }

            GL11.glTexParameteri(3553, '\u813d', var5);
         }

         if(GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            FloatBuffer var21 = BufferUtils.createFloatBuffer(16);
            var21.rewind();
            GL11.glGetFloat('\u84ff', var21);
            float var20 = var21.get(0);
            float var7 = (float)Config.getAnisotropicFilterLevel();
            var7 = Math.min(var7, var20);
            GL11.glTexParameterf(3553, '\u84fe', var7);
         }
      } else {
         GL11.glTexParameteri(3553, 10241, 9728);
         GL11.glTexParameteri(3553, 10240, 9728);
      }

      if(this.field_78361_b) {
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
      }

      if(this.field_78363_a) {
         GL11.glTexParameteri(3553, 10242, 10496);
         GL11.glTexParameteri(3553, 10243, 10496);
      } else {
         GL11.glTexParameteri(3553, 10242, 10497);
         GL11.glTexParameteri(3553, 10243, 10497);
      }

      var4 = p_78351_1_.getWidth();
      var5 = p_78351_1_.getHeight();
      this.setTextureDimension(p_78351_2_, new Dimension(var4, var5));
      if(Reflector.FMLRender.exists()) {
         Reflector.callVoid(Reflector.FMLRender_setTextureDimensions, new Object[]{Integer.valueOf(p_78351_2_), Integer.valueOf(var4), Integer.valueOf(var5), this.field_78367_h});
      }

      int[] var19 = new int[var4 * var5];
      byte[] var22 = new byte[var4 * var5 * 4];
      p_78351_1_.getRGB(0, 0, var4, var5, var19, 0, var4);
      int[] var8 = null;
      int var9;
      int var10;
      if(var3) {
         if(TextureUtils.isAtlasId(p_78351_2_)) {
            var8 = new int[256];

            for(var9 = 0; var9 < 16; ++var9) {
               for(var10 = 0; var10 < 16; ++var10) {
                  var8[var9 * 16 + var10] = this.getAverageOpaqueColor(var19, var10, var9, var4, var5);
               }
            }
         }

         if(this.singleTileTexture) {
            var8 = new int[]{this.getAverageOpaqueColor(var19)};
         }
      }

      int var11;
      int var12;
      int var13;
      int var14;
      int var15;
      int var17;
      for(var9 = 0; var9 < var19.length; ++var9) {
         var10 = var19[var9] >> 24 & 255;
         var11 = var19[var9] >> 16 & 255;
         var12 = var19[var9] >> 8 & 255;
         var13 = var19[var9] & 255;
         int var16;
         if(this.field_78365_j != null && this.field_78365_j.field_74337_g) {
            var14 = (var11 * 30 + var12 * 59 + var13 * 11) / 100;
            var15 = (var11 * 30 + var12 * 70) / 100;
            var16 = (var11 * 30 + var13 * 70) / 100;
            var11 = var14;
            var12 = var15;
            var13 = var16;
         }

         if(var10 == 0) {
            var11 = 0;
            var12 = 0;
            var13 = 0;
            if(TextureUtils.isAtlasId(p_78351_2_) || this.singleTileTexture) {
               var11 = 255;
               var12 = 255;
               var13 = 255;
               if(var3) {
                  boolean var24 = false;
                  if(this.singleTileTexture) {
                     var14 = var8[0];
                  } else {
                     var15 = var9 % var4;
                     var16 = var9 / var4;
                     var17 = var15 / (var4 / 16);
                     int var18 = var16 / (var5 / 16);
                     var14 = var8[var18 * 16 + var17];
                  }

                  if(var14 != 0) {
                     var11 = var14 >> 16 & 255;
                     var12 = var14 >> 8 & 255;
                     var13 = var14 & 255;
                  }
               }
            }
         }

         var22[var9 * 4 + 0] = (byte)var11;
         var22[var9 * 4 + 1] = (byte)var12;
         var22[var9 * 4 + 2] = (byte)var13;
         var22[var9 * 4 + 3] = (byte)var10;
      }

      this.checkImageDataSize(var4, var5);
      this.field_78358_g.clear();
      this.field_78358_g.put(var22);
      this.field_78358_g.position(0).limit(var22.length);
      GL11.glTexImage2D(3553, 0, 6408, var4, var5, 0, 6408, 5121, this.field_78358_g);
      if(var3) {
         this.generateMipMaps(this.field_78358_g, var4, var5);
      }

      if(Config.isMultiTexture() && TextureUtils.isAtlasId(p_78351_2_)) {
         int[] var23 = Tessellator.getTileTextures(p_78351_2_);
         if(var23 == null) {
            var23 = new int[256];
         }

         var10 = var4 / 16;
         var11 = var5 / 16;

         for(var12 = 0; var12 < 16; ++var12) {
            for(var13 = 0; var13 < 16; ++var13) {
               var14 = var13 * var10;
               var15 = var12 * var11;
               BufferedImage var25 = p_78351_1_.getSubimage(var14, var15, var10, var11);
               var17 = var12 * 16 + var13;
               if(var23[var17] <= 0) {
                  this.field_78357_f.clear();
                  GLAllocation.func_74528_a(this.field_78357_f);
                  var23[var17] = this.field_78357_f.get(0);
               }

               this.field_78363_a = this.isTileClamped(p_78351_2_, var17);
               this.singleTileTexture = true;
               this.func_78351_a(var25, var23[var17]);
               this.singleTileTexture = false;
            }
         }

         this.field_78363_a = false;
         Tessellator.setTileTextures(p_78351_2_, var23);
      }

   }

   public void func_78349_a(int[] p_78349_1_, int p_78349_2_, int p_78349_3_, int p_78349_4_) {
      GL11.glBindTexture(3553, p_78349_4_);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      if(this.field_78361_b) {
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
      }

      if(this.field_78363_a) {
         GL11.glTexParameteri(3553, 10242, 10496);
         GL11.glTexParameteri(3553, 10243, 10496);
      } else {
         GL11.glTexParameteri(3553, 10242, 10497);
         GL11.glTexParameteri(3553, 10243, 10497);
      }

      byte[] var5 = new byte[p_78349_2_ * p_78349_3_ * 4];

      for(int var6 = 0; var6 < p_78349_1_.length; ++var6) {
         int var7 = p_78349_1_[var6] >> 24 & 255;
         int var8 = p_78349_1_[var6] >> 16 & 255;
         int var9 = p_78349_1_[var6] >> 8 & 255;
         int var10 = p_78349_1_[var6] & 255;
         if(this.field_78365_j != null && this.field_78365_j.field_74337_g) {
            int var11 = (var8 * 30 + var9 * 59 + var10 * 11) / 100;
            int var12 = (var8 * 30 + var9 * 70) / 100;
            int var13 = (var8 * 30 + var10 * 70) / 100;
            var8 = var11;
            var9 = var12;
            var10 = var13;
         }

         var5[var6 * 4 + 0] = (byte)var8;
         var5[var6 * 4 + 1] = (byte)var9;
         var5[var6 * 4 + 2] = (byte)var10;
         var5[var6 * 4 + 3] = (byte)var7;
      }

      this.field_78358_g.clear();
      this.field_78358_g.put(var5);
      this.field_78358_g.position(0).limit(var5.length);
      GL11.glTexSubImage2D(3553, 0, 0, 0, p_78349_2_, p_78349_3_, 6408, 5121, this.field_78358_g);
   }

   public void func_78344_a(int p_78344_1_) {
      this.field_78360_e.func_76049_d(p_78344_1_);
      this.field_78357_f.clear();
      this.field_78357_f.put(p_78344_1_);
      this.field_78357_f.flip();
      GL11.glDeleteTextures(this.field_78357_f);
   }

   public int func_78350_a(String p_78350_1_, String p_78350_2_) {
      if(Config.isRandomMobs()) {
         int var3 = RandomMobs.getTexture(p_78350_1_, p_78350_2_);
         if(var3 >= 0) {
            return var3;
         }
      }

      ThreadDownloadImageData var4 = (ThreadDownloadImageData)this.field_78368_i.get(p_78350_1_);
      if(var4 != null && var4.field_78462_a != null && !var4.field_78459_d) {
         if(var4.field_78461_c < 0) {
            var4.field_78461_c = this.func_78353_a(var4.field_78462_a);
         } else {
            this.func_78351_a(var4.field_78462_a, var4.field_78461_c);
         }

         var4.field_78459_d = true;
      }

      return var4 != null && var4.field_78461_c >= 0?var4.field_78461_c:(p_78350_2_ == null?-1:this.func_78341_b(p_78350_2_));
   }

   public boolean func_82773_c(String p_82773_1_) {
      return this.field_78368_i.containsKey(p_82773_1_);
   }

   public ThreadDownloadImageData func_78356_a(String p_78356_1_, IImageBuffer p_78356_2_) {
      if(p_78356_1_ != null && p_78356_1_.length() > 0 && Character.isDigit(p_78356_1_.charAt(0))) {
         return null;
      } else {
         ThreadDownloadImageData var3 = (ThreadDownloadImageData)this.field_78368_i.get(p_78356_1_);
         if(var3 == null) {
            this.field_78368_i.put(p_78356_1_, new ThreadDownloadImageData(p_78356_1_, p_78356_2_));
         } else {
            ++var3.field_78460_b;
         }

         return var3;
      }
   }

   public void func_78347_c(String p_78347_1_) {
      ThreadDownloadImageData var2 = (ThreadDownloadImageData)this.field_78368_i.get(p_78347_1_);
      if(var2 != null) {
         --var2.field_78460_b;
         if(var2.field_78460_b == 0) {
            if(var2.field_78461_c >= 0) {
               this.func_78344_a(var2.field_78461_c);
            }

            this.field_78368_i.remove(p_78347_1_);
         }
      }

   }

   public void func_78355_a(TextureFX p_78355_1_) {
      if(Reflector.FMLRender.exists()) {
         Reflector.callVoid(Reflector.FMLRender_preRegisterEffect, new Object[]{p_78355_1_});
      }

      int var2 = this.getTextureId(p_78355_1_);

      for(int var3 = 0; var3 < this.field_78367_h.size(); ++var3) {
         TextureFX var4 = (TextureFX)this.field_78367_h.get(var3);
         int var5 = this.getTextureId(var4);
         if(var5 == var2 && var4.field_76850_b == p_78355_1_.field_76850_b) {
            this.field_78367_h.remove(var3);
            --var3;
            Config.log("TextureFX removed: " + var4 + ", texId: " + var5 + ", index: " + var4.field_76850_b);
         }
      }

      if(p_78355_1_ instanceof TextureHDFX) {
         TextureHDFX var7 = (TextureHDFX)p_78355_1_;
         var7.setTexturePackBase(this.field_78366_k.func_77292_e());
         Dimension var6 = this.getTextureDimensions(var2);
         if(var6 != null) {
            var7.setTileWidth(var6.width / 16);
         }
      }

      this.field_78367_h.add(p_78355_1_);
      p_78355_1_.func_76846_a();
      Config.log("TextureFX registered: " + p_78355_1_ + ", texId: " + var2 + ", index: " + p_78355_1_.field_76850_b);
      this.dynamicTexturesUpdated = false;
   }

   public void func_78343_a() {
      boolean var1 = useMipmaps && Config.isUseMipmaps();
      this.checkHdTextures();
      ++this.tickCounter;
      if(this.terrainTextureId < 0) {
         this.terrainTextureId = this.func_78341_b("/terrain.png");
      }

      if(this.guiItemsTextureId < 0) {
         this.guiItemsTextureId = this.func_78341_b("/gui/items.png");
      }

      StringBuffer var2 = new StringBuffer();
      int var3 = -1;

      for(int var4 = 0; var4 < this.field_78367_h.size(); ++var4) {
         TextureFX var5 = (TextureFX)this.field_78367_h.get(var4);
         var5.field_76851_c = this.field_78365_j.field_74337_g;
         if(!var5.getClass().getName().equals("ModTextureStatic") || !this.dynamicTexturesUpdated) {
            int var6 = this.getTextureId(var5);
            Dimension var7 = this.getTextureDimensions(var6);
            if(var7 == null) {
               throw new IllegalArgumentException("Unknown dimensions for texture id: " + var6);
            }

            int var8 = var7.width / 16;
            int var9 = var7.height / 16;
            this.checkImageDataSize(var7.width, var7.height);
            this.field_78358_g.limit(0);
            var2.setLength(0);
            boolean var10 = this.updateCustomTexture(var5, var6, this.field_78358_g, var7.width / 16, var2);
            if(!var10 || this.field_78358_g.limit() > 0) {
               boolean var11;
               if(this.field_78358_g.limit() <= 0) {
                  var11 = this.updateDefaultTexture(var5, var6, this.field_78358_g, var7.width / 16, var2);
                  if(var11 && this.field_78358_g.limit() <= 0) {
                     continue;
                  }
               }

               if(this.field_78358_g.limit() <= 0) {
                  var5.func_76846_a();
                  if(Reflector.FMLRender.exists() && !Reflector.callBoolean(Reflector.FMLRender_onUpdateTextureEffect, new Object[]{var5}) || var5.field_76852_a == null) {
                     continue;
                  }

                  int var26 = var8 * var9 * 4;
                  if(var5.field_76852_a.length == var26) {
                     this.field_78358_g.clear();
                     this.field_78358_g.put(var5.field_76852_a);
                     this.field_78358_g.position(0).limit(var5.field_76852_a.length);
                  } else {
                     this.copyScaled(var5.field_76852_a, this.field_78358_g, var8);
                  }
               }

               if(var6 != var3) {
                  var5.func_76845_a(this);
                  var3 = var6;
               }

               var11 = this.scalesWithFastColor(var5);

               int var12;
               int var13;
               for(var12 = 0; var12 < var5.field_76849_e; ++var12) {
                  for(var13 = 0; var13 < var5.field_76849_e; ++var13) {
                     int var14 = var5.field_76850_b % 16 * var8 + var12 * var8;
                     int var15 = var5.field_76850_b / 16 * var9 + var13 * var9;
                     GL11.glTexSubImage2D(3553, 0, var14, var15, var8, var9, 6408, 5121, this.field_78358_g);
                     if(var1 && var6 != this.guiItemsTextureId) {
                        String var16 = var2.toString();
                        if(var12 == 0 && var13 == 0) {
                           this.generateMipMapsSub(var14, var15, var8, var9, this.field_78358_g, var5.field_76849_e, var11, 0, 0, var16);
                        }
                     }
                  }
               }

               if(Config.isMultiTexture() && (var6 == this.terrainTextureId || var6 == this.guiItemsTextureId)) {
                  for(var12 = 0; var12 < var5.field_76849_e; ++var12) {
                     for(var13 = 0; var13 < var5.field_76849_e; ++var13) {
                        byte var28 = 0;
                        byte var27 = 0;
                        int var29 = var13 * 16 + var12;
                        int[] var17 = Tessellator.getTileTextures(var6);
                        int var18 = var17[var5.field_76850_b + var29];
                        GL11.glBindTexture(3553, var18);
                        var3 = var18;
                        GL11.glTexSubImage2D(3553, 0, var28, var27, var8, var9, 6408, 5121, this.field_78358_g);
                        if(var1) {
                           String var19 = var2.toString();
                           if(var12 == 0 && var13 == 0) {
                              this.generateMipMapsSub(var28, var27, var8, var9, this.field_78358_g, var5.field_76849_e, var11, var6, var5.field_76850_b, var19);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if(this.textureAnimations != null) {
         boolean var20 = this.field_78365_j.ofAnimatedTextures;

         for(int var21 = 0; var21 < this.textureAnimations.length; ++var21) {
            CustomAnimation var22 = this.textureAnimations[var21];
            int var23 = this.func_78341_b(var22.destTexture);
            if(var23 >= 0) {
               Dimension var24 = this.getTextureDimensions(var23);
               if(var24 != null) {
                  this.checkImageDataSize(var24.width, var24.height);
                  this.field_78358_g.limit(0);
                  var2.setLength(0);
                  boolean var25 = var22.updateCustomTexture(this.field_78358_g, var20, this.dynamicTexturesUpdated, var2);
                  if((!var25 || this.field_78358_g.limit() > 0) && this.field_78358_g.limit() > 0) {
                     this.func_78342_b(var23);
                     GL11.glTexSubImage2D(3553, 0, var22.destX, var22.destY, var22.frameWidth, var22.frameHeight, 6408, 5121, this.field_78358_g);
                  }
               }
            }
         }
      }

      this.dynamicTexturesUpdated = true;
   }

   public int func_82772_a(TextureFX p_82772_1_, int p_82772_2_) {
      if(p_82772_1_ instanceof TextureCompassFX) {
         p_82772_1_ = TextureHDCompassFX.instance;
      }

      this.field_78358_g.clear();
      this.field_78358_g.put(((TextureFX)p_82772_1_).field_76852_a);
      this.field_78358_g.position(0).limit(((TextureFX)p_82772_1_).field_76852_a.length);
      if(((TextureFX)p_82772_1_).field_76850_b != p_82772_2_) {
         ((TextureFX)p_82772_1_).func_76845_a(this);
         p_82772_2_ = ((TextureFX)p_82772_1_).field_76850_b;
      }

      boolean var3 = true;
      int var9;
      if(((TextureFX)p_82772_1_).field_76847_f == 0) {
         var9 = Config.getIconWidthTerrain();
      } else {
         var9 = Config.getIconWidthItems();
      }

      int var4 = var9;

      for(int var5 = 0; var5 < ((TextureFX)p_82772_1_).field_76849_e; ++var5) {
         for(int var6 = 0; var6 < ((TextureFX)p_82772_1_).field_76849_e; ++var6) {
            int var7 = ((TextureFX)p_82772_1_).field_76850_b % 16 * var9 + var5 * var9;
            int var8 = ((TextureFX)p_82772_1_).field_76850_b / 16 * var4 + var6 * var4;
            GL11.glTexSubImage2D(3553, 0, var7, var8, var9, var4, 6408, 5121, this.field_78358_g);
         }
      }

      return p_82772_2_;
   }

   public void func_78352_b() {
      this.textureDataMap.clear();
      this.textureFxMap.clear();
      this.dynamicTexturesUpdated = false;
      Config.setTextureUpdateTime(System.currentTimeMillis());
      WrUpdates.finishCurrentUpdate();
      this.mipDataBufsMap.clear();
      this.customAnimationMap.clear();
      ITexturePack var1 = this.field_78366_k.func_77292_e();
      Iterator var2 = this.field_78360_e.func_76039_d().iterator();

      BufferedImage var3;
      while(var2.hasNext()) {
         int var4 = ((Integer)var2.next()).intValue();
         var3 = (BufferedImage)this.field_78360_e.func_76041_a(var4);
         this.func_78351_a(var3, var4);
      }

      ThreadDownloadImageData var9;
      for(var2 = this.field_78368_i.values().iterator(); var2.hasNext(); var9.field_78459_d = false) {
         var9 = (ThreadDownloadImageData)var2.next();
      }

      var2 = this.field_78362_c.keySet().iterator();

      String var5;
      while(var2.hasNext()) {
         var5 = (String)var2.next();

         try {
            if(var5.startsWith("##")) {
               var3 = this.func_78354_c(this.func_78345_a(var1.func_77532_a(var5.substring(2))));
            } else if(var5.startsWith("%clamp%")) {
               this.field_78363_a = true;
               var3 = this.func_78345_a(var1.func_77532_a(var5.substring(7)));
            } else if(var5.startsWith("%blur%")) {
               this.field_78361_b = true;
               var3 = this.func_78345_a(var1.func_77532_a(var5.substring(6)));
            } else if(var5.startsWith("%blurclamp%")) {
               this.field_78361_b = true;
               this.field_78363_a = true;
               var3 = this.func_78345_a(var1.func_77532_a(var5.substring(11)));
            } else {
               var3 = this.func_78345_a(var1.func_77532_a(var5));
            }

            int var6 = ((Integer)this.field_78362_c.get(var5)).intValue();
            this.func_78351_a(var3, var6);
            this.field_78361_b = false;
            this.field_78363_a = false;
         } catch (Exception var8) {
            if(!"input == null!".equals(var8.getMessage())) {
               var8.printStackTrace();
            }
         }
      }

      var2 = this.field_78359_d.keySet().iterator();

      while(var2.hasNext()) {
         var5 = (String)var2.next();

         try {
            if(var5.startsWith("##")) {
               var3 = this.func_78354_c(this.func_78345_a(var1.func_77532_a(var5.substring(2))));
            } else if(var5.startsWith("%clamp%")) {
               this.field_78363_a = true;
               var3 = this.func_78345_a(var1.func_77532_a(var5.substring(7)));
            } else if(var5.startsWith("%blur%")) {
               this.field_78361_b = true;
               var3 = this.func_78345_a(var1.func_77532_a(var5.substring(6)));
            } else {
               var3 = this.func_78345_a(var1.func_77532_a(var5));
            }

            this.func_78340_a(var3, (int[])((int[])this.field_78359_d.get(var5)));
            this.field_78361_b = false;
            this.field_78363_a = false;
         } catch (Exception var7) {
            if(!"input == null!".equals(var7.getMessage())) {
               var7.printStackTrace();
            }
         }
      }

      this.registerCustomTexturesFX();
      CustomColorizer.update(this);
      ConnectedTextures.update(this);
      NaturalTextures.update(this);
      RandomMobs.resetTextures();
      if(Reflector.FMLRender.exists()) {
         Reflector.callVoid(Reflector.FMLRender_onTexturePackChange, new Object[]{this, var1, this.field_78367_h});
      }

      this.func_78343_a();
   }

   private BufferedImage func_78345_a(InputStream p_78345_1_) throws IOException {
      BufferedImage var2 = ImageIO.read(p_78345_1_);
      p_78345_1_.close();
      return var2;
   }

   public void func_78342_b(int p_78342_1_) {
      if(p_78342_1_ >= 0) {
         GL11.glBindTexture(3553, p_78342_1_);
      }

   }

   private void setTextureDimension(int var1, Dimension var2) {
      this.textureDimensionsMap.put(new Integer(var1), var2);
      if(var1 == this.terrainTextureId) {
         Config.setIconWidthTerrain(var2.width / 16);
      }

      if(var1 == this.guiItemsTextureId) {
         Config.setIconWidthItems(var2.width / 16);
      }

      this.updateDinamicTextures(var1, var2);
   }

   public Dimension getTextureDimensions(int var1) {
      Dimension var2 = (Dimension)this.textureDimensionsMap.get(new Integer(var1));
      return var2;
   }

   private void updateDinamicTextures(int var1, Dimension var2) {
      for(int var3 = 0; var3 < this.field_78367_h.size(); ++var3) {
         TextureFX var4 = (TextureFX)this.field_78367_h.get(var3);
         int var5 = this.getTextureId(var4);
         if(var5 == var1 && var4 instanceof TextureHDFX) {
            TextureHDFX var6 = (TextureHDFX)var4;
            var6.setTexturePackBase(this.field_78366_k.func_77292_e());
            var6.setTileWidth(var2.width / 16);
            var6.func_76846_a();
         }
      }

   }

   public boolean updateCustomTexture(TextureFX var1, int var2, ByteBuffer var3, int var4, StringBuffer var5) {
      if(var2 == this.terrainTextureId) {
         if(var1.field_76850_b == Block.field_71943_B.field_72059_bZ) {
            if(Config.isGeneratedWater()) {
               return false;
            }

            return this.updateCustomTexture(var1, "/custom_water_still.png", var3, var4, Config.isAnimatedWater(), 1, var5);
         }

         if(var1.field_76850_b == Block.field_71943_B.field_72059_bZ + 1) {
            if(Config.isGeneratedWater()) {
               return false;
            }

            return this.updateCustomTexture(var1, "/custom_water_flowing.png", var3, var4, Config.isAnimatedWater(), 1, var5);
         }

         if(var1.field_76850_b == Block.field_71938_D.field_72059_bZ) {
            if(Config.isGeneratedLava()) {
               return false;
            }

            return this.updateCustomTexture(var1, "/custom_lava_still.png", var3, var4, Config.isAnimatedLava(), 1, var5);
         }

         if(var1.field_76850_b == Block.field_71938_D.field_72059_bZ + 1) {
            if(Config.isGeneratedLava()) {
               return false;
            }

            return this.updateCustomTexture(var1, "/custom_lava_flowing.png", var3, var4, Config.isAnimatedLava(), 1, var5);
         }

         if(var1.field_76850_b == Block.field_72015_be.field_72059_bZ) {
            return this.updateCustomTexture(var1, "/custom_portal.png", var3, var4, Config.isAnimatedPortal(), 1, var5);
         }

         if(var1.field_76850_b == Block.field_72067_ar.field_72059_bZ) {
            return this.updateCustomTexture(var1, "/custom_fire_n_s.png", var3, var4, Config.isAnimatedFire(), 1, var5);
         }

         if(var1.field_76850_b == Block.field_72067_ar.field_72059_bZ + 16) {
            return this.updateCustomTexture(var1, "/custom_fire_e_w.png", var3, var4, Config.isAnimatedFire(), 1, var5);
         }

         if(Config.isAnimatedTerrain()) {
            return this.updateCustomTexture(var1, "/custom_terrain_" + var1.field_76850_b + ".png", var3, var4, Config.isAnimatedTerrain(), 1, var5);
         }
      }

      return var2 == this.guiItemsTextureId && Config.isAnimatedItems()?this.updateCustomTexture(var1, "/custom_item_" + var1.field_76850_b + ".png", var3, var4, Config.isAnimatedTerrain(), 1, var5):false;
   }

   private boolean updateDefaultTexture(TextureFX var1, int var2, ByteBuffer var3, int var4, StringBuffer var5) {
      return var2 != this.terrainTextureId?false:(this.field_78366_k.func_77292_e() instanceof TexturePackDefault?false:(var1.field_76850_b == Block.field_71943_B.field_72059_bZ?(Config.isGeneratedWater()?false:this.updateDefaultTexture(var1, var3, var4, false, 1, var5)):(var1.field_76850_b == Block.field_71943_B.field_72059_bZ + 1?(Config.isGeneratedWater()?false:this.updateDefaultTexture(var1, var3, var4, Config.isAnimatedWater(), 1, var5)):(var1.field_76850_b == Block.field_71938_D.field_72059_bZ?(Config.isGeneratedLava()?false:this.updateDefaultTexture(var1, var3, var4, false, 1, var5)):(var1.field_76850_b == Block.field_71938_D.field_72059_bZ + 1?(Config.isGeneratedLava()?false:this.updateDefaultTexture(var1, var3, var4, Config.isAnimatedLava(), 3, var5)):false)))));
   }

   private boolean updateDefaultTexture(TextureFX var1, ByteBuffer var2, int var3, boolean var4, int var5, StringBuffer var6) {
      int var7 = var1.field_76850_b;
      if(!var4 && this.dynamicTexturesUpdated) {
         return true;
      } else {
         byte[] var8 = this.getTerrainIconData(var7, var3, var6);
         if(var8 == null) {
            return false;
         } else {
            var2.clear();
            int var9 = var8.length;
            if(var4) {
               int var10 = var3 - this.tickCounter / var5 % var3;
               int var11 = var10 * var3 * 4;
               var2.put(var8, var11, var9 - var11);
               var2.put(var8, 0, var11);
               var6.append(":");
               var6.append(var10);
            } else {
               var2.put(var8, 0, var9);
            }

            var2.position(0).limit(var9);
            return true;
         }
      }
   }

   private boolean updateCustomTexture(TextureFX var1, String var2, ByteBuffer var3, int var4, boolean var5, int var6, StringBuffer var7) {
      CustomAnimation var9 = this.getCustomAnimation(var2, var4, var4, var6);
      return var9 == null?false:var9.updateCustomTexture(var3, var5, this.dynamicTexturesUpdated, var7);
   }

   private CustomAnimation getCustomAnimation(String var1, int var2, int var3, int var4) {
      CustomAnimation var5 = (CustomAnimation)this.customAnimationMap.get(var1);
      if(var5 == null) {
         if(this.customAnimationMap.containsKey(var1)) {
            return null;
         }

         byte[] var6 = this.getCustomTextureData(var1, var2);
         if(var6 == null) {
            this.customAnimationMap.put(var1, (Object)null);
            return null;
         }

         Properties var7 = new Properties();
         String var8 = this.makePropertiesName(var1);
         if(var8 != null) {
            try {
               InputStream var9 = this.field_78366_k.func_77292_e().func_77532_a(var8);
               if(var9 == null) {
                  var9 = this.field_78366_k.func_77292_e().func_77532_a("/anim" + var8);
               }

               if(var9 != null) {
                  var7.load(var9);
               }
            } catch (IOException var10) {
               var10.printStackTrace();
            }
         }

         var5 = new CustomAnimation(var1, var6, var2, var3, var7, var4);
         this.customAnimationMap.put(var1, var5);
      }

      return var5;
   }

   private String makePropertiesName(String var1) {
      if(!var1.endsWith(".png")) {
         return null;
      } else {
         int var2 = var1.lastIndexOf(".png");
         if(var2 < 0) {
            return null;
         } else {
            String var3 = var1.substring(0, var2) + ".properties";
            return var3;
         }
      }
   }

   private byte[] getTerrainIconData(int var1, int var2, StringBuffer var3) {
      String var4 = "Tile-" + var1;
      byte[] var5 = this.getCustomTextureData(var4, var2);
      if(var5 != null) {
         var3.append(var4);
         return var5;
      } else {
         byte[] var6 = this.getCustomTextureData("/terrain.png", var2 * 16);
         if(var6 == null) {
            return null;
         } else {
            var5 = new byte[var2 * var2 * 4];
            int var7 = var1 % 16;
            int var8 = var1 / 16;
            int var9 = var7 * var2;
            int var10 = var8 * var2;
            int var10000 = var9 + var2;
            var10000 = var10 + var2;

            for(int var13 = 0; var13 < var2; ++var13) {
               int var14 = var10 + var13;

               for(int var15 = 0; var15 < var2; ++var15) {
                  int var16 = var9 + var15;
                  int var17 = 4 * (var16 + var14 * var2 * 16);
                  int var18 = 4 * (var15 + var13 * var2);
                  var5[var18 + 0] = var6[var17 + 0];
                  var5[var18 + 1] = var6[var17 + 1];
                  var5[var18 + 2] = var6[var17 + 2];
                  var5[var18 + 3] = var6[var17 + 3];
               }
            }

            this.setCustomTextureData(var4, var5);
            var3.append(var4);
            return var5;
         }
      }
   }

   public byte[] getCustomTextureData(String var1, int var2) {
      byte[] var3 = (byte[])((byte[])this.textureDataMap.get(var1));
      if(var3 == null) {
         if(this.textureDataMap.containsKey(var1)) {
            return null;
         }

         var3 = this.loadImage(var1, var2);
         if(var3 == null) {
            var3 = this.loadImage("/anim" + var1, var2);
         }

         this.textureDataMap.put(var1, var3);
      }

      return var3;
   }

   private void setCustomTextureData(String var1, byte[] var2) {
      this.textureDataMap.put(var1, var2);
   }

   private byte[] loadImage(String var1, int var2) {
      try {
         ITexturePack var3 = this.field_78366_k.func_77292_e();
         if(var3 == null) {
            return null;
         } else {
            InputStream var4 = var3.func_77532_a(var1);
            if(var4 == null) {
               return null;
            } else {
               BufferedImage var5 = this.func_78345_a(var4);
               if(var5 == null) {
                  return null;
               } else {
                  if(var2 > 0 && var5.getWidth() != var2) {
                     double var6 = (double)(var5.getHeight() / var5.getWidth());
                     int var8 = (int)((double)var2 * var6);
                     var5 = scaleBufferedImage(var5, var2, var8);
                  }

                  int var19 = var5.getWidth();
                  int var7 = var5.getHeight();
                  int[] var20 = new int[var19 * var7];
                  byte[] var9 = new byte[var19 * var7 * 4];
                  var5.getRGB(0, 0, var19, var7, var20, 0, var19);

                  for(int var10 = 0; var10 < var20.length; ++var10) {
                     int var11 = var20[var10] >> 24 & 255;
                     int var12 = var20[var10] >> 16 & 255;
                     int var13 = var20[var10] >> 8 & 255;
                     int var14 = var20[var10] & 255;
                     if(this.field_78365_j != null && this.field_78365_j.field_74337_g) {
                        int var15 = (var12 * 30 + var13 * 59 + var14 * 11) / 100;
                        int var16 = (var12 * 30 + var13 * 70) / 100;
                        int var17 = (var12 * 30 + var14 * 70) / 100;
                        var12 = var15;
                        var13 = var16;
                        var14 = var17;
                     }

                     var9[var10 * 4 + 0] = (byte)var12;
                     var9[var10 * 4 + 1] = (byte)var13;
                     var9[var10 * 4 + 2] = (byte)var14;
                     var9[var10 * 4 + 3] = (byte)var11;
                  }

                  return var9;
               }
            }
         }
      } catch (Exception var18) {
         var18.printStackTrace();
         return null;
      }
   }

   public static BufferedImage scaleBufferedImage(BufferedImage var0, int var1, int var2) {
      BufferedImage var3 = new BufferedImage(var1, var2, 2);
      Graphics2D var4 = var3.createGraphics();
      var4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      var4.drawImage(var0, 0, 0, var1, var2, (ImageObserver)null);
      return var3;
   }

   private void checkImageDataSize(int var1, int var2) {
      if(this.field_78358_g != null) {
         int var3 = var1 * var2 * 4;
         if(this.field_78358_g.capacity() >= var3) {
            return;
         }
      }

      this.allocateImageData(var1, var2);
   }

   private void allocateImageData(int var1, int var2) {
      var1 = this.powerOfTwo(var1);
      var2 = this.powerOfTwo(var2);
      int var3 = var1 * var2 * 4;
      this.field_78358_g = GLAllocation.func_74524_c(var3);
      ArrayList var4 = new ArrayList();
      int var5 = var1 / 2;

      for(int var6 = var2 / 2; var5 > 0 && var6 > 0; var6 /= 2) {
         int var7 = var5 * var6 * 4;
         ByteBuffer var8 = GLAllocation.func_74524_c(var7);
         var4.add(var8);
         var5 /= 2;
      }

      this.mipImageDatas = (ByteBuffer[])((ByteBuffer[])var4.toArray(new ByteBuffer[var4.size()]));
   }

   private int powerOfTwo(int var1) {
      int var2;
      for(var2 = 1; var2 < var1; var2 *= 2) {
         ;
      }

      return var2;
   }

   public void checkHdTextures() {
      if(!this.hdTexturesInstalled) {
         Minecraft var1 = Config.getMinecraft();
         if(var1 != null) {
            this.hdTexturesInstalled = true;
            this.func_78355_a(new TextureHDLavaFX());
            this.func_78355_a(new TextureHDWaterFX());
            this.func_78355_a(new TextureHDPortalFX());
            this.func_78355_a(new TextureHDWaterFlowFX());
            this.func_78355_a(new TextureHDLavaFlowFX());
            this.func_78355_a(new TextureHDFlamesFX(0));
            this.func_78355_a(new TextureHDFlamesFX(1));
            this.func_78355_a(new TextureHDCompassFX(var1));
            this.func_78355_a(new TextureHDWatchFX(var1));
            this.registerCustomTexturesFX();
            CustomColorizer.update(this);
            ConnectedTextures.update(this);
            NaturalTextures.update(this);
         }
      }
   }

   private void registerCustomTexturesFX() {
      TextureFX[] var1 = this.getRegisteredTexturesFX(TextureHDCustomFX.class);

      int var2;
      for(var2 = 0; var2 < var1.length; ++var2) {
         TextureFX var3 = var1[var2];
         this.unregisterTextureFX(var3);
      }

      if(Config.isAnimatedTerrain()) {
         for(var2 = 0; var2 < 256; ++var2) {
            this.registerCustomTextureFX("/custom_terrain_" + var2 + ".png", var2, 0);
         }
      }

      if(Config.isAnimatedItems()) {
         for(var2 = 0; var2 < 256; ++var2) {
            this.registerCustomTextureFX("/custom_item_" + var2 + ".png", var2, 1);
         }
      }

      this.textureAnimations = this.getTextureAnimations();
   }

   private CustomAnimation[] getTextureAnimations() {
      ITexturePack var1 = this.field_78366_k.func_77292_e();
      if(!(var1 instanceof TexturePackImplementation)) {
         return null;
      } else {
         TexturePackImplementation var2 = (TexturePackImplementation)var1;
         File var3 = var2.field_77548_a;
         if(var3 == null) {
            return null;
         } else if(!var3.exists()) {
            return null;
         } else {
            Properties[] var4 = null;
            if(var3.isFile()) {
               var4 = this.getAnimationPropertiesZip(var3);
            } else {
               var4 = this.getAnimationPropertiesDir(var3);
            }

            if(var4 == null) {
               return null;
            } else {
               ArrayList var5 = new ArrayList();

               for(int var6 = 0; var6 < var4.length; ++var6) {
                  Properties var7 = var4[var6];
                  CustomAnimation var8 = this.makeTextureAnimation(var7);
                  if(var8 != null) {
                     var5.add(var8);
                  }
               }

               CustomAnimation[] var9 = (CustomAnimation[])((CustomAnimation[])var5.toArray(new CustomAnimation[var5.size()]));
               return var9;
            }
         }
      }
   }

   private CustomAnimation makeTextureAnimation(Properties var1) {
      String var2 = var1.getProperty("from");
      String var3 = var1.getProperty("to");
      int var4 = Config.parseInt(var1.getProperty("x"), -1);
      int var5 = Config.parseInt(var1.getProperty("y"), -1);
      int var6 = Config.parseInt(var1.getProperty("w"), -1);
      int var7 = Config.parseInt(var1.getProperty("h"), -1);
      if(var2 != null && var3 != null) {
         if(var4 >= 0 && var5 >= 0 && var6 >= 0 && var7 >= 0) {
            byte[] var8 = this.getCustomTextureData(var2, var6);
            if(var8 == null) {
               return null;
            } else {
               CustomAnimation var9 = new CustomAnimation(var2, var8, var6, var7, var1, 1);
               var9.destTexture = var3;
               var9.destX = var4;
               var9.destY = var5;
               return var9;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private Properties[] getAnimationPropertiesDir(File var1) {
      File var2 = new File(var1, "anim");
      if(!var2.exists()) {
         return null;
      } else if(!var2.isDirectory()) {
         return null;
      } else {
         File[] var3 = var2.listFiles();
         if(var3 == null) {
            return null;
         } else {
            try {
               ArrayList var4 = new ArrayList();

               for(int var5 = 0; var5 < var3.length; ++var5) {
                  File var6 = var3[var5];
                  String var7 = var6.getName();
                  if(!var7.startsWith("custom_") && var7.endsWith(".properties") && var6.isFile() && var6.canRead()) {
                     FileInputStream var8 = new FileInputStream(var6);
                     Properties var9 = new Properties();
                     var9.load(var8);
                     var8.close();
                     var4.add(var9);
                  }
               }

               Properties[] var11 = (Properties[])((Properties[])var4.toArray(new Properties[var4.size()]));
               return var11;
            } catch (IOException var10) {
               var10.printStackTrace();
               return null;
            }
         }
      }
   }

   private Properties[] getAnimationPropertiesZip(File var1) {
      try {
         ZipFile var2 = new ZipFile(var1);
         Enumeration var3 = var2.entries();
         ArrayList var4 = new ArrayList();

         while(var3.hasMoreElements()) {
            ZipEntry var5 = (ZipEntry)var3.nextElement();
            String var6 = var5.getName();
            if(var6.startsWith("anim/") && !var6.startsWith("anim/custom_") && var6.endsWith(".properties")) {
               InputStream var7 = var2.getInputStream(var5);
               Properties var8 = new Properties();
               var8.load(var7);
               var7.close();
               var4.add(var8);
            }
         }

         Properties[] var10 = (Properties[])((Properties[])var4.toArray(new Properties[var4.size()]));
         return var10;
      } catch (IOException var9) {
         var9.printStackTrace();
         return null;
      }
   }

   private void unregisterTextureFX(TextureFX var1) {
      for(int var2 = 0; var2 < this.field_78367_h.size(); ++var2) {
         TextureFX var3 = (TextureFX)this.field_78367_h.get(var2);
         if(var3 == var1) {
            this.field_78367_h.remove(var2);
            --var2;
         }
      }

   }

   private TextureFX[] getRegisteredTexturesFX(Class var1) {
      ArrayList var2 = new ArrayList();

      for(int var3 = 0; var3 < this.field_78367_h.size(); ++var3) {
         TextureFX var4 = (TextureFX)this.field_78367_h.get(var3);
         if(var1.isAssignableFrom(var4.getClass())) {
            var2.add(var4);
         }
      }

      TextureFX[] var5 = (TextureFX[])((TextureFX[])var2.toArray(new TextureFX[var2.size()]));
      return var5;
   }

   private void registerCustomTextureFX(String var1, int var2, int var3) {
      Object var4 = null;
      byte[] var5;
      if(var3 == 0) {
         var5 = this.getCustomTextureData(var1, Config.getIconWidthTerrain());
      } else {
         var5 = this.getCustomTextureData(var1, Config.getIconWidthItems());
      }

      if(var5 != null) {
         this.func_78355_a(new TextureHDCustomFX(var2, var3));
      }
   }

   private int getMaxMipmapLevel(int var1) {
      int var2;
      for(var2 = 0; var1 > 0; ++var2) {
         var1 /= 2;
      }

      return var2 - 1;
   }

   private void copyScaled(byte[] var1, ByteBuffer var2, int var3) {
      int var4 = (int)Math.sqrt((double)(var1.length / 4));
      int var5 = var3 / var4;
      byte[] var6 = new byte[4];
      int var7 = var3 * var3;
      var2.clear();
      if(var5 > 1) {
         for(int var8 = 0; var8 < var4; ++var8) {
            int var9 = var8 * var4;
            int var10 = var8 * var5;
            int var11 = var10 * var3;

            for(int var12 = 0; var12 < var4; ++var12) {
               int var13 = (var12 + var9) * 4;
               var6[0] = var1[var13];
               var6[1] = var1[var13 + 1];
               var6[2] = var1[var13 + 2];
               var6[3] = var1[var13 + 3];
               int var14 = var12 * var5;
               int var15 = var14 + var11;

               for(int var16 = 0; var16 < var5; ++var16) {
                  int var17 = var15 + var16 * var3;
                  var2.position(var17 * 4);

                  for(int var18 = 0; var18 < var5; ++var18) {
                     var2.put(var6);
                  }
               }
            }
         }
      }

      var2.position(0).limit(var3 * var3 * 4);
   }

   private boolean scalesWithFastColor(TextureFX var1) {
      return !var1.getClass().getName().equals("ModTextureStatic");
   }

   private boolean isTileClamped(int var1, int var2) {
      return var1 != this.terrainTextureId || !Config.between(var2, 0, 2) && !Config.between(var2, 4, 10) && !Config.between(var2, 16, 21) && !Config.between(var2, 32, 37) && !Config.between(var2, 40, 40) && !Config.between(var2, 48, 53) && !Config.between(var2, 64, 67) && !Config.between(var2, 69, 75) && !Config.between(var2, 86, 87) && !Config.between(var2, 102, 107) && !Config.between(var2, 109, 110) && !Config.between(var2, 113, 114) && !Config.between(var2, 116, 121) && !Config.between(var2, 129, 133) && !Config.between(var2, 144, 147) && !Config.between(var2, 160, 165) && !Config.between(var2, 176, 181) && !Config.between(var2, 192, 195) && !Config.between(var2, 205, 207) && !Config.between(var2, 208, 210) && !Config.between(var2, 222, 223) && !Config.between(var2, 225, 225) && !Config.between(var2, 237, 239) && !Config.between(var2, 240, 249) && !Config.between(var2, 254, 255);
   }

   private void generateMipMapsSub(int var1, int var2, int var3, int var4, ByteBuffer var5, int var6, boolean var7, int var8, int var9, String var10) {
      ByteBuffer var11 = var5;
      byte[][] var12 = (byte[][])null;
      if(var10.length() > 0) {
         var12 = (byte[][])((byte[][])this.mipDataBufsMap.get(var10));
         if(var12 == null) {
            var12 = new byte[17][];
            this.mipDataBufsMap.put(var10, var12);
         }
      }

      for(int var13 = 1; var13 <= 16; ++var13) {
         int var14 = var3 >> var13 - 1;
         int var15 = var3 >> var13;
         int var16 = var4 >> var13;
         int var17 = var1 >> var13;
         int var18 = var2 >> var13;
         if(var15 <= 0 || var16 <= 0) {
            break;
         }

         ByteBuffer var19 = this.mipImageDatas[var13 - 1];
         var19.limit(var15 * var16 * 4);
         byte[] var20 = null;
         if(var12 != null) {
            var20 = var12[var13];
         }

         if(var20 != null && var20.length != var15 * var16 * 4) {
            var20 = null;
         }

         int var21;
         int var23;
         int var22;
         int var25;
         int var24;
         if(var20 == null) {
            if(var12 != null) {
               var20 = new byte[var15 * var16 * 4];
            }

            for(var21 = 0; var21 < var15; ++var21) {
               for(var22 = 0; var22 < var16; ++var22) {
                  var23 = var11.getInt((var21 * 2 + 0 + (var22 * 2 + 0) * var14) * 4);
                  var24 = var11.getInt((var21 * 2 + 1 + (var22 * 2 + 0) * var14) * 4);
                  var25 = var11.getInt((var21 * 2 + 1 + (var22 * 2 + 1) * var14) * 4);
                  int var26 = var11.getInt((var21 * 2 + 0 + (var22 * 2 + 1) * var14) * 4);
                  int var27;
                  if(var7) {
                     var27 = this.averageColor(this.averageColor(var23, var24), this.averageColor(var25, var26));
                  } else {
                     var27 = this.alphaBlend(var23, var24, var25, var26);
                  }

                  var19.putInt((var21 + var22 * var15) * 4, var27);
               }
            }

            if(var12 != null) {
               var19.rewind();
               var19.get(var20);
               var12[var13] = var20;
            }
         }

         if(var20 != null) {
            var19.rewind();
            var19.put(var20);
         }

         var19.rewind();

         for(var21 = 0; var21 < var6; ++var21) {
            for(var22 = 0; var22 < var6; ++var22) {
               var23 = var21 * var15;
               var24 = var22 * var16;
               if(Config.isMultiTexture() && var8 == this.terrainTextureId) {
                  var25 = var22 * 16 + var21;
                  GL11.glBindTexture(3553, Tessellator.getTileTextures(this.terrainTextureId)[var9 + var25]);
                  var23 = 0;
                  var24 = 0;
               }

               GL11.glTexSubImage2D(3553, var13, var17 + var23, var18 + var24, var15, var16, 6408, 5121, var19);
            }
         }

         var11 = var19;
      }

   }

   private int alphaBlend(int var1, int var2, int var3, int var4) {
      int var5 = this.alphaBlend(var1, var2);
      int var6 = this.alphaBlend(var3, var4);
      int var7 = this.alphaBlend(var5, var6);
      return var7;
   }

   private int alphaBlend(int var1, int var2) {
      int var3 = (var1 & -16777216) >> 24 & 255;
      int var4 = (var2 & -16777216) >> 24 & 255;
      int var5 = (var3 + var4) / 2;
      if(var3 == 0 && var4 == 0) {
         var3 = 1;
         var4 = 1;
      } else {
         if(var3 == 0) {
            var1 = var2;
            var5 /= 2;
         }

         if(var4 == 0) {
            var2 = var1;
            var5 /= 2;
         }
      }

      int var6 = (var1 >> 16 & 255) * var3;
      int var7 = (var1 >> 8 & 255) * var3;
      int var8 = (var1 & 255) * var3;
      int var9 = (var2 >> 16 & 255) * var4;
      int var10 = (var2 >> 8 & 255) * var4;
      int var11 = (var2 & 255) * var4;
      int var12 = (var6 + var9) / (var3 + var4);
      int var13 = (var7 + var10) / (var3 + var4);
      int var14 = (var8 + var11) / (var3 + var4);
      return var5 << 24 | var12 << 16 | var13 << 8 | var14;
   }

   private int averageColor(int var1, int var2) {
      int var3 = (var1 & -16777216) >> 24 & 255;
      int var4 = (var2 & -16777216) >> 24 & 255;
      return (var3 + var4 >> 1 << 24) + ((var1 & 16711422) + (var2 & 16711422) >> 1);
   }

   private int getAverageOpaqueColor(int[] var1) {
      long var2 = 0L;
      long var4 = 0L;
      long var6 = 0L;
      long var8 = 0L;

      int var11;
      int var12;
      int var13;
      for(int var10 = 0; var10 < var1.length; ++var10) {
         var11 = var1[var10];
         var12 = var11 >> 24 & 255;
         if(var12 != 0) {
            var13 = var11 >> 16 & 255;
            int var14 = var11 >> 8 & 255;
            int var15 = var11 & 255;
            var2 += (long)var13;
            var4 += (long)var14;
            var6 += (long)var15;
            ++var8;
         }
      }

      if(var8 <= 0L) {
         return -1;
      } else {
         short var16 = 255;
         var11 = (int)(var2 / var8);
         var12 = (int)(var4 / var8);
         var13 = (int)(var6 / var8);
         return var16 << 24 | var11 << 16 | var12 << 8 | var13;
      }
   }

   private void fixAlpha(BufferedImage var1) {
      long var2 = 0L;
      long var4 = 0L;
      long var6 = 0L;
      long var8 = 0L;
      int var10 = var1.getWidth();
      int var11 = var1.getHeight();

      int var12;
      int var13;
      int var14;
      int var15;
      int var17;
      int var16;
      int var18;
      for(var12 = 0; var12 < var11; ++var12) {
         for(var13 = 0; var13 < var10; ++var13) {
            var14 = var1.getRGB(var13, var12);
            var15 = var14 >> 24 & 255;
            if(var15 != 0) {
               var16 = var14 >> 16 & 255;
               var17 = var14 >> 8 & 255;
               var18 = var14 & 255;
               var2 += (long)var16;
               var4 += (long)var17;
               var6 += (long)var18;
               ++var8;
            }
         }
      }

      if(var8 > 0L) {
         var12 = (int)(var2 / var8);
         var13 = (int)(var4 / var8);
         var14 = (int)(var6 / var8);

         for(var15 = 0; var15 < var11; ++var15) {
            for(var16 = 0; var16 < var10; ++var16) {
               var17 = var1.getRGB(var16, var15);
               var18 = var17 >> 24 & 255;
               if(var18 == 0) {
                  var17 = var18 << 24 | var12 << 16 | var13 << 8 | var14 << 0;
                  var1.setRGB(var16, var15, var17);
               }
            }
         }

      }
   }

   private int getAverageOpaqueColor(int[] var1, int var2, int var3, int var4, int var5) {
      int var6 = var4 / 16;
      int var7 = var5 / 16;
      int var8 = var3 * var7 * var4 + var2 * var6;
      long var9 = 0L;
      long var11 = 0L;
      long var13 = 0L;
      long var15 = 0L;

      int var19;
      int var18;
      int var20;
      for(int var17 = 0; var17 < var7; ++var17) {
         for(var18 = 0; var18 < var6; ++var18) {
            var19 = var8 + var17 * var4 + var18;
            var20 = var1[var19] >> 24 & 255;
            if(var20 != 0) {
               int var21 = var1[var19] >> 16 & 255;
               int var22 = var1[var19] >> 8 & 255;
               int var23 = var1[var19] & 255;
               var9 += (long)var21;
               var11 += (long)var22;
               var13 += (long)var23;
               ++var15;
            }
         }
      }

      if(var15 <= 0L) {
         return 0;
      } else {
         short var24 = 255;
         var18 = (int)(var9 / var15);
         var19 = (int)(var11 / var15);
         var20 = (int)(var13 / var15);
         return var24 << 24 | var18 << 16 | var19 << 8 | var20;
      }
   }

   private void generateMipMaps(ByteBuffer var1, int var2, int var3) {
      ByteBuffer var4 = var1;

      for(int var5 = 1; var5 <= 16; ++var5) {
         int var6 = var2 >> var5 - 1;
         int var7 = var2 >> var5;
         int var8 = var3 >> var5;
         if(var7 <= 0 || var8 <= 0) {
            break;
         }

         ByteBuffer var9 = this.mipImageDatas[var5 - 1];
         var9.limit(var7 * var8 * 4);

         for(int var10 = 0; var10 < var7; ++var10) {
            for(int var11 = 0; var11 < var8; ++var11) {
               int var12 = var4.getInt((var10 * 2 + 0 + (var11 * 2 + 0) * var6) * 4);
               int var13 = var4.getInt((var10 * 2 + 1 + (var11 * 2 + 0) * var6) * 4);
               int var14 = var4.getInt((var10 * 2 + 1 + (var11 * 2 + 1) * var6) * 4);
               int var15 = var4.getInt((var10 * 2 + 0 + (var11 * 2 + 1) * var6) * 4);
               int var16 = this.alphaBlend(var12, var13, var14, var15);
               var9.putInt((var10 + var11 * var7) * 4, var16);
            }
         }

         var9.rewind();
         GL11.glTexImage2D(3553, var5, 6408, var7, var8, 0, 6408, 5121, var9);
         var4 = var9;
      }

   }

   private int getBoundTexture() {
      int var1 = GL11.glGetInteger('\u8069');
      return var1;
   }

   private int getTextureId(TextureFX var1) {
      Integer var2 = (Integer)this.textureFxMap.get(var1);
      if(var2 != null) {
         return var2.intValue();
      } else {
         int var3 = this.getBoundTexture();
         var1.func_76845_a(this);
         int var4 = this.getBoundTexture();
         this.func_78342_b(var3);
         this.textureFxMap.put(var1, new Integer(var4));
         return var4;
      }
   }

   protected BufferedImage readTextureImage(String var1) throws IOException {
      InputStream var2 = this.field_78366_k.func_77292_e().func_77532_a(var1);
      if(var2 == null) {
         return null;
      } else {
         BufferedImage var3 = ImageIO.read(var2);
         var2.close();
         return var3;
      }
   }

   public TexturePackList getTexturePack() {
      return this.field_78366_k;
   }

}
