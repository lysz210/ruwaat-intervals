# Ruwaat Intervals

Ruwaat Intervals is a dedicated data collector component within the home lab environment. Acting as an integration bridge, its primary responsibility is to fetch, normalize, and ingest athletic activity data into the centralized ecosystem, working alongside companion services like the AlIdrisi geolocation manager.

## Overview

The service operates as an automated ingestion pipeline. It periodically synchronizes fitness metrics, training loads, and structural workout data from upstream platforms, ensuring that the home lab maintains an accurate, up-to-date historical record of physical activities for downstream analysis and mapping.

## Data Ecosystem

Ruwaat Intervals relies on the robust APIs of the endurance training community to function:

*   **Intervals.icu:** Serving as the direct upstream source, Intervals.icu provides the comprehensive analytics, load tracking, and organized activity streams that this service downloads and parses.
*   **Garmin:** A foundational acknowledgment goes to Garmin as the primary origin of the underlying activity data, generating the high-fidelity telemetry (GPS, heart rate, power) that feeds into the broader ecosystem.

## Related Components

*   **AlIdrisi:** The core geolocation and spatial data management group within the home lab, which consumes the tracking data processed by Ruwaat Intervals to map and contextualize activity histories.