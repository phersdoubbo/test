cd C:\SHARE\mailSend
curl -v -X GET http://bpbps.scholastic.com/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > file1.txt && mailsend1.19 -f SMohapatra-consultant@Scholastic.com -t SMohapatra-consultant@Scholastic.com -sub "bpbps.scholastic.com" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "file1.txt" 
curl -v -X GET http://ec-e1b-lapp-012.scholastic.net:8080/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > file2.txt && mailsend1.19 -f SMohapatra-consultant@Scholastic.com -t SMohapatra-consultant@Scholastic.com -sub "ec-e1b-lapp-012.scholastic.net" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "file2.txt" 
curl -v -X GET http://ec-e1c-lapp-013.scholastic.net:8080/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > file3.txt && mailsend1.19 -f SMohapatra-consultant@Scholastic.com -t SMohapatra-consultant@Scholastic.com -sub "ec-e1c-lapp-013.scholastic.net" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "file3.txt" 
curl -v -X GET http://ec-e1b-lapp-052.scholastic.net:8080/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > file4.txt && mailsend1.19 -f SMohapatra-consultant@Scholastic.com -t SMohapatra-consultant@Scholastic.com -sub "ec-e1b-lapp-052.scholastic.net" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "file4.txt" 
curl -v -X GET http://ec-e1c-lapp-053.scholastic.net:8080/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > file5.txt && mailsend1.19 -f SMohapatra-consultant@Scholastic.com -t SMohapatra-consultant@Scholastic.com -sub "ec-e1c-lapp-053.scholastic.net" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "file5.txt" 
