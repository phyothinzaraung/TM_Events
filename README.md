# Android Event App with Clean Architecture and Jetpack Compose

This is an Android application built using **Clean Architecture** and **Jetpack Compose** for UI development. The app retrieves event data from an API and stores it in a local database. Pagination is implemented with **RemoteMediator** to handle large datasets efficiently.

## Table of Contents
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Architecture](#architecture)
- [Pagination](#pagination)
- [Dependency Injection](#dependency-injection)
- [Testing](#testing)
  - [Unit Testing](#unit-testing)
  - [UI Testing](#ui-testing)
- [Screenshots](#screenshots)
- [Getting Started](#getting-started)
- [License](#license)

## Technologies Used

- **Kotlin**: The primary language used for Android development.
- **Jetpack Compose**: For building UIs in a declarative manner.
- **Clean Architecture**: A modular architecture that separates the code into layers for maintainability.
- **Paging 3**: For handling large datasets with efficient memory and network usage.
- **Room Database**: For storing event data locally.
- **Dagger Hilt**: For dependency injection to manage the app’s dependencies.
- **Retrofit**: For making API calls to retrieve event data.
- **Flow**: For managing asynchronous data streams.
- **RemoteMediator**: For handling pagination and syncing data between the API and the local database.
- **JUnit**: For unit testing.
- **Espresso**: For UI testing.

## Features

- Fetches event data from a remote API.
- Saves event data in a local database using Room.
- Supports pagination for loading large datasets.
- Allows searching events based on a query.
- Built using Jetpack Compose for modern UI design.
- Dependency injection with Dagger Hilt for clean and efficient code.
- Includes unit tests and UI tests to ensure functionality.

## Architecture

This project follows **Clean Architecture** principles, which ensures separation of concerns and easy maintainability. The architecture layers are:

- **Domain Layer**: Contains the core business logic and entities (e.g., `Event`).
- **Data Layer**: Handles data sources, repositories, and mappers to transform data between the domain and data models.
- **Presentation Layer**: Contains UI logic, built with Jetpack Compose.

## Pagination

The app uses **Paging 3** to handle large datasets with efficiency. **RemoteMediator** is used to fetch data from the API and store it in the local database. Pagination is triggered by the user’s interaction with the UI, and new data is fetched as needed.

## Dependency Injection

**Dagger Hilt** is used for dependency injection in this project. It helps to manage and provide the app's dependencies in a clean and efficient manner. With Hilt, we avoid manual object creation and manage dependencies more easily, promoting better testability and scalability.

### How Dagger Hilt is used:
- Injecting dependencies into classes like `IEventHelperImpl`, `EventDao`, `EventService`, and others.
- Managing and providing network utilities, database access, and service dependencies.

## Testing

### Unit Testing

Unit tests are written using **JUnit** to ensure that the individual components of the app are functioning correctly. These tests are designed to verify the logic of the methods, repositories, and other domain-related functionalities.

- Unit tests cover core functionalities such as event retrieval, data transformation, and network interaction.

### UI Testing

UI tests are written using **Espresso** to verify the correctness of the user interface. These tests ensure that the UI behaves as expected and interacts with the underlying components properly.

- UI tests verify that data is displayed correctly, pagination works, and search functionality is functional.

## Screenshots

Here are some screenshots of the app:

<img src="https://github.com/user-attachments/assets/25ec9a83-0f07-4c98-b7a6-f8b94c77b2e7" width="300"/>

_Above: Screenshot showing the event listing UI with pagination._

_Above: Screenshot showing the event listing UI with pagination._

## Getting Started

To run this project locally, follow the steps below:

### Prerequisites
- Android Studio (latest version).
- JDK 11 or above.

### Steps
1. Clone the repository:
    ```bash
    git clone [<your-repository-url>](https://github.com/phyothinzaraung/TM_Events.git)
    cd <your-project-directory>
    ```

2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Build and run the app on an emulator or physical device.
