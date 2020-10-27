(ns mediocre.views.edit
  (:require [re-frame.core :as rf]
            [mediocre.styles.menu :as menu-styles]
            [mediocre.styles.patients :as patients-styles]
            [mediocre.styles.edit :as styles]
            [mediocre.subs :as subs]
            [mediocre.events :as events]))

(defn input [label-kw]
  (let [locale @(rf/subscribe [::subs/locale])
        edit-form @(rf/subscribe [::subs/edit-form])]
    [:<>
     [:label {:class (styles/label)} (label-kw locale)]
     [:input {:value (label-kw edit-form)
              :on-change #(rf/dispatch [::events/set-edit-form-field
                                        label-kw
                                        (-> % .-target .-value)])}]]))

(defn form []
  (let [locale @(rf/subscribe [::subs/locale])
        styles (styles/form)
        edited-patient @(rf/subscribe [::subs/edit-form])
        add-patient-form-active? @(rf/subscribe [::subs/add-patient-form-active?])]
    [:form {:class styles}
     [input :last-name]
     [input :first-name]
     [input :middle-name]
     [input :gender]
     [input :birth-date]
     [input :address]
     [input :policy]

     [:br]
     [:br]

     [:button {:class (patients-styles/button-without-offset)
               :on-click (fn [e]
                           (.preventDefault e)
                           (rf/dispatch
                            (if add-patient-form-active?
                              [::events/switch-add-patient-form]
                              [::events/switch-edit-form])))}
      (:back locale)]

     [:button {:class (patients-styles/button-without-offset)
               :on-click (fn [e]
                           (.preventDefault e)
                           (rf/dispatch
                            (if add-patient-form-active?
                              [::events/put-patient edited-patient locale]
                              [::events/patch-patient edited-patient locale])))}
      (:ok locale)]]))

(defn view []
  (let [color @(rf/subscribe [::subs/theme-colors])
        styles (menu-styles/menu color)]
    [:div {:class styles} [form]]))
