# Book Store Microservice

A repository that implement a simple online bookstore inventory system. The microservice interacts with a PG SQL 
database to store and retrieve data. 

## 1. Dependencies
* Docker (20.10.7) - https://docs.docker.com/get-docker/
* Java (1.8)
* Maven

## 2. Steps to Start Microservice
An entrypoint file is created and by running the following command, the following will be performed:
* Create a Postgres Docker container and database
* Create a Springboot jar
* Run jar to start the Springboot microservice
```
sh entrypoint.sh start
```
The microservice URL is available at http://localhost:3000 and the Swagger UI is available at http://localhost:3000/swagger-ui/

## 3. Microservice Service Contract

The postman collection is available in `Bookstore.postman_collection.json`

The following is the microservice API service contract for the book store service:
### 3.1. Checking status of the server
**Request:** POST

**Endpoint:** 
```
http://localhost:3000/api/v1/status
```
**Request Payload:** Not applicable

**Sample request:** 
```agsl
curl --location 'localhost:3000/api/v1/status'
```

**Sample response (status code: 200):**
```agsl
{
    "message": "Okay",
    "result": null
}
```

### 3.2. Add a book to bookstore
**Request:** POST

**Endpoint:**
```
localhost:3000/api/v1/bookstore/book
```

**Request Payload:**
```agsl
{
    "title": "title of the book",
    "author": "author of the book",
    "quantity": <quantity of the book>,
    "price": <price of the book>
}
```

**Sample request:**
```agsl
curl --location 'localhost:3000/api/v1/bookstore/book' \
--header 'Content-Type: application/json' \
--data '{
    "title": "book2",
    "author": "author2",
    "quantity": 20,
    "price": 5.5
}'
```

**Sample response (status code: 200):**
```agsl
{
    "message": "Successfully added a book",
    "result": {
        "book_id": 1,
        "title": "book2",
        "author": "author2",
        "quantity": 20,
        "price": 5.5
    }
}
```

**Sample response when missing or incomplete payload (status code: 400):**
```agsl
{
    "message": "Incorrect payload given: title: book1; author: null; price: 5.5; quantity: 10",
    "result": null
}
```

### 3.3. Update a book in bookstore
**Request:** PATCH

**Endpoint:**
```
localhost:3000/api/v1/bookstore/book/{bookTitle}
```

**Request Payload:**
```agsl
{
    "author": "author of the book",
    "quantity": <quantity of the book>,
    "price": <price of the book>
}
```

**Sample request:**
```agsl
curl --location --request PATCH 'localhost:3000/api/v1/bookstore/book/book2' \
--header 'Content-Type: application/json' \
--data '{
    "author": "author1",
    "quantity": 30,
    "price": 5.5
}'
```

**Sample response (status code: 200):**
```agsl
{
    "message": "Successfully updated a book",
    "result": {
        "book_id": 1,
        "title": "book2",
        "author": "author1",
        "quantity": 30,
        "price": 5.5
    }
}
```

**Sample response when book is not present (status code: 400):**
```agsl
{
    "message": "No book with title: book3",
    "result": null
}
```

### 3.4. Get quantity of a book in bookstore
**Request:** GET

**Endpoint:**
```
localhost:3000/api/v1/bookstore/book/book-quantity/{bookTitle}
```

**Request Payload:** Not applicable

**Sample request:**
```agsl
curl 'localhost:3000/api/v1/bookstore/book/book-quantity/book2' 
```

**Sample response (status code: 200):**
```agsl
{
    "message": "Successfully retrieved a book's quantity",
    "result": 30
}
```

**Sample response when book is not present (status code: 400):**
```agsl
{
    "message": "No book with title: book3",
    "result": null
}
```

### 3.5. Get all books
**Request:** GET

**Endpoint:**
```
localhost:3000/api/v1/bookstore/books
```

**Request Payload:** Not applicable

**Sample request:**
```agsl
curl 'localhost:3000/api/v1/bookstore/books'
```

**Sample response (status code: 200):**
```agsl
{
    "message": "Successfully listed books",
    "result": [
        "book2"
    ]
}
```

### 3.6. Get all books by author
**Request:** GET

**Endpoint:**
```
localhost:3000/api/v1/bookstore/books?author={author}
```

**Request Payload:** Not applicable

**Sample request:**
```agsl
curl 'localhost:3000/api/v1/bookstore/books?author=author1'
```

**Sample response (status code: 200):**
```agsl
{
    "message": "Successfully listed books by author",
    "result": [
        "book2"
    ]
}
```

### 3.7. Get all books by price range
**Request:** GET

**Endpoint:**
```
localhost:3000/api/v1/bookstore/books?upperThreshold={price upper threshold}&lowerThreshold={price lower threshold}
```

**Request Payload:** Not applicable

**Sample request:**
```agsl
curl 'localhost:3000/api/v1/bookstore/books?upperThreshold=50.0&lowerThreshold=1.0' 
```

**Sample response (status code: 200):**
```agsl
{
    "message": "Successfully listed books by price range",
    "result": [
        "book2"
    ]
}
```

### 3.8. Delete a book in bookstore
**Request:** DELETE

**Endpoint:**
```
localhost:3000/api/v1/bookstore/book/{bookTitle}
```

**Request Payload:** Not applicable

**Sample request:**
```agsl
curl --location --request DELETE 'localhost:3000/api/v1/bookstore/book/book2' \
--data ''
```

**Sample response (status code: 200):**
```agsl
{
    "message": "Successfully deleted a book",
    "result": {
        "book_id": 1,
        "title": "book2",
        "author": "author1",
        "quantity": 30,
        "price": 5.5
    }
}
```

**Sample response when book is not present (status code: 400):**
```agsl
{
    "message": "No book with title: book3",
    "result": null
}
```

## 4. Steps to Stop Microservice
Kill the running program and run the following command to stop and clean container
```
sh entrypoint.sh stop
```