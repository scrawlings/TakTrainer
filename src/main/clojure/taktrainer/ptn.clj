 (ns taktrainer.ptn)


(defn fromPTN-place [ptnstr]
  (let [full      (if (= (count ptnstr) 3) ptnstr (str "F" ptnstr))
        piece (keyword (str (first full)))
        x    (keyword (str (second full)))
        y    (Integer/parseInt (str (last full)))]
    {:move :place
     :at [x y]
     :piece piece}))

(defn fromPTN-slide [take-from-dir-parts]
  (let [size-given    (Character/isDigit (first take-from-dir-parts))
        size          (if size-given (Integer/parseInt (str (first take-from-dir-parts))) :max)
        from-dir-parts (if size-given (rest take-from-dir-parts) take-from-dir-parts)
        from-x        (keyword (str (first from-dir-parts)))
        from-y        (Integer/parseInt (str (second from-dir-parts)))
        direction     (keyword (str (nth from-dir-parts 2)))
        parts         (drop 3 from-dir-parts)
        partition     (if (empty? parts) :all (vec (map #(Integer/parseInt (str %)) parts)))]
    {:move :slide
     :from [from-x from-y]
     :direction direction
     :pieces size
     :partition partition}))

(defn fromPTN [ptnstr]
  (let [len       (count ptnstr)
        lowercase (Character/isLowerCase (first ptnstr))
        uppercase (Character/isUpperCase (first ptnstr))]
  (if (or (and lowercase (= 2 len)) (and uppercase (= 3 len)))
    (fromPTN-place ptnstr)
    (fromPTN-slide ptnstr))))


(defn toPTN-place [{[x y] :at piece :piece}]
  (let [piece-str (if (= piece :F) "" (name piece))
        x-str     (name x)
        y-str     (str y)]
    (str piece-str x-str y-str)))

(defn toPTN-slide [{[x y] :from direction :direction size :pieces partition :partition}]
  (let [x-str     (name x)
        y-str     (str y)
        size-str  (if (= :max size) "" (str size))
        dir-str   (name direction)
        part-str  (if (= :all partition) "" (clojure.string/join partition))]
    (str size-str x-str y-str dir-str part-str)))

(defn toPTN [{move :move :as all}]
  (if (= move :place)
    (toPTN-place all)
    (toPTN-slide all)))