package net.minecraft.src;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Config;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Reflector;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderItem extends Render {

   private RenderBlocks field_77022_g = new RenderBlocks();
   private Random field_77025_h = new Random();
   public boolean field_77024_a = true;
   public float field_77023_b = 0.0F;
   public static boolean field_82407_g = false;


   public RenderItem() {
      this.field_76989_e = 0.15F;
      this.field_76987_f = 0.75F;
   }

   public void func_77014_a(EntityItem p_77014_1_, double p_77014_2_, double p_77014_4_, double p_77014_6_, float p_77014_8_, float p_77014_9_) {
      this.field_77025_h.setSeed(187L);
      ItemStack var10 = p_77014_1_.func_92059_d();
      if(var10.func_77973_b() != null) {
         GL11.glPushMatrix();
         float var11 = MathHelper.func_76126_a(((float)p_77014_1_.field_70292_b + p_77014_9_) / 10.0F + p_77014_1_.field_70290_d) * 0.1F + 0.1F;
         float var12 = (((float)p_77014_1_.field_70292_b + p_77014_9_) / 20.0F + p_77014_1_.field_70290_d) * 57.295776F;
         byte var13 = this.getMiniBlockCountForItemStack(var10);
         GL11.glTranslatef((float)p_77014_2_, (float)p_77014_4_ + var11, (float)p_77014_6_);
         GL11.glEnable('\u803a');
         boolean var14 = var10.func_77973_b() instanceof ItemBlock;
         Block var15 = null;
         if(var14) {
            var15 = Block.field_71973_m[var10.field_77993_c];
         }

         boolean var20 = false;
         if(Reflector.ForgeHooksClient_renderEntityItem.exists()) {
            var20 = Reflector.callBoolean(Reflector.ForgeHooksClient_renderEntityItem, new Object[]{p_77014_1_, var10, Float.valueOf(var11), Float.valueOf(var12), this.field_77025_h, this.field_76990_c.field_78724_e, this.field_77022_g});
         }

         if(!var20) {
            float var17;
            int var16;
            float var19;
            float var18;
            float var22;
            if(var15 != null && RenderBlocks.func_78597_b(var15.func_71857_b())) {
               GL11.glRotatef(var12, 0.0F, 1.0F, 0.0F);
               if(field_82407_g) {
                  GL11.glScalef(1.25F, 1.25F, 1.25F);
                  GL11.glTranslatef(0.0F, 0.05F, 0.0F);
                  GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
               }

               String var27 = "/terrain.png";
               if(Reflector.ForgeBlock_getTextureFile.exists()) {
                  var27 = Reflector.callString(var15, Reflector.ForgeBlock_getTextureFile, new Object[0]);
               }

               this.func_76985_a(var27);
               var22 = 0.25F;
               var16 = var15.func_71857_b();
               if(var16 == 1 || var16 == 19 || var16 == 12 || var16 == 2) {
                  var22 = 0.5F;
               }

               GL11.glScalef(var22, var22, var22);

               for(int var28 = 0; var28 < var13; ++var28) {
                  GL11.glPushMatrix();
                  if(var28 > 0) {
                     var19 = (this.field_77025_h.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                     var17 = (this.field_77025_h.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                     var18 = (this.field_77025_h.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                     GL11.glTranslatef(var19, var17, var18);
                  }

                  var19 = 1.0F;
                  this.field_77022_g.func_78600_a(var15, var10.func_77960_j(), var19);
                  GL11.glPopMatrix();
               }
            } else {
               int var21;
               String var23;
               if(var10.func_77973_b().func_77623_v()) {
                  if(field_82407_g) {
                     GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
                     GL11.glTranslatef(0.0F, -0.05F, 0.0F);
                  } else {
                     GL11.glScalef(0.5F, 0.5F, 0.5F);
                  }

                  var23 = "/gui/items.png";
                  if(Reflector.ForgeItem_getTextureFile.exists()) {
                     var23 = Reflector.callString(var10.func_77973_b(), Reflector.ForgeItem_getTextureFile, new Object[0]);
                  }

                  this.func_76985_a(var23);
                  int var24 = 1;
                  if(Reflector.ForgeItem_getRenderPasses.exists()) {
                     var24 = Reflector.callInt(var10.func_77973_b(), Reflector.ForgeItem_getRenderPasses, new Object[]{Integer.valueOf(var10.func_77960_j())});
                     --var24;
                  }

                  for(var21 = 0; var21 <= var24; ++var21) {
                     this.field_77025_h.setSeed(187L);
                     if(Reflector.ForgeItem_getIconIndex_2.exists()) {
                        var16 = Reflector.callInt(var10.func_77973_b(), Reflector.ForgeItem_getIconIndex_2, new Object[]{var10, Integer.valueOf(var21)});
                     } else {
                        var16 = var10.func_77973_b().func_77618_c(var10.func_77960_j(), var21);
                     }

                     var22 = 1.0F;
                     if(this.field_77024_a) {
                        int var25 = Item.field_77698_e[var10.field_77993_c].func_82790_a(var10, var21);
                        var17 = (float)(var25 >> 16 & 255) / 255.0F;
                        var18 = (float)(var25 >> 8 & 255) / 255.0F;
                        float var26 = (float)(var25 & 255) / 255.0F;
                        GL11.glColor4f(var17 * var22, var18 * var22, var26 * var22, 1.0F);
                        this.func_77020_a(p_77014_1_, var16, var13, p_77014_9_, var17 * var22, var18 * var22, var26 * var22);
                     } else {
                        this.func_77020_a(p_77014_1_, var16, var13, p_77014_9_, 1.0F, 1.0F, 1.0F);
                     }
                  }
               } else {
                  if(field_82407_g) {
                     GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
                     GL11.glTranslatef(0.0F, -0.05F, 0.0F);
                  } else {
                     GL11.glScalef(0.5F, 0.5F, 0.5F);
                  }

                  var21 = var10.func_77954_c();
                  if(Reflector.ForgeItem_getTextureFile.exists()) {
                     var23 = Reflector.callString(var10.func_77973_b(), Reflector.ForgeItem_getTextureFile, new Object[0]);
                     this.func_76985_a(var23);
                  } else if(var15 != null) {
                     this.func_76985_a("/terrain.png");
                  } else {
                     this.func_76985_a("/gui/items.png");
                  }

                  if(this.field_77024_a) {
                     var16 = Item.field_77698_e[var10.field_77993_c].func_82790_a(var10, 0);
                     var22 = (float)(var16 >> 16 & 255) / 255.0F;
                     var19 = (float)(var16 >> 8 & 255) / 255.0F;
                     var17 = (float)(var16 & 255) / 255.0F;
                     var18 = 1.0F;
                     this.func_77020_a(p_77014_1_, var21, var13, p_77014_9_, var22 * var18, var19 * var18, var17 * var18);
                  } else {
                     this.func_77020_a(p_77014_1_, var21, var13, p_77014_9_, 1.0F, 1.0F, 1.0F);
                  }
               }
            }
         }

         GL11.glDisable('\u803a');
         GL11.glPopMatrix();
      }

   }

   private void func_77020_a(EntityItem p_77020_1_, int p_77020_2_, int p_77020_3_, float p_77020_4_, float p_77020_5_, float p_77020_6_, float p_77020_7_) {
      Tessellator var8 = Tessellator.field_78398_a;
      float var9 = (float)(p_77020_2_ % 16 * 16 + 0) / 256.0F;
      float var10 = (float)(p_77020_2_ % 16 * 16 + 16) / 256.0F;
      float var11 = (float)(p_77020_2_ / 16 * 16 + 0) / 256.0F;
      float var12 = (float)(p_77020_2_ / 16 * 16 + 16) / 256.0F;
      float var13 = 1.0F;
      float var14 = 0.5F;
      float var15 = 0.25F;
      float var16;
      if(Config.isDroppedItemsFancy()) {
         GL11.glPushMatrix();
         if(field_82407_g) {
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GL11.glRotatef((((float)p_77020_1_.field_70292_b + p_77020_4_) / 20.0F + p_77020_1_.field_70290_d) * 57.295776F, 0.0F, 1.0F, 0.0F);
         }

         float var17 = 0.0625F;
         var16 = 0.021875F;
         ItemStack var18 = p_77020_1_.func_92059_d();
         int var19 = var18.field_77994_a;
         byte var20 = this.getMiniItemCountForItemStack(var18);
         GL11.glTranslatef(-var14, -var15, -((var17 + var16) * (float)var20 / 2.0F));

         for(int var21 = 0; var21 < var20; ++var21) {
            GL11.glTranslatef(0.0F, 0.0F, var17 + var16);
            int var22 = 16;
            if(Reflector.ForgeItem_getTextureFile.exists()) {
               String var23 = Reflector.callString(var18.func_77973_b(), Reflector.ForgeItem_getTextureFile, new Object[0]);
               if(var23.equals("/terrain.png")) {
                  var22 = Config.getIconWidthTerrain();
               } else if(var23.equals("/gui/items.png")) {
                  var22 = Config.getIconWidthItems();
               }

               this.func_76985_a(var23);
            } else if(var18.func_77973_b() instanceof ItemBlock) {
               this.func_76985_a("/terrain.png");
               var22 = Config.getIconWidthTerrain();
            } else {
               this.func_76985_a("/gui/items.png");
               var22 = Config.getIconWidthItems();
            }

            GL11.glColor4f(p_77020_5_, p_77020_6_, p_77020_7_, 1.0F);
            ItemRenderer.renderItemIn2D(var8, var10, var11, var9, var12, var17, var22);
            if(var18 != null && var18.func_77962_s()) {
               GL11.glDepthFunc(514);
               GL11.glDisable(2896);
               this.field_76990_c.field_78724_e.func_78342_b(this.field_76990_c.field_78724_e.func_78341_b("%blur%/misc/glint.png"));
               GL11.glEnable(3042);
               GL11.glBlendFunc(768, 1);
               float var29 = 0.76F;
               GL11.glColor4f(0.5F * var29, 0.25F * var29, 0.8F * var29, 1.0F);
               GL11.glMatrixMode(5890);
               GL11.glPushMatrix();
               float var24 = 0.125F;
               GL11.glScalef(var24, var24, var24);
               float var25 = (float)(Minecraft.func_71386_F() % 3000L) / 3000.0F * 8.0F;
               GL11.glTranslatef(var25, 0.0F, 0.0F);
               GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
               ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, var17, var22);
               GL11.glPopMatrix();
               GL11.glPushMatrix();
               GL11.glScalef(var24, var24, var24);
               var25 = (float)(Minecraft.func_71386_F() % 4873L) / 4873.0F * 8.0F;
               GL11.glTranslatef(-var25, 0.0F, 0.0F);
               GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
               ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F, var22);
               GL11.glPopMatrix();
               GL11.glMatrixMode(5888);
               GL11.glDisable(3042);
               GL11.glEnable(2896);
               GL11.glDepthFunc(515);
            }
         }

         GL11.glPopMatrix();
      } else {
         for(int var26 = 0; var26 < p_77020_3_; ++var26) {
            GL11.glPushMatrix();
            if(var26 > 0) {
               var16 = (this.field_77025_h.nextFloat() * 2.0F - 1.0F) * 0.3F;
               float var28 = (this.field_77025_h.nextFloat() * 2.0F - 1.0F) * 0.3F;
               float var27 = (this.field_77025_h.nextFloat() * 2.0F - 1.0F) * 0.3F;
               GL11.glTranslatef(var16, var28, var27);
            }

            if(!field_82407_g) {
               GL11.glRotatef(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
            }

            GL11.glColor4f(p_77020_5_, p_77020_6_, p_77020_7_, 1.0F);
            var8.func_78382_b();
            var8.func_78375_b(0.0F, 1.0F, 0.0F);
            var8.func_78374_a((double)(0.0F - var14), (double)(0.0F - var15), 0.0D, (double)var9, (double)var12);
            var8.func_78374_a((double)(var13 - var14), (double)(0.0F - var15), 0.0D, (double)var10, (double)var12);
            var8.func_78374_a((double)(var13 - var14), (double)(1.0F - var15), 0.0D, (double)var10, (double)var11);
            var8.func_78374_a((double)(0.0F - var14), (double)(1.0F - var15), 0.0D, (double)var9, (double)var11);
            var8.func_78381_a();
            GL11.glPopMatrix();
         }
      }

   }

   public void func_77015_a(FontRenderer p_77015_1_, RenderEngine p_77015_2_, ItemStack p_77015_3_, int p_77015_4_, int p_77015_5_) {
      int var6 = p_77015_3_.field_77993_c;
      int var7 = p_77015_3_.func_77960_j();
      int var8 = p_77015_3_.func_77954_c();
      int var9;
      float var10;
      float var11;
      float var12;
      String var14;
      if(p_77015_3_.func_77973_b() instanceof ItemBlock && RenderBlocks.func_78597_b(Block.field_71973_m[p_77015_3_.field_77993_c].func_71857_b())) {
         Block var18 = Block.field_71973_m[var6];
         var14 = "/terrain.png";
         if(Reflector.ForgeBlock_getTextureFile.exists()) {
            var14 = Reflector.callString(var18, Reflector.ForgeBlock_getTextureFile, new Object[0]);
         }

         p_77015_2_.func_78342_b(p_77015_2_.func_78341_b(var14));
         GL11.glPushMatrix();
         GL11.glTranslatef((float)(p_77015_4_ - 2), (float)(p_77015_5_ + 3), -3.0F + this.field_77023_b);
         GL11.glScalef(10.0F, 10.0F, 10.0F);
         GL11.glTranslatef(1.0F, 0.5F, 1.0F);
         GL11.glScalef(1.0F, 1.0F, -1.0F);
         GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         var9 = Item.field_77698_e[var6].func_82790_a(p_77015_3_, 0);
         var12 = (float)(var9 >> 16 & 255) / 255.0F;
         var10 = (float)(var9 >> 8 & 255) / 255.0F;
         var11 = (float)(var9 & 255) / 255.0F;
         if(this.field_77024_a) {
            GL11.glColor4f(var12, var10, var11, 1.0F);
         }

         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         this.field_77022_g.field_78668_c = this.field_77024_a;
         this.field_77022_g.func_78600_a(var18, var7, 1.0F);
         this.field_77022_g.field_78668_c = true;
         GL11.glPopMatrix();
      } else {
         int var13;
         if(Item.field_77698_e[var6].func_77623_v()) {
            GL11.glDisable(2896);
            var14 = "/gui/items.png";
            if(Reflector.ForgeItem_getTextureFile.exists()) {
               var14 = Reflector.callString(Item.field_77698_e[var6], Reflector.ForgeItem_getTextureFile, new Object[0]);
            }

            p_77015_2_.func_78342_b(p_77015_2_.func_78341_b(var14));
            int var15 = 1;
            if(Reflector.ForgeItem_getRenderPasses.exists()) {
               var15 = Reflector.callInt(Item.field_77698_e[var6], Reflector.ForgeItem_getRenderPasses, new Object[]{Integer.valueOf(var7)});
               --var15;
            }

            for(var13 = 0; var13 <= var15; ++var13) {
               if(Reflector.ForgeItem_getIconIndex_2.exists()) {
                  var9 = Reflector.callInt(Item.field_77698_e[var6], Reflector.ForgeItem_getIconIndex_2, new Object[]{p_77015_3_, Integer.valueOf(var13)});
               } else {
                  var9 = Item.field_77698_e[var6].func_77618_c(var7, var13);
               }

               int var16 = Item.field_77698_e[var6].func_82790_a(p_77015_3_, var13);
               var10 = (float)(var16 >> 16 & 255) / 255.0F;
               var11 = (float)(var16 >> 8 & 255) / 255.0F;
               float var17 = (float)(var16 & 255) / 255.0F;
               if(this.field_77024_a) {
                  GL11.glColor4f(var10, var11, var17, 1.0F);
               }

               this.func_77016_a(p_77015_4_, p_77015_5_, var9 % 16 * 16, var9 / 16 * 16, 16, 16);
            }

            GL11.glEnable(2896);
         } else if(var8 >= 0) {
            GL11.glDisable(2896);
            var14 = null;
            if(Reflector.ForgeItem_getTextureFile.exists()) {
               var14 = Reflector.callString(p_77015_3_.func_77973_b(), Reflector.ForgeItem_getTextureFile, new Object[0]);
            } else if(Item.field_77698_e[var6] instanceof ItemBlock) {
               var14 = "/terrain.png";
            } else {
               var14 = "/gui/items.png";
            }

            p_77015_2_.func_78342_b(p_77015_2_.func_78341_b(var14));
            var13 = Item.field_77698_e[var6].func_82790_a(p_77015_3_, 0);
            float var19 = (float)(var13 >> 16 & 255) / 255.0F;
            var12 = (float)(var13 >> 8 & 255) / 255.0F;
            var10 = (float)(var13 & 255) / 255.0F;
            if(this.field_77024_a) {
               GL11.glColor4f(var19, var12, var10, 1.0F);
            }

            this.func_77016_a(p_77015_4_, p_77015_5_, var8 % 16 * 16, var8 / 16 * 16, 16, 16);
            GL11.glEnable(2896);
         }
      }

      GL11.glEnable(2884);
   }

   public void func_82406_b(FontRenderer p_82406_1_, RenderEngine p_82406_2_, ItemStack p_82406_3_, int p_82406_4_, int p_82406_5_) {
      if(p_82406_3_ != null) {
         if(!Reflector.ForgeHooksClient_renderInventoryItem.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_renderInventoryItem, new Object[]{this.field_77022_g, p_82406_2_, p_82406_3_, Boolean.valueOf(this.field_77024_a), Float.valueOf(this.field_77023_b), Float.valueOf((float)p_82406_4_), Float.valueOf((float)p_82406_5_)})) {
            this.func_77015_a(p_82406_1_, p_82406_2_, p_82406_3_, p_82406_4_, p_82406_5_);
         }

         if(p_82406_3_ != null && p_82406_3_.func_77962_s()) {
            GL11.glDepthFunc(516);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            p_82406_2_.func_78342_b(p_82406_2_.func_78341_b("%blur%/misc/glint.png"));
            this.field_77023_b -= 50.0F;
            GL11.glEnable(3042);
            GL11.glBlendFunc(774, 774);
            GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
            this.func_77018_a(p_82406_4_ * 431278612 + p_82406_5_ * 32178161, p_82406_4_ - 2, p_82406_5_ - 2, 20, 20);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            this.field_77023_b += 50.0F;
            GL11.glEnable(2896);
            GL11.glDepthFunc(515);
         }
      }

   }

   private void func_77018_a(int p_77018_1_, int p_77018_2_, int p_77018_3_, int p_77018_4_, int p_77018_5_) {
      for(int var6 = 0; var6 < 2; ++var6) {
         if(var6 == 0) {
            GL11.glBlendFunc(768, 1);
         }

         if(var6 == 1) {
            GL11.glBlendFunc(768, 1);
         }

         float var7 = 0.00390625F;
         float var8 = 0.00390625F;
         float var9 = (float)(Minecraft.func_71386_F() % (long)(3000 + var6 * 1873)) / (3000.0F + (float)(var6 * 1873)) * 256.0F;
         float var10 = 0.0F;
         Tessellator var11 = Tessellator.field_78398_a;
         float var12 = 4.0F;
         if(var6 == 1) {
            var12 = -1.0F;
         }

         var11.func_78382_b();
         var11.func_78374_a((double)(p_77018_2_ + 0), (double)(p_77018_3_ + p_77018_5_), (double)this.field_77023_b, (double)((var9 + (float)p_77018_5_ * var12) * var7), (double)((var10 + (float)p_77018_5_) * var8));
         var11.func_78374_a((double)(p_77018_2_ + p_77018_4_), (double)(p_77018_3_ + p_77018_5_), (double)this.field_77023_b, (double)((var9 + (float)p_77018_4_ + (float)p_77018_5_ * var12) * var7), (double)((var10 + (float)p_77018_5_) * var8));
         var11.func_78374_a((double)(p_77018_2_ + p_77018_4_), (double)(p_77018_3_ + 0), (double)this.field_77023_b, (double)((var9 + (float)p_77018_4_) * var7), (double)((var10 + 0.0F) * var8));
         var11.func_78374_a((double)(p_77018_2_ + 0), (double)(p_77018_3_ + 0), (double)this.field_77023_b, (double)((var9 + 0.0F) * var7), (double)((var10 + 0.0F) * var8));
         var11.func_78381_a();
      }

   }

   public void func_77021_b(FontRenderer p_77021_1_, RenderEngine p_77021_2_, ItemStack p_77021_3_, int p_77021_4_, int p_77021_5_) {
      if(p_77021_3_ != null) {
         if(p_77021_3_.field_77994_a > 1) {
            String var6 = "" + p_77021_3_.field_77994_a;
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            p_77021_1_.func_78261_a(var6, p_77021_4_ + 19 - 2 - p_77021_1_.func_78256_a(var6), p_77021_5_ + 6 + 3, 16777215);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
         }

         if(p_77021_3_.func_77951_h()) {
            int var11 = (int)Math.round(13.0D - (double)p_77021_3_.func_77952_i() * 13.0D / (double)p_77021_3_.func_77958_k());
            int var7 = (int)Math.round(255.0D - (double)p_77021_3_.func_77952_i() * 255.0D / (double)p_77021_3_.func_77958_k());
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDisable(3553);
            Tessellator var8 = Tessellator.field_78398_a;
            int var9 = 255 - var7 << 16 | var7 << 8;
            int var10 = (255 - var7) / 4 << 16 | 16128;
            this.func_77017_a(var8, p_77021_4_ + 2, p_77021_5_ + 13, 13, 2, 0);
            this.func_77017_a(var8, p_77021_4_ + 2, p_77021_5_ + 13, 12, 1, var10);
            this.func_77017_a(var8, p_77021_4_ + 2, p_77021_5_ + 13, var11, 1, var9);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

   }

   private void func_77017_a(Tessellator p_77017_1_, int p_77017_2_, int p_77017_3_, int p_77017_4_, int p_77017_5_, int p_77017_6_) {
      p_77017_1_.func_78382_b();
      p_77017_1_.func_78378_d(p_77017_6_);
      p_77017_1_.func_78377_a((double)(p_77017_2_ + 0), (double)(p_77017_3_ + 0), 0.0D);
      p_77017_1_.func_78377_a((double)(p_77017_2_ + 0), (double)(p_77017_3_ + p_77017_5_), 0.0D);
      p_77017_1_.func_78377_a((double)(p_77017_2_ + p_77017_4_), (double)(p_77017_3_ + p_77017_5_), 0.0D);
      p_77017_1_.func_78377_a((double)(p_77017_2_ + p_77017_4_), (double)(p_77017_3_ + 0), 0.0D);
      p_77017_1_.func_78381_a();
   }

   public void func_77016_a(int p_77016_1_, int p_77016_2_, int p_77016_3_, int p_77016_4_, int p_77016_5_, int p_77016_6_) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.field_78398_a;
      var9.func_78382_b();
      var9.func_78374_a((double)(p_77016_1_ + 0), (double)(p_77016_2_ + p_77016_6_), (double)this.field_77023_b, (double)((float)(p_77016_3_ + 0) * var7), (double)((float)(p_77016_4_ + p_77016_6_) * var8));
      var9.func_78374_a((double)(p_77016_1_ + p_77016_5_), (double)(p_77016_2_ + p_77016_6_), (double)this.field_77023_b, (double)((float)(p_77016_3_ + p_77016_5_) * var7), (double)((float)(p_77016_4_ + p_77016_6_) * var8));
      var9.func_78374_a((double)(p_77016_1_ + p_77016_5_), (double)(p_77016_2_ + 0), (double)this.field_77023_b, (double)((float)(p_77016_3_ + p_77016_5_) * var7), (double)((float)(p_77016_4_ + 0) * var8));
      var9.func_78374_a((double)(p_77016_1_ + 0), (double)(p_77016_2_ + 0), (double)this.field_77023_b, (double)((float)(p_77016_3_ + 0) * var7), (double)((float)(p_77016_4_ + 0) * var8));
      var9.func_78381_a();
   }

   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.func_77014_a((EntityItem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public boolean shouldSpreadItems() {
      return true;
   }

   public boolean shouldBob() {
      return true;
   }

   public byte getMiniBlockCountForItemStack(ItemStack var1) {
      byte var2 = 1;
      if(var1.field_77994_a > 1) {
         var2 = 2;
      }

      if(var1.field_77994_a > 5) {
         var2 = 3;
      }

      if(var1.field_77994_a > 20) {
         var2 = 4;
      }

      if(var1.field_77994_a > 40) {
         var2 = 5;
      }

      return var2;
   }

   public byte getMiniItemCountForItemStack(ItemStack var1) {
      int var3 = var1.field_77994_a;
      byte var2;
      if(var3 < 2) {
         var2 = 1;
      } else if(var3 < 16) {
         var2 = 2;
      } else if(var3 < 32) {
         var2 = 3;
      } else {
         var2 = 4;
      }

      return var2;
   }

}
