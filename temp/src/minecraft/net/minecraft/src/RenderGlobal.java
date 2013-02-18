package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CallableParticlePositionInfo;
import net.minecraft.src.CompactArrayList;
import net.minecraft.src.Config;
import net.minecraft.src.CrashReport;
import net.minecraft.src.CrashReportCategory;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.DestroyBlockProgress;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAuraFX;
import net.minecraft.src.EntityBreakingFX;
import net.minecraft.src.EntityBubbleFX;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityCloudFX;
import net.minecraft.src.EntityCritFX;
import net.minecraft.src.EntityDiggingFX;
import net.minecraft.src.EntityDropParticleFX;
import net.minecraft.src.EntityEnchantmentTableParticleFX;
import net.minecraft.src.EntityExplodeFX;
import net.minecraft.src.EntityFX;
import net.minecraft.src.EntityFireworkSparkFX;
import net.minecraft.src.EntityFlameFX;
import net.minecraft.src.EntityFootStepFX;
import net.minecraft.src.EntityHeartFX;
import net.minecraft.src.EntityHugeExplodeFX;
import net.minecraft.src.EntityItemFrame;
import net.minecraft.src.EntityLargeExplodeFX;
import net.minecraft.src.EntityLavaFX;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityNoteFX;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPortalFX;
import net.minecraft.src.EntityReddustFX;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EntitySmokeFX;
import net.minecraft.src.EntitySnowShovelFX;
import net.minecraft.src.EntitySorter;
import net.minecraft.src.EntitySpellParticleFX;
import net.minecraft.src.EntitySplashFX;
import net.minecraft.src.EntitySuspendFX;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.ICamera;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.ImageBufferDownload;
import net.minecraft.src.Item;
import net.minecraft.src.ItemRecord;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.OpenGlCapsChecker;
import net.minecraft.src.Profiler;
import net.minecraft.src.RandomMobs;
import net.minecraft.src.Reflector;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ReportedException;
import net.minecraft.src.StringUtils;
import net.minecraft.src.Tessellator;
import net.minecraft.src.ThreadDownloadImage;
import net.minecraft.src.ThreadDownloadImageData;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.Vec3;
import net.minecraft.src.WorldClient;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.WrUpdates;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;

public class RenderGlobal implements IWorldAccess {

   public List field_72762_a = new ArrayList();
   public WorldClient field_72769_h;
   public final RenderEngine field_72770_i;
   public CompactArrayList field_72767_j = new CompactArrayList(100, 0.8F);
   private WorldRenderer[] field_72768_k;
   private WorldRenderer[] field_72765_l;
   private int field_72766_m;
   private int field_72763_n;
   private int field_72764_o;
   private int field_72778_p;
   public Minecraft field_72777_q;
   public RenderBlocks field_72776_r;
   private IntBuffer field_72775_s;
   private boolean field_72774_t = false;
   private int field_72773_u = 0;
   private int field_72772_v;
   private int field_72771_w;
   private int field_72781_x;
   private int field_72780_y;
   private int field_72779_z;
   private int field_72741_A;
   private int field_72742_B;
   private int field_72743_C;
   private int field_72737_D;
   public Map field_72738_E = new HashMap();
   private int field_72739_F = -1;
   private int field_72740_G = 2;
   private int field_72748_H;
   private int field_72749_I;
   private int field_72750_J;
   int[] field_72760_b = new int['\uc350'];
   IntBuffer field_72761_c = GLAllocation.func_74527_f(64);
   private int field_72751_K;
   private int field_72744_L;
   private int field_72745_M;
   private int field_72746_N;
   private int field_72747_O;
   private int field_72753_P;
   private int field_72752_Q;
   private IntBuffer glListBuffer = BufferUtils.createIntBuffer(65536);
   double field_72758_d = -9999.0D;
   double field_72759_e = -9999.0D;
   double field_72756_f = -9999.0D;
   int field_72757_g = 0;
   double prevReposX;
   double prevReposY;
   double prevReposZ;
   private long lastMovedTime = System.currentTimeMillis();
   private long lastActionTime = System.currentTimeMillis();


   public RenderGlobal(Minecraft p_i3194_1_, RenderEngine p_i3194_2_) {
      this.field_72777_q = p_i3194_1_;
      this.field_72770_i = p_i3194_2_;
      byte var3 = 65;
      byte var4 = 16;
      this.field_72778_p = GLAllocation.func_74526_a(var3 * var3 * var4 * 3);
      this.field_72774_t = OpenGlCapsChecker.func_74371_a();
      if(this.field_72774_t) {
         this.field_72761_c.clear();
         this.field_72775_s = GLAllocation.func_74527_f(var3 * var3 * var4);
         this.field_72775_s.clear();
         this.field_72775_s.position(0);
         this.field_72775_s.limit(var3 * var3 * var4);
         ARBOcclusionQuery.glGenQueriesARB(this.field_72775_s);
      }

      this.field_72772_v = GLAllocation.func_74526_a(3);
      GL11.glPushMatrix();
      GL11.glNewList(this.field_72772_v, 4864);
      this.func_72730_g();
      GL11.glEndList();
      GL11.glPopMatrix();
      Tessellator var5 = Tessellator.field_78398_a;
      this.field_72771_w = this.field_72772_v + 1;
      GL11.glNewList(this.field_72771_w, 4864);
      byte var6 = 64;
      int var7 = 256 / var6 + 2;
      float var8 = 16.0F;

      int var9;
      int var10;
      for(var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6) {
         for(var10 = -var6 * var7; var10 <= var6 * var7; var10 += var6) {
            var5.func_78382_b();
            var5.func_78377_a((double)(var9 + 0), (double)var8, (double)(var10 + 0));
            var5.func_78377_a((double)(var9 + var6), (double)var8, (double)(var10 + 0));
            var5.func_78377_a((double)(var9 + var6), (double)var8, (double)(var10 + var6));
            var5.func_78377_a((double)(var9 + 0), (double)var8, (double)(var10 + var6));
            var5.func_78381_a();
         }
      }

      GL11.glEndList();
      this.field_72781_x = this.field_72772_v + 2;
      GL11.glNewList(this.field_72781_x, 4864);
      var8 = -16.0F;
      var5.func_78382_b();

      for(var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6) {
         for(var10 = -var6 * var7; var10 <= var6 * var7; var10 += var6) {
            var5.func_78377_a((double)(var9 + var6), (double)var8, (double)(var10 + 0));
            var5.func_78377_a((double)(var9 + 0), (double)var8, (double)(var10 + 0));
            var5.func_78377_a((double)(var9 + 0), (double)var8, (double)(var10 + var6));
            var5.func_78377_a((double)(var9 + var6), (double)var8, (double)(var10 + var6));
         }
      }

      var5.func_78381_a();
      GL11.glEndList();
      this.field_72770_i.func_78343_a();
   }

   private void func_72730_g() {
      Random var1 = new Random(10842L);
      Tessellator var2 = Tessellator.field_78398_a;
      var2.func_78382_b();

      for(int var3 = 0; var3 < 1500; ++var3) {
         double var4 = (double)(var1.nextFloat() * 2.0F - 1.0F);
         double var6 = (double)(var1.nextFloat() * 2.0F - 1.0F);
         double var8 = (double)(var1.nextFloat() * 2.0F - 1.0F);
         double var10 = (double)(0.15F + var1.nextFloat() * 0.1F);
         double var12 = var4 * var4 + var6 * var6 + var8 * var8;
         if(var12 < 1.0D && var12 > 0.01D) {
            var12 = 1.0D / Math.sqrt(var12);
            var4 *= var12;
            var6 *= var12;
            var8 *= var12;
            double var14 = var4 * 100.0D;
            double var16 = var6 * 100.0D;
            double var18 = var8 * 100.0D;
            double var20 = Math.atan2(var4, var8);
            double var22 = Math.sin(var20);
            double var24 = Math.cos(var20);
            double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
            double var28 = Math.sin(var26);
            double var30 = Math.cos(var26);
            double var32 = var1.nextDouble() * 3.141592653589793D * 2.0D;
            double var34 = Math.sin(var32);
            double var36 = Math.cos(var32);

            for(int var38 = 0; var38 < 4; ++var38) {
               double var39 = 0.0D;
               double var41 = (double)((var38 & 2) - 1) * var10;
               double var43 = (double)((var38 + 1 & 2) - 1) * var10;
               double var45 = var41 * var36 - var43 * var34;
               double var47 = var43 * var36 + var41 * var34;
               double var49 = var45 * var28 + var39 * var30;
               double var51 = var39 * var28 - var45 * var30;
               double var53 = var51 * var22 - var47 * var24;
               double var55 = var47 * var22 + var51 * var24;
               var2.func_78377_a(var14 + var53, var16 + var49, var18 + var55);
            }
         }
      }

      var2.func_78381_a();
   }

   public void func_72732_a(WorldClient p_72732_1_) {
      if(this.field_72769_h != null) {
         this.field_72769_h.func_72848_b(this);
      }

      this.field_72758_d = -9999.0D;
      this.field_72759_e = -9999.0D;
      this.field_72756_f = -9999.0D;
      RenderManager.field_78727_a.func_78717_a(p_72732_1_);
      this.field_72769_h = p_72732_1_;
      this.field_72776_r = new RenderBlocks(p_72732_1_);
      if(p_72732_1_ != null) {
         p_72732_1_.func_72954_a(this);
         this.func_72712_a();
      }

   }

   public void func_72712_a() {
      if(this.field_72769_h != null) {
         Block.field_71952_K.func_72133_a(Config.isTreesFancy());
         this.field_72739_F = this.field_72777_q.field_71474_y.field_74339_e;
         int var1;
         if(this.field_72765_l != null) {
            for(var1 = 0; var1 < this.field_72765_l.length; ++var1) {
               this.field_72765_l[var1].func_78911_c();
            }
         }

         var1 = 64 << 3 - this.field_72739_F;
         short var2 = 512;
         var1 = 2 * this.field_72777_q.field_71474_y.ofRenderDistanceFine;
         if(Config.isLoadChunksFar() && var1 < var2) {
            var1 = var2;
         }

         var1 += Config.getPreloadedChunks() * 2 * 16;
         short var3 = 400;
         if(this.field_72777_q.field_71474_y.ofRenderDistanceFine > 256) {
            var3 = 1024;
         }

         if(var1 > var3) {
            var1 = var3;
         }

         this.prevReposX = -9999.0D;
         this.prevReposY = -9999.0D;
         this.prevReposZ = -9999.0D;
         this.field_72766_m = var1 / 16 + 1;
         this.field_72763_n = 16;
         this.field_72764_o = var1 / 16 + 1;
         this.field_72765_l = new WorldRenderer[this.field_72766_m * this.field_72763_n * this.field_72764_o];
         this.field_72768_k = new WorldRenderer[this.field_72766_m * this.field_72763_n * this.field_72764_o];
         int var4 = 0;
         int var5 = 0;
         this.field_72780_y = 0;
         this.field_72779_z = 0;
         this.field_72741_A = 0;
         this.field_72742_B = this.field_72766_m;
         this.field_72743_C = this.field_72763_n;
         this.field_72737_D = this.field_72764_o;

         int var7;
         for(var7 = 0; var7 < this.field_72767_j.size(); ++var7) {
            WorldRenderer var8 = (WorldRenderer)this.field_72767_j.get(var7);
            if(var8 != null) {
               var8.field_78939_q = false;
            }
         }

         this.field_72767_j.clear();
         this.field_72762_a.clear();

         for(var7 = 0; var7 < this.field_72766_m; ++var7) {
            for(int var12 = 0; var12 < this.field_72763_n; ++var12) {
               for(int var9 = 0; var9 < this.field_72764_o; ++var9) {
                  int var10 = (var9 * this.field_72763_n + var12) * this.field_72766_m + var7;
                  this.field_72765_l[var10] = WrUpdates.makeWorldRenderer(this.field_72769_h, this.field_72762_a, var7 * 16, var12 * 16, var9 * 16, this.field_72778_p + var4);
                  if(this.field_72774_t) {
                     this.field_72765_l[var10].field_78934_v = this.field_72775_s.get(var5);
                  }

                  this.field_72765_l[var10].field_78935_u = false;
                  this.field_72765_l[var10].field_78936_t = true;
                  this.field_72765_l[var10].field_78927_l = false;
                  this.field_72765_l[var10].field_78937_s = var5++;
                  this.field_72768_k[var10] = this.field_72765_l[var10];
                  if(this.field_72769_h.func_72916_c(var7, var9)) {
                     this.field_72765_l[var10].func_78914_f();
                     this.field_72767_j.add(this.field_72765_l[var10]);
                  }

                  var4 += 3;
               }
            }
         }

         if(this.field_72769_h != null) {
            Object var11 = this.field_72777_q.field_71451_h;
            if(var11 == null) {
               var11 = this.field_72777_q.field_71439_g;
            }

            if(var11 != null) {
               this.func_72722_c(MathHelper.func_76128_c(((EntityLiving)var11).field_70165_t), MathHelper.func_76128_c(((EntityLiving)var11).field_70163_u), MathHelper.func_76128_c(((EntityLiving)var11).field_70161_v));
               Arrays.sort(this.field_72768_k, new EntitySorter((Entity)var11));
            }
         }

         this.field_72740_G = 2;
      }

   }

   public void func_72713_a(Vec3 p_72713_1_, ICamera p_72713_2_, float p_72713_3_) {
      if(this.field_72740_G > 0) {
         --this.field_72740_G;
      } else {
         this.field_72769_h.field_72984_F.func_76320_a("prepare");
         TileEntityRenderer.field_76963_a.func_76953_a(this.field_72769_h, this.field_72770_i, this.field_72777_q.field_71466_p, this.field_72777_q.field_71451_h, p_72713_3_);
         RenderManager.field_78727_a.func_78718_a(this.field_72769_h, this.field_72770_i, this.field_72777_q.field_71466_p, this.field_72777_q.field_71451_h, this.field_72777_q.field_71474_y, p_72713_3_);
         this.field_72748_H = 0;
         this.field_72749_I = 0;
         this.field_72750_J = 0;
         EntityLiving var4 = this.field_72777_q.field_71451_h;
         RenderManager.field_78725_b = var4.field_70142_S + (var4.field_70165_t - var4.field_70142_S) * (double)p_72713_3_;
         RenderManager.field_78726_c = var4.field_70137_T + (var4.field_70163_u - var4.field_70137_T) * (double)p_72713_3_;
         RenderManager.field_78723_d = var4.field_70136_U + (var4.field_70161_v - var4.field_70136_U) * (double)p_72713_3_;
         TileEntityRenderer.field_76961_b = var4.field_70142_S + (var4.field_70165_t - var4.field_70142_S) * (double)p_72713_3_;
         TileEntityRenderer.field_76962_c = var4.field_70137_T + (var4.field_70163_u - var4.field_70137_T) * (double)p_72713_3_;
         TileEntityRenderer.field_76959_d = var4.field_70136_U + (var4.field_70161_v - var4.field_70136_U) * (double)p_72713_3_;
         this.field_72777_q.field_71460_t.func_78463_b((double)p_72713_3_);
         this.field_72769_h.field_72984_F.func_76318_c("global");
         List var5 = this.field_72769_h.func_72910_y();
         this.field_72748_H = var5.size();

         int var6;
         Entity var7;
         for(var6 = 0; var6 < this.field_72769_h.field_73007_j.size(); ++var6) {
            var7 = (Entity)this.field_72769_h.field_73007_j.get(var6);
            ++this.field_72749_I;
            if(var7.func_70102_a(p_72713_1_)) {
               RenderManager.field_78727_a.func_78720_a(var7, p_72713_3_);
            }
         }

         this.field_72769_h.field_72984_F.func_76318_c("entities");

         for(var6 = 0; var6 < var5.size(); ++var6) {
            var7 = (Entity)var5.get(var6);
            if(var7.func_70102_a(p_72713_1_) && (var7.field_70158_ak || p_72713_2_.func_78546_a(var7.field_70121_D) || var7.field_70153_n == this.field_72777_q.field_71439_g) && (var7 != this.field_72777_q.field_71451_h || this.field_72777_q.field_71474_y.field_74320_O != 0 || this.field_72777_q.field_71451_h.func_70608_bn()) && this.field_72769_h.func_72899_e(MathHelper.func_76128_c(var7.field_70165_t), 0, MathHelper.func_76128_c(var7.field_70161_v))) {
               ++this.field_72749_I;
               if(var7.getClass() == EntityItemFrame.class) {
                  var7.field_70155_l = 0.06D;
               }

               RenderManager.field_78727_a.func_78720_a(var7, p_72713_3_);
            }
         }

         this.field_72769_h.field_72984_F.func_76318_c("tileentities");
         RenderHelper.func_74519_b();

         for(var6 = 0; var6 < this.field_72762_a.size(); ++var6) {
            TileEntity var8 = (TileEntity)this.field_72762_a.get(var6);
            Class var9 = var8.getClass();
            if(var9 == TileEntitySign.class && !Config.zoomMode) {
               EntityClientPlayerMP var10 = this.field_72777_q.field_71439_g;
               double var11 = var8.func_70318_a(var10.field_70165_t, var10.field_70163_u, var10.field_70161_v);
               if(var11 > 256.0D) {
                  FontRenderer var13 = TileEntityRenderer.field_76963_a.func_76954_a();
                  var13.enabled = false;
                  TileEntityRenderer.field_76963_a.func_76950_a(var8, p_72713_3_);
                  var13.enabled = true;
                  continue;
               }
            }

            if(var9 == TileEntityChest.class) {
               int var14 = this.field_72769_h.func_72798_a(var8.field_70329_l, var8.field_70330_m, var8.field_70327_n);
               if(var14 != Block.field_72077_au.field_71990_ca) {
                  continue;
               }
            }

            TileEntityRenderer.field_76963_a.func_76950_a(var8, p_72713_3_);
         }

         this.field_72777_q.field_71460_t.func_78483_a((double)p_72713_3_);
         this.field_72769_h.field_72984_F.func_76319_b();
      }

   }

   public String func_72735_c() {
      return "C: " + this.field_72746_N + "/" + this.field_72751_K + ". F: " + this.field_72744_L + ", O: " + this.field_72745_M + ", E: " + this.field_72747_O;
   }

   public String func_72723_d() {
      return "E: " + this.field_72749_I + "/" + this.field_72748_H + ". B: " + this.field_72750_J + ", I: " + (this.field_72748_H - this.field_72750_J - this.field_72749_I) + ", " + Config.getVersion();
   }

   private void func_72722_c(int p_72722_1_, int p_72722_2_, int p_72722_3_) {
      p_72722_1_ -= 8;
      p_72722_2_ -= 8;
      p_72722_3_ -= 8;
      this.field_72780_y = Integer.MAX_VALUE;
      this.field_72779_z = Integer.MAX_VALUE;
      this.field_72741_A = Integer.MAX_VALUE;
      this.field_72742_B = Integer.MIN_VALUE;
      this.field_72743_C = Integer.MIN_VALUE;
      this.field_72737_D = Integer.MIN_VALUE;
      int var4 = this.field_72766_m * 16;
      int var5 = var4 / 2;

      for(int var6 = 0; var6 < this.field_72766_m; ++var6) {
         int var7 = var6 * 16;
         int var8 = var7 + var5 - p_72722_1_;
         if(var8 < 0) {
            var8 -= var4 - 1;
         }

         var8 /= var4;
         var7 -= var8 * var4;
         if(var7 < this.field_72780_y) {
            this.field_72780_y = var7;
         }

         if(var7 > this.field_72742_B) {
            this.field_72742_B = var7;
         }

         for(int var9 = 0; var9 < this.field_72764_o; ++var9) {
            int var10 = var9 * 16;
            int var11 = var10 + var5 - p_72722_3_;
            if(var11 < 0) {
               var11 -= var4 - 1;
            }

            var11 /= var4;
            var10 -= var11 * var4;
            if(var10 < this.field_72741_A) {
               this.field_72741_A = var10;
            }

            if(var10 > this.field_72737_D) {
               this.field_72737_D = var10;
            }

            for(int var12 = 0; var12 < this.field_72763_n; ++var12) {
               int var13 = var12 * 16;
               if(var13 < this.field_72779_z) {
                  this.field_72779_z = var13;
               }

               if(var13 > this.field_72743_C) {
                  this.field_72743_C = var13;
               }

               WorldRenderer var14 = this.field_72765_l[(var9 * this.field_72763_n + var12) * this.field_72766_m + var6];
               boolean var15 = var14.field_78939_q;
               var14.func_78913_a(var7, var13, var10);
               if(!var15 && var14.field_78939_q) {
                  this.field_72767_j.add(var14);
               }
            }
         }
      }

   }

