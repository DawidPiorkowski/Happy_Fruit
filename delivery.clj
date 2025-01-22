(ns happy-fruit.routes)

;; Define List of Cities with their Corresponding data
(def cities
  [{:name "Munich" :initial 500 :min-capacity 100 :max-capacity 500 :current-stock 500}
   {:name "Napoli" :initial 0 :min-capacity 70 :max-capacity 100 :current-stock 0}
   {:name "Innsbruck" :initial 50 :min-capacity 60 :max-capacity 150 :current-stock 50}
   {:name "Krakov" :initial 0 :min-capacity 80 :max-capacity 100 :current-stock 0}
   {:name "Hamburg" :initial 0 :min-capacity 20 :max-capacity 50 :current-stock 0}])

;; Define distances between cities
(def distances
  {"Munich-Napoli" 800
   "Munich-Hamburg" 600
   "Munich-Innsbruck" 200
   "Munich-Krakov" 500
   "Napoli-Hamburg" 1000
   "Napoli-Innsbruck" 1000
   "Napoli-Krakov" 1200
   "Hamburg-Innsbruck" 800
   "Hamburg-Krakov" 700
   "Innsbruck-Krakov" 400})

;; Function to calculate distance between cities
(defn get-distance [city1 city2]
  (let [key (str city1 "-" city2)
        rev-key (str city2 "-" city1)]
    (or (distances key) (distances rev-key) 0)))

;; Function to plan Day 2 routes ensuring Innsbruck has 10 units of stock
(defn plan-day-2-routes [cities truck-capacity truck-count]
  (let [innsbruck (first (filter #(= (:name %) "Innsbruck") cities))
        excess-stock (- (:current-stock innsbruck) 10)
        needs-supply (->> cities
                          (filter #(and (> (:min-capacity %) (:current-stock %))
                                        (not= (:name %) "Innsbruck")))
                          (sort-by #(get-distance "Innsbruck" (:name %))))] ;; Prioritize closest cities
    (loop [remaining-needs needs-supply
           routes []
           updated-cities cities
           excess-stock excess-stock
           trucks-left truck-count]
      (if (and (seq remaining-needs) (> excess-stock 0) (> trucks-left 0))
        (let [dest (first remaining-needs)
              needed-stock (- (:min-capacity dest) (:current-stock dest))
              transfer-amount (min truck-capacity excess-stock needed-stock)
              route {:truck (- truck-count trucks-left -1)
                     :from "Innsbruck"
                     :to (:name dest)
                     :amount transfer-amount
                     :distance (get-distance "Innsbruck" (:name dest))}
              new-updated-cities (map #(cond
                                         (= (:name %) "Innsbruck") (update % :current-stock - transfer-amount)
                                         (= (:name %) (:name dest)) (update % :current-stock + transfer-amount)
                                         :else %)
                                      updated-cities)]
          (recur (rest remaining-needs)
                 (conj routes route)
                 new-updated-cities
                 (- excess-stock transfer-amount)
                 (dec trucks-left)))
        {:routes routes :updated-cities updated-cities}))))

;; Function to plan Day 3 routes ensuring Krakow has 30 units of stock
(defn plan-day-3-routes [cities truck-capacity truck-count]
  (let [krakov (first (filter #(= (:name %) "Krakov") cities))
        excess-stock (- (:current-stock krakov) 30)
        needs-supply (->> cities
                          (filter #(and (> (:min-capacity %) (:current-stock %))
                                        (not= (:name %) "Krakov")))
                          (sort-by #(get-distance "Krakov" (:name %))))] ;; Prioritize closest cities
    (loop [remaining-needs needs-supply
           routes []
           updated-cities cities
           excess-stock excess-stock
           trucks-left truck-count]
      (if (and (seq remaining-needs) (> excess-stock 0) (> trucks-left 0))
        (let [dest (first remaining-needs)
              needed-stock (- (:min-capacity dest) (:current-stock dest))
              transfer-amount (min truck-capacity excess-stock needed-stock)
              route {:truck (- truck-count trucks-left -1)
                     :from "Krakov"
                     :to (:name dest)
                     :amount transfer-amount
                     :distance (get-distance "Krakov" (:name dest))}
              new-updated-cities (map #(cond
                                         (= (:name %) "Krakov") (update % :current-stock - transfer-amount)
                                         (= (:name %) (:name dest)) (update % :current-stock + transfer-amount)
                                         :else %)
                                      updated-cities)]
          (recur (rest remaining-needs)
                 (conj routes route)
                 new-updated-cities
                 (- excess-stock transfer-amount)
                 (dec trucks-left)))
        {:routes routes :updated-cities updated-cities}))))

;; Function to print city stocks
(defn print-city-stocks [cities]
  (println "Current city stocks:")
  (doseq [city cities]
    (println (:name city) "- Stock:" (:current-stock city))))

;; Simulate a day of trading
(defn simulate-day [cities day truck-capacity truck-count plan-routes-fn]
  (println "\nDay" day "Simulation:")
  (let [{:keys [routes updated-cities]} (plan-routes-fn cities truck-capacity truck-count)]
    (if (empty? routes)
      (println "No deliveries required.")
      (doseq [route routes]
        (println "Truck" (:truck route) "delivers" (:amount route) "units from" (:from route) "to" (:to route) "covering" (:distance route) "km")))
    (print-city-stocks updated-cities)
    updated-cities))

;; Simulate all trading days
(defn simulate-trading-days [cities]
  (let [truck-capacity 100
        truck-count 2
        ;; Day 1: Regular logic
        day1 (simulate-day cities 1 truck-capacity truck-count
                           (fn [cities truck-capacity truck-count]
                             (let [needs-supply (->> cities
                                                     (filter #(> (:min-capacity %) (:current-stock %)))
                                                     (sort-by #(get-distance "Munich" (:name %))))]
                               (loop [remaining-needs needs-supply
                                      routes []
                                      updated-cities cities
                                      trucks-left truck-count]
                                 (if (and (seq remaining-needs) (> trucks-left 0))
                                   (let [dest (first remaining-needs)
                                         needed-stock (- (:min-capacity dest) (:current-stock dest))
                                         source (first (filter #(= (:name %) "Munich") updated-cities))
                                         available-stock (:current-stock source)
                                         transfer-amount (min truck-capacity needed-stock available-stock)
                                         route {:truck (- truck-count trucks-left -1)
                                                :from "Munich"
                                                :to (:name dest)
                                                :amount transfer-amount
                                                :distance (get-distance "Munich" (:name dest))}
                                         new-updated-cities (map #(cond
                                                                    (= (:name %) "Munich") (update % :current-stock - transfer-amount)
                                                                    (= (:name %) (:name dest)) (update % :current-stock + transfer-amount)
                                                                    :else %)
                                                                 updated-cities)]
                                     (recur (rest remaining-needs)
                                            (conj routes route)
                                            new-updated-cities
                                            (dec trucks-left)))
                                   {:routes routes :updated-cities updated-cities})))))
        ;; Day 2: Ensure Innsbruck ends with 10 units of stock
        day2 (simulate-day day1 2 truck-capacity truck-count plan-day-2-routes)
        ;; Day 3: Ensure Krakow ends with 30 units of stock
        day3 (simulate-day day2 3 truck-capacity truck-count plan-day-3-routes)]
    (println "\nFinal city stocks:")
    (doseq [city day3]
      (println (:name city) "- Stock:" (:current-stock city)))))

;; Run the simulation
(defn -main []
  (simulate-trading-days cities))

(-main)
