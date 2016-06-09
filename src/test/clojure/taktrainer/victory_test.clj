(ns taktrainer.victory-test
  (:require [clojure.test :refer :all]
            [taktrainer.victory :refer :all]
            [taktrainer.move :refer :all]
            [taktrainer.tps :refer :all]
            [taktrainer.ptn :refer :all] ))


(def possible-results [:1 :2 :neither])
;; slide makes to road then the person who mad the move wins
;; equal number of flats goes to who ever has most in hand, (logically inpossible to run out in sync)

(deftest flats
  (is (= :neither (victory (fromTPS "[TPS \"111,222,11/111,222,1/x3 2 7\"]"))))
  (is (= :1 (victory (fromTPS "[TPS \"111,222,111/111,222,1/x3 2 7\"]"))))
  (is (= :2 (victory (fromTPS "[TPS \"x,222,111/111,222,222/2,x2 2 7\"]")))))

(deftest flats-game-not-over
  (is (= :neither (victory (fromTPS "[TPS \"111,222,11/111,222,1/x3 2 7\"]")))))

(deftest flats-last-move-wins
  (is (= :1 (victory (fromTPS "[TPS \"111,222,111/111,222,222/2,1,x 2 7\"]")))))