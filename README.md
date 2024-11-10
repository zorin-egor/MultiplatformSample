# Multiplatform sample
## <b>MultiplatformSample</b> - sample of kotlin/compose multiplatform multi modals app developed with conventions plugins.
Targeting Android, iOS, Web, Desktop.

## Using libraries

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

## Notes
1) Js build failed yarn ./gradlew kotlinUpgradeYarnLock
https://youtrack.jetbrains.com/issue/KT-65870/KJS-Gradle-kotlinUpgradePackageLock-fails-making-Yarn-unusable
2) Run desktop ./graldew :composeApp:run
3) Run js ./gradlew jsBrowserRun
4) https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform


## Screenshots
<p align="center">
<img src="https://github.com/user-attachments/assets/30ce1f08-b9ae-4f2a-8baf-27c1549b7059"/>
<img src="https://github.com/user-attachments/assets/8d96b474-3677-4796-835a-e5b609b51e53"/>
<img src="https://github.com/user-attachments/assets/8010a8e3-987d-4e15-a5c9-f5d7a0e526bc"/>
</p>
