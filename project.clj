(defproject cpoile/xml-to-clj "0.9.0"
  :description "Turns an xml document into a clojure map. Converts
  strings to their types (if it is a well known type). Turns multiple
  siblings into a vector value of their parent's key."
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [cheshire "5.3.1"]
                 [clj-time "0.6.0"]])
