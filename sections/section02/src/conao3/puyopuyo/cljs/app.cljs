(ns conao3.puyopuyo.cljs.app)

(def config
  {:puyo-image-width 40
   :puyo-image-height 40
   :stage {:cols 6
           :rows 12
           :background-color "#11213b"}})

(defn Stage []
  [:div {:style {:width (str (* (-> config :puyo-image-width) (-> config :stage :cols)) "px")
                 :height (str (* (-> config :puyo-image-height) (-> config :stage :rows)) "px")
                 :background "url(sega-img/puyo_2bg.png)"
                 :background-color (-> config :stage :background-color)}}])

(defn App []
  [Stage])
