package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCache;
import net.minecraft.src.Config;
import net.minecraft.src.Entity;
import net.minecraft.src.ICamera;
import net.minecraft.src.Reflector;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderItem;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.World;
import org.lwjgl.opengl.GL11;

public class WorldRenderer {

   public World field_78924_a;
   protected int field_78942_y = -1;
   public static volatile int field_78922_b = 0;
   public int field_78923_c;
   public int field_78920_d;
   public int field_78921_e;
   public int field_78918_f;
   public int field_78919_g;
   public int field_78931_h;
   public int field_78932_i;
   public int field_78929_j;
   public int field_78930_k;
   public boolean field_78927_l = false;
   public boolean[] field_78928_m = new boolean[2];
   public int field_78925_n;
   public int field_78926_o;
   public int field_78940_p;
   public volatile boolean field_78939_q;
   public AxisAlignedBB field_78938_r;
   public int field_78937_s;
   public boolean field_78936_t = true;
   public boolean field_78935_u;
   public int field_78934_v;
   public boolean field_78933_w;
   protected boolean field_78915_A = false;
   public List field_78943_x = new ArrayList();
   protected List field_78916_B;
   protected int field_78917_C;
   public boolean isVisibleFromPosition = false;
   public double visibleFromX;
   public double visibleFromY;
   public double visibleFromZ;
   public boolean isInFrustrumFully = false;
   protected boolean needsBoxUpdate = false;
   public volatile boolean isUpdating = false;
   public static int globalChunkOffsetX = 0;
   public static int globalChunkOffsetZ = 0;


   public WorldRenderer(World p_i3196_1_, List p_i3196_2_, int p_i3196_3_, int p_i3196_4_, int p_i3196_5_, int p_i3196_6_) {
      this.field_78924_a = p_i3196_1_;
      this.field_78916_B = p_i3196_2_;
      this.field_78942_y = p_i3196_6_;
      this.field_78923_c = -999;
      this.func_78913_a(p_i3196_3_, p_i3196_4_, p_i3196_5_);
      this.field_78939_q = false;
   }

   public void func_78913_a(int p_78913_1_, int p_78913_2_, int p_78913_3_) {
      if(p_78913_1_ != this.field_78923_c || p_78913_2_ != this.field_78920_d || p_78913_3_ != this.field_78921_e) {
         this.func_78910_b();
         this.field_78923_c = p_78913_1_;
         this.field_78920_d = p_78913_2_;
         this.field_78921_e = p_78913_3_;
         this.field_78925_n = p_78913_1_ + 8;
         this.field_78926_o = p_78913_2_ + 8;
         this.field_78940_p = p_78913_3_ + 8;
         this.field_78932_i = p_78913_1_ & 1023;
         this.field_78929_j = p_78913_2_;
         this.field_78930_k = p_78913_3_ & 1023;
         this.field_78918_f = p_78913_1_ - this.field_78932_i;
         this.field_78919_g = p_78913_2_ - this.field_78929_j;
         this.field_78931_h = p_78913_3_ - this.field_78930_k;
         float var4 = 0.0F;
         this.field_78938_r = AxisAlignedBB.func_72330_a((double)((float)p_78913_1_ - var4), (double)((float)p_78913_2_ - var4), (double)((float)p_78913_3_ - var4), (double)((float)(p_78913_1_ + 16) + var4), (double)((float)(p_78913_2_ + 16) + var4), (double)((float)(p_78913_3_ + 16) + var4));
         this.needsBoxUpdate = true;
         this.func_78914_f();
         this.isVisibleFromPosition = false;
      }
   }

   private void func_78905_g() {
      GL11.glTranslatef((float)this.field_78932_i, (float)this.field_78929_j, (float)this.field_78930_k);
   }

   public void func_78907_a() {
      if(this.field_78924_a != null) {
         if(this.field_78939_q) {
            if(this.needsBoxUpdate) {
               float var1 = 0.0F;
               GL11.glNewList(this.field_78942_y + 2, 4864);
               RenderItem.func_76980_a(AxisAlignedBB.func_72332_a().func_72299_a((double)((float)this.field_78932_i - var1), (double)((float)this.field_78929_j - var1), (double)((float)this.field_78930_k - var1), (double)((float)(this.field_78932_i + 16) + var1), (double)((float)(this.field_78929_j + 16) + var1), (double)((float)(this.field_78930_k + 16) + var1)));
               GL11.glEndList();
               this.needsBoxUpdate = false;
            }

            this.field_78936_t = true;
            this.isVisibleFromPosition = false;
            this.field_78939_q = false;
            int var24 = this.field_78923_c;
            int var2 = this.field_78920_d;
            int var3 = this.field_78921_e;
            int var4 = this.field_78923_c + 16;
            int var5 = this.field_78920_d + 16;
            int var6 = this.field_78921_e + 16;

            for(int var7 = 0; var7 < 2; ++var7) {
               this.field_78928_m[var7] = true;
            }

            if(Reflector.LightCache.exists()) {
               Object var26 = Reflector.getFieldValue(Reflector.LightCache_cache);
               Reflector.callVoid(var26, Reflector.LightCache_clear, new Object[0]);
               Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
            }

            Chunk.field_76640_a = false;
            HashSet var25 = new HashSet();
            var25.addAll(this.field_78943_x);
            this.field_78943_x.clear();
            byte var8 = 1;
            ChunkCache var9 = new ChunkCache(this.field_78924_a, var24 - var8, var2 - var8, var3 - var8, var4 + var8, var5 + var8, var6 + var8);
            if(!var9.func_72806_N()) {
               ++field_78922_b;
               RenderBlocks var10 = new RenderBlocks(var9);
               this.field_78917_C = 0;
               Tessellator var11 = Tessellator.field_78398_a;
               boolean var12 = Reflector.ForgeHooksClient.exists();

               for(int var13 = 0; var13 < 2; ++var13) {
                  boolean var14 = false;
                  boolean var15 = false;
                  boolean var16 = false;

                  for(int var17 = var2; var17 < var5; ++var17) {
                     for(int var18 = var3; var18 < var6; ++var18) {
                        for(int var19 = var24; var19 < var4; ++var19) {
                           int var20 = var9.func_72798_a(var19, var17, var18);
                           if(var20 > 0) {
                              if(!var16) {
                                 var16 = true;
                                 GL11.glNewList(this.field_78942_y + var13, 4864);
                                 var11.setRenderingChunk(true);
                                 if(var12) {
                                    Reflector.callVoid(Reflector.ForgeHooksClient_beforeRenderPass, new Object[]{Integer.valueOf(var13)});
                                 }

                                 var11.func_78382_b();
                                 var11.func_78373_b((double)(-globalChunkOffsetX), 0.0D, (double)(-globalChunkOffsetZ));
                              }

                              Block var21 = Block.field_71973_m[var20];
                              if(var21 != null) {
                                 if(var13 == 0 && var21.func_71887_s()) {
                                    TileEntity var22 = var9.func_72796_p(var19, var17, var18);
                                    if(TileEntityRenderer.field_76963_a.func_76952_a(var22)) {
                                       this.field_78943_x.add(var22);
                                    }
                                 }

                                 int var28 = var21.func_71856_s_();
                                 boolean var23 = true;
                                 if(var28 != var13) {
                                    var14 = true;
                                    var23 = false;
                                 }

                                 if(var12) {
                                    var23 = Reflector.callBoolean(var21, Reflector.ForgeBlock_canRenderInPass, new Object[]{Integer.valueOf(var13)});
                                 }

                                 if(var23) {
                                    if(var12) {
                                       Reflector.callVoid(Reflector.ForgeHooksClient_beforeBlockRender, new Object[]{var21, var10});
                                    }

                                    var15 |= var10.func_78612_b(var21, var19, var17, var18);
                                    if(var12) {
                                       Reflector.callVoid(Reflector.ForgeHooksClient_afterBlockRender, new Object[]{var21, var10});
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  if(var16) {
                     if(var12) {
                        Reflector.callVoid(Reflector.ForgeHooksClient_afterRenderPass, new Object[]{Integer.valueOf(var13)});
                     }

                     this.field_78917_C += var11.func_78381_a();
                     GL11.glEndList();
                     var11.setRenderingChunk(false);
                     var11.func_78373_b(0.0D, 0.0D, 0.0D);
                  } else {
                     var15 = false;
                  }

                  if(var15) {
                     this.field_78928_m[var13] = false;
                  }

                  if(!var14) {
                     break;
                  }
               }
            }

            HashSet var27 = new HashSet();
            var27.addAll(this.field_78943_x);
            var27.removeAll(var25);
            this.field_78916_B.addAll(var27);
            var25.removeAll(this.field_78943_x);
            this.field_78916_B.removeAll(var25);
            this.field_78933_w = Chunk.field_76640_a;
            this.field_78915_A = true;
         }
      }
   }

   public float func_78912_a(Entity p_78912_1_) {
      float var2 = (float)(p_78912_1_.field_70165_t - (double)this.field_78925_n);
      float var3 = (float)(p_78912_1_.field_70163_u - (double)this.field_78926_o);
      float var4 = (float)(p_78912_1_.field_70161_v - (double)this.field_78940_p);
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   public void func_78910_b() {
      for(int var1 = 0; var1 < 2; ++var1) {
         this.field_78928_m[var1] = true;
      }

      this.field_78927_l = false;
      this.field_78915_A = false;
   }

   public void func_78911_c() {
      this.func_78910_b();
      this.field_78924_a = null;
   }

   public int func_78909_a(int p_78909_1_) {
      return !this.field_78927_l?-1:(!this.field_78928_m[p_78909_1_]?this.field_78942_y + p_78909_1_:-1);
   }

   public void func_78908_a(ICamera p_78908_1_) {
      this.field_78927_l = p_78908_1_.func_78546_a(this.field_78938_r);
      if(this.field_78927_l && Config.isOcclusionEnabled() && Config.isOcclusionFancy()) {
         this.isInFrustrumFully = p_78908_1_.isBoundingBoxInFrustumFully(this.field_78938_r);
      } else {
         this.isInFrustrumFully = false;
      }

   }

   public void func_78904_d() {
      GL11.glCallList(this.field_78942_y + 2);
   }

   public boolean func_78906_e() {
      return !this.field_78915_A?false:this.field_78928_m[0] && this.field_78928_m[1];
   }

   public void func_78914_f() {
      this.field_78939_q = true;
   }

}
