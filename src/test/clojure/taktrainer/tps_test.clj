(ns taktrainer.tps-test
  (:require [clojure.test :refer :all]
            [taktrainer.tps :refer :all]))


(deftest sizes
  (is (= (find-size 0 "x6") 6))
  (is (= (find-size 0 "1,x3,2121/x5") 5)))

'(deftest load-board
  (is (= (toTPS (fromTPS "[TPS \"x6/x6/x6/x6/x6/x6 1 1\"]")) "[TPS \"x6/x6/x6/x6/x6/x6 1 1\"]")))


(deftest print-board
  (is (= {:board [[[] [] []]
                  [[] [] []]
                  [[] [] []]]
          :size 3}
         (fromTPS "[TPS \"x3/x3/x3 1 1\"]"))))





