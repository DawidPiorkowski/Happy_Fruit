# -Happy_Fruit-

This Clojure-based simulation program makes sure that every city has the necessary minimum supply levels by coordinating vehicles to deliver canned foods across cities. Trucks are used to efficiently move stock once the software identifies cities in need of supplies and those with excess inventory.

# Features: 
- Truck Management
- City Stock Management 
- Transportation
- Simulation loop 
- Logs:  

# Code Breakdown: 
Warehouse properties: 
- :name - The city's name.
- :initial - Initial stock level.
- :min-capacity - Minimum stock capacity required.
- :max-capacity - Maximum stock capacity allowed.
- :current-stock - Current stock level.

Truck Properties:
- truck-capacity: Fixed at 100 cans.
- truck-count: Two trucks available.

Functions: 
- cities-needing-supply: Finds the cities with the lowest capacity in terms of current stock.
- cities-with-excess: Finds the cities with the current stocks exceeds the maximum capacity.
- transport-cans: Handles the transportation of cans between cities, adjusting stock levels and confirming the transfer.
- move-truck: Simulates a single trip for a truck, picking a source (excess supply) and a destination (needing supply).
- all-cities-satisfied?: Checks if all cities meet their minimum stock requirements.
- simulate-truck-trips: The main simulation loop that repeats truck trips until all cities are satisfied.