   public int func_72719_a(EntityLiving p_72719_1_, int p_72719_2_, double p_72719_3_) {
      Profiler var5 = this.field_72769_h.field_72984_F;
      var5.func_76320_a("sortchunks");
      if(this.field_72767_j.size() < 10) {
         byte var6 = 10;

         for(int var7 = 0; var7 < var6; ++var7) {
            this.field_72752_Q = (this.field_72752_Q + 1) % this.field_72765_l.length;
            WorldRenderer var8 = this.field_72765_l[this.field_72752_Q];
            if(var8.field_78939_q && !this.field_72767_j.contains(var8)) {
               this.field_72767_j.add(var8);
            }
         }
      }

      if(this.field_72777_q.field_71474_y.field_74339_e != this.field_72739_F && !Config.isLoadChunksFar()) {
         this.func_72712_a();
      }

      if(p_72719_2_ == 0) {
         this.field_72751_K = 0;
         this.field_72753_P = 0;
         this.field_72744_L = 0;
         this.field_72745_M = 0;
         this.field_72746_N = 0;
         this.field_72747_O = 0;
      }

      double var40 = p_72719_1_.field_70142_S + (p_72719_1_.field_70165_t - p_72719_1_.field_70142_S) * p_72719_3_;
      double var41 = p_72719_1_.field_70137_T + (p_72719_1_.field_70163_u - p_72719_1_.field_70137_T) * p_72719_3_;
      double var10 = p_72719_1_.field_70136_U + (p_72719_1_.field_70161_v - p_72719_1_.field_70136_U) * p_72719_3_;
      double var12 = p_72719_1_.field_70165_t - this.field_72758_d;
      double var14 = p_72719_1_.field_70163_u - this.field_72759_e;
      double var16 = p_72719_1_.field_70161_v - this.field_72756_f;
      double var18 = var12 * var12 + var14 * var14 + var16 * var16;
      int var20;
      if(var18 > 16.0D) {
         this.field_72758_d = p_72719_1_.field_70165_t;
         this.field_72759_e = p_72719_1_.field_70163_u;
         this.field_72756_f = p_72719_1_.field_70161_v;
         var20 = Config.getPreloadedChunks() * 16;
         double var21 = p_72719_1_.field_70165_t - this.prevReposX;
         double var23 = p_72719_1_.field_70163_u - this.prevReposY;
         double var25 = p_72719_1_.field_70161_v - this.prevReposZ;
         double var27 = var21 * var21 + var23 * var23 + var25 * var25;
         if(var27 > (double)(var20 * var20) + 16.0D) {
            this.prevReposX = p_72719_1_.field_70165_t;
            this.prevReposY = p_72719_1_.field_70163_u;
            this.prevReposZ = p_72719_1_.field_70161_v;
            this.func_72722_c(MathHelper.func_76128_c(p_72719_1_.field_70165_t), MathHelper.func_76128_c(p_72719_1_.field_70163_u), MathHelper.func_76128_c(p_72719_1_.field_70161_v));
         }

         Arrays.sort(this.field_72768_k, new EntitySorter(p_72719_1_));
         int var29 = (int)p_72719_1_.field_70165_t;
         int var30 = (int)p_72719_1_.field_70161_v;
         short var31 = 2000;
         if(Math.abs(var29 - WorldRenderer.globalChunkOffsetX) > var31 || Math.abs(var30 - WorldRenderer.globalChunkOffsetZ) > var31) {
            WorldRenderer.globalChunkOffsetX = var29;
            WorldRenderer.globalChunkOffsetZ = var30;
            this.func_72712_a();
         }
      }

      RenderHelper.func_74518_a();
      WrUpdates.preRender(this, p_72719_1_);
      if(this.field_72777_q.field_71474_y.ofSmoothFps && p_72719_2_ == 0) {
         GL11.glFinish();
      }

      byte var42 = 0;
      int var44 = 0;
      if(this.field_72774_t && this.field_72777_q.field_71474_y.field_74349_h && !this.field_72777_q.field_71474_y.field_74337_g && p_72719_2_ == 0) {
         byte var22 = 0;
         byte var43 = 20;
         this.checkOcclusionQueryResult(var22, var43, p_72719_1_.field_70165_t, p_72719_1_.field_70163_u, p_72719_1_.field_70161_v);

         int var24;
         for(var24 = var22; var24 < var43; ++var24) {
            this.field_72768_k[var24].field_78936_t = true;
         }

         var5.func_76318_c("render");
         var20 = var42 + this.func_72724_a(var22, var43, p_72719_2_, p_72719_3_);
         var24 = var43;
         int var45 = 0;
         byte var26 = 40;

         int var28;
         for(int var46 = this.field_72766_m; var24 < this.field_72768_k.length; var20 += this.func_72724_a(var28, var24, p_72719_2_, p_72719_3_)) {
            var5.func_76318_c("occ");
            var28 = var24;
            if(var45 < var46) {
               ++var45;
            } else {
               --var45;
            }

            var24 += var45 * var26;
            if(var24 <= var28) {
               var24 = var28 + 10;
            }

            if(var24 > this.field_72768_k.length) {
               var24 = this.field_72768_k.length;
            }

            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(3008);
            GL11.glDisable(2912);
            GL11.glColorMask(false, false, false, false);
            GL11.glDepthMask(false);
            var5.func_76320_a("check");
            this.checkOcclusionQueryResult(var28, var24, p_72719_1_.field_70165_t, p_72719_1_.field_70163_u, p_72719_1_.field_70161_v);
            var5.func_76319_b();
            GL11.glPushMatrix();
            float var49 = 0.0F;
            float var47 = 0.0F;
            float var48 = 0.0F;

            for(int var32 = var28; var32 < var24; ++var32) {
               WorldRenderer var33 = this.field_72768_k[var32];
               if(var33.func_78906_e()) {
                  var33.field_78927_l = false;
               } else if(var33.isUpdating) {
                  var33.field_78936_t = true;
               } else if(var33.field_78927_l) {
                  if(Config.isOcclusionFancy() && !var33.isInFrustrumFully) {
                     var33.field_78936_t = true;
                  } else if(var33.field_78927_l && !var33.field_78935_u) {
                     float var34;
                     float var35;
                     float var36;
                     float var37;
                     if(var33.isVisibleFromPosition) {
                        var34 = Math.abs((float)(var33.visibleFromX - p_72719_1_.field_70165_t));
                        var35 = Math.abs((float)(var33.visibleFromY - p_72719_1_.field_70163_u));
                        var36 = Math.abs((float)(var33.visibleFromZ - p_72719_1_.field_70161_v));
                        var37 = var34 + var35 + var36;
                        if((double)var37 < 10.0D + (double)var32 / 1000.0D) {
                           var33.field_78936_t = true;
                           continue;
                        }

                        var33.isVisibleFromPosition = false;
                     }

                     var34 = (float)((double)var33.field_78918_f - var40);
                     var35 = (float)((double)var33.field_78919_g - var41);
                     var36 = (float)((double)var33.field_78931_h - var10);
                     var37 = var34 - var49;
                     float var38 = var35 - var47;
                     float var39 = var36 - var48;
                     if(var37 != 0.0F || var38 != 0.0F || var39 != 0.0F) {
                        GL11.glTranslatef(var37, var38, var39);
                        var49 += var37;
                        var47 += var38;
                        var48 += var39;
                     }

                     var5.func_76320_a("bb");
                     ARBOcclusionQuery.glBeginQueryARB('\u8914', var33.field_78934_v);
                     var33.func_78904_d();
                     ARBOcclusionQuery.glEndQueryARB('\u8914');
                     var5.func_76319_b();
                     var33.field_78935_u = true;
                     ++var44;
                  }
               }
            }

            GL11.glPopMatrix();
            if(this.field_72777_q.field_71474_y.field_74337_g) {
               if(EntityRenderer.field_78515_b == 0) {
                  GL11.glColorMask(false, true, true, true);
               } else {
                  GL11.glColorMask(true, false, false, true);
               }
            } else {
               GL11.glColorMask(true, true, true, true);
            }

            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glEnable(2912);
            var5.func_76318_c("render");
         }
      } else {
         var5.func_76318_c("render");
         var20 = var42 + this.func_72724_a(0, this.field_72768_k.length, p_72719_2_, p_72719_3_);
      }

      var5.func_76319_b();
      WrUpdates.postRender();
      return var20;
   }

   private void checkOcclusionQueryResult(int var1, int var2, double var3, double var5, double var7) {
      for(int var9 = var1; var9 < var2; ++var9) {
         WorldRenderer var10 = this.field_72768_k[var9];
         if(var10.field_78935_u) {
            this.field_72761_c.clear();
            ARBOcclusionQuery.glGetQueryObjectuARB(var10.field_78934_v, '\u8867', this.field_72761_c);
            if(this.field_72761_c.get(0) != 0) {
               var10.field_78935_u = false;
               this.field_72761_c.clear();
               ARBOcclusionQuery.glGetQueryObjectuARB(var10.field_78934_v, '\u8866', this.field_72761_c);
               boolean var11 = var10.field_78936_t;
               var10.field_78936_t = this.field_72761_c.get(0) > 0;
               if(var11 && var10.field_78936_t) {
                  var10.isVisibleFromPosition = true;
                  var10.visibleFromX = var3;
                  var10.visibleFromY = var5;
                  var10.visibleFromZ = var7;
               }
            }
         }
      }

   }

   private int func_72724_a(int p_72724_1_, int p_72724_2_, int p_72724_3_, double p_72724_4_) {
      this.glListBuffer.clear();
      int var6 = 0;

      for(int var7 = p_72724_1_; var7 < p_72724_2_; ++var7) {
         WorldRenderer var8 = this.field_72768_k[var7];
         if(p_72724_3_ == 0) {
            ++this.field_72751_K;
            if(var8.field_78928_m[p_72724_3_]) {
               ++this.field_72747_O;
            } else if(!var8.field_78927_l) {
               ++this.field_72744_L;
            } else if(this.field_72774_t && !var8.field_78936_t) {
               ++this.field_72745_M;
            } else {
               ++this.field_72746_N;
            }
         }

         if(var8.field_78927_l && !var8.field_78928_m[p_72724_3_] && (!this.field_72774_t || var8.field_78936_t)) {
            int var9 = var8.func_78909_a(p_72724_3_);
            if(var9 >= 0) {
               this.glListBuffer.put(var9);
               ++var6;
            }
         }
      }

      if(var6 == 0) {
         return 0;
      } else {
         if(Config.isFogOff()) {
            GL11.glDisable(2912);
         }

         this.glListBuffer.flip();
         EntityLiving var14 = this.field_72777_q.field_71451_h;
         double var15 = var14.field_70142_S + (var14.field_70165_t - var14.field_70142_S) * p_72724_4_ - (double)WorldRenderer.globalChunkOffsetX;
         double var10 = var14.field_70137_T + (var14.field_70163_u - var14.field_70137_T) * p_72724_4_;
         double var12 = var14.field_70136_U + (var14.field_70161_v - var14.field_70136_U) * p_72724_4_ - (double)WorldRenderer.globalChunkOffsetZ;
         this.field_72777_q.field_71460_t.func_78463_b(p_72724_4_);
         GL11.glTranslatef((float)(-var15), (float)(-var10), (float)(-var12));
         GL11.glCallLists(this.glListBuffer);
         GL11.glTranslatef((float)var15, (float)var10, (float)var12);
         this.field_72777_q.field_71460_t.func_78483_a(p_72724_4_);
         return var6;
      }
   }

   public void func_72733_a(int p_72733_1_, double p_72733_2_) {}

   public void func_72734_e() {
      ++this.field_72773_u;
      if(this.field_72773_u % 20 == 0) {
         Iterator var1 = this.field_72738_E.values().iterator();

         while(var1.hasNext()) {
            DestroyBlockProgress var2 = (DestroyBlockProgress)var1.next();
            int var3 = var2.func_82743_f();
            if(this.field_72773_u - var3 > 400) {
               var1.remove();
            }
         }
      }

   }

   public void func_72714_a(float p_72714_1_) {
      if(Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
         WorldProvider var2 = this.field_72777_q.field_71441_e.field_73011_w;
         Object var3 = Reflector.call(var2, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0]);
         if(var3 != null) {
            Reflector.callVoid(var3, Reflector.IRenderHandler_render, new Object[]{Float.valueOf(p_72714_1_), this.field_72769_h, this.field_72777_q});
            return;
         }
      }

