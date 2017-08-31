(ns file-parse-service.core-spec
  (:require [speclj.core :refer :all]
            [file-parse-service.core :refer :all]))

(def data-file-count 3)
(def r1 ["doe" "john" "blue" "male" "08-01-82"])
(def r2 ["smith" "harold" "purple" "male" "06-01-82"])
(def rows [r1 r2])
(def p1 (apply ->Person r1))
(def p2 (apply ->Person r2))
(def parsed-rows [p1 p2])
(defn make-deliminated-string [rows delim]
  (apply str
         (interpose "\n"
                    (map (fn [row]
                           (apply str
                                  (interpose delim row)))
                         rows))))

(def make-deliminated-string-rows (partial make-deliminated-string rows))
(def pipe-newline-string (make-deliminated-string-rows " | "))
(def comma-newline-string (make-deliminated-string-rows ", "))
(def space-newline-string (make-deliminated-string-rows " "))

(defn assert-parsed [expected input]
  (should= expected
           (ffirst (parse-files [input]))))

(describe 'read-data-files
  (context "when data files exist"
    (it "returns aggregate data from data files"
      (should-not-be empty? (read-data-files)))
    (it "returns a vector for each file"
      (should= data-file-count (count (read-data-files)))))

  (context "when data files do not exist"
    (with-stubs)
    (it "returns an empty vector"
      (with-redefs [clojure.core/file-seq (stub :file {:return []})]
        (should= [] (read-data-files))))))

(describe 'row->person
  (it "converts a parsed row to a Person record"
    (should= p1 (row->person r1))))

(describe 'parse-files
  (with-stubs)
  (it "splits file data by newline into rows"
    (should= 2 (count (first (parse-files [pipe-newline-string])))))
  (it "parses comma separated rows"
    (assert-parsed p1 comma-newline-string))
  (it "parses pipe separated rows"
    (assert-parsed p1 pipe-newline-string))
  (it "parses space separated rows"
    (assert-parsed p1 space-newline-string)))
