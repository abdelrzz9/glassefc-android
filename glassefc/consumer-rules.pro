# GlassEffectConfig fields must not be stripped or renamed.
# Clients could otherwise bypass the lock by reflection.
-keep class com.glassefc.GlassEffectConfig { *; }

# Keep private unlock internals so unlock(key) works.
-keepclassmembers class com.glassefc.GlassEffectConfig {
    private java.lang.String UNLOCK_KEY;
}

# Keep all public API entry points.
-keep class com.glassefc.GlassEffect { *; }
-keep class com.glassefc.GlassEffectLayout { *; }
