# Technologies

- java 21
- spring boot 3
- docker
- git

# commands

## docker

``` bash
docker build -t microservice .
```

``` bash
docker run -d -t --name microservice --network=ing_software_ii --env-file .env -p 8081:8081 microservice
```
