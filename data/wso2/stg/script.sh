#cd /Users/fernaull/workspace/neon/services/sch-wso

echo dw_credit_hold ... done1
#echo curl http://is-int1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"qa_dw_credit_hold\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-int1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @dw_credit_hold.json


echo dw_school ... done1
#echo curl http://is-int1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"qa_dw_school\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-int1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @dw_school.json


echo dw_verify_board ... done
#echo curl http://is-int1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"qa_dw_verify_board\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-int1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @dw_verify_board.json


echo qa_rco_ca_api_2 ...done
#echo curl http://is-int1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"qa-rco-ca-api_2\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-int1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @qa_rco_ca_api_2.json


echo vertex_dw ... done
#echo curl http://is-int1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"vertex_dw\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-int1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @vertex_dw.json



echo dw_digital_download ... done
#echo curl http://is-int1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"qa_digital_download\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-int1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @dw_digital_download.json


echo dw_subscriptions ... done
#echo curl http://is-int1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"dw_subscriptions\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-int1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @dw_subscriptions.json


echo dw_teacher_store ... done
#echo curl http://is-int1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"dw_subscriptions\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-int1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @dw_teacher_store.json
