(ns file-parse-service.sort)

(defn sort-by-field
  "Keywordize sort field and sort data by it"
  [data field]
  (let [sbk (partial sort-by (keyword field))]
    (case field
      "lname" (reverse (sbk data))
      (sbk data))))
