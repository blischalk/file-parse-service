(ns file-parse-service.core
  (:require [clojure.pprint :refer [pprint]])
  (:gen-class))

(def data-dir "resources")
(defrecord Person [lname fname sex fcolor dob])
(defn row->person [fields] (apply ->Person fields))

(defn read-data-files []
  (let [fs (file-seq (clojure.java.io/file data-dir))
        files (rest fs)
        data-files (if-not files
                     []
                     (map slurp files))]
    data-files))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
