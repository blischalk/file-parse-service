(ns file-parse-service.parse
  "Parse namespace provides functionality for parsing data files with
  various deliminaters into Person records"
  (:require [clojure.string :as s]
            [clj-time.format :as f]))

(defrecord Person [lname fname gender fcolor dob])
(defn row->person
  "Converts a row of fields as strings to a Person record"
  [[lname fname gender fcolor dob]]
  (->Person lname fname gender fcolor (f/parse (f/formatter "MM-dd-yyyy")
                                            dob)))

(defn ^:private split-and-trim-by
  "Composed function of splitting and trimming on a deliminator"
  [string delim]
  (map s/trim (s/split string delim)))


(defn ^:private file-contents->unparsed-rows
  "Iterates over a collection of the contents ffrom files and divides
  each files contents into individual rows by newlines"
  [data-files-contents]
  (map #(s/split %1 #"\n") data-files-contents))


(defn ^:private parse-row
  "Converts a delimated row string into collections of person records"
  [row]
  (let [sp-tr-row (partial split-and-trim-by row)
        fields (cond
                (.contains row ",") (sp-tr-row #", ")
                (.contains row "|") (sp-tr-row #"\|")
                (.contains row " ") (sp-tr-row #" ")
                :else (throw (AssertionError.
                              (str "Unable to parse row: " row))))]
    (row->person fields)))


(defn ^:private parse-rows
  "Parses each row of a collection of multiple files unparsed rows"
  [multiple-files-unparsed-rows]
  (map #(map parse-row %1)
       multiple-files-unparsed-rows))


(defn parse-files
  "Parses data files with various deliminaters
  into Person records"
  [data-files-contents]
  (->> data-files-contents
       (file-contents->unparsed-rows)
       (parse-rows)
       flatten))
