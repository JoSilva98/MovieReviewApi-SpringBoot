# Movie Review API - SpringBoot
***
### SUMMARY
***
Movie review API was created with the purpose of giving its users the ability to search, 
and rate movies. It also has got an admin role that can add, update and remove movies,
writers, actors, directors and genres.
<br/><br/>

### API LINKS
***
Path should be specific to localhost or to:
https://moviereview-api.herokuapp.com

Swagger documentation:
https://moviereview-api.herokuapp.com/swagger-ui/index.html
<br/><br/>

### EXTERNAL API
***
Our app is powered by IMDb API.
<br/><br/>

### IMPLEMENTATIONS
***
- Model Relationships;
- Tests;
- Spring Security with JWT (Authentication/Authorization);
- External API;
- Swagger;
- Postman Collection;
- Docker Compose;
- Redis Cloud;
- Heroku PostgreSQL DB deployment;
- Heroku Deployment.
  <br/><br/>

### METHODS
***
| Request  | Description                                 |
|----------|---------------------------------------------|
| `GET`    | Returns information of one or more records. |
| `POST`   | Used to create new record in DB.            |
| `PUT`    | Updates date from a record.                 |
| `DELETE` | Deletes a record from the DB.               |
<br/>

### RESPONSES
***
| Responses | Description                          |
|-----------|--------------------------------------|
| `200`     | Request executed successfully.       |
| `400`     | Validation errors.                   |
| `403`     | Forbidden Access.                    |
| `404`     | Searched record not found.           |
| `405`     | Method not implemented.              |
| `409`     | Conflict trying to save same record. |
| `500`     | Server error.                        |
<br/>

### AUTHENTICATION - AUTH0
***
Our API uses [AuthO](https://auth0.com/) as a way of authentication/authorization.
<br/><br/>
**Sign Up:**

| Request | Description | Link          |
|---------|-------------|---------------|
| `POST`  | `SignUp`    | /api/v1/users |

Required JSON body (**Sign Up** endpoint):
```
{
    "firstName": "Example",
    "lastName": "Example",
    "email": "example@email.com",
    "dateOfBirth": "1960-09-07",
    "password": "password"
}
```
**Note:** When someone signs up, it's role is automatically **User**.
<br/><br/>

**Login:**

| Request | Description | Link      |
|---------|-------------|-----------|
| `POST`  | `Login`     | /login    |

Required JSON body (**Login** endpoint):
```
{
    "email": "example@email.com",
    "password": "password"
}
```
<br/>

## RESOURCE GROUPS
***
**Note:** All the endpoints that require parameters should be called this way:
```
api/v1/movie/search/genre?genre=drama
```
Where `genre=drama` is the parameter.
<br/><br/>

### MOVIE ( /api/v1/movie )
***
| Request  | Description     | Link               |
|----------|-----------------|--------------------|
| `GET`    | Get Movies List | /api/v1/movie      |
| `DELETE` | Delete Movie    | /api/v1/movie/{id} |

| Request  | Description    | Link               |
|----------|----------------|--------------------|
| `POST`   | Add Movie      | /api/v1/movie      |
| `PUT`    | Update Movie   | /api/v1/movie/{id} |

Required JSON body (**Add Movie** and **Update Movie** endpoints):
```
{
    "title": "The Shawshank Redemption",
    "fullTitle": "The Shawshank Redemption (1994)",
    "image": "https://imdb-api.com/images/original/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_Ratio0.6751_AL_.jpg",
    "year": "1994",
    "releaseDate": "1994-10-14",
    "runtimeStr": "2h 22min",
    "contentRating": "PG",
    "actorList": [
        {
            "id": 1
        }
    ],
    "directorList": [
        {
            "id": 1
        }
    ],
    "writerList": [
        {
            "id": 1
        }
    ],
    "genreList": [
        {
            "id": 1
        }
    ]
}
```
<br/>

### MOVIE SEARCH ( /api/v1/movie/search )
***
| Request | Description            | Link                        | Parameters                     |
|---------|------------------------|-----------------------------|--------------------------------|
| `GET`   | Search by              | /api/v1/movie/search        | id, title, year, contentrating |
| `GET`   | Search Movie by Actor  | /api/v1/movie/search/actor  | name                           |
| `GET`   | Search by Genre        | /api/v1/movie/search/genre  | genre                          |
| `GET`   | Search Movie by Rating | /api/v1/movie/search/rating | id                             |
<br/>

### ACTOR ( /api/v1/actor )
***
| Request  | Description     | Link               |
|----------|-----------------|--------------------|
| `GET`    | Get Actors List | /api/v1/actor      |
| `DELETE` | Delete Actor    | /api/v1/actor/{id} |

| Request | Description  | Link               |
|---------|--------------|--------------------|
| `POST`  | Add Actor    | /api/v1/actor      |
| `PUT`   | Update Actor | /api/v1/actor/{id} |

Required JSON body (**Add Actor** and **Update Actor** endpoints):
```
{
    "image": "https://upload.wikimedia.org/wikipedia/en/9/9a/Trollface_non-free.png",
    "name": "Example"
}
```
<br/>

### DIRECTOR ( /api/v1/director )
***
| Request  | Description        | Link                  |
|----------|--------------------|-----------------------|
| `GET`    | Get Directors List | /api/v1/director      |
| `DELETE` | Delete Director    | /api/v1/director/{id} |

| Request | Description     | Link                  |
|---------|-----------------|-----------------------|
| `POST`  | Add Director    | /api/v1/director      |
| `PUT`   | Update Director | /api/v1/director/{id} |

Required JSON body (**Add Director** and **Update Director** endpoints):
```
{
    "name": "Example"
}
```
<br/>

### WRITER ( /api/v1/writer )
***
| Request  | Description      | Link                |
|----------|------------------|---------------------|
| `GET`    | Get Writers List | /api/v1/writer      |
| `DELETE` | Delete Writer    | /api/v1/writer/{id} |

| Request | Description     | Link                |
|---------|-----------------|---------------------|
| `POST`  | Add Writer      | /api/v1/writer      |
| `PUT`   | Update Writer   | /api/v1/writer/{id} |

Required JSON body (**Add Writer** and **Update Writer** endpoints):
```
{
    "name": "Example"
}
```
<br/>

### GENRE ( /api/v1/genre )
***
| Request  | Description     | Link               |
|----------|-----------------|--------------------|
| `GET`    | Get Genres List | /api/v1/genre      |
| `DELETE` | Delete Genre    | /api/v1/genre/{id} |

| Request | Description    | Link               |
|---------|----------------|--------------------|
| `POST`  | Add Genre      | /api/v1/genre      |
| `PUT`   | Update Genre   | /api/v1/genre/{id} |

Required JSON body (**Add Genre** and **Update Genre** endpoints):
```
{
    "value": "Drama"
}
```
<br/>

### USER ( /api/v1/users )
***
| Request  | Description    | Link               |
|----------|----------------|--------------------|
| `GET`    | Get Users List | /api/v1/users      |
| `DELETE` | Delete User    | /api/v1/users/{id} |

| Request | Description | Link               |
|---------|-------------|--------------------|
| `PUT`   | Update User | /api/v1/users/{id} |

Required JSON body (**Update User** endpoint):
```
{
    "firstName": "Example",
    "lastName": "Example",
    "email": "example@email.com",
    "dateOfBirth": "1976-10-02",
    "password": "password"
}
```
<br/>

### USER SEARCH ( /api/v1/users )
***
| Request  | Description    | Link                 | Parameters                         |
|----------|----------------|----------------------|------------------------------------|
| `GET`    | Get User by Id | /api/v1/users/{id}   |                                    |
| `GET`    | Search Users   | /api/v1/users/search | roleid, firstname, lastname, email |
<br/>

### ROLE ( /api/v1/users/roles )
***
| Request  | Description    | Link                     |
|----------|----------------|--------------------------|
| `GET`    | Get Roles List | /api/v1/users/roles      |
| `DELETE` | Delete Role    | /api/v1/users/roles/{id} |

| Request  | Description   | Link                     |
|----------|---------------|--------------------------|
| `POST`   | Add Role      | /api/v1/users/roles      |
| `PUT`    | Update Role   | /api/v1/users/roles/{id} |

Required JSON body (**Add Role** and **Update Role** endpoints):
```
{
    "roleName": "User"
}
```
<br/>

### FAVOURITES ( /api/v1/users/favourite )
***
| Request  | Description         | Link                         | Parameters      |
|----------|---------------------|------------------------------|-----------------|
| `GET`    | Get Favourites List | /api/v1/users/favourite/{id} |                 |
| `POST`   | Add Favourite       | /api/v1/users/favourite      | userid, movieid |
| `DELETE` | Delete Favourite    | /api/v1/users/favourite      | userid, movieid |
<br/>

### REVIEW ( /api/v1/review )
***
| Request  | Description      | Link           |
|----------|------------------|----------------|
| `GET`    | Get Reviews List | /api/v1/review |

| Request  | Description     | Link                |
|----------|-----------------|---------------------|
| `POST`   | Add Review      | /api/v1/review      |
| `PUT`    | Update Review   | /api/v1/review/{id} |

Required JSON body (**Add Review** and **Update Review** endpoints):
```
{
    "userId": "1",
    "movieId": "1",
    "review": "this movie is really really really bad",
    "ratingId": 1
}
```

| Request  | Description     | Link           |
|----------|-----------------|----------------|
| `DELETE` | Delete Review   | /api/v1/review |

Required JSON body (**Delete Review** endpoint):
```
{
    "id": 2,
    "userId": "1",
    "movieId": "1",
    "review": "This movie is really bad",
    "ratingId": 1
}
```
<br/>

### REVIEW LIST ( /api/v1/review/byuser )
***
| Request  | Description             | Link                       |
|----------|-------------------------|----------------------------|
| `GET`    | Get User's Reviews List | /api/v1/review/byuser/{id} |
<br/>

### RATING ( /api/v1/rating )
***
| Request  | Description      | Link                |
|----------|------------------|---------------------|
| `GET`    | Get Ratings List | /api/v1/rating      |
| `DELETE` | Delete Rating    | /api/v1/rating/{id} |

| Request  | Description     | Link                |
|----------|-----------------|---------------------|
| `POST`   | Add Rating      | /api/v1/rating      |
| `PUT`    | Update Rating   | /api/v1/rating/{id} |

Required JSON body (**Add Rating** and **Update Rating** endpoints):
```
{
    "rate": "1"
}
```
<br/>

### ROLES
***
| Role  | Id  |
|-------|-----|
| User  | 1   |
| Admin | 2   |
<br/>

### STRUCTURE
___
The API contains: 
* **Command** package - contains the **DTO** (Data Transfer Object) classes;
* **Config** package - contains the **BeansConfig** and **CheckAuth** classes;
* **Controller** package - contains the **Controller** classes used for routes;
* **Converter** package - contains the classes that convert an **Entity** to a 
**DTO** and vice-versa;
* **Dataloader** package - contains a class that implements the interface 
**ApplicationRunner** and populates the database when the program is started;
* **Exception** package - contains the custom exceptions and the **AppExceptionHandler**
class;
* **Persistence** package - contains the **Model** package and the **Repository**
package:
  * **Model** package - contains the **Entity** classes;
  * **Repository** package - contains the repository interfaces that allow the
  connection with the **Database**;
* **Security** package - contains the implementation of **JWT**;
* **Service** package - contains the **Service** classes used for business logic;
* **application.properties** file - allows the connection to the **Redis** container;
* **application.yml** file - allows the connection to the **Database**;
* **docker-compose.yml** file - creates the **Database** container;
* **pom.xml** file - contains the dependencies used in the API.