package net.minecraft.src;

import java.util.HashSet;
import java.util.List;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.ChunkCache;
import net.minecraft.src.Reflector;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderItem;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityRenderer;
import net.minecraft.src.World;
import net.minecraft.src.WorldRenderer;
import net.minecraft.src.WrUpdateState;
import org.lwjgl.opengl.GL11;

public class WorldRendererSmooth extends WorldRenderer {

   private WrUpdateState updateState = new WrUpdateState();
   public int activeSet = 0;
   public int[] activeListIndex = new int[]{0, 0};
   public int[][][] glWorkLists = new int[2][2][16];
   public boolean[] tempSkipRenderPass = new boolean[2];


   public WorldRendererSmooth(World var1, List var2, int var3, int var4, int var5, int var6) {
      super(var1, var2, var3, var4, var5, var6);
      int var7 = 393216 + 64 * (this.field_78942_y / 3);

      for(int var8 = 0; var8 < 2; ++var8) {
         int var9 = var7 + var8 * 2 * 16;

         for(int var10 = 0; var10 < 2; ++var10) {
            int var11 = var9 + var10 * 16;

            for(int var12 = 0; var12 < 16; ++var12) {
               this.glWorkLists[var8][var10][var12] = var11 + var12;
            }
         }
      }

   }

   public void func_78913_a(int var1, int var2, int var3) {
      if(this.isUpdating) {
         this.func_78907_a();
      }

      super.func_78913_a(var1, var2, var3);
   }

   public void func_78907_a() {
      if(this.field_78924_a != null) {
         this.updateRenderer(0L);
         this.finishUpdate();
      }
   }

