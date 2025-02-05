# Vélos Libre Service

This project simulates a **bike-sharing system**, similar to Vélib or V'Lille, where users can rent bikes from stations and return them after use. The system includes functionalities for bike maintenance, theft, and redistribution of bikes among stations based on usage patterns. It's possible to extend the vehicle types so there're other types of stations. The game is automatic. 

## Features:
- **Stations & Bikes**: Each station has a unique ID and a capacity of 10 to 20 slots for bikes. Bikes can be returned to empty slots and only taken from occupied slots.
- **Vehicle Types**: Supports both regular bikes and scooters. Future expansions could add other types of vehicles and accessories.
- **Redistribution**: When a station becomes too full or empty for a certain period, a central control system redistributes bikes among stations.
- **Maintenance**: Bikes can become out-of-service after multiple rentals, requiring a repair check.
- **Theft**: Bikes left unused at a station for too long can be marked as stolen.

## Design

- RentStation : stations are generic, allowing them to manage any type of vehicle without complications.
- ControlCenter : also generic to simplify redistribution and prevent issues such as assigning incorrect types of vehicles to stations.
- TimeManager : responsible for managing the passage of time or intervals.
- Observer : The Observer design pattern was used to notify the ControlCenter of every vehicle drop-off and pick-up.  
**Observer** - ControlCenter  
**Observable** - RentStation
- Strategy : The Strategy design pattern was implemented to handle different vehicle redistribution methods.
- Visitor : The Visitor design pattern was used to allow different professions to interact with a bike. For instance, a repairman who repairs bikes.
- Decorator : The Decorator design pattern was employed to create decorated vehicles, adding additional features or accessories to them.


## Setup & Run:

1. Clone the repository:
```
git clone git@gitlab-etu.fil.univ-lille.fr:nathan.cordenier.etu/coo_cordenier_batbaatar.git coo
```

Place yourself in the cloned directory:
```
cd coo
```
2. To generate the documentation:

```
make docs
```
*The documentation will be generated in the docs directory.*

3. Compile and execute the source files:

To compile:

```
make cls
```

To execute:

```
make run
```

4. Compile and execute the tests:

To compile:

```
make test
```

To execute:

```
make test-run
```

5. Generate and execute the .jar file:

```
make velos.jar
```

*Run the commands from the project root directory*
