(ns mediocre.locales.en
  (:require [mediocre.tools.locale :refer [map->Locale]]))

(def fields
  {:patients "Patients"
   :home "Home"
   :home-content "This is Mediocre app, it's a CRUD that allows people to work with patients.
                  It supports locales and themes.
                  Please, tap on the top right button to proceed"
   :themes "Themes"
   :locales "Locales"
   :first-name "First name"
   :last-name "Last name"
   :middle-name "Middle name"
   :gender "Gender"
   :birth-date "Birth date"
   :address "Address"
   :policy "Policy"
   :edit "Edit"
   :delete "Delete"
   :add-patient "Add patient"
   :ok "OK"
   :back "Back"
   :choose-theme "Choose theme"
   :standard-theme "Standard"
   :dark-theme "Dark"
   :error-empty "can't be empty"
   :error-date "is not a valid date"
   :error-policy "Policy should have 16 digits and be in the format yyyy-mm-dd"})

(def locale (map->Locale fields))
