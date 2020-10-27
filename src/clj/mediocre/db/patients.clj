(ns mediocre.db.patients
  (:require [mediocre.db :refer [query-with exec-with]])
  (:refer-clojure :exclude [update]))

(def select-query {:select [:*] :from [:patients]})

(defn select [conn]
  (query-with conn select-query))

(defn insert-query [patient]
  {:insert-into :patients
   :values [patient]})

(defn insert [conn patient]
  (exec-with conn (insert-query patient)))

(defn update-query [patient]
  {:update :patients
   :set patient
   :where [:= :uid (:uid patient)]})

(defn update [conn patient]
  (exec-with conn (update-query patient)))

(defn delete-query [uid]
  {:delete-from :patients
   :where [:= :uid uid]})

(defn delete [conn uid]
  (exec-with conn (delete-query uid)))

(def truncate-query {:truncate :patients})

(defn truncate [conn]
  (exec-with conn truncate-query))

(def init-query "CREATE TABLE IF NOT EXISTS patients(
                   uid serial primary key,
                   first_name text,
                   last_name text,
                   middle_name text,
                   gender text,
                   birth_date text,
                   address text,
                   policy text
                 )")
