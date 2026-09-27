# Booking Catalogue

A small Kotlin/JVM console application that simulates a simple accommodation booking system.
A user with a balance browses a catalogue of establishments (hotels and AirBNB apartments),
filters them by price and size, and books them asynchronously. Each booking either succeeds
(the balance is charged and a `Booking` record is stored) or fails (insufficient funds or unknown establishment).

No Android components are used — this is a plain Kotlin/JVM project built with Gradle.

## How to run

Requirements: JDK 8+ (Gradle will provision a toolchain automatically via the Foojay resolver if needed).

From the project root:

```bash
# Windows
gradlew.bat run

# Linux / macOS
./gradlew run
```

Alternatively, open the project in IntelliJ IDEA and run the `main()` function in `src/main/kotlin/Main.kt`.

Expected output (dates and booking IDs vary):

```
Total price of all establishments:225
1 Mini Hotel
2 Cozy Apartment
Booking successful
Booking successful
Admin booked Mini Hotel for for the date 2026-09-27
Admin booked Cozy Apartment for for the date 2026-09-27
Larger establishments: Grand Hotel with area greater than 70
```

## Project structure

```
src/main/kotlin/
├── Main.kt                        # Entry point: builds the catalogue and runs the demo scenario
├── User.kt                        # User with a mutable balance
├── Bookable.kt                    # Interface implemented by every establishment
├── Establishment.kt               # Abstract base class for all establishments
├── establishments/
│   ├── Hotel.kt                   # Concrete establishment
│   └── AirBNB.kt                  # Concrete establishment with an extra `owner` field
├── Booking.kt                     # Data class describing a completed booking
├── bookingResult/
│   └── BookingResult.kt           # Sealed class + enum describing the booking outcome
└── Catalogue.kt                   # Business logic: search, filtering, pricing, bookings
```

## Where the requirements are demonstrated

| Requirement | Where |
|---|---|
| Variables, data types, conditions, loops | `Main.kt` (`val` declarations, `for` loop over `largeEstablishments`), `Catalogue.kt` (`if/else` in `makeBooking`, `for` loops in `showCatalogue`, `printBookingsForUser`, `getEstablishmentByName`), `User.kt` (`var balance`) |
| **List** | `establishments: List<Establishment>` in `Main.kt` / `Catalogue.kt` |
| **Set** | `amenities: Set<String>` on every `Establishment` (e.g. `setOf("Wifi", "Pool", "Gym")`) |
| **Map** | `bookings: MutableMap<Int, Booking>` in `Catalogue.kt`, exposed via `getBookings()` |
| Collection `map` | `Catalogue.getTotalCatalougePrices()`, `Catalogue.printBookingsForUser()`, `Main.kt` (`largeEstablishments.map { it.name }`) |
| Collection `filter` | `Catalogue.showEstablishmentsCheaperThan()`, `Catalogue.search()` |
| Collection `reduce` | `Catalogue.getTotalCatalougePrices()` — sums all prices with `reduce` |
| Functions | Throughout, e.g. `User.withdrawBalance()`, `Catalogue.getEstablishmentByName()` |
| Higher-order functions & lambdas | `Catalogue.search(condition: (Establishment) -> Boolean)` called from `Main.kt` as `catalogue.search { it.livableArea > 70 }`; lambdas passed to `map`/`filter`/`reduce` |
| Classes and objects | `User`, `Catalogue`, `Hotel`, `AirBNB` and their instances created in `Main.kt` |
| Inheritance | `Hotel` and `AirBNB` extend the abstract class `Establishment` |
| Interfaces and polymorphism | `Bookable` interface implemented by `Establishment`; `Catalogue.makeBooking()` calls `establishment.book()` on the base type, dispatching to `Hotel.book()` / `AirBNB.book()` |
| Data class | `Booking` (`Booking.kt`) |
| Sealed class | `BookingResult` with subclasses `BookingSuccess` and `BookingError` (`bookingResult/BookingResult.kt`), plus the `BookingStatus` enum |
| Suspend function & coroutine | `Catalogue.makeBooking()` is a `suspend fun` that calls `delay(1000)`; it is invoked from a `runBlocking { ... }` coroutine in `Main.kt` |

## Dependencies

- Kotlin 1.9.0 (JVM)
- `org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1`
