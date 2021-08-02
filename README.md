# Film Service
## _Reel Films Notification using Web sockets and Reactive_

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

## _Reactive Spring WebFlux API + Real-time React_

This is written as microservice API for managing films using the event sourcing pattern of microservice.

Prerequisites: 
- Java 8
- - ✨Maven
## Features

- Add a film service using POST reactive API
- Get a film by slug
- Get All the films
- Add notifications via web-sockets whenever a new film is added

## Tech

The example uses a number of tech as follows:

- [React.js] - HTML enhanced for web apps!
- [Java 11] - Backend
- [springboot reactive framework] - Reactive framework from spring 
- [npm] - run the frontend build
- [maven] - build tool for backend
- [EXT JS](https://www.sencha.com) - To power React JS
- [HTML and JS] - duh


## Installation

This service requires [Node.js](https://nodejs.org/) v10+  [Java ](https://www.oracle.com/ae/java/technologies/javase-jdk11-downloads.html)v11+, [ReactJS](https://reactjs.org) to run.

Install the dependencies and devDependencies and start the server.

```sh 
git clone https://github.com/ishusinghal/reel-cinemas-service.git
git checkout react-app
cd reel-cinemas-service
./mvnw spring-boot:run
```
The data migration will create 3 film entries for us to test.

For frontend environments...

```sh
cd react-app
npm run start
```

## Plugins


| Plugin | Download link |
| ------ | ------ |
| Postman | [https://www.postman.com/downloads/][PlDb] |


## Testing


Open your favorite Terminal and run these commands.

First Tab:

```sh
curl -X POST \
  http://localhost:8080/films \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 0b38fcbf-c198-eac7-a4df-32cc89156220' \
  -d '{
  "name" : "Avengers",
  "description" : "A BlockBluster movie",
  "releaseDate" : "2020-07-07 22:10",
  "rating":5,
  "price":25.2,
  "country":"America",
  "genre":["THRILLER","ACTION","SUPERHERO"],
  "slugLine":"AVENGER",
  "comments" : [
	  	{
	  		"user" : "Ishu",
	  		"comment" : "A must watch movie"
	  	}
  	] 
}'
```
Open http://localhost:8080/ws.html which is created to get notifications from the websockets. You should see a alert. 

Second Tab: See The movies

```sh
curl -X GET \
  http://localhost:8080/films \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 4a91e476-bdf5-5f74-ef39-af3d47a853eb'
```

(optional) Third: get a movie by slug

```sh
curl -X GET \
  http://localhost:8080/films/BlackWidow \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 5211d69d-a809-07f7-a94e-31c21acb6617'
```

## License

MIT
**Free Software, Hell Yeah!**
## Connect with me
   |linkedin|: [https://www.linkedin.com/in/ishu-singhal199]
  
