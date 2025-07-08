## Курсовой проект «Сервис перевода денег»

Настройка перед запуском: <br>
- back: docker build -t apptm:1.0 -t apptm:latest . <br>
- front: docker build -t transfer-master-front:1.0 -t transfer-master-front:latest . <br>
Команда запуска: docker-compose up app node -d <br>

Порты:
- back: 8080
- front: 3000 <br>

Пример команды запуска: <br>
curl -X POST -H "Content-Type: application/json" -d '{
    "cardFromNumber": "1111111111111111",
    "cardFromValidTill": "11/33",
    "cardFromCVV": "111",
    "cardToNumber": "2222222222222222",
    "amount": {
        "value": 100,
        "currency": "rub"
    }
}' http://localhost:8080/transfer
