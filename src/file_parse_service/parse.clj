(ns file-parse-service.parse)

(defrecord Person [lname fname sex fcolor dob])
(defn row->person
"Converts a row of fields as trings to a Person record"
[fields] (apply ->Person fields))

(defn split-and-trim-by
"Composed function of splitting and trimming on a deliminator"
[string delim]
(map clojure.string/trim (clojure.string/split string delim)))


(defn file-contents->unparsed-rows
"Iterates over a collection of the contents ffrom files and divides
 each files contents into individual rows by newlines"
[data-files-contents]
  (map #(clojure.string/split %1 #"\n") data-files-contents))


(defn parse-row
"Converts a delimated row string into collections of strings for each
  field"
[row]
(let [sp-tr-row (partial split-and-trim-by row)
      fields (cond
              (.contains row ",") (sp-tr-row #", ")
              (.contains row "|") (sp-tr-row #"\|")
              :else (sp-tr-row #" "))]
  (row->person fields)))


(defn parse-rows
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
       (parse-rows)))
