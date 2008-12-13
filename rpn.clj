(def socket (java.net.ServerSocket. 5001))

(defn make-reader [in-stream]
  (java.io.InputStreamReader. in-stream))

(defn make-writer [out-stream]
  (java.io.OutputStreamWriter. out-stream))

(defn process [socket]
 (let [ in (java.io.LineNumberReader. (make-reader (. socket getInputStream)))
        line (. in readLine)
        out (java.io.PrintWriter. (make-writer (. socket getOutputStream)))]
   (println "line read:\n\t'" line "'")
   (println "Class of line: " (.. line getClass getName))
   (. out print "you sent: '")
   (. out print line)
   (. out println "'")
   (. out flush)
   (. socket close)))

(defn run []
  (println "accepting connections")
  (process (. socket accept)))

(run)
