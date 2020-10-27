(ns mediocre.events
  (:require [re-frame.core :as rf]
            [ajax.core :refer [GET PUT PATCH DELETE]]
            [clojure.string :as str]
            [cljs-time.coerce :as tc]
            [mediocre.db :as db]
            [mediocre.common.data.patients :as patients]))

(rf/reg-event-db
 ::initialize-db
 (fn [] db/default))

(rf/reg-event-db
 ::switch-menu
 #(update % :menu-active? not))

(rf/reg-event-db
 ::switch-edit-form
 #(update % :edit-form-active? not))

(rf/reg-event-db
 ::reset-edit-form
 #(assoc % :edit-form patients/empty-patient))

(rf/reg-event-db
 ::set-edit-form
 (fn [db [_ edit-form]]
   (assoc db :edit-form edit-form)))

(defn validate-number [value]
  (str/replace value #"[^0-9]" ""))

(defn validate-date [value]
  (str/replace value #"[^0-9-]" ""))

(defn validate-form-field [label value]
  (case label
    :policy (validate-number value)
    :birth-date (validate-date value)
    value))

(rf/reg-event-db
 ::set-edit-form-field
 (fn [db [_ label value]]
   (assoc db
          :edit-form
          (assoc (:edit-form db) label (validate-form-field label value)))))

(rf/reg-event-db
 ::switch-add-patient-form
 #(update % :add-patient-form-active? not))

;; HTTP

(def base-url "http://localhost:3000/")

(defn url [path]
  (str base-url path))

(def patients-url (url "patients"))

(defn event->handler [event]
  (fn [response]
    (rf/dispatch [event (js->clj response)])))

(defn as-body [data]
  (.stringify js/JSON (clj->js data)))

(defn body [body]
  {:body (as-body body)
   :headers {"Content-Type" "application/json;charset=UTF-8"}
   :handler #(rf/dispatch [::get-patients])})

(rf/reg-event-db
 ::get-patients
 (fn [db]
   (GET patients-url
        {:handler (event->handler ::handle-patients)
         :response-format :json
         :keywords? true})
   db))

(rf/reg-event-db
 ::handle-patients
 (fn [db [_ response]]
   (assoc db :patients (map patients/map->Patient response))))

(defn refine-patient [patient]
  (-> patient
      (assoc :first-name (str/trim (:first-name patient)))
      (assoc :last-name (str/trim (:last-name patient)))
      (assoc :middle-name (str/trim (:middle-name patient)))
      (assoc :gender (str/trim (:gender patient)))
      (assoc :birth-date (str/trim (:birth-date patient)))
      (assoc :address (str/trim (:address patient)))))

(defn validate-patient [patient locale]
  (let [birth-date (tc/from-string (str (:birth-date patient) "T00:00:00.000Z"))
        errors (concat (when (= "" (:first-name patient)) [(str (:first-name locale) " " (:error-empty locale))])
                       (when (= "" (:last-name patient)) [(str (:last-name locale) " " (:error-empty locale))])
                       (when (= "" (:gender patient)) [(str (:gender locale) " " (:error-empty locale))])
                       (when-not birth-date [(str "'" (:birth-date patient) "' " (:error-date locale))])
                       (when (= "" (:birth-date patient)) [(str (:birth-date locale) " " (:error-empty locale))])
                       (when (= "" (:address patient)) [(str (:address locale) " " (:error-empty locale))])
                       (when-not (= (count (:policy patient)) 16) [(:error-policy locale)]))]
    (if (empty? errors)
      patient
      (js/alert (str/join "\n" errors)))))

(rf/reg-event-db
 ::put-patient
 (fn [db [_ patient locale]]
   (let [validated-patient (-> patient refine-patient (validate-patient locale))]
     (when validated-patient
       (PUT patients-url (body validated-patient))))
   db))

(rf/reg-event-db
 ::patch-patient
 (fn [db [_ patient-diff locale]]
   (let [validated-patient (-> patient-diff refine-patient (validate-patient locale))]
     (when validated-patient
       (PATCH patients-url (body validated-patient))))
   db))

(rf/reg-event-db
 ::delete-patient
 (fn [db [_ uid]]
   (DELETE patients-url (body {:uid uid}))
   db))

;; Themes and Locales

(rf/reg-event-db
 ::set-theme
 (fn [db [_ theme]]
   (assoc db :theme theme)))

