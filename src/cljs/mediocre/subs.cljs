(ns mediocre.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::theme-colors
 #(-> % :theme :color))

(rf/reg-sub
 ::locale
 :locale)

(rf/reg-sub
 ::menu-active?
 #(:menu-active? %))

(rf/reg-sub
 ::edit-form-active?
 #(:edit-form-active? %))

(rf/reg-sub
 ::edit-form
 :edit-form)

(rf/reg-sub
 ::patients
 :patients)

(rf/reg-sub
 ::add-patient-form-active?
 #(:add-patient-form-active? %))
