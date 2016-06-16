(ns taktrainer.move-test
  (:require [clojure.test :refer :all]
            [taktrainer.move :refer :all]
            [taktrainer.tps :refer :all]
            [taktrainer.ptn :refer :all] ))

(deftest special-place
  (is (= "[TPS \"2S,x/x2 1 8\"]"
         (toTPS (make-move (fromPTN "Sa1") (fromTPS "[TPS \"x2/x2 2 7\"]"))) )))

(deftest special-place
   (is (= {:board [[[:2S] []]
                   [[] []]]
           :move 8
           :turn :1
           :size 2
           :pieces {:1 {:C 0 :T 3}
                    :2 {:C 0 :T 2} }}
          (make-move (fromPTN "Sa1") (fromTPS "[TPS \"x2/x2 2 7\"]")) )))

(deftest simple-place-of-cap-reverse-first-moves
  (is (= {:board [[[] [:1C] [] [] []]
                  [[] [:2] [] [] []]
                  [[] [] [] [] []]
                  [[] [] [] [] []]
                  [[] [] [] [] []]]
          :move 2
          :turn :1
          :size 5
          :pieces {:1 {:C 0 :T 20}
                   :2 {:C 1 :T 19} }}
         (make-move (fromPTN "Cb1") (make-move (fromPTN "b2") (fromTPS "[TPS \"x5/x5/x5/x5/x5 1 1\"]"))) )))


(deftest simple-slides
  (is (= (make-move (fromPTN "4a2>112") (fromTPS "[TPS \"x4/1112,x3/x4/x4 2 7\"]"))
         (fromTPS "[TPS \"x4/x,1,1,12/x4/x4 1 8\"]")))
  (is (= (make-move (fromPTN "3a2+12") (fromTPS "[TPS \"x4/1112,x3/x4/x4 2 7\"]"))
         (fromTPS "[TPS \"x4/1,x3/1,x3/12,x3 1 8\"]")))
  (is (= (make-move (fromPTN "3a2+12") (fromTPS "[TPS \"x4/1112,x3/2,x3/x4 2 7\"]"))
         (fromTPS "[TPS \"x4/1,x3/21,x3/12,x3 1 8\"]")))
  (is (= (make-move (fromPTN "a1>") (fromTPS "[TPS \"2,x2/x3/x3 2 7\"]"))
         (fromTPS "[TPS \"x,2,x/x3/x3 1 8\"]") )))

(deftest slides-with-flattening
  (is (= (make-move (fromPTN "4a2>121") (fromTPS "[TPS \"x4/1112C,x2,1S/x4/x4 2 7\"]"))
         (fromTPS "[TPS \"x4/x,1,11,12C/x4/x4 1 8\"]") )))