      if(this.field_72777_q.field_71441_e.field_73011_w.field_76574_g == 1) {
         if(!Config.isSkyEnabled()) {
            return;
         }

         GL11.glDisable(2912);
         GL11.glDisable(3008);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         RenderHelper.func_74518_a();
         GL11.glDepthMask(false);
         this.field_72770_i.func_78342_b(this.field_72770_i.func_78341_b("/misc/tunnel.png"));
         Tessellator var20 = Tessellator.field_78398_a;

         for(int var22 = 0; var22 < 6; ++var22) {
            GL11.glPushMatrix();
            if(var22 == 1) {
               GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if(var22 == 2) {
               GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if(var22 == 3) {
               GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if(var22 == 4) {
               GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if(var22 == 5) {
               GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            var20.func_78382_b();
            var20.func_78378_d(2631720);
            var20.func_78374_a(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
            var20.func_78374_a(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
            var20.func_78374_a(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
            var20.func_78374_a(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
            var20.func_78381_a();
            GL11.glPopMatrix();
         }

         GL11.glDepthMask(true);
         GL11.glEnable(3553);
         GL11.glEnable(3008);
      } else if(this.field_72777_q.field_71441_e.field_73011_w.func_76569_d()) {
         GL11.glDisable(3553);
         Vec3 var21 = this.field_72769_h.func_72833_a(this.field_72777_q.field_71451_h, p_72714_1_);
         var21 = CustomColorizer.getSkyColor(var21, this.field_72777_q.field_71441_e, this.field_72777_q.field_71451_h.field_70165_t, this.field_72777_q.field_71451_h.field_70163_u + 1.0D, this.field_72777_q.field_71451_h.field_70161_v);
         float var23 = (float)var21.field_72450_a;
         float var4 = (float)var21.field_72448_b;
         float var5 = (float)var21.field_72449_c;
         float var6;
         if(this.field_72777_q.field_71474_y.field_74337_g) {
            float var7 = (var23 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
            float var8 = (var23 * 30.0F + var4 * 70.0F) / 100.0F;
            var6 = (var23 * 30.0F + var5 * 70.0F) / 100.0F;
            var23 = var7;
            var4 = var8;
            var5 = var6;
         }

         GL11.glColor3f(var23, var4, var5);
         Tessellator var24 = Tessellator.field_78398_a;
         GL11.glDepthMask(false);
         GL11.glEnable(2912);
         GL11.glColor3f(var23, var4, var5);
         if(Config.isSkyEnabled()) {
            GL11.glCallList(this.field_72771_w);
         }

         GL11.glDisable(2912);
         GL11.glDisable(3008);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         RenderHelper.func_74518_a();
         float[] var25 = this.field_72769_h.field_73011_w.func_76560_a(this.field_72769_h.func_72826_c(p_72714_1_), p_72714_1_);
         float var9;
         float var10;
         float var11;
         float var12;
         float var13;
         int var15;
         float var17;
         float var16;
         if(var25 != null && Config.isSunMoonEnabled()) {
            GL11.glDisable(3553);
            GL11.glShadeModel(7425);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(MathHelper.func_76126_a(this.field_72769_h.func_72929_e(p_72714_1_)) < 0.0F?180.0F:0.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            var6 = var25[0];
            var9 = var25[1];
            var10 = var25[2];
            if(this.field_72777_q.field_71474_y.field_74337_g) {
               var11 = (var6 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
               var12 = (var6 * 30.0F + var9 * 70.0F) / 100.0F;
               var13 = (var6 * 30.0F + var10 * 70.0F) / 100.0F;
               var6 = var11;
               var9 = var12;
               var10 = var13;
            }

            var24.func_78371_b(6);
            var24.func_78369_a(var6, var9, var10, var25[3]);
            var24.func_78377_a(0.0D, 100.0D, 0.0D);
            byte var14 = 16;
            var24.func_78369_a(var25[0], var25[1], var25[2], 0.0F);

            for(var15 = 0; var15 <= var14; ++var15) {
               var13 = (float)var15 * 3.1415927F * 2.0F / (float)var14;
               var16 = MathHelper.func_76126_a(var13);
               var17 = MathHelper.func_76134_b(var13);
               var24.func_78377_a((double)(var16 * 120.0F), (double)(var17 * 120.0F), (double)(-var17 * 40.0F * var25[3]));
            }

            var24.func_78381_a();
            GL11.glPopMatrix();
            GL11.glShadeModel(7424);
         }

         GL11.glEnable(3553);
         GL11.glBlendFunc(770, 1);
         GL11.glPushMatrix();
         var6 = 1.0F - this.field_72769_h.func_72867_j(p_72714_1_);
         var9 = 0.0F;
         var10 = 0.0F;
         var11 = 0.0F;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, var6);
         GL11.glTranslatef(var9, var10, var11);
         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.field_72769_h.func_72826_c(p_72714_1_) * 360.0F, 1.0F, 0.0F, 0.0F);
         if(Config.isSunMoonEnabled()) {
            var12 = 30.0F;
            GL11.glBindTexture(3553, this.field_72770_i.func_78341_b("/terrain/sun.png"));
            var24.func_78382_b();
            var24.func_78374_a((double)(-var12), 100.0D, (double)(-var12), 0.0D, 0.0D);
            var24.func_78374_a((double)var12, 100.0D, (double)(-var12), 1.0D, 0.0D);
            var24.func_78374_a((double)var12, 100.0D, (double)var12, 1.0D, 1.0D);
            var24.func_78374_a((double)(-var12), 100.0D, (double)var12, 0.0D, 1.0D);
            var24.func_78381_a();
            var12 = 20.0F;
            GL11.glBindTexture(3553, this.field_72770_i.func_78341_b("/terrain/moon_phases.png"));
            int var26 = this.field_72769_h.func_72853_d(p_72714_1_);
            int var27 = var26 % 4;
            var15 = var26 / 4 % 2;
            var16 = (float)(var27 + 0) / 4.0F;
            var17 = (float)(var15 + 0) / 2.0F;
            float var18 = (float)(var27 + 1) / 4.0F;
            float var19 = (float)(var15 + 1) / 2.0F;
            var24.func_78382_b();
            var24.func_78374_a((double)(-var12), -100.0D, (double)var12, (double)var18, (double)var19);
            var24.func_78374_a((double)var12, -100.0D, (double)var12, (double)var16, (double)var19);
            var24.func_78374_a((double)var12, -100.0D, (double)(-var12), (double)var16, (double)var17);
            var24.func_78374_a((double)(-var12), -100.0D, (double)(-var12), (double)var18, (double)var17);
            var24.func_78381_a();
         }

         GL11.glDisable(3553);
         var13 = this.field_72769_h.func_72880_h(p_72714_1_) * var6;
         if(var13 > 0.0F && Config.isStarsEnabled()) {
            GL11.glColor4f(var13, var13, var13, var13);
            GL11.glCallList(this.field_72772_v);
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDisable(3042);
         GL11.glEnable(3008);
         GL11.glEnable(2912);
         GL11.glPopMatrix();
         GL11.glDisable(3553);
         GL11.glColor3f(0.0F, 0.0F, 0.0F);
         double var28 = this.field_72777_q.field_71439_g.func_70666_h(p_72714_1_).field_72448_b - this.field_72769_h.func_72919_O();
         if(var28 < 0.0D) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.field_72781_x);
            GL11.glPopMatrix();
            var10 = 1.0F;
            var11 = -((float)(var28 + 65.0D));
            var12 = -var10;
            var24.func_78382_b();
            var24.func_78384_a(0, 255);
            var24.func_78377_a((double)(-var10), (double)var11, (double)var10);
            var24.func_78377_a((double)var10, (double)var11, (double)var10);
            var24.func_78377_a((double)var10, (double)var12, (double)var10);
            var24.func_78377_a((double)(-var10), (double)var12, (double)var10);
            var24.func_78377_a((double)(-var10), (double)var12, (double)(-var10));
            var24.func_78377_a((double)var10, (double)var12, (double)(-var10));
            var24.func_78377_a((double)var10, (double)var11, (double)(-var10));
            var24.func_78377_a((double)(-var10), (double)var11, (double)(-var10));
            var24.func_78377_a((double)var10, (double)var12, (double)(-var10));
            var24.func_78377_a((double)var10, (double)var12, (double)var10);
            var24.func_78377_a((double)var10, (double)var11, (double)var10);
            var24.func_78377_a((double)var10, (double)var11, (double)(-var10));
            var24.func_78377_a((double)(-var10), (double)var11, (double)(-var10));
            var24.func_78377_a((double)(-var10), (double)var11, (double)var10);
            var24.func_78377_a((double)(-var10), (double)var12, (double)var10);
            var24.func_78377_a((double)(-var10), (double)var12, (double)(-var10));
            var24.func_78377_a((double)(-var10), (double)var12, (double)(-var10));
            var24.func_78377_a((double)(-var10), (double)var12, (double)var10);
            var24.func_78377_a((double)var10, (double)var12, (double)var10);
            var24.func_78377_a((double)var10, (double)var12, (double)(-var10));
            var24.func_78381_a();
         }

         if(this.field_72769_h.field_73011_w.func_76561_g()) {
            GL11.glColor3f(var23 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
         } else {
            GL11.glColor3f(var23, var4, var5);
         }

         if(this.field_72777_q.field_71474_y.ofRenderDistanceFine <= 64) {
            GL11.glColor3f(this.field_72777_q.field_71460_t.field_78518_n, this.field_72777_q.field_71460_t.field_78519_o, this.field_72777_q.field_71460_t.field_78533_p);
         }

         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, -((float)(var28 - 16.0D)), 0.0F);
         if(Config.isSkyEnabled()) {
            GL11.glCallList(this.field_72781_x);
         }

         GL11.glPopMatrix();
         GL11.glEnable(3553);
         GL11.glDepthMask(true);
      }

   }

   public void func_72718_b(float p_72718_1_) {
      if(this.field_72777_q.field_71474_y.ofClouds != 3) {
         if(Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
            WorldProvider var2 = this.field_72777_q.field_71441_e.field_73011_w;
            Object var3 = Reflector.call(var2, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0]);
            if(var3 != null) {
               Reflector.callVoid(var3, Reflector.IRenderHandler_render, new Object[]{Float.valueOf(p_72718_1_), this.field_72769_h, this.field_72777_q});
               return;
            }
         }

         if(this.field_72777_q.field_71441_e.field_73011_w.func_76569_d()) {
            if(Config.isCloudsFancy()) {
               this.func_72736_c(p_72718_1_);
            } else {
               GL11.glDisable(2884);
               float var24 = (float)(this.field_72777_q.field_71451_h.field_70137_T + (this.field_72777_q.field_71451_h.field_70163_u - this.field_72777_q.field_71451_h.field_70137_T) * (double)p_72718_1_);
               byte var25 = 32;
               int var4 = 256 / var25;
               Tessellator var5 = Tessellator.field_78398_a;
               GL11.glBindTexture(3553, this.field_72770_i.func_78341_b("/environment/clouds.png"));
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               Vec3 var6 = this.field_72769_h.func_72824_f(p_72718_1_);
               float var7 = (float)var6.field_72450_a;
               float var8 = (float)var6.field_72448_b;
               float var9 = (float)var6.field_72449_c;
               float var10;
               if(this.field_72777_q.field_71474_y.field_74337_g) {
                  var10 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
                  float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
                  float var12 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
                  var7 = var10;
                  var8 = var11;
                  var9 = var12;
               }

               var10 = 4.8828125E-4F;
               double var26 = (double)((float)this.field_72773_u + p_72718_1_);
               double var13 = this.field_72777_q.field_71451_h.field_70169_q + (this.field_72777_q.field_71451_h.field_70165_t - this.field_72777_q.field_71451_h.field_70169_q) * (double)p_72718_1_ + var26 * 0.029999999329447746D;
               double var15 = this.field_72777_q.field_71451_h.field_70166_s + (this.field_72777_q.field_71451_h.field_70161_v - this.field_72777_q.field_71451_h.field_70166_s) * (double)p_72718_1_;
               int var17 = MathHelper.func_76128_c(var13 / 2048.0D);
               int var18 = MathHelper.func_76128_c(var15 / 2048.0D);
               var13 -= (double)(var17 * 2048);
               var15 -= (double)(var18 * 2048);
               float var19 = this.field_72769_h.field_73011_w.func_76571_f() - var24 + 0.33F;
               var19 += this.field_72777_q.field_71474_y.ofCloudsHeight * 128.0F;
               float var20 = (float)(var13 * (double)var10);
               float var21 = (float)(var15 * (double)var10);
               var5.func_78382_b();
               var5.func_78369_a(var7, var8, var9, 0.8F);

               for(int var22 = -var25 * var4; var22 < var25 * var4; var22 += var25) {
                  for(int var23 = -var25 * var4; var23 < var25 * var4; var23 += var25) {
                     var5.func_78374_a((double)(var22 + 0), (double)var19, (double)(var23 + var25), (double)((float)(var22 + 0) * var10 + var20), (double)((float)(var23 + var25) * var10 + var21));
                     var5.func_78374_a((double)(var22 + var25), (double)var19, (double)(var23 + var25), (double)((float)(var22 + var25) * var10 + var20), (double)((float)(var23 + var25) * var10 + var21));
                     var5.func_78374_a((double)(var22 + var25), (double)var19, (double)(var23 + 0), (double)((float)(var22 + var25) * var10 + var20), (double)((float)(var23 + 0) * var10 + var21));
                     var5.func_78374_a((double)(var22 + 0), (double)var19, (double)(var23 + 0), (double)((float)(var22 + 0) * var10 + var20), (double)((float)(var23 + 0) * var10 + var21));
                  }
               }

               var5.func_78381_a();
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glDisable(3042);
               GL11.glEnable(2884);
            }
         }

      }
   }

   public boolean func_72721_a(double p_72721_1_, double p_72721_3_, double p_72721_5_, float p_72721_7_) {
      return false;
   }

   public void func_72736_c(float p_72736_1_) {
      GL11.glDisable(2884);
      float var2 = (float)(this.field_72777_q.field_71451_h.field_70137_T + (this.field_72777_q.field_71451_h.field_70163_u - this.field_72777_q.field_71451_h.field_70137_T) * (double)p_72736_1_);
      Tessellator var3 = Tessellator.field_78398_a;
      float var4 = 12.0F;
      float var5 = 4.0F;
      double var6 = (double)((float)this.field_72773_u + p_72736_1_);
      double var8 = (this.field_72777_q.field_71451_h.field_70169_q + (this.field_72777_q.field_71451_h.field_70165_t - this.field_72777_q.field_71451_h.field_70169_q) * (double)p_72736_1_ + var6 * 0.029999999329447746D) / (double)var4;
      double var10 = (this.field_72777_q.field_71451_h.field_70166_s + (this.field_72777_q.field_71451_h.field_70161_v - this.field_72777_q.field_71451_h.field_70166_s) * (double)p_72736_1_) / (double)var4 + 0.33000001311302185D;
      float var12 = this.field_72769_h.field_73011_w.func_76571_f() - var2 + 0.33F;
      var12 += this.field_72777_q.field_71474_y.ofCloudsHeight * 128.0F;
      int var13 = MathHelper.func_76128_c(var8 / 2048.0D);
      int var14 = MathHelper.func_76128_c(var10 / 2048.0D);
      var8 -= (double)(var13 * 2048);
      var10 -= (double)(var14 * 2048);
      GL11.glBindTexture(3553, this.field_72770_i.func_78341_b("/environment/clouds.png"));
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      Vec3 var15 = this.field_72769_h.func_72824_f(p_72736_1_);
      float var16 = (float)var15.field_72450_a;
      float var17 = (float)var15.field_72448_b;
      float var18 = (float)var15.field_72449_c;
      float var19;
      float var21;
      float var20;
      if(this.field_72777_q.field_71474_y.field_74337_g) {
         var19 = (var16 * 30.0F + var17 * 59.0F + var18 * 11.0F) / 100.0F;
         var21 = (var16 * 30.0F + var17 * 70.0F) / 100.0F;
         var20 = (var16 * 30.0F + var18 * 70.0F) / 100.0F;
         var16 = var19;
         var17 = var21;
         var18 = var20;
      }

      var19 = (float)(var8 * 0.0D);
      var21 = (float)(var10 * 0.0D);
      var20 = 0.00390625F;
      var19 = (float)MathHelper.func_76128_c(var8) * var20;
      var21 = (float)MathHelper.func_76128_c(var10) * var20;
      float var22 = (float)(var8 - (double)MathHelper.func_76128_c(var8));
      float var23 = (float)(var10 - (double)MathHelper.func_76128_c(var10));
      byte var24 = 8;
      byte var25 = 4;
      float var26 = 9.765625E-4F;
      GL11.glScalef(var4, 1.0F, var4);

      for(int var27 = 0; var27 < 2; ++var27) {
         if(var27 == 0) {
            GL11.glColorMask(false, false, false, false);
         } else if(this.field_72777_q.field_71474_y.field_74337_g) {
            if(EntityRenderer.field_78515_b == 0) {
               GL11.glColorMask(false, true, true, true);
            } else {
               GL11.glColorMask(true, false, false, true);
            }
         } else {
            GL11.glColorMask(true, true, true, true);
         }

         for(int var28 = -var25 + 1; var28 <= var25; ++var28) {
            for(int var29 = -var25 + 1; var29 <= var25; ++var29) {
               var3.func_78382_b();
               float var30 = (float)(var28 * var24);
               float var31 = (float)(var29 * var24);
               float var32 = var30 - var22;
               float var33 = var31 - var23;
               if(var12 > -var5 - 1.0F) {
                  var3.func_78369_a(var16 * 0.7F, var17 * 0.7F, var18 * 0.7F, 0.8F);
                  var3.func_78375_b(0.0F, -1.0F, 0.0F);
                  var3.func_78374_a((double)(var32 + 0.0F), (double)(var12 + 0.0F), (double)(var33 + (float)var24), (double)((var30 + 0.0F) * var20 + var19), (double)((var31 + (float)var24) * var20 + var21));
                  var3.func_78374_a((double)(var32 + (float)var24), (double)(var12 + 0.0F), (double)(var33 + (float)var24), (double)((var30 + (float)var24) * var20 + var19), (double)((var31 + (float)var24) * var20 + var21));
                  var3.func_78374_a((double)(var32 + (float)var24), (double)(var12 + 0.0F), (double)(var33 + 0.0F), (double)((var30 + (float)var24) * var20 + var19), (double)((var31 + 0.0F) * var20 + var21));
                  var3.func_78374_a((double)(var32 + 0.0F), (double)(var12 + 0.0F), (double)(var33 + 0.0F), (double)((var30 + 0.0F) * var20 + var19), (double)((var31 + 0.0F) * var20 + var21));
               }

               if(var12 <= var5 + 1.0F) {
                  var3.func_78369_a(var16, var17, var18, 0.8F);
                  var3.func_78375_b(0.0F, 1.0F, 0.0F);
                  var3.func_78374_a((double)(var32 + 0.0F), (double)(var12 + var5 - var26), (double)(var33 + (float)var24), (double)((var30 + 0.0F) * var20 + var19), (double)((var31 + (float)var24) * var20 + var21));
                  var3.func_78374_a((double)(var32 + (float)var24), (double)(var12 + var5 - var26), (double)(var33 + (float)var24), (double)((var30 + (float)var24) * var20 + var19), (double)((var31 + (float)var24) * var20 + var21));
                  var3.func_78374_a((double)(var32 + (float)var24), (double)(var12 + var5 - var26), (double)(var33 + 0.0F), (double)((var30 + (float)var24) * var20 + var19), (double)((var31 + 0.0F) * var20 + var21));
                  var3.func_78374_a((double)(var32 + 0.0F), (double)(var12 + var5 - var26), (double)(var33 + 0.0F), (double)((var30 + 0.0F) * var20 + var19), (double)((var31 + 0.0F) * var20 + var21));
               }

               var3.func_78369_a(var16 * 0.9F, var17 * 0.9F, var18 * 0.9F, 0.8F);
               int var34;
               if(var28 > -1) {
                  var3.func_78375_b(-1.0F, 0.0F, 0.0F);

                  for(var34 = 0; var34 < var24; ++var34) {
                     var3.func_78374_a((double)(var32 + (float)var34 + 0.0F), (double)(var12 + 0.0F), (double)(var33 + (float)var24), (double)((var30 + (float)var34 + 0.5F) * var20 + var19), (double)((var31 + (float)var24) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var34 + 0.0F), (double)(var12 + var5), (double)(var33 + (float)var24), (double)((var30 + (float)var34 + 0.5F) * var20 + var19), (double)((var31 + (float)var24) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var34 + 0.0F), (double)(var12 + var5), (double)(var33 + 0.0F), (double)((var30 + (float)var34 + 0.5F) * var20 + var19), (double)((var31 + 0.0F) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var34 + 0.0F), (double)(var12 + 0.0F), (double)(var33 + 0.0F), (double)((var30 + (float)var34 + 0.5F) * var20 + var19), (double)((var31 + 0.0F) * var20 + var21));
                  }
               }

               if(var28 <= 1) {
                  var3.func_78375_b(1.0F, 0.0F, 0.0F);

                  for(var34 = 0; var34 < var24; ++var34) {
                     var3.func_78374_a((double)(var32 + (float)var34 + 1.0F - var26), (double)(var12 + 0.0F), (double)(var33 + (float)var24), (double)((var30 + (float)var34 + 0.5F) * var20 + var19), (double)((var31 + (float)var24) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var34 + 1.0F - var26), (double)(var12 + var5), (double)(var33 + (float)var24), (double)((var30 + (float)var34 + 0.5F) * var20 + var19), (double)((var31 + (float)var24) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var34 + 1.0F - var26), (double)(var12 + var5), (double)(var33 + 0.0F), (double)((var30 + (float)var34 + 0.5F) * var20 + var19), (double)((var31 + 0.0F) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var34 + 1.0F - var26), (double)(var12 + 0.0F), (double)(var33 + 0.0F), (double)((var30 + (float)var34 + 0.5F) * var20 + var19), (double)((var31 + 0.0F) * var20 + var21));
                  }
               }

               var3.func_78369_a(var16 * 0.8F, var17 * 0.8F, var18 * 0.8F, 0.8F);
               if(var29 > -1) {
                  var3.func_78375_b(0.0F, 0.0F, -1.0F);

                  for(var34 = 0; var34 < var24; ++var34) {
                     var3.func_78374_a((double)(var32 + 0.0F), (double)(var12 + var5), (double)(var33 + (float)var34 + 0.0F), (double)((var30 + 0.0F) * var20 + var19), (double)((var31 + (float)var34 + 0.5F) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var24), (double)(var12 + var5), (double)(var33 + (float)var34 + 0.0F), (double)((var30 + (float)var24) * var20 + var19), (double)((var31 + (float)var34 + 0.5F) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var24), (double)(var12 + 0.0F), (double)(var33 + (float)var34 + 0.0F), (double)((var30 + (float)var24) * var20 + var19), (double)((var31 + (float)var34 + 0.5F) * var20 + var21));
                     var3.func_78374_a((double)(var32 + 0.0F), (double)(var12 + 0.0F), (double)(var33 + (float)var34 + 0.0F), (double)((var30 + 0.0F) * var20 + var19), (double)((var31 + (float)var34 + 0.5F) * var20 + var21));
                  }
               }

               if(var29 <= 1) {
                  var3.func_78375_b(0.0F, 0.0F, 1.0F);

                  for(var34 = 0; var34 < var24; ++var34) {
                     var3.func_78374_a((double)(var32 + 0.0F), (double)(var12 + var5), (double)(var33 + (float)var34 + 1.0F - var26), (double)((var30 + 0.0F) * var20 + var19), (double)((var31 + (float)var34 + 0.5F) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var24), (double)(var12 + var5), (double)(var33 + (float)var34 + 1.0F - var26), (double)((var30 + (float)var24) * var20 + var19), (double)((var31 + (float)var34 + 0.5F) * var20 + var21));
                     var3.func_78374_a((double)(var32 + (float)var24), (double)(var12 + 0.0F), (double)(var33 + (float)var34 + 1.0F - var26), (double)((var30 + (float)var24) * var20 + var19), (double)((var31 + (float)var34 + 0.5F) * var20 + var21));
                     var3.func_78374_a((double)(var32 + 0.0F), (double)(var12 + 0.0F), (double)(var33 + (float)var34 + 1.0F - var26), (double)((var30 + 0.0F) * var20 + var19), (double)((var31 + (float)var34 + 0.5F) * var20 + var21));
                  }
               }

               var3.func_78381_a();
            }
         }
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
      GL11.glEnable(2884);
   }

   public boolean func_72716_a(EntityLiving p_72716_1_, boolean p_72716_2_) {
      if(WrUpdates.hasWrUpdater()) {
         return WrUpdates.updateRenderers(this, p_72716_1_, p_72716_2_);
      } else if(this.field_72767_j.size() <= 0) {
         return false;
      } else {
         int var3 = 0;
         int var4 = Config.getUpdatesPerFrame();
         if(Config.isDynamicUpdates() && !this.isMoving(p_72716_1_)) {
            var4 *= 3;
         }

         byte var5 = 4;
         int var6 = 0;
         WorldRenderer var7 = null;
         float var8 = Float.MAX_VALUE;
         int var9 = -1;

         for(int var10 = 0; var10 < this.field_72767_j.size(); ++var10) {
            WorldRenderer var11 = (WorldRenderer)this.field_72767_j.get(var10);
            if(var11 != null) {
               ++var6;
               if(!var11.field_78939_q) {
                  this.field_72767_j.set(var10, (Object)null);
               } else {
                  float var12 = var11.func_78912_a(p_72716_1_);
                  if(var12 <= 256.0F && this.isActingNow()) {
                     var11.func_78907_a();
                     var11.field_78939_q = false;
                     this.field_72767_j.set(var10, (Object)null);
                     ++var3;
                  } else {
                     if(var12 > 256.0F && var3 >= var4) {
                        break;
                     }

                     if(!var11.field_78927_l) {
                        var12 *= (float)var5;
                     }

                     if(var7 == null) {
                        var7 = var11;
                        var8 = var12;
                        var9 = var10;
                     } else if(var12 < var8) {
                        var7 = var11;
                        var8 = var12;
                        var9 = var10;
                     }
                  }
               }
            }
         }

         if(var7 != null) {
            var7.func_78907_a();
            var7.field_78939_q = false;
            this.field_72767_j.set(var9, (Object)null);
            ++var3;
            float var16 = var8 / 5.0F;

            for(int var15 = 0; var15 < this.field_72767_j.size() && var3 < var4; ++var15) {
               WorldRenderer var17 = (WorldRenderer)this.field_72767_j.get(var15);
               if(var17 != null) {
                  float var13 = var17.func_78912_a(p_72716_1_);
                  if(!var17.field_78927_l) {
                     var13 *= (float)var5;
                  }

                  float var14 = Math.abs(var13 - var8);
                  if(var14 < var16) {
                     var17.func_78907_a();
                     var17.field_78939_q = false;
                     this.field_72767_j.set(var15, (Object)null);
                     ++var3;
                  }
               }
            }
         }

         if(var6 == 0) {
            this.field_72767_j.clear();
         }

         this.field_72767_j.compact();
         return true;
      }
   }

   public void func_72727_a(EntityPlayer p_72727_1_, MovingObjectPosition p_72727_2_, int p_72727_3_, ItemStack p_72727_4_, float p_72727_5_) {
      Tessellator var6 = Tessellator.field_78398_a;
      GL11.glEnable(3042);
      GL11.glEnable(3008);
      GL11.glBlendFunc(770, 1);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathHelper.func_76126_a((float)Minecraft.func_71386_F() / 100.0F) * 0.2F + 0.4F) * 0.5F);
      if(p_72727_3_ != 0 && p_72727_4_ != null) {
         GL11.glBlendFunc(770, 771);
         float var7 = MathHelper.func_76126_a((float)Minecraft.func_71386_F() / 100.0F) * 0.2F + 0.8F;
         GL11.glColor4f(var7, var7, var7, MathHelper.func_76126_a((float)Minecraft.func_71386_F() / 200.0F) * 0.2F + 0.5F);
         int var8 = this.field_72770_i.func_78341_b("/terrain.png");
         GL11.glBindTexture(3553, var8);
      }

      GL11.glDisable(3042);
      GL11.glDisable(3008);
   }

   public void func_72717_a(Tessellator p_72717_1_, EntityPlayer p_72717_2_, float p_72717_3_) {
      this.drawBlockDamageTexture(p_72717_1_, p_72717_2_, p_72717_3_);
   }

   public void drawBlockDamageTexture(Tessellator var1, EntityLiving var2, float var3) {
      double var4 = var2.field_70142_S + (var2.field_70165_t - var2.field_70142_S) * (double)var3;
      double var6 = var2.field_70137_T + (var2.field_70163_u - var2.field_70137_T) * (double)var3;
      double var8 = var2.field_70136_U + (var2.field_70161_v - var2.field_70136_U) * (double)var3;
      if(!this.field_72738_E.isEmpty()) {
         GL11.glBlendFunc(774, 768);
         int var10 = this.field_72770_i.func_78341_b("/terrain.png");
         GL11.glBindTexture(3553, var10);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
         GL11.glPushMatrix();
         GL11.glDisable(3008);
         GL11.glPolygonOffset(-3.0F, -3.0F);
         GL11.glEnable('\u8037');
         GL11.glEnable(3008);
         var1.func_78382_b();
         var1.func_78373_b(-var4, -var6, -var8);
         var1.func_78383_c();
         Iterator var11 = this.field_72738_E.values().iterator();

         while(var11.hasNext()) {
            DestroyBlockProgress var12 = (DestroyBlockProgress)var11.next();
            double var13 = (double)var12.func_73110_b() - var4;
            double var15 = (double)var12.func_73109_c() - var6;
            double var17 = (double)var12.func_73108_d() - var8;
            if(var13 * var13 + var15 * var15 + var17 * var17 > 1024.0D) {
               var11.remove();
            } else {
               int var19 = this.field_72769_h.func_72798_a(var12.func_73110_b(), var12.func_73109_c(), var12.func_73108_d());
               Block var20 = var19 > 0?Block.field_71973_m[var19]:null;
               if(var20 == null) {
                  var20 = Block.field_71981_t;
               }

               this.field_72776_r.func_78604_a(var20, var12.func_73110_b(), var12.func_73109_c(), var12.func_73108_d(), 240 + var12.func_73106_e());
            }
         }

         var1.func_78381_a();
         var1.func_78373_b(0.0D, 0.0D, 0.0D);
         GL11.glDisable(3008);
         GL11.glPolygonOffset(0.0F, 0.0F);
         GL11.glDisable('\u8037');
         GL11.glEnable(3008);
         GL11.glDepthMask(true);
         GL11.glPopMatrix();
      }

   }

   public void func_72731_b(EntityPlayer p_72731_1_, MovingObjectPosition p_72731_2_, int p_72731_3_, ItemStack p_72731_4_, float p_72731_5_) {
      if(p_72731_3_ == 0 && p_72731_2_.field_72313_a == EnumMovingObjectType.TILE) {
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
         GL11.glLineWidth(2.0F);
         GL11.glDisable(3553);
         GL11.glDepthMask(false);
         float var6 = 0.002F;
         int var7 = this.field_72769_h.func_72798_a(p_72731_2_.field_72311_b, p_72731_2_.field_72312_c, p_72731_2_.field_72309_d);
         if(var7 > 0) {
            Block.field_71973_m[var7].func_71902_a(this.field_72769_h, p_72731_2_.field_72311_b, p_72731_2_.field_72312_c, p_72731_2_.field_72309_d);
            double var8 = p_72731_1_.field_70142_S + (p_72731_1_.field_70165_t - p_72731_1_.field_70142_S) * (double)p_72731_5_;
            double var10 = p_72731_1_.field_70137_T + (p_72731_1_.field_70163_u - p_72731_1_.field_70137_T) * (double)p_72731_5_;
            double var12 = p_72731_1_.field_70136_U + (p_72731_1_.field_70161_v - p_72731_1_.field_70136_U) * (double)p_72731_5_;
            this.func_72715_a(Block.field_71973_m[var7].func_71911_a_(this.field_72769_h, p_72731_2_.field_72311_b, p_72731_2_.field_72312_c, p_72731_2_.field_72309_d).func_72314_b((double)var6, (double)var6, (double)var6).func_72325_c(-var8, -var10, -var12));
         }

         GL11.glDepthMask(true);
         GL11.glEnable(3553);
         GL11.glDisable(3042);
      }

   }

   private void func_72715_a(AxisAlignedBB p_72715_1_) {
      Tessellator var2 = Tessellator.field_78398_a;
      var2.func_78371_b(3);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72338_b, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78381_a();
      var2.func_78371_b(3);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72337_e, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78381_a();
      var2.func_78371_b(1);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72338_b, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72337_e, p_72715_1_.field_72339_c);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72338_b, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72336_d, p_72715_1_.field_72337_e, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72338_b, p_72715_1_.field_72334_f);
      var2.func_78377_a(p_72715_1_.field_72340_a, p_72715_1_.field_72337_e, p_72715_1_.field_72334_f);
      var2.func_78381_a();
   }

   public void func_72725_b(int p_72725_1_, int p_72725_2_, int p_72725_3_, int p_72725_4_, int p_72725_5_, int p_72725_6_) {
      int var7 = MathHelper.func_76137_a(p_72725_1_, 16);
      int var8 = MathHelper.func_76137_a(p_72725_2_, 16);
      int var9 = MathHelper.func_76137_a(p_72725_3_, 16);
      int var10 = MathHelper.func_76137_a(p_72725_4_, 16);
      int var11 = MathHelper.func_76137_a(p_72725_5_, 16);
      int var12 = MathHelper.func_76137_a(p_72725_6_, 16);

      for(int var13 = var7; var13 <= var10; ++var13) {
         int var14 = var13 % this.field_72766_m;
         if(var14 < 0) {
            var14 += this.field_72766_m;
         }

         for(int var15 = var8; var15 <= var11; ++var15) {
            int var16 = var15 % this.field_72763_n;
            if(var16 < 0) {
               var16 += this.field_72763_n;
            }

            for(int var17 = var9; var17 <= var12; ++var17) {
               int var18 = var17 % this.field_72764_o;
               if(var18 < 0) {
                  var18 += this.field_72764_o;
               }

               int var19 = (var18 * this.field_72763_n + var16) * this.field_72766_m + var14;
               WorldRenderer var20 = this.field_72765_l[var19];
               if(var20 != null && !var20.field_78939_q) {
                  this.field_72767_j.add(var20);
                  var20.func_78914_f();
               }
            }
         }
      }

   }

   public void func_72710_a(int p_72710_1_, int p_72710_2_, int p_72710_3_) {
      this.func_72725_b(p_72710_1_ - 1, p_72710_2_ - 1, p_72710_3_ - 1, p_72710_1_ + 1, p_72710_2_ + 1, p_72710_3_ + 1);
   }

   public void func_72711_b(int p_72711_1_, int p_72711_2_, int p_72711_3_) {
      this.func_72725_b(p_72711_1_ - 1, p_72711_2_ - 1, p_72711_3_ - 1, p_72711_1_ + 1, p_72711_2_ + 1, p_72711_3_ + 1);
   }

   public void func_72707_a(int p_72707_1_, int p_72707_2_, int p_72707_3_, int p_72707_4_, int p_72707_5_, int p_72707_6_) {
      this.func_72725_b(p_72707_1_ - 1, p_72707_2_ - 1, p_72707_3_ - 1, p_72707_4_ + 1, p_72707_5_ + 1, p_72707_6_ + 1);
   }

   public void func_72729_a(ICamera p_72729_1_, float p_72729_2_) {
      for(int var3 = 0; var3 < this.field_72765_l.length; ++var3) {
         if(!this.field_72765_l[var3].func_78906_e()) {
            this.field_72765_l[var3].func_78908_a(p_72729_1_);
         }
      }

      ++this.field_72757_g;
   }

   public void func_72702_a(String p_72702_1_, int p_72702_2_, int p_72702_3_, int p_72702_4_) {
      ItemRecord var5 = ItemRecord.func_90042_d(p_72702_1_);
      if(p_72702_1_ != null && var5 != null) {
         this.field_72777_q.field_71456_v.func_73833_a(var5.func_90043_g());
      }

      this.field_72777_q.field_71416_A.func_77368_a(p_72702_1_, (float)p_72702_2_, (float)p_72702_3_, (float)p_72702_4_);
   }

   public void func_72704_a(String p_72704_1_, double p_72704_2_, double p_72704_4_, double p_72704_6_, float p_72704_8_, float p_72704_9_) {}

   public void func_85102_a(EntityPlayer p_85102_1_, String p_85102_2_, double p_85102_3_, double p_85102_5_, double p_85102_7_, float p_85102_9_, float p_85102_10_) {}

   public void func_72708_a(String p_72708_1_, double p_72708_2_, double p_72708_4_, double p_72708_6_, double p_72708_8_, double p_72708_10_, double p_72708_12_) {
      try {
         this.func_72726_b(p_72708_1_, p_72708_2_, p_72708_4_, p_72708_6_, p_72708_8_, p_72708_10_, p_72708_12_);
      } catch (Throwable var17) {
         CrashReport var15 = CrashReport.func_85055_a(var17, "Exception while adding particle");
         CrashReportCategory var16 = var15.func_85058_a("Particle being added");
         var16.func_71507_a("Name", p_72708_1_);
         var16.func_71500_a("Position", new CallableParticlePositionInfo(this, p_72708_2_, p_72708_4_, p_72708_6_));
         throw new ReportedException(var15);
      }
   }

   public EntityFX func_72726_b(String p_72726_1_, double p_72726_2_, double p_72726_4_, double p_72726_6_, double p_72726_8_, double p_72726_10_, double p_72726_12_) {
      if(this.field_72777_q != null && this.field_72777_q.field_71451_h != null && this.field_72777_q.field_71452_i != null) {
         int var14 = this.field_72777_q.field_71474_y.field_74362_aa;
         if(var14 == 1 && this.field_72769_h.field_73012_v.nextInt(3) == 0) {
            var14 = 2;
         }

         double var15 = this.field_72777_q.field_71451_h.field_70165_t - p_72726_2_;
         double var17 = this.field_72777_q.field_71451_h.field_70163_u - p_72726_4_;
         double var19 = this.field_72777_q.field_71451_h.field_70161_v - p_72726_6_;
         Object var21 = null;
         Object var22 = null;
         if(p_72726_1_.equals("hugeexplosion")) {
            if(Config.isAnimatedExplosion()) {
               this.field_72777_q.field_71452_i.func_78873_a(var21 = new EntityHugeExplodeFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_));
            }
         } else if(p_72726_1_.equals("largeexplode")) {
            if(Config.isAnimatedExplosion()) {
               this.field_72777_q.field_71452_i.func_78873_a(var21 = new EntityLargeExplodeFX(this.field_72770_i, this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_));
            }
         } else if(p_72726_1_.equals("fireworksSpark")) {
            this.field_72777_q.field_71452_i.func_78873_a(var21 = new EntityFireworkSparkFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, this.field_72777_q.field_71452_i));
         }

         if(var21 != null) {
            return (EntityFX)var21;
         } else {
            double var23 = 16.0D;
            double var25 = 16.0D;
            if(p_72726_1_.equals("crit")) {
               var23 = 196.0D;
            }

            if(var15 * var15 + var17 * var17 + var19 * var19 > var23 * var23) {
               return null;
            } else if(var14 > 1) {
               return null;
            } else {
               if(p_72726_1_.equals("bubble")) {
                  var21 = new EntityBubbleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  CustomColorizer.updateWaterFX((EntityFX)var21, this.field_72769_h);
               } else if(p_72726_1_.equals("suspended")) {
                  if(Config.isWaterParticles()) {
                     var21 = new EntitySuspendFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  }
               } else if(p_72726_1_.equals("depthsuspend")) {
                  if(Config.isVoidParticles()) {
                     var21 = new EntityAuraFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  }
               } else if(p_72726_1_.equals("townaura")) {
                  var21 = new EntityAuraFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  CustomColorizer.updateMyceliumFX((EntityFX)var21);
               } else if(p_72726_1_.equals("crit")) {
                  var21 = new EntityCritFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("magicCrit")) {
                  var21 = new EntityCritFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntityFX)var21).func_70538_b(((EntityFX)var21).func_70534_d() * 0.3F, ((EntityFX)var21).func_70542_f() * 0.8F, ((EntityFX)var21).func_70535_g());
                  ((EntityFX)var21).func_70536_a(((EntityFX)var21).func_70540_h() + 1);
               } else if(p_72726_1_.equals("smoke")) {
                  if(Config.isAnimatedSmoke()) {
                     var21 = new EntitySmokeFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  }
               } else if(p_72726_1_.equals("mobSpell")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, 0.0D, 0.0D, 0.0D);
                  ((EntityFX)var21).func_70538_b((float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
               } else if(p_72726_1_.equals("mobSpellAmbient")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, 0.0D, 0.0D, 0.0D);
                  ((EntityFX)var21).func_82338_g(0.15F);
                  ((EntityFX)var21).func_70538_b((float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
               } else if(p_72726_1_.equals("spell")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("instantSpell")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntitySpellParticleFX)var21).func_70589_b(144);
               } else if(p_72726_1_.equals("witchMagic")) {
                  var21 = new EntitySpellParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntitySpellParticleFX)var21).func_70589_b(144);
                  float var27 = this.field_72769_h.field_73012_v.nextFloat() * 0.5F + 0.35F;
                  ((EntityFX)var21).func_70538_b(1.0F * var27, 0.0F * var27, 1.0F * var27);
               } else if(p_72726_1_.equals("note")) {
                  var21 = new EntityNoteFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("portal")) {
                  var21 = new EntityPortalFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  CustomColorizer.updatePortalFX((EntityFX)var21);
               } else if(p_72726_1_.equals("enchantmenttable")) {
                  var21 = new EntityEnchantmentTableParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("explode")) {
                  if(Config.isAnimatedExplosion()) {
                     var21 = new EntityExplodeFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  }
               } else if(p_72726_1_.equals("flame")) {
                  if(Config.isAnimatedFlame()) {
                     var21 = new EntityFlameFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  }
               } else if(p_72726_1_.equals("lava")) {
                  var21 = new EntityLavaFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_);
               } else if(p_72726_1_.equals("footstep")) {
                  var21 = new EntityFootStepFX(this.field_72770_i, this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_);
               } else if(p_72726_1_.equals("splash")) {
                  var21 = new EntitySplashFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  CustomColorizer.updateWaterFX((EntityFX)var21, this.field_72769_h);
               } else if(p_72726_1_.equals("largesmoke")) {
                  if(Config.isAnimatedSmoke()) {
                     var21 = new EntitySmokeFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, 2.5F);
                  }
               } else if(p_72726_1_.equals("cloud")) {
                  var21 = new EntityCloudFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("reddust")) {
                  if(Config.isAnimatedRedstone()) {
                     var21 = new EntityReddustFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, (float)p_72726_8_, (float)p_72726_10_, (float)p_72726_12_);
                     CustomColorizer.updateReddustFX((EntityFX)var21, this.field_72769_h, var15, var17, var19);
                  }
               } else if(p_72726_1_.equals("snowballpoof")) {
                  var21 = new EntityBreakingFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, Item.field_77768_aD);
                  var22 = Item.field_77768_aD;
               } else if(p_72726_1_.equals("dripWater")) {
                  if(Config.isDrippingWaterLava()) {
                     var21 = new EntityDropParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, Material.field_76244_g);
                  }
               } else if(p_72726_1_.equals("dripLava")) {
                  if(Config.isDrippingWaterLava()) {
                     var21 = new EntityDropParticleFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, Material.field_76256_h);
                  }
               } else if(p_72726_1_.equals("snowshovel")) {
                  var21 = new EntitySnowShovelFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("slime")) {
                  var21 = new EntityBreakingFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, Item.field_77761_aM);
                  var22 = Item.field_77761_aM;
               } else if(p_72726_1_.equals("heart")) {
                  var21 = new EntityHeartFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
               } else if(p_72726_1_.equals("angryVillager")) {
                  var21 = new EntityHeartFX(this.field_72769_h, p_72726_2_, p_72726_4_ + 0.5D, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntityFX)var21).func_70536_a(81);
                  ((EntityFX)var21).func_70538_b(1.0F, 1.0F, 1.0F);
               } else if(p_72726_1_.equals("happyVillager")) {
                  var21 = new EntityAuraFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_);
                  ((EntityFX)var21).func_70536_a(82);
                  ((EntityFX)var21).func_70538_b(1.0F, 1.0F, 1.0F);
               } else if(p_72726_1_.startsWith("iconcrack_")) {
                  int var31 = Integer.parseInt(p_72726_1_.substring(p_72726_1_.indexOf("_") + 1));
                  var21 = new EntityBreakingFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, Item.field_77698_e[var31]);
                  var22 = Item.field_77698_e[var31];
               } else if(p_72726_1_.startsWith("tilecrack_")) {
                  String[] var30 = p_72726_1_.split("_", 3);
                  int var28 = Integer.parseInt(var30[1]);
                  int var29 = Integer.parseInt(var30[2]);
                  var21 = (new EntityDiggingFX(this.field_72769_h, p_72726_2_, p_72726_4_, p_72726_6_, p_72726_8_, p_72726_10_, p_72726_12_, Block.field_71973_m[var28], 0, var29)).func_90019_g(var29);
                  var22 = Block.field_71973_m[var28];
               }

               if(var21 != null) {
                  if(Reflector.MinecraftForgeClient.exists()) {
                     Reflector.callVoid(this.field_72777_q.field_71452_i, Reflector.ForgeEffectRenderer_addEffect, new Object[]{var21, var22});
                  } else {
                     this.field_72777_q.field_71452_i.func_78873_a((EntityFX)var21);
                  }
               }

               return (EntityFX)var21;
            }
         }
      } else {
         return null;
      }
   }

   public void func_72703_a(Entity p_72703_1_) {
      p_72703_1_.func_70059_ac();
      if(p_72703_1_.field_70120_cr != null) {
         this.field_72770_i.func_78356_a(p_72703_1_.field_70120_cr, new ImageBufferDownload());
      }

      if(p_72703_1_.field_70119_cs != null) {
         this.field_72770_i.func_78356_a(p_72703_1_.field_70119_cs, new ImageBufferDownload());
         if(p_72703_1_ instanceof EntityPlayer) {
            EntityPlayer var2 = (EntityPlayer)p_72703_1_;
            ThreadDownloadImageData var3 = this.field_72770_i.func_78356_a(var2.field_70119_cs, new ImageBufferDownload());
            this.field_72770_i.func_78347_c(var2.field_70119_cs);
            String var4 = "http://s.optifine.net/capes/" + StringUtils.func_76338_a(var2.field_71092_bJ) + ".png";
            ThreadDownloadImage var5 = new ThreadDownloadImage(var3, var4, new ImageBufferDownload());
            var5.start();
            if(!Config.isShowCapes()) {
               var2.field_71084_cw = "";
            }
         }
      }

      if(Config.isRandomMobs()) {
         RandomMobs.entityLoaded(p_72703_1_);
      }

   }

   public void func_72709_b(Entity p_72709_1_) {
      if(p_72709_1_.field_70120_cr != null) {
         this.field_72770_i.func_78347_c(p_72709_1_.field_70120_cr);
      }

      if(p_72709_1_.field_70119_cs != null) {
         this.field_72770_i.func_78347_c(p_72709_1_.field_70119_cs);
      }

   }

   public void setAllRenderersVisible() {
      if(this.field_72765_l != null) {
         for(int var1 = 0; var1 < this.field_72765_l.length; ++var1) {
            this.field_72765_l[var1].field_78936_t = true;
         }

      }
   }

   public void func_72728_f() {
      GLAllocation.func_74523_b(this.field_72778_p);
   }

   public void func_82746_a(int p_82746_1_, int p_82746_2_, int p_82746_3_, int p_82746_4_, int p_82746_5_) {
      Random var6 = this.field_72769_h.field_73012_v;
      switch(p_82746_1_) {
      case 1013:
      case 1018:
         if(this.field_72777_q.field_71451_h != null) {
            double var7 = (double)p_82746_2_ - this.field_72777_q.field_71451_h.field_70165_t;
            double var9 = (double)p_82746_3_ - this.field_72777_q.field_71451_h.field_70163_u;
            double var11 = (double)p_82746_4_ - this.field_72777_q.field_71451_h.field_70161_v;
            double var13 = Math.sqrt(var7 * var7 + var9 * var9 + var11 * var11);
            double var15 = this.field_72777_q.field_71451_h.field_70165_t;
            double var17 = this.field_72777_q.field_71451_h.field_70163_u;
            double var19 = this.field_72777_q.field_71451_h.field_70161_v;
            if(var13 > 0.0D) {
               var15 += var7 / var13 * 2.0D;
               var17 += var9 / var13 * 2.0D;
               var19 += var11 / var13 * 2.0D;
            }

            if(p_82746_1_ == 1013) {
               this.field_72769_h.func_72980_b(var15, var17, var19, "mob.wither.spawn", 1.0F, 1.0F, false);
            } else if(p_82746_1_ == 1018) {
               this.field_72769_h.func_72980_b(var15, var17, var19, "mob.enderdragon.end", 5.0F, 1.0F, false);
            }
         }
      default:
      }
   }

   public void func_72706_a(EntityPlayer p_72706_1_, int p_72706_2_, int p_72706_3_, int p_72706_4_, int p_72706_5_, int p_72706_6_) {
      Random var7 = this.field_72769_h.field_73012_v;
      double var8;
      double var10;
      double var12;
      String var14;
      int var15;
      double var17;
      int var16;
      double var19;
      double var21;
      double var23;
      double var25;
      switch(p_72706_2_) {
      case 1000:
         this.field_72769_h.func_72980_b((double)p_72706_3_, (double)p_72706_4_, (double)p_72706_5_, "random.click", 1.0F, 1.0F, false);
         break;
      case 1001:
         this.field_72769_h.func_72980_b((double)p_72706_3_, (double)p_72706_4_, (double)p_72706_5_, "random.click", 1.0F, 1.2F, false);
         break;
      case 1002:
         this.field_72769_h.func_72980_b((double)p_72706_3_, (double)p_72706_4_, (double)p_72706_5_, "random.bow", 1.0F, 1.2F, false);
         break;
      case 1003:
         if(Math.random() < 0.5D) {
            this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "random.door_open", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         } else {
            this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "random.door_close", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         }
         break;
      case 1004:
         this.field_72769_h.func_72980_b((double)((float)p_72706_3_ + 0.5F), (double)((float)p_72706_4_ + 0.5F), (double)((float)p_72706_5_ + 0.5F), "random.fizz", 0.5F, 2.6F + (var7.nextFloat() - var7.nextFloat()) * 0.8F, false);
         break;
      case 1005:
         if(Item.field_77698_e[p_72706_6_] instanceof ItemRecord) {
            this.field_72769_h.func_72934_a(((ItemRecord)Item.field_77698_e[p_72706_6_]).field_77837_a, p_72706_3_, p_72706_4_, p_72706_5_);
         } else {
            this.field_72769_h.func_72934_a((String)null, p_72706_3_, p_72706_4_, p_72706_5_);
         }
         break;
      case 1007:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.ghast.charge", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1008:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.ghast.fireball", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1009:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.ghast.fireball", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1010:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.wood", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1011:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.metal", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1012:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.woodbreak", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1014:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.wither.shoot", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1015:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.bat.takeoff", 0.05F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1016:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.infect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1017:
         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "mob.zombie.unfect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1020:
         this.field_72769_h.func_72980_b((double)((float)p_72706_3_ + 0.5F), (double)((float)p_72706_4_ + 0.5F), (double)((float)p_72706_5_ + 0.5F), "random.anvil_break", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1021:
         this.field_72769_h.func_72980_b((double)((float)p_72706_3_ + 0.5F), (double)((float)p_72706_4_ + 0.5F), (double)((float)p_72706_5_ + 0.5F), "random.anvil_use", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1022:
         this.field_72769_h.func_72980_b((double)((float)p_72706_3_ + 0.5F), (double)((float)p_72706_4_ + 0.5F), (double)((float)p_72706_5_ + 0.5F), "random.anvil_land", 0.3F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2000:
         int var27 = p_72706_6_ % 3 - 1;
         int var28 = p_72706_6_ / 3 % 3 - 1;
         var10 = (double)p_72706_3_ + (double)var27 * 0.6D + 0.5D;
         var12 = (double)p_72706_4_ + 0.5D;
         double var29 = (double)p_72706_5_ + (double)var28 * 0.6D + 0.5D;

         for(int var44 = 0; var44 < 10; ++var44) {
            double var43 = var7.nextDouble() * 0.2D + 0.01D;
            double var48 = var10 + (double)var27 * 0.01D + (var7.nextDouble() - 0.5D) * (double)var28 * 0.5D;
            var25 = var12 + (var7.nextDouble() - 0.5D) * 0.5D;
            var17 = var29 + (double)var28 * 0.01D + (var7.nextDouble() - 0.5D) * (double)var27 * 0.5D;
            var19 = (double)var27 * var43 + var7.nextGaussian() * 0.01D;
            var21 = -0.03D + var7.nextGaussian() * 0.01D;
            var23 = (double)var28 * var43 + var7.nextGaussian() * 0.01D;
            this.func_72708_a("smoke", var48, var25, var17, var19, var21, var23);
         }

         return;
      case 2001:
         var16 = p_72706_6_ & 4095;
         if(var16 > 0) {
            Block var42 = Block.field_71973_m[var16];
            this.field_72777_q.field_71416_A.func_77364_b(var42.field_72020_cn.func_72676_a(), (float)p_72706_3_ + 0.5F, (float)p_72706_4_ + 0.5F, (float)p_72706_5_ + 0.5F, (var42.field_72020_cn.func_72677_b() + 1.0F) / 2.0F, var42.field_72020_cn.func_72678_c() * 0.8F);
         }

         this.field_72777_q.field_71452_i.func_78871_a(p_72706_3_, p_72706_4_, p_72706_5_, p_72706_6_ & 4095, p_72706_6_ >> 12 & 255);
         break;
      case 2002:
         var8 = (double)p_72706_3_;
         var10 = (double)p_72706_4_;
         var12 = (double)p_72706_5_;
         var14 = "iconcrack_" + Item.field_77726_bs.field_77779_bT;

         for(var15 = 0; var15 < 8; ++var15) {
            this.func_72708_a(var14, var8, var10, var12, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
         }

         var15 = Item.field_77726_bs.func_77620_a(p_72706_6_);
         float var31 = (float)(var15 >> 16 & 255) / 255.0F;
         float var32 = (float)(var15 >> 8 & 255) / 255.0F;
         float var33 = (float)(var15 >> 0 & 255) / 255.0F;
         String var34 = "spell";
         if(Item.field_77726_bs.func_77833_h(p_72706_6_)) {
            var34 = "instantSpell";
         }

         for(var16 = 0; var16 < 100; ++var16) {
            var25 = var7.nextDouble() * 4.0D;
            var17 = var7.nextDouble() * 3.141592653589793D * 2.0D;
            var19 = Math.cos(var17) * var25;
            var21 = 0.01D + var7.nextDouble() * 0.5D;
            var23 = Math.sin(var17) * var25;
            EntityFX var45 = this.func_72726_b(var34, var8 + var19 * 0.1D, var10 + 0.3D, var12 + var23 * 0.1D, var19, var21, var23);
            if(var45 != null) {
               float var46 = 0.75F + var7.nextFloat() * 0.25F;
               var45.func_70538_b(var31 * var46, var32 * var46, var33 * var46);
               var45.func_70543_e((float)var25);
            }
         }

         this.field_72769_h.func_72980_b((double)p_72706_3_ + 0.5D, (double)p_72706_4_ + 0.5D, (double)p_72706_5_ + 0.5D, "random.glass", 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2003:
         var8 = (double)p_72706_3_ + 0.5D;
         var10 = (double)p_72706_4_;
         var12 = (double)p_72706_5_ + 0.5D;
         var14 = "iconcrack_" + Item.field_77748_bA.field_77779_bT;

         for(var15 = 0; var15 < 8; ++var15) {
            this.func_72708_a(var14, var8, var10, var12, var7.nextGaussian() * 0.15D, var7.nextDouble() * 0.2D, var7.nextGaussian() * 0.15D);
         }

         for(double var47 = 0.0D; var47 < 6.283185307179586D; var47 += 0.15707963267948966D) {
            this.func_72708_a("portal", var8 + Math.cos(var47) * 5.0D, var10 - 0.4D, var12 + Math.sin(var47) * 5.0D, Math.cos(var47) * -5.0D, 0.0D, Math.sin(var47) * -5.0D);
            this.func_72708_a("portal", var8 + Math.cos(var47) * 5.0D, var10 - 0.4D, var12 + Math.sin(var47) * 5.0D, Math.cos(var47) * -7.0D, 0.0D, Math.sin(var47) * -7.0D);
         }

         return;
      case 2004:
         for(int var35 = 0; var35 < 20; ++var35) {
            double var36 = (double)p_72706_3_ + 0.5D + ((double)this.field_72769_h.field_73012_v.nextFloat() - 0.5D) * 2.0D;
            double var38 = (double)p_72706_4_ + 0.5D + ((double)this.field_72769_h.field_73012_v.nextFloat() - 0.5D) * 2.0D;
            double var40 = (double)p_72706_5_ + 0.5D + ((double)this.field_72769_h.field_73012_v.nextFloat() - 0.5D) * 2.0D;
            this.field_72769_h.func_72869_a("smoke", var36, var38, var40, 0.0D, 0.0D, 0.0D);
            this.field_72769_h.func_72869_a("flame", var36, var38, var40, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public void func_72705_a(int p_72705_1_, int p_72705_2_, int p_72705_3_, int p_72705_4_, int p_72705_5_) {
      if(p_72705_5_ >= 0 && p_72705_5_ < 10) {
         DestroyBlockProgress var6 = (DestroyBlockProgress)this.field_72738_E.get(Integer.valueOf(p_72705_1_));
         if(var6 == null || var6.func_73110_b() != p_72705_2_ || var6.func_73109_c() != p_72705_3_ || var6.func_73108_d() != p_72705_4_) {
            var6 = new DestroyBlockProgress(p_72705_1_, p_72705_2_, p_72705_3_, p_72705_4_);
            this.field_72738_E.put(Integer.valueOf(p_72705_1_), var6);
         }

         var6.func_73107_a(p_72705_5_);
         var6.func_82744_b(this.field_72773_u);
      } else {
         this.field_72738_E.remove(Integer.valueOf(p_72705_1_));
      }

   }

   public boolean isMoving(EntityLiving var1) {
      boolean var2 = this.isMovingNow(var1);
      if(var2) {
         this.lastMovedTime = System.currentTimeMillis();
         return true;
      } else {
         return System.currentTimeMillis() - this.lastMovedTime < 2000L;
      }
   }

   private boolean isMovingNow(EntityLiving var1) {
      double var2 = 0.001D;
      return var1.field_70703_bu?true:(var1.func_70093_af()?true:((double)var1.field_70732_aI > var2?true:(this.field_72777_q.field_71417_B.field_74377_a != 0?true:(this.field_72777_q.field_71417_B.field_74375_b != 0?true:(Math.abs(var1.field_70165_t - var1.field_70169_q) > var2?true:(Math.abs(var1.field_70163_u - var1.field_70167_r) > var2?true:Math.abs(var1.field_70161_v - var1.field_70166_s) > var2))))));
   }

   public boolean isActing() {
      boolean var1 = this.isActingNow();
      if(var1) {
         this.lastActionTime = System.currentTimeMillis();
         return true;
      } else {
         return System.currentTimeMillis() - this.lastActionTime < 500L;
      }
   }

   public boolean isActingNow() {
      return Mouse.isButtonDown(0)?true:Mouse.isButtonDown(1);
   }

   public int renderAllSortedRenderers(int var1, double var2) {
      return this.func_72724_a(0, this.field_72768_k.length, var1, var2);
   }

   public void updateCapes() {
      if(this.field_72769_h != null) {
         boolean var1 = Config.isShowCapes();
         List var2 = this.field_72769_h.field_73010_i;

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            Entity var4 = (Entity)var2.get(var3);
            if(var4 instanceof EntityPlayer) {
               EntityPlayer var5 = (EntityPlayer)var4;
               if(var1) {
                  var5.field_71084_cw = var5.field_70119_cs;
               } else {
                  var5.field_71084_cw = "";
               }
            }
         }

      }
   }
}