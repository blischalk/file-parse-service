(defproject file-parse-service "0.1.0-SNAPSHOT"
  :description "An application to parse data files in multiple formats
  and output sorted by different fields."
  :url "http://github.com/blischalk/file-parse-service"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot file-parse-service.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
