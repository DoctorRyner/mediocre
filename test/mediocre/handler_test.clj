(ns mediocre.handler-test
  (:require [clojure.test :as t]
            [compojure.core :refer [defroutes]]
            [compojure.route :refer [resources]]
            [mediocre.handler :as handler]
            [mediocre.db.patients :as patients]
            [jdbc.core :as jdbc]
            [honeysql.core :as hsql]))

(def test-db-spec {:vendor "postgresql"
                   :name "mediocre_test_db"
                   :host "localhost"
                   :port 5432
                   :use "mediocre_admin"
                   :password ""})

(def conn (jdbc/connection test-db-spec))

(defn query [x]
  (jdbc/fetch conn (hsql/format x)))

(defn exec [x]
  (jdbc/execute conn (hsql/format x)))

(def victor {:last-name "von Doom"
             :first-name "Victor"
             :middle-name ""
             :gender "Male"
             :birth-date "1922-01-01"
             :address "Latveria"
             :policy "1111111111111111"})

(def routes (handler/gen-routes conn))

(defn delete-test [patched-dr-doom]
  (when-let [delete-req (routes {:request-method :delete
                                 :body {:uid (:uid patched-dr-doom)}})]
    (t/is (= (:status delete-req) 200))
    (when (nil? delete-req)
      (let [get-req (routes {:request-method :get
                             :uri "/patients"})]
        (t/is (= (:status get-req) 200))
        (t/is (empty? (:body get-req)))))))

(defn patch-test [dr-doom]
  (when-let [patch-req (routes {:request-method :patch
                                :body (assoc dr-doom :gender "Uber Techno-Mage")
                                :uri "/patients"})]
      (t/is (= (:status patch-req) 200))
      (when (nil? patch-req)
        (let [get-req (routes {:request-method :get
                               :uri "/patients"})
              patched-dr-doom (-> get-req :body first)]
          (t/is (= (:status get-req) 200))
          (t/is (= "Uber Techno-Mage" (:gender patched-dr-doom)))
          (delete-test patched-dr-doom)))))

(t/deftest handler-test
  (exec patients/truncate-query)
  (let [get-req (routes {:request-method :get
                            :uri "/patients"})]
    (t/is (= (:status get-req) 200))
    (t/is (empty? (:body get-req))))
  (routes {:request-method :put
           :body victor
           :uri "/patients"})
  (let [get-req (routes {:request-method :get
                         :uri "/patients"})
        dr-doom (-> get-req :body first)]
    (t/is (= (:status get-req) 200))
    (t/is (not (empty? (:body get-req))))
    (t/is (= "von Doom" (:last-name dr-doom)))
    (t/is (string? (:gender dr-doom)))
    (t/is (= "Male" (:gender dr-doom)))

    (patch-test dr-doom)))
