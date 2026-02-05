# FooBall App

Android (Jetpack Compose) app for football match predictions, built with MVVM + Koin DI.

## Tech
- Kotlin, Jetpack Compose (Material 3)
- MVVM (ViewModels expose `StateFlow`)
- Koin (DI)
- Coroutines/Flow
- Navigation Compose

## Module / package layout (in `app/src/main/java/com/zonkesoft/fooball`)
- `ui/` — Compose screens, navigation, UI components
- `ui/viewmodel/` — ViewModels
- `core/` — utilities/services (e.g., connectivity observer)
- `domain/` — domain models (e.g., `NetworkState`)
- `di/` — Koin modules

## Run
Open in Android Studio and run the `app` configuration.

## CI
GitHub Actions runs ktlint + unit tests + debug build on PRs to `dev` and `prod`.

## Architecture (Network / Offline)

```text
UI (Compose)
  ├─ Screens use NetworkAwareContent { onlineContent / offlineContent }
  └─ OfflineScreen observes OfflineViewModel.networkState
            │
            ▼
ViewModel (MVVM)
  └─ OfflineViewModel collects NetworkConnectivityObserver.observe()
            │
            ▼
Core service
  └─ NetworkConnectivityObserver (Flow<NetworkState> + isConnected())
            │
            ▼
Domain
  └─ NetworkState = Connected | Disconnected
```

Notes:
- OfflineScreen auto-navigates back to Home when `Connected` is detected.
- Retry button is only shown when `Disconnected`.
