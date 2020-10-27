(ns mediocre.db
  (:require [honeysql.core :as hsql]
            [jdbc.core :as jdbc]))

;; (def db {:connection-uri "jdbc:postgresql://localhost:5432/mediocre_db?user=mediocre_admin&password="})

(def db-spec {:vendor "postgresql"
              :name "mediocre_db"
              :host "localhost"
              :port 5432
              :use "mediocre_admin"
              :password ""})

(def conn (jdbc/connection db-spec))

(defn query [x]
  (jdbc/fetch conn (hsql/format x)))

(defn exec [x]
  (jdbc/execute conn (hsql/format x)))

(defn query-with [connection x]
  (jdbc/fetch connection (hsql/format x)))

(defn exec-with [connection x]
  (jdbc/execute connection (hsql/format x)))

(defn exec-raw [x]
  (jdbc/execute conn x))

(defn exec-raw-with [connection x]
  (jdbc/execute connection x))
