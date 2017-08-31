(ns file-parse-service.parse-spec
  (:require [speclj.core :refer :all]
            [file-parse-service.parse :refer :all]))

(def r1 ["doe" "john" "blue" "male" "08-01-82"])
(def r2 ["smith" "harold" "purple" "male" "06-01-82"])
(def rows [r1 r2])
(def p1 (apply ->Person r1))
(def p2 (apply ->Person r2))
(def parsed-rows [p1 p2])
(defn make-deliminated-string [rows delim]
  (->> rows
       (map #(apply str (interpose delim %1)))
       (interpose "\n")
       (apply str)))

(def make-deliminated-string-rows (partial make-deliminated-string rows))
(def pipe-newline-string (make-deliminated-string-rows " | "))
(def comma-newline-string (make-deliminated-string-rows ", "))
(def space-newline-string (make-deliminated-string-rows " "))


(defn assert-parsed [expected input]
  (->> [input]
       parse-files
       first
       (should= expected)))


(describe 'parse-files
  (with-stubs)
  (it "splits file data by newline into rows"
    (should= 2 (count (parse-files [pipe-newline-string]))))
  (it "parses comma separated rows into person records"
    (assert-parsed p1 comma-newline-string))
  (it "parses pipe separated rows into person records"
    (assert-parsed p1 pipe-newline-string))
  (it "parses space separated rows into preson records"
    (assert-parsed p1 space-newline-string)))
