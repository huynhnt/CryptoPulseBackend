# Capability: api-caching

## ADDED Requirements

### Requirement: Backend caches coin list responses
The backend SHALL cache the response of `/coins/markets` API from CoinGecko for 1 minute to prevent rate limiting while keeping data relatively fresh.

#### Scenario: Consecutive requests to coin list
- **WHEN** the frontend requests the top coins multiple times within 1 minute
- **THEN** the backend fetches from CoinGecko only on the first request
- **AND** returns the cached data for all subsequent requests within the 1-minute window

### Requirement: Backend caches coin chart responses
The backend SHALL cache the response of `/coins/{id}/market_chart` API from CoinGecko for 5 minutes, as historical chart data is less sensitive to minute-by-minute fluctuations.

#### Scenario: Consecutive requests to market chart
- **WHEN** the frontend requests the market chart for a specific coin multiple times within 5 minutes
- **THEN** the backend fetches from CoinGecko only on the first request
- **AND** returns the cached data for all subsequent requests within the 5-minute window

### Requirement: Backend caches coin detail responses
The backend SHALL cache the response of `/coins/{id}` API from CoinGecko for 1 minute.

#### Scenario: Consecutive requests to coin details
- **WHEN** the frontend requests the details for a specific coin multiple times within 1 minute
- **THEN** the backend fetches from CoinGecko only on the first request
- **AND** returns the cached data for all subsequent requests within the 1-minute window

### Requirement: Centralized CoinGecko Base URL Configuration
The backend SHALL extract the hardcoded CoinGecko Base URL (`https://api.coingecko.com/api/v3`) into a configurable property (`coingecko.api.baseUrl`) in the `application.properties` file.

#### Scenario: Calling CoinGecko APIs
- **GIVEN** the application is configured with `coingecko.api.baseUrl=https://api.coingecko.com/api/v3`
- **WHEN** the `CoinServiceImpl` makes a request to any CoinGecko endpoint
- **THEN** it must construct the full URL by appending the specific endpoint path to the configured base URL
