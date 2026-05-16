(ns ^:figwheel-hooks conao3.puyopuyo.cljs.puyopuyo
  (:require
   [conao3.puyopuyo.cljs.app :as c.cljs.app]
   [reagent.dom.client :as reagent.dom.client]))

(enable-console-print!)

(defn ^:after-load init []
  (let [root (-> js/document (.getElementById "app") reagent.dom.client/create-root)]
    (reagent.dom.client/render root [c.cljs.app/App])))

(defonce initialized (do (init) true))
