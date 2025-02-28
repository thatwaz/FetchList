# 📲 FetchList – Fetch Rewards Android Coding Exercise

## 📌 Overview

FetchList is a **native Android app** built using **Kotlin and Jetpack Compose**. It retrieves data from the Fetch Rewards API and displays it in an **organized, easy-to-read list**. The app follows a **lightweight MVVM architecture** with clean code practices and includes error handling and unit tests.

## 🚀 Features

✔ **Fetches data from the API**  
✔ **Filters out blank/null names**  
✔ **Groups items by `listId`**  
✔ **Sorts first by `listId`, then numerically by name**  
✔ **Expandable lists for better readability**  
✔ **Network error handling with retry option**  
✔ **Unit tests for repository filtering and sorting logic**  

## 📸 Screenshot
![Image](https://github.com/user-attachments/assets/14efd0c0-c2e3-4d77-82e9-5a486ed3d522)


## 🛠️ Tech Stack

- **Kotlin** – Modern, concise language for Android
- **Jetpack Compose** – Declarative UI framework
- **Retrofit** – HTTP client for API calls
- **Lightweight MVVM Architecture** – Clean separation of concerns
- **Coroutines & Flow** – Asynchronous data handling
- **JUnit** – Unit testing

## 📦 Project Structure

```
📂 com.thatwaz.fetchlist
 ├── 📂 data        # Data layer (models, repository, API service)
 ├── 📂 ui          # UI components and screens
 ├── 📂 viewmodel   # Lightweight ViewModel (business logic)
 ├── 📂 theme       # App theme (colors, typography, shapes)
 ├── MainActivity.kt # Entry point of the app
```

## 🔧 Setup & Run Instructions

1. Clone the repository:
   ```sh
   git clone https://github.com/YOUR_GITHUB_USERNAME/FetchList.git
   ```
2. Open the project in **Android Studio** (latest stable version).
3. Ensure your system has the latest **Android SDK & Build Tools** installed.
4. Build and run the app on an **emulator or physical device**.

## ✅ Testing

To run unit tests, execute:

```sh
./gradlew test
```

Tests are located in:

```
📂 src/test/java/com.thatwaz.fetchlist.data
    ├── FetchRepositoryTest.kt  # Unit test for repository sorting & filtering
```

## ❌ Error Handling

- If the network request fails, an **error screen** is displayed with a **retry button**.
- **Null or blank names are automatically removed** to ensure data cleanliness.

## 🎯 Submission Requirements Met

✔ **Retrieves data from Fetch API**  
✔ **Filters out blank/null names**  
✔ **Groups items by `listId`**  
✔ **Sorts first by `listId`, then numerically by name**  
✔ **Easy-to-read UI with expandable lists**  
✔ **Public GitHub repository for submission**  

## 📩 Submission

This project is submitted for Fetch Rewards' **Android Engineer Apprentice** take-home assessment.



