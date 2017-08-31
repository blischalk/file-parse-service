(ns file-parse-service.core
  (:require [clojure.pprint :refer [pprint]])
  (:gen-class))

(def data-dir "resources")
(defrecord Person [lname fname sex fcolor dob])
(defn row->person [fields] (apply ->Person fields))


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


(defn split-and-trim-by
"Composed function of splitting and trimming on a deliminator"
[string delim]
  (->> (clojure.string/split string delim)
       (map clojure.string/trim)))


(defn file-contents->unparsed-rows
"Iterates over a collection of the contents ffrom files and divides
 each files contents into individual rows by newlines"
[data-files-contents]
  (map #(clojure.string/split %1 #"\n") data-files-contents))


(defn parse-row
"Converts a delimated row string into collections of strings for each
  field"
[row]
  (let [sp-tr-row (partial split-and-trim-by row)]
    (cond
     (.contains row ",") (sp-tr-row #", ")
     (.contains row "|") (sp-tr-row #"\|")
     :else (sp-tr-row #" "))))


(defn parse-rows
"Parses each row of a collection of multiple files unparsed rows"
[multiple-files-unparsed-rows]
  (map #(map parse-row %1)
       multiple-files-unparsed-rows))


(defn string-parse-files [data-files-contents]
  (->> data-files-contents
       (file-contents->unparsed-rows)
       (parse-rows)))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
