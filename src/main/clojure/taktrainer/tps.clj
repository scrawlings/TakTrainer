(ns taktrainer.tps)


(defn find-size [acc row]
  (if (empty? row)
    acc
    (let [blank  (= \x (first row))
          count  (if blank (Integer/parseInt (str (second row))) 1)
          rest   (drop 1 (drop-while #(not (= \, %)) row))]
      (recur (+ acc count) rest))))

(defn fromTPS [tps-str]
  (let [prefix     (take 6 tps-str)
        remainder  (drop 6 tps-str)
        board-size (find-size 0 (take-while #(not (= \/ %)) remainder))]
    {}))

(defn toTPS [board]
  "")