   public boolean updateRenderer(long var1) {
      if(this.field_78924_a == null) {
         return true;
      } else {
         this.field_78939_q = false;
         if(!this.isUpdating) {
            if(this.needsBoxUpdate) {
               float var3 = 0.0F;
               GL11.glNewList(this.field_78942_y + 2, 4864);
               RenderItem.func_76980_a(AxisAlignedBB.func_72332_a().func_72299_a((double)((float)this.field_78932_i - var3), (double)((float)this.field_78929_j - var3), (double)((float)this.field_78930_k - var3), (double)((float)(this.field_78932_i + 16) + var3), (double)((float)(this.field_78929_j + 16) + var3), (double)((float)(this.field_78930_k + 16) + var3)));
               GL11.glEndList();
               this.needsBoxUpdate = false;
            }

            if(Reflector.LightCache.exists()) {
               Object var25 = Reflector.getFieldValue(Reflector.LightCache_cache);
               Reflector.callVoid(var25, Reflector.LightCache_clear, new Object[0]);
               Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
            }

            Chunk.field_76640_a = false;
         }

         int var26 = this.field_78923_c;
         int var4 = this.field_78920_d;
         int var5 = this.field_78921_e;
         int var6 = this.field_78923_c + 16;
         int var7 = this.field_78920_d + 16;
         int var8 = this.field_78921_e + 16;
         ChunkCache var9 = null;
         RenderBlocks var10 = null;
         HashSet var11 = null;
         if(!this.isUpdating) {
            for(int var12 = 0; var12 < 2; ++var12) {
               this.tempSkipRenderPass[var12] = true;
            }

            byte var27 = 1;
            var9 = new ChunkCache(this.field_78924_a, var26 - var27, var4 - var27, var5 - var27, var6 + var27, var7 + var27, var8 + var27);
            var10 = new RenderBlocks(var9);
            var11 = new HashSet();
            var11.addAll(this.field_78943_x);
            this.field_78943_x.clear();
         }

         if(this.isUpdating || !var9.func_72806_N()) {
            this.field_78917_C = 0;
            Tessellator var28 = Tessellator.field_78398_a;
            boolean var13 = Reflector.ForgeHooksClient.exists();

            for(int var14 = 0; var14 < 2; ++var14) {
               boolean var15 = false;
               boolean var16 = false;
               boolean var17 = false;

               for(int var18 = var4; var18 < var7; ++var18) {
                  if(this.isUpdating) {
                     this.isUpdating = false;
                     var9 = this.updateState.chunkcache;
                     var10 = this.updateState.renderblocks;
                     var11 = this.updateState.setOldEntityRenders;
                     var14 = this.updateState.renderPass;
                     var18 = this.updateState.y;
                     var15 = this.updateState.flag;
                     var16 = this.updateState.hasRenderedBlocks;
                     var17 = this.updateState.hasGlList;
                     if(var17) {
                        GL11.glNewList(this.glWorkLists[this.activeSet][var14][this.activeListIndex[var14]], 4864);
                        if(var13) {
                           Reflector.callVoid(Reflector.ForgeHooksClient_beforeRenderPass, new Object[]{Integer.valueOf(var14)});
                        }

                        var28.setRenderingChunk(true);
                        var28.func_78382_b();
                        var28.func_78373_b((double)(-globalChunkOffsetX), 0.0D, (double)(-globalChunkOffsetZ));
                     }
                  } else if(var17 && var1 != 0L && System.nanoTime() - var1 > 0L && this.activeListIndex[var14] < 15) {
                     if(var13) {
                        Reflector.callVoid(Reflector.ForgeHooksClient_afterRenderPass, new Object[]{Integer.valueOf(var14)});
                     }

                     var28.func_78381_a();
                     GL11.glEndList();
                     var28.setRenderingChunk(false);
                     var28.func_78373_b(0.0D, 0.0D, 0.0D);
                     ++this.activeListIndex[var14];
                     this.updateState.chunkcache = var9;
                     this.updateState.renderblocks = var10;
                     this.updateState.setOldEntityRenders = var11;
                     this.updateState.renderPass = var14;
                     this.updateState.y = var18;
                     this.updateState.flag = var15;
                     this.updateState.hasRenderedBlocks = var16;
                     this.updateState.hasGlList = var17;
                     this.isUpdating = true;
                     return false;
                  }

                  for(int var19 = var5; var19 < var8; ++var19) {
                     for(int var20 = var26; var20 < var6; ++var20) {
                        int var21 = var9.func_72798_a(var20, var18, var19);
                        if(var21 > 0) {
                           if(!var17) {
                              var17 = true;
                              GL11.glNewList(this.glWorkLists[this.activeSet][var14][this.activeListIndex[var14]], 4864);
                              if(var13) {
                                 Reflector.callVoid(Reflector.ForgeHooksClient_beforeRenderPass, new Object[]{Integer.valueOf(var14)});
                              }

                              var28.setRenderingChunk(true);
                              var28.func_78382_b();
                              var28.func_78373_b((double)(-globalChunkOffsetX), 0.0D, (double)(-globalChunkOffsetZ));
                           }

                           Block var22 = Block.field_71973_m[var21];
                           if(var14 == 0 && var22.func_71887_s()) {
                              TileEntity var23 = var9.func_72796_p(var20, var18, var19);
                              if(TileEntityRenderer.field_76963_a.func_76952_a(var23)) {
                                 this.field_78943_x.add(var23);
                              }
                           }

                           int var30 = var22.func_71856_s_();
                           boolean var24 = true;
                           if(var30 != var14) {
                              var15 = true;
                              var24 = false;
                           }

                           if(var13) {
                              var24 = Reflector.callBoolean(var22, Reflector.ForgeBlock_canRenderInPass, new Object[]{Integer.valueOf(var14)});
                           }

                           if(var24) {
                              if(var13) {
                                 Reflector.callVoid(Reflector.ForgeHooksClient_beforeBlockRender, new Object[]{var22, var10});
                              }

                              var16 |= var10.func_78612_b(var22, var20, var18, var19);
                              if(var13) {
                                 Reflector.callVoid(Reflector.ForgeHooksClient_afterBlockRender, new Object[]{var22, var10});
                              }
                           }
                        }
                     }
                  }
               }

               if(var17) {
                  if(var13) {
                     Reflector.callVoid(Reflector.ForgeHooksClient_afterRenderPass, new Object[]{Integer.valueOf(var14)});
                  }

                  this.field_78917_C += var28.func_78381_a();
                  GL11.glEndList();
                  var28.setRenderingChunk(false);
                  var28.func_78373_b(0.0D, 0.0D, 0.0D);
               } else {
                  var16 = false;
               }

               if(var16) {
                  this.tempSkipRenderPass[var14] = false;
               }

               if(!var15) {
                  break;
               }
            }
         }

         HashSet var29 = new HashSet();
         var29.addAll(this.field_78943_x);
         var29.removeAll(var11);
         this.field_78916_B.addAll(var29);
         var11.removeAll(this.field_78943_x);
         this.field_78916_B.removeAll(var11);
         this.field_78933_w = Chunk.field_76640_a;
         this.field_78915_A = true;
         ++field_78922_b;
         this.field_78936_t = true;
         this.isVisibleFromPosition = false;
         this.field_78928_m[0] = this.tempSkipRenderPass[0];
         this.field_78928_m[1] = this.tempSkipRenderPass[1];
         this.isUpdating = false;
         return true;
      }
   }

   public void finishUpdate() {
      int var1;
      int var2;
      int var3;
      for(var1 = 0; var1 < 2; ++var1) {
         if(!this.field_78928_m[var1]) {
            GL11.glNewList(this.field_78942_y + var1, 4864);

            for(var2 = 0; var2 <= this.activeListIndex[var1]; ++var2) {
               var3 = this.glWorkLists[this.activeSet][var1][var2];
               GL11.glCallList(var3);
            }

            GL11.glEndList();
         }
      }

      if(this.activeSet == 0) {
         this.activeSet = 1;
      } else {
         this.activeSet = 0;
      }

      for(var1 = 0; var1 < 2; ++var1) {
         if(!this.field_78928_m[var1]) {
            for(var2 = 0; var2 <= this.activeListIndex[var1]; ++var2) {
               var3 = this.glWorkLists[this.activeSet][var1][var2];
               GL11.glNewList(var3, 4864);
               GL11.glEndList();
            }
         }
      }

      for(var1 = 0; var1 < 2; ++var1) {
         this.activeListIndex[var1] = 0;
      }

   }
}
