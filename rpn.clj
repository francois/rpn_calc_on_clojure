(def socket (java.net.ServerSocket. 5001))

(defn make-reader [in-stream]
  (java.io.InputStreamReader. in-stream))

(defn make-writer [out-stream]
  (java.io.OutputStreamWriter. out-stream))

(defn process [socket]
  (let [ in (java.io.PushbackReader. (make-reader (. socket getInputStream)))
         out (java.io.PrintWriter. (make-writer (. socket getOutputStream)))]
    (loop []
      (let [value (eval (read in))]
        (if (= 'quit value)
          (do
            (. out println "Connection closed by client, closing socket")
            (. out flush)
            (. socket close))
          (do
            (. out println value)
            (. out flush)
            (recur)))))))

(defn run []
  (println "accepting connections")
  (process (. socket accept)))

(run)
