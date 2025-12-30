# Service SOAP - Gestion de Comptes Bancaires

Service SOAP Spring Boot pour la gestion de comptes bancaires, compatible avec l'application Android SOAPCompteApp.

## Prérequis

- Java 17 ou supérieur
- Maven 3.6+

## Installation et Démarrage

### Option 1 : Avec Maven (recommandé)

```bash
cd soap-service
mvnw clean install
mvnw spring-boot:run
```

### Option 2 : Avec Maven installé

```bash
cd soap-service
mvn clean install
mvn spring-boot:run
```

## Endpoints

- **Service SOAP** : http://localhost:8082/services/ws
- **WSDL** : http://localhost:8082/services/ws?wsdl
- **Console H2** : http://localhost:8082/services/h2-console

## Opérations SOAP disponibles

### 1. getComptes()
Récupère la liste de tous les comptes

### 2. getCompteById(Long id)
Récupère un compte par son ID

### 3. createCompte(Double solde, String type)
Crée un nouveau compte
- **solde** : Montant initial
- **type** : "COURANT" ou "EPARGNE"

### 4. updateCompte(Long id, Double solde, String type)
Met à jour un compte existant

### 5. deleteCompte(Long id)
Supprime un compte par son ID

### 6. getComptesByType(String type)
Récupère les comptes par type

## Base de données

Le service utilise une base de données H2 en mémoire avec des données de test initialisées automatiquement.

### Données initiales :
- Compte 1 : 2000.0 DH (COURANT)
- Compte 2 : 30007.0 DH (COURANT)
- Compte 3 : 45999.688 DH (EPARGNE)

## Test du service

### Avec SoapUI ou Postman

URL : http://localhost:8082/services/ws?wsdl

Exemple de requête SOAP pour getComptes :
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.soapservice.projet.ma/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:getComptes/>
   </soapenv:Body>
</soapenv:Envelope>
```

Exemple de requête SOAP pour createCompte :
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.soapservice.projet.ma/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:createCompte>
         <solde>5000.0</solde>
         <type>COURANT</type>
      </ws:createCompte>
   </soapenv:Body>
</soapenv:Envelope>
```

## Structure du projet

```
soap-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ma/projet/soapservice/
│   │   │       ├── SoapServiceApplication.java
│   │   │       ├── config/
│   │   │       │   ├── DataInitializer.java
│   │   │       │   └── WebServiceConfig.java
│   │   │       ├── entity/
│   │   │       │   ├── Compte.java
│   │   │       │   └── TypeCompte.java
│   │   │       ├── repository/
│   │   │       │   └── CompteRepository.java
│   │   │       └── ws/
│   │   │           └── CompteService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Technologies utilisées

- Spring Boot 3.1.5
- Apache CXF (JAX-WS)
- Spring Data JPA
- H2 Database
- Maven

## Port et Configuration

Le service démarre sur le port **8082** avec le context path **/services**

Pour modifier ces paramètres, éditez le fichier `application.properties`.
