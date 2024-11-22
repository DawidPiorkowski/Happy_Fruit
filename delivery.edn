(ns happy-fruit.core)

;; Define List of Cities with their Corresponding data 

(def cities
  [{:name "Munich" :initial 500 :min-capacity 100 :max-capacity 500 :current-stock 500}
   {:name "Napoli" :initial 20 :min-capacity 70 :max-capacity 100 :current-stock 20}
   {:name "Innsbruck" :initial 50 :min-capacity 60 :max-capacity 150 :current-stock 50}
   {:name "Krakov" :initial 0 :min-capacity 80 :max-capacity 100 :current-stock 0}
   {:name "Hamburg" :initial 10 :min-capacity 20 :max-capacity 50 :current-stock 10}])

;; Define Truck Properties
(def truck-capacity 100)
;; "The number of available trucks."
(def truck-count 2)

;; Function to identify cities that need supply
(defn cities-needing-supply [cities]
  (filter #(> (:min-capacity %) (:current-stock %)) cities))

;; Function to find cities that have extra supply
(defn cities-with-excess [cities]
  (filter #(> (:current-stock %) (:min-capacity %)) cities))

;; Function to transport cans from source to destination with driver confirmation
(defn transport-cans [source dest amount truck-id]
  (let [actual-amount (min amount (- (:max-capacity dest) (:current-stock dest)))]
    ;; Confirmation message
    (println "Truck" truck-id "confirmation: Delivering" actual-amount "cans from" (:name source) "to" (:name dest))
    [(assoc source :current-stock (- (:current-stock source) actual-amount))
     (assoc dest :current-stock (+ (:current-stock dest) actual-amount))]))

;; Simulate one truck trip
(defn move-truck [cities truck-id]
  (let [needs-supply (cities-needing-supply cities)
        has-excess (cities-with-excess cities)]
    (if (and (seq needs-supply) (seq has-excess))
      (let [source (first has-excess)
            dest (first needs-supply)
            [updated-source updated-dest] (transport-cans source dest truck-capacity truck-id)]
        (map #(cond
                (= (:name %) (:name source)) updated-source
                (= (:name %) (:name dest)) updated-dest
                :else %) cities))
      cities)))

;; Check if all cities meet their stock requirements
(defn all-cities-satisfied? [cities]
  (every? #(>= (:current-stock %) (:min-capacity %)) cities))

;; Universal simulation loop
(defn simulate-truck-trips [cities]
  (loop [current-cities cities
         trip-number 1
         truck-id 1]
    ;; Check if all cities have required stock 
    (if (all-cities-satisfied? current-cities)
      current-cities
      (let [updated-cities (move-truck current-cities truck-id)
            next-truck-id (if (= truck-id 1) 2 1)]
        ;; Print stock levels after each trip
        (println "\nStock levels after trip" trip-number ":")
        (doseq [city updated-cities]
          (println (:name city) "- Stock:" (:current-stock city)))
        ;; Recur with updated cities, incremented trip number, and alternate truck ID
        (recur updated-cities (inc trip-number) next-truck-id)))))

;; Running the simulation
(defn -main []
  (let [final-cities (simulate-truck-trips cities)]
    (println "\nFinal city stocks after simulation:")
    (doseq [city final-cities]
      (println (:name city) "- Stock:" (:current-stock city)))))

(-main)
