# ElectroLux 
### Задание

Реализовать backend приложение, которое имитирует управление стиральной машиной через Rest сервис:
В сервисе есть 4 сущности

USER -> содержит набор режимов стирки WORKMODE
WORKMODE -> режим стирки может загружаться в разные стиральные машины MODEL
MODEL -> сожержит набор режимов стирки и записи своего состояния ENTRY при каждом включении

Для простоты тестирования при старте создается 1 USER, 1 MODEL, 1 WORKMODE и WORKMODE
загружается в стиральную машину.   Можно сразу вызывтать метод запуска стирки с их id.

Stack технологий: Java 8, Spring Boot 2.0, H2 Database, JUNIT, Swagger

### Описание

Приложение реализует работу с заказами командировок. У командировки есть:
- Имя командируемого
- Место командировки
- Дата начала
- Дата окончания
- Статус

Статус может принимать значения: 
Open -> InProgress -> Closed

Переход между статусами возможен только слева направо. 
Попытка других переходов вызовет ошибку.
Переход InProgress -> Closed автоматический по шедулеру.


### Запуск
`$ mvnw spring-boot:run`

### Описание REST API
Для просмотра API можно сипользовать swagger http://localhost:8080/swagger-ui.html

В некоторых запросах понадобится вот такая структура:
1. создание USER | api/user
```
{
	"login":"login1"
}
```
2. создание MODE | api/mode/{userId}
```
{
	"nameMode":"1-3",
	"spidSpeed":200,
	"washingTemperature":30,
	"washingTimer":50,
	"saveWater":"low"
}
```
3. создание MODEL | api/model/{userId}
```
{
	"modelName":"1-1WM",
	"mainsVoltage":200,
	"hardnessWater":30,
	"washingNumber":0,
	"volume":20,
	"hexCodeCollor":"codecollor",
	"isDisplay":true,
	"warrantyExpirationDate":"2017-12-27"
}
```
4. добавление ENTRY | api/log/createFor/{modelId}/andMode/{modeId}

### REST API. Работа с объектами

1. Получение всех записей во временном промежутке

       GET api/log/{firstDate}/between/{lastDate}
       
2. Получение всех записей по дате первой записи

       GET api/log/getAll/{createdDate}
       
3. Загрузить список режимов в стиральную машину

       PUT api/model/putSomeMode/{userId}/model/{modelId}
      
       {
       	"listModes":[1,2]
       }
       
4. Удалить список режимов из стиральной машины

       PUT api/model/deleteSomeModes/{userId}/model/{modelId}
       
5. Получение всех режимов установленных в стиральной машине

       GET api/model/allModesByWMId/{id}

### REST API. Запуск стирки

    Для удобста тестирования при старте приложения создаются сущности USER, MODEL, WORKMODE.
    WORKMODE загружается в стиральную машину и можно тестировать метод оператора.

    GET /api/operation/run/{wmId}/withMode/{modeId}

каждый запуск будет увеличивать число стирок в данной модели
Пример ответа сервера на число записей:
````
[
    {
        "id": 1,
        "log": "User with login: Jean-Luc Picard | run the washing mode with following futures: WorkMode(nameMode=default, spidSpeed=1100, washingTemperature=75, washingTimer=45, saveWater=economically) | on washing machine with a following futures: Model(modelName=USS_Enterprise_(NCC-1701-D), mainsVoltage=220, hardnessWater=59, volume=5, washingNumber=1, hexCodeCollor=#FFFF00, isDisplay=true, warrantyExpirationDate=2023-12-12)",
        "currentlyDate": "2020-03-09",
        "createdAt": "2020-03-09"
    },
    {
        "id": 2,
        "log": "User with login: Jean-Luc Picard | run the washing mode with following futures: WorkMode(nameMode=default, spidSpeed=1100, washingTemperature=75, washingTimer=45, saveWater=economically) | on washing machine with a following futures: Model(modelName=USS_Enterprise_(NCC-1701-D), mainsVoltage=220, hardnessWater=59, volume=5, washingNumber=2, hexCodeCollor=#FFFF00, isDisplay=true, warrantyExpirationDate=2023-12-12)",
        "currentlyDate": "2020-03-09",
        "createdAt": "2020-03-09"
    },
    {
        "id": 3,
        "log": "User with login: Jean-Luc Picard | run the washing mode with following futures: WorkMode(nameMode=default, spidSpeed=1100, washingTemperature=75, washingTimer=45, saveWater=economically) | on washing machine with a following futures: Model(modelName=USS_Enterprise_(NCC-1701-D), mainsVoltage=220, hardnessWater=59, volume=5, washingNumber=3, hexCodeCollor=#FFFF00, isDisplay=true, warrantyExpirationDate=2023-12-12)",
        "currentlyDate": "2020-03-09",
        "createdAt": "2020-03-09"
    },
    {
        "id": 4,
        "log": "User with login: Jean-Luc Picard | run the washing mode with following futures: WorkMode(nameMode=default, spidSpeed=1100, washingTemperature=75, washingTimer=45, saveWater=economically) | on washing machine with a following futures: Model(modelName=USS_Enterprise_(NCC-1701-D), mainsVoltage=220, hardnessWater=59, volume=5, washingNumber=4, hexCodeCollor=#FFFF00, isDisplay=true, warrantyExpirationDate=2023-12-12)",
        "currentlyDate": "2020-03-09",
        "createdAt": "2020-03-09"
    },
    {
        "id": 5,
        "log": "User with login: Jean-Luc Picard | run the washing mode with following futures: WorkMode(nameMode=default, spidSpeed=1100, washingTemperature=75, washingTimer=45, saveWater=economically) | on washing machine with a following futures: Model(modelName=USS_Enterprise_(NCC-1701-D), mainsVoltage=220, hardnessWater=59, volume=5, washingNumber=5, hexCodeCollor=#FFFF00, isDisplay=true, warrantyExpirationDate=2023-12-12)",
        "currentlyDate": "2020-03-09",
        "createdAt": "2020-03-09"
    }
]
````

