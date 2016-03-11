# cljsjs/react-addons-test-utils

[](dependency)
```clojure
[cljsjs/react-addons-test-utils "0.14.3-0"] ;; latest release
```
[](/dependency)

Facebook publishes React Test-Utils as a [separate package on npm](https://www.npmjs.com/package/react-addons-test-utils). Doing the same here should hopefully enable you to pull in those without having to work with exclusions when using om, reagent or similar.

This jar comes with `deps.cljs` as used by the [Foreign Libs][flibs] feature
of the Clojurescript compiler. After adding the above dependency to your project
you can require the packaged library like so:

```clojure
(ns application.core
  (:require cljsjs.react-addons-test-utils :as test-utils))
```

[flibs]: https://github.com/clojure/clojurescript/wiki/Packaging-Foreign-Dependencies
