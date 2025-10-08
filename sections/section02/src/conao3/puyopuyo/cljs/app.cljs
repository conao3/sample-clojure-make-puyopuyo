(ns conao3.puyopuyo.cljs.app
  (:require
   [clojure.walk :as clojure.walk]
   [reagent.core :as r]))

(def config
  {:puyo-image-width 40
   :puyo-image-height 40
   :stage {:cols 6
           :rows 12
           :background-color "#11213b"}

   :initial-board [[0 1 2 3 4 5]
                   [1 2 3 4 5 0]
                   [2 3 4 5 0 1]
                   [0 0 0 0 0 0]
                   [0 1 1 1 2 0]
                   [0 4 5 5 2 0]
                   [0 4 5 5 2 0]
                   [0 4 3 3 3 0]
                   [0 0 0 0 0 0]
                   [1 1 0 0 2 2]
                   [0 0 2 2 0 0]
                   [4 4 0 0 5 5]]})

(defn Stage []
  (let [puyo-board (r/atom (->> (config :initial-board)
                                (clojure.walk/postwalk (fn [elm] (if (zero? elm) nil elm)))))
        puyo-count (r/atom (->> (for [row @puyo-board item row] item) (filter some?) count))]
    (fn []
      [:div {:style {:width (str (* (-> config :puyo-image-width) (-> config :stage :cols)) "px")
                     :height (str (* (-> config :puyo-image-height) (-> config :stage :rows)) "px")
                     :background "url(sega-img/puyo_2bg.png)"
                     :background-color (-> config :stage :background-color)
                     :position :relative}}
       (->> (for [[y row] (map-indexed vector @puyo-board)
                  [x elm] (map-indexed vector row)]
              [y x elm])
            (map (fn [[y x elm]]
                   (when elm
                     [:img
                      {:key (str y "_" x)
                       :src (str "sega-img/puyo_" elm ".png")
                       :width (-> config :puyo-image-width)
                       :height (-> config :puyo-image-height)
                       :style {:position :absolute
                               :left (str (* (-> config :puyo-image-width) x) "px")
                               :top (str (* (-> config :puyo-image-height) y) "px")}}]))))])))

(defn App []
  [Stage])
