package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class VersionThread extends Thread
{
    public static String newRelease = null;
    public static final String OF_NAME = "OptiFine";
    public static final String MC_VERSION = "1.4.6";
    public static final String OF_EDITION = "L";
    public static final String OF_RELEASE = "B5";
    public static final String VERSION = "OptiFine_1.4.6_L_B5";

    public void run()
    {
        HttpURLConnection var1 = null;

        try
        {
            dbg("Checking for new version");
            URL var2 = new URL("http://optifine.net/version/1.4.6/L.txt");
            var1 = (HttpURLConnection)var2.openConnection();
            var1.setDoInput(true);
            var1.setDoOutput(false);
            var1.connect();

            try
            {
                InputStream var3 = var1.getInputStream();
                String var4 = readInputStream(var3);
                var3.close();
                String[] var5 = tokenize(var4, "\n\r");

                if (var5.length >= 1)
                {
                    String var6 = var5[0];
                    dbg("Version found: " + var6);

                    if (compareRelease(var6, "B5") <= 0)
                    {
                        return;
                    }

                    newRelease = var6;
                    return;
                }
            }
            finally
            {
                if (var1 != null)
                {
                    var1.disconnect();
                }
            }
        }
        catch (Exception var11)
        {
            dbg("Version check failed");
        }
    }

    public static void checkOpenGlCaps()
    {
        log("");
        log("OptiFine_1.4.6_L_B5");
        log("" + new Date());
        log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        log("LWJGL: " + Sys.getVersion());
        log("OpenGL: " + GL11.glGetString(GL11.GL_RENDERER) + " version " + GL11.glGetString(GL11.GL_VERSION) + ", " + GL11.glGetString(GL11.GL_VENDOR));
        int var0 = getOpenGlVersion();
        String var1 = "" + var0 / 10 + "." + var0 % 10;
        log("OpenGL Version: " + var1);

        if (!GLContext.getCapabilities().GL_NV_fog_distance)
        {
            log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }

        if (!GLContext.getCapabilities().GL_ARB_occlusion_query)
        {
            log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }
    }

    private static int getOpenGlVersion()
    {
        return !GLContext.getCapabilities().OpenGL11 ? 10 : (!GLContext.getCapabilities().OpenGL12 ? 11 : (!GLContext.getCapabilities().OpenGL13 ? 12 : (!GLContext.getCapabilities().OpenGL14 ? 13 : (!GLContext.getCapabilities().OpenGL15 ? 14 : (!GLContext.getCapabilities().OpenGL20 ? 15 : (!GLContext.getCapabilities().OpenGL21 ? 20 : (!GLContext.getCapabilities().OpenGL30 ? 21 : (!GLContext.getCapabilities().OpenGL31 ? 30 : (!GLContext.getCapabilities().OpenGL32 ? 31 : (!GLContext.getCapabilities().OpenGL33 ? 32 : (!GLContext.getCapabilities().OpenGL40 ? 33 : 40)))))))))));
    }

    private static void dbg(String var0)
    {
        System.out.println(var0);
    }

    private static void log(String var0)
    {
        System.out.println(var0);
    }

    public static String readInputStream(InputStream var0) throws IOException
    {
        return readInputStream(var0, "ASCII");
    }

    public static String readInputStream(InputStream var0, String var1) throws IOException
    {
        InputStreamReader var2 = new InputStreamReader(var0, var1);
        BufferedReader var3 = new BufferedReader(var2);
        StringBuffer var4 = new StringBuffer();

        while (true)
        {
            String var5 = var3.readLine();

            if (var5 == null)
            {
                return var4.toString();
            }

            var4.append(var5);
            var4.append("\n");
        }
    }

    public static String[] tokenize(String var0, String var1)
    {
        StringTokenizer var2 = new StringTokenizer(var0, var1);
        ArrayList var3 = new ArrayList();

        while (var2.hasMoreTokens())
        {
            String var4 = var2.nextToken();
            var3.add(var4);
        }

        String[] var5 = (String[])((String[])var3.toArray(new String[var3.size()]));
        return var5;
    }

    public static int compareRelease(String var0, String var1)
    {
        String[] var2 = splitRelease(var0);
        String[] var3 = splitRelease(var1);
        String var4 = var2[0];
        String var5 = var3[0];

        if (!var4.equals(var5))
        {
            return var4.compareTo(var5);
        }
        else
        {
            int var6 = parseInt(var2[1], -1);
            int var7 = parseInt(var3[1], -1);

            if (var6 != var7)
            {
                return var6 - var7;
            }
            else
            {
                String var8 = var2[2];
                String var9 = var3[2];
                return var8.compareTo(var9);
            }
        }
    }

    private static String[] splitRelease(String var0)
    {
        if (var0 != null && var0.length() > 0)
        {
            String var1 = var0.substring(0, 1);

            if (var0.length() <= 1)
            {
                return new String[] {var1, "", ""};
            }
            else
            {
                int var2;

                for (var2 = 1; var2 < var0.length() && Character.isDigit(var0.charAt(var2)); ++var2)
                {
                    ;
                }

                String var3 = var0.substring(1, var2);

                if (var2 >= var0.length())
                {
                    return new String[] {var1, var3, ""};
                }
                else
                {
                    String var4 = var0.substring(var2);
                    return new String[] {var1, var3, var4};
                }
            }
        }
        else
        {
            return new String[] {"", "", ""};
        }
    }

    public static int parseInt(String var0, int var1)
    {
        try
        {
            return var0 == null ? var1 : Integer.parseInt(var0);
        }
        catch (NumberFormatException var3)
        {
            return var1;
        }
    }
}
