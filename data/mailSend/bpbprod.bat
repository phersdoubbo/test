cd C:\SHARE\mailSend


set size=3000


set "url=bpbps"
set "file=%url%.txt"
curl -v -X GET http://%url%.scholastic.com/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA LSS %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)

set "url=ec-e1b-lapp-012"
set "file=%url%.txt"
curl -v -X GET http://%url%.scholastic.net:8080/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA LSS %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)


set "url=ec-e1c-lapp-013"
set "file=%url%.txt"
curl -v -X GET http://%url%.scholastic.net:8080/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA LSS %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)


set "url=ec-e1b-lapp-052"
set "file=%url%.txt"
curl -v -X GET http://%url%.scholastic.net:8080/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA LSS %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)


set "url=ec-e1c-lapp-053"
set "file=%url%.txt"
curl -v -X GET http://%url%.scholastic.net:8080/BPBPSWeb/PaymentHistoryServlet?bcoe=182862656   --cookie "SPS_SESSION=TFK1vUrSUHGfUOpAZpgacA==; SPS_TSP=gYr+YnKDJSroDGIpbhdVAw==;" > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA LSS %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)

set "url=bpb.executing"
set "file=%url%.txt"
for %%A in (output/%file%) do ( 
  if %%~zA LSS %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)
