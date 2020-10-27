(ns mediocre.styles.main
  (:require [cljss.core :refer-macros [inject-global defstyles]]
            [clojure.string :as str]))

(defn generate-global-styles []
  (inject-global
   {:html {:font-size "62.5%"
           :height "100%"}

    :body {:font-size "1.6em"
           :height "100%"}

    "#app" {:height "100%"}

    "label, p" {:min-height "0vw"}

    "h1, p" {:margin-left "1%"}
    "p" {:font-size "2.6rem"}
    "label" {:font-size "2.2rem"}}))

(defstyles wrapper [color]
  {:color (:font color)
   :height "100%"})

(defstyles route-heading [color]
  {:color (:secondary color)
   :position "relative"
   :z-index 1
   :height "5rem"})

(defstyles drop-shadow []
  {:box-shadow "0px 11px 12px 0px rgba(179,179,179,1)"})

(defn names [& classes]
  (str/join " " classes))
