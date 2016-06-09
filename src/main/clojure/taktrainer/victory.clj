(ns taktrainer.victory)


(defn played-count-cell [cell counting acc]
  (let [controlling (first cell)]
    (if (some #(= controlling %) counting)
      (inc acc)
      acc)))

(defn played-count-row [row counting acc]
  (if (empty? row)
    acc
    (recur (rest row) counting (played-count-cell (first row) counting acc))))

(defn played-count [board counting acc]
  (if (empty? board)
    acc
    (recur (rest board) counting (played-count-row (first board) counting acc))))

(defn victory [{board :board turn :turn move :move {{p1C :C p1T :T} :1 {p2C :C p2T :T} :2} :pieces}]
  (let [remaing-1   (+ p1C p1T)
        remaing-2   (+ p2C p2T)
        last-player (if (= turn :1) :2 :1)]
    (if (= 0 remaing-1 remaing-2)
      last-player
      (if (<= 1 remaing-1 remaing-2)
        :neither
        (let [played-1 (played-count board  [:1C :1] 0)
              played-2 (played-count board  [:2C :2] 0)]
          (if (> played-1 played-2)
            :1
            :2))))))