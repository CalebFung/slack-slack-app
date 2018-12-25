(ns slack-slack-app.core
    (:require [compojure.core :refer :all]
              [compojure.route :as route]
              [compojure.handler :as handler]
              [ring.middleware.json :as middleware]
              [org.httpkit.server :refer [run-server]]))

(defroutes app-routes
  (POST "/events" request 
    (let [challenge (get-in request [:body :challenge])]
        {:status 200
         :body {:challenge challenge}}))
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> (handler/site app-routes)
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response))

(defn -main []
  (run-server app {:port 9431})
  (println "Server started on port 9431"))