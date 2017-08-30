(ns file-parse-service.core-spec
  (:require [speclj.core :refer :all]
            [file-parse-service.core :refer :all])
  )

(def data-file-count 3)
(def r1 ["doe" "john" "blue" "male" "08-01-82"])
(def r2 ["smith" "harold" "purple" "male" "06-01-82"])
(def p1 (apply ->Person r1))
(def p2 (apply ->Person r2))
(def parsed-rows [p1 p2])

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
