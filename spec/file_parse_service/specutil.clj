(ns file-parse-service.specutil
  (:require [file-parse-service.parse :refer [row->person]]))


(def p1 (row->person ["Ryan" "Jane" "Female" "Blue" "11-28-1983"]))
(def p2 (row->person ["Daniel" "John" "Male" "Blue" "10-28-1923"]))
(def p3 (row->person ["Marley" "Bob" "Male" "Green" "02-10-1945"]))
(def p4 (row->person ["McDonald" "Ronda" "Female" "Pink" "11-06-1953"]))
(def records [p1 p2 p3 p4])
