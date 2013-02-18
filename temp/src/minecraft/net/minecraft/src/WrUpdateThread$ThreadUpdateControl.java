package net.minecraft.src;

import net.minecraft.src.IWrUpdateControl;
import net.minecraft.src.Tessellator;
import net.minecraft.src.WrUpdateThread;
import net.minecraft.src.WrUpdateThread$1;

class WrUpdateThread$ThreadUpdateControl implements IWrUpdateControl {

   private IWrUpdateControl updateControl;
   private boolean paused;
   // $FF: synthetic field
   final WrUpdateThread this$0;


   private WrUpdateThread$ThreadUpdateControl(WrUpdateThread var1) {
      this.this$0 = var1;
      this.updateControl = null;
      this.paused = false;
   }

   public void pause() {
      if(!this.paused) {
         this.paused = true;
         this.updateControl.pause();
         Tessellator.field_78398_a = WrUpdateThread.access$000(this.this$0);
      }

   }

   public void resume() {
      if(this.paused) {
         this.paused = false;
         Tessellator.field_78398_a = WrUpdateThread.access$100(this.this$0);
         this.updateControl.resume();
      }

   }

   public void setUpdateControl(IWrUpdateControl var1) {
      this.updateControl = var1;
   }

   // $FF: synthetic method
   WrUpdateThread$ThreadUpdateControl(WrUpdateThread var1, WrUpdateThread$1 var2) {
      this(var1);
   }
}
