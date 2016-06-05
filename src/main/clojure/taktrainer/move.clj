(ns taktrainer.move)


(def ids [:a :b :c :d :e :f :g :h])

(defn decode-x [x]
  (.indexOf ids x))

(defn decode-y [y]
  (dec y))


(def offsets-ref {:+ [0 1] :- [0 -1] :< [-1 0] :> [1 0]})


(defn is-piece-type [piece type]
  (if (nil? piece)
    false
    (let [type-char  (second (name piece))]
      (and (not (nil? type-char)) (= type type-char)))))

(defn standing? [piece]
  (is-piece-type piece \S))

(defn cap? [piece]
  (is-piece-type piece \C))


(defn vector-partition [original partitions from acc]
  (if (empty? partitions)
    acc
    (let [part-size (first partitions)
          part      (subvec original from (+ from part-size))
          next-from (+ from part-size)]
      (recur original (rest partitions) next-from (conj acc part)))))

(defn replace-at [board [x y] stack]
  (let [row         (assoc (board y) x (vec stack))
        board       (assoc board y row)]
    board))

(defn flat [piece]
  (->> piece name first str keyword))

(defn add-at [board [x y] stack]
  (let [row         (board y)
        cell        (row x)
        base        (if (empty? cell) [] (subvec cell 0 (dec (count cell))))
        top         (if (empty? cell) [] (last cell))
        solo-cap    (and (= 1 (count stack)) (cap? (last stack)))
        top         (if solo-cap (flat top) top)
        top         (if (keyword? top) [top] top)
        cell        (vec (concat base top stack))
        row         (assoc row x cell)
        board       (assoc board y row)]
    board))

(defn apply-add-at [board parts locations]
  (if (or (empty? parts) (empty? locations))
    board
    (recur (add-at board (first locations) (first parts)) (rest parts) (rest locations))))

(defn coordinates-series [[x y] dir steps acc]
  (if (= 0 steps)
    acc
    (let [[x-offset y-offset] (offsets-ref dir)
          next                [(+ x x-offset) (+ y y-offset)]]
      (recur next dir (dec steps) (conj acc next)))))

(defn make-move-slide [{[x y] :from direction :direction moving-count :pieces partition :partition player :player}
                       {board :board size :size turn :turn move :move :as b}]
  (let [x            (decode-x x)
        y            (decode-y y)
        row          (board y)
        cell         (row x)
        moving-count (if (number? moving-count) moving-count (min (count cell) size))
        leaving      (- (count cell) moving-count)
        partition    (if (keyword? partition) [moving-count] partition)
        partitions   (cons leaving partition)
        parts        (vector-partition cell partitions 0 [])
        board        (replace-at board [x y] (first parts))
        parts        (rest parts)
        locations    (coordinates-series [x y] direction (count parts) [])
        board        (apply-add-at board parts locations)]
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

(defn check-stackable [board [x y] stack]
  (let [row         (board y)
        cell        (row x)
        top         (last cell)
        flat-top    (not (or (standing? top) (cap? top)))
        solo-cap    (and (= 1 (count stack)) (cap? (last stack)))]
    (or flat-top (and solo-cap (standing? top))) ))

(defn check-slidable [board parts locations valid]
  (if (or (not valid) (empty? parts) (empty? locations))
    valid
    (recur board (rest parts) (rest locations) (check-stackable board (first locations) (first parts)))))

(defn check-unblocked [board cell moving-count coords direction partition]
  (let [leaving             (- (count cell) moving-count)
        partitions          (cons leaving partition)
        parts               (vector-partition cell partitions 0 [])
        parts               (rest parts)
        locations           (coordinates-series coords direction (count parts) [])]
    (check-slidable board parts locations true)))

(defn controlled? [piece player]
  (let [player-char       (->> player name first)
        piece-char        (->> piece name first)]
    (= player-char piece-char)))


(defn validate-move-slide [{[x y] :from direction :direction moving-count :pieces partition :partition}
                           {board :board size :size turn :turn move :move}]
  (let [x                   (decode-x x)
        y                   (decode-y y)
        row                 (board y)
        cell                (row x)
        control             (controlled? (last cell) turn)
        moving-count        (if (number? moving-count) moving-count (min (count cell) size))
        sufficient          (<= moving-count (count cell))
        partition           (if (keyword? partition) [moving-count] partition)
        partable            (= moving-count (reduce + partition))
        [x-offset y-offset] (offsets-ref direction)
        x-extent            (+ x (* x-offset (count partition)))
        y-extent            (+ y (* y-offset (count partition)))
        within-bounds       (and (>= x-extent 0) (>= y-extent 0) (< x-extent size) (< y-extent size))]
    (and control sufficient partable within-bounds (check-unblocked board cell moving-count [x y] direction partition))))

(defn validate-move-place [{[x y] :at}
                           {board :board}]
  (let [x (decode-x x)
        y (decode-y y)
        row (board y)
        cell (row x)]
    (empty? cell)))


(defn validate-move [{type :move :as m} b]
    (if (= type :place)
      (validate-move-place m b)
      (validate-move-slide m b)))