(defproject mediocre "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs "2.11.4"]
                 [reagent "0.10.0"]
                 [re-frame "1.1.1"]
                 [cljs-ajax "0.7.5"]
                 [ring/ring-json "0.5.0"]
                 [yogthos/config "1.1.7"]
                 [ring "1.8.1"]
                 [oksql "1.3.2"]
                 [honeysql "1.0.444"]
                 [funcool/clojure.jdbc "0.9.0"]
                 [camel-snake-kebab "0.4.2"]
                 [clj-commons/cljss "1.6.4"]]

  :plugins [[lein-shadow "0.3.1"]
            [lein-shell "0.5.0"]
            [lein-ring "0.12.5"]]

  :min-lein-version "2.9.0"

  :ring {:handler mediocre.handler/handler :auto-refresh true}

  :source-paths ["src/clj" "src/cljc" "src/cljs" "test"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :shadow-cljs {:nrepl {:port 8777}
                :builds {:app {:target :browser
                               :output-dir "resources/public/js/compiled"
                               :asset-path "/js/compiled"
                               :modules {:app {:init-fn mediocre.core/init
                                               :preloads [devtools.preload
                                                          re-frisk.preload]}}

                               :devtools {:http-root "resources/public"
                                          :http-port 1234}}}}

  :shell {:commands {"karma" {:windows         ["cmd" "/c" "karma"]
                              :default-command "karma"}
                     "open"  {:windows         ["cmd" "/c" "start"]
                              :macosx          "open"
                              :linux           "xdg-open"}}}

  :aliases {"dev"          ["do"
                            ["shell" "echo" "\"DEPRECATED: Please use lein watch instead.\""]
                            ["watch"]]
            "watch"        ["with-profile" "dev" "do"
                            ["shadow" "watch" "app" "browser-test" "karma-test"]]

            "prod"         ["do"
                            ["shell" "echo" "\"DEPRECATED: Please use lein release instead.\""]
                            ["release"]]

            "release"      ["with-profile" "prod" "do"
                            ["shadow" "release" "app"]]

            "build-report" ["with-profile" "prod" "do"
                            ["shadow" "run" "shadow.cljs.build-report" "app" "target/build-report.html"]
                            ["shell" "open" "target/build-report.html"]]

            "karma"        ["do"
                            ["shell" "echo" "\"DEPRECATED: Please use lein ci instead.\""]
                            ["ci"]]
            "ci"           ["with-profile" "prod" "do"
                            ["shadow" "compile" "karma-test"]
                            ["shell" "karma" "start" "--single-run" "--reporters" "junit,dots"]]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "1.0.2"]
                   [re-frisk "1.3.4"]]
    :source-paths ["dev"]}

   :prod {}

   :uberjar {:source-paths ["env/prod/clj"]
             :omit-source  true
             :main         mediocre.server
             :aot          [mediocre.server]
             :uberjar-name "mediocre.jar"
             :prep-tasks   ["compile" ["prod"]]}}

  :prep-tasks [])
