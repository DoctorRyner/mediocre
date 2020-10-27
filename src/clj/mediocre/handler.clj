(ns mediocre.handler
  (:require
    ;; [compojure.core :refer [GET PUT PATCH DELETE defroutes]]
    ;; [compojure.route :refer [resources]]
    [camel-snake-kebab.core :as csk]
    [camel-snake-kebab.extras :as cske]
    [mediocre.db.patients :as patients]
    [mediocre.db :as db]
    [ring.util.response :refer [response]]
    [ring.middleware.reload :refer [wrap-reload]]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
    [shadow.http.push-state :as push-state]))

;; Utils

(defn kebab [x]
  (cske/transform-keys csk/->kebab-case-keyword x))

;; CORS

(defn wrap-cors [handler]
  (fn [request]
    (let [response (handler request)]
      (-> response
          (assoc-in [:headers "Access-Control-Allow-Origin"] (get-in request [:headers "origin"]))
          (assoc-in [:headers "Access-Control-Allow-Headers"] "x-requested-with, content-type")
          (assoc-in [:headers "Access-Control-Allow-Methods"] "*")))))

;; Routes



(defn getPatients [conn _]
  (response (kebab (patients/select conn))))

;; (defn putPatients [conn]
;;   (PUT "/patients"
;;        []
;;        (fn [{body :body}]
;;          (when-let [_ (patients/insert conn (dissoc (kebab body) :uid))]
;;            {:status 200}))))

;; (defn patchPatients [conn]
;;   (PATCH "/patients"
;;          []
;;          (fn [{body :body}]
;;            (when-let [_ (patients/update conn (kebab body))]
;;              {:status 200}))))

;; (defn deletePatients [conn]
;;   (DELETE "/patients"
;;           []
;;           (fn [{body :body}]
;;             (when-let [_ (patients/delete conn (:uid body))]
;;               {:statis 200}))))

(defn routing [request conn]
  (case request
    {:request-method :get :uri "/patients"} (getPatients conn request)
    {:request-method :put :uri "/patients"} (getPatients conn request)))

(comment
  (routing {:request-method :get :uri "/patients"} db/conn))

;; (defroutes routes
;;   (getPatients db/conn)
;;   (putPatients db/conn)
;;   (patchPatients db/conn)
;;   (deletePatients db/conn)
;;   (resources "/"))

;; (defn gen-routes [connection]
;;   (defroutes routes
;;     (getPatients connection)
;;     (putPatients connection)
;;     (patchPatients connection)
;;     (deletePatients connection)
;;     (resources "/")))

;; (def dev-handler (-> #'routes wrap-reload push-state/handle))

;; (def handler (-> (gen-routes db/conn)
;;                  (wrap-json-body {:keywords? true})
;;                  wrap-json-response
;;                  wrap-cors))
