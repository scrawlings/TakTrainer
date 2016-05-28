(ns taktrainer.tps-test
  (:require [clojure.test :refer :all]
            [taktrainer.tps :refer :all]))


(deftest sizes
  (is (= (find-size 0 "x6") 6))
  (is (= (find-size 0 "1,x3,2121/x5") 5)))

(deftest load-board-empty
  (is (= (toTPS (fromTPS "[TPS \"x6/x6/x6/x6/x6/x6 1 7\"]")) "[TPS \"x6/x6/x6/x6/x6/x6 1 7\"]")))



(deftest load-board-empty
  (is (= (toTPS {:board [[[] [:1C] []]
                         [[] [:1 :2 :2] []]
                         [[] [] [:2S]]]
                 :move 33
                 :turn :2
                 :size 3})
         "[TPS \"x,1C,x/x,122,x/x2,2S 2 33\"]")))

(deftest print-board-small
  (is (= {:board [[[] [] []]
                  [[] [:1 :2] [:2 :1S]]
                  [[] [] []]]
          :move 13
          :turn :2
          :size 3}
         (fromTPS "[TPS \"x3/x1,12,21S/x,x2 2 13\"]"))))


(deftest print-board-large
  (is (= {:board [[[] [] [] [] [] []]
                  [[] [] [] [] [] []]
                  [[] [] [] [] [] []]
                  [[] [] [] [] [] []]
                  [[] [] [] [] [] []]
                  [[] [] [] [] [] []]]
          :move 7
          :turn :1
          :size 6}
         (fromTPS "[TPS \"x6/x6/x6/x6/x6/x6 1 7\"]"))))

