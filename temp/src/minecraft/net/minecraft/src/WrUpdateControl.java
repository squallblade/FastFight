package net.minecraft.src;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Config;
import net.minecraft.src.IWrUpdateControl;
import net.minecraft.src.Reflector;
import net.minecraft.src.WorldClient;

public class WrUpdateControl implements IWrUpdateControl {

   private boolean hasForge;
   private int renderPass;


   public WrUpdateControl() {
      this.hasForge = Reflector.ForgeHooksClient.exists();
      this.renderPass = 0;
   }

   public void resume() {
      if(this.hasForge) {
         Reflector.callVoid(Reflector.ForgeHooksClient_beforeRenderPass, new Object[]{Integer.valueOf(this.renderPass)});
      }

   }

   public void pause() {
      AxisAlignedBB.func_72332_a().func_72298_a();
      WorldClient var1 = Config.getMinecraft().field_71441_e;
      if(var1 != null) {
         var1.func_82732_R().func_72343_a();
      }

      if(this.hasForge) {
         Reflector.callVoid(Reflector.ForgeHooksClient_afterRenderPass, new Object[]{Integer.valueOf(this.renderPass)});
      }

   }

   public void setRenderPass(int var1) {
      this.renderPass = var1;
   }
}
