(def socket (java.net.ServerSocket. 5001))

(defn make-reader [in-stream]
  (java.io.InputStreamReader. in-stream))

(defn make-writer [out-stream]
  (java.io.OutputStreamWriter. out-stream))

(defn process [socket]
  (let [ in (java.io.PushbackReader. (make-reader (. socket getInputStream)))
         out (java.io.PrintWriter. (make-writer (. socket getOutputStream)))]
    (. out println (eval (read in)))
    (. out flush)
    (. socket close)))

(defn run []
  (println "accepting connections")
  (process (. socket accept)))

(run)
