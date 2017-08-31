(defproject file-parse-service "0.1.0-SNAPSHOT"
  :description "An application to parse data files in multiple formats
  and output sorted by different fields."
  :url "http://github.com/blischalk/file-parse-service"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]]
  :main ^:skip-aot file-parse-service.core
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[speclj "3.3.0"]]}
             :uberjar {:aot :all}}
  :plugins [[lein-bikeshed "0.4.1"]
            [speclj "3.3.0"]]
  :test-paths ["spec"])
