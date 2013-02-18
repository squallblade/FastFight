package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.Config;
import net.minecraft.src.EmptyChunk;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.EnumOptionsHelper;
import net.minecraft.src.ExtendedBlockStorage;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.IWrUpdater;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.NibbleArray;
import net.minecraft.src.Packet204ClientInfo;
import net.minecraft.src.Reflector;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.StatCollector;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.WrUpdaterSmooth;
import net.minecraft.src.WrUpdaterThreaded;
import net.minecraft.src.WrUpdates;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class GameSettings {

   private static final String[] field_74360_ac = new String[]{"options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny"};
   private static final String[] field_74361_ad = new String[]{"options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard"};
   private static final String[] field_74367_ae = new String[]{"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
   private static final String[] field_74369_af = new String[]{"options.chat.visibility.full", "options.chat.visibility.system", "options.chat.visibility.hidden"};
   private static final String[] field_74364_ag = new String[]{"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
   private static final String[] field_74365_ah = new String[]{"performance.max", "performance.balanced", "performance.powersaver"};
   public float field_74342_a = 1.0F;
   public float field_74340_b = 1.0F;
   public float field_74341_c = 0.5F;
   public boolean field_74338_d = false;
   public int field_74339_e = 0;
   public boolean field_74336_f = true;
   public boolean field_74337_g = false;
   public boolean field_74349_h = false;
   public int field_74350_i = 1;
   public boolean field_74347_j = true;
   public boolean field_74348_k = true;
   public boolean field_74345_l = true;
   public int ofRenderDistanceFine = 128;
   public int ofLimitFramerateFine = 0;
   public int ofFogType = 1;
   public float ofFogStart = 0.8F;
   public int ofMipmapLevel = 0;
   public boolean ofMipmapLinear = false;
   public boolean ofLoadFar = false;
   public int ofPreloadedChunks = 0;
   public boolean ofOcclusionFancy = false;
   public boolean ofSmoothFps = false;
   public boolean ofSmoothWorld = Config.isSingleProcessor();
   public boolean ofLazyChunkLoading = Config.isSingleProcessor();
   public float ofAoLevel = 1.0F;
   public int ofAaLevel = 0;
   public int ofAfLevel = 1;
   public int ofClouds = 0;
   public float ofCloudsHeight = 0.0F;
   public int ofTrees = 0;
   public int ofGrass = 0;
   public int ofRain = 0;
   public int ofWater = 0;
   public int ofDroppedItems = 0;
   public int ofBetterGrass = 3;
   public int ofAutoSaveTicks = 4000;
   public boolean ofLagometer = false;
   public boolean ofProfiler = false;
   public boolean ofWeather = true;
   public boolean ofSky = true;
   public boolean ofStars = true;
   public boolean ofSunMoon = true;
   public int ofChunkUpdates = 1;
   public int ofChunkLoading = 0;
   public boolean ofChunkUpdatesDynamic = false;
   public int ofTime = 0;
   public boolean ofClearWater = false;
   public boolean ofDepthFog = true;
   public boolean ofBetterSnow = false;
   public String ofFullscreenMode = "Default";
   public boolean ofSwampColors = true;
   public boolean ofRandomMobs = true;
   public boolean ofSmoothBiomes = true;
   public boolean ofCustomFonts = true;
   public boolean ofCustomColors = true;
   public boolean ofShowCapes = true;
   public int ofConnectedTextures = 2;
   public boolean ofNaturalTextures = false;
   public int ofAnimatedWater = 0;
   public int ofAnimatedLava = 0;
   public boolean ofAnimatedFire = true;
   public boolean ofAnimatedPortal = true;
   public boolean ofAnimatedRedstone = true;
   public boolean ofAnimatedExplosion = true;
   public boolean ofAnimatedFlame = true;
   public boolean ofAnimatedSmoke = true;
   public boolean ofVoidParticles = true;
   public boolean ofWaterParticles = true;
   public boolean ofRainSplash = true;
   public boolean ofPortalParticles = true;
   public boolean ofDrippingWaterLava = true;
   public boolean ofAnimatedTerrain = true;
   public boolean ofAnimatedItems = true;
   public boolean ofAnimatedTextures = true;
   public static final int DEFAULT = 0;
   public static final int FAST = 1;
   public static final int FANCY = 2;
   public static final int OFF = 3;
   public static final int ANIM_ON = 0;
   public static final int ANIM_GENERATED = 1;
   public static final int ANIM_OFF = 2;
   public static final int CL_DEFAULT = 0;
   public static final int CL_SMOOTH = 1;
   public static final int CL_THREADED = 2;
   public static final String DEFAULT_STR = "Default";
   public KeyBinding ofKeyBindZoom;
   public String field_74346_m = "Default";
   public int field_74343_n = 0;
   public boolean field_74344_o = true;
   public boolean field_74359_p = true;
   public boolean field_74358_q = true;
   public float field_74357_r = 1.0F;
   public boolean field_74356_s = true;
   public boolean field_74355_t = true;
   public boolean field_74353_u = false;
   public boolean field_74352_v = true;
   public boolean field_80005_w = false;
   public boolean field_82882_x = false;
   public boolean field_82881_y = true;
   public boolean field_82880_z = true;
   public boolean field_85185_A = false;
   public int field_92118_B = 0;
   public int field_92119_C = 0;
   public boolean field_92117_D = true;
   public KeyBinding field_74351_w = new KeyBinding("key.forward", 17);
   public KeyBinding field_74370_x = new KeyBinding("key.left", 30);
   public KeyBinding field_74368_y = new KeyBinding("key.back", 31);
   public KeyBinding field_74366_z = new KeyBinding("key.right", 32);
   public KeyBinding field_74314_A = new KeyBinding("key.jump", 57);
   public KeyBinding field_74315_B = new KeyBinding("key.inventory", 18);
   public KeyBinding field_74316_C = new KeyBinding("key.drop", 16);
   public KeyBinding field_74310_D = new KeyBinding("key.chat", 20);
   public KeyBinding field_74311_E = new KeyBinding("key.sneak", 42);
   public KeyBinding field_74312_F = new KeyBinding("key.attack", -100);
   public KeyBinding field_74313_G = new KeyBinding("key.use", -99);
   public KeyBinding field_74321_H = new KeyBinding("key.playerlist", 15);
   public KeyBinding field_74322_I = new KeyBinding("key.pickItem", -98);
   public KeyBinding field_74323_J = new KeyBinding("key.command", 53);
   public KeyBinding[] field_74324_K;
   protected Minecraft field_74317_L;
   private File field_74354_ai;
   public int field_74318_M;
   public boolean field_74319_N;
   public int field_74320_O;
   public boolean field_74330_P;
   public boolean field_74329_Q;
   public String field_74332_R;
   public boolean field_74331_S;
   public boolean field_74326_T;
   public boolean field_74325_U;
   public float field_74328_V;
   public float field_74327_W;
   public float field_74334_X;
   public float field_74333_Y;
   public int field_74335_Z;
   public int field_74362_aa;
   public String field_74363_ab;
   private File optionsFileOF;


   public GameSettings(Minecraft p_i3009_1_, File p_i3009_2_) {
      this.field_74339_e = 1;
      this.field_74350_i = 0;
      this.ofKeyBindZoom = new KeyBinding("Zoom", 29);
      this.field_74324_K = new KeyBinding[]{this.field_74312_F, this.field_74313_G, this.field_74351_w, this.field_74370_x, this.field_74368_y, this.field_74366_z, this.field_74314_A, this.field_74311_E, this.field_74316_C, this.field_74315_B, this.field_74310_D, this.field_74321_H, this.field_74322_I, this.ofKeyBindZoom, this.field_74323_J};
      this.field_74318_M = 2;
      this.field_74319_N = false;
      this.field_74320_O = 0;
      this.field_74330_P = false;
      this.field_74329_Q = false;
      this.field_74332_R = "";
      this.field_74331_S = false;
      this.field_74326_T = false;
      this.field_74325_U = false;
      this.field_74328_V = 1.0F;
      this.field_74327_W = 1.0F;
      this.field_74334_X = 0.0F;
      this.field_74333_Y = 0.0F;
      this.field_74335_Z = 0;
      this.field_74362_aa = 0;
      this.field_74363_ab = "en_US";
      this.field_74317_L = p_i3009_1_;
      this.field_74354_ai = new File(p_i3009_2_, "options.txt");
      this.optionsFileOF = new File(p_i3009_2_, "optionsof.txt");
      this.func_74300_a();
      Config.setGameSettings(this);
   }

   public GameSettings() {
      this.field_74339_e = 1;
      this.field_74350_i = 0;
      this.ofKeyBindZoom = new KeyBinding("Zoom", 29);
      this.field_74324_K = new KeyBinding[]{this.field_74312_F, this.field_74313_G, this.field_74351_w, this.field_74370_x, this.field_74368_y, this.field_74366_z, this.field_74314_A, this.field_74311_E, this.field_74316_C, this.field_74315_B, this.field_74310_D, this.field_74321_H, this.field_74322_I, this.ofKeyBindZoom, this.field_74323_J};
      this.field_74318_M = 2;
      this.field_74319_N = false;
      this.field_74320_O = 0;
      this.field_74330_P = false;
      this.field_74329_Q = false;
      this.field_74332_R = "";
      this.field_74331_S = false;
      this.field_74326_T = false;
      this.field_74325_U = false;
      this.field_74328_V = 1.0F;
      this.field_74327_W = 1.0F;
      this.field_74334_X = 0.0F;
      this.field_74333_Y = 0.0F;
      this.field_74335_Z = 0;
      this.field_74362_aa = 0;
      this.field_74363_ab = "en_US";
   }

   public String func_74302_a(int p_74302_1_) {
      StringTranslate var2 = StringTranslate.func_74808_a();
      return var2.func_74805_b(this.field_74324_K[p_74302_1_].field_74515_c);
   }

   public String func_74301_b(int p_74301_1_) {
      int var2 = this.field_74324_K[p_74301_1_].field_74512_d;
      return func_74298_c(var2);
   }

   public static String func_74298_c(int p_74298_0_) {
      return p_74298_0_ < 0?StatCollector.func_74837_a("key.mouseButton", new Object[]{Integer.valueOf(p_74298_0_ + 101)}):Keyboard.getKeyName(p_74298_0_);
   }

   public void func_74307_a(int p_74307_1_, int p_74307_2_) {
      this.field_74324_K[p_74307_1_].field_74512_d = p_74307_2_;
      this.func_74303_b();
   }

   public void func_74304_a(EnumOptions p_74304_1_, float p_74304_2_) {
      if(p_74304_1_ == EnumOptions.MUSIC) {
         this.field_74342_a = p_74304_2_;
         this.field_74317_L.field_71416_A.func_77367_a();
      }

      if(p_74304_1_ == EnumOptions.SOUND) {
         this.field_74340_b = p_74304_2_;
         this.field_74317_L.field_71416_A.func_77367_a();
      }

      if(p_74304_1_ == EnumOptions.SENSITIVITY) {
         this.field_74341_c = p_74304_2_;
      }

      if(p_74304_1_ == EnumOptions.FOV) {
         this.field_74334_X = p_74304_2_;
      }

      if(p_74304_1_ == EnumOptions.GAMMA) {
         this.field_74333_Y = p_74304_2_;
      }

      if(p_74304_1_ == EnumOptions.CLOUD_HEIGHT) {
         this.ofCloudsHeight = p_74304_2_;
      }

      if(p_74304_1_ == EnumOptions.AO_LEVEL) {
         this.ofAoLevel = p_74304_2_;
         this.field_74348_k = this.ofAoLevel > 0.0F;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74304_1_ == EnumOptions.RENDER_DISTANCE_FINE) {
         int var3 = this.ofRenderDistanceFine;
         this.ofRenderDistanceFine = 32 + (int)(p_74304_2_ * 480.0F);
         this.ofRenderDistanceFine = this.ofRenderDistanceFine >> 4 << 4;
         this.ofRenderDistanceFine = Config.limit(this.ofRenderDistanceFine, 32, 512);
         this.field_74339_e = fineToRenderDistance(this.ofRenderDistanceFine);
         if(this.ofRenderDistanceFine != var3) {
            this.field_74317_L.field_71438_f.func_72712_a();
         }
      }

      if(p_74304_1_ == EnumOptions.FRAMERATE_LIMIT_FINE) {
         this.ofLimitFramerateFine = (int)(p_74304_2_ * 200.0F);
         this.field_74352_v = false;
         if(this.ofLimitFramerateFine < 5) {
            this.field_74352_v = true;
            this.ofLimitFramerateFine = 0;
         }

         if(this.ofLimitFramerateFine > 199) {
            this.field_74352_v = false;
            this.ofLimitFramerateFine = 0;
         }

         if(this.ofLimitFramerateFine > 30) {
            this.ofLimitFramerateFine = this.ofLimitFramerateFine / 5 * 5;
         }

         if(this.ofLimitFramerateFine > 100) {
            this.ofLimitFramerateFine = this.ofLimitFramerateFine / 10 * 10;
         }

         this.field_74350_i = fineToLimitFramerate(this.ofLimitFramerateFine);
         this.updateVSync();
      }

      if(p_74304_1_ == EnumOptions.CHAT_OPACITY) {
         this.field_74357_r = p_74304_2_;
      }

   }

   private void updateWaterOpacity() {
      byte var1 = 3;
      if(this.ofClearWater) {
         var1 = 1;
      }

      Block.field_71943_B.func_71868_h(var1);
      Block.field_71942_A.func_71868_h(var1);
      if(this.field_74317_L.field_71441_e != null) {
         IChunkProvider var2 = this.field_74317_L.field_71441_e.field_73020_y;
         if(var2 != null) {
            for(int var3 = -512; var3 < 512; ++var3) {
               for(int var4 = -512; var4 < 512; ++var4) {
                  if(var2.func_73149_a(var3, var4)) {
                     Chunk var5 = var2.func_73154_d(var3, var4);
                     if(var5 != null && !(var5 instanceof EmptyChunk)) {
                        ExtendedBlockStorage[] var6 = var5.func_76587_i();

                        for(int var7 = 0; var7 < var6.length; ++var7) {
                           ExtendedBlockStorage var8 = var6[var7];
                           if(var8 != null) {
                              NibbleArray var9 = var8.func_76671_l();
                              if(var9 != null) {
                                 byte[] var10 = var9.field_76585_a;

                                 for(int var11 = 0; var11 < var10.length; ++var11) {
                                    var10[var11] = 0;
                                 }
                              }
                           }
                        }

                        var5.func_76603_b();
                     }
                  }
               }
            }

            this.field_74317_L.field_71438_f.func_72712_a();
         }
      }
   }

   public void updateChunkLoading() {
      switch(this.ofChunkLoading) {
      case 1:
         WrUpdates.setWrUpdater(new WrUpdaterSmooth());
         break;
      case 2:
         WrUpdates.setWrUpdater(new WrUpdaterThreaded());
         break;
      default:
         WrUpdates.setWrUpdater((IWrUpdater)null);
      }

      if(this.field_74317_L.field_71438_f != null) {
         this.field_74317_L.field_71438_f.func_72712_a();
      }

   }

   public void setAllAnimations(boolean var1) {
      int var2 = var1?0:2;
      this.ofAnimatedWater = var2;
      this.ofAnimatedLava = var2;
      this.ofAnimatedFire = var1;
      this.ofAnimatedPortal = var1;
      this.ofAnimatedRedstone = var1;
      this.ofAnimatedExplosion = var1;
      this.ofAnimatedFlame = var1;
      this.ofAnimatedSmoke = var1;
      this.ofVoidParticles = var1;
      this.ofWaterParticles = var1;
      this.ofRainSplash = var1;
      this.ofPortalParticles = var1;
      this.field_74362_aa = var1?0:2;
      this.ofDrippingWaterLava = var1;
      this.ofAnimatedTerrain = var1;
      this.ofAnimatedItems = var1;
      this.ofAnimatedTextures = var1;
      this.field_74317_L.field_71446_o.func_78352_b();
   }

   public void func_74306_a(EnumOptions p_74306_1_, int p_74306_2_) {
      if(p_74306_1_ == EnumOptions.INVERT_MOUSE) {
         this.field_74338_d = !this.field_74338_d;
      }

      if(p_74306_1_ == EnumOptions.RENDER_DISTANCE) {
         this.field_74339_e = this.field_74339_e + p_74306_2_ & 3;
         this.ofRenderDistanceFine = renderDistanceToFine(this.field_74339_e);
      }

      if(p_74306_1_ == EnumOptions.GUI_SCALE) {
         this.field_74335_Z = this.field_74335_Z + p_74306_2_ & 3;
      }

      if(p_74306_1_ == EnumOptions.PARTICLES) {
         this.field_74362_aa = (this.field_74362_aa + p_74306_2_) % 3;
      }

      if(p_74306_1_ == EnumOptions.VIEW_BOBBING) {
         this.field_74336_f = !this.field_74336_f;
      }

      if(p_74306_1_ == EnumOptions.RENDER_CLOUDS) {
         this.field_74345_l = !this.field_74345_l;
      }

      if(p_74306_1_ == EnumOptions.ADVANCED_OPENGL) {
         if(!Config.isOcclusionAvailable()) {
            this.ofOcclusionFancy = false;
            this.field_74349_h = false;
         } else if(!this.field_74349_h) {
            this.field_74349_h = true;
            this.ofOcclusionFancy = false;
         } else if(!this.ofOcclusionFancy) {
            this.ofOcclusionFancy = true;
         } else {
            this.ofOcclusionFancy = false;
            this.field_74349_h = false;
         }

         this.field_74317_L.field_71438_f.setAllRenderersVisible();
      }

      if(p_74306_1_ == EnumOptions.ANAGLYPH) {
         this.field_74337_g = !this.field_74337_g;
         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.FRAMERATE_LIMIT) {
         this.field_74350_i = (this.field_74350_i + p_74306_2_ + 3) % 3;
         this.ofLimitFramerateFine = limitFramerateToFine(this.field_74350_i);
      }

      if(p_74306_1_ == EnumOptions.DIFFICULTY) {
         this.field_74318_M = this.field_74318_M + p_74306_2_ & 3;
      }

      if(p_74306_1_ == EnumOptions.GRAPHICS) {
         this.field_74347_j = !this.field_74347_j;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.AMBIENT_OCCLUSION) {
         this.field_74348_k = !this.field_74348_k;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.FOG_FANCY) {
         switch(this.ofFogType) {
         case 1:
            this.ofFogType = 2;
            if(!Config.isFancyFogAvailable()) {
               this.ofFogType = 3;
            }
            break;
         case 2:
            this.ofFogType = 3;
            break;
         case 3:
            this.ofFogType = 1;
            break;
         default:
            this.ofFogType = 1;
         }
      }

      if(p_74306_1_ == EnumOptions.FOG_START) {
         this.ofFogStart += 0.2F;
         if(this.ofFogStart > 0.81F) {
            this.ofFogStart = 0.2F;
         }
      }

      if(p_74306_1_ == EnumOptions.MIPMAP_LEVEL) {
         ++this.ofMipmapLevel;
         if(this.ofMipmapLevel > 4) {
            this.ofMipmapLevel = 0;
         }

         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.MIPMAP_TYPE) {
         this.ofMipmapLinear = !this.ofMipmapLinear;
         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.LOAD_FAR) {
         this.ofLoadFar = !this.ofLoadFar;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.PRELOADED_CHUNKS) {
         this.ofPreloadedChunks += 2;
         if(this.ofPreloadedChunks > 8) {
            this.ofPreloadedChunks = 0;
         }

         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.SMOOTH_FPS) {
         this.ofSmoothFps = !this.ofSmoothFps;
      }

      if(p_74306_1_ == EnumOptions.SMOOTH_WORLD) {
         this.ofSmoothWorld = !this.ofSmoothWorld;
         Config.updateThreadPriorities();
      }

      if(p_74306_1_ == EnumOptions.CLOUDS) {
         ++this.ofClouds;
         if(this.ofClouds > 3) {
            this.ofClouds = 0;
         }
      }

      if(p_74306_1_ == EnumOptions.TREES) {
         ++this.ofTrees;
         if(this.ofTrees > 2) {
            this.ofTrees = 0;
         }

         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.GRASS) {
         ++this.ofGrass;
         if(this.ofGrass > 2) {
            this.ofGrass = 0;
         }

         RenderBlocks.field_78667_b = Config.isGrassFancy();
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.DROPPED_ITEMS) {
         ++this.ofDroppedItems;
         if(this.ofDroppedItems > 2) {
            this.ofDroppedItems = 0;
         }
      }

      if(p_74306_1_ == EnumOptions.RAIN) {
         ++this.ofRain;
         if(this.ofRain > 3) {
            this.ofRain = 0;
         }
      }

      if(p_74306_1_ == EnumOptions.WATER) {
         ++this.ofWater;
         if(this.ofWater > 2) {
            this.ofWater = 0;
         }
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_WATER) {
         ++this.ofAnimatedWater;
         if(this.ofAnimatedWater > 2) {
            this.ofAnimatedWater = 0;
         }

         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_LAVA) {
         ++this.ofAnimatedLava;
         if(this.ofAnimatedLava > 2) {
            this.ofAnimatedLava = 0;
         }

         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_FIRE) {
         this.ofAnimatedFire = !this.ofAnimatedFire;
         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_PORTAL) {
         this.ofAnimatedPortal = !this.ofAnimatedPortal;
         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_REDSTONE) {
         this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_EXPLOSION) {
         this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_FLAME) {
         this.ofAnimatedFlame = !this.ofAnimatedFlame;
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_SMOKE) {
         this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
      }

      if(p_74306_1_ == EnumOptions.VOID_PARTICLES) {
         this.ofVoidParticles = !this.ofVoidParticles;
      }

      if(p_74306_1_ == EnumOptions.WATER_PARTICLES) {
         this.ofWaterParticles = !this.ofWaterParticles;
      }

      if(p_74306_1_ == EnumOptions.PORTAL_PARTICLES) {
         this.ofPortalParticles = !this.ofPortalParticles;
      }

      if(p_74306_1_ == EnumOptions.DRIPPING_WATER_LAVA) {
         this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_TERRAIN) {
         this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_TEXTURES) {
         this.ofAnimatedTextures = !this.ofAnimatedTextures;
      }

      if(p_74306_1_ == EnumOptions.ANIMATED_ITEMS) {
         this.ofAnimatedItems = !this.ofAnimatedItems;
         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.RAIN_SPLASH) {
         this.ofRainSplash = !this.ofRainSplash;
      }

      if(p_74306_1_ == EnumOptions.LAGOMETER) {
         this.ofLagometer = !this.ofLagometer;
      }

      if(p_74306_1_ == EnumOptions.AUTOSAVE_TICKS) {
         this.ofAutoSaveTicks *= 10;
         if(this.ofAutoSaveTicks > '\u9c40') {
            this.ofAutoSaveTicks = 40;
         }
      }

      if(p_74306_1_ == EnumOptions.BETTER_GRASS) {
         ++this.ofBetterGrass;
         if(this.ofBetterGrass > 3) {
            this.ofBetterGrass = 1;
         }

         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.CONNECTED_TEXTURES) {
         ++this.ofConnectedTextures;
         if(this.ofConnectedTextures > 3) {
            this.ofConnectedTextures = 1;
         }

         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.WEATHER) {
         this.ofWeather = !this.ofWeather;
      }

      if(p_74306_1_ == EnumOptions.SKY) {
         this.ofSky = !this.ofSky;
      }

      if(p_74306_1_ == EnumOptions.STARS) {
         this.ofStars = !this.ofStars;
      }

      if(p_74306_1_ == EnumOptions.SUN_MOON) {
         this.ofSunMoon = !this.ofSunMoon;
      }

      if(p_74306_1_ == EnumOptions.CHUNK_UPDATES) {
         ++this.ofChunkUpdates;
         if(this.ofChunkUpdates > 5) {
            this.ofChunkUpdates = 1;
         }
      }

      if(p_74306_1_ == EnumOptions.CHUNK_LOADING) {
         ++this.ofChunkLoading;
         if(this.ofChunkLoading > 2) {
            this.ofChunkLoading = 0;
         }

         this.updateChunkLoading();
      }

      if(p_74306_1_ == EnumOptions.CHUNK_UPDATES_DYNAMIC) {
         this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
      }

      if(p_74306_1_ == EnumOptions.TIME) {
         ++this.ofTime;
         if(this.ofTime > 3) {
            this.ofTime = 0;
         }
      }

      if(p_74306_1_ == EnumOptions.CLEAR_WATER) {
         this.ofClearWater = !this.ofClearWater;
         this.updateWaterOpacity();
      }

      if(p_74306_1_ == EnumOptions.DEPTH_FOG) {
         this.ofDepthFog = !this.ofDepthFog;
      }

      if(p_74306_1_ == EnumOptions.AA_LEVEL) {
         int[] var3 = new int[]{0, 2, 4, 6, 8, 12, 16};
         boolean var4 = false;

         for(int var5 = 0; var5 < var3.length - 1; ++var5) {
            if(this.ofAaLevel == var3[var5]) {
               this.ofAaLevel = var3[var5 + 1];
               var4 = true;
               break;
            }
         }

         if(!var4) {
            this.ofAaLevel = 0;
         }
      }

      if(p_74306_1_ == EnumOptions.AF_LEVEL) {
         this.ofAfLevel *= 2;
         if(this.ofAfLevel > 16) {
            this.ofAfLevel = 1;
         }

         this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
         this.field_74317_L.field_71446_o.func_78352_b();
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.PROFILER) {
         this.ofProfiler = !this.ofProfiler;
      }

      if(p_74306_1_ == EnumOptions.BETTER_SNOW) {
         this.ofBetterSnow = !this.ofBetterSnow;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.SWAMP_COLORS) {
         this.ofSwampColors = !this.ofSwampColors;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.RANDOM_MOBS) {
         this.ofRandomMobs = !this.ofRandomMobs;
         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.SMOOTH_BIOMES) {
         this.ofSmoothBiomes = !this.ofSmoothBiomes;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.CUSTOM_FONTS) {
         this.ofCustomFonts = !this.ofCustomFonts;
         this.field_74317_L.field_71446_o.func_78352_b();
      }

      if(p_74306_1_ == EnumOptions.CUSTOM_COLORS) {
         this.ofCustomColors = !this.ofCustomColors;
         this.field_74317_L.field_71446_o.func_78352_b();
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.SHOW_CAPES) {
         this.ofShowCapes = !this.ofShowCapes;
         this.field_74317_L.field_71438_f.updateCapes();
      }

      if(p_74306_1_ == EnumOptions.NATURAL_TEXTURES) {
         this.ofNaturalTextures = !this.ofNaturalTextures;
         this.field_74317_L.field_71446_o.func_78352_b();
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.LAZY_CHUNK_LOADING) {
         this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
         this.field_74317_L.field_71438_f.func_72712_a();
      }

      if(p_74306_1_ == EnumOptions.FULLSCREEN_MODE) {
         List var6 = Arrays.asList(Config.getFullscreenModes());
         if(this.ofFullscreenMode.equals("Default")) {
            this.ofFullscreenMode = (String)var6.get(0);
         } else {
            int var7 = var6.indexOf(this.ofFullscreenMode);
            if(var7 < 0) {
               this.ofFullscreenMode = "Default";
            } else {
               ++var7;
               if(var7 >= var6.size()) {
                  this.ofFullscreenMode = "Default";
               } else {
                  this.ofFullscreenMode = (String)var6.get(var7);
               }
            }
         }
      }

      if(p_74306_1_ == EnumOptions.HELD_ITEM_TOOLTIPS) {
         this.field_92117_D = !this.field_92117_D;
      }

      if(p_74306_1_ == EnumOptions.CHAT_VISIBILITY) {
         this.field_74343_n = (this.field_74343_n + p_74306_2_) % 3;
      }

      if(p_74306_1_ == EnumOptions.CHAT_COLOR) {
         this.field_74344_o = !this.field_74344_o;
      }

      if(p_74306_1_ == EnumOptions.CHAT_LINKS) {
         this.field_74359_p = !this.field_74359_p;
      }

      if(p_74306_1_ == EnumOptions.CHAT_LINKS_PROMPT) {
         this.field_74358_q = !this.field_74358_q;
      }

      if(p_74306_1_ == EnumOptions.USE_SERVER_TEXTURES) {
         this.field_74356_s = !this.field_74356_s;
      }

      if(p_74306_1_ == EnumOptions.SNOOPER_ENABLED) {
         this.field_74355_t = !this.field_74355_t;
      }

      if(p_74306_1_ == EnumOptions.SHOW_CAPE) {
         this.field_82880_z = !this.field_82880_z;
      }

      if(p_74306_1_ == EnumOptions.TOUCHSCREEN) {
         this.field_85185_A = !this.field_85185_A;
      }

      if(p_74306_1_ == EnumOptions.USE_FULLSCREEN) {
         this.field_74353_u = !this.field_74353_u;
         if(this.field_74317_L.func_71372_G() != this.field_74353_u) {
            this.field_74317_L.func_71352_k();
         }
      }

      if(p_74306_1_ == EnumOptions.ENABLE_VSYNC) {
         this.field_74352_v = !this.field_74352_v;
         Display.setVSyncEnabled(this.field_74352_v);
      }

      this.func_74303_b();
   }

   public float func_74296_a(EnumOptions p_74296_1_) {
      return p_74296_1_ == EnumOptions.FOV?this.field_74334_X:(p_74296_1_ == EnumOptions.GAMMA?this.field_74333_Y:(p_74296_1_ == EnumOptions.MUSIC?this.field_74342_a:(p_74296_1_ == EnumOptions.SOUND?this.field_74340_b:(p_74296_1_ == EnumOptions.SENSITIVITY?this.field_74341_c:(p_74296_1_ == EnumOptions.CLOUD_HEIGHT?this.ofCloudsHeight:(p_74296_1_ == EnumOptions.AO_LEVEL?this.ofAoLevel:(p_74296_1_ == EnumOptions.RENDER_DISTANCE_FINE?(float)(this.ofRenderDistanceFine - 32) / 480.0F:(p_74296_1_ == EnumOptions.FRAMERATE_LIMIT_FINE?(this.ofLimitFramerateFine > 0 && this.ofLimitFramerateFine < 200?(float)this.ofLimitFramerateFine / 200.0F:(this.field_74352_v?0.0F:1.0F)):(p_74296_1_ == EnumOptions.CHAT_OPACITY?this.field_74357_r:0.0F)))))))));
   }

   public boolean func_74308_b(EnumOptions p_74308_1_) {
      switch(EnumOptionsHelper.field_74414_a[p_74308_1_.ordinal()]) {
      case 1:
         return this.field_74338_d;
      case 2:
         return this.field_74336_f;
      case 3:
         return this.field_74337_g;
      case 4:
         return this.field_74349_h;
      case 5:
         return this.field_74348_k;
      case 6:
         return this.field_74345_l;
      case 7:
         return this.field_74344_o;
      case 8:
         return this.field_74359_p;
      case 9:
         return this.field_74358_q;
      case 10:
         return this.field_74356_s;
      case 11:
         return this.field_74355_t;
      case 12:
         return this.field_74353_u;
      case 13:
         return this.field_74352_v;
      case 14:
         return this.field_82880_z;
      case 15:
         return this.field_85185_A;
      default:
         return false;
      }
   }

   private static String func_74299_a(String[] p_74299_0_, int p_74299_1_) {
      if(p_74299_1_ < 0 || p_74299_1_ >= p_74299_0_.length) {
         p_74299_1_ = 0;
      }

      StringTranslate var2 = StringTranslate.func_74808_a();
      return var2.func_74805_b(p_74299_0_[p_74299_1_]);
   }

   public String func_74297_c(EnumOptions p_74297_1_) {
      StringTranslate var2 = StringTranslate.func_74808_a();
      String var3 = var2.func_74805_b(p_74297_1_.func_74378_d());
      if(var3 == null) {
         var3 = p_74297_1_.func_74378_d();
      }

      String var4 = var3 + ": ";
      if(p_74297_1_.func_74380_a()) {
         float var9 = this.func_74296_a(p_74297_1_);
         if(p_74297_1_ == EnumOptions.SENSITIVITY) {
            return var9 == 0.0F?var4 + var2.func_74805_b("options.sensitivity.min"):(var9 == 1.0F?var4 + var2.func_74805_b("options.sensitivity.max"):var4 + (int)(var9 * 200.0F) + "%");
         } else if(p_74297_1_ == EnumOptions.FOV) {
            return var9 == 0.0F?var4 + var2.func_74805_b("options.fov.min"):(var9 == 1.0F?var4 + var2.func_74805_b("options.fov.max"):var4 + (int)(70.0F + var9 * 40.0F));
         } else if(p_74297_1_ == EnumOptions.GAMMA) {
            return var9 == 0.0F?var4 + var2.func_74805_b("options.gamma.min"):(var9 == 1.0F?var4 + var2.func_74805_b("options.gamma.max"):var4 + "+" + (int)(var9 * 100.0F) + "%");
         } else if(p_74297_1_ == EnumOptions.RENDER_DISTANCE_FINE) {
            String var6 = "Tiny";
            short var7 = 32;
            if(this.ofRenderDistanceFine >= 64) {
               var6 = "Short";
               var7 = 64;
            }

            if(this.ofRenderDistanceFine >= 128) {
               var6 = "Normal";
               var7 = 128;
            }

            if(this.ofRenderDistanceFine >= 256) {
               var6 = "Far";
               var7 = 256;
            }

            if(this.ofRenderDistanceFine >= 512) {
               var6 = "Extreme";
               var7 = 512;
            }

            int var8 = this.ofRenderDistanceFine - var7;
            return var8 == 0?var4 + var6:var4 + var6 + " +" + var8;
         } else {
            return p_74297_1_ == EnumOptions.FRAMERATE_LIMIT_FINE?(this.ofLimitFramerateFine > 0 && this.ofLimitFramerateFine < 200?var4 + " " + this.ofLimitFramerateFine + " FPS":(this.field_74352_v?var4 + " VSync":var4 + " MaxFPS")):(p_74297_1_ == EnumOptions.CHAT_OPACITY?var4 + (int)(var9 * 90.0F + 10.0F) + "%":(var9 == 0.0F?var4 + var2.func_74805_b("options.off"):var4 + (int)(var9 * 100.0F) + "%"));
         }
      } else if(p_74297_1_ == EnumOptions.ADVANCED_OPENGL) {
         return !this.field_74349_h?var4 + "OFF":(this.ofOcclusionFancy?var4 + "Fancy":var4 + "Fast");
      } else if(p_74297_1_.func_74382_b()) {
         boolean var5 = this.func_74308_b(p_74297_1_);
         return var5?var4 + var2.func_74805_b("options.on"):var4 + var2.func_74805_b("options.off");
      } else if(p_74297_1_ == EnumOptions.RENDER_DISTANCE) {
         return var4 + func_74299_a(field_74360_ac, this.field_74339_e);
      } else if(p_74297_1_ == EnumOptions.DIFFICULTY) {
         return var4 + func_74299_a(field_74361_ad, this.field_74318_M);
      } else if(p_74297_1_ == EnumOptions.GUI_SCALE) {
         return var4 + func_74299_a(field_74367_ae, this.field_74335_Z);
      } else if(p_74297_1_ == EnumOptions.CHAT_VISIBILITY) {
         return var4 + func_74299_a(field_74369_af, this.field_74343_n);
      } else if(p_74297_1_ == EnumOptions.PARTICLES) {
         return var4 + func_74299_a(field_74364_ag, this.field_74362_aa);
      } else if(p_74297_1_ == EnumOptions.FRAMERATE_LIMIT) {
         return var4 + func_74299_a(field_74365_ah, this.field_74350_i);
      } else if(p_74297_1_ == EnumOptions.FOG_FANCY) {
         switch(this.ofFogType) {
         case 1:
            return var4 + "Fast";
         case 2:
            return var4 + "Fancy";
         case 3:
            return var4 + "OFF";
         default:
            return var4 + "OFF";
         }
      } else if(p_74297_1_ == EnumOptions.FOG_START) {
         return var4 + this.ofFogStart;
      } else if(p_74297_1_ == EnumOptions.MIPMAP_LEVEL) {
         return this.ofMipmapLevel == 0?var4 + "OFF":(this.ofMipmapLevel == 4?var4 + "Max":var4 + this.ofMipmapLevel);
      } else if(p_74297_1_ == EnumOptions.MIPMAP_TYPE) {
         return this.ofMipmapLinear?var4 + "Linear":var4 + "Nearest";
      } else if(p_74297_1_ == EnumOptions.LOAD_FAR) {
         return this.ofLoadFar?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.PRELOADED_CHUNKS) {
         return this.ofPreloadedChunks == 0?var4 + "OFF":var4 + this.ofPreloadedChunks;
      } else if(p_74297_1_ == EnumOptions.SMOOTH_FPS) {
         return this.ofSmoothFps?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.SMOOTH_WORLD) {
         return this.ofSmoothWorld?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.CLOUDS) {
         switch(this.ofClouds) {
         case 1:
            return var4 + "Fast";
         case 2:
            return var4 + "Fancy";
         case 3:
            return var4 + "OFF";
         default:
            return var4 + "Default";
         }
      } else if(p_74297_1_ == EnumOptions.TREES) {
         switch(this.ofTrees) {
         case 1:
            return var4 + "Fast";
         case 2:
            return var4 + "Fancy";
         default:
            return var4 + "Default";
         }
      } else if(p_74297_1_ == EnumOptions.GRASS) {
         switch(this.ofGrass) {
         case 1:
            return var4 + "Fast";
         case 2:
            return var4 + "Fancy";
         default:
            return var4 + "Default";
         }
      } else if(p_74297_1_ == EnumOptions.DROPPED_ITEMS) {
         switch(this.ofDroppedItems) {
         case 1:
            return var4 + "Fast";
         case 2:
            return var4 + "Fancy";
         default:
            return var4 + "Default";
         }
      } else if(p_74297_1_ == EnumOptions.RAIN) {
         switch(this.ofRain) {
         case 1:
            return var4 + "Fast";
         case 2:
            return var4 + "Fancy";
         case 3:
            return var4 + "OFF";
         default:
            return var4 + "Default";
         }
      } else if(p_74297_1_ == EnumOptions.WATER) {
         switch(this.ofWater) {
         case 1:
            return var4 + "Fast";
         case 2:
            return var4 + "Fancy";
         case 3:
            return var4 + "OFF";
         default:
            return var4 + "Default";
         }
      } else if(p_74297_1_ == EnumOptions.ANIMATED_WATER) {
         switch(this.ofAnimatedWater) {
         case 1:
            return var4 + "Dynamic";
         case 2:
            return var4 + "OFF";
         default:
            return var4 + "ON";
         }
      } else if(p_74297_1_ == EnumOptions.ANIMATED_LAVA) {
         switch(this.ofAnimatedLava) {
         case 1:
            return var4 + "Dynamic";
         case 2:
            return var4 + "OFF";
         default:
            return var4 + "ON";
         }
      } else if(p_74297_1_ == EnumOptions.ANIMATED_FIRE) {
         return this.ofAnimatedFire?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.ANIMATED_PORTAL) {
         return this.ofAnimatedPortal?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.ANIMATED_REDSTONE) {
         return this.ofAnimatedRedstone?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.ANIMATED_EXPLOSION) {
         return this.ofAnimatedExplosion?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.ANIMATED_FLAME) {
         return this.ofAnimatedFlame?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.ANIMATED_SMOKE) {
         return this.ofAnimatedSmoke?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.VOID_PARTICLES) {
         return this.ofVoidParticles?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.WATER_PARTICLES) {
         return this.ofWaterParticles?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.PORTAL_PARTICLES) {
         return this.ofPortalParticles?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.DRIPPING_WATER_LAVA) {
         return this.ofDrippingWaterLava?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.ANIMATED_TERRAIN) {
         return this.ofAnimatedTerrain?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.ANIMATED_TEXTURES) {
         return this.ofAnimatedTextures?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.ANIMATED_ITEMS) {
         return this.ofAnimatedItems?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.RAIN_SPLASH) {
         return this.ofRainSplash?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.LAGOMETER) {
         return this.ofLagometer?var4 + "ON":var4 + "OFF";
      } else if(p_74297_1_ == EnumOptions.AUTOSAVE_TICKS) {
         return this.ofAutoSaveTicks <= 40?var4 + "Default (2s)":(this.ofAutoSaveTicks <= 400?var4 + "20s":(this.ofAutoSaveTicks <= 4000?var4 + "3min":var4 + "30min"));
      } else if(p_74297_1_ == EnumOptions.BETTER_GRASS) {
         switch(this.ofBetterGrass) {
         case 1:
            return var4 + "Fast";
         case 2:
            return var4 + "Fancy";
         default:
            return var4 + "OFF";
         }
      } else if(p_74297_1_ == EnumOptions.CONNECTED_TEXTURES) {
         switch(this.ofConnectedTextures) {
         case 1:
            return var4 + "Fast";
         case 2:
            return var4 + "Fancy";
         default:
            return var4 + "OFF";
         }
      } else {
         return p_74297_1_ == EnumOptions.WEATHER?(this.ofWeather?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.SKY?(this.ofSky?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.STARS?(this.ofStars?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.SUN_MOON?(this.ofSunMoon?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.CHUNK_UPDATES?var4 + this.ofChunkUpdates:(p_74297_1_ == EnumOptions.CHUNK_LOADING?(this.ofChunkLoading == 1?var4 + "Smooth":(this.ofChunkLoading == 2?var4 + "Multi-Core":var4 + "Default")):(p_74297_1_ == EnumOptions.CHUNK_UPDATES_DYNAMIC?(this.ofChunkUpdatesDynamic?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.TIME?(this.ofTime == 1?var4 + "Day Only":(this.ofTime == 3?var4 + "Night Only":var4 + "Default")):(p_74297_1_ == EnumOptions.CLEAR_WATER?(this.ofClearWater?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.DEPTH_FOG?(this.ofDepthFog?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.AA_LEVEL?(this.ofAaLevel == 0?var4 + "OFF":var4 + this.ofAaLevel):(p_74297_1_ == EnumOptions.AF_LEVEL?(this.ofAfLevel == 1?var4 + "OFF":var4 + this.ofAfLevel):(p_74297_1_ == EnumOptions.PROFILER?(this.ofProfiler?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.BETTER_SNOW?(this.ofBetterSnow?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.SWAMP_COLORS?(this.ofSwampColors?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.RANDOM_MOBS?(this.ofRandomMobs?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.SMOOTH_BIOMES?(this.ofSmoothBiomes?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.CUSTOM_FONTS?(this.ofCustomFonts?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.CUSTOM_COLORS?(this.ofCustomColors?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.SHOW_CAPES?(this.ofShowCapes?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.NATURAL_TEXTURES?(this.ofNaturalTextures?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.LAZY_CHUNK_LOADING?(this.ofLazyChunkLoading?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.FULLSCREEN_MODE?var4 + this.ofFullscreenMode:(p_74297_1_ == EnumOptions.HELD_ITEM_TOOLTIPS?(this.field_92117_D?var4 + "ON":var4 + "OFF"):(p_74297_1_ == EnumOptions.GRAPHICS?(this.field_74347_j?var4 + var2.func_74805_b("options.graphics.fancy"):var4 + var2.func_74805_b("options.graphics.fast")):var4))))))))))))))))))))))));
      }
   }

   public void func_74300_a() {
      try {
         if(!this.field_74354_ai.exists()) {
            return;
         }

         BufferedReader var1 = new BufferedReader(new FileReader(this.field_74354_ai));
         String var2 = "";

         while((var2 = var1.readLine()) != null) {
            try {
               String[] var3 = var2.split(":");
               if(var3[0].equals("music")) {
                  this.field_74342_a = this.func_74305_a(var3[1]);
               }

               if(var3[0].equals("sound")) {
                  this.field_74340_b = this.func_74305_a(var3[1]);
               }

               if(var3[0].equals("mouseSensitivity")) {
                  this.field_74341_c = this.func_74305_a(var3[1]);
               }

               if(var3[0].equals("fov")) {
                  this.field_74334_X = this.func_74305_a(var3[1]);
               }

               if(var3[0].equals("gamma")) {
                  this.field_74333_Y = this.func_74305_a(var3[1]);
               }

               if(var3[0].equals("invertYMouse")) {
                  this.field_74338_d = var3[1].equals("true");
               }

               if(var3[0].equals("viewDistance")) {
                  this.field_74339_e = Integer.parseInt(var3[1]);
                  this.ofRenderDistanceFine = renderDistanceToFine(this.field_74339_e);
               }

               if(var3[0].equals("guiScale")) {
                  this.field_74335_Z = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("particles")) {
                  this.field_74362_aa = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("bobView")) {
                  this.field_74336_f = var3[1].equals("true");
               }

               if(var3[0].equals("anaglyph3d")) {
                  this.field_74337_g = var3[1].equals("true");
               }

               if(var3[0].equals("advancedOpengl")) {
                  this.field_74349_h = var3[1].equals("true");
               }

               if(var3[0].equals("fpsLimit")) {
                  this.field_74350_i = Integer.parseInt(var3[1]);
                  this.ofLimitFramerateFine = limitFramerateToFine(this.field_74350_i);
               }

               if(var3[0].equals("difficulty")) {
                  this.field_74318_M = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("fancyGraphics")) {
                  this.field_74347_j = var3[1].equals("true");
               }

               if(var3[0].equals("ao")) {
                  this.field_74348_k = var3[1].equals("true");
                  if(this.field_74348_k) {
                     this.ofAoLevel = 1.0F;
                  } else {
                     this.ofAoLevel = 0.0F;
                  }
               }

               if(var3[0].equals("clouds")) {
                  this.field_74345_l = var3[1].equals("true");
               }

               if(var3[0].equals("skin")) {
                  this.field_74346_m = var3[1];
               }

               if(var3[0].equals("lastServer") && var3.length >= 2) {
                  this.field_74332_R = var3[1];
               }

               if(var3[0].equals("lang") && var3.length >= 2) {
                  this.field_74363_ab = var3[1];
               }

               if(var3[0].equals("chatVisibility")) {
                  this.field_74343_n = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("chatColors")) {
                  this.field_74344_o = var3[1].equals("true");
               }

               if(var3[0].equals("chatLinks")) {
                  this.field_74359_p = var3[1].equals("true");
               }

               if(var3[0].equals("chatLinksPrompt")) {
                  this.field_74358_q = var3[1].equals("true");
               }

               if(var3[0].equals("chatOpacity")) {
                  this.field_74357_r = this.func_74305_a(var3[1]);
               }

               if(var3[0].equals("serverTextures")) {
                  this.field_74356_s = var3[1].equals("true");
               }

               if(var3[0].equals("snooperEnabled")) {
                  this.field_74355_t = var3[1].equals("true");
               }

               if(var3[0].equals("fullscreen")) {
                  this.field_74353_u = var3[1].equals("true");
               }

               if(var3[0].equals("enableVsync")) {
                  this.field_74352_v = var3[1].equals("true");
                  this.updateVSync();
               }

               if(var3[0].equals("hideServerAddress")) {
                  this.field_80005_w = var3[1].equals("true");
               }

               if(var3[0].equals("advancedItemTooltips")) {
                  this.field_82882_x = var3[1].equals("true");
               }

               if(var3[0].equals("pauseOnLostFocus")) {
                  this.field_82881_y = var3[1].equals("true");
               }

               if(var3[0].equals("showCape")) {
                  this.field_82880_z = var3[1].equals("true");
               }

               if(var3[0].equals("touchscreen")) {
                  this.field_85185_A = var3[1].equals("true");
               }

               if(var3[0].equals("overrideHeight")) {
                  this.field_92119_C = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("overrideWidth")) {
                  this.field_92118_B = Integer.parseInt(var3[1]);
               }

               if(var3[0].equals("heldItemTooltips")) {
                  this.field_92117_D = var3[1].equals("true");
               }

               for(int var4 = 0; var4 < this.field_74324_K.length; ++var4) {
                  if(var3[0].equals("key_" + this.field_74324_K[var4].field_74515_c)) {
                     this.field_74324_K[var4].field_74512_d = Integer.parseInt(var3[1]);
                  }
               }
            } catch (Exception var7) {
               System.out.println("Skipping bad option: " + var2);
               var7.printStackTrace();
            }
         }

         KeyBinding.func_74508_b();
         var1.close();
      } catch (Exception var8) {
         System.out.println("Failed to load options");
         var8.printStackTrace();
      }

      try {
         File var9 = this.optionsFileOF;
         if(!var9.exists()) {
            var9 = this.field_74354_ai;
         }

         if(!var9.exists()) {
            return;
         }

         BufferedReader var10 = new BufferedReader(new FileReader(var9));
         String var11 = "";

         while((var11 = var10.readLine()) != null) {
            try {
               String[] var12 = var11.split(":");
               if(var12[0].equals("ofRenderDistanceFine") && var12.length >= 2) {
                  this.ofRenderDistanceFine = Integer.valueOf(var12[1]).intValue();
                  this.ofRenderDistanceFine = Config.limit(this.ofRenderDistanceFine, 32, 512);
                  this.field_74339_e = fineToRenderDistance(this.ofRenderDistanceFine);
               }

               if(var12[0].equals("ofLimitFramerateFine") && var12.length >= 2) {
                  this.ofLimitFramerateFine = Integer.valueOf(var12[1]).intValue();
                  this.ofLimitFramerateFine = Config.limit(this.ofLimitFramerateFine, 0, 199);
                  this.field_74350_i = fineToLimitFramerate(this.ofLimitFramerateFine);
               }

               if(var12[0].equals("ofFogType") && var12.length >= 2) {
                  this.ofFogType = Integer.valueOf(var12[1]).intValue();
                  this.ofFogType = Config.limit(this.ofFogType, 1, 3);
               }

               if(var12[0].equals("ofFogStart") && var12.length >= 2) {
                  this.ofFogStart = Float.valueOf(var12[1]).floatValue();
                  if(this.ofFogStart < 0.2F) {
                     this.ofFogStart = 0.2F;
                  }

                  if(this.ofFogStart > 0.81F) {
                     this.ofFogStart = 0.8F;
                  }
               }

               if(var12[0].equals("ofMipmapLevel") && var12.length >= 2) {
                  this.ofMipmapLevel = Integer.valueOf(var12[1]).intValue();
                  if(this.ofMipmapLevel < 0) {
                     this.ofMipmapLevel = 0;
                  }

                  if(this.ofMipmapLevel > 4) {
                     this.ofMipmapLevel = 4;
                  }
               }

               if(var12[0].equals("ofMipmapLinear") && var12.length >= 2) {
                  this.ofMipmapLinear = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofLoadFar") && var12.length >= 2) {
                  this.ofLoadFar = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofPreloadedChunks") && var12.length >= 2) {
                  this.ofPreloadedChunks = Integer.valueOf(var12[1]).intValue();
                  if(this.ofPreloadedChunks < 0) {
                     this.ofPreloadedChunks = 0;
                  }

                  if(this.ofPreloadedChunks > 8) {
                     this.ofPreloadedChunks = 8;
                  }
               }

               if(var12[0].equals("ofOcclusionFancy") && var12.length >= 2) {
                  this.ofOcclusionFancy = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofSmoothFps") && var12.length >= 2) {
                  this.ofSmoothFps = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofSmoothWorld") && var12.length >= 2) {
                  this.ofSmoothWorld = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAoLevel") && var12.length >= 2) {
                  this.ofAoLevel = Float.valueOf(var12[1]).floatValue();
                  this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0F, 1.0F);
                  this.field_74348_k = this.ofAoLevel > 0.0F;
               }

               if(var12[0].equals("ofClouds") && var12.length >= 2) {
                  this.ofClouds = Integer.valueOf(var12[1]).intValue();
                  this.ofClouds = Config.limit(this.ofClouds, 0, 3);
               }

               if(var12[0].equals("ofCloudsHeight") && var12.length >= 2) {
                  this.ofCloudsHeight = Float.valueOf(var12[1]).floatValue();
                  this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0F, 1.0F);
               }

               if(var12[0].equals("ofTrees") && var12.length >= 2) {
                  this.ofTrees = Integer.valueOf(var12[1]).intValue();
                  this.ofTrees = Config.limit(this.ofTrees, 0, 2);
               }

               if(var12[0].equals("ofGrass") && var12.length >= 2) {
                  this.ofGrass = Integer.valueOf(var12[1]).intValue();
                  this.ofGrass = Config.limit(this.ofGrass, 0, 2);
               }

               if(var12[0].equals("ofDroppedItems") && var12.length >= 2) {
                  this.ofDroppedItems = Integer.valueOf(var12[1]).intValue();
                  this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
               }

               if(var12[0].equals("ofRain") && var12.length >= 2) {
                  this.ofRain = Integer.valueOf(var12[1]).intValue();
                  this.ofRain = Config.limit(this.ofRain, 0, 3);
               }

               if(var12[0].equals("ofWater") && var12.length >= 2) {
                  this.ofWater = Integer.valueOf(var12[1]).intValue();
                  this.ofWater = Config.limit(this.ofWater, 0, 3);
               }

               if(var12[0].equals("ofAnimatedWater") && var12.length >= 2) {
                  this.ofAnimatedWater = Integer.valueOf(var12[1]).intValue();
                  this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
               }

               if(var12[0].equals("ofAnimatedLava") && var12.length >= 2) {
                  this.ofAnimatedLava = Integer.valueOf(var12[1]).intValue();
                  this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
               }

               if(var12[0].equals("ofAnimatedFire") && var12.length >= 2) {
                  this.ofAnimatedFire = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAnimatedPortal") && var12.length >= 2) {
                  this.ofAnimatedPortal = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAnimatedRedstone") && var12.length >= 2) {
                  this.ofAnimatedRedstone = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAnimatedExplosion") && var12.length >= 2) {
                  this.ofAnimatedExplosion = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAnimatedFlame") && var12.length >= 2) {
                  this.ofAnimatedFlame = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAnimatedSmoke") && var12.length >= 2) {
                  this.ofAnimatedSmoke = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofVoidParticles") && var12.length >= 2) {
                  this.ofVoidParticles = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofWaterParticles") && var12.length >= 2) {
                  this.ofWaterParticles = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofPortalParticles") && var12.length >= 2) {
                  this.ofPortalParticles = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofDrippingWaterLava") && var12.length >= 2) {
                  this.ofDrippingWaterLava = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAnimatedTerrain") && var12.length >= 2) {
                  this.ofAnimatedTerrain = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAnimatedTextures") && var12.length >= 2) {
                  this.ofAnimatedTextures = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAnimatedItems") && var12.length >= 2) {
                  this.ofAnimatedItems = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofRainSplash") && var12.length >= 2) {
                  this.ofRainSplash = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofLagometer") && var12.length >= 2) {
                  this.ofLagometer = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAutoSaveTicks") && var12.length >= 2) {
                  this.ofAutoSaveTicks = Integer.valueOf(var12[1]).intValue();
                  this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, '\u9c40');
               }

               if(var12[0].equals("ofBetterGrass") && var12.length >= 2) {
                  this.ofBetterGrass = Integer.valueOf(var12[1]).intValue();
                  this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
               }

               if(var12[0].equals("ofConnectedTextures") && var12.length >= 2) {
                  this.ofConnectedTextures = Integer.valueOf(var12[1]).intValue();
                  this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
               }

               if(var12[0].equals("ofWeather") && var12.length >= 2) {
                  this.ofWeather = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofSky") && var12.length >= 2) {
                  this.ofSky = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofStars") && var12.length >= 2) {
                  this.ofStars = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofSunMoon") && var12.length >= 2) {
                  this.ofSunMoon = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofChunkUpdates") && var12.length >= 2) {
                  this.ofChunkUpdates = Integer.valueOf(var12[1]).intValue();
                  this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
               }

               if(var12[0].equals("ofChunkLoading") && var12.length >= 2) {
                  this.ofChunkLoading = Integer.valueOf(var12[1]).intValue();
                  this.ofChunkLoading = Config.limit(this.ofChunkLoading, 0, 2);
                  this.updateChunkLoading();
               }

               if(var12[0].equals("ofChunkUpdatesDynamic") && var12.length >= 2) {
                  this.ofChunkUpdatesDynamic = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofTime") && var12.length >= 2) {
                  this.ofTime = Integer.valueOf(var12[1]).intValue();
                  this.ofTime = Config.limit(this.ofTime, 0, 3);
               }

               if(var12[0].equals("ofClearWater") && var12.length >= 2) {
                  this.ofClearWater = Boolean.valueOf(var12[1]).booleanValue();
                  this.updateWaterOpacity();
               }

               if(var12[0].equals("ofDepthFog") && var12.length >= 2) {
                  this.ofDepthFog = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofAaLevel") && var12.length >= 2) {
                  this.ofAaLevel = Integer.valueOf(var12[1]).intValue();
                  this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
               }

               if(var12[0].equals("ofAfLevel") && var12.length >= 2) {
                  this.ofAfLevel = Integer.valueOf(var12[1]).intValue();
                  this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
               }

               if(var12[0].equals("ofProfiler") && var12.length >= 2) {
                  this.ofProfiler = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofBetterSnow") && var12.length >= 2) {
                  this.ofBetterSnow = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofSwampColors") && var12.length >= 2) {
                  this.ofSwampColors = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofRandomMobs") && var12.length >= 2) {
                  this.ofRandomMobs = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofSmoothBiomes") && var12.length >= 2) {
                  this.ofSmoothBiomes = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofCustomFonts") && var12.length >= 2) {
                  this.ofCustomFonts = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofCustomColors") && var12.length >= 2) {
                  this.ofCustomColors = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofShowCapes") && var12.length >= 2) {
                  this.ofShowCapes = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofNaturalTextures") && var12.length >= 2) {
                  this.ofNaturalTextures = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofLazyChunkLoading") && var12.length >= 2) {
                  this.ofLazyChunkLoading = Boolean.valueOf(var12[1]).booleanValue();
               }

               if(var12[0].equals("ofFullscreenMode") && var12.length >= 2) {
                  this.ofFullscreenMode = var12[1];
               }
            } catch (Exception var5) {
               System.out.println("Skipping bad option: " + var11);
               var5.printStackTrace();
            }
         }

         KeyBinding.func_74508_b();
         var10.close();
      } catch (Exception var6) {
         System.out.println("Failed to load options");
         var6.printStackTrace();
      }

   }

   private float func_74305_a(String p_74305_1_) {
      return p_74305_1_.equals("true")?1.0F:(p_74305_1_.equals("false")?0.0F:Float.parseFloat(p_74305_1_));
   }

   public void func_74303_b() {
      if(Reflector.FMLClientHandler.exists()) {
         Object var1 = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
         if(var1 != null && Reflector.callBoolean(var1, Reflector.FMLClientHandler_isLoading, new Object[0])) {
            return;
         }
      }

      PrintWriter var5;
      try {
         var5 = new PrintWriter(new FileWriter(this.field_74354_ai));
         var5.println("music:" + this.field_74342_a);
         var5.println("sound:" + this.field_74340_b);
         var5.println("invertYMouse:" + this.field_74338_d);
         var5.println("mouseSensitivity:" + this.field_74341_c);
         var5.println("fov:" + this.field_74334_X);
         var5.println("gamma:" + this.field_74333_Y);
         var5.println("viewDistance:" + this.field_74339_e);
         var5.println("guiScale:" + this.field_74335_Z);
         var5.println("particles:" + this.field_74362_aa);
         var5.println("bobView:" + this.field_74336_f);
         var5.println("anaglyph3d:" + this.field_74337_g);
         var5.println("advancedOpengl:" + this.field_74349_h);
         var5.println("fpsLimit:" + this.field_74350_i);
         var5.println("difficulty:" + this.field_74318_M);
         var5.println("fancyGraphics:" + this.field_74347_j);
         var5.println("ao:" + this.field_74348_k);
         var5.println("clouds:" + this.field_74345_l);
         var5.println("skin:" + this.field_74346_m);
         var5.println("lastServer:" + this.field_74332_R);
         var5.println("lang:" + this.field_74363_ab);
         var5.println("chatVisibility:" + this.field_74343_n);
         var5.println("chatColors:" + this.field_74344_o);
         var5.println("chatLinks:" + this.field_74359_p);
         var5.println("chatLinksPrompt:" + this.field_74358_q);
         var5.println("chatOpacity:" + this.field_74357_r);
         var5.println("serverTextures:" + this.field_74356_s);
         var5.println("snooperEnabled:" + this.field_74355_t);
         var5.println("fullscreen:" + this.field_74353_u);
         var5.println("enableVsync:" + this.field_74352_v);
         var5.println("hideServerAddress:" + this.field_80005_w);
         var5.println("advancedItemTooltips:" + this.field_82882_x);
         var5.println("pauseOnLostFocus:" + this.field_82881_y);
         var5.println("showCape:" + this.field_82880_z);
         var5.println("touchscreen:" + this.field_85185_A);
         var5.println("overrideWidth:" + this.field_92118_B);
         var5.println("overrideHeight:" + this.field_92119_C);
         var5.println("heldItemTooltips:" + this.field_92117_D);

         for(int var2 = 0; var2 < this.field_74324_K.length; ++var2) {
            var5.println("key_" + this.field_74324_K[var2].field_74515_c + ":" + this.field_74324_K[var2].field_74512_d);
         }

         var5.close();
      } catch (Exception var4) {
         System.out.println("Failed to save options");
         var4.printStackTrace();
      }

      try {
         var5 = new PrintWriter(new FileWriter(this.optionsFileOF));
         var5.println("ofRenderDistanceFine:" + this.ofRenderDistanceFine);
         var5.println("ofLimitFramerateFine:" + this.ofLimitFramerateFine);
         var5.println("ofFogType:" + this.ofFogType);
         var5.println("ofFogStart:" + this.ofFogStart);
         var5.println("ofMipmapLevel:" + this.ofMipmapLevel);
         var5.println("ofMipmapLinear:" + this.ofMipmapLinear);
         var5.println("ofLoadFar:" + this.ofLoadFar);
         var5.println("ofPreloadedChunks:" + this.ofPreloadedChunks);
         var5.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
         var5.println("ofSmoothFps:" + this.ofSmoothFps);
         var5.println("ofSmoothWorld:" + this.ofSmoothWorld);
         var5.println("ofAoLevel:" + this.ofAoLevel);
         var5.println("ofClouds:" + this.ofClouds);
         var5.println("ofCloudsHeight:" + this.ofCloudsHeight);
         var5.println("ofTrees:" + this.ofTrees);
         var5.println("ofGrass:" + this.ofGrass);
         var5.println("ofDroppedItems:" + this.ofDroppedItems);
         var5.println("ofRain:" + this.ofRain);
         var5.println("ofWater:" + this.ofWater);
         var5.println("ofAnimatedWater:" + this.ofAnimatedWater);
         var5.println("ofAnimatedLava:" + this.ofAnimatedLava);
         var5.println("ofAnimatedFire:" + this.ofAnimatedFire);
         var5.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
         var5.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
         var5.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
         var5.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
         var5.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
         var5.println("ofVoidParticles:" + this.ofVoidParticles);
         var5.println("ofWaterParticles:" + this.ofWaterParticles);
         var5.println("ofPortalParticles:" + this.ofPortalParticles);
         var5.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
         var5.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
         var5.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
         var5.println("ofAnimatedItems:" + this.ofAnimatedItems);
         var5.println("ofRainSplash:" + this.ofRainSplash);
         var5.println("ofLagometer:" + this.ofLagometer);
         var5.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
         var5.println("ofBetterGrass:" + this.ofBetterGrass);
         var5.println("ofConnectedTextures:" + this.ofConnectedTextures);
         var5.println("ofWeather:" + this.ofWeather);
         var5.println("ofSky:" + this.ofSky);
         var5.println("ofStars:" + this.ofStars);
         var5.println("ofSunMoon:" + this.ofSunMoon);
         var5.println("ofChunkUpdates:" + this.ofChunkUpdates);
         var5.println("ofChunkLoading:" + this.ofChunkLoading);
         var5.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
         var5.println("ofTime:" + this.ofTime);
         var5.println("ofClearWater:" + this.ofClearWater);
         var5.println("ofDepthFog:" + this.ofDepthFog);
         var5.println("ofAaLevel:" + this.ofAaLevel);
         var5.println("ofAfLevel:" + this.ofAfLevel);
         var5.println("ofProfiler:" + this.ofProfiler);
         var5.println("ofBetterSnow:" + this.ofBetterSnow);
         var5.println("ofSwampColors:" + this.ofSwampColors);
         var5.println("ofRandomMobs:" + this.ofRandomMobs);
         var5.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
         var5.println("ofCustomFonts:" + this.ofCustomFonts);
         var5.println("ofCustomColors:" + this.ofCustomColors);
         var5.println("ofShowCapes:" + this.ofShowCapes);
         var5.println("ofNaturalTextures:" + this.ofNaturalTextures);
         var5.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
         var5.println("ofFullscreenMode:" + this.ofFullscreenMode);
         var5.close();
      } catch (Exception var3) {
         System.out.println("Failed to save options");
         var3.printStackTrace();
      }

      this.func_82879_c();
   }

   public void func_82879_c() {
      if(this.field_74317_L.field_71439_g != null) {
         this.field_74317_L.field_71439_g.field_71174_a.func_72552_c(new Packet204ClientInfo(this.field_74363_ab, this.field_74339_e, this.field_74343_n, this.field_74344_o, this.field_74318_M, this.field_82880_z));
      }

   }

   public void resetSettings() {
      this.field_74339_e = 1;
      this.ofRenderDistanceFine = renderDistanceToFine(this.field_74339_e);
      this.field_74336_f = true;
      this.field_74337_g = false;
      this.field_74349_h = false;
      this.field_74350_i = 0;
      this.field_74352_v = false;
      this.updateVSync();
      this.ofLimitFramerateFine = 0;
      this.field_74347_j = true;
      this.field_74348_k = true;
      this.field_74345_l = true;
      this.field_74334_X = 0.0F;
      this.field_74333_Y = 0.0F;
      this.field_74335_Z = 0;
      this.field_74362_aa = 0;
      this.field_92117_D = true;
      this.ofFogType = 1;
      this.ofFogStart = 0.8F;
      this.ofMipmapLevel = 0;
      this.ofMipmapLinear = false;
      this.ofLoadFar = false;
      this.ofPreloadedChunks = 0;
      this.ofOcclusionFancy = false;
      this.ofSmoothFps = false;
      this.ofSmoothWorld = Config.isSingleProcessor();
      this.ofLazyChunkLoading = Config.isSingleProcessor();
      if(this.field_74348_k) {
         this.ofAoLevel = 1.0F;
      } else {
         this.ofAoLevel = 0.0F;
      }

      this.ofAaLevel = 0;
      this.ofAfLevel = 1;
      this.ofClouds = 0;
      this.ofCloudsHeight = 0.0F;
      this.ofTrees = 0;
      this.ofGrass = 0;
      this.ofRain = 0;
      this.ofWater = 0;
      this.ofBetterGrass = 3;
      this.ofAutoSaveTicks = 4000;
      this.ofLagometer = false;
      this.ofProfiler = false;
      this.ofWeather = true;
      this.ofSky = true;
      this.ofStars = true;
      this.ofSunMoon = true;
      this.ofChunkUpdates = 1;
      this.ofChunkLoading = 0;
      this.ofChunkUpdatesDynamic = false;
      this.ofTime = 0;
      this.ofClearWater = false;
      this.ofDepthFog = true;
      this.ofBetterSnow = false;
      this.ofFullscreenMode = "Default";
      this.ofSwampColors = true;
      this.ofRandomMobs = true;
      this.ofSmoothBiomes = true;
      this.ofCustomFonts = true;
      this.ofCustomColors = true;
      this.ofShowCapes = true;
      this.ofConnectedTextures = 2;
      this.ofNaturalTextures = false;
      this.ofAnimatedWater = 0;
      this.ofAnimatedLava = 0;
      this.ofAnimatedFire = true;
      this.ofAnimatedPortal = true;
      this.ofAnimatedRedstone = true;
      this.ofAnimatedExplosion = true;
      this.ofAnimatedFlame = true;
      this.ofAnimatedSmoke = true;
      this.ofVoidParticles = true;
      this.ofWaterParticles = true;
      this.ofRainSplash = true;
      this.ofPortalParticles = true;
      this.ofDrippingWaterLava = true;
      this.ofAnimatedTerrain = true;
      this.ofAnimatedItems = true;
      this.ofAnimatedTextures = true;
      this.field_74317_L.field_71438_f.updateCapes();
      this.updateWaterOpacity();
      this.field_74317_L.field_71438_f.setAllRenderersVisible();
      this.field_74317_L.field_71446_o.func_78352_b();
      this.field_74317_L.field_71438_f.func_72712_a();
      this.func_74303_b();
   }

   public void updateVSync() {
      Display.setVSyncEnabled(this.field_74352_v);
   }

   private static int fineToRenderDistance(int var0) {
      byte var1 = 3;
      if(var0 > 32) {
         var1 = 2;
      }

      if(var0 > 64) {
         var1 = 1;
      }

      if(var0 > 128) {
         var1 = 0;
      }

      return var1;
   }

   private static int renderDistanceToFine(int var0) {
      return 32 << 3 - var0;
   }

   private static int fineToLimitFramerate(int var0) {
      byte var1 = 2;
      if(var0 > 35) {
         var1 = 1;
      }

      if(var0 >= 200) {
         var1 = 0;
      }

      if(var0 <= 0) {
         var1 = 0;
      }

      return var1;
   }

   private static int limitFramerateToFine(int var0) {
      switch(var0) {
      case 0:
         return 0;
      case 1:
         return 120;
      case 2:
         return 35;
      default:
         return 0;
      }
   }

   public boolean func_74309_c() {
      return this.ofRenderDistanceFine > 64 && this.field_74345_l;
   }

}
