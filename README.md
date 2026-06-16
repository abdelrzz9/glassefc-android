# Glassefc Android

A Kotlin Android library that protects freelance work by locking the app until payment is confirmed. Transparent, agreed upfront in the contract — not a hack, a professional payment gate.

This is the Android/Kotlin equivalent of the [Glassefc Swift Package](https://github.com/wailbentafat/glassefc).

---

## How it works

1. You fork this repo and fill in your info in the config
2. You add the library to the client's project
3. Client's code shows only `GlassEffect { }` — nothing revealing
4. App is locked on launch until you flip `isUnlocked = true` after payment

---

## Setup (after forking)

Edit one file — `glassefc/src/main/kotlin/com/glassefc/GlassEffectConfig.kt`:

```kotlin
object GlassEffectConfig {
    var isUnlocked = false          // → true after payment
    var freelancer = "Your Name"
    var project    = "Project Name"
    var amount     = "500 USD"
    var deadline   = "2025-01-01"   // ISO format: yyyy-MM-dd
}
```

---

## Add to client project

### Option A — Local module

Copy the `glassefc/` directory into the client's project. Add to `settings.gradle.kts`:

```kotlin
include(":glassefc")
```

Add to the app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":glassefc"))
}
```

### Option B — JitPack (recommended for remote hosting)

1. Push your fork to GitHub
2. [Create a JitPack release](https://jitpack.io/#your-username/glassefc-android)
3. Add JitPack repository and dependency:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

// app/build.gradle.kts
dependencies {
    implementation("com.github.your-username:glassefc-android:Tag")
}
```

---

## Integration (one line)

### Jetpack Compose

```kotlin
import com.glassefc.GlassEffect

@Composable
fun MyApp() {
    GlassEffect {
        // your composable content here
    }
}
```

In your `MainActivity`:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlassEffect {
                Greeting()
            }
        }
    }
}
```

### XML / View-based apps

```kotlin
import com.glassefc.GlassEffectLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlassEffectLayout.wrap(this)  // overlay lock screen
    }
}
```

---

## Unlocking after payment

1. Go to your fork on GitHub
2. Open `GlassEffectConfig.kt`
3. Change `isUnlocked = false` → `isUnlocked = true`
4. Push
5. For local module: client pulls the latest code and rebuilds
6. For JitPack: create a new release; client updates the version tag

App unlocks immediately.

---

## Deadline handling

- Before deadline → full-screen black overlay, lock icon, days remaining shown
- After deadline → full-screen dark red overlay, warning icon, "Payment Overdue" message — automatic, no code change needed

---

## Requirements

- Android API 26+
- Kotlin 1.9+
- Jetpack Compose BOM 2024+ (optional — only needed if using Compose integration)
- No external dependencies beyond AndroidX

---

## Project structure

```
glassefc/                     # Library module
├── build.gradle.kts
└── src/main/kotlin/com/glassefc/
    ├── GlassEffectConfig.kt    # Config — edit this
    ├── GlassEffect.kt          # Compose integration
    └── GlassEffectLayout.kt    # XML / View integration

app/                          # Sample app
├── build.gradle.kts
└── src/main/kotlin/com/glassefc/sample/
    └── MainActivity.kt
```
