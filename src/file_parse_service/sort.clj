(ns file-parse-service.sort
  "Sort namespace provides functionality for sorting Person records by
  various fields")

(declare sort-by-field)

(defn filter-by-gender
  "Filter collection of Person records by gender field"
  [people gender]
  (filter #(= (:gender %1) gender) people))


(defn sort-by-gender
  "Sort a collection of Person records by gender"
  [data]
  (let [tmp-fn #(-> data
                    (filter-by-gender %1)
                    (sort-by-field "lname")
                    reverse)
        females (tmp-fn "Female")
        males (tmp-fn "Male")]
    (concat females males)))


(defn sort-by-field
  "Keywordize sort field and sort data by it"
  [data field]
  (let [sbk (partial sort-by (keyword field))]
    (case field
      "lname" (reverse (sbk data))
      "gender" (sort-by-gender data)
      (sbk data))))
