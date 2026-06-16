# Glassefc Android SDK

A production-ready Android SDK for freelance payment protection. Lock app access until payment is confirmed — transparent, agreed upfront in the contract.

```
implementation("com.github.your-username:glassefc-android:1.0.0")
```

---

## Quick Start

### 1. Initialize the SDK

In your `Application` or `Activity`:

```kotlin
import com.glassefc.sdk.Glassefc

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glassefc.init(
            context = applicationContext,
            licenseKey = "YOUR_LICENSE_KEY_HERE",
            freelancer = "Your Name",
            project = "Client Project",
            amount = "500 USD",
            deadline = "2025-01-01"       // yyyy-MM-dd format
        )

        setContent {
            Glassefc {
                // Your app content here
                AppContent()
            }
        }
    }
}
```

### 2. Wrap Your App Content

Replace your content root with `Glassefc { }`:

```kotlin
// Before
setContent { AppContent() }

// After
setContent {
    Glassefc {
        AppContent()
    }
}
```

The SDK automatically:
- **Shows a lock overlay** when the app is not activated
- **Renders your content** when activated
- **Displays overdue warnings** past the deadline

---

## Activation

### Auto-activation (default)

On first launch, the SDK auto-activates using the embedded license key. The activation is stored persistently using **EncryptedSharedPreferences**.

### Runtime activation

```kotlin
Glassefc.activate("ACTIVATION_CODE")
```

### Check status

```kotlin
if (Glassefc.isActivated()) {
    // App is unlocked
}

if (Glassefc.isLicensed()) {
    // License key is valid and matches activation
}
```

---

## SDK Architecture

```
com.glassefc.sdk/
├── Glassefc.kt                  # Public API (single entry point)
├── internal/
│   ├── GlassefcConfig.kt        # Immutable configuration
│   ├── GlassEffectState.kt      # State management
│   ├── LicenseManager.kt        # License validation & activation
│   ├── SecureStorage.kt         # EncryptedSharedPreferences storage
│   ├── AntiTamper.kt            # Debug detection & tamper checks
│   └── ui/
│       ├── GlassEffectComposable.kt   # Compose lock overlay
│       ├── GlassEffectDefaults.kt     # UI constants
│       └── GlassEffectLayout.kt       # View-based overlay
```

---

## API Reference

| Method | Description |
|--------|-------------|
| `Glassefc.init(...)` | Initialize the SDK with your license key and project details. |
| `Glassefc.activate(key)` | Activate the SDK with a license key at runtime. |
| `Glassefc.isActivated()` | Returns `true` if the SDK is currently activated. |
| `Glassefc.isLicensed()` | Returns `true` if the current license key matches the stored activation. |
| `Glassefc { ... }` | Composable wrapper — shows content if activated, lock overlay otherwise. |

### init() Parameters

| Parameter | Required | Description |
|-----------|----------|-------------|
| `context` | ✓ | Application or Activity context |
| `licenseKey` | ✓ | Your secret license key (min 16 chars, alphanumeric + hyphens) |
| `freelancer` | ✓ | Your name or business name |
| `project` | ✓ | Project name displayed on the lock screen |
| `amount` | ✓ | Payment amount displayed on the lock screen |
| `deadline` | ✓ | Payment deadline in `yyyy-MM-dd` format |
| `enableAntiTamper` | | Enable debug build detection (default: `true`) |
| `licenseServerUrl` | | Optional backend URL for remote license validation |

---

## Requirements

- Android API 26+
- Kotlin 1.9+
- Jetpack Compose BOM 2024+ (provided by host app)
- No backend required (optional for remote validation)

---

## Adding to Your Project

### JitPack

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

// app/build.gradle.kts
dependencies {
    implementation("com.github.your-username:glassefc-android:1.0.0")
}
```

### Local Module

```kotlin
// settings.gradle.kts
include(":glassefc")

// app/build.gradle.kts
dependencies {
    implementation(project(":glassefc"))
}
```

---

## Security

| Feature | Description |
|---------|-------------|
| **Encrypted storage** | Activation state stored in EncryptedSharedPreferences (AES-256) |
| **License hashing** | License keys stored as SHA-256 hashes, never in plaintext |
| **Debug detection** | Optionally locks the app when running on debug builds |
| **ProGuard ready** | Consumer ProGuard rules included |
| **No network required** | Works fully offline |

---

## License

This SDK is licensed under the MIT License.
