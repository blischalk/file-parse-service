(ns file-parse-service.sort-spec
  (:require [speclj.core :refer :all]
            [file-parse-service.parse :refer [row->person]]
            [file-parse-service.sort :refer :all]))

(def p1 (row->person ["Ryan" "Jane" "Female" "Blue" "11-28-1983"]))
(def p2 (row->person ["Daniel" "John" "Male" "Blue" "10-28-1923"]))
(def p3 (row->person ["Marley" "Bob" "Male" "Green" "02-10-1945"]))
(def p4 (row->person ["McDonald" "Ronda" "Female" "Pink" "11-06-1953"]))
(def records [p1 p2 p3 p4])

(describe 'sort-by-field
  (it "sorts by last name decending"
    (should= [p1 p4 p3 p2] (sort-by-field records "lname")))
  (it "sorts by birth date ascending"
    (should= [p2 p3 p4 p1] (sort-by-field records "dob")))
  (it "sorts by gender (females before males), last name asc"
    (should= [p4 p1 p2 p3] (sort-by-field records "sex"))))
