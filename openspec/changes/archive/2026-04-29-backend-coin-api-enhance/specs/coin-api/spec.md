## ADDED Requirements

### Requirement: Coin list API includes sparkline data
The backend `/coins` API SHALL include sparkline data for the last 7 days for each coin returned.

#### Scenario: Requesting coin list
- **WHEN** client makes a GET request to `/coins?page=1`
- **THEN** the system returns a list of coins where each coin has a `sparkline` field containing an array of price values.

### Requirement: CORS is enabled for all origins in development
The backend SHALL allow Cross-Origin Resource Sharing (CORS) from any origin during development to facilitate Flutter app integration.

#### Scenario: Preflight request from Flutter app
- **WHEN** Flutter web app sends an OPTIONS request to any API endpoint
- **THEN** the system returns CORS headers allowing the request.

### Requirement: Backend runs on port 8088
The backend service SHALL listen on port 8088 to avoid port conflicts with other services.

#### Scenario: Starting the backend service
- **WHEN** the backend application is started
- **THEN** it successfully binds to and listens on port 8088.
