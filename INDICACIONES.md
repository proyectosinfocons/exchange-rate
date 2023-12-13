# Reto Backend Pichincha

## Pruebas con Postman

Para probar la API con Postman, puede importar la colección de endpoints provista en el archivo `Reto Backend Exchange Rate.postman_collection.json`.

## Crear contenedor de mongoDB

````
docker run -d --name some-mongo -p 27017:27017 \
-e MONGO_INITDB_ROOT_USERNAME=mongoadmin \
-e MONGO_INITDB_ROOT_PASSWORD=secret \
mongo
````

## Data Inicial MongoDB

````
use reto-backend

db.getCollection('roles').insertOne({nombre: "ADMIN"})
db.getCollection('roles').insertOne({nombre: "USER"})



db.getCollection('usuarios').insertOne({
"usuario" : "arauzo",
"clave" : "$2a$10$ju20i95JTDkRa7Sua63JWOChSBc0MNFtG/6Sps2ahFFqN.HCCUMW.",
"estado" : true,
"roles" : [
{
"_id" : ObjectId("6577cf7c1233e776f685ca53")
}
]
})


db.getCollection('usuarios').insertOne({
"usuario" : "joel",
"clave" : "$2a$10$ju20i95JTDkRa7Sua63JWOChSBc0MNFtG/6Sps2ahFFqN.HCCUMW.",
"estado" : true,
"roles" : [
{
"_id" : ObjectId("6577cf991233e776f685ca55")
}
]
})

db.getCollection('roles').find({})
db.getCollection('usuarios').find({})
db.getCollection('exchange_rates').find({})
````

