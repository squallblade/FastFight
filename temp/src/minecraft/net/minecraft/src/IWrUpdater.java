package net.minecraft.src;

import java.util.List;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.World;
import net.minecraft.src.WorldRenderer;

public interface IWrUpdater {

   void initialize();

   WorldRenderer makeWorldRenderer(World var1, List var2, int var3, int var4, int var5, int var6);

   void preRender(RenderGlobal var1, EntityLiving var2);

   void postRender();

   boolean updateRenderers(RenderGlobal var1, EntityLiving var2, boolean var3);

   void resumeBackgroundUpdates();

   void pauseBackgroundUpdates();

   void finishCurrentUpdate();

   void terminate();
}
