# Test

## 1. REST API Design

### a. Create a new task:
   - **Endpoint:** `POST /api/tasks`
   - **Request:**
     ```json
     {
       "title": "Task Title",
       "description": "Task Description"
     }
     ```
   - **Response:**
     ```json
     {
       "id": 1,
       "title": "Task Title",
       "description": "Task Description",
       "completed": false
     }
     ```

### b. Mark a task as completed:
   - **Endpoint:** `PUT /api/tasks/{id}`
   - **Request:**
     ```json
     {
       "completed": true
     }
     ```
   - **Response:**
     ```json
     {
       "id": 1,
       "title": "Task Title",
       "description": "Task Description",
       "completed": true
     }
     ```

### c. Retrieve a list of tasks:
   - **Endpoint:** `GET /api/tasks`
   - **Response:**
     ```json
     [
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
     ```

## 2. Authentication

Implemented token-based authentication using Spring Security with JWT. Secure endpoints by requiring a valid token in the `Authorization` header. Used libraries like `jjwt` for JWT handling.

## 3. Database Design

Designed a database schema with tables for `users` and `tasks`. Established a foreign key relationship between them. 

```sql
CREATE TABLE public.tasks
(
    id integer NOT NULL DEFAULT nextval('tasks_id_seq'::regclass),
    title character varying(255) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    completed boolean DEFAULT false,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone,
    user_id integer,
    CONSTRAINT tasks_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.users
(
    id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    username character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(100) COLLATE pg_catalog."default" NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    token character varying(100) COLLATE pg_catalog."default",
    token_expired_at bigint,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_token_key UNIQUE (token),
    CONSTRAINT users_username_key UNIQUE (username)
);
```

## 4. SQL Queries

SQL query to retrieve all incomplete tasks:

```sql
SELECT * FROM tasks WHERE completed = false;
```

## 5. Problem-Solving

Java function to sum even numbers in a list:

```java
public class MathUtils {
    public static int sumOfEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(num -> num % 2 == 0)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
```

## 6. Error-Handling

Implemented error handling in Spring Boot using @ControllerAdvice for global exception handling. Handled exceptions related to HTTP client errors (4xx) and server errors (5xx) and responded with a generic error message.

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = {HttpClientErrorException.class, HttpServerErrorException.class})
    public ResponseEntity<String> handleHttpClientException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error connecting to external service");
    }
}
```