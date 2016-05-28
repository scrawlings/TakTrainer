(ns taktrainer.tps)


(defn find-size [acc row]
  (if (empty? row)
    acc
    (let [blank  (= \x (first row))
          count  (if blank (Integer/parseInt (str (second row))) 1)
          rest   (drop 1 (drop-while #(not (= \, %)) row))]
      (recur (+ acc count) rest))))


(defn realise-stack [stack-str]
  (let [digits    (take-while #(Character/isDigit %) stack-str)
        flat-top  (= (count digits) (count stack-str))
        top       (if flat-top "F" (str (last stack-str)))
        stack     (vec (map #(keyword (str %)) digits))
        stack     (if (not flat-top) (assoc stack (dec (count stack)) (keyword (str (last digits) top))) stack)]
    stack))

(defn realise-row [acc row-str]
  (if (empty? row-str)
    acc
    (let [chunks          (clojure.string/split row-str #"," 2)
          chunk           (first chunks)
          remaining       (second chunks)
          blank           (= \x (first chunk))
          pos-digit       (second chunk)
          digit-following (and blank pos-digit (Character/isDigit pos-digit))
          blank-count     (if digit-following (Integer/parseInt (str pos-digit)) 1)
          stack           (if (not blank) [(realise-stack chunk)] (repeat blank-count []))]
      (recur (vec (concat acc stack)) remaining))))

(defn fromTPS [tps-str]
  (let [remainder  (subs tps-str 6)
        parts      (clojure.string/split remainder #" " 2)
        row-strs   (clojure.string/split (first parts) #"/")
        size       (find-size 0 (first row-strs))
        board      (vec (map #(realise-row [] %) row-strs))
        steps      (clojure.string/split (second parts) #" " 2)
        turn       (keyword (str (read-string (first steps))))
        move       (read-string (second steps))]
    {:board board :size size :turn turn :move move}))

(defn render-row [row rep]
  (if (empty? row)
    (clojure.string/join "," rep)
    (let [blanks        (count (take-while empty? row))
          blanks-rep    (if (= 1 blanks) "x" (clojure.string/join ["x" (str blanks)]))
          after-blanks  (drop-while empty? row)
          stack         (first row)
          stack-rep     (clojure.string/join (map name stack))
          after-stack   (rest row)]
      (if (> blanks 0)
        (recur after-blanks (conj rep blanks-rep))
        (recur after-stack (conj rep stack-rep))))))

(defn render-board [board rep]
  (if (empty? board)
    (clojure.string/join "/" rep)
    (let [row         (first board)
          row-rep     (render-row row [])
          remaining   (rest board)]
      (recur remaining (conj rep row-rep)))))

(defn toTPS [{board :board size :size turn :turn move :move}]
  (let [tps         "[TPS \""
        board-str   (render-board board [])]
    (clojure.string/join [tps board-str " " (name turn) " " (str move) "\"]"])))