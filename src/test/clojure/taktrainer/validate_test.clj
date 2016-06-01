(ns taktrainer.validate-test
  (:require [clojure.test :refer :all]
            [taktrainer.move :refer :all]
            [taktrainer.tps :refer :all]
            [taktrainer.ptn :refer :all]))

(deftest validate-placement
  (is (true?  (validate-move (fromPTN "Ca1") (fromTPS "[TPS \"x2/x2 2 7\"]"))))
  (is (false? (validate-move (fromPTN "Ca1") (fromTPS "[TPS \"1,x/x2 2 7\"]")))))



(deftest validate-slide-simple
  (is (true? (validate-move (fromPTN "3a1>21") (fromTPS "[TPS \"1111,x2/x3/x3 2 7\"]"))))
  (is (true? (validate-move (fromPTN "3a1>21") (fromTPS "[TPS \"111,1,2/x3/x3 2 7\"]")))))




(deftest validate-slide-fail
  (is (false? (validate-move (fromPTN "3a1>111") (fromTPS "[TPS \"1111,x2/x3/x3 2 7\"]")))))