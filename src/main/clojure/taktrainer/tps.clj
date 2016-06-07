(ns taktrainer.tps)

(def starting-pieces {2 {:C 0 :T 3}
                      3 {:C 0 :T 10}
                      4 {:C 0 :T 15}
                      5 {:C 1 :T 20}
                      6 {:C 1 :T 30}
                      7 {:C 2 :T 40}
                      8 {:C 2 :T 50} })

(defn find-size [acc row]
  (if (empty? row)
    acc
    (let [blank           (= \x (first row))
          pos-digit       (second row)
          digit-following (and blank pos-digit (Character/isDigit pos-digit))
          count           (if digit-following (Integer/parseInt (str pos-digit)) 1)
          rest            (drop 1 (drop-while #(not (= \, %)) row))]
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

(defn cnt-fltr [s e]
  (count (filter #(= e %) s)))

(defn played-stack [stack pieces]
  (let [caps-1      (cnt-fltr stack :1C)
        standing-1  (cnt-fltr stack :1S)
        flat-1      (cnt-fltr stack :1)
        caps-2      (cnt-fltr stack :2C)
        standing-2  (cnt-fltr stack :2S)
        flat-2      (cnt-fltr stack :2)
        p1          (pieces :1)
        caps-1      (- (p1 :C) caps-1)
        tiles-1     (- (p1 :T) (+ standing-1 flat-1))
        p2          (pieces :2)
        caps-2      (- (p2 :C) caps-2)
        tiles-2     (- (p2 :T) (+ standing-2 flat-2))]
    {:1 {:C (max 0 caps-1) :T (max 0 tiles-1)}
     :2 {:C (max 0 caps-2) :T (max 0 tiles-2)}}))

(defn played-row [row pieces]
  (if (empty? row)
    pieces
    (recur (rest row) (played-stack (first row ) pieces)) ))

(defn played [board pieces]
  (if (empty? board)
    pieces
    (recur (rest board) (played-row (first board) pieces)) ))

(defn fromTPS [tps-str]
  (let [remainder  (subs tps-str 6)
        parts      (clojure.string/split remainder #" " 2)
        row-strs   (clojure.string/split (first parts) #"/")
        size       (find-size 0 (first row-strs))
        board      (vec (map #(realise-row [] %) row-strs))
        steps      (clojure.string/split (second parts) #" " 2)
        turn       (keyword (str (read-string (first steps))))
        move       (read-string (second steps))
        pieces     {:1 (starting-pieces size) :2 (starting-pieces size)}
        pieces     (played board pieces)]
    {:board board :size size :turn turn :move move :pieces pieces}))

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