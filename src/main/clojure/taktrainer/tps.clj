(ns taktrainer.tps)


(defn find-size [acc row]
  (if (empty? row)
    acc
    (let [blank  (= \x (first row))
          count  (if blank (Integer/parseInt (str (second row))) 1)
          rest   (drop 1 (drop-while #(not (= \, %)) row))]
      (recur (+ acc count) rest))))

(defn realise-row [acc row-str]
  (if (empty? row-str)
    acc
    (let [blank        (= \x (first row-str))
          blank-count  (if blank (Integer/parseInt (str (second row-str))) nil)]
      (vec (concat acc (repeat blank-count []))))))

(defn fromTPS [tps-str]
  (let [prefix     (subs tps-str 0 6)
        remainder  (subs tps-str 6)
        board      (first (clojure.string/split remainder #" " 1))
        row-strs   (clojure.string/split board #"/")
        size       (find-size 0 (first row-strs))
        board      (vec (map #(realise-row [] %) row-strs))]
    {:board board :size size}))

(defn toTPS [board]
  "")