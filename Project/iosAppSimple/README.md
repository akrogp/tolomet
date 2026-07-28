# Tolomet iOS App

This is a minimal SwiftUI app that consumes the Kotlin Multiplatform shared module (`tolometShared`) via `TolometShared.xcframework`.

## 1. Build the XCFramework

From the `Project` directory:

```bash
./gradlew :tolometShared:assembleTolometSharedReleaseXCFramework
```

The framework is generated at:

- `tolometShared/build/XCFrameworks/release/TolometShared.xcframework`

## 2. Generate the Xcode project

This app uses XcodeGen (`project.yml`).

Install once if needed:

```bash
brew install xcodegen
```

Generate project from `iosAppSimple` directory:

```bash
xcodegen generate
```

## 3. Open and run

```bash
open Tolomet.xcodeproj
```

Select an iOS simulator and run.

## Notes

- The app uses live calls through `EuskalmetProvider` from the shared module.
- If the provider endpoint is temporarily unavailable, the UI will show the failure message instead of crashing.
