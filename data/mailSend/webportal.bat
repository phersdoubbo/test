cd C:\SHARE\mailSend


set size=9922
set "url=ec-e1d-lapp-016"
set "file=wp-prod-%url%.txt"
curl -v -X GET http://%url%.scholastic.net:8080/webportal/WebPortal.htm  > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA NEQ %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)


set "url=ec-e1c-lapp-015"
set "file=wp-prod-%url%.txt"
curl -v -X GET http://%url%.scholastic.net:8080/webportal/WebPortal.htm  > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA NEQ %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)


set "url=ec-e1b-lapp-014"
set "file=wp-prod-%url%.txt"
curl -v -X GET http://%url%.scholastic.net:8080/webportal/WebPortal.htm  > output/%file%
for %%A in (output/%file%) do ( 
  if %%~zA NEQ %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%file%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "output/%file%" 
)



set "file=wp.executing.txt"
for %%A in (template/%file%) do ( 
  if %%~zA NEQ %size%   mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "simple run" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "template/%file%" 
)
