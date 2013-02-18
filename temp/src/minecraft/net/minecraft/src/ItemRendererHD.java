package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Block;
import net.minecraft.src.Config;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Reflector;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class ItemRendererHD extends ItemRenderer {

   private Minecraft minecraft = null;


   public ItemRendererHD(Minecraft var1) {
      super(var1);
      this.minecraft = var1;
   }

   public void func_78443_a(EntityLiving var1, ItemStack var2, int var3) {
      boolean var4 = Reflector.MinecraftForgeClient.exists();
      if(var4) {
         Object var5 = Reflector.getFieldValue(Reflector.ItemRenderType_EQUIPPED);
         Object var6 = Reflector.call(Reflector.MinecraftForgeClient_getItemRenderer, new Object[]{var2, var5});
         if(var6 != null) {
            super.func_78443_a(var1, var2, var3);
            return;
         }
      }

      boolean var21 = var2.func_77973_b() instanceof ItemBlock;
      if(var21 && RenderBlocks.func_78597_b(Block.field_71973_m[var2.field_77993_c].func_71857_b())) {
         super.func_78443_a(var1, var2, var3);
      } else {
         int var22 = Config.getIconWidthTerrain();
         if(var22 < 16) {
            super.func_78443_a(var1, var2, var3);
         } else {
            GL11.glPushMatrix();
            int var7 = var1.func_70620_b(var2, var3);
            float var8 = 256.0F;
            String var9;
            if(var21) {
               var9 = "/terrain.png";
               if(var4) {
                  var9 = Reflector.callString(var2.func_77973_b(), Reflector.ForgeItem_getTextureFile, new Object[0]);
               }

               if(var9.equals("/terrain.png") && Config.isMultiTexture()) {
                  GL11.glBindTexture(3553, Tessellator.getTileTextures(this.minecraft.field_71446_o.func_78341_b(var9))[var7]);
                  var7 = 0;
                  var8 = 16.0F;
               } else {
                  GL11.glBindTexture(3553, this.minecraft.field_71446_o.func_78341_b(var9));
               }

               var22 = Config.getIconWidthTerrain();
            } else {
               var9 = "/gui/items.png";
               if(var4) {
                  var9 = Reflector.callString(var2.func_77973_b(), Reflector.ForgeItem_getTextureFile, new Object[0]);
               }

               if(var9.equals("/gui/items.png") && Config.isMultiTexture()) {
                  GL11.glBindTexture(3553, Tessellator.getTileTextures(this.minecraft.field_71446_o.func_78341_b(var9))[var7]);
                  var7 = 0;
                  var8 = 16.0F;
               } else {
                  GL11.glBindTexture(3553, this.minecraft.field_71446_o.func_78341_b(var9));
               }

               var22 = Config.getIconWidthItems();
            }

            Tessellator var23 = Tessellator.field_78398_a;
            float var11 = ((float)(var7 % 16 * 16) + 0.01F) / var8;
            float var12 = ((float)(var7 % 16 * 16) + 15.99F) / var8;
            float var13 = ((float)(var7 / 16 * 16) + 0.01F) / var8;
            float var14 = ((float)(var7 / 16 * 16) + 15.99F) / var8;
            float var15 = 0.0F;
            float var16 = 0.3F;
            GL11.glEnable('\u803a');
            GL11.glTranslatef(-var15, -var16, 0.0F);
            float var17 = 1.5F;
            GL11.glScalef(var17, var17, var17);
            GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
            this.renderItem3D(var23, var12, var13, var11, var14, var22);
            if(var2 != null && var2.func_77962_s() && var3 == 0) {
               GL11.glDepthFunc(514);
               GL11.glDisable(2896);
               this.minecraft.field_71446_o.func_78342_b(this.minecraft.field_71446_o.func_78341_b("%blur%/misc/glint.png"));
               GL11.glEnable(3042);
               GL11.glBlendFunc(768, 1);
               float var18 = 0.76F;
               GL11.glColor4f(0.5F * var18, 0.25F * var18, 0.8F * var18, 1.0F);
               GL11.glMatrixMode(5890);
               GL11.glPushMatrix();
               float var19 = 0.125F;
               GL11.glScalef(var19, var19, var19);
               float var20 = (float)(System.currentTimeMillis() % 3000L) / 3000.0F * 8.0F;
               GL11.glTranslatef(var20, 0.0F, 0.0F);
               GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
               this.renderItem3D(var23, 0.0F, 0.0F, 1.0F, 1.0F, var22);
               GL11.glPopMatrix();
               GL11.glPushMatrix();
               GL11.glScalef(var19, var19, var19);
               var20 = (float)(System.currentTimeMillis() % 4873L) / 4873.0F * 8.0F;
               GL11.glTranslatef(-var20, 0.0F, 0.0F);
               GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
               this.renderItem3D(var23, 0.0F, 0.0F, 1.0F, 1.0F, var22);
               GL11.glPopMatrix();
               GL11.glMatrixMode(5888);
               GL11.glDisable(3042);
               GL11.glEnable(2896);
               GL11.glDepthFunc(515);
            }

            GL11.glDisable('\u803a');
            GL11.glPopMatrix();
         }
      }
   }

   private void renderItem3D(Tessellator var1, float var2, float var3, float var4, float var5, int var6) {
      float var7 = 1.0F;
      float var8 = 0.0625F;
      var1.func_78382_b();
      var1.func_78375_b(0.0F, 0.0F, 1.0F);
      var1.func_78374_a(0.0D, 0.0D, 0.0D, (double)var2, (double)var5);
      var1.func_78374_a((double)var7, 0.0D, 0.0D, (double)var4, (double)var5);
      var1.func_78374_a((double)var7, 1.0D, 0.0D, (double)var4, (double)var3);
      var1.func_78374_a(0.0D, 1.0D, 0.0D, (double)var2, (double)var3);
      var1.func_78381_a();
      var1.func_78382_b();
      var1.func_78375_b(0.0F, 0.0F, -1.0F);
      var1.func_78374_a(0.0D, 1.0D, (double)(0.0F - var8), (double)var2, (double)var3);
      var1.func_78374_a((double)var7, 1.0D, (double)(0.0F - var8), (double)var4, (double)var3);
      var1.func_78374_a((double)var7, 0.0D, (double)(0.0F - var8), (double)var4, (double)var5);
      var1.func_78374_a(0.0D, 0.0D, (double)(0.0F - var8), (double)var2, (double)var5);
      var1.func_78381_a();
      float var9 = 1.0F / (float)(32 * var6);
      float var10 = 1.0F / (float)var6;
      var1.func_78382_b();
      var1.func_78375_b(-1.0F, 0.0F, 0.0F);

      int var11;
      float var12;
      float var13;
      float var14;
      for(var11 = 0; var11 < var6; ++var11) {
         var12 = (float)var11 / ((float)var6 * 1.0F);
         var13 = var2 + (var4 - var2) * var12 - var9;
         var14 = var7 * var12;
         var1.func_78374_a((double)var14, 0.0D, (double)(0.0F - var8), (double)var13, (double)var5);
         var1.func_78374_a((double)var14, 0.0D, 0.0D, (double)var13, (double)var5);
         var1.func_78374_a((double)var14, 1.0D, 0.0D, (double)var13, (double)var3);
         var1.func_78374_a((double)var14, 1.0D, (double)(0.0F - var8), (double)var13, (double)var3);
      }

      var1.func_78381_a();
      var1.func_78382_b();
      var1.func_78375_b(1.0F, 0.0F, 0.0F);

      for(var11 = 0; var11 < var6; ++var11) {
         var12 = (float)var11 / ((float)var6 * 1.0F);
         var13 = var2 + (var4 - var2) * var12 - var9;
         var14 = var7 * var12 + var10;
         var1.func_78374_a((double)var14, 1.0D, (double)(0.0F - var8), (double)var13, (double)var3);
         var1.func_78374_a((double)var14, 1.0D, 0.0D, (double)var13, (double)var3);
         var1.func_78374_a((double)var14, 0.0D, 0.0D, (double)var13, (double)var5);
         var1.func_78374_a((double)var14, 0.0D, (double)(0.0F - var8), (double)var13, (double)var5);
      }

      var1.func_78381_a();
      var1.func_78382_b();
      var1.func_78375_b(0.0F, 1.0F, 0.0F);

      for(var11 = 0; var11 < var6; ++var11) {
         var12 = (float)var11 / ((float)var6 * 1.0F);
         var13 = var5 + (var3 - var5) * var12 - var9;
         var14 = var7 * var12 + var10;
         var1.func_78374_a(0.0D, (double)var14, 0.0D, (double)var2, (double)var13);
         var1.func_78374_a((double)var7, (double)var14, 0.0D, (double)var4, (double)var13);
         var1.func_78374_a((double)var7, (double)var14, (double)(0.0F - var8), (double)var4, (double)var13);
         var1.func_78374_a(0.0D, (double)var14, (double)(0.0F - var8), (double)var2, (double)var13);
      }

      var1.func_78381_a();
      var1.func_78382_b();
      var1.func_78375_b(0.0F, -1.0F, 0.0F);

      for(var11 = 0; var11 < var6; ++var11) {
         var12 = (float)var11 / ((float)var6 * 1.0F);
         var13 = var5 + (var3 - var5) * var12 - var9;
         var14 = var7 * var12;
         var1.func_78374_a((double)var7, (double)var14, 0.0D, (double)var4, (double)var13);
         var1.func_78374_a(0.0D, (double)var14, 0.0D, (double)var2, (double)var13);
         var1.func_78374_a(0.0D, (double)var14, (double)(0.0F - var8), (double)var2, (double)var13);
         var1.func_78374_a((double)var7, (double)var14, (double)(0.0F - var8), (double)var4, (double)var13);
      }

      var1.func_78381_a();
   }
}
