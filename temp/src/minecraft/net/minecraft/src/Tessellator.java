package net.minecraft.src;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import net.minecraft.src.Config;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.OpenGlHelper;
import net.minecraft.src.VertexData;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Tessellator {

   private static boolean field_78396_b = false;
   private static boolean field_78397_c = false;
   private ByteBuffer field_78394_d;
   private IntBuffer field_78395_e;
   private FloatBuffer field_78392_f;
   private ShortBuffer field_78393_g;
   private int[] field_78405_h;
   private int field_78406_i;
   private double field_78403_j;
   private double field_78404_k;
   private int field_78401_l;
   private int field_78402_m;
   private boolean field_78399_n;
   private boolean field_78400_o;
   private boolean field_78414_p;
   private boolean field_78413_q;
   private int field_78412_r;
   private int field_78411_s;
   private boolean field_78410_t;
   public int field_78409_u;
   public double field_78408_v;
   public double field_78407_w;
   public double field_78417_x;
   private int field_78416_y;
   public static Tessellator field_78398_a = new Tessellator(524288);
   public boolean field_78415_z;
   private boolean field_78389_A;
   private IntBuffer field_78390_B;
   private int field_78391_C;
   private int field_78387_D;
   private int field_78388_E;
   private boolean renderingChunk;
   private static boolean littleEndianByteOrder = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
   public static boolean renderingWorldRenderer = false;
   public boolean defaultTexture;
   public int textureID;
   public boolean autoGrow;
   private Tessellator[] subTessellators;
   private int[] subTextures;
   private static int terrainTexture = 0;
   private long textureUpdateTime;
   public static int[][] atlasSubTextures = new int[0][];
   private VertexData[] vertexDatas;
   private boolean[] drawnIcons;
   private int[] vertexIconIndex;
   private int[] tileTextures;


   public Tessellator() {
      this(65536);
      this.defaultTexture = false;
   }

   public Tessellator(int p_i3191_1_) {
      this.renderingChunk = false;
      this.defaultTexture = true;
      this.textureID = 0;
      this.autoGrow = true;
      this.subTessellators = new Tessellator[0];
      this.subTextures = new int[0];
      this.textureUpdateTime = 0L;
      this.vertexDatas = null;
      this.drawnIcons = new boolean[256];
      this.vertexIconIndex = null;
      this.tileTextures = null;
      this.field_78406_i = 0;
      this.field_78399_n = false;
      this.field_78400_o = false;
      this.field_78414_p = false;
      this.field_78413_q = false;
      this.field_78412_r = 0;
      this.field_78411_s = 0;
      this.field_78410_t = false;
      this.field_78415_z = false;
      this.field_78389_A = false;
      this.field_78391_C = 0;
      this.field_78387_D = 10;
      this.field_78388_E = p_i3191_1_;
      this.field_78394_d = GLAllocation.func_74524_c(p_i3191_1_ * 4);
      this.field_78395_e = this.field_78394_d.asIntBuffer();
      this.field_78392_f = this.field_78394_d.asFloatBuffer();
      this.field_78393_g = this.field_78394_d.asShortBuffer();
      this.field_78405_h = new int[p_i3191_1_];
      this.field_78389_A = field_78397_c && GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
      if(this.field_78389_A) {
         this.field_78390_B = GLAllocation.func_74527_f(this.field_78387_D);
         ARBVertexBufferObject.glGenBuffersARB(this.field_78390_B);
      }

      this.vertexDatas = new VertexData[4];

      for(int var2 = 0; var2 < this.vertexDatas.length; ++var2) {
         this.vertexDatas[var2] = new VertexData();
      }

      this.vertexIconIndex = new int[this.field_78388_E];
   }

   private void draw(int var1, int var2) {
      int var3 = var2 - var1;
      if(var3 > 0) {
         if(var3 % 4 == 0) {
            if(this.field_78389_A) {
               throw new IllegalStateException("VBO not implemented");
            } else {
               this.field_78392_f.position(3);
               GL11.glTexCoordPointer(2, 32, this.field_78392_f);
               OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
               this.field_78393_g.position(14);
               GL11.glTexCoordPointer(2, 32, this.field_78393_g);
               GL11.glEnableClientState('\u8078');
               OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
               this.field_78394_d.position(20);
               GL11.glColorPointer(4, true, 32, this.field_78394_d);
               this.field_78392_f.position(0);
               GL11.glVertexPointer(3, 32, this.field_78392_f);
               if(this.field_78409_u == 7 && field_78396_b) {
                  GL11.glDrawArrays(4, var1, var3);
               } else {
                  GL11.glDrawArrays(this.field_78409_u, var1, var3);
               }

            }
         }
      }
   }

   private int drawForIcon(int var1, int var2) {
      GL11.glBindTexture(3553, this.tileTextures[var1]);
      int var3 = -1;
      int var4 = -1;

      for(int var5 = var2; var5 < this.field_78411_s; ++var5) {
         int var6 = this.vertexIconIndex[var5];
         if(var6 == var1) {
            if(var4 < 0) {
               var4 = var5;
            }
         } else if(var4 >= 0) {
            this.draw(var4, var5);
            var4 = -1;
            if(var3 < 0) {
               var3 = var5;
            }
         }
      }

      if(var4 >= 0) {
         this.draw(var4, this.field_78411_s);
      }

      if(var3 < 0) {
         var3 = this.field_78411_s;
      }

      return var3;
   }

   public int func_78381_a() {
      if(!this.field_78415_z) {
         throw new IllegalStateException("Not tesselating!");
      } else {
         int var2;
         int var3;
         if(this.renderingChunk && this.subTessellators.length > 0) {
            boolean var1 = false;

            for(var2 = 0; var2 < this.subTessellators.length; ++var2) {
               var3 = this.subTextures[var2];
               if(var3 <= 0) {
                  break;
               }

               Tessellator var4 = this.subTessellators[var2];
               if(var4.field_78415_z) {
                  GL11.glBindTexture(3553, var3);
                  var1 = true;
                  var4.func_78381_a();
               }
            }

            if(var1) {
               if(this.textureID > 0) {
                  GL11.glBindTexture(3553, this.textureID);
               } else {
                  GL11.glBindTexture(3553, getTerrainTexture());
               }
            }
         }

         this.field_78415_z = false;
         int var5;
         if(this.field_78406_i > 0) {
            if(Config.isMultiTexture() && this.tileTextures != null) {
               this.field_78395_e.clear();
               this.field_78395_e.put(this.field_78405_h, 0, this.field_78412_r);
               this.field_78394_d.position(0);
               this.field_78394_d.limit(this.field_78412_r * 4);
               GL11.glEnableClientState('\u8078');
               GL11.glEnableClientState('\u8076');
               GL11.glEnableClientState('\u8074');
               Arrays.fill(this.drawnIcons, false);
               var5 = 0;

               for(var2 = 0; var2 < this.field_78411_s; ++var2) {
                  var3 = this.vertexIconIndex[var2];
                  if(!this.drawnIcons[var3]) {
                     var2 = this.drawForIcon(var3, var2) - 1;
                     ++var5;
                     this.drawnIcons[var3] = true;
                  }
               }

               GL11.glDisableClientState('\u8078');
               OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
               GL11.glDisableClientState('\u8078');
               OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
               GL11.glDisableClientState('\u8076');
               GL11.glDisableClientState('\u8074');
            } else {
               this.field_78395_e.clear();
               this.field_78395_e.put(this.field_78405_h, 0, this.field_78412_r);
               this.field_78394_d.position(0);
               this.field_78394_d.limit(this.field_78412_r * 4);
               if(this.field_78389_A) {
                  this.field_78391_C = (this.field_78391_C + 1) % this.field_78387_D;
                  ARBVertexBufferObject.glBindBufferARB('\u8892', this.field_78390_B.get(this.field_78391_C));
                  ARBVertexBufferObject.glBufferDataARB('\u8892', this.field_78394_d, '\u88e0');
               }

               if(this.field_78400_o) {
                  if(this.field_78389_A) {
                     GL11.glTexCoordPointer(2, 5126, 32, 12L);
                  } else {
                     this.field_78392_f.position(3);
                     GL11.glTexCoordPointer(2, 32, this.field_78392_f);
                  }

                  GL11.glEnableClientState('\u8078');
               }

               if(this.field_78414_p) {
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
                  if(this.field_78389_A) {
                     GL11.glTexCoordPointer(2, 5122, 32, 28L);
                  } else {
                     this.field_78393_g.position(14);
                     GL11.glTexCoordPointer(2, 32, this.field_78393_g);
                  }

                  GL11.glEnableClientState('\u8078');
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
               }

               if(this.field_78399_n) {
                  if(this.field_78389_A) {
                     GL11.glColorPointer(4, 5121, 32, 20L);
                  } else {
                     this.field_78394_d.position(20);
                     GL11.glColorPointer(4, true, 32, this.field_78394_d);
                  }

                  GL11.glEnableClientState('\u8076');
               }

               if(this.field_78413_q) {
                  if(this.field_78389_A) {
                     GL11.glNormalPointer(5121, 32, 24L);
                  } else {
                     this.field_78394_d.position(24);
                     GL11.glNormalPointer(32, this.field_78394_d);
                  }

                  GL11.glEnableClientState('\u8075');
               }

               if(this.field_78389_A) {
                  GL11.glVertexPointer(3, 5126, 32, 0L);
               } else {
                  this.field_78392_f.position(0);
                  GL11.glVertexPointer(3, 32, this.field_78392_f);
               }

               GL11.glEnableClientState('\u8074');
               if(this.field_78409_u == 7 && field_78396_b) {
                  GL11.glDrawArrays(4, 0, this.field_78406_i);
               } else {
                  GL11.glDrawArrays(this.field_78409_u, 0, this.field_78406_i);
               }

               GL11.glDisableClientState('\u8074');
               if(this.field_78400_o) {
                  GL11.glDisableClientState('\u8078');
               }

               if(this.field_78414_p) {
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
                  GL11.glDisableClientState('\u8078');
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
               }

               if(this.field_78399_n) {
                  GL11.glDisableClientState('\u8076');
               }

               if(this.field_78413_q) {
                  GL11.glDisableClientState('\u8075');
               }
            }
         }

         var5 = this.field_78412_r * 4;
         this.func_78379_d();
         return var5;
      }
   }

   private void func_78379_d() {
      this.field_78406_i = 0;
      this.field_78394_d.clear();
      this.field_78412_r = 0;
      this.field_78411_s = 0;
   }

   public void func_78382_b() {
      this.func_78371_b(7);
   }

   public void func_78371_b(int p_78371_1_) {
      if(this.field_78415_z) {
         throw new IllegalStateException("Already tesselating!");
      } else {
         this.field_78415_z = true;
         this.func_78379_d();
         this.field_78409_u = p_78371_1_;
         this.field_78413_q = false;
         this.field_78399_n = false;
         this.field_78400_o = false;
         this.field_78414_p = false;
         this.field_78410_t = false;
         if(this.renderingChunk && this.textureID == 0) {
            this.tileTextures = getTileTextures(getTerrainTexture());
         } else {
            this.tileTextures = getTileTextures(this.textureID);
         }

      }
   }

   public void func_78385_a(double p_78385_1_, double p_78385_3_) {
      this.field_78400_o = true;
      this.field_78403_j = p_78385_1_;
      this.field_78404_k = p_78385_3_;
   }

   public void func_78380_c(int p_78380_1_) {
      this.field_78414_p = true;
      this.field_78401_l = p_78380_1_;
   }

   public void func_78386_a(float p_78386_1_, float p_78386_2_, float p_78386_3_) {
      this.func_78376_a((int)(p_78386_1_ * 255.0F), (int)(p_78386_2_ * 255.0F), (int)(p_78386_3_ * 255.0F));
   }

   public void func_78369_a(float p_78369_1_, float p_78369_2_, float p_78369_3_, float p_78369_4_) {
      this.func_78370_a((int)(p_78369_1_ * 255.0F), (int)(p_78369_2_ * 255.0F), (int)(p_78369_3_ * 255.0F), (int)(p_78369_4_ * 255.0F));
   }

   public void func_78376_a(int p_78376_1_, int p_78376_2_, int p_78376_3_) {
      this.func_78370_a(p_78376_1_, p_78376_2_, p_78376_3_, 255);
   }

   public void func_78370_a(int p_78370_1_, int p_78370_2_, int p_78370_3_, int p_78370_4_) {
      if(!this.field_78410_t) {
         if(p_78370_1_ > 255) {
            p_78370_1_ = 255;
         }

         if(p_78370_2_ > 255) {
            p_78370_2_ = 255;
         }

         if(p_78370_3_ > 255) {
            p_78370_3_ = 255;
         }

         if(p_78370_4_ > 255) {
            p_78370_4_ = 255;
         }

         if(p_78370_1_ < 0) {
            p_78370_1_ = 0;
         }

         if(p_78370_2_ < 0) {
            p_78370_2_ = 0;
         }

         if(p_78370_3_ < 0) {
            p_78370_3_ = 0;
         }

         if(p_78370_4_ < 0) {
            p_78370_4_ = 0;
         }

         this.field_78399_n = true;
         if(littleEndianByteOrder) {
            this.field_78402_m = p_78370_4_ << 24 | p_78370_3_ << 16 | p_78370_2_ << 8 | p_78370_1_;
         } else {
            this.field_78402_m = p_78370_1_ << 24 | p_78370_2_ << 16 | p_78370_3_ << 8 | p_78370_4_;
         }

      }
   }

   public void func_78374_a(double p_78374_1_, double p_78374_3_, double p_78374_5_, double p_78374_7_, double p_78374_9_) {
      if(Config.isMultiTexture() && this.tileTextures != null) {
         int var11 = this.field_78411_s % 4;
         VertexData var12 = this.vertexDatas[var11];
         var12.x = p_78374_1_;
         var12.y = p_78374_3_;
         var12.z = p_78374_5_;
         var12.u = p_78374_7_;
         var12.v = p_78374_9_;
         var12.color = this.field_78402_m;
         var12.brightness = this.field_78401_l;
         if(var11 != 3) {
            ++this.field_78411_s;
         } else {
            this.field_78411_s -= 3;
            double var13 = (this.vertexDatas[0].u + this.vertexDatas[1].u + this.vertexDatas[2].u + this.vertexDatas[3].u) / 4.0D;
            double var15 = (this.vertexDatas[0].v + this.vertexDatas[1].v + this.vertexDatas[2].v + this.vertexDatas[3].v) / 4.0D;
            if(var13 > 0.875D && var13 < 1.0D && var15 > 0.75D && var15 < 0.875D) {
               boolean var17 = true;
            }

            int var29 = (int)(var13 * 16.0D);
            int var18 = (int)(var15 * 16.0D);
            int var19 = var18 * 16 + var29;
            double var20 = (double)var29 / 16.0D;
            double var22 = (double)var18 / 16.0D;
            int var24 = this.field_78411_s;

            for(int var25 = 0; var25 < 4; ++var25) {
               VertexData var26 = this.vertexDatas[var25];
               p_78374_1_ = var26.x;
               p_78374_3_ = var26.y;
               p_78374_5_ = var26.z;
               p_78374_7_ = var26.u;
               p_78374_9_ = var26.v;
               this.vertexIconIndex[var24 + var25] = var19;
               p_78374_7_ -= var20;
               p_78374_9_ -= var22;
               p_78374_7_ *= 16.0D;
               p_78374_9_ *= 16.0D;
               int var27 = this.field_78402_m;
               this.field_78402_m = var26.color;
               int var28 = this.field_78401_l;
               this.field_78401_l = var26.brightness;
               this.func_78385_a(p_78374_7_, p_78374_9_);
               this.func_78377_a(p_78374_1_, p_78374_3_, p_78374_5_);
               this.field_78402_m = var27;
               this.field_78401_l = var28;
            }

         }
      } else {
         this.func_78385_a(p_78374_7_, p_78374_9_);
         this.func_78377_a(p_78374_1_, p_78374_3_, p_78374_5_);
      }
   }

   public void func_78377_a(double p_78377_1_, double p_78377_3_, double p_78377_5_) {
      if(this.autoGrow && this.field_78412_r >= this.field_78388_E - 32) {
         Config.dbg("Expand tessellator buffer, old: " + this.field_78388_E + ", new: " + this.field_78388_E * 2);
         this.field_78388_E *= 2;
         int[] var7 = new int[this.field_78388_E];
         System.arraycopy(this.field_78405_h, 0, var7, 0, this.field_78405_h.length);
         this.field_78405_h = var7;
         this.field_78394_d = GLAllocation.func_74524_c(this.field_78388_E * 4);
         this.field_78395_e = this.field_78394_d.asIntBuffer();
         this.field_78392_f = this.field_78394_d.asFloatBuffer();
         this.field_78393_g = this.field_78394_d.asShortBuffer();
         int[] var8 = new int[this.field_78388_E];
         System.arraycopy(this.vertexIconIndex, 0, var8, 0, this.vertexIconIndex.length);
         this.vertexIconIndex = var8;
      }

      ++this.field_78411_s;
      if(this.field_78409_u == 7 && field_78396_b && this.field_78411_s % 4 == 0) {
         for(int var9 = 0; var9 < 2; ++var9) {
            int var10 = 8 * (3 - var9);
            if(this.field_78400_o) {
               this.field_78405_h[this.field_78412_r + 3] = this.field_78405_h[this.field_78412_r - var10 + 3];
               this.field_78405_h[this.field_78412_r + 4] = this.field_78405_h[this.field_78412_r - var10 + 4];
            }

            if(this.field_78414_p) {
               this.field_78405_h[this.field_78412_r + 7] = this.field_78405_h[this.field_78412_r - var10 + 7];
            }

            if(this.field_78399_n) {
               this.field_78405_h[this.field_78412_r + 5] = this.field_78405_h[this.field_78412_r - var10 + 5];
            }

            this.field_78405_h[this.field_78412_r + 0] = this.field_78405_h[this.field_78412_r - var10 + 0];
            this.field_78405_h[this.field_78412_r + 1] = this.field_78405_h[this.field_78412_r - var10 + 1];
            this.field_78405_h[this.field_78412_r + 2] = this.field_78405_h[this.field_78412_r - var10 + 2];
            ++this.field_78406_i;
            this.field_78412_r += 8;
         }
      }

      if(this.field_78400_o) {
         this.field_78405_h[this.field_78412_r + 3] = Float.floatToRawIntBits((float)this.field_78403_j);
         this.field_78405_h[this.field_78412_r + 4] = Float.floatToRawIntBits((float)this.field_78404_k);
      }

      if(this.field_78414_p) {
         this.field_78405_h[this.field_78412_r + 7] = this.field_78401_l;
      }

      if(this.field_78399_n) {
         this.field_78405_h[this.field_78412_r + 5] = this.field_78402_m;
      }

      if(this.field_78413_q) {
         this.field_78405_h[this.field_78412_r + 6] = this.field_78416_y;
      }

      this.field_78405_h[this.field_78412_r + 0] = Float.floatToRawIntBits((float)(p_78377_1_ + this.field_78408_v));
      this.field_78405_h[this.field_78412_r + 1] = Float.floatToRawIntBits((float)(p_78377_3_ + this.field_78407_w));
      this.field_78405_h[this.field_78412_r + 2] = Float.floatToRawIntBits((float)(p_78377_5_ + this.field_78417_x));
      this.field_78412_r += 8;
      ++this.field_78406_i;
      if(!this.autoGrow && this.field_78411_s % 4 == 0 && this.field_78412_r >= this.field_78388_E - 32) {
         this.func_78381_a();
         this.field_78415_z = true;
      }

   }

   public void func_78378_d(int p_78378_1_) {
      int var2 = p_78378_1_ >> 16 & 255;
      int var3 = p_78378_1_ >> 8 & 255;
      int var4 = p_78378_1_ & 255;
      this.func_78376_a(var2, var3, var4);
   }

   public void func_78384_a(int p_78384_1_, int p_78384_2_) {
      int var3 = p_78384_1_ >> 16 & 255;
      int var4 = p_78384_1_ >> 8 & 255;
      int var5 = p_78384_1_ & 255;
      this.func_78370_a(var3, var4, var5, p_78384_2_);
   }

   public void func_78383_c() {
      this.field_78410_t = true;
   }

   public void func_78375_b(float p_78375_1_, float p_78375_2_, float p_78375_3_) {
      this.field_78413_q = true;
      byte var4 = (byte)((int)(p_78375_1_ * 127.0F));
      byte var5 = (byte)((int)(p_78375_2_ * 127.0F));
      byte var6 = (byte)((int)(p_78375_3_ * 127.0F));
      this.field_78416_y = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
   }

   public void func_78373_b(double p_78373_1_, double p_78373_3_, double p_78373_5_) {
      this.field_78408_v = p_78373_1_;
      this.field_78407_w = p_78373_3_;
      this.field_78417_x = p_78373_5_;
   }

   public void func_78372_c(float p_78372_1_, float p_78372_2_, float p_78372_3_) {
      this.field_78408_v += (double)p_78372_1_;
      this.field_78407_w += (double)p_78372_2_;
      this.field_78417_x += (double)p_78372_3_;
   }

   public boolean isRenderingChunk() {
      return this.renderingChunk;
   }

   public void setRenderingChunk(boolean var1) {
      if(this.renderingChunk != var1) {
         for(int var2 = 0; var2 < this.subTextures.length; ++var2) {
            this.subTextures[var2] = 0;
         }
      }

      this.renderingChunk = var1;
   }

   public Tessellator getSubTessellator(int var1) {
      Tessellator var2 = this.getSubTessellatorImpl(var1);
      if(!var2.field_78415_z) {
         var2.func_78371_b(this.field_78409_u);
      }

      var2.field_78401_l = this.field_78401_l;
      var2.field_78414_p = this.field_78414_p;
      var2.field_78402_m = this.field_78402_m;
      var2.field_78399_n = this.field_78399_n;
      var2.field_78416_y = this.field_78416_y;
      var2.field_78413_q = this.field_78413_q;
      var2.renderingChunk = this.renderingChunk;
      var2.defaultTexture = false;
      var2.field_78408_v = this.field_78408_v;
      var2.field_78407_w = this.field_78407_w;
      var2.field_78417_x = this.field_78417_x;
      return var2;
   }

   public Tessellator getSubTessellatorImpl(int var1) {
      int var2;
      int var3;
      Tessellator var4;
      for(var2 = 0; var2 < this.subTextures.length; ++var2) {
         var3 = this.subTextures[var2];
         if(var3 == var1) {
            var4 = this.subTessellators[var2];
            return var4;
         }
      }

      for(var2 = 0; var2 < this.subTextures.length; ++var2) {
         var3 = this.subTextures[var2];
         if(var3 <= 0) {
            var4 = this.subTessellators[var2];
            this.subTextures[var2] = var1;
            var4.textureID = var1;
            return var4;
         }
      }

      Tessellator var5 = new Tessellator();
      var5.textureID = var1;
      Tessellator[] var6 = this.subTessellators;
      int[] var7 = this.subTextures;
      this.subTessellators = new Tessellator[var6.length + 1];
      this.subTextures = new int[var7.length + 1];
      System.arraycopy(var6, 0, this.subTessellators, 0, var6.length);
      System.arraycopy(var7, 0, this.subTextures, 0, var7.length);
      this.subTessellators[var6.length] = var5;
      this.subTextures[var7.length] = var1;
      Config.dbg("Allocated subtessellator, count: " + this.subTessellators.length);
      return var5;
   }

   public static int getTerrainTexture() {
      if(terrainTexture == 0) {
         terrainTexture = Config.getMinecraft().field_71446_o.func_78341_b("/terrain.png");
      }

      return terrainTexture;
   }

   public static void setTileTextures(int var0, int[] var1) {
      if(var0 >= atlasSubTextures.length) {
         int[][] var2 = new int[var0 + 1][];
         System.arraycopy(atlasSubTextures, 0, var2, 0, atlasSubTextures.length);
         atlasSubTextures = var2;
      }

      atlasSubTextures[var0] = var1;
   }

   public static int[] getTileTextures(int var0) {
      return var0 > 0 && var0 < atlasSubTextures.length?atlasSubTextures[var0]:null;
   }

}
