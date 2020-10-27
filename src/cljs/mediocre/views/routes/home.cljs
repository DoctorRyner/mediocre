(ns mediocre.views.routes.home
  (:require [re-frame.core :as rf]
            [mediocre.subs :as subs]))

(defn view []
  (let [locale @(rf/subscribe [::subs/locale])]
    [:p (:home-content locale)]))
