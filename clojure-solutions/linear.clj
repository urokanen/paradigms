(defn checkVector [v] (and (vector? v) (every? number? v)))
(defn checkScalar [args] (every? number? args))
(defn check-matrix [m] (let [first (first m)] (every? #(= (count %) (count first)) m)))
(defn checkMatrix [m] (and (every? checkVector m) (check-matrix m)))
(defn checkArr [args] (let [matrix-sizes (map #(count %) args)] (apply = matrix-sizes)))
(defn checkTensor [args] (and (every? vector? args) (every? checkMatrix args) (checkArr args)))
(defn getSize [t] (if (vector? t) (conj (getSize (first t)) (count t)) (vector)))
(defn checkChildSize [t] (every? #(= (getSize (first t)) (getSize %)) t))
(defn checkSize [t] (if (number? t) true (if (checkChildSize t) (every? checkSize t) false)))
(defn isPrefix [vec1 vec2] (if (< (count vec1) (count vec2)) false (= (take (count vec2) vec1) vec2)))
(defn checkPrefix [t1 t2] (or (isPrefix (getSize t1) (getSize t2)) (isPrefix (getSize t2) (getSize t1))))

(defn operateV [f] (fn [& args]
                     {:pre[(checkMatrix args)]}
                     (apply mapv f args)))

(defn operateM [f] (fn [& args]
                     {:pre[(checkTensor args)]}
                     (apply mapv f args)))

(defn calcT [f t1 t2]
    {:pre[(and (checkSize t1) (checkSize t2) (checkPrefix t1 t2))]}
    (if (and (vector? t1) (vector? t2))
        (if (= (count (getSize t1)) (count (getSize t2)))
        (mapv #(calcT f %1 %2) t1 t2)
        (if (< (count (getSize t1)) (count (getSize t2)))
            (mapv #(calcT f t1 %) t2)
            (mapv #(calcT f % t2) t1)))
        (if (and (number? t1) (number? t2))
            (f t1 t2)
            (if (number? t2)
                (mapv #(calcT f % t2) t1)
                (mapv #(calcT f t1 %) t2)))))

(defn operateT [elem f] (fn [& args]
    {:pre[(every? checkSize args)]}
    (if (= 1 (count args))
        (calcT f elem (first args))
    (reduce (fn [t1 t2] (calcT f t1 t2)) args))))

(def v+ (operateV +))
(def v- (operateV -))
(def v* (operateV *))
(def vd (operateV /))

(def m+ (operateM v+))
(def m- (operateM v-))
(def m* (operateM v*))
(def md (operateM vd))

(def b+ (operateT 0 +))
(def b- (operateT 0 -))
(def b* (operateT 1 *))
(def bd (operateT 1 /))

(defn v*s [v & args]
  {:pre[(and (checkVector v) (checkScalar args))]}
  (let [calculated (apply * args)]
    (mapv #(* % calculated) v)))

(defn scalar [& args]
  {:pre[(checkMatrix args)]}
  (reduce + (apply v* args)))

(defn vect [& args]
  {:pre[(checkMatrix args)]}
  (reduce (fn [v1 v2] (letfn [ (vectCord [l1 l2 r1 r2] (- (* l1 r2) (* l2 r1)))]
                         (vector (vectCord (nth v1 1) (nth v1 2) (nth v2 1) (nth v2 2))
                                 (vectCord (nth v1 2) (nth v1 0) (nth v2 2) (nth v2 0))
                                 (vectCord (nth v1 0) (nth v1 1) (nth v2 0) (nth v2 1))))) args))

(defn m*s [m & args]
  {:pre[(and (checkMatrix m) (checkScalar args))]}
  (let [calculated (apply * args)]
  (mapv #(v*s % calculated) m)))

(defn m*v [m1 v1]
  {:pre[(and (checkMatrix m1) (checkVector v1))]}
  (vec (mapv #(scalar % v1) m1)))

(defn transpose [m1]
  {:pre[(checkMatrix m1)]}
  (apply mapv vector m1))

(defn m*m [& args]
    {:pre[(every? checkMatrix args)]}
    (reduce (fn [m1 m2]
          {:pre[(= (first (getSize m1)) (second (getSize m2)))]}
          (mapv #(mapv (partial scalar %) (transpose m2)) m1)) args))
