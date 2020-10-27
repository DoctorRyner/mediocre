(ns mediocre.common.data.patients)

(defrecord Patient [uid
                    first-name
                    last-name
                    middle-name
                    gender
                    birth-date
                    address
                    policy])

(def ryner (Patient. "sagfsdkm2e23d"
                     "Ryner"
                     "Reinhardt"
                     "Andreevich"
                     "Male"
                     "15.09.1998"
                     "Ulichnay ulitsa 37"
                     "23894723"))

(def alice (Patient. "d23dad2dsfsdf"
                     "Alice"
                     "Reinhardt"
                     "Andreevna"
                     "Female"
                     "24.06.1968"
                     "Obichnaya utilsa 91"
                     "29984723"))

(def victor (Patient. "dsffdgg35"
                      "Victor"
                      "von Doom"
                      ""
                      "Male"
                      "12.05.1968"
                      "Latveria plaza 77"
                      "29142723"))

(def empty-patient (Patient. "" "" "" "" "" "" "" ""))

(def default
  [ryner
   alice
   victor])
