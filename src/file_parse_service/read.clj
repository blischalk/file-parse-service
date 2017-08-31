(ns file-parse-service.read)

(def ^:private data-dir "resources")

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
