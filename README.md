# Parking Management System

Parking Management System (PMS) is a Spring Boot 3.5 application that models a multi-level parking lot.
Vehicles check-in to receive a receipt, occupy a spot that matches their type and later check-out
to finish the parking session and compute the parking fee. The system persists its state using Spring Data JPA (in-memory H2 database) and exposes REST
endpoints.

### Core behavior

- Spot assignment is strategy-based. Each vehicle type (car, motorcycle, truck) has its own strategy that finds the first available spot appropriate for the
  vehicle and the requested parking lot. So e.g. if no proper places are found it can try to find bitter spot it is applicable for a vehicle type. Handicapped
  cars can use handicapped spot before falling back to other spots.
- Pricing is also strategy-base and handle fee calculation based on the completed ParkingSession for different vehicle type. So it allows extension of the logic
  whenever new
  vehicle type is added.
- Data model: Parking lots contain levels, which in turn contain typed parking spots, parking sessions assign a vehicle to a spot for a time range.

## Architecture and design rationale

- Domain entities
    - ParkingLot, ParkingLevel and ParkingSpot that has multiple versions per size (CompactSpot, LargeSpot, MotorcycleSpot, HandicappedSpot). They use
      one-to-many relationship correspondingly.
    - ParkingSession stores information about the current occupied place and keeps track on how long it takes. Therefore it stores a vehicle, the assigned spot
      and start/finish timestamps.
    - Vehicles are modeled with inheritance for extensibility (Car, Motorcycle, Truck) and created through VehicleFactory to keep controller logic thin. It also
      allows to have specific properties of vehicle type while maintaining a single table
- User use-cases:
    - During check-in `CheckInService` validates target lot, upserts the vehicle, assigns it to an available spot, marks that spot as occupied and returns a
      Receipt DTO for the client.
    - During check-out `CheckOutService` finishes the active session, marks the spot as available and calculates the fee.
- Strategy-based extension
    - Spot selection uses slog assignment strategy per vehicle type so rules can evolve and extend independently.
    - Fee calculation uses pricing strategy per vehicle type. For simplicity currently all strategies calculate fee per-second multiplying a static hourly rate
      for the vehicle category.
- Validation and errors
    - Request DTOs uses Bean Validation annotations and are validated automatically by Spring.
    - Trying to access entity that does not exist or trying to check-in when there are no free spots throw custom exceptions, resulting in errors with
      corresponding HTTP codes.

## Building and running

Prerequisites: Java 25. The project includes the Gradle wrapper, so no additional tools are required.

1. Build
   ```bash
   ./gradlew clean build
   ```
2. Run the application
   ```bash
   ./gradlew bootRun
   ```
   The service starts on port `8080`.

## API usage examples

Base path: `http://localhost:8080/api/v1`

Swagger UI with API documentation is available at `http://localhost:8080/swagger-ui/index.html` when the app is running.
It can be used to try the API.

### Admin management endpoints

Admin endpoints are available at `/api/v1/admin` for CRUD related to parking lots, levels and spots. For example:

Create a parking lot
```bash
curl -X POST "http://localhost:8080/api/v1/admin/parking-lots" \
  -H "Content-Type: application/json" \
  -d '{ "name": "Test lot" }'
```
Create a parking level for lot 1
```bash
curl -X POST "http://localhost:8080/api/v1/admin/parking-lots/1/levels" \
  -H "Content-Type: application/json" \
  -d '{ "level": 1 }'
```
Add a compact spot to lot 1 level 1
```bash
curl -X POST "http://localhost:8080/api/v1/admin/parking-lots/1/levels/1/parking-spots" \
  -H "Content-Type: application/json" \
  -d '{ "parkingSpotNo": 10, "type": "COMPACT", "available": true }'
```

### Get endpoints

Get all parking lots
```bash
curl "http://localhost:8080/api/v1/parking-lots"
```

Get all parking levels for a parking lot 1
```bash
curl "http://localhost:8080/api/v1/parking-lots/1/levels"
```

Get all parking spots for a parking level 1 in a parking lot 1
```bash
curl "http://localhost:8080/api/v1/parking-lots/1/levels/1/parking-spots"
```

### Check in a vehicle

```bash
curl -X POST "http://localhost:8080/api/v1/check-in" \
  -H "Content-Type: application/json" \
  -d '{
    "parkingLotId": 1,
    "licensePlate": "ABC-123-XY",
    "vehicleType": "CAR",
    "handicapped": true
  }'
```

Successful response (201 Created):

```json
{
  "id": 1,
  "vehicle": {
    "licensePlate": "Test Lot"
  },
  "parkingSpot": {
    "parkingLotId": 1,
    "level": 1,
    "parkingSpotNo": 1,
    "available": false
  },
  "startDateTime": "2024-06-20T09:15:00Z"
}
```

### Check out a vehicle

```bash
curl -X POST "http://localhost:8080/api/v1/check-out" \
  -H "Content-Type: application/json" \
  -d '{ "receiptId": 1 }'
```

Successful response (200 OK):

```json
{
  "receiptId": 42,
  "entryTime": "2024-06-20T09:15:00Z",
  "exitTime": "2024-06-20T10:05:30Z",
  "duration": "PT3030S",
  "fee": 2.02
}
```

## Known limitations / TODOs

- Authentication/authorization is not implemented for simplicity - all operations are open. Spring security should be added with proper config. Also it would
  make sense to have all CRUD operations for an entity in corresponding controllers. Write endpoint can be marked as admin-only using Spring security
  annotation. Currently all admin endpoints reside in a separate controller just for presentation purposes.
- Admin can make spot available when it is occupied by a vehicle. Should be handled properly (e.g. reject request or finish the parking session).
- Level index in parking lot probably should not be provided but instead automatically incremented. On the other hand, it can make it harder to have custom
  level indexes (e.g. start from the second floor).
- No validation when admin tries to insert a level or a spot with the same ID.
- Test coverage can be improved, especially unit tests.
  - Available parking slot should be implemented in a way that only one place is returned (See TODO in ParkingSpotRepository).