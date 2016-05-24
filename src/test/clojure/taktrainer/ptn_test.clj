(ns taktrainer.ptn-test
    (:require [clojure.test :refer :all]
      [taktrainer.ptn :refer :all]))

(deftest parse-ptn-piece-placements
  (is (= (fromPTN "Sc2") {:move :place :at [:c 2] :piece :S}))
  (is (= (fromPTN "d3") {:move :place :at [:d 3] :piece :F}))
  (is (= (fromPTN "Cb1") {:move :place :at [:b 1] :piece :C})))


(deftest parse-ptn-slides
  (is (= (fromPTN "6c2+123") {:move :slide :from [:c 2] :direction :+ :pieces 6 :partition [1 2 3]}))
  (is (= (fromPTN "3c2<") {:move :slide :from [:c 2] :direction :< :pieces 3 :partition :all}))
  (is (= (fromPTN "c2<") {:move :slide :from [:c 2] :direction :< :pieces :max :partition :all})))


(deftest print-ptn-piece-placements
  (is (= (toPTN {:move :place :at [:c 2] :piece :S}) "Sc2"))
  (is (= (toPTN {:move :place :at [:d 3] :piece :F}) "d3"))
  (is (= (toPTN {:move :place :at [:b 1] :piece :C}) "Cb1")))


(deftest print-ptn-slides
  (is (= (toPTN {:move :slide :from [:c 2] :direction :+ :pieces 6 :partition [1 2 3]}) "6c2+123"))
  (is (= (toPTN {:move :slide :from [:c 2] :direction :< :pieces 3 :partition :all}) "3c2<"))
  (is (= (toPTN {:move :slide :from [:c 2] :direction :< :pieces :max :partition :all}) "c2<")))
