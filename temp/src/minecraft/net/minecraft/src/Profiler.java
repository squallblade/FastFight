package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.src.Config;
import net.minecraft.src.ProfilerResult;

public class Profiler {

   private final List field_76325_b = new ArrayList();
   private final List field_76326_c = new ArrayList();
   public boolean field_76327_a = false;
   private String field_76323_d = "";
   private final Map field_76324_e = new HashMap();
   public boolean profilerGlobalEnabled = true;
   private boolean profilerLocalEnabled;
   private long startTickNano;
   public long timeTickNano;
   private long startUpdateChunksNano;
   public long timeUpdateChunksNano;


   public Profiler() {
      this.profilerLocalEnabled = this.profilerGlobalEnabled;
      this.startTickNano = 0L;
      this.timeTickNano = 0L;
      this.startUpdateChunksNano = 0L;
      this.timeUpdateChunksNano = 0L;
   }

   public void func_76317_a() {
      this.field_76324_e.clear();
      this.field_76323_d = "";
      this.field_76325_b.clear();
      this.profilerLocalEnabled = this.profilerGlobalEnabled;
   }

   public void func_76320_a(String p_76320_1_) {
      if(Config.getGameSettings().field_74330_P) {
         if(this.startTickNano == 0L && p_76320_1_.equals("tick")) {
            this.startTickNano = System.nanoTime();
         }

         if(this.startTickNano != 0L && p_76320_1_.equals("preRenderErrors")) {
            this.timeTickNano = System.nanoTime() - this.startTickNano;
            this.startTickNano = 0L;
         }

         if(this.startUpdateChunksNano == 0L && p_76320_1_.equals("updatechunks")) {
            this.startUpdateChunksNano = System.nanoTime();
         }

         if(this.startUpdateChunksNano != 0L && p_76320_1_.equals("terrain")) {
            this.timeUpdateChunksNano = System.nanoTime() - this.startUpdateChunksNano;
            this.startUpdateChunksNano = 0L;
         }
      }

      if(this.profilerLocalEnabled) {
         if(this.field_76327_a) {
            if(this.field_76323_d.length() > 0) {
               this.field_76323_d = this.field_76323_d + ".";
            }

            this.field_76323_d = this.field_76323_d + p_76320_1_;
            this.field_76325_b.add(this.field_76323_d);
            this.field_76326_c.add(Long.valueOf(System.nanoTime()));
         }

      }
   }

   public void func_76319_b() {
      if(this.profilerLocalEnabled) {
         if(this.field_76327_a) {
            long var1 = System.nanoTime();
            long var3 = ((Long)this.field_76326_c.remove(this.field_76326_c.size() - 1)).longValue();
            this.field_76325_b.remove(this.field_76325_b.size() - 1);
            long var5 = var1 - var3;
            if(this.field_76324_e.containsKey(this.field_76323_d)) {
               this.field_76324_e.put(this.field_76323_d, Long.valueOf(((Long)this.field_76324_e.get(this.field_76323_d)).longValue() + var5));
            } else {
               this.field_76324_e.put(this.field_76323_d, Long.valueOf(var5));
            }

            if(var5 > 100000000L) {
               System.out.println("Something\'s taking too long! \'" + this.field_76323_d + "\' took aprox " + (double)var5 / 1000000.0D + " ms");
            }

            this.field_76323_d = !this.field_76325_b.isEmpty()?(String)this.field_76325_b.get(this.field_76325_b.size() - 1):"";
         }

      }
   }

   public List func_76321_b(String p_76321_1_) {
      this.profilerLocalEnabled = this.profilerGlobalEnabled;
      if(!this.profilerLocalEnabled) {
         return new ArrayList(Arrays.asList(new ProfilerResult[]{new ProfilerResult("root", 0.0D, 0.0D)}));
      } else if(!this.field_76327_a) {
         return null;
      } else {
         long var2 = this.field_76324_e.containsKey("root")?((Long)this.field_76324_e.get("root")).longValue():0L;
         long var4 = this.field_76324_e.containsKey(p_76321_1_)?((Long)this.field_76324_e.get(p_76321_1_)).longValue():-1L;
         ArrayList var6 = new ArrayList();
         if(p_76321_1_.length() > 0) {
            p_76321_1_ = p_76321_1_ + ".";
         }

         long var7 = 0L;
         Iterator var9 = this.field_76324_e.keySet().iterator();

         while(var9.hasNext()) {
            String var10 = (String)var9.next();
            if(var10.length() > p_76321_1_.length() && var10.startsWith(p_76321_1_) && var10.indexOf(".", p_76321_1_.length() + 1) < 0) {
               var7 += ((Long)this.field_76324_e.get(var10)).longValue();
            }
         }

         float var20 = (float)var7;
         if(var7 < var4) {
            var7 = var4;
         }

         if(var2 < var7) {
            var2 = var7;
         }

         Iterator var11 = this.field_76324_e.keySet().iterator();

         String var12;
         while(var11.hasNext()) {
            var12 = (String)var11.next();
            if(var12.length() > p_76321_1_.length() && var12.startsWith(p_76321_1_) && var12.indexOf(".", p_76321_1_.length() + 1) < 0) {
               long var13 = ((Long)this.field_76324_e.get(var12)).longValue();
               double var15 = (double)var13 * 100.0D / (double)var7;
               double var17 = (double)var13 * 100.0D / (double)var2;
               String var19 = var12.substring(p_76321_1_.length());
               var6.add(new ProfilerResult(var19, var15, var17));
            }
         }

         var11 = this.field_76324_e.keySet().iterator();

         while(var11.hasNext()) {
            var12 = (String)var11.next();
            this.field_76324_e.put(var12, Long.valueOf(((Long)this.field_76324_e.get(var12)).longValue() * 999L / 1000L));
         }

         if((float)var7 > var20) {
            var6.add(new ProfilerResult("unspecified", (double)((float)var7 - var20) * 100.0D / (double)var7, (double)((float)var7 - var20) * 100.0D / (double)var2));
         }

         Collections.sort(var6);
         var6.add(0, new ProfilerResult(p_76321_1_, 100.0D, (double)var7 * 100.0D / (double)var2));
         return var6;
      }
   }

   public void func_76318_c(String p_76318_1_) {
      if(this.profilerLocalEnabled) {
         this.func_76319_b();
         this.func_76320_a(p_76318_1_);
      }
   }

   public String func_76322_c() {
      return this.field_76325_b.size() == 0?"[UNKNOWN]":(String)this.field_76325_b.get(this.field_76325_b.size() - 1);
   }
}
