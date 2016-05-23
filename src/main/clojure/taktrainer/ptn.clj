 (ns taktrainer.ptn)


(defn fromPTN-place [ptnstr]
  (let [full      (if (= (count ptnstr) 3) ptnstr (str "F" ptnstr))
        piece (keyword (str (first full)))
        x    (keyword (str (second full)))
        y    (Integer/parseInt (str (last full)))]
    {:move :place
     :at [x y]
     :piece piece}))

(defn fromPTN-slide [ptnstr]
  (let []
    :nope))

(defn fromPTN [ptnstr]
  (let [len       (count ptnstr)
        lowercase (Character/isLowerCase (first ptnstr))
        uppercase (Character/isUpperCase (first ptnstr))]
  (if (or (and lowercase (= 2 len)) (and uppercase (= 3 len)))
    (fromPTN-place ptnstr)
    (fromPTN-slide ptnstr))))

(defn toPTN [{move :move :as all}]
  "f6")