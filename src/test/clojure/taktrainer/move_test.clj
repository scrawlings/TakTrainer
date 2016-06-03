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


(deftest simple-slides
  (is (= (make-move (fromPTN "4a2>112") (fromTPS "[TPS \"x4/1112,x3/x4/x4 2 7\"]"))
         (fromTPS "[TPS \"x4/x,1,1,12/x4/x4 1 8\"]")))
  (is (= (make-move (fromPTN "3a2+12") (fromTPS "[TPS \"x4/1112,x3/x4/x4 2 7\"]"))
         (fromTPS "[TPS \"x4/1,x3/1,x3/12,x3 1 8\"]")))
  (is (= (make-move (fromPTN "3a2+12") (fromTPS "[TPS \"x4/1112,x3/2,x3/x4 2 7\"]"))
         (fromTPS "[TPS \"x4/1,x3/21,x3/12,x3 1 8\"]")))
  (is (= (make-move (fromPTN "a1>") (fromTPS "[TPS \"2,x2/x3/x3 2 7\"]"))
         (fromTPS "[TPS \"x,2,x/x3/x3 1 8\"]"))))
