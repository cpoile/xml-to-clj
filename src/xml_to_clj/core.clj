(ns xml-to-clj.core
  (:require [cheshire.core :as j]
            [clojure.xml :as x]
            [clj-time [format :as ctf]]
            [clojure.string :as cs]))

(defn- xml-parse-string [#^java.lang.String x]
  (x/parse (java.io.ByteArrayInputStream. (.getBytes x))))

(declare build-node)

(defn- decorate-kwd
  "attach @ at the beginning of a keyword"
  [kw]
  (keyword (str "@" (subs (str kw) 1))))

(defn- decorate-attrs
  "prepends @ to keys in a map"
  [m]
  (zipmap (map decorate-kwd (keys m)) (vals m)))

(defn- to-vec
  [x]
  (if (vector? x) x (vector x)))

(defn- merge-to-vector
  "merge 2 maps, putting values of repeating keys in a vector"
  [m1 m2]
  (merge-with #(into (to-vec %1) (to-vec %2)) m1 m2))

(defn- format-unit
  [x]
  (condp re-matches x
    #"\d+" (Integer/parseInt x)
    #"(?:\d*\.\d+|\d+\.\d*)" (Double/parseDouble x)
    #"(?i)true" true
    #"(?i)false" false
    #"\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}Z" (ctf/parse x)
    #"(?s)<\?xml.*" (build-node (xml-parse-string x))
    x))

(defn- contentMap?
  "Check if node content is a map i.e. has child nodes"
  [content]
  (map? (first content)))

(defn- parts
  [{:keys [attrs content]}]
  (merge (decorate-attrs attrs)
         (cond
          (contentMap? content) (reduce merge-to-vector (map build-node content))
          (nil? content) nil
          :else (hash-map  "#text" (format-unit (first content))))))

(defn- check-text-only
  [m]
  (if (= (keys m) '("#text"))
    (val (first m))
    m))

(defn- guard-empty
  [m]
  (if (empty? m)
    nil
    m))

(defn- build-node
  [node]
  (hash-map (:tag node) (-> (parts node)
                            guard-empty
                            check-text-only)))

(defn xml-to-clj
  [xml]
  (build-node (xml-parse-string xml)))
