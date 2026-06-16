# Glassefc SDK - Consumer ProGuard Rules

# Keep the public API entry point
-keep class com.glassefc.sdk.Glassefc { *; }

# Keep internal classes for state management and reflection safety
-keep class com.glassefc.sdk.internal.** { *; }
