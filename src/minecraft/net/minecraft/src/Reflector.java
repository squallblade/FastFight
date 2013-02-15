package net.minecraft.src;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reflector
{
    private static Class[] classes = new Class[30];
    private static boolean[] classesChecked = new boolean[300];
    private static Map classNameMap = null;
    private static Method[] methods = new Method[300];
    private static boolean[] methodsChecked = new boolean[300];
    private static Map methodNameMap = null;
    private static Field[] fields = new Field[300];
    private static boolean[] fieldsChecked = new boolean[300];
    private static Map fieldNameMap = null;
    public static final int ModLoader = 0;
    public static final int ModLoader_renderWorldBlock = 0;
    public static final int ModLoader_renderInvBlock = 1;
    public static final int ModLoader_renderBlockIsItemFull3D = 2;
    public static final int ForgeHooksClient = 1;
    public static final int ForgeHooksClient_onDrawBlockHighlight = 10;
    public static final int ForgeHooksClient_orientBedCamera = 11;
    public static final int ForgeHooksClient_getTexture = 12;
    public static final int ForgeHooksClient_beforeRenderPass = 13;
    public static final int ForgeHooksClient_afterRenderPass = 14;
    public static final int ForgeHooksClient_beforeBlockRender = 15;
    public static final int ForgeHooksClient_afterBlockRender = 16;
    public static final int ForgeHooksClient_dispatchRenderLast = 17;
    public static final int ForgeHooksClient_onTextureLoadPre = 18;
    public static final int ForgeHooksClient_onTextureLoad = 19;
    public static final int MinecraftForgeClient = 2;
    public static final int MinecraftForgeClient_getItemRenderer = 20;
    public static final int LightCache = 3;
    public static final int LightCache_clear = 30;
    public static final int BlockCoord = 4;
    public static final int BlockCoord_resetPool = 40;
    public static final int ForgeBlock = 5;
    public static final int ForgeBlock_isLadder = 50;
    public static final int ForgeBlock_isBed = 51;
    public static final int ForgeBlock_getBedDirection = 52;
    public static final int ForgeBlock_isBedFoot = 53;
    public static final int ForgeBlock_canRenderInPass = 54;
    public static final int ItemRenderType = 6;
    public static final int FMLRender = 7;
    public static final int FMLRender_setTextureDimensions = 70;
    public static final int FMLRender_preRegisterEffect = 71;
    public static final int FMLRender_onUpdateTextureEffect = 72;
    public static final int FMLRender_onTexturePackChange = 73;
    public static final int ForgeEffectRenderer = 8;
    public static final int ForgeEffectRenderer_addEffect = 80;
    public static final int ForgeHooks = 9;
    public static final int ForgeHooks_onLivingSetAttackTarget = 90;
    public static final int ForgeHooks_onLivingUpdate = 91;
    public static final int ForgeHooks_onLivingAttack = 92;
    public static final int ForgeHooks_onLivingHurt = 93;
    public static final int ForgeHooks_onLivingDeath = 94;
    public static final int ForgeHooks_onLivingDrops = 95;
    public static final int ForgeHooks_onLivingFall = 96;
    public static final int ForgeHooks_onLivingJump = 97;
    public static final int ForgeHooks_isLivingOnLadder = 98;
    public static final int Entity = 10;
    public static final int List = 11;
    public static final int List_clear = 110;
    public static final int ForgeItem = 12;
    public static final int ForgeItem_getTextureFile = 120;
    public static final int PotionEffect = 13;
    public static final int PotionEffect_isCurativeItem = 130;
    public static final int WorldProvider = 14;
    public static final int WorldProvider_getSkyProvider = 140;
    public static final int WorldProvider_getCloudRenderer = 141;
    public static final int SkyProvider = 15;
    public static final int SkyProvider_render = 150;
    public static final int FMLClientHandler = 16;
    public static final int FMLClientHandler_instance = 160;
    public static final int FMLClientHandler_isLoading = 161;
    public static final int DimensionManager = 17;
    public static final int DimensionManager_getStaticDimensionIDs = 170;
    public static final int WorldEvent_Load = 18;
    public static final int MinecraftForge = 19;
    public static final int EventBus = 20;
    public static final int EventBus_post = 200;
    public static final int FMLCommonHandler = 21;
    public static final int FMLCommonHandler_instance = 210;
    public static final int FMLCommonHandler_handleServerStarting = 211;
    public static final int ChunkWatchEvent_UnWatch = 22;
    public static final int LightCache_cache = 30;
    public static final int ItemRenderType_EQUIPPED = 60;
    public static final int Entity_captureDrops = 100;
    public static final int Entity_capturedDrops = 101;
    public static final int MinecraftForge_EVENT_BUS = 190;
    public static final Object[] NO_PARAMS = new Object[0];

    private static Map getClassNameMap()
    {
        if (classNameMap == null)
        {
            classNameMap = new HashMap();
            classNameMap.put(Integer.valueOf(0), "ModLoader");
            classNameMap.put(Integer.valueOf(1), "net.minecraftforge.client.ForgeHooksClient");
            classNameMap.put(Integer.valueOf(2), "net.minecraftforge.client.MinecraftForgeClient");
            classNameMap.put(Integer.valueOf(3), "LightCache");
            classNameMap.put(Integer.valueOf(4), "BlockCoord");
            classNameMap.put(Integer.valueOf(5), Block.class);
            classNameMap.put(Integer.valueOf(6), "net.minecraftforge.client.IItemRenderer$ItemRenderType");
            classNameMap.put(Integer.valueOf(7), "FMLRenderAccessLibrary");
            classNameMap.put(Integer.valueOf(8), EffectRenderer.class);
            classNameMap.put(Integer.valueOf(9), "net.minecraftforge.common.ForgeHooks");
            classNameMap.put(Integer.valueOf(10), Entity.class);
            classNameMap.put(Integer.valueOf(11), List.class);
            classNameMap.put(Integer.valueOf(12), Item.class);
            classNameMap.put(Integer.valueOf(13), PotionEffect.class);
            classNameMap.put(Integer.valueOf(14), WorldProvider.class);
            classNameMap.put(Integer.valueOf(15), "net.minecraftforge.client.SkyProvider");
            classNameMap.put(Integer.valueOf(16), "cpw.mods.fml.client.FMLClientHandler");
            classNameMap.put(Integer.valueOf(17), "net.minecraftforge.common.DimensionManager");
            classNameMap.put(Integer.valueOf(18), "net.minecraftforge.event.world.WorldEvent$Load");
            classNameMap.put(Integer.valueOf(19), "net.minecraftforge.common.MinecraftForge");
            classNameMap.put(Integer.valueOf(20), "net.minecraftforge.event.EventBus");
            classNameMap.put(Integer.valueOf(21), "cpw.mods.fml.common.FMLCommonHandler");
            classNameMap.put(Integer.valueOf(22), "net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
        }

        return classNameMap;
    }

    private static Map getMethodNameMap()
    {
        if (methodNameMap == null)
        {
            methodNameMap = new HashMap();
            methodNameMap.put(Integer.valueOf(0), "renderWorldBlock");
            methodNameMap.put(Integer.valueOf(1), "renderInvBlock");
            methodNameMap.put(Integer.valueOf(2), "renderBlockIsItemFull3D");
            methodNameMap.put(Integer.valueOf(10), "onDrawBlockHighlight");
            methodNameMap.put(Integer.valueOf(11), "orientBedCamera");
            methodNameMap.put(Integer.valueOf(12), "getTexture");
            methodNameMap.put(Integer.valueOf(13), "beforeRenderPass");
            methodNameMap.put(Integer.valueOf(14), "afterRenderPass");
            methodNameMap.put(Integer.valueOf(15), "beforeBlockRender");
            methodNameMap.put(Integer.valueOf(16), "afterBlockRender");
            methodNameMap.put(Integer.valueOf(17), "dispatchRenderLast");
            methodNameMap.put(Integer.valueOf(18), "onTextureLoadPre");
            methodNameMap.put(Integer.valueOf(19), "onTextureLoad");
            methodNameMap.put(Integer.valueOf(20), "getItemRenderer");
            methodNameMap.put(Integer.valueOf(30), "clear");
            methodNameMap.put(Integer.valueOf(40), "resetPool");
            methodNameMap.put(Integer.valueOf(50), "isLadder");
            methodNameMap.put(Integer.valueOf(51), "isBed");
            methodNameMap.put(Integer.valueOf(52), "getBedDirection");
            methodNameMap.put(Integer.valueOf(53), "isBedFoot");
            methodNameMap.put(Integer.valueOf(54), "canRenderInPass");
            methodNameMap.put(Integer.valueOf(70), "setTextureDimensions");
            methodNameMap.put(Integer.valueOf(71), "preRegisterEffect");
            methodNameMap.put(Integer.valueOf(72), "onUpdateTextureEffect");
            methodNameMap.put(Integer.valueOf(73), "onTexturePackChange");
            methodNameMap.put(Integer.valueOf(80), "addEffect");
            methodNameMap.put(Integer.valueOf(90), "onLivingSetAttackTarget");
            methodNameMap.put(Integer.valueOf(91), "onLivingUpdate");
            methodNameMap.put(Integer.valueOf(92), "onLivingAttack");
            methodNameMap.put(Integer.valueOf(93), "onLivingHurt");
            methodNameMap.put(Integer.valueOf(94), "onLivingDeath");
            methodNameMap.put(Integer.valueOf(95), "onLivingDrops");
            methodNameMap.put(Integer.valueOf(96), "onLivingFall");
            methodNameMap.put(Integer.valueOf(97), "onLivingJump");
            methodNameMap.put(Integer.valueOf(98), "isLivingOnLadder");
            methodNameMap.put(Integer.valueOf(110), "clear");
            methodNameMap.put(Integer.valueOf(120), "getTextureFile");
            methodNameMap.put(Integer.valueOf(130), "isCurativeItem");
            methodNameMap.put(Integer.valueOf(140), "getSkyProvider");
            methodNameMap.put(Integer.valueOf(141), "getCloudRenderer");
            methodNameMap.put(Integer.valueOf(150), "render");
            methodNameMap.put(Integer.valueOf(160), "instance");
            methodNameMap.put(Integer.valueOf(161), "isLoading");
            methodNameMap.put(Integer.valueOf(170), "getStaticDimensionIDs");
            methodNameMap.put(Integer.valueOf(200), "post");
            methodNameMap.put(Integer.valueOf(210), "instance");
            methodNameMap.put(Integer.valueOf(211), "handleServerStarting");
        }

        return methodNameMap;
    }

    private static Map getFieldNameMap()
    {
        if (fieldNameMap == null)
        {
            fieldNameMap = new HashMap();
            fieldNameMap.put(Integer.valueOf(30), "cache");
            fieldNameMap.put(Integer.valueOf(60), "EQUIPPED");
            fieldNameMap.put(Integer.valueOf(100), "captureDrops");
            fieldNameMap.put(Integer.valueOf(101), "capturedDrops");
            fieldNameMap.put(Integer.valueOf(190), "EVENT_BUS");
        }

        return fieldNameMap;
    }

    public static void callVoid(int var0, Object[] var1)
    {
        try
        {
            Method var2 = getMethod(var0);

            if (var2 == null)
            {
                return;
            }

            var2.invoke((Object)null, var1);
        }
        catch (Exception var3)
        {
            var3.printStackTrace();
        }
    }

    public static int callInt(int var0, Object[] var1)
    {
        Integer var2 = (Integer)call(var0, var1);
        return var2.intValue();
    }

    public static String callString(int var0, Object[] var1)
    {
        return (String)call(var0, var1);
    }

    public static String callString(Object var0, int var1, Object[] var2)
    {
        return (String)call(var0, var1, var2);
    }

    public static boolean callBoolean(int var0, Object[] var1)
    {
        try
        {
            Method var2 = getMethod(var0);

            if (var2 == null)
            {
                return false;
            }
            else
            {
                Boolean var3 = (Boolean)var2.invoke((Object)null, var1);
                return var3.booleanValue();
            }
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
            return false;
        }
    }

    public static float callFloat(int var0, Object[] var1)
    {
        try
        {
            Method var2 = getMethod(var0);

            if (var2 == null)
            {
                return 0.0F;
            }
            else
            {
                Float var3 = (Float)var2.invoke((Object)null, var1);
                return var3.floatValue();
            }
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
            return 0.0F;
        }
    }

    public static boolean callBoolean(Object var0, int var1, Object[] var2)
    {
        try
        {
            Method var3 = getMethod(var1);

            if (var3 == null)
            {
                return false;
            }
            else
            {
                Boolean var4 = (Boolean)var3.invoke(var0, var2);
                return var4.booleanValue();
            }
        }
        catch (Throwable var5)
        {
            var5.printStackTrace();
            return false;
        }
    }

    public static int callInt(Object var0, int var1, Object[] var2)
    {
        try
        {
            Method var3 = getMethod(var1);

            if (var3 == null)
            {
                return 0;
            }
            else
            {
                Integer var4 = (Integer)var3.invoke(var0, var2);
                return var4.intValue();
            }
        }
        catch (Throwable var5)
        {
            var5.printStackTrace();
            return 0;
        }
    }

    public static Object call(int var0, Object[] var1)
    {
        try
        {
            Method var2 = getMethod(var0);

            if (var2 == null)
            {
                return null;
            }
            else
            {
                Object var3 = var2.invoke((Object)null, var1);
                return var3;
            }
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
            return null;
        }
    }

    public static Object call(Object var0, int var1, Object[] var2)
    {
        try
        {
            Method var3 = getMethod(var1);

            if (var3 == null)
            {
                return null;
            }
            else
            {
                Object var4 = var3.invoke(var0, var2);
                return var4;
            }
        }
        catch (Throwable var5)
        {
            var5.printStackTrace();
            return null;
        }
    }

    public static void callVoid(Object var0, int var1, Object[] var2)
    {
        try
        {
            if (var0 == null)
            {
                return;
            }

            Method var3 = getMethod(var1);

            if (var3 == null)
            {
                return;
            }

            var3.invoke(var0, var2);
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
        }
    }

    private static Method getMethod(int var0)
    {
        Method var1 = methods[var0];

        if (var1 == null)
        {
            if (methodsChecked[var0])
            {
                return null;
            }

            methodsChecked[var0] = true;
            var1 = findMethod(var0);
            methods[var0] = var1;
        }

        return var1;
    }

    private static Method findMethod(int var0)
    {
        int var1 = var0 / 10;
        Class var2 = getClass(var1);

        if (var2 == null)
        {
            return null;
        }
        else
        {
            String var3 = (String)getMethodNameMap().get(Integer.valueOf(var0));

            if (var3 == null)
            {
                Config.log("Method name not found for id: " + var0);
                return null;
            }
            else
            {
                Method[] var4 = var2.getMethods();

                for (int var5 = 0; var5 < var4.length; ++var5)
                {
                    Method var6 = var4[var5];

                    if (var6.getName().equals(var3))
                    {
                        return var6;
                    }
                }

                Config.log("Method not found: " + var2.getName() + "." + var3);
                return null;
            }
        }
    }

    private static Field getField(int var0)
    {
        Field var1 = fields[var0];

        if (var1 == null)
        {
            if (fieldsChecked[var0])
            {
                return null;
            }

            fieldsChecked[var0] = true;
            var1 = findField(var0);
            fields[var0] = var1;
        }

        return var1;
    }

    private static Field findField(int var0)
    {
        int var1 = var0 / 10;
        Class var2 = getClass(var1);

        if (var2 == null)
        {
            return null;
        }
        else
        {
            String var3 = (String)getFieldNameMap().get(Integer.valueOf(var0));

            if (var3 == null)
            {
                Config.log("Field name not found for id: " + var0);
                return null;
            }
            else
            {
                try
                {
                    Field var4 = var2.getDeclaredField(var3);
                    return var4;
                }
                catch (SecurityException var5)
                {
                    var5.printStackTrace();
                }
                catch (NoSuchFieldException var6)
                {
                    Config.log("Field not found: " + var2.getName() + "." + var3);
                }

                return null;
            }
        }
    }

    private static Class getClass(int var0)
    {
        Class var1 = classes[var0];

        if (var1 == null)
        {
            if (classesChecked[var0])
            {
                return null;
            }

            classesChecked[var0] = true;
            Object var2 = getClassNameMap().get(Integer.valueOf(var0));

            if (var2 instanceof Class)
            {
                var1 = (Class)var2;
                classes[var0] = var1;
                return var1;
            }

            String var3 = (String)var2;

            if (var3 == null)
            {
                Config.log("Class name not found for id: " + var0);
                return null;
            }

            try
            {
                var1 = Class.forName(var3);
                classes[var0] = var1;
            }
            catch (ClassNotFoundException var5)
            {
                Config.log("Class not present: " + var3);
            }
            catch (Throwable var6)
            {
                var6.printStackTrace();
            }
        }

        return var1;
    }

    public static boolean hasClass(int var0)
    {
        Class var1 = getClass(var0);
        return var1 != null;
    }

    public static boolean hasMethod(int var0)
    {
        Method var1 = getMethod(var0);
        return var1 != null;
    }

    public static Object getFieldValue(int var0)
    {
        return getFieldValue((Object)null, var0);
    }

    public static Object getFieldValue(Object var0, int var1)
    {
        try
        {
            Field var2 = getField(var1);

            if (var2 == null)
            {
                return null;
            }
            else
            {
                Object var3 = var2.get(var0);
                return var3;
            }
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
            return null;
        }
    }

    public static void setFieldValue(int var0, Object var1)
    {
        setFieldValue((Object)null, var0, var1);
    }

    public static void setFieldValue(Object var0, int var1, Object var2)
    {
        try
        {
            Field var3 = getField(var1);

            if (var3 == null)
            {
                return;
            }

            var3.set(var0, var2);
        }
        catch (Throwable var4)
        {
            var4.printStackTrace();
        }
    }

    public static void postForgeBusEvent(int var0, Object[] var1)
    {
        try
        {
            Class var2 = getClass(var0);

            if (var2 == null)
            {
                return;
            }

            Constructor[] var3 = var2.getConstructors();
            Constructor var4 = getConstructor(var3, var1);

            if (var4 == null)
            {
                return;
            }

            Object var5 = var4.newInstance(var1);
            Object var6 = getFieldValue(190);

            if (var6 == null)
            {
                return;
            }

            call(var6, 200, new Object[] {var5});
        }
        catch (Throwable var7)
        {
            var7.printStackTrace();
        }
    }

    private static Constructor getConstructor(Constructor[] var0, Object[] var1)
    {
        for (int var2 = 0; var2 < var0.length; ++var2)
        {
            Constructor var3 = var0[var2];

            if (var3.getParameterTypes().length == var1.length)
            {
                return var3;
            }
        }

        return null;
    }
}
