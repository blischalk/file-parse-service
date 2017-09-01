(ns file-parse-service.sort
  "Sort namespace provides functionality for sorting Person records by
  various fields")

(declare sort-by-field)

(defn filter-by-sex
  "Filter collection of Person records by sex field"
  [people sex]
  (filter #(= (:sex %1) sex) people))


(defn sort-by-sex
  "Sort a collection of Person records by sex"
  [data]
  (let [tmp-fn #(-> data
                    (filter-by-sex %1)
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
      "sex" (sort-by-sex data)
      (sbk data))))
