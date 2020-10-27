(ns mediocre.styles.edit
  (:require [cljss.core :refer-macros [defstyles]]))

(defstyles label []
  {:display "block"
   :margin-top "2rem"})

(defstyles form []
  {:overflow-y "scroll"
   :margin-left "2%"
   :height "calc(100% - 10rem)"})
