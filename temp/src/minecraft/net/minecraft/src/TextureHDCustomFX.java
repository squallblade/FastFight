package net.minecraft.src;

import net.minecraft.src.ITexturePack;
import net.minecraft.src.TextureFX;
import net.minecraft.src.TextureHDFX;

public class TextureHDCustomFX extends TextureFX implements TextureHDFX {

   private ITexturePack texturePackBase;
   private int tileWidth = 0;


   public TextureHDCustomFX(int var1, int var2) {
      super(var1);
      this.field_76847_f = var2;
      this.tileWidth = 16;
      this.field_76852_a = null;
   }

   public void setTileWidth(int var1) {
      this.tileWidth = var1;
   }

   public void setTexturePackBase(ITexturePack var1) {
      this.texturePackBase = var1;
   }

   public void func_76846_a() {}
}
