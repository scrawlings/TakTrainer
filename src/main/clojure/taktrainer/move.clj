(ns taktrainer.move)


(defn make-move-slide [{[x y] :at piece :piece player :player}
                       {board :board size :size turn :turn move :move :as b}]
  :joking)

(def ids [:a :b :c :d :e :f :g :h])

(defn decode-x [x]
  (.indexOf ids x))

(defn decode-y [y]
  (dec y))


(defn make-move-place [{[x y] :at piece :piece player :player}
                       {board :board :as b}]
  (let [x     (decode-x x)
        y     (decode-y y)
        piece (keyword (str (name player) (if (not (= piece :F)) (name piece) "")))
        row   (board y)
        cell  (row x)
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