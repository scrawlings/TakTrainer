(ns taktrainer.move)


(def ids [:a :b :c :d :e :f :g :h])

(def offsets-ref {:+ [0 1] :- [0 -1] :< [-1 0] :> [1 0]})

(defn decode-x [x]
  (.indexOf ids x))

(defn decode-y [y]
  (dec y))



(defn vector-partition [original partitions from acc]
  (if (empty? partitions)
    acc
    (let [part-size (first partitions)
          part      (subvec original from (+ from part-size))
          next-from (+ from part-size)]
      (recur original (rest partitions) next-from (conj acc part)))))

(defn add-at [board [x y] stack]
  (let [row         (board y)
        cell        (row x)
        cell        (vec (concat cell stack))
        row         (assoc row x cell)
        board       (assoc board y row)]
    board))

(defn coordinates-series [[x y] dir steps acc]
  (if (= 0 steps)
    acc
    (let [[x-offset y-offset] (offsets-ref dir)
          next                [(+ x x-offset) (+ y y-offset)]]
      (recur next dir (dec steps) (conj acc next)))))

(defn apply-add-at [board parts locations]
  (if (or (empty? parts) (empty? locations))
    board
    (recur (add-at board (first locations) (first parts)) (rest parts) (rest locations))))

(defn make-move-slide [{[x y] :from direction :direction moving-count :pieces partition :partition player :player}
                       {board :board size :size turn :turn move :move :as b}]
  (let [x           (decode-x x)
        y           (decode-y y)
        row         (board y)
        cell        (row x)
        leaving     (- (count cell) moving-count)
        partitions  (cons leaving partition)
        parts       (vector-partition cell partitions 0 [])
        cell        (first parts)
        row         (assoc row x cell)
        board       (assoc board y row)
        parts       (rest parts)
        locations   (coordinates-series [x y] direction (count parts) [])
        board       (apply-add-at board parts locations)]
    (assoc b :board board)))


(defn make-move-place [{[x y] :at piece :piece player :player}
                       {board :board :as b}]
  (let [x     (decode-x x)
        y     (decode-y y)
        row   (board y)
        cell  (row x)
        piece (keyword (str (name player) (if (not (= piece :F)) (name piece) "")))
        cell  (conj cell piece)
        row   (assoc row x cell)
        board (assoc board y row)]
    (assoc b :board board)))

(defn next-player [p]
  (if (= p :1)
    :2
    :1))

(defn make-move [{type :move :as m}
                 {turn :turn move :move :as b}]
  (let [next-p    (next-player turn)
        next-m    (if (= next-p :1) (inc move) move)
        m         (assoc m :player turn)
        next-b    (if (= type :place)
                    (make-move-place m b)
                    (make-move-slide m b))]
    (assoc next-b :move next-m :turn next-p)))