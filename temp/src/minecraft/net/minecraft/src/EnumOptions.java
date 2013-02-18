package net.minecraft.src;


public enum EnumOptions {

   MUSIC("MUSIC", 0, "options.music", true, false),
   SOUND("SOUND", 1, "options.sound", true, false),
   INVERT_MOUSE("INVERT_MOUSE", 2, "options.invertMouse", false, true),
   SENSITIVITY("SENSITIVITY", 3, "options.sensitivity", true, false),
   FOV("FOV", 4, "options.fov", true, false),
   GAMMA("GAMMA", 5, "options.gamma", true, false),
   RENDER_DISTANCE("RENDER_DISTANCE", 6, "options.renderDistance", false, false),
   VIEW_BOBBING("VIEW_BOBBING", 7, "options.viewBobbing", false, true),
   ANAGLYPH("ANAGLYPH", 8, "options.anaglyph", false, true),
   ADVANCED_OPENGL("ADVANCED_OPENGL", 9, "options.advancedOpengl", false, true),
   FRAMERATE_LIMIT("FRAMERATE_LIMIT", 10, "options.framerateLimit", false, false),
   DIFFICULTY("DIFFICULTY", 11, "options.difficulty", false, false),
   GRAPHICS("GRAPHICS", 12, "options.graphics", false, false),
   AMBIENT_OCCLUSION("AMBIENT_OCCLUSION", 13, "options.ao", false, true),
   GUI_SCALE("GUI_SCALE", 14, "options.guiScale", false, false),
   RENDER_CLOUDS("RENDER_CLOUDS", 15, "options.renderClouds", false, true),
   PARTICLES("PARTICLES", 16, "options.particles", false, false),
   CHAT_VISIBILITY("CHAT_VISIBILITY", 17, "options.chat.visibility", false, false),
   CHAT_COLOR("CHAT_COLOR", 18, "options.chat.color", false, true),
   CHAT_LINKS("CHAT_LINKS", 19, "options.chat.links", false, true),
   CHAT_OPACITY("CHAT_OPACITY", 20, "options.chat.opacity", true, false),
   CHAT_LINKS_PROMPT("CHAT_LINKS_PROMPT", 21, "options.chat.links.prompt", false, true),
   USE_SERVER_TEXTURES("USE_SERVER_TEXTURES", 22, "options.serverTextures", false, true),
   SNOOPER_ENABLED("SNOOPER_ENABLED", 23, "options.snooper", false, true),
   USE_FULLSCREEN("USE_FULLSCREEN", 24, "options.fullscreen", false, true),
   ENABLE_VSYNC("ENABLE_VSYNC", 25, "options.vsync", false, true),
   SHOW_CAPE("SHOW_CAPE", 26, "options.showCape", false, true),
   TOUCHSCREEN("TOUCHSCREEN", 27, "options.touchscreen", false, true),
   FOG_FANCY("FOG_FANCY", 28, "Fog", false, false),
   FOG_START("FOG_START", 29, "Fog Start", false, false),
   MIPMAP_LEVEL("MIPMAP_LEVEL", 30, "Mipmap Level", false, false),
   MIPMAP_TYPE("MIPMAP_TYPE", 31, "Mipmap Type", false, false),
   LOAD_FAR("LOAD_FAR", 32, "Load Far", false, false),
   PRELOADED_CHUNKS("PRELOADED_CHUNKS", 33, "Preloaded Chunks", false, false),
   SMOOTH_FPS("SMOOTH_FPS", 34, "Smooth FPS", false, false),
   CLOUDS("CLOUDS", 35, "Clouds", false, false),
   CLOUD_HEIGHT("CLOUD_HEIGHT", 36, "Cloud Height", true, false),
   TREES("TREES", 37, "Trees", false, false),
   GRASS("GRASS", 38, "Grass", false, false),
   RAIN("RAIN", 39, "Rain & Snow", false, false),
   WATER("WATER", 40, "Water", false, false),
   ANIMATED_WATER("ANIMATED_WATER", 41, "Water Animated", false, false),
   ANIMATED_LAVA("ANIMATED_LAVA", 42, "Lava Animated", false, false),
   ANIMATED_FIRE("ANIMATED_FIRE", 43, "Fire Animated", false, false),
   ANIMATED_PORTAL("ANIMATED_PORTAL", 44, "Portal Animated", false, false),
   AO_LEVEL("AO_LEVEL", 45, "Smooth Lighting", true, false),
   LAGOMETER("LAGOMETER", 46, "Lagometer", false, false),
   AUTOSAVE_TICKS("AUTOSAVE_TICKS", 47, "Autosave", false, false),
   BETTER_GRASS("BETTER_GRASS", 48, "Better Grass", false, false),
   ANIMATED_REDSTONE("ANIMATED_REDSTONE", 49, "Redstone Animated", false, false),
   ANIMATED_EXPLOSION("ANIMATED_EXPLOSION", 50, "Explosion Animated", false, false),
   ANIMATED_FLAME("ANIMATED_FLAME", 51, "Flame Animated", false, false),
   ANIMATED_SMOKE("ANIMATED_SMOKE", 52, "Smoke Animated", false, false),
   WEATHER("WEATHER", 53, "Weather", false, false),
   SKY("SKY", 54, "Sky", false, false),
   STARS("STARS", 55, "Stars", false, false),
   SUN_MOON("SUN_MOON", 56, "Sun & Moon", false, false),
   CHUNK_UPDATES("CHUNK_UPDATES", 57, "Chunk Updates per Frame", false, false),
   CHUNK_UPDATES_DYNAMIC("CHUNK_UPDATES_DYNAMIC", 58, "Dynamic Updates", false, false),
   TIME("TIME", 59, "Time", false, false),
   CLEAR_WATER("CLEAR_WATER", 60, "Clear Water", false, false),
   SMOOTH_WORLD("SMOOTH_WORLD", 61, "Smooth World", false, false),
   DEPTH_FOG("DEPTH_FOG", 62, "Depth Fog", false, false),
   VOID_PARTICLES("VOID_PARTICLES", 63, "Void Particles", false, false),
   WATER_PARTICLES("WATER_PARTICLES", 64, "Water Particles", false, false),
   RAIN_SPLASH("RAIN_SPLASH", 65, "Rain Splash", false, false),
   PORTAL_PARTICLES("PORTAL_PARTICLES", 66, "Portal Particles", false, false),
   PROFILER("PROFILER", 67, "Debug Profiler", false, false),
   DRIPPING_WATER_LAVA("DRIPPING_WATER_LAVA", 68, "Dripping Water/Lava", false, false),
   BETTER_SNOW("BETTER_SNOW", 69, "Better Snow", false, false),
   FULLSCREEN_MODE("FULLSCREEN_MODE", 70, "Fullscreen Mode", false, false),
   ANIMATED_TERRAIN("ANIMATED_TERRAIN", 71, "Terrain Animated", false, false),
   ANIMATED_ITEMS("ANIMATED_ITEMS", 72, "Items Animated", false, false),
   SWAMP_COLORS("SWAMP_COLORS", 73, "Swamp Colors", false, false),
   RANDOM_MOBS("RANDOM_MOBS", 74, "Random Mobs", false, false),
   SMOOTH_BIOMES("SMOOTH_BIOMES", 75, "Smooth Biomes", false, false),
   CUSTOM_FONTS("CUSTOM_FONTS", 76, "Custom Fonts", false, false),
   CUSTOM_COLORS("CUSTOM_COLORS", 77, "Custom Colors", false, false),
   SHOW_CAPES("SHOW_CAPES", 78, "Show Capes", false, false),
   CONNECTED_TEXTURES("CONNECTED_TEXTURES", 79, "Connected Textures", false, false),
   AA_LEVEL("AA_LEVEL", 80, "Antialiasing", false, false),
   AF_LEVEL("AF_LEVEL", 81, "Anisotropic Filtering", false, false),
   RENDER_DISTANCE_FINE("RENDER_DISTANCE_FINE", 82, "Render Distance", true, false),
   ANIMATED_TEXTURES("ANIMATED_TEXTURES", 83, "Textures Animated", false, false),
   NATURAL_TEXTURES("NATURAL_TEXTURES", 84, "Natural Textures", false, false),
   CHUNK_LOADING("CHUNK_LOADING", 85, "Chunk Loading", false, false),
   FRAMERATE_LIMIT_FINE("FRAMERATE_LIMIT_FINE", 86, "Performance", true, false),
   HELD_ITEM_TOOLTIPS("HELD_ITEM_TOOLTIPS", 87, "Held Item Tooltips", false, false),
   DROPPED_ITEMS("DROPPED_ITEMS", 88, "Dropped Items", false, false),
   LAZY_CHUNK_LOADING("LAZY_CHUNK_LOADING", 89, "Lazy Chunk Loading", false, false);
   private final boolean field_74385_A;
   private final boolean field_74386_B;
   private final String field_74387_C;
   // $FF: synthetic field
   private static final EnumOptions[] $VALUES = new EnumOptions[]{MUSIC, SOUND, INVERT_MOUSE, SENSITIVITY, FOV, GAMMA, RENDER_DISTANCE, VIEW_BOBBING, ANAGLYPH, ADVANCED_OPENGL, FRAMERATE_LIMIT, DIFFICULTY, GRAPHICS, AMBIENT_OCCLUSION, GUI_SCALE, RENDER_CLOUDS, PARTICLES, CHAT_VISIBILITY, CHAT_COLOR, CHAT_LINKS, CHAT_OPACITY, CHAT_LINKS_PROMPT, USE_SERVER_TEXTURES, SNOOPER_ENABLED, USE_FULLSCREEN, ENABLE_VSYNC, SHOW_CAPE, TOUCHSCREEN, FOG_FANCY, FOG_START, MIPMAP_LEVEL, MIPMAP_TYPE, LOAD_FAR, PRELOADED_CHUNKS, SMOOTH_FPS, CLOUDS, CLOUD_HEIGHT, TREES, GRASS, RAIN, WATER, ANIMATED_WATER, ANIMATED_LAVA, ANIMATED_FIRE, ANIMATED_PORTAL, AO_LEVEL, LAGOMETER, AUTOSAVE_TICKS, BETTER_GRASS, ANIMATED_REDSTONE, ANIMATED_EXPLOSION, ANIMATED_FLAME, ANIMATED_SMOKE, WEATHER, SKY, STARS, SUN_MOON, CHUNK_UPDATES, CHUNK_UPDATES_DYNAMIC, TIME, CLEAR_WATER, SMOOTH_WORLD, DEPTH_FOG, VOID_PARTICLES, WATER_PARTICLES, RAIN_SPLASH, PORTAL_PARTICLES, PROFILER, DRIPPING_WATER_LAVA, BETTER_SNOW, FULLSCREEN_MODE, ANIMATED_TERRAIN, ANIMATED_ITEMS, SWAMP_COLORS, RANDOM_MOBS, SMOOTH_BIOMES, CUSTOM_FONTS, CUSTOM_COLORS, SHOW_CAPES, CONNECTED_TEXTURES, AA_LEVEL, AF_LEVEL, RENDER_DISTANCE_FINE, ANIMATED_TEXTURES, NATURAL_TEXTURES, CHUNK_LOADING, FRAMERATE_LIMIT_FINE, HELD_ITEM_TOOLTIPS, DROPPED_ITEMS, LAZY_CHUNK_LOADING};


   public static EnumOptions func_74379_a(int p_74379_0_) {
      EnumOptions[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnumOptions var4 = var1[var3];
         if(var4.func_74381_c() == p_74379_0_) {
            return var4;
         }
      }

      return null;
   }

   private EnumOptions(String p_i3011_1_, int p_i3011_2_, String p_i3011_3_, boolean p_i3011_4_, boolean p_i3011_5_) {
      this.field_74387_C = p_i3011_3_;
      this.field_74385_A = p_i3011_4_;
      this.field_74386_B = p_i3011_5_;
   }

   public boolean func_74380_a() {
      return this.field_74385_A;
   }

   public boolean func_74382_b() {
      return this.field_74386_B;
   }

   public int func_74381_c() {
      return this.ordinal();
   }

   public String func_74378_d() {
      return this.field_74387_C;
   }

}
