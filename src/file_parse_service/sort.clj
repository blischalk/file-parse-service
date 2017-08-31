(ns file-parse-service.sort)

(declare sort-by-field)

(defn sort-by-sex
[data]
(let [females (filter (fn [r] (= (:sex r) "Female")) data)
      males (filter (fn [r] (= (:sex r) "Male")) data)
      sorted-females (reverse (sort-by-field females "lname"))
      sorted-males (reverse (sort-by-field males "lname"))]
  (concat sorted-females sorted-males)))


(defn sort-by-field
  "Keywordize sort field and sort data by it"
  [data field]
  (let [sbk (partial sort-by (keyword field))]
    (case field
      "lname" (reverse (sbk data))
      "sex" (sort-by-sex data)
      (sbk data))))
