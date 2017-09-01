(ns file-parse-service.core
  "Provides the main startup functionality and command argument parsing."
  (:require [clojure.pprint :refer [pprint]]
            [clojure.tools.cli :refer [parse-opts]]
            [clj-time.format :as f]
            [file-parse-service.handler :as h]
            [file-parse-service.parse :as p]
            [file-parse-service.read :as r]
            [file-parse-service.sort :as so]
            [ring.adapter.jetty :refer [run-jetty]])

  (:gen-class))

(def ^:private jetty-port "The port to run Jetty on" 9999)
(def ^:private version "The current version of the application" "1.0")
(defn ^:private print-formatted-records
  "Formats and prints records"
  [records]
  (dorun (map (fn [{:keys [lname fname fcolor sex dob]}]
                (println (str "FirstName: " fname
                              " LastName: " lname
                              " FavoriteColor: " fcolor
                              " Sex: " sex
                              " DOB: " (f/unparse (f/formatter "MM-dd-yyyy")
                                                  dob))))
              records)))


(defn ^:private parse-sort-print
  "parses, sorts, and prints formatted data"
  [data sort-field]
  (-> data
      p/parse-files
      (so/sort-by-field sort-field)
      print-formatted-records))


(defn ^:private validate-sort-field
  "Verifies that a supplied field to sort on is valid."
  [field]
  (let [valid-fields ["fname" "lname" "fcolor" "sex" "dob"]]
    (if (.contains valid-fields field) field
        (throw (AssertionError. "Unknown sort field error")))))


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
      (parse-sort-print (r/read-data-files)
                        (validate-sort-field (:sort-by options)))
      (System/exit 0))

    (when (:web-service options)
      (println "Starting webserver...")
      (run-jetty h/app {:port jetty-port, :join? true})
      (System/exit 0))

    (-> *in*
        slurp
        vector
        (parse-sort-print (validate-sort-field (:sort-by options))))

    (System/exit 0)))
