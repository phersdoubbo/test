cd C:\Users\Fernaull\FVU-PPP\schca_ppp_uploader\bin\
set classpath=".;C:\Users\Fernaull\FVU\shared_deps\schca_ppp_uploader\*;C:\Users\Fernaull\FVU-PPP\schca_ppp_uploader\bin\*"
java  ca.scholastic.local.LocalProcess >  C:\SHARE\mailSend\output\ppp_out.log

set file="C:\SHARE\mailSend\output\ppp_out.log"
C:\SHARE\mailSend\mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "%subject%" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "%file%" 
cd C:\SHARE\mailSend

