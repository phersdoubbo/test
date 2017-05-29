
cd C:\Users\Fernaull\FVU\ScriptsTest\bin2\
set classpath=".;C:\Users\Fernaull\FVU\ScriptsTest\jar\*;C:\Users\Fernaull\FVU\ScriptsTest\bin2\*"
java  ebctest.entrypoint.PaymentLogs ferprod>  C:\SHARE\mailSend\output\bpb_logs.log
C:\SHARE\mailSend\mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "bpb_logs" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "C:\SHARE\mailSend\output\bpb_logs.log" 


java  ebctest.entrypoint.PaymentBatch ferprod>  C:\SHARE\mailSend\output\bpb_batch.log
C:\SHARE\mailSend\mailsend1.19 -f fulloa@scholastic.com -t fulloa@scholastic.com -sub "bpb_batch" -smtp nj2bizmail.scholastic.net -mime-type "text/plain"   -msg-body "C:\SHARE\mailSend\output\bpb_batch.log" 

