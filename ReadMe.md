# Technologies

## docker

``` bash
docker build -t microservice .
```

``` bash
docker run -d -t --name microservice --network=ing_software_ii --env-file .env -p 8080:8080 microservice
```

## gcp

``` bash
gcloud app deploy
```
