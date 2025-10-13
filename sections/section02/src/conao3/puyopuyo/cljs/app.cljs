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
                   [4 4 0 0 5 5]]

   :falling-speed 6})

(defn get-puyo-info [board x y]
  (let [rows (-> board count)
        cols (-> board first count)]
    (cond
      (or (< x 0) (<= cols x) (<= rows y)) -1
      (< y 0) nil
      :else (-> board (get x) (get y)))))

(defn DebugPanel [request-id frame last-timestamp delta-frame fps game-state]
  [:div {:style {:position :absolute
                 :top 0
                 :background :white
                 :width "200px"
                 :border "1px solid"}}
   [:div "request-id: " @request-id]
   [:div "frame: " @frame]
   [:div "last-timestamp: " @last-timestamp]
   [:div "delta-frame: " @delta-frame]
   [:div "fps: " @fps]
   [:div "game-state: " @game-state]])

(defn GameFrame [debug game-state frame delta-frame & children]
  (r/with-let [request-id (r/atom nil)
               last-timestamp (r/atom (.now js/performance))
               fps (r/atom nil)
               callback (fn callback [timestamp]
                          (swap! frame inc)
                          (let [delta (- timestamp @last-timestamp)]
                            (reset! delta-frame (.toFixed delta 2))
                            (reset! fps (.toFixed (/ 1000 delta) 2)))
                          (when (= @game-state :start)
                            (reset! game-state :check-falling-puyo))
                          (reset! last-timestamp timestamp)
                          (reset! request-id (js/requestAnimationFrame callback)))
               _ (reset! request-id (js/requestAnimationFrame callback))]
    (if debug
      [:div {:style {:display :relative}}
       (into [:div] children)
       [DebugPanel request-id frame last-timestamp delta-frame fps game-state]]
      (into [:div] children))
    (finally
      (when @request-id
        (js/cancelAnimationFrame @request-id)))))

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
            (keep (fn [[y x elm]]
                    (when elm
                      ^{:key (str y "_" x)}
                      [:img
                       {:src (str "sega-img/puyo_" elm ".png")
                        :width (-> config :puyo-image-width)
                        :height (-> config :puyo-image-height)
                        :style {:position :absolute
                                :left (str (* (-> config :puyo-image-width) x) "px")
                                :top (str (* (-> config :puyo-image-height) y) "px")}}]))))])))

(defn App []
  (r/with-let [game-state (r/atom :start)
               frame (r/atom 0)
               delta-frame (r/atom nil)]
    [GameFrame true game-state frame delta-frame
     [:div {:style {:display :flex
                    :justify-content :center}}
      [Stage]]]))
