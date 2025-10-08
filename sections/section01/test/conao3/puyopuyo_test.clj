(ns conao3.puyopuyo-test
  (:require
   [clojure.test :as t]
   [conao3.puyopuyo :as c.puyopuyo]))

(t/deftest add-test
  (t/is (= 3 (c.puyopuyo/add 1 2))))
