(ns file-parse-service.sort-spec
  (:require [speclj.core :refer :all]
            [file-parse-service.sort :refer :all]
            [file-parse-service.specutil :refer :all]))

(describe 'sort-by-field
  (it "sorts by last name decending"
    (should= [p1 p4 p3 p2] (sort-by-field records "lname")))
  (it "sorts by birth date ascending"
    (should= [p2 p3 p4 p1] (sort-by-field records "dob")))
  (it "sorts by gender (females before males), last name asc"
    (should= [p4 p1 p2 p3] (sort-by-field records "gender"))))
