# Aktiia Foursquare App

## Overview

This Android app was developed as a test assignment for a Senior Android Developer position at Aktiia.  
It allows users to search for venues using the Foursquare API, view details, and save favorites — all with offline support.

---

## Features

### Core Functionality
- Search venues by keyword and current location
- View detailed information about venues
- Save venues as favorites and access them anytime
- Offline support with cached search results
- Rationale dialog and permission handling for location access

---

## Architecture & Tech Stack

### Architecture
- Multi-module structure
- MVVM pattern
- Clean Architecture (Data, Domain, Presentation)

### Tech Stack
- Jetpack Compose for all UI components
- Kotlin Coroutines and Flow for reactive programming
- Retrofit + OkHttp for API communication
- Room for local storage (caching & favorites)
- Koin for dependency injection

---

## Testing

### Unit Tests
- Database layer tested with Room
- Search Places feature covered with:
  - `mockk` for mocking dependencies
  - `turbine` for testing Flows

### UI Tests
- Isolated UI tests for Search screen and components

---

## Future Enhancements

- Separate screen for all venues vs search results
- Full-screen venue photo preview
- Sorting options for venues (by distance, rating, etc.)

