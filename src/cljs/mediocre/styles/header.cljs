(ns mediocre.styles.header
  (:require [cljss.core :refer-macros [defstyles]]))

(defstyles header [color]
  {:display "flex"
   :background-color (:primary color)
   :height "10rem"
   :width "100%"

   "div" {:display "flex"
          :justify-content "space-between"
          :margin "auto"
          :width "90%"}
   "div img" {:object-fit "contain"}})
