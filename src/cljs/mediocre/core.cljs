(ns mediocre.core
  (:require [cljss.core :as css]
            [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [mediocre.events :as events]
            [mediocre.views :as views]
            [mediocre.config :as config]
            [mediocre.styles.main :as styles]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (css/remove-styles!)
  (styles/generate-global-styles)
  (rf/clear-subscription-cache!)
  (rf/dispatch-sync [::events/initialize-db])
  (rf/dispatch [::events/get-patients])
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (dev-setup)
  (mount-root))
