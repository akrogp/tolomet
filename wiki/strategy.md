Optimized tool selection

Updated todo list

Explore codebase to understand architecture and tech stack

Read [](file:///Users/marcoq/Developer/tolomet/Project/tolometCore/src/main/java)

Read [](file:///Users/marcoq/Developer/tolomet/Project/tolometGui/src/main/java)

Read [](file:///Users/marcoq/Developer/tolomet/Project/build.gradle)

Read [](file:///Users/marcoq/Developer/tolomet/Project/tolometCore/build.gradle)

Read [](file:///Users/marcoq/Developer/tolomet/Project/tolometGui/build.gradle)

Searched for files matching `**/tolometCore/**/*.kt`, no matches

Searched for files matching `**/tolometGui/**/*.kt`, no matches

Read [](file:///Users/marcoq/Developer/tolomet/Project/tolometGui/src/main/res/layout/)

Searched for regex `HttpURLConnection|OkHttpClient|Retrofit|new URL\(`, 15 results

Searched for files matching `**/Project/**/*.kt`, no matches

Read [](file:///Users/marcoq/Developer/tolomet/Project/tolometCore/src/main/java/com/akrog/tolomet/)

Read [](file:///Users/marcoq/Developer/tolomet/Project/tolometCore/src/main/java/com/akrog/tolomet)

Read [](file:///Users/marcoq/Developer/tolomet/Project/tolometCore/src/main/java/com/akrog/tolomet/providers)

Read [](file:///Users/marcoq/Developer/tolomet/Project/tolometCore/src/main/java/com/akrog/tolomet/io/Downloader.java#1-1), lines 1 to 100

Read [](file:///Users/marcoq/Developer/tolomet/TO-DO.md)

Porting the Tolomet application to iOS is a significant undertaking, but the current project structure provides a solid foundation for a modern, efficient approach.

### Recommended Strategy: Kotlin Multiplatform (KMP) + SwiftUI

Since Tolomet has a rich set of over 30 weather data providers implemented in `tolometCore`, the most efficient path is to reuse this logic rather than rewriting it in Swift. **Kotlin Multiplatform** allows you to share the core business logic while building a 100% native UI for iOS using **SwiftUI**.

---

### 1. Phase 1: Core Migration (Shared Logic)
The first goal is to transform the `tolometCore` Java module into a shared Kotlin module.

*   **Java to Kotlin Transition**: Convert the existing Java code in `tolometCore` to Kotlin.
*   **Decouple Frameworks**: Remove Android-specific dependencies from `tolometCore`.
*   **Networking**: Replace the custom `HttpURLConnection` in Downloader.java with **Ktor**, which works seamlessly on both Android and iOS.
*   **Serialization**: Switch to `kotlinx.serialization` for parsing the various JSON and XML formats used by weather providers.

### 2. Phase 2: Establishing the KMP Library
Restructure the Gradle project to produce an iOS-compatible framework.

*   **Source Sets**: Define `commonMain` (shared logic), `androidMain` (Android specific), and `iosMain` (iOS specific).
*   **Build Artifacts**: Configure the build to generate an **XCFramework** that can be easily imported into Xcode.
*   **Storage**: If you want to share the database logic, consider migrating from Room to **SQLDelight**, which supports both SQLite on Android and CoreData/SQLite on iOS.

### 3. Phase 3: Native iOS UI Development
Build a native iOS experience that follows Apple's Human Interface Guidelines.

*   **SwiftUI**: Use SwiftUI for all views (Station lists, Detail views, and Graphs).
*   **Maps**: Integrate **MapKit** (Apple Maps) or continue using **Google Maps for iOS** to maintain parity with the Android version.
*   **Lifecycle**: Implement iOS-specific background fetching using the `BackgroundTasks` framework to replace the functionality of Android's `WorkManager`.

### 4. Phase 4: Feature Parity & Integration
*   **Firebase**: Integrate the Firebase iOS SDK for Analytics and Realtime Database.
*   **Widgets**: Port the Android widget functionality to iOS using **WidgetKit**.
*   **Sensors/Location**: Implement iOS-specific location services (`CoreLocation`).

---

### Comparison of Approaches

| Feature | KMP + SwiftUI (Recommended) | Full Swift Rewrite | Flutter / React Native |
| :--- | :--- | :--- | :--- |
| **Logic Reuse** | ~80% (Providers, Models) | 0% | 0% |
| **Performance** | Native | Native | High |
| **UI Quality** | Native Apple look & feel | Native Apple look & feel | Custom / Non-native |
| **Effort** | Medium (migration focus) | Very High | High (total rewrite) |

### Immediate Next Steps
1.  **Proof of Concept**: Move one simple provider (e.g., `AemetProvider`) to a new Kotlin module and verify it can be called from both a simple Android app and a simple Swift app.
2.  **Dependency Audit**: Identify any JAR-only dependencies in `tolometCore/libs` that might need multiplatform alternatives.

Created 5 todos