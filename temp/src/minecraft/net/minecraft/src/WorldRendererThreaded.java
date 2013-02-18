package net.minecraft.src;

import java.util.HashSet;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCache;
import net.minecraft.src.IWrUpdateListener;
import net.minecraft.src.Reflector;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderItem;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.World;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.WrUpdateControl;
import org.lwjgl.opengl.GL11;

public class WorldRendererThreaded extends WorldRenderer {

   private int glRenderListStable;
   private int glRenderListBoundingBox;


   public WorldRendererThreaded(World var1, List var2, int var3, int var4, int var5, int var6) {
      super(var1, var2, var3, var4, var5, var6);
      this.glRenderListStable = this.field_78942_y + 393216;
      this.glRenderListBoundingBox = this.field_78942_y + 2;
   }

   public void func_78907_a() {
      if(this.field_78924_a != null) {
         this.updateRenderer((IWrUpdateListener)null);
         this.finishUpdate();
      }
   }

   public void updateRenderer(IWrUpdateListener var1) {
      if(this.field_78924_a != null) {
         this.field_78939_q = false;
         int var2 = this.field_78923_c;
         int var3 = this.field_78920_d;
         int var4 = this.field_78921_e;
         int var5 = this.field_78923_c + 16;
         int var6 = this.field_78920_d + 16;
         int var7 = this.field_78921_e + 16;
         boolean[] var8 = new boolean[2];

         for(int var9 = 0; var9 < var8.length; ++var9) {
            var8[var9] = true;
         }

         if(Reflector.LightCache.exists()) {
            Object var28 = Reflector.getFieldValue(Reflector.LightCache_cache);
            Reflector.callVoid(var28, Reflector.LightCache_clear, new Object[0]);
            Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
         }

         Chunk.field_76640_a = false;
         HashSet var27 = new HashSet();
         var27.addAll(this.field_78943_x);
         this.field_78943_x.clear();
         byte var10 = 1;
         ChunkCache var11 = new ChunkCache(this.field_78924_a, var2 - var10, var3 - var10, var4 - var10, var5 + var10, var6 + var10, var7 + var10);
         if(!var11.func_72806_N()) {
            ++field_78922_b;
            RenderBlocks var12 = new RenderBlocks(var11);
            this.field_78917_C = 0;
            Tessellator var13 = Tessellator.field_78398_a;
            boolean var14 = Reflector.ForgeHooksClient.exists();
            WrUpdateControl var15 = new WrUpdateControl();

            for(int var16 = 0; var16 < 2; ++var16) {
               var15.setRenderPass(var16);
               boolean var17 = false;
               boolean var18 = false;
               boolean var19 = false;

               for(int var20 = var3; var20 < var6; ++var20) {
                  if(var18 && var1 != null) {
                     var1.updating(var15);
                  }

                  for(int var21 = var4; var21 < var7; ++var21) {
                     for(int var22 = var2; var22 < var5; ++var22) {
                        int var23 = var11.func_72798_a(var22, var20, var21);
                        if(var23 > 0) {
                           if(!var19) {
                              var19 = true;
                              GL11.glNewList(this.field_78942_y + var16, 4864);
                              var13.setRenderingChunk(true);
                              if(var14) {
                                 Reflector.callVoid(Reflector.ForgeHooksClient_beforeRenderPass, new Object[]{Integer.valueOf(var16)});
                              }

                              var13.func_78382_b();
                              var13.func_78373_b((double)(-globalChunkOffsetX), 0.0D, (double)(-globalChunkOffsetZ));
                           }

                           Block var24 = Block.field_71973_m[var23];
                           if(var16 == 0 && var24.func_71887_s()) {
                              TileEntity var25 = var11.func_72796_p(var22, var20, var21);
                              if(TileEntityRenderer.field_76963_a.func_76952_a(var25)) {
                                 this.field_78943_x.add(var25);
                              }
                           }

                           int var31 = var24.func_71856_s_();
                           boolean var26 = true;
                           if(var31 != var16) {
                              var17 = true;
                              var26 = false;
                           }

                           if(var14) {
                              var26 = Reflector.callBoolean(var24, Reflector.ForgeBlock_canRenderInPass, new Object[]{Integer.valueOf(var16)});
                           }

                           if(var26) {
                              if(var14) {
                                 Reflector.callVoid(Reflector.ForgeHooksClient_beforeBlockRender, new Object[]{var24, var12});
                              }

                              var18 |= var12.func_78612_b(var24, var22, var20, var21);
                              if(var14) {
                                 Reflector.callVoid(Reflector.ForgeHooksClient_afterBlockRender, new Object[]{var24, var12});
                              }
                           }
                        }
                     }
                  }
               }

               if(var19) {
                  if(var1 != null) {
                     var1.updating(var15);
                  }

                  if(var14) {
                     Reflector.callVoid(Reflector.ForgeHooksClient_afterRenderPass, new Object[]{Integer.valueOf(var16)});
                  }

                  this.field_78917_C += var13.func_78381_a();
                  GL11.glEndList();
                  var13.setRenderingChunk(false);
                  var13.func_78373_b(0.0D, 0.0D, 0.0D);
               } else {
                  var18 = false;
               }

               if(var18) {
                  var8[var16] = false;
               }

               if(!var17) {
                  break;
               }
            }
         }

         for(int var29 = 0; var29 < 2; ++var29) {
            this.field_78928_m[var29] = var8[var29];
         }

         HashSet var30 = new HashSet();
         var30.addAll(this.field_78943_x);
         var30.removeAll(var27);
         this.field_78916_B.addAll(var30);
         var27.removeAll(this.field_78943_x);
         this.field_78916_B.removeAll(var27);
         this.field_78933_w = Chunk.field_76640_a;
         this.field_78915_A = true;
         this.field_78936_t = true;
         this.isVisibleFromPosition = false;
      }
   }

   public void finishUpdate() {
      int var1 = this.field_78942_y;
      this.field_78942_y = this.glRenderListStable;
      this.glRenderListStable = var1;

      for(int var2 = 0; var2 < 2; ++var2) {
         if(!this.field_78928_m[var2]) {
            GL11.glNewList(this.field_78942_y + var2, 4864);
            GL11.glEndList();
         }
      }

      if(this.needsBoxUpdate && !this.func_78906_e()) {
         float var3 = 0.0F;
         GL11.glNewList(this.glRenderListBoundingBox, 4864);
         RenderItem.func_76980_a(AxisAlignedBB.func_72332_a().func_72299_a((double)((float)this.field_78932_i - var3), (double)((float)this.field_78929_j - var3), (double)((float)this.field_78930_k - var3), (double)((float)(this.field_78932_i + 16) + var3), (double)((float)(this.field_78929_j + 16) + var3), (double)((float)(this.field_78930_k + 16) + var3)));
         GL11.glEndList();
         this.needsBoxUpdate = false;
      }

   }

   public int func_78909_a(int var1) {
      return !this.field_78927_l?-1:(!this.field_78928_m[var1]?this.glRenderListStable + var1:-1);
   }

   public void func_78904_d() {
      GL11.glCallList(this.glRenderListBoundingBox);
   }
}
