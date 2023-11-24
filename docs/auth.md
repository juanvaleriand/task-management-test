# User API Spec

## Register User

Endpoint : POST /api/auth/register

Request Body :

```json
{
  "username" : "juanvaleriand",
  "password" : "123456!",
  "name" : "Juan Valerian Delima" 
}
```

Response Body (Success) :

```json
{
  "data" : "OK"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Username must not blank!"
}
```

## Login User

Endpoint : POST /api/auth/login

Request Body :

```json
{
  "username" : "juanvaleriand",
  "password" : "123456!" 
}
```

Response Body (Success) :

```json
{
  "data" : {
    "token" : "TOKEN",
    "expiredAt" : 2342342423423 // milliseconds
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Username or password wrong"
}
```


## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : "OK"
}
```