(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.1" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all]
         '[boot.core :as boot]
         '[boot.tmpdir :as tmpd]
         '[clojure.java.io :as io]
         '[boot.util :refer [sh]])

(def +lib-version+ "0.14.3")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/react-addons-test-utils
       :version     +version+
       :description "Reacts TestUtils added as a separate package, to avoid having to do exclusions etc when working with Om/Reagent"
       :url         "https://facebook.github.io/react/docs/test-utils.html"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"BSD" "http://opensource.org/licenses/BSD-3-Clause"}})

(deftask download-and-build-test-utils []
  (let [tmp (boot/tmp-dir!)]
    (with-pre-wrap
      fileset
      (binding [boot.util/*sh-dir* (io/file tmp)]
        ((sh "npm" "install" "react@0.14.3" "react-addons-test-utils@0.14.3" "browserify"))
        ((sh "node_modules/.bin/browserify" "-r" "react-addons-test-utils" "-s" "TestUtils" "-o" "testutils-build.js")))
      (-> fileset (boot/add-resource tmp) boot/commit!))))


(deftask package []
  (comp
    (download-and-build-test-utils)
    (sift     :move     {#"testutils-build.js"
                         "cljsjs/react-addons-test-utils/development/TestUtils.inc.js"})
    (minify :in "cljsjs/react-addons-test-utils/development/TestUtils.inc.js"
            :out "cljsjs/react-addons-test-utils/production/TestUtils.min.inc.js"
            :lang :ecmascript5)
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.react-addons-test-utils")))
