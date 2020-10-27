(ns mediocre.styles.menu
  (:require [cljss.core :refer-macros [defstyles]]))

(defstyles menu [color]
  {:position "fixed"
   :width "100%"
   :height "100%"
   :margin-top "10rem"
   :overflow "scroll"
   :z-index 9999

   :opacity 0.95
   :background-color (:background color)

   "li" {:font-size "2.6rem"}})
