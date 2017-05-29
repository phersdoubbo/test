cd C:\SHARE\mailSend

set file="output-qa1-postback.txt"
set subject="error-in-postbackQA1"
curl -i -X POST "http://cbs.qa1.scholastic.com/Sch/services/PBService/ivr"  -H "Content-Type: application/x-www-form-urlencoded" -d  @postback.xml > output/%file% 
find "200 OK" %file% 1>nul || mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%subject%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 

set file="output-qa2-postback.txt"
set subject="error-in-postbackQA2"
curl -i -X POST "http://cbs.qa2.scholastic.com/Sch/services/PBService/ivr"  -H "Content-Type: application/x-www-form-urlencoded" -d  @postback.xml > output/%file% 
find "200 OK" %file% 1>nul || mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%subject%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 

set file="postback-succes.xml"
set subject="run-sucessful"
mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%subject%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 




