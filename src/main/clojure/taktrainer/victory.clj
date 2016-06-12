(ns taktrainer.victory
  (:require [clojure.math.combinatorics :as combi]))

(defn controlled-by? [cell counting]
  (let [controlling (first cell)]
    (some #(= controlling %) counting)))

(defn played-count-cell [cell counting acc]
    (if (controlled-by? cell counting)
      (inc acc)
      acc))

(defn played-count-row [row counting acc]
  (if (empty? row)
    acc
    (recur (rest row) counting (played-count-cell (first row) counting acc))))

(defn played-count [board counting acc]
  (if (empty? board)
    acc
    (recur (rest board) counting (played-count-row (first board) counting acc))))

(defn last-player [{turn :turn}]
  (if (= turn :1) :2 :1))

(defn victory-flats [{board :board turn :turn move :move {{p1C :C p1T :T} :1 {p2C :C p2T :T} :2} :pieces :as b}]
  (let [remaing-1   (+ p1C p1T)
        remaing-2   (+ p2C p2T)]
    (if (= 0 remaing-1 remaing-2)
      (last-player b)
      (if (<= 1 remaing-1 remaing-2)
        :neither
        (let [played-1 (played-count board  [:1C :1] 0)
              played-2 (played-count board  [:2C :2] 0)]
          (if (> played-1 played-2)
            :1
            :2))))))


(defn starting-side [board player row acc]
  (if (empty? board)
    acc
    (if (controlled-by? (first (first board)) player)
      (recur (rest board) player (inc row) (conj acc [0 row]))
      (recur (rest board) player (inc row) acc))))

(defn starting-top [row player col acc]
  (if (empty? row)
    acc
    (if (controlled-by? (first row) player)
      (recur (rest row) player (inc col) (conj acc [col 0]))
      (recur (rest row) player (inc col) acc))))

(defn in? [els e]
  (if (empty? els)
    false
    (if (= e (first els))
      true
      (recur (rest els) e))))

(defn allowable-offsets [d s]
  (concat [] (if (> d 0) [(dec d)] nil) (if (< d s) [(inc d)] nil)))

(defn neighbours [[x y] size]
  (let [x-dirs      (allowable-offsets x size)
        y-dirs      (allowable-offsets y size)]
    (concat (combi/cartesian-product x-dirs [y])
            (combi/cartesian-product [x] y-dirs))))

(defn get-at [[x y] board]
  (let [row         (board y)]
    (row x)))

(defn flood-from [{board :board size :size :as b} player to-check visited picker]
  (if (empty? to-check)
    false
    (let [finishing-in              (dec size)
          current                   (first to-check)
          all-next-cells            (neighbours current finishing-in)
          next-cells                (remove visited all-next-cells)
          controlled-next-cells     (filter #(controlled-by? (get-at % board) player) next-cells)
          next-cols                 (map picker controlled-next-cells)
          at-other-side             (in? next-cols finishing-in)]
      (if at-other-side
        true
        (recur b player (concat (rest to-check) controlled-next-cells) (conj visited current) picker) ))))

(defn east-west-road [{board :board :as b} player]
  (let [start  (starting-side board player 0 #{})]
    (flood-from b player start #{} first)))

(defn north-south-road [{board :board :as b} player]
  (let [start  (starting-top (first board) player 0 #{})]
    (flood-from b player start #{} second)))

(defn victory-road [b]
  (let [ew1   (east-west-road b [:1 :1C])
        ew2   (east-west-road b [:2 :2C])
        ns1   (north-south-road b [:1 :1C])
        ns2   (north-south-road b [:2 :2C])
        p1    (or ns1 ew1)
        p2    (or ns2 ew2)]
    (if (and p1 p2)
      (last-player b)
      (if p1
        :1
        (if p2
          :2
          :neither)))))

(defn victory [b]
  (let [by-flats    (victory-flats b)
        by-road     (victory-road b)]
  (if (not= by-road :neither)
    by-road
    by-flats)))
