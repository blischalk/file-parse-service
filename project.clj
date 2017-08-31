(defproject file-parse-service "0.1.0-SNAPSHOT"
  :description "An application to parse data files in multiple formats
  and output sorted by different fields."
  :url "http://github.com/blischalk/file-parse-service"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[cheshire "5.8.0"]
                 [clj-time "0.14.0"]
                 [metosin/compojure-api "1.1.10"]
                 [metosin/ring-swagger-ui "2.2.10"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [ring "1.6.1"]]
  :main ^:skip-aot file-parse-service.core
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[ring/ring-mock "0.3.1"]
                                  [speclj "3.3.0"]]}
             :uberjar {:aot :all}}
  :plugins [[lein-bikeshed "0.4.1"]
            [speclj "3.3.0"]]
  :test-paths ["spec"])
