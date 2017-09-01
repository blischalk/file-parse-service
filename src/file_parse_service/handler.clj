(ns file-parse-service.handler
  (:require [compojure.api.sweet :refer :all]
            [file-parse-service.parse :as p]
            [file-parse-service.read :refer :all]
            [file-parse-service.sort :as so]
            [ring.util.http-response :refer :all]
            [schema.core :as s]))

(def records (atom (p/parse-files (read-data-files))))

(s/defschema Person
  {:fname s/Str
   :lname s/Str
   :fcolor s/Str
   :sex s/Str
   :dob s/Str})


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
      (POST "/" []
        :body [person Person]
        :summary "Returns collection of people"
        (do
          (swap! records conj (p/map->Person person))
          (ok {:result (so/sort-by-field @records "lname")})))

      (GET "/gender" []
        :summary "returns records sortd by gender"
        (ok {:result (so/sort-by-field @records "sex")}))

      (GET "/name" []
        :summary "returns records sortd by last name"
        (ok {:result (so/sort-by-field @records "lname")}))

      (GET "/birthdate" []
        :summary "returns records sortd by birthdate"
        (ok {:result (so/sort-by-field @records "dob")})))))
