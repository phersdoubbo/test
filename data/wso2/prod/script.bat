cd C:\Users\Fernaull\workspace\neon\POSTBACK\sps_util\wso2\prod\

echo dw_credit_hold ... done1
echo curl http://is-prod1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"prod_dw_credit_hold\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-prod1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @dw_credit_hold.json


echo dw_school ... done1
echo curl http://is-prod1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"prod_dw_school\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-prod1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @dw_school.json


echo dw_verify_board ... done
echo curl http://is-prod1-scala.api.scholastic.com:8080/remove -d "{\"name\":\"prod_dw_verify_board\",\"version\": \"1.0\"}" -H "Content-Type: application/json"
curl -X POST "http://is-prod1-scala.api.scholastic.com:8080/publish" -H "Content-Type: application/json"  -d @dw_verify_board.json



