(ns mediocre.db
  (:require [mediocre.styles.theme :as theme]
            [mediocre.common.data.patients :as patients]
            [mediocre.locales.en :as en]))

(def default
  {:theme theme/default
   :locale en/locale
   :menu-active? false
   :edit-form-active? false
   :edit-form (dissoc patients/empty-patient :uid)
   :add-patient-form? false
   :patients []})
