(ns taktrainer.ptn-test
    (:require [clojure.test :refer :all]
      [taktrainer.ptn :refer :all]))

(deftest parse-ptn-piece-placements
  (is (= (fromPTN "Sc2") {:move :place :at [:c 2] :piece :S}))
  (is (= (fromPTN "d3") {:move :place :at [:d 3] :piece :F}))
  (is (= (fromPTN "Cb1") {:move :place :at [:b 1] :piece :C})))


(deftest parse-ptn-slides
  (is (= (fromPTN "6c2+123") {:move :slide :from [:c 2] :direction :+ :pieces 6 :partition [1 2 3]}))
  (is (= (fromPTN "3c2<") {:move :slide :from [:c 2] :direction :< :pieces 3 :partition [3]}))
  (is (= (fromPTN "c2<") {:move :slide :from [:c 2] :direction :< :pieces :max :partition [:remaining]})))
