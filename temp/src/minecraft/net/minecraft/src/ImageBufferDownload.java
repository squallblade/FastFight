package net.minecraft.src;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import net.minecraft.src.IImageBuffer;

public class ImageBufferDownload implements IImageBuffer {

   private int[] field_78438_a;
   private int field_78436_b;
   private int field_78437_c;


   public BufferedImage func_78432_a(BufferedImage p_78432_1_) {
      if(p_78432_1_ == null) {
         return null;
      } else {
         this.field_78436_b = 64;
         this.field_78437_c = 32;

         for(BufferedImage var2 = p_78432_1_; this.field_78436_b < var2.getWidth() || this.field_78437_c < var2.getHeight(); this.field_78437_c *= 2) {
            this.field_78436_b *= 2;
         }

         BufferedImage var3 = new BufferedImage(this.field_78436_b, this.field_78437_c, 2);
         Graphics var4 = var3.getGraphics();
         var4.drawImage(p_78432_1_, 0, 0, (ImageObserver)null);
         var4.dispose();
         this.field_78438_a = ((DataBufferInt)var3.getRaster().getDataBuffer()).getData();
         int var5 = this.field_78436_b;
         int var6 = this.field_78437_c;
         this.func_78433_b(0, 0, var5 / 2, var6 / 2);
         this.func_78434_a(var5 / 2, 0, var5, var6);
         this.func_78433_b(0, var6 / 2, var5, var6);
         boolean var7 = false;

         int var8;
         int var9;
         int var10;
         for(var8 = var5 / 2; var8 < var5; ++var8) {
            for(var9 = 0; var9 < var6 / 2; ++var9) {
               var10 = this.field_78438_a[var8 + var9 * var5];
               if((var10 >> 24 & 255) < 128) {
                  var7 = true;
               }
            }
         }

         if(!var7) {
            for(var8 = var5 / 2; var8 < var5; ++var8) {
               for(var9 = 0; var9 < var6 / 2; ++var9) {
                  var10 = this.field_78438_a[var8 + var9 * var5];
                  if((var10 >> 24 & 255) < 128) {
                     boolean var11 = true;
                  }
               }
            }
         }

         return var3;
      }
   }

   private void func_78434_a(int p_78434_1_, int p_78434_2_, int p_78434_3_, int p_78434_4_) {
      if(!this.func_78435_c(p_78434_1_, p_78434_2_, p_78434_3_, p_78434_4_)) {
         for(int var5 = p_78434_1_; var5 < p_78434_3_; ++var5) {
            for(int var6 = p_78434_2_; var6 < p_78434_4_; ++var6) {
               this.field_78438_a[var5 + var6 * this.field_78436_b] &= 16777215;
            }
         }
      }

   }

   private void func_78433_b(int p_78433_1_, int p_78433_2_, int p_78433_3_, int p_78433_4_) {
      for(int var5 = p_78433_1_; var5 < p_78433_3_; ++var5) {
         for(int var6 = p_78433_2_; var6 < p_78433_4_; ++var6) {
            this.field_78438_a[var5 + var6 * this.field_78436_b] |= -16777216;
         }
      }

   }

   private boolean func_78435_c(int p_78435_1_, int p_78435_2_, int p_78435_3_, int p_78435_4_) {
      for(int var5 = p_78435_1_; var5 < p_78435_3_; ++var5) {
         for(int var6 = p_78435_2_; var6 < p_78435_4_; ++var6) {
            int var7 = this.field_78438_a[var5 + var6 * this.field_78436_b];
            if((var7 >> 24 & 255) < 128) {
               return true;
            }
         }
      }

      return false;
   }
}
