## Примеры запросов CURL
### Получение списка студентов

curl -X GET http://localhost:8080/api/students \
-H 'Authorization: Bearer <ACCESS_TOKEN>'
### Создание нового студента

curl -X POST http://localhost:8080/api/students \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer <ACCESS_TOKEN>' \
-d '{
"lastName": "Иванов",
"firstName": "Иван",
"middleName": "Иванович",
"group": "101",
"averageGrade": 4.5
}'
### Обновление существующего студента

curl -X PUT http://localhost:8080/api/students/<STUDENT_ID> \
-H 'Content-Type: application/json' \
-H 'Authorization: Bearer <ACCESS_TOKEN>' \
-d '{
"lastName": "Петров",
"firstName": "Петр",
"middleName": "Петрович",
"group": "102",
"averageGrade": 4.8
}'
### Удаление студента

curl -X DELETE http://localhost:8080/api/students/<STUDENT_ID> \
-H 'Authorization: Bearer <ACCESS_TOKEN>'