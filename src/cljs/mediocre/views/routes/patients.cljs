(ns mediocre.views.routes.patients
  (:require [re-frame.core :as rf]
            [mediocre.styles.patients :as styles]
            [mediocre.styles.main :as main-styles :refer [names]]
            [mediocre.subs :as subs]
            [mediocre.events :as events]))

(defn mk-initials-label [patient]
  (let [middle-name (:middle-name patient)]
    (str (:last-name patient)
         " "
         (first (:first-name patient))
         ". "
         (when (not-empty middle-name)
           (str (first middle-name) ".")))))

(defn render-patient [patient]
  (let [color @(rf/subscribe [::subs/theme-colors])
        locale @(rf/subscribe [::subs/locale])
        styles (names (styles/patient-card color)
                      (main-styles/drop-shadow))
        name (mk-initials-label patient)]
    [:div {:key (:uid patient)
           :class styles}
     [:p name]
     [:button {:class (styles/button)
               :on-click (fn []
                           (rf/dispatch [::events/switch-edit-form])
                           (rf/dispatch [::events/set-edit-form patient]))}
      (:edit locale)]
     [:button {:class (styles/button)
               :on-click #(rf/dispatch [::events/delete-patient (:uid patient)])}
      (:delete locale)]]))

(defn view []
  (let [locale @(rf/subscribe [::subs/locale])
        patients @(rf/subscribe [::subs/patients])]
    [:div {:class (styles/patients-list)}
     [:<>
      [:button {:class (styles/button)
                :on-click (fn []
                            (rf/dispatch [::events/reset-edit-form])
                            (rf/dispatch [::events/switch-add-patient-form]))} (:add-patient locale)]
      (map render-patient patients)]]))
