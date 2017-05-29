cd C:\SHARE\mailSend


set size=963
set type="qa_dw_credit_hold-get-profile"
set "url=nonprod.api"
set "context=qa/app/ca-svcs/1.0"
set "file=%url%.txt"
curl -v -X GET http://%url%.scholastic.com/%context%/630097/profile  -H "Authorization: Bearer xhJDTIqr4oTXw50QucKzHVUP9Wka"  > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA LSS %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%type%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)


set size=499
set type="10.15.0.90:9888/app/ca-svcs/school/1.0/1983263/profile"
set "url=10.15.0.90:9888"
set "context=qa/app/ca-svcs/school/1.0/1983263/profile"
set "file=%url%.txt"
curl -v -X GET http://%url%/%context%  -H "Authorization: Bearer xhJDTIqr4oTXw50QucKzHVUP9Wka"  > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA LSS %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%type%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)


set size=499
set "url=nonprod.api.scholastic.com/qa/app/ca-svcs/1.0/1983263/profile"
set "file=profile_qa.txt"
curl -v -X GET http://%url%  -H "Authorization: Bearer xhJDTIqr4oTXw50QucKzHVUP9Wka"  > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA LSS %size% mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%url%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)



set "url=wso2.dw-ca"
set "file=%url%.txt"
for %%A in (template/%file%) do ( 
  if %%~zA LSS %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "template/%file%" 
)


