package net.minecraft.src;


public class ClippingHelper {

   public float[][] field_78557_a = new float[16][16];
   public float[] field_78555_b = new float[16];
   public float[] field_78556_c = new float[16];
   public float[] field_78554_d = new float[16];


   public boolean func_78553_b(double p_78553_1_, double p_78553_3_, double p_78553_5_, double p_78553_7_, double p_78553_9_, double p_78553_11_) {
      for(int var13 = 0; var13 < 6; ++var13) {
         float var14 = (float)p_78553_1_;
         float var15 = (float)p_78553_3_;
         float var16 = (float)p_78553_5_;
         float var17 = (float)p_78553_7_;
         float var18 = (float)p_78553_9_;
         float var19 = (float)p_78553_11_;
         if(this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F) {
            return false;
         }
      }

      return true;
   }

   public boolean isBoxInFrustumFully(double var1, double var3, double var5, double var7, double var9, double var11) {
      for(int var13 = 0; var13 < 6; ++var13) {
         float var14 = (float)var1;
         float var15 = (float)var3;
         float var16 = (float)var5;
         float var17 = (float)var7;
         float var18 = (float)var9;
         float var19 = (float)var11;
         if(var13 < 4) {
            if(this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F || this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F || this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F || this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F || this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F || this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F || this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F || this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F) {
               return false;
            }
         } else if(this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var16 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var15 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var14 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F && this.field_78557_a[var13][0] * var17 + this.field_78557_a[var13][1] * var18 + this.field_78557_a[var13][2] * var19 + this.field_78557_a[var13][3] <= 0.0F) {
            return false;
         }
      }

      return true;
   }
}
