#Banco-web-app - backend

API REST desarrollada con **Java** y **Spring Boot** para la creacion de cuentas bancarias y envio de transferencias

##Tecnologías utilizadas
- Java 25
- Spring boot
- Spring security + JWT
- H2 Database


##Seguridad
La autenticación se realiza mediante tokens JWT firmados dinamicamente con la clave secreta gestionada por variables de entorno (`JWT_SECRET`)