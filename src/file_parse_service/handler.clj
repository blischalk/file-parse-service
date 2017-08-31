(ns file-parse-service.handler
  (:require [compojure.api.sweet :refer :all]
            [file-parse-service.parse :as p]
            [file-parse-service.read :refer :all]
            [file-parse-service.sort :as so]
            [ring.util.http-response :refer :all]
            [schema.core :as s]))

(def records (atom (p/parse-files (read-data-files))))

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Data Parsing and Sorting API"
                    :description "Data Parsing and Sorting API"}
             :tags [{:name "api", :description "some apis"}]}}}

    (context "/records" []
      :tags ["api"]

      (GET "/gender" []
        :summary "returns records sortd by gender"
        (ok {:result (so/sort-by-field @records "sex")}))

      (GET "/name" []
        :summary "returns records sortd by last name"
        (ok {:result (so/sort-by-field @records "lname")}))

      (GET "/birthdate" []
        :summary "returns records sortd by birthdate"
        (ok {:result (so/sort-by-field @records "dob")})))))
