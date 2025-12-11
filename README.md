# PMS
Parking Management System (PMS) is a Spring Boot application that models a parking lot.

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

```bash
# Get all parking lots
curl "http://localhost:8080/api/v1/admin/parking-lots"

# Create a parking level for lot 1
curl -X POST "http://localhost:8080/api/v1/admin/parking-lots/1/levels" \
  -H "Content-Type: application/json" \
  -d '{ "levelNumber": 1, "spots": [] }'

# Add a compact spot to lot 1 level 1
curl -X POST "http://localhost:8080/api/v1/admin/parking-lots/1/levels/1/spots" \
  -H "Content-Type: application/json" \
  -d '{ "spotNumber": 10, "type": "COMPACT" }'
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
  "id": 42,
  "vehicle": { "licensePlate": "ABC-123-XY" },
  "parkingSpot": { "id": { "parkingLotId": 1, "parkingLevelId": 0, "spotNumber": 7 }, "type": "HANDICAPPED" },
  "startDateTime": "2024-06-20T09:15:00Z"
}
```

### Check out a vehicle
```bash
curl -X POST "http://localhost:8080/api/v1/check-out" \
  -H "Content-Type: application/json" \
  -d '{ "receiptId": 42 }'
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