package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Config;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EnumAction;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemMap;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MapData;
import net.minecraft.src.MapItemRenderer;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.Reflector;
import net.minecraft.src.Render;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.RenderPlayer;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {

   private Minecraft field_78455_a;
   private ItemStack field_78453_b = null;
   private float field_78454_c = 0.0F;
   private float field_78451_d = 0.0F;
   private RenderBlocks field_78452_e = new RenderBlocks();
   public final MapItemRenderer field_78449_f;
   private int field_78450_g = -1;


   public ItemRenderer(Minecraft p_i3186_1_) {
      this.field_78455_a = p_i3186_1_;
      this.field_78449_f = new MapItemRenderer(p_i3186_1_.field_71466_p, p_i3186_1_.field_71474_y, p_i3186_1_.field_71446_o);
   }

   public void func_78443_a(EntityLiving p_78443_1_, ItemStack p_78443_2_, int p_78443_3_) {
      GL11.glPushMatrix();
      boolean var4 = Reflector.MinecraftForgeClient.exists();
      String var5 = null;
      if(var4) {
         var5 = Reflector.callString(p_78443_2_.func_77973_b(), Reflector.ForgeItem_getTextureFile, new Object[0]);
         Object var6 = Reflector.getFieldValue(Reflector.ItemRenderType_EQUIPPED);
         Object var7 = Reflector.call(Reflector.MinecraftForgeClient_getItemRenderer, new Object[]{p_78443_2_, var6});
         if(var7 != null) {
            GL11.glBindTexture(3553, this.field_78455_a.field_71446_o.func_78341_b(var5));
            Reflector.callVoid(Reflector.ForgeHooksClient_renderEquippedItem, new Object[]{var7, this.field_78452_e, p_78443_1_, p_78443_2_});
            GL11.glPopMatrix();
            return;
         }
      }

      boolean var23 = p_78443_2_.func_77973_b() instanceof ItemBlock;
      Block var24 = null;
      if(var23) {
         var24 = Block.field_71973_m[p_78443_2_.field_77993_c];
      }

      if(var24 != null && RenderBlocks.func_78597_b(var24.func_71857_b())) {
         if(var5 == null) {
            var5 = "/terrain.png";
         }

         GL11.glBindTexture(3553, this.field_78455_a.field_71446_o.func_78341_b(var5));
         this.field_78452_e.func_78600_a(var24, p_78443_2_.func_77960_j(), 1.0F);
      } else {
         int var8 = Config.getIconWidthTerrain();
         int var9 = p_78443_1_.func_70620_b(p_78443_2_, p_78443_3_);
         float var10 = 256.0F;
         if(var23) {
            if(var5 == null) {
               var5 = "/terrain.png";
            }

            if(Config.isMultiTexture() && var5.equals("/terrain.png")) {
               GL11.glBindTexture(3553, Tessellator.getTileTextures(this.field_78455_a.field_71446_o.func_78341_b(var5))[var9]);
               var9 = 0;
               var10 = 16.0F;
            } else {
               GL11.glBindTexture(3553, this.field_78455_a.field_71446_o.func_78341_b(var5));
            }

            var8 = Config.getIconWidthTerrain();
         } else {
            if(var5 == null) {
               var5 = "/gui/items.png";
            }

            if(Config.isMultiTexture() && var5.equals("/gui/items.png")) {
               GL11.glBindTexture(3553, Tessellator.getTileTextures(this.field_78455_a.field_71446_o.func_78341_b(var5))[var9]);
               var9 = 0;
               var10 = 16.0F;
            } else {
               GL11.glBindTexture(3553, this.field_78455_a.field_71446_o.func_78341_b(var5));
            }

            var8 = Config.getIconWidthItems();
         }

         Tessellator var11 = Tessellator.field_78398_a;
         float var13 = ((float)(var9 % 16 * 16) + 0.0F) / var10;
         float var14 = ((float)(var9 % 16 * 16) + 15.99F) / var10;
         float var15 = ((float)(var9 / 16 * 16) + 0.0F) / var10;
         float var16 = ((float)(var9 / 16 * 16) + 15.99F) / var10;
         float var17 = 0.0F;
         float var18 = 0.3F;
         GL11.glEnable('\u803a');
         GL11.glTranslatef(-var17, -var18, 0.0F);
         float var19 = 1.5F;
         GL11.glScalef(var19, var19, var19);
         GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
         renderItemIn2D(var11, var14, var15, var13, var16, 0.0625F, var8);
         if(p_78443_2_ != null && p_78443_2_.func_77962_s() && p_78443_3_ == 0) {
            GL11.glDepthFunc(514);
            GL11.glDisable(2896);
            this.field_78455_a.field_71446_o.func_78342_b(this.field_78455_a.field_71446_o.func_78341_b("%blur%/misc/glint.png"));
            GL11.glEnable(3042);
            GL11.glBlendFunc(768, 1);
            float var20 = 0.76F;
            GL11.glColor4f(0.5F * var20, 0.25F * var20, 0.8F * var20, 1.0F);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            float var21 = 0.125F;
            GL11.glScalef(var21, var21, var21);
            float var22 = (float)(Minecraft.func_71386_F() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(var22, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            renderItemIn2D(var11, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F, var8);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(var21, var21, var21);
            var22 = (float)(Minecraft.func_71386_F() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-var22, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            renderItemIn2D(var11, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F, var8);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glDepthFunc(515);
         }

         GL11.glDisable('\u803a');
      }

      GL11.glPopMatrix();
   }

   public static void func_78439_a(Tessellator p_78439_0_, float p_78439_1_, float p_78439_2_, float p_78439_3_, float p_78439_4_, float p_78439_5_) {
      renderItemIn2D(p_78439_0_, p_78439_1_, p_78439_2_, p_78439_3_, p_78439_4_, p_78439_5_, 16);
   }

   public static void renderItemIn2D(Tessellator var0, float var1, float var2, float var3, float var4, float var5, int var6) {
      float var7 = 1.0F;
      var0.func_78382_b();
      var0.func_78375_b(0.0F, 0.0F, 1.0F);
      var0.func_78374_a(0.0D, 0.0D, 0.0D, (double)var1, (double)var4);
      var0.func_78374_a((double)var7, 0.0D, 0.0D, (double)var3, (double)var4);
      var0.func_78374_a((double)var7, 1.0D, 0.0D, (double)var3, (double)var2);
      var0.func_78374_a(0.0D, 1.0D, 0.0D, (double)var1, (double)var2);
      var0.func_78381_a();
      var0.func_78382_b();
      var0.func_78375_b(0.0F, 0.0F, -1.0F);
      var0.func_78374_a(0.0D, 1.0D, (double)(0.0F - var5), (double)var1, (double)var2);
      var0.func_78374_a((double)var7, 1.0D, (double)(0.0F - var5), (double)var3, (double)var2);
      var0.func_78374_a((double)var7, 0.0D, (double)(0.0F - var5), (double)var3, (double)var4);
      var0.func_78374_a(0.0D, 0.0D, (double)(0.0F - var5), (double)var1, (double)var4);
      var0.func_78381_a();
      float var8 = 1.0F / (float)(32 * var6);
      float var9 = 1.0F / (float)var6;
      var0.func_78382_b();
      var0.func_78375_b(-1.0F, 0.0F, 0.0F);

      int var10;
      float var11;
      float var12;
      float var13;
      for(var10 = 0; var10 < var6; ++var10) {
         var11 = (float)var10 / (float)var6;
         var12 = var1 + (var3 - var1) * var11 - var8;
         var13 = var7 * var11;
         var0.func_78374_a((double)var13, 0.0D, (double)(0.0F - var5), (double)var12, (double)var4);
         var0.func_78374_a((double)var13, 0.0D, 0.0D, (double)var12, (double)var4);
         var0.func_78374_a((double)var13, 1.0D, 0.0D, (double)var12, (double)var2);
         var0.func_78374_a((double)var13, 1.0D, (double)(0.0F - var5), (double)var12, (double)var2);
      }

      var0.func_78381_a();
      var0.func_78382_b();
      var0.func_78375_b(1.0F, 0.0F, 0.0F);

      for(var10 = 0; var10 < var6; ++var10) {
         var11 = (float)var10 / (float)var6;
         var12 = var1 + (var3 - var1) * var11 - var8;
         var13 = var7 * var11 + var9;
         var0.func_78374_a((double)var13, 1.0D, (double)(0.0F - var5), (double)var12, (double)var2);
         var0.func_78374_a((double)var13, 1.0D, 0.0D, (double)var12, (double)var2);
         var0.func_78374_a((double)var13, 0.0D, 0.0D, (double)var12, (double)var4);
         var0.func_78374_a((double)var13, 0.0D, (double)(0.0F - var5), (double)var12, (double)var4);
      }

      var0.func_78381_a();
      var0.func_78382_b();
      var0.func_78375_b(0.0F, 1.0F, 0.0F);

      for(var10 = 0; var10 < var6; ++var10) {
         var11 = (float)var10 / (float)var6;
         var12 = var4 + (var2 - var4) * var11 - var8;
         var13 = var7 * var11 + var9;
         var0.func_78374_a(0.0D, (double)var13, 0.0D, (double)var1, (double)var12);
         var0.func_78374_a((double)var7, (double)var13, 0.0D, (double)var3, (double)var12);
         var0.func_78374_a((double)var7, (double)var13, (double)(0.0F - var5), (double)var3, (double)var12);
         var0.func_78374_a(0.0D, (double)var13, (double)(0.0F - var5), (double)var1, (double)var12);
      }

      var0.func_78381_a();
      var0.func_78382_b();
      var0.func_78375_b(0.0F, -1.0F, 0.0F);

      for(var10 = 0; var10 < var6; ++var10) {
         var11 = (float)var10 / (float)var6;
         var12 = var4 + (var2 - var4) * var11 - var8;
         var13 = var7 * var11;
         var0.func_78374_a((double)var7, (double)var13, 0.0D, (double)var3, (double)var12);
         var0.func_78374_a(0.0D, (double)var13, 0.0D, (double)var1, (double)var12);
         var0.func_78374_a(0.0D, (double)var13, (double)(0.0F - var5), (double)var1, (double)var12);
         var0.func_78374_a((double)var7, (double)var13, (double)(0.0F - var5), (double)var3, (double)var12);
      }

      var0.func_78381_a();
   }

   public void func_78440_a(float p_78440_1_) {
      float var2 = this.field_78451_d + (this.field_78454_c - this.field_78451_d) * p_78440_1_;
      EntityClientPlayerMP var3 = this.field_78455_a.field_71439_g;
      float var4 = var3.field_70127_C + (var3.field_70125_A - var3.field_70127_C) * p_78440_1_;
      GL11.glPushMatrix();
      GL11.glRotatef(var4, 1.0F, 0.0F, 0.0F);
      GL11.glRotatef(var3.field_70126_B + (var3.field_70177_z - var3.field_70126_B) * p_78440_1_, 0.0F, 1.0F, 0.0F);
      RenderHelper.func_74519_b();
      GL11.glPopMatrix();
      float var5;
      float var6;
      if(var3 instanceof EntityPlayerSP) {
         var5 = var3.field_71164_i + (var3.field_71155_g - var3.field_71164_i) * p_78440_1_;
         var6 = var3.field_71163_h + (var3.field_71154_f - var3.field_71163_h) * p_78440_1_;
         GL11.glRotatef((var3.field_70125_A - var5) * 0.1F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef((var3.field_70177_z - var6) * 0.1F, 0.0F, 1.0F, 0.0F);
      }

      ItemStack var7 = this.field_78453_b;
      var5 = this.field_78455_a.field_71441_e.func_72801_o(MathHelper.func_76128_c(var3.field_70165_t), MathHelper.func_76128_c(var3.field_70163_u), MathHelper.func_76128_c(var3.field_70161_v));
      var5 = 1.0F;
      int var8 = this.field_78455_a.field_71441_e.func_72802_i(MathHelper.func_76128_c(var3.field_70165_t), MathHelper.func_76128_c(var3.field_70163_u), MathHelper.func_76128_c(var3.field_70161_v), 0);
      int var9 = var8 % 65536;
      int var10 = var8 / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)var9 / 1.0F, (float)var10 / 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      float var11;
      float var12;
      float var13;
      if(var7 != null) {
         var8 = Item.field_77698_e[var7.field_77993_c].func_82790_a(var7, 0);
         var13 = (float)(var8 >> 16 & 255) / 255.0F;
         var12 = (float)(var8 >> 8 & 255) / 255.0F;
         var11 = (float)(var8 & 255) / 255.0F;
         GL11.glColor4f(var5 * var13, var5 * var12, var5 * var11, 1.0F);
      } else {
         GL11.glColor4f(var5, var5, var5, 1.0F);
      }

      float var14;
      float var15;
      Render var17;
      float var16;
      RenderPlayer var18;
      if(var7 != null && var7.func_77973_b() instanceof ItemMap) {
         GL11.glPushMatrix();
         var6 = 0.8F;
         var13 = var3.func_70678_g(p_78440_1_);
         var12 = MathHelper.func_76126_a(var13 * 3.1415927F);
         var11 = MathHelper.func_76126_a(MathHelper.func_76129_c(var13) * 3.1415927F);
         GL11.glTranslatef(-var11 * 0.4F, MathHelper.func_76126_a(MathHelper.func_76129_c(var13) * 3.1415927F * 2.0F) * 0.2F, -var12 * 0.2F);
         var13 = 1.0F - var4 / 45.0F + 0.1F;
         if(var13 < 0.0F) {
            var13 = 0.0F;
         }

         if(var13 > 1.0F) {
            var13 = 1.0F;
         }

         var13 = -MathHelper.func_76134_b(var13 * 3.1415927F) * 0.5F + 0.5F;
         GL11.glTranslatef(0.0F, 0.0F * var6 - (1.0F - var2) * 1.2F - var13 * 0.5F + 0.04F, -0.9F * var6);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(var13 * -85.0F, 0.0F, 0.0F, 1.0F);
         GL11.glEnable('\u803a');
         GL11.glBindTexture(3553, this.field_78455_a.field_71446_o.func_78350_a(this.field_78455_a.field_71439_g.field_70120_cr, this.field_78455_a.field_71439_g.func_70073_O()));

         for(var10 = 0; var10 < 2; ++var10) {
            int var29 = var10 * 2 - 1;
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.0F, -0.6F, 1.1F * (float)var29);
            GL11.glRotatef((float)(-45 * var29), 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef((float)(-65 * var29), 0.0F, 1.0F, 0.0F);
            var17 = RenderManager.field_78727_a.func_78713_a(this.field_78455_a.field_71439_g);
            var18 = (RenderPlayer)var17;
            var16 = 1.0F;
            GL11.glScalef(var16, var16, var16);
            var18.func_82441_a(this.field_78455_a.field_71439_g);
            GL11.glPopMatrix();
         }

         var12 = var3.func_70678_g(p_78440_1_);
         var11 = MathHelper.func_76126_a(var12 * var12 * 3.1415927F);
         var14 = MathHelper.func_76126_a(MathHelper.func_76129_c(var12) * 3.1415927F);
         GL11.glRotatef(-var11 * 20.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-var14 * 20.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-var14 * 80.0F, 1.0F, 0.0F, 0.0F);
         var15 = 0.38F;
         GL11.glScalef(var15, var15, var15);
         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
         GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
         var16 = 0.015625F;
         GL11.glScalef(var16, var16, var16);
         this.field_78455_a.field_71446_o.func_78342_b(this.field_78455_a.field_71446_o.func_78341_b("/misc/mapbg.png"));
         Tessellator var30 = Tessellator.field_78398_a;
         GL11.glNormal3f(0.0F, 0.0F, -1.0F);
         var30.func_78382_b();
         byte var25 = 7;
         var30.func_78374_a((double)(0 - var25), (double)(128 + var25), 0.0D, 0.0D, 1.0D);
         var30.func_78374_a((double)(128 + var25), (double)(128 + var25), 0.0D, 1.0D, 1.0D);
         var30.func_78374_a((double)(128 + var25), (double)(0 - var25), 0.0D, 1.0D, 0.0D);
         var30.func_78374_a((double)(0 - var25), (double)(0 - var25), 0.0D, 0.0D, 0.0D);
         var30.func_78381_a();
         MapData var26 = ((ItemMap)var7.func_77973_b()).func_77873_a(var7, this.field_78455_a.field_71441_e);
         Object var27 = null;
         Object var28 = null;
         if(Reflector.MinecraftForgeClient_getItemRenderer.exists()) {
            var28 = Reflector.getFieldValue(Reflector.ItemRenderType_FIRST_PERSON_MAP);
            var27 = Reflector.call(Reflector.MinecraftForgeClient_getItemRenderer, new Object[]{var7, var28});
         }

         if(var27 != null) {
            Reflector.callVoid(var27, Reflector.IItemRenderer_renderItem, new Object[]{var28, var7, this.field_78455_a.field_71439_g, this.field_78455_a.field_71446_o, var26});
         } else if(var26 != null) {
            this.field_78449_f.func_78319_a(this.field_78455_a.field_71439_g, this.field_78455_a.field_71446_o, var26);
         }

         GL11.glPopMatrix();
      } else if(var7 != null) {
         GL11.glPushMatrix();
         var6 = 0.8F;
         if(var3.func_71052_bv() > 0) {
            EnumAction var19 = var7.func_77975_n();
            if(var19 == EnumAction.eat || var19 == EnumAction.drink) {
               var12 = (float)var3.func_71052_bv() - p_78440_1_ + 1.0F;
               var11 = 1.0F - var12 / (float)var7.func_77988_m();
               var14 = 1.0F - var11;
               var14 = var14 * var14 * var14;
               var14 = var14 * var14 * var14;
               var14 = var14 * var14 * var14;
               var15 = 1.0F - var14;
               GL11.glTranslatef(0.0F, MathHelper.func_76135_e(MathHelper.func_76134_b(var12 / 4.0F * 3.1415927F) * 0.1F) * (float)((double)var11 > 0.2D?1:0), 0.0F);
               GL11.glTranslatef(var15 * 0.6F, -var15 * 0.5F, 0.0F);
               GL11.glRotatef(var15 * 90.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(var15 * 10.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(var15 * 30.0F, 0.0F, 0.0F, 1.0F);
            }
         } else {
            var13 = var3.func_70678_g(p_78440_1_);
            var12 = MathHelper.func_76126_a(var13 * 3.1415927F);
            var11 = MathHelper.func_76126_a(MathHelper.func_76129_c(var13) * 3.1415927F);
            GL11.glTranslatef(-var11 * 0.4F, MathHelper.func_76126_a(MathHelper.func_76129_c(var13) * 3.1415927F * 2.0F) * 0.2F, -var12 * 0.2F);
         }

         GL11.glTranslatef(0.7F * var6, -0.65F * var6 - (1.0F - var2) * 0.6F, -0.9F * var6);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         GL11.glEnable('\u803a');
         var13 = var3.func_70678_g(p_78440_1_);
         var12 = MathHelper.func_76126_a(var13 * var13 * 3.1415927F);
         var11 = MathHelper.func_76126_a(MathHelper.func_76129_c(var13) * 3.1415927F);
         GL11.glRotatef(-var12 * 20.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-var11 * 20.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(-var11 * 80.0F, 1.0F, 0.0F, 0.0F);
         var14 = 0.4F;
         GL11.glScalef(var14, var14, var14);
         float var20;
         float var24;
         if(var3.func_71052_bv() > 0) {
            EnumAction var21 = var7.func_77975_n();
            if(var21 == EnumAction.block) {
               GL11.glTranslatef(-0.5F, 0.2F, 0.0F);
               GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-80.0F, 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
            } else if(var21 == EnumAction.bow) {
               GL11.glRotatef(-18.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-12.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
               GL11.glTranslatef(-0.9F, 0.2F, 0.0F);
               var16 = (float)var7.func_77988_m() - ((float)var3.func_71052_bv() - p_78440_1_ + 1.0F);
               var24 = var16 / 20.0F;
               var24 = (var24 * var24 + var24 * 2.0F) / 3.0F;
               if(var24 > 1.0F) {
                  var24 = 1.0F;
               }

               if(var24 > 0.1F) {
                  GL11.glTranslatef(0.0F, MathHelper.func_76126_a((var16 - 0.1F) * 1.3F) * 0.01F * (var24 - 0.1F), 0.0F);
               }

               GL11.glTranslatef(0.0F, 0.0F, var24 * 0.1F);
               GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
               GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
               GL11.glTranslatef(0.0F, 0.5F, 0.0F);
               var20 = 1.0F + var24 * 0.2F;
               GL11.glScalef(1.0F, 1.0F, var20);
               GL11.glTranslatef(0.0F, -0.5F, 0.0F);
               GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
            }
         }

         if(var7.func_77973_b().func_77629_n_()) {
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
         }

         if(var7.func_77973_b().func_77623_v()) {
            this.func_78443_a(var3, var7, 0);
            int var31 = 2;
            if(Reflector.ForgeItem_getRenderPasses.exists()) {
               var31 = Reflector.callInt(var7.func_77973_b(), Reflector.ForgeItem_getRenderPasses, new Object[]{Integer.valueOf(var7.func_77960_j())});
            }

            for(int var22 = 1; var22 < var31; ++var22) {
               int var23 = Item.field_77698_e[var7.field_77993_c].func_82790_a(var7, var22);
               var16 = (float)(var23 >> 16 & 255) / 255.0F;
               var24 = (float)(var23 >> 8 & 255) / 255.0F;
               var20 = (float)(var23 & 255) / 255.0F;
               GL11.glColor4f(var5 * var16, var5 * var24, var5 * var20, 1.0F);
               this.func_78443_a(var3, var7, var22);
            }
         } else {
            this.func_78443_a(var3, var7, 0);
         }

         GL11.glPopMatrix();
      } else if(!var3.func_82150_aj()) {
         GL11.glPushMatrix();
         var6 = 0.8F;
         var13 = var3.func_70678_g(p_78440_1_);
         var12 = MathHelper.func_76126_a(var13 * 3.1415927F);
         var11 = MathHelper.func_76126_a(MathHelper.func_76129_c(var13) * 3.1415927F);
         GL11.glTranslatef(-var11 * 0.3F, MathHelper.func_76126_a(MathHelper.func_76129_c(var13) * 3.1415927F * 2.0F) * 0.4F, -var12 * 0.4F);
         GL11.glTranslatef(0.8F * var6, -0.75F * var6 - (1.0F - var2) * 0.6F, -0.9F * var6);
         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
         GL11.glEnable('\u803a');
         var13 = var3.func_70678_g(p_78440_1_);
         var12 = MathHelper.func_76126_a(var13 * var13 * 3.1415927F);
         var11 = MathHelper.func_76126_a(MathHelper.func_76129_c(var13) * 3.1415927F);
         GL11.glRotatef(var11 * 70.0F, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-var12 * 20.0F, 0.0F, 0.0F, 1.0F);
         GL11.glBindTexture(3553, this.field_78455_a.field_71446_o.func_78350_a(this.field_78455_a.field_71439_g.field_70120_cr, this.field_78455_a.field_71439_g.func_70073_O()));
         GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
         GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
         GL11.glScalef(1.0F, 1.0F, 1.0F);
         GL11.glTranslatef(5.6F, 0.0F, 0.0F);
         var17 = RenderManager.field_78727_a.func_78713_a(this.field_78455_a.field_71439_g);
         var18 = (RenderPlayer)var17;
         var16 = 1.0F;
         GL11.glScalef(var16, var16, var16);
         var18.func_82441_a(this.field_78455_a.field_71439_g);
         GL11.glPopMatrix();
      }

      GL11.glDisable('\u803a');
      RenderHelper.func_74518_a();
   }

   public void func_78447_b(float p_78447_1_) {
      GL11.glDisable(3008);
      int var2;
      if(this.field_78455_a.field_71439_g.func_70027_ad()) {
         var2 = this.field_78455_a.field_71446_o.func_78341_b("/terrain.png");
         GL11.glBindTexture(3553, var2);
         this.func_78442_d(p_78447_1_);
      }

      if(this.field_78455_a.field_71439_g.func_70094_T()) {
         var2 = MathHelper.func_76128_c(this.field_78455_a.field_71439_g.field_70165_t);
         int var3 = MathHelper.func_76128_c(this.field_78455_a.field_71439_g.field_70163_u);
         int var4 = MathHelper.func_76128_c(this.field_78455_a.field_71439_g.field_70161_v);
         int var5 = this.field_78455_a.field_71446_o.func_78341_b("/terrain.png");
         GL11.glBindTexture(3553, var5);
         int var6 = this.field_78455_a.field_71441_e.func_72798_a(var2, var3, var4);
         if(this.field_78455_a.field_71441_e.func_72809_s(var2, var3, var4)) {
            this.func_78446_a(p_78447_1_, Block.field_71973_m[var6].func_71851_a(2));
         } else {
            for(int var7 = 0; var7 < 8; ++var7) {
               float var8 = ((float)((var7 >> 0) % 2) - 0.5F) * this.field_78455_a.field_71439_g.field_70130_N * 0.9F;
               float var9 = ((float)((var7 >> 1) % 2) - 0.5F) * this.field_78455_a.field_71439_g.field_70131_O * 0.2F;
               float var10 = ((float)((var7 >> 2) % 2) - 0.5F) * this.field_78455_a.field_71439_g.field_70130_N * 0.9F;
               int var11 = MathHelper.func_76141_d((float)var2 + var8);
               int var12 = MathHelper.func_76141_d((float)var3 + var9);
               int var13 = MathHelper.func_76141_d((float)var4 + var10);
               if(this.field_78455_a.field_71441_e.func_72809_s(var11, var12, var13)) {
                  var6 = this.field_78455_a.field_71441_e.func_72798_a(var11, var12, var13);
               }
            }
         }

         if(Block.field_71973_m[var6] != null) {
            this.func_78446_a(p_78447_1_, Block.field_71973_m[var6].func_71851_a(2));
         }
      }

      if(this.field_78455_a.field_71439_g.func_70055_a(Material.field_76244_g)) {
         var2 = this.field_78455_a.field_71446_o.func_78341_b("/misc/water.png");
         GL11.glBindTexture(3553, var2);
         this.func_78448_c(p_78447_1_);
      }

      GL11.glEnable(3008);
   }

   private void func_78446_a(float p_78446_1_, int p_78446_2_) {
      Tessellator var3 = Tessellator.field_78398_a;
      this.field_78455_a.field_71439_g.func_70013_c(p_78446_1_);
      float var4 = 0.1F;
      GL11.glColor4f(var4, var4, var4, 0.5F);
      GL11.glPushMatrix();
      float var5 = -1.0F;
      float var6 = 1.0F;
      float var7 = -1.0F;
      float var8 = 1.0F;
      float var9 = -0.5F;
      float var10 = 0.0078125F;
      float var11 = (float)(p_78446_2_ % 16) / 256.0F - var10;
      float var12 = ((float)(p_78446_2_ % 16) + 15.99F) / 256.0F + var10;
      float var13 = (float)(p_78446_2_ / 16) / 256.0F - var10;
      float var14 = ((float)(p_78446_2_ / 16) + 15.99F) / 256.0F + var10;
      var3.func_78382_b();
      var3.func_78374_a((double)var5, (double)var7, (double)var9, (double)var12, (double)var14);
      var3.func_78374_a((double)var6, (double)var7, (double)var9, (double)var11, (double)var14);
      var3.func_78374_a((double)var6, (double)var8, (double)var9, (double)var11, (double)var13);
      var3.func_78374_a((double)var5, (double)var8, (double)var9, (double)var12, (double)var13);
      var3.func_78381_a();
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void func_78448_c(float p_78448_1_) {
      Tessellator var2 = Tessellator.field_78398_a;
      float var3 = this.field_78455_a.field_71439_g.func_70013_c(p_78448_1_);
      GL11.glColor4f(var3, var3, var3, 0.5F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glPushMatrix();
      float var4 = 4.0F;
      float var5 = -1.0F;
      float var6 = 1.0F;
      float var7 = -1.0F;
      float var8 = 1.0F;
      float var9 = -0.5F;
      float var10 = -this.field_78455_a.field_71439_g.field_70177_z / 64.0F;
      float var11 = this.field_78455_a.field_71439_g.field_70125_A / 64.0F;
      var2.func_78382_b();
      var2.func_78374_a((double)var5, (double)var7, (double)var9, (double)(var4 + var10), (double)(var4 + var11));
      var2.func_78374_a((double)var6, (double)var7, (double)var9, (double)(0.0F + var10), (double)(var4 + var11));
      var2.func_78374_a((double)var6, (double)var8, (double)var9, (double)(0.0F + var10), (double)(0.0F + var11));
      var2.func_78374_a((double)var5, (double)var8, (double)var9, (double)(var4 + var10), (double)(0.0F + var11));
      var2.func_78381_a();
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
   }

   private void func_78442_d(float p_78442_1_) {
      Tessellator var2 = Tessellator.field_78398_a;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      float var3 = 1.0F;

      for(int var4 = 0; var4 < 2; ++var4) {
         GL11.glPushMatrix();
         int var5 = Block.field_72067_ar.field_72059_bZ + var4 * 16;
         int var6 = (var5 & 15) << 4;
         int var7 = var5 & 240;
         float var8 = (float)var6 / 256.0F;
         float var9 = ((float)var6 + 15.99F) / 256.0F;
         float var10 = (float)var7 / 256.0F;
         float var11 = ((float)var7 + 15.99F) / 256.0F;
         float var12 = (0.0F - var3) / 2.0F;
         float var13 = var12 + var3;
         float var14 = 0.0F - var3 / 2.0F;
         float var15 = var14 + var3;
         float var16 = -0.5F;
         GL11.glTranslatef((float)(-(var4 * 2 - 1)) * 0.24F, -0.3F, 0.0F);
         GL11.glRotatef((float)(var4 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
         var2.func_78382_b();
         var2.func_78374_a((double)var12, (double)var14, (double)var16, (double)var9, (double)var11);
         var2.func_78374_a((double)var13, (double)var14, (double)var16, (double)var8, (double)var11);
         var2.func_78374_a((double)var13, (double)var15, (double)var16, (double)var8, (double)var10);
         var2.func_78374_a((double)var12, (double)var15, (double)var16, (double)var9, (double)var10);
         var2.func_78381_a();
         GL11.glPopMatrix();
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
   }

   public void func_78441_a() {
      this.field_78451_d = this.field_78454_c;
      EntityClientPlayerMP var1 = this.field_78455_a.field_71439_g;
      ItemStack var2 = var1.field_71071_by.func_70448_g();
      boolean var3 = this.field_78450_g == var1.field_71071_by.field_70461_c && var2 == this.field_78453_b;
      if(this.field_78453_b == null && var2 == null) {
         var3 = true;
      }

      if(var2 != null && this.field_78453_b != null && var2 != this.field_78453_b && var2.field_77993_c == this.field_78453_b.field_77993_c && var2.func_77960_j() == this.field_78453_b.func_77960_j()) {
         this.field_78453_b = var2;
         var3 = true;
      }

      float var4 = 0.4F;
      float var5 = var3?1.0F:0.0F;
      float var6 = var5 - this.field_78454_c;
      if(var6 < -var4) {
         var6 = -var4;
      }

      if(var6 > var4) {
         var6 = var4;
      }

      this.field_78454_c += var6;
      if(this.field_78454_c < 0.1F) {
         this.field_78453_b = var2;
         this.field_78450_g = var1.field_71071_by.field_70461_c;
      }

   }

   public void func_78444_b() {
      this.field_78454_c = 0.0F;
   }

   public void func_78445_c() {
      this.field_78454_c = 0.0F;
   }
}
