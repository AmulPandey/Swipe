# Swipe Android App

**Swipe** is an Android app designed for managing products with a modern and user-friendly interface. It leverages advanced features like local database storage, network requests, and image uploads. The app uses **Kotlin** for development, **Koin** for dependency injection, **Retrofit** for network communication, and **Room** for local data persistence.

## ScreenShot
![WhatsApp Image](https://github.com/AmulPandey/Swipe/blob/main/WhatsApp%20Image%202025-01-31%20at%204.39.22%20AM.jpeg)


## Features

- **Product Management**: Add, fetch, and manage products with details like name, type, price, tax, and image.
- **Networking**: Seamless integration with a remote API for fetching and adding products.
- **Local Database**: Use of **Room** for storing product data locally.
- **Image Upload**: Ability to upload images associated with products.
- **Dependency Injection**: Powered by **Koin** for cleaner, maintainable code.
- **Error Handling**: Robust error handling to manage API and database errors.

## Tech Stack

- **Kotlin**: Primary programming language used for development.
- **Room**: Local database to store product details.
- **Retrofit**: For network communication (API integration).
- **Koin**: Dependency Injection for better code management and testing.
- **OkHttp**: Used by Retrofit for network requests.
- **Gson**: For parsing JSON data from the API.




## Architecture

This project follows **MVVM (Model-View-ViewModel)** architecture to separate concerns and improve the maintainability of the code. It uses the following layers:

## How It Works
 **Dependency Injection with Koin**
Koin is used to inject the required dependencies (such as the repository, view model, API services) into different parts of the app, making the codebase more modular and testable.

**Networking with Retrofit**
The app fetches product data from a remote server using Retrofit and displays it within the app. It also allows adding new products with associated details and images.

**Local Storage with Room**
Room is used for local data persistence to store products when there’s no network connectivity. This ensures a smooth offline experience.

**Image Upload**
Product images are uploaded using Retrofit's MultipartBody. The image is stored in the app's cache directory before being uploaded to the server.

## Installation

### Requirements

- **Android Studio** (latest stable version)
- **Kotlin 1.x**
- **Gradle 6.x**


