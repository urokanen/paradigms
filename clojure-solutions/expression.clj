(defn operation [op] (fn [& args] #(apply op (mapv (fn [arg] (arg %)) args))))
(defn div [a b] (/ (double a) (double b)))
(defn del ([arg] (div 1 arg)) ([arg & args] (reduce div arg args)))
(def constant constantly)
(defn variable [v] (fn [res] (res v)))
(defn letter? [s] (or (= s 'y) (= s 'x) (= s 'z)))
(defn mean [& args] (del (apply + args) (count args)))
(defn inv [vect] (mapv #(del 1 %) vect))
(defn harm [& args] (del (count args) (apply + (inv args))))
(defn geom [& args] (Math/pow (abs (apply * args)) (del 1 (count args))))

(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def negate (operation -))
(def divide (operation del))
(def arithMean (operation mean))
(def geomMean (operation geom))
(def harmMean (operation harm))

(def expTokens {'+ add
            '- subtract
            '* multiply
            '/ divide
            'negate negate
            'arithMean arithMean
            'geomMean geomMean
            'harmMean harmMean})

(defn calc [expr tokens cnst varb]
    (let [smth (read-string expr)]
        (if (number? smth)
            (cnst smth)
            (if (letter? smth)
                (varb (str smth))
                (let [f (first smth)]
                    (letfn [(parseToken [token]
                                (if (list? token)
                                  (calc (str token) tokens cnst varb)
                                  (if (number? token)
                                    (cnst token)
                                    (varb (str token)))))]
                  (apply (get tokens f) (mapv parseToken (rest smth)))))))))

(defn parseFunction [expr] (calc expr expTokens constant variable))

;objectExpression HW11

(defn proto-get ([obj key] (proto-get obj key nil)) ([obj key default]
        (cond (contains? obj key) (obj key)
              (contains? obj :prototype) (proto-get (obj :prototype) key default)
              :else default)))

(defn proto-call [this key & args] (apply (proto-get this key) this args))
(defn method [key] (fn [this & args] (apply proto-call this key args)))
(defn field [key] (fn ([this] (proto-get this key)) ([this def] (proto-get this key def))))
(defn constructor [ctor prototype] (fn [& args] (apply ctor {:prototype prototype} args)))

(declare cnst0)
(declare cnst1)

(def evaluate (method :evaluate))
(def toString (method :toString))
(def elems (field :elems))
(def diff (method :diff))

(defn Constant [val] {
    :evaluate (fn [this res] val)
    :toString (fn [this] (str val))
    :diff (fn [this var] cnst0)
})

(def cnst0 (Constant 0))
(def cnst1 (Constant 1))

(defn Variable [tag] {
    :evaluate (fn [this res] (res tag))
    :toString (fn [this] (str tag))
    :diff (fn [this var] (if (= var tag) cnst1 cnst0))
})

(def Constructor {
    :evaluate (fn [this res] (apply ((field :calc) this) (mapv #(evaluate % res) ((field :elems) this))))
    :toString
    (fn [this] (str "(" ((field :oper) this) " " (clojure.string/join " " (mapv toString ((field :elems) this))) ")"))
})

(defn OpConstructor [operation operator diff]
    (constructor (fn [this & args] (assoc this :elems (vec args)))
        { :prototype Constructor :oper operation :calc operator :diff diff}))

(declare harmArg)
(declare multArg)
(declare diffAdd)
(declare diffNegate)
(declare diffDivide)
(declare diffMultiply)
(declare diffSubtract)
(declare diffGeomMean)
(declare diffHarmMean)
(declare diffArithMean)

(defn myPow [a b] (if (= b 0) (1) (Math/pow a b)))

(def Add (OpConstructor "+" + #'diffAdd))
(def Pow (OpConstructor "pow" myPow myPow))
(def Divide (OpConstructor "/" del #'diffDivide))
(def Subtract (OpConstructor "-" - #'diffSubtract))
(def Multiply (OpConstructor "*" * #'diffMultiply))
(def Negate (OpConstructor "negate" - #'diffNegate))
(def GeomMean (OpConstructor "geomMean" geom #'diffGeomMean))
(def HarmMean (OpConstructor "harmMean" harm #'diffHarmMean))
(def ArithMean (OpConstructor "arithMean" mean #'diffArithMean))

(defn diffNegate [this var] (Negate (diff (first (elems this)) var)))
(defn diffAdd [this var] (apply Add (mapv #(diff % var) (elems this))))
(defn diffSubtract [this var] (apply Subtract (mapv #(diff % var) (elems this))))
(defn diffMultiply [this var]
  (Multiply (apply Multiply (elems this)) (apply Add (mapv #(Divide (diff % var) %) (elems this)))))
(defn diffDivide [this var] (let [els (elems this)] (if (= 1 (count els)) (diff (Divide cnst1 (first els)) var)
    (let [lst (apply Multiply (rest (elems this)))]
      (Divide (Subtract (Multiply (diff (first (elems this)) var) lst)
                        (Multiply (diff lst var) (first (elems this)))) (Multiply lst lst))))))
(defn diffArithMean [this var] (Divide (Multiply (diff (apply Add (elems this)) var) (Constant (count (elems this))))
                                       (Multiply (Constant (count (elems this))) (Constant (count (elems this))))))
(defn diffHarmMean [this var] (let [ex (apply harmArg (elems this))]
    (Divide (Negate (Multiply (Constant (count (elems this))) (diff ex var))) (Multiply ex ex))))
(defn harmArg [& args] (if (= 1 (count args)) (Divide cnst1 (first args))
(Add (Divide cnst1 (first args)) (apply harmArg (rest args)))))
(defn multArg [& args] (apply Multiply (first args) (rest args)))
(defn diffGeomMean [this var] (Multiply (Multiply (Pow (apply multArg (elems this))
    (Constant (- (del 1 (count (elems this))) 1))) (Constant (del 1 (count (elems this))))) (diff (apply multArg (elems this)) var)))

(def objTokens {'+ Add
                '- Subtract
                '* Multiply
                '/ Divide
                'negate Negate
                'geomMean GeomMean
                'harmMean HarmMean
                'arithMean ArithMean})

(defn parseObject [expr] (calc expr objTokens Constant Variable))