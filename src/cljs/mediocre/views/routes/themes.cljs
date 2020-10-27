(ns mediocre.views.routes.themes
  (:require [re-frame.core :as rf]
            [mediocre.subs :as subs]
            [mediocre.events :as events]
            [mediocre.styles.theme :as themes]))

(defn view []
  (let [locale @(rf/subscribe [::subs/locale])]
    [:div
     [:h1 (:choose-theme locale)]
     [:button {:on-click #(rf/dispatch [::events/set-theme themes/default])} (:standard-theme locale)]
     [:button {:on-click #(rf/dispatch [::events/set-theme themes/dark])} (:dark-theme locale)]]))
