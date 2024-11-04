In progress.

<b>MultiplatformSample</b> - sample of kotlin/compose multiplatform multi modals app developed with conventions plugins.
Targeting Android, iOS, Web, Desktop.

``` text
├── composeApp..... Entry point to the application
│   └── NavHost.... App navigation coordination
├── core......... Independent project/component logic
│   ├── common.......... Utilities, extension functions, helpers
│   ├── network.......... Interaction with the network
│   ├── datastore.......... Logic for saving primitive data and objectsв
│   ├── data........ Repositories. Database.
│   ├── domain...... Business logic
│   ├── model....... Business logic models
│   ├── ui....... Basic UI components, themes, color schemes. Comprehensive UI components for a specific presentation
├── features....... All screens are divided into module-features
│   ├── users.......... Feature list of users
│   ├── user_details.......... Feature details about the user
│   ├── repositories.......... Feature list of repositories
│   └── repository_details.....Feature details about the repository
└──gradle-plugins.......... Convention gradle plugin for forwarding dependencies between modules
```

My notes
1) Js build failed yarn ./gradlew kotlinUpgradeYarnLock
https://youtrack.jetbrains.com/issue/KT-65870/KJS-Gradle-kotlinUpgradePackageLock-fails-making-Yarn-unusable
2) Run desktop ./graldew :composeApp:run
3) Run js ./gradlew jsBrowserRun
4) https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform