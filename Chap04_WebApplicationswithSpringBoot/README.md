# CHAPTER 4, Web Applications with Spring Boot

## Remarks 
- main folder: Spring Boot Web RESTful API
- client subfolder: Spring Boot Web Client


## RESTful API
### 1. http://localhost:8080/api/todo
<img src="./img/httpapi.png" style="width:50%;"/>

### 2. curl -i http://localhost:8080/api/todo
<img src="./img/restapi-i.png" style="width:50%;"/>

### 3. curl -i -X POST -H "Content-Type: application/json" -d '{"description":"Read the Pro Spring Boot 2nd Edition Book"}' http://localhost:8080/api/todo

<img src="./img/restapi-post.png" style="width:50%;"/>

### 4. curl -i -X PUT -H "Content-Type: application/json" -d '{"description":"Take the dog and the cat for a walk", "id":"2d051b67-7716-4ee6-9c45-1de939fa579f"}' http://localhost:8080/api/todo

<img src="./img/restapi-put.png" style="width:50%;"/>

### 5. curl -i -X PATCH http://localhost:8080/api/todo/f253e504-d2ed-4b4a-96db-f83463b85de2

<img src="./img/restapi-patch.png" style="width:50%;"/>

### 6. curl -i -X DELETE http://localhost:8080/api/todo/f253e504-d2ed-4b4a-96db-f83463b85de2

<img src="./img/restapi-delete.png" style="width:50%;"/>

### 7. curl -i -X POST -H "Content-Type: application/json" -d '{"description":""}' http://localhost:8080/api/todo

<img src="./img/restapi-error.png" style="width:50%;"/>

## RESTful API-Client
### 
<img src="./img/restapi-client.png" style="width:100%;" />
