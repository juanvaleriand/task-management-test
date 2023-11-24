# Task Management API Spec

## Get All Task

Endpoint : GET /api/tasks

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success):

```json
{
    "data": [
        {
            "id": 1,
            "title": "Task Title 1",
            "description": "Task Description 1",
            "completed": false
        },
        {
            "id": 2,
            "title": "Task Title 2",
            "description": "Task Description 2",
            "completed": true
        }
    ]
}
```


## Search Task

Endpoint : GET /api/tasks

Request Header :

- X-API-TOKEN : Token (Mandatory)

Query Params :
- title : String, task title, using like query, optional
- description : String, task description, using like query, optional
- completed : Boolean
- page : Integer, start from 0, default 0
- size : Integer, default 10

Response Body (Success):

```json
{
    "data": [
        {
            "id": 1,
            "title": "Task Title 1",
            "description": "Task Description 1",
            "completed": false
        },
        {
            "id": 2,
            "title": "Task Title 2",
            "description": "Task Description 2",
            "completed": true
        }
    ],
    "paging": {
        "currentPage": 1,
        "totalPage": 10,
        "size": 10
    }
}
```


## Get Task By Id

Endpont : GET /api/tasks/{id}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success):

```json
{
    "data": {
        "id": 1,
        "title": "Task Title",
        "description": "Task Description",
        "completed": false
    }
}
```

Response Body (Failed, 404):

```json
{
    "errors": "Task is not found"
}
```


## Create Task

Endpoint : POST /api/tasks

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
    "title": "Task Title",
    "description": "Task Description"
}
```

Response Body (Success):

```json
{
    "data": {
        "id": 1,
        "title": "Task Title",
        "description": "Task Description",
        "completed": false
    }
}
```

Response Body (Failed):

```json
{
    "errors": "Title must not blank!"
}
```


## Update Task

Endpoint : PUT /api/tasks/{id}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
    "title": "Update Task Title",
    "description": "Update Task Description",
    "completed": true
}
```

Response Body (Success):

```json
{
    "data": {
        "id": 1,
        "title": "Update Task Title",
        "description": "Update Task Description",
        "completed": true
    }
}
```

Response Body (Failed):

```json
{
    "errors": "Title must not blank!"
}
```


## Delete Task

Endpoint : PUT /api/tasks/{id}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success):

```json
{
    "data": "OK"
}
```

Response Body (Failed):

```json
{
    "errors": "Task is not found!"
}
```