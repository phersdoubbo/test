cd C:\SHARE\mailSend\

set file="C:\SHARE\mailSend\output\C:\SHARE\mailSend\output\output-dev-vertex.txt"
set size=6824
curl -X POST "http://vertexdev.scholastic.net/vertex-ws/services/CalculateTax60"  -H "Content-Type: text/xml;charset=UTF-8" -H "SOAPAction:;"  -d  @vertexperf.xml > %file% 
for %%A in (%file%) do ( 
  if %%~zA NEQ %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertexDEV" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)



set file="C:\SHARE\mailSend\output\output-qa-vertex.txt"
set size=6824
curl -X POST "http://internal-ec-qa-vertex1-app-elb-1964888938.us-east-1.elb.amazonaws.com/vertex-ws/services/CalculateTax60"  -H "Content-Type: text/xml;charset=UTF-8" -H "SOAPAction:;"  -d  @vertexperf.xml > %file% 
for %%A in (%file%) do ( 
  if %%~zA NEQ %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertexQA" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)





set file="C:\SHARE\mailSend\output\output-perf-vertex.txt"
set size=6846
curl -X POST "http://internal-ec-perf1-vertex-web-1366550061.us-east-1.elb.amazonaws.com/vertex-ws/services/CalculateTax60"  -H "Content-Type: text/xml;charset=UTF-8" -H "SOAPAction:;"  -d  @vertexperf.xml > %file% 
for %%A in (%file%) do ( 
  if %%~zA NEQ %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertexPERF" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)

set file="C:\SHARE\mailSend\output\output-prod-vertex.txt"
set size=6843
curl -X POST "http://vertex.scholastic.net/vertex-ws/services/CalculateTax60"  -H "Content-Type: text/xml;charset=UTF-8" -H "SOAPAction:;"  -d  @vertexprod.xml > %file%
for %%A in (%file%) do ( 
  if %%~zA NEQ %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertexPROD" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)




set file="C:\SHARE\mailSend\output\output-prod-033-vertex.txt"
set size=6843
curl -X POST "ec-e1b-lapp-033.scholastic.net:8080/vertex-ws/services/CalculateTax60"  -H "Content-Type: text/xml;charset=UTF-8" -H "SOAPAction:;"  -d  @vertexprod.xml > %file%
for %%A in (%file%) do ( 
  if %%~zA NEQ %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertexPROD-033" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)



set file="C:\SHARE\mailSend\output\output-prod-034-vertex.txt"
set size=6843
curl -X POST "ec-e1c-lapp-034.scholastic.net:8080/vertex-ws/services/CalculateTax60"  -H "Content-Type: text/xml;charset=UTF-8" -H "SOAPAction:;"  -d  @vertexprod.xml > %file%
for %%A in (%file%) do ( 
  if %%~zA NEQ %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertexPROD-034" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)


set file="C:\SHARE\mailSend\output\output-qa-trusted-vertex.txt"
set size=6799
curl -X POST "http://internal-ec-qa-vertex1-app-elb-1964888938.us-east-1.elb.amazonaws.com/vertex-ws/services/CalculateTax60"  -H "Content-Type: text/xml;charset=UTF-8" -H "SOAPAction:;"  -d  @vertexperfTrusted.xml > %file% 
for %%A in (%file%) do ( 
  if %%~zA NEQ %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertexQA-trusted" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)


set file="C:\SHARE\mailSend\output\output-wso-qa-vertex.txt"
set size=6799
curl -X POST "http://nonprod.api.scholastic.com/qa/vertex/1.0/CalculateTax60" -H "Authorization: Bearer xhJDTIqr4oTXw50QucKzHVUP9Wka" -H "Content-Type: text/xml;charset=UTF-8" -d  @vertexperfTrusted.xml > %file% 
for %%A in (%file%) do ( 
  if %%~zA NEQ %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertexWSO2-Qa" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)

set file="C:\SHARE\mailSend\output\output-prod-fail-vertex.txt"
set size=5535
curl -X POST "http://vertex.scholastic.net/vertex-ws/services/CalculateTax60"  -H "Content-Type: text/xml;charset=UTF-8" -H "SOAPAction:;"  -d  @vertexprodFailing.xml > %file%
for %%A in (%file%) do ( 
  if %%~zA NEQ %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertex-PROD-newcase" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)

set "url=C:\SHARE\mailSend\output\vertex.executing"
set "file=%url%.txt"
for %%A in (%file%) do ( 
  mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "vertex executed" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
)

