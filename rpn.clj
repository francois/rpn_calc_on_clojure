(def socket (java.net.ServerSocket. 5001))

(defn make-reader [in-stream]
  (java.io.InputStreamReader. in-stream))

(defn make-writer [out-stream]
  (java.io.OutputStreamWriter. out-stream))

(defn process [socket]
  (let [ in (java.io.PushbackReader. (make-reader (. socket getInputStream)))
         out (java.io.PrintWriter. (make-writer (. socket getOutputStream)))]
    (loop [stack ()]
      (let [form (read in)]
        (println "STACK:\t" stack)
        (println "READ:\t" form)
        (cond
          (number? form)
            (recur (conj stack form))
          (= 'drop form)
            (recur (rest stack))
          (= '+ form)
            (recur (conj (rest (rest stack)) (reduce + (take 2 stack))))
          (= 'quit form) (do
            (. out println "Final stack:")
            (. out println (print-str (reverse stack)))
            (. out println "Connection closed by client, closing socket")
            (. out flush)
            (. socket close))
          :else (do
            (. out println "Syntax error: unrecognized form" form)
            (. out flush)
            (. socket close)))))))

(defn run []
  (println "accepting connections")
  (process (. socket accept)))

(run)
