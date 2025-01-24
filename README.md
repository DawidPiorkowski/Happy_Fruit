# -Happy_Fruit-

The goal of this project is to ensure effective stock distribution based on pre-established requirements by simulating a trading system between several cities. It guarantees that certain cities meet their minimum stock requirements by the end of particular trading days and includes day-by-day routing plans.

# Features: 
- Truck Management
- City Stock Management 
- Transportation
- Simulation loop 
- Logs:  

# Code Breakdown: 
- Warehouse Properties
    :name - The city's name.
    :initial - The city's initial stock level at the beginning of the simulation.
    :min-capacity - The minimum stock level required to ensure the city is adequately supplied.
    :max-capacity - The maximum stock capacity that the city can hold.
    :current-stock - The current stock level during the simulation.
- Truck Properties
    truck-capacity - Each truck has a fixed capacity of 100 units.
    truck-count - There are two trucks available for deliveries on each day.
- Key Functions
    get-distance
        Calculates the distance between two cities using the predefined distances map.
        Accounts for bidirectional routes by checking both combinations of city pairs.
    cities-needing-supply
        Identifies cities whose :current-stock is below their :min-capacity.
        Prioritizes these cities based on proximity to the source city.
    cities-with-excess
        Finds cities whose :current-stock exceeds their target level for the day (e.g., Innsbruck with 10 units or Krakow with 30 units).
        Supplies other cities from these excess stocks.
    transport-cans
        Handles the actual transportation of cans between a source and a destination.
        Updates :current-stock for both cities and tracks the amount transported and the route taken.
    move-truck
        Simulates a single trip for a truck.
        Finds a source city (with excess supply) and a destination city (needing supply).
        Assigns the truck to transfer as much stock as possible within its capacity and updates the city stocks accordingly.
    plan-day-2-routes
        Ensures Innsbruck's stock ends at exactly 10 units by redistributing any excess to nearby cities.
    plan-day-3-routes
        Focuses on ensuring Krakow's stock ends at 30 units while redistributing excess stocks to other cities.
    all-cities-satisfied?
        Checks if all cities meet their :min-capacity stock requirements.
        Returns true if all cities are satisfied; otherwise, false.
    simulate-day
        Simulates a single trading day.
        Prints truck routes, transported stock, and updated stock levels for all cities.
    simulate-trading-days
        The main simulation function.
        Runs day-by-day simulations:
            Day 1: General supply to cities from Munich.
            Day 2: Redistribute stock from Innsbruck.
            Day 3: Ensure Krakow has at least 30 units of stock.
        Prints the final stock levels for all cities.
