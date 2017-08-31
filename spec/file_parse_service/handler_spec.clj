(ns file-parse-service.handler-spec
  (:require [cheshire.core :as cheshire]
            [file-parse-service.handler :refer :all]
            [ring.mock.request :as mock]
            [speclj.core :refer :all]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))


(describe "/records"
  (describe "/gender"
    (let [response (app (-> (mock/request :get  "/records/gender")))
          body     (parse-body (:body response))]
      (it "reports success"
        (should= (:status response) 200)))))
