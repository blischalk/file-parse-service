(ns file-parse-service.core
  (:require [clojure.pprint :refer [pprint]]
            [clojure.tools.cli :refer [parse-opts]]
            [file-parse-service.parse :as p])
  (:gen-class))

(def ^:private version "1.0")
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


(defn ^:private print-formatted-records [records]
  (dorun (map (fn [{:keys [lname fname fcolor sex dob]}]
                (println (str "FirstName: " fname
                              " LastName: " lname
                              " FavoriteColor: " fcolor
                              " Sex: " sex
                              " DOB: " dob)))
              records)))


(defn -main
  "Parses and sorts data from std in, from a directory of files, or
  makes data parsing and sorting avaiable via webservic."
  [& args]
  (let [{:keys [options arguments errors summary]}
        (parse-opts args
                    [["-h" "--help"]
                     ["-s" "--sort-by field-name" :default "lname"]
                     ["-r" "--read-datafiles"
                      "Read and sort datafiles from resources"]
                     ["-v" "--version"]
                     ["-w" "--web-service" "Not Implemented Yet..."]])]

    (when (:version options)
      (println version)
      (System/exit 0))

    (when (:read-datafiles options)
      (println "Reading data from datafiles in resources directory...")
      (print-formatted-records (p/parse-files (read-data-files)))
      (System/exit 0))

    (when (:web-service options)
      (println "Starting webserver...")
      (println "Not currently implemented!")
      (System/exit 0))

    (let [data-from-stdin (slurp *in*)
          parsed (p/parse-files [data-from-stdin])
          parsed-and-sorted parsed]
      (print-formatted-records parsed-and-sorted)
      (System/exit 0))))
