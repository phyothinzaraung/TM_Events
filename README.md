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

Unit tests are written using **JUnit, Mockk, Robolectric** to ensure that the individual components of the app function correctly. These tests verify the logic of the methods, repositories, and other domain-related functionalities.

The following unit tests are implemented in this project:

- **IEventHelperImplTest** – Verifies that event data is correctly fetched from the API and stored in the database while handling different query scenarios.  
- **EventDaoTest** – Tests the database operations, including inserting, retrieving, and searching for events in the local storage.  
- **EventMapperTest** – Ensures that data mapping between database entities, domain models, and UI models is accurate.  
- **EventRemoteMediatorTest** – Validates the pagination logic, ensuring correct behavior when loading data from the API and storing it in the database.  
- **IEventRepositoryImplTest** – Checks if the repository correctly interacts with the API and database while managing data flow between them.  
- **GetEventsUseCaseTest** – Tests the business logic for retrieving events, ensuring correct filtering, transformation, and error handling.  
- **EventsViewModelTest** – Ensures that the ViewModel correctly processes UI interactions, maintains state, and communicates with the use case layer.

These tests help maintain code reliability, detect regressions, and improve the overall robustness of the application.

### UI Testing  

UI tests are written using **Compose Testing APIs** to verify the correctness of the user interface. These tests ensure that the UI behaves as expected and interacts properly with the underlying components.  

The following UI tests are implemented in this project:  

- **EventItemTest** – Ensures that a single event item is displayed correctly with the expected details, including title, date, and location.  
- **EventListTest** – Verifies that the event list displays data correctly, supports pagination, and updates UI elements properly based on user interactions (e.g., scrolling, searching, and empty state handling).  

These UI tests help ensure a smooth user experience by detecting UI regressions and validating component interactions.

## Getting Started

To run this project locally, follow the steps below:

### Prerequisites
- Android Studio (latest version).
- JDK 11 or above.

### Steps  

Follow these steps to set up and run the project on your local machine:  

1. **Clone the repository:**  
    ```bash
    git clone https://github.com/phyothinzaraung/TM_Events.git
    cd TM_Events
    ```  

2. **Open the project in Android Studio:**  
   - Launch **Android Studio**.  
   - Click on **"Open"** and select the project directory.  
   - Wait for Gradle to sync the dependencies.  

3. **Run the app:**  
   - Connect an **Android device** or use an **emulator**.  
   - Click on the **Run** ▶️ button in Android Studio.  

4. **Run tests (Unit & UI Tests):**  
    - To run **unit tests**, execute the following command in the terminal:  
      ```bash
      ./gradlew testDebugUnitTest
      ```  
    - To run **UI tests**, execute:  
      ```bash
      ./gradlew connectedAndroidTest
      ```  

Now, the project should be up and running! 🚀  

