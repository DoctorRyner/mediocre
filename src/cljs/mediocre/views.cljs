(ns mediocre.views
  (:require [mediocre.styles.main :as styles :refer [names]]
            [mediocre.subs :as subs]
            [mediocre.views.edit :as edit]
            [mediocre.views.header :as header]
            [mediocre.views.menu :as menu]
            [mediocre.views.routes.home :as home-route]
            [mediocre.views.routes.patients :as patients-route]
            [mediocre.views.routes.themes :as themes-route]
            [re-frame.core :as rf]
            [react-router-dom :refer (Route Switch BrowserRouter)]))

(defn route-heading [label]
  (let [color @(rf/subscribe [::subs/theme-colors])
        styles (names (styles/route-heading color)
                      (styles/drop-shadow))]
    [:div {:class styles}
     [:h1 label]]))

(defn router []
  (let [locale @(rf/subscribe [::subs/locale])]
    [:> Switch
     [:> Route {:path "/" :exact true}
      [home-route/view]]
     [:> Route {:path "/patients/"}
      [route-heading (:patients locale)]
      [patients-route/view]]
     [:> Route {:path "/locales/"}
      [route-heading (:locales locale)]]
     [:> Route {:path "/themes/"}
      [route-heading (:themes locale)]
      [themes-route/view]]]))

(defn main-panel []
  (let [color @(rf/subscribe [::subs/theme-colors])
        menu-active? @(rf/subscribe [::subs/menu-active?])
        edit-form-active? @(rf/subscribe [::subs/edit-form-active?])
        add-patient-form-active? @(rf/subscribe [::subs/add-patient-form-active?])]
    [:> BrowserRouter
     [:div {:class (styles/wrapper color)}
      (when menu-active? [menu/view])
      (when (or edit-form-active? add-patient-form-active?) [edit/view])
      [header/view]
      [router]]]))
