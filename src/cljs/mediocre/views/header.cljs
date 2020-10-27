(ns mediocre.views.header
  (:require [re-frame.core :as rf]
            [mediocre.subs :as subs]
            [mediocre.events :as events]
            [mediocre.styles.header :as styles]
            [mediocre.styles.main :as main-styles :refer [names]]))

(defn logo []
  [:img {:src "img/logo.png"}])

(defn menu-switch []
  [:img {:src "img/menu-switch.png"
         :on-click #(rf/dispatch [::events/switch-menu])}])

(defn view []
  (let [color @(rf/subscribe [::subs/theme-colors])
        styles (names (styles/header color) (main-styles/drop-shadow))]
    [:header {:class styles}
     [:div
      [logo]
      [menu-switch]]]))
