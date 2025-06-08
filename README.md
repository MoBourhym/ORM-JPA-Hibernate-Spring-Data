# Patient - Spring Data JPA & Hibernate - PARTIE 1

## 📋 Description

Cette application Spring Boot démontre l'utilisation de **Spring Data JPA** avec **Hibernate** pour la gestion d'une base de données de patients. Il s'agit de la première partie d'un TP pratique sur l'ORM (Object-Relational Mapping) avec Spring Data.

## 🎯 Objectifs du TP

- Découvrir Spring Data JPA et Hibernate
- Comprendre les annotations JPA (@Entity, @Id, @GeneratedValue, etc.)
- Maîtriser les opérations CRUD (Create, Read, Update, Delete)
- Implémenter des requêtes personnalisées avec Spring Data
- Utiliser Lombok pour réduire le code boilerplate

## 🏗️ Architecture de l'application

### 📦 Structure du projet

```
src/main/java/com/spring/patient/
├── PatientApplication.java          # Classe principale Spring Boot
├── entities/
│   └── Patient.java                 # Entité JPA Patient
└── repository/
    └── PatientRepository.java       # Repository Spring Data
```

### 🗃️ Modèle de données

#### Entité Patient

```java
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    private String name;                // Nom du patient
  
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;          // Date de naissance
  
    private boolean sickness;          // État de santé (malade ou non)
  
    private int score;                 // Score de santé
}
```

## 🔧 Technologies utilisées

| Technologie               | Version | Description                               |
| ------------------------- | ------- | ----------------------------------------- |
| **Spring Boot**     | 3.2.4   | Framework principal                       |
| **Spring Data JPA** | 3.2.4   | Abstraction pour JPA                      |
| **Hibernate**       | 6.4.4   | Implémentation JPA/ORM                   |
| **MySQL**           | 8.x     | Base de données relationnelle            |
| **H2 Database**     | -       | Base de données en mémoire (pour tests) |
| **Lombok**          | 1.18.38 | Réduction du code boilerplate            |
| **Java**            | 17+     | Langage de programmation                  |

## ⚙️ Configuration

### Base de données MySQL

```properties
# Configuration MySQL (active)
spring.datasource.url=jdbc:mysql://localhost:3306/patients-db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=password

# Configuration JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true
```

### Base de données H2 (pour tests)

```properties
# Configuration H2 (commentée)
# spring.datasource.url=jdbc:h2:mem:patient-db
# spring.h2.console.enabled=true
# spring.datasource.driver-class-name=org.h2.Driver
```

## 🚀 Fonctionnalités implémentées

### 1.  Création de patients

L'application crée automatiquement 6 patients de test au démarrage :

- **Famille Morsi** : Line, Omar, Hiba (non malades)
- **Famille Nouri** : Khaled, Yasmine, Hajar (malades)

### 2. Consultation des patients

- **Affichage de tous les patients** avec `findAll()`
- **Recherche par ID** avec `findById(Long id)`

### 3. Mise à jour des patients

- Modification des données d'un patient existant
- Persistance automatique avec `save()`

### 4. Suppression de patients

- Suppression par ID avec `deleteById(Long id)`

### 5. 🔍 Recherches personnalisées

- **Filtrage par état de santé** : `findBySickness(boolean sickness)`
- Utilisation des **Query Methods** de Spring Data

## 📊 Opérations CRUD démontrées

| Opération       | Méthode                      | Description                        |
| ---------------- | ----------------------------- | ---------------------------------- |
| **Create** | `save(Patient)`             | Créer un nouveau patient          |
| **Read**   | `findAll()`, `findById()` | Lire les données                  |
| **Update** | `save(Patient)`             | Mettre à jour un patient existant |
| **Delete** | `deleteById(Long)`          | Supprimer un patient               |

## Prochaines étapes du TP

Cette première partie couvre les bases. Les parties suivantes pourraient inclure :

- Requêtes JPQL personnalisées
- Relations entre entités (@OneToMany, @ManyToOne)
- Pagination et tri
- Validation des données
- API REST avec Spring Web
- Tests unitaires et d'intégration
