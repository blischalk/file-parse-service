(ns file-parse-service.read
  "Read namespace provides functionality for reading data from data files")

(def ^:private data-dir
  "Directory where datafiles for the application are read"
  "resources")

(defn read-data-files
  "Reads data files from the data-dir into a collection.
  Each item in the collection is a string which separates
  each row of data by a newline"
  []
  (-> data-dir
      clojure.java.io/file
      file-seq
      rest
      (#(if-not %1
          []
          (map slurp %1)))))
