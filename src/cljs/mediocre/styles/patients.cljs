(ns mediocre.styles.patients
  (:require [cljss.core :refer-macros [defstyles]]))

(defstyles patients-list []
  {:overflow-y "scroll"
   :position "relative"
   :z-index 0
   :flex-direction "column"
   :padding-top "2rem"
   :width "100%"
   :height "calc(100% - 19.1rem)"})

(defstyles patient-card [color]
  {:height "10rem"
   :background-color (:primary color)
   :border-top "2px solid gray"})

(defstyles button []
  {:height "4rem"
   :width "10rem"
   :margin-top "-1rem"})

(defstyles button-without-offset []
  {:height "4rem"
   :width "10rem"})

