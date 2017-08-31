(ns file-parse-service.handler-spec
  (:require [cheshire.core :as cheshire]
            [file-parse-service.handler :refer :all]
            [file-parse-service.parse :as p]
            [file-parse-service.sort :as so]
            [file-parse-service.specutil :as su]
            [ring.mock.request :as mock]
            [speclj.core :refer :all]))

(defn ^:private parse-json-body [m]
  (if-let [body (:body m)]
    (update m :body (comp cheshire/parse-string slurp))
    m))

(defn ^:private sort-records->json [records field]
  (-> records
      (so/sort-by-field field)
      cheshire/generate-string
      cheshire/parse-string))


(defn mock-json-request [method endpoint data]
  (-> (mock/request method endpoint
                    (cheshire/generate-string data))
      (mock/content-type "application/json")
      app
      parse-json-body))


(describe "/records"
  (around [it]
    (with-redefs [file-parse-service.handler/records (atom su/records)]
      (it)))

  (describe "/gender"
    (with gender-request #(mock-json-request :get "/records/gender" {}))
    (it "reports success"
      (should= (:status (@gender-request)) 200))
    (it "sorts by gender (females before males), last name asc"
      (should= (sort-records->json su/records "sex")
               (get (:body (@gender-request)) "result"))))

  (describe "name"
    (with name-request #(mock-json-request :get "/records/name" {}))
    (it "reports success"
      (should= (:status (@name-request)) 200))
    (it "sorts by last name decending"
      (should= (sort-records->json su/records "lname")
               (get (:body (@name-request)) "result"))))

  (describe "birthdate"
    (with birthdate-request #(mock-json-request :get "/records/birthdate" {}))
    (it "reports success"
      (should= (:status (@birthdate-request)) 200))
    (it "sorts by birth date ascending"
      (should= (sort-records->json su/records "dob")
               (get (:body (@birthdate-request)) "result")))))
