package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ClippingHelper;
import net.minecraft.src.ClippingHelperImpl;
import net.minecraft.src.ICamera;

public class Frustrum implements ICamera {

   private ClippingHelper field_78552_a = ClippingHelperImpl.func_78558_a();
   private double field_78550_b;
   private double field_78551_c;
   private double field_78549_d;


   public void func_78547_a(double p_78547_1_, double p_78547_3_, double p_78547_5_) {
      this.field_78550_b = p_78547_1_;
      this.field_78551_c = p_78547_3_;
      this.field_78549_d = p_78547_5_;
   }

   public boolean func_78548_b(double p_78548_1_, double p_78548_3_, double p_78548_5_, double p_78548_7_, double p_78548_9_, double p_78548_11_) {
      return this.field_78552_a.func_78553_b(p_78548_1_ - this.field_78550_b, p_78548_3_ - this.field_78551_c, p_78548_5_ - this.field_78549_d, p_78548_7_ - this.field_78550_b, p_78548_9_ - this.field_78551_c, p_78548_11_ - this.field_78549_d);
   }

   public boolean func_78546_a(AxisAlignedBB p_78546_1_) {
      return this.func_78548_b(p_78546_1_.field_72340_a, p_78546_1_.field_72338_b, p_78546_1_.field_72339_c, p_78546_1_.field_72336_d, p_78546_1_.field_72337_e, p_78546_1_.field_72334_f);
   }

   public boolean isBoxInFrustumFully(double var1, double var3, double var5, double var7, double var9, double var11) {
      return this.field_78552_a.isBoxInFrustumFully(var1 - this.field_78550_b, var3 - this.field_78551_c, var5 - this.field_78549_d, var7 - this.field_78550_b, var9 - this.field_78551_c, var11 - this.field_78549_d);
   }

   public boolean isBoundingBoxInFrustumFully(AxisAlignedBB var1) {
      return this.isBoxInFrustumFully(var1.field_72340_a, var1.field_72338_b, var1.field_72339_c, var1.field_72336_d, var1.field_72337_e, var1.field_72334_f);
   }
}
