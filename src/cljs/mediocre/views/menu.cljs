(ns mediocre.views.menu
  (:require [re-frame.core :as rf]
            [mediocre.styles.menu :as styles]
            [mediocre.subs :as subs]
            [mediocre.events :as events]
            ["react-router-dom" :refer (Link)]))

(defn link [path label]
  [:li
   [:> Link {:to path
             :on-click #(rf/dispatch [::events/switch-menu])}
    label]])

(defn view []
  (let [locale @(rf/subscribe [::subs/locale])
        color @(rf/subscribe [::subs/theme-colors])
        styles (styles/menu color)]
    [:div {:class styles}
     [:ul
      [link "/" (:home locale)]
      [link "/patients" (:patients locale)]
      [link "/themes" (:themes locale)]
      [link "/locales" (:locales locale)]]]))
