(ns taktrainer.move-test
  (:require [clojure.test :refer :all]
            [taktrainer.move :refer :all]
            [taktrainer.tps :refer :all]
            [taktrainer.ptn :refer :all]))

(deftest special-place
  (is (= "[TPS \"2S,x/x2 1 8\"]"
         (toTPS (make-move (fromPTN "Sa1") (fromTPS "[TPS \"x2/x2 2 7\"]"))))))

(deftest special-place
   (is (= {:board [[[:2C] []]
                   [[] []]]
           :move 8
           :turn :1
           :size 2}
          (make-move (fromPTN "Ca1") (fromTPS "[TPS \"x2/x2 2 7\"]")))))

(deftest simple-place
  (is (= {:board [[[] [] []]
                  [[] [:1] []]
                  [[] [] []]]
          :move 1
          :turn :2
          :size 3}
         (make-move (fromPTN "b2") (fromTPS "[TPS \"x3/x3/x3 1 1\"]")))))