(ns taktrainer.validate-test
  (:require [clojure.test :refer :all]
            [taktrainer.move :refer :all]
            [taktrainer.tps :refer :all]
            [taktrainer.ptn :refer :all]))


(deftest validate-slide-blocked-but-cap-flattens
  (is (true? (validate-move (fromPTN "3a1>21") (fromTPS "[TPS \"1112C,x,1S/x3/x3 2 7\"]"))))
  (is (false? (validate-move (fromPTN "3a1>21") (fromTPS "[TPS \"112C,1C,2/x3/x3 2 7\"]"))))
  (is (false? (validate-move (fromPTN "3a1>21") (fromTPS "[TPS \"1112C,x,1C/x3/x3 2 7\"]")))))

(deftest validate-placement
  (is (true?  (validate-move (fromPTN "Ca1") (fromTPS "[TPS \"x2/x2 2 7\"]"))))
  (is (false? (validate-move (fromPTN "Ca1") (fromTPS "[TPS \"1,x/x2 2 7\"]")))))

(deftest validate-slide-simple
  (is (true? (validate-move (fromPTN "3a1>21") (fromTPS "[TPS \"1112,x2/x3/x3 2 7\"]"))))
  (is (true? (validate-move (fromPTN "3a1>21") (fromTPS "[TPS \"112,1,2/x3/x3 2 7\"]")))))

(deftest control-fail
   (is (false? (validate-move (fromPTN "a1>") (fromTPS "[TPS \"1,x2/x3/x3 2 7\"]")))))

(deftest validate-slide-fail
  (is (false? (validate-move (fromPTN "3a1>111") (fromTPS "[TPS \"1112,x2/x3/x3 2 7\"]")))))

(deftest validate-slide-blocked
  (is (false? (validate-move (fromPTN "3a1>21") (fromTPS "[TPS \"1112,x,1S/x3/x3 2 7\"]"))))
  (is (false? (validate-move (fromPTN "3a1>21") (fromTPS "[TPS \"112,1C,2/x3/x3 2 7\"]")))))