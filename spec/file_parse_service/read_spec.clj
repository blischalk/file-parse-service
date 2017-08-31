(ns file-parse-service.read-spec
  (:require [speclj.core :refer :all]
            [file-parse-service.read :refer :all]))

(def data-file-count 3)

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
