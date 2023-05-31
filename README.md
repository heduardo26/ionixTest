# IonixTest Test
## _Evaluación técnica para Carlos Escalona por parte de Ionix_
-------------------------------------------------------------------
### Tecnologías usadas:
- Java 11
- Spring Boot 2.7.12
- Base de Datos MySqlServer
- Lombok
- Spring Security
- Junit
- Mockito

### Arquitectura
Se implementó una arquitectura N-tier o de capas, en la cual existe un controlador que ejecuta los servicios que son clases que contienen la lógica de negocio,
estas clases hacen uso de los servicios de las interfaces repository.
-------------------------------------------------------------------

## Instalación/Ejecución
- Descargar o clonar el proyecto
- Modificar el archivo properties y colocar los parametros correspondientes de la BD de MySql (url,username,password).
- Ejecutar el proyecto, puerto por defecto 8080.
-------------------------------------------------------------------

## Funcionamiento

Para implementar la primera parte __ApiRest de Usuarios__ se usa la url http://localhost:8080/api/v1/user y a continuación están 
cada uno de los respectivos metodos.

- Get All Users
  * http://localhost:8080/api/v1/user

- Get User By Email
  * http://localhost:8080/api/v1/user/{email}

- Post Create User (Protected) 
  * http://localhost:8080/api/v1/user
  * {
      "userName":"UserNme",
      "name":"Name",
      "email":"Email@gmail.com",
      "phone":"55555"
    }

- Delete User (Protected) 
  * http://localhost:8080/api/v1/user/{email}

Para los métodos marcados como (Protected) se debe usar **Basic Auth**, el usuario y password están en el properties en la sección **##Security**. 
  

Para implementar la segunda parte, el consumo de una Api Externa se usa la siguiente ruta
- Post External Api (Protected)
  * http://localhost:8080/api/v1/external?param=1-9
  * El valor del parametro será *1-9* y nos devolvera el siguiente response
  * {
       "responseCode": 200,
       "description": "OK",
       "elapsedTime": 1178,
       "result": {
       "registerCount": 3
    }
   

### ¡Importante!
Debido a que no tuve tiempo de implementar Swagger para este proyecto coloque adjunto una PostMan Collection (__Ionix.postman_collection.json__)
necesaria para implementar el Api Rest.   
  