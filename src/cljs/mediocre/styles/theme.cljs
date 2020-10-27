(ns mediocre.styles.theme)

(defrecord Color [primary secondary background font])
(defrecord Theme [name color])

(def default-color (Color. "#F4F4F4" "#FF577F" "#FFFFFF" "#494949"))

(def default (Theme. "Standard" default-color))

(def dark-color (Color. "#303030" "#C60552" "#000000" "#FFFFFF"))

(def dark (Theme. "Dark" dark-color))
