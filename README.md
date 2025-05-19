
# Venkata Walmart Coding Assessment

An Android application built as part of a coding assessment, demonstrating Clean Architecture, MVVM pattern, and modern Android development practices using Kotlin, Coroutines, Flow, and Retrofit.

---

## Overview

This app fetches a list of countries from a public API and displays them in a RecyclerView. It's built with **separation of concerns** in mind and follows the principles of Clean Architecture.

---

## Features

- Fetch countries from remote API using Retrofit
- Show country name, capital, region, and ISO code
- MVVM architecture + Clean Architecture layering
- Handles loading, success, and error states using sealed classes
- Unit tested repository, ViewModel, and use cases

---

## Screenshots

**Please click the images below to enlarge**

<img src="https://github.com/user-attachments/assets/57ccfc1a-5c49-454b-bff4-c7f4fa0410c9" height="600" width="300" hspace="20">

---

## Built With

- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Flow](https://developer.android.com/kotlin/flow)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [ViewModel & LiveData](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
- [Material Components](https://material.io/develop/android)

---

## Clean Architecture

This app uses **Clean Architecture** with well-defined layers.

```
Presentation Layer
    → UI (Activity, Adapter, ViewModel)
    → Communicates via ViewModel & UI State

Domain Layer
    → Contains UseCases and interfaces
    → No dependency on data or presentation layers

Data Layer
    → Retrofit API, DTOs, Repository implementations
    → Maps data models to domain models
```

### Architecture Diagram

![Clean Architecture Diagram](https://user-images.githubusercontent.com/51234843/190183169-fb67bce4-9dc7-4de8-819b-3ee23d298e8a.png)

---

## Package Structure

```
com.walmart.venkata
|
├── data
│   ├── common              # App-wide utils ApiResponseState, Network Constants
│   ├── dto                 # API response models
│   ├── mapper              # Extension functions for mapping DTOs
│   ├── remote              # Retrofit API interface
│   └── repository          # Repository implementation
|
├── domain
│   ├── model               # Core business models (Country)
│   ├── repository          # Abstract repository interface
│   └── use_case            # Business use case for fetching countries
|
├── presentation
│   ├── view
│   │   ├── adapter         # RecyclerView adapter
│   │   └── viewholder      # ViewHolders 
│   ├── viewmodel           # ViewModels and ViewModelFactory
│   └── MainActivity        # App launcher activity
```

---

## Testing

- Unit tests included for:
  - Repository 
  - ViewModel behavior
  - UseCase flow validation

```bash
./gradlew test
```

---

## How to Run

1. Clone the repo:
   ```bash
   git clone https://github.com/Venkata-Raja-Vara-Prasad-Sanayila/VenkataWalmartCodingAssessment.git
   ```

2. Open the project in **Android Studio**

3. Sync Gradle and Run on an emulator or real device

---

## Future Improvements

- Add country detail screen with flag, currency, and language
- Add swipe-to-refresh
- Migrate UI to Jetpack Compose
- Integrate Room for offline caching

---

## Author

**Venkata Raja Sanayila**
venkataraja.sanayila4@gmail.com  
[LinkedIn](https://www.linkedin.com/in/venkatarajasanayila)

---

<p>If this helped you or inspired you, consider giving it a ⭐. It motivates me to contribute more open-source projects!</p>
