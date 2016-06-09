(ns taktrainer.victory-test
  (:require [clojure.test :refer :all]
            [taktrainer.victory :refer :all]
            [taktrainer.move :refer :all]
            [taktrainer.tps :refer :all]
            [taktrainer.ptn :refer :all] ))

(deftest flats-one-player-runs-out-no-roads
  (is (= :1 (victory (fromTPS "[TPS \"111,222,111/111,222,1/x3 2 7\"]"))))
  (is (= :2 (victory (fromTPS "[TPS \"x,222,111/111,222,222/2,x2 2 7\"]")))))

(deftest flats-game-not-over-no-roads
  (is (= :neither (victory (fromTPS "[TPS \"111,222,11/111,222,1/x3 2 7\"]")))))

(deftest flats-last-move-wins-no-roads
  (is (= :1 (victory (fromTPS "[TPS \"111,222,111/111,222,222/2,1,x 2 7\"]")))))

(deftest roads-left-to-right)

(deftest roads-top-to-bottom)

(deftest roads-beat-flats-as-it-matters-for-scoring)