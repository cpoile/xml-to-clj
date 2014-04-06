# xml-to-clj

Turns an xml document into a clojure map. Converts strings to their types (if it is a well known type). Turns multiple siblings into a vector value of their parent's key. Based on [kolov/x2j](https://github.com/kolov/x2j), with many thanks.

## Usage

For now this isn't on clojars, so you will need to clone the project, run ```lein install```, and make a symlink from the calling project's checkouts directory to this project's root directory. For reference, see [Leiningen checkouts](https://github.com/technomancy/leiningen/blob/master/doc/TUTORIAL.md#checkout-dependencies) from the tutorial.

Call ```(xml-to-clj xml)``` and receive a clojure map. The end.

## License

Copyright © 2014 Christopher Poile

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
