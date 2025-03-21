# Android Event App with Clean Architecture and Jetpack Compose

This is an Android application showing a list of events including concerts, sports, musics and so on. It was built using **Clean Architecture** and **Jetpack Compose** for UI development. The app retrieves event data from an API and stores it in a local database. Pagination is implemented with **RemoteMediator** to handle large datasets efficiently. The application can also support search functioality by event name, city or venue.

## Table of Contents
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Screenshots](#screenshots)
- [Architecture](#architecture)
- [Testing](#testing)
  - [Unit Testing](#unit-testing)
  - [UI Testing](#ui-testing)
- [Getting Started](#getting-started)

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
- **JUnit, Mockk, robolectric**: For unit testing.
- **Compose Tesing API**: For UI testing.

## Features

- Fetches event data from a remote API.
- Saves event data in a local database using Room.
- Supports pagination for loading large datasets.
- Allows searching events based on a event name, city or venue.
- Built using Jetpack Compose for modern UI design.
- Dependency injection with Dagger Hilt for clean and efficient code.
- Includes unit tests and UI tests to ensure functionality.

## Screenshots

Here are some screenshots of the app:

### Event List Screenshots

<div>
  <img src="https://github.com/user-attachments/assets/c8e189e0-df0d-49b7-baba-39e302922890" alt="Event_List_Online", width="20%">
  <img src="https://github.com/user-attachments/assets/98b313f5-6db3-4a13-a2c5-3ed9c2658dc0" alt="Event_List_Offline", width="20%">
  <img src="https://github.com/user-attachments/assets/ab7514e6-80cc-43a5-9388-c2bf767b54a6" alt="Search_Results", width="20%">
  <img src="https://github.com/user-attachments/assets/6fc14862-7245-4e1d-966b-943e630fc108" alt="Empty_View", width="20%">
</div>

### Image Descriptions

1. **Event List Data when Online**  
   This screenshot shows the event list data when the app is online and successfully fetching data from the server.

2. **Event List Data when Offline**  
   This screenshot illustrates the event list data available when the app is offline, using previously cached data.

3. **Event List Search Result**  
   This screenshot shows the result after searching for a specific event in the list.

4. **Event List Empty View**  
   This screenshot demonstrates the empty view when there are no events available to display.

## Architecture

This project follows **Clean Architecture** principles, which ensures separation of concerns and easy maintainability. The architecture layers are:

- **Domain Layer**: Contains the core business logic and entities (e.g., `Event`).
- **Data Layer**: Handles data sources, repositories, and mappers to transform data between the domain and data models.
- **Presentation Layer**: Contains UI logic, built with Jetpack Compose.

## Testing

### Unit Testing

Unit tests are written using **JUnit** to ensure that the individual components of the app are functioning correctly. These tests are designed to verify the logic of the methods, repositories, and other domain-related functionalities.

- Unit tests cover core functionalities such as event retrieval, data transformation, and network interaction.

### UI Testing

UI tests are written using **Compose Testing APIs** to verify the correctness of the user interface. These tests ensure that the UI behaves as expected and interacts with the underlying components properly.

- UI tests verify that data is displayed correctly, pagination works, and search functionality is functional.

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
