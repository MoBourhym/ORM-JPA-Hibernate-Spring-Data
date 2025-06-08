# Patient - Spring Data JPA & Hibernate - PARTIE 1

Ce projet démontre l'utilisation de **Spring Data JPA** avec **Hibernate** pour la gestion des patients dans une application Spring Boot. Il illustre les concepts clés de l'ORM (Object-Relational Mapping) et les opérations CRUD.

## Table des Matières

- [Architecture du Projet](#architecture-du-projet)
- [Technologies Utilisées](#technologies-utilisées)
- [Structure du Projet](#structure-du-projet)
- [Configuration de la Base de Données](#configuration-de-la-base-de-données)
- [Explication du Code](#explication-du-code)
- [Installation et Exécution](#installation-et-exécution)
- [Tests et Utilisation](#tests-et-utilisation)
- [Concepts Clés](#concepts-clés)

## Architecture du Projet

Cette application suit l'architecture en couches de Spring Boot :

```
┌─────────────────────────┐
│   Contrôleur Web       │  (Non implémenté dans cette démo)
├─────────────────────────┤
│   Couche Service       │  (Logique métier)
├─────────────────────────┤
│   Couche Repository    │  (Spring Data JPA)
├─────────────────────────┤
│   Couche Entité        │  (JPA Entities)
├─────────────────────────┤
│   Base de Données      │  (MySQL)
└─────────────────────────┘
```

## Technologies Utilisées

- **Spring Boot 3.3.5** - Framework principal
- **Spring Data JPA** - Abstraction pour l'accès aux données
- **Hibernate** - Implémentation JPA/ORM
- **MySQL 8.0** - Base de données relationnelle
- **Maven** - Gestionnaire de dépendances
- **Java 17** - Langage de programmation

## Structure du Projet

```
src/main/java/com/spring/patient/
├── PatientApplication.java          # Classe principale Spring Boot
├── entities/
│   └── Patient.java                 # Entité JPA Patient
└── repository/
    └── PatientRepository.java       # Interface Repository Spring Data
```

## Configuration de la Base de Données

### application.properties

```properties
# Configuration MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/patients-db
spring.datasource.username=root
spring.datasource.password=

# Configuration JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Configuration H2 (désactivée)
# spring.h2.console.enabled=true
# spring.datasource.url=jdbc:h2:mem:patient-db
```

**Explication des propriétés :**

- `ddl-auto=update` : Hibernate met à jour automatiquement le schéma de la base de données
- `show-sql=true` : Affiche les requêtes SQL générées dans la console
- `format_sql=true` : Formate les requêtes SQL pour une meilleure lisibilité

## Explication du Code

### 1. Classe Principale - PatientApplication.java

```java
@SpringBootApplication
public class PatientApplication {
    public static void main(String[] args) {
        SpringApplication.run(PatientApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {
            // Démonstration des opérations CRUD
        };
    }
}
```

**Annotations expliquées :**

- `@SpringBootApplication` : Combine `@Configuration`, `@EnableAutoConfiguration`, et `@ComponentScan`
- `@Bean` : Définit un bean Spring géré par le conteneur IoC
- `CommandLineRunner` : Interface pour exécuter du code au démarrage de l'application

### 2. Entité JPA - Patient.java

```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @Column(name = "name")
    private String name;
  
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
  
    @Column(name = "sickness")
    private boolean sickness;
  
    @Column(name = "score")
    private int score;
}
```

**Annotations JPA expliquées :**

- `@Entity` : Marque la classe comme une entité JPA persistante
- `@Table(name = "patients")` : Spécifie le nom de la table en base de données
- `@Id` : Définit la clé primaire de l'entité
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` : Auto-incrémentation de l'ID
- `@Column(name = "...")` : Mapping explicite vers les colonnes de la table
- `@Temporal(TemporalType.DATE)` : Spécifie le type de donnée temporelle pour les dates

**Annotations Lombok expliquées :**

- `@Data` : Génère automatiquement getters, setters, toString, equals, et hashCode
- `@NoArgsConstructor` : Génère un constructeur sans paramètres
- `@AllArgsConstructor` : Génère un constructeur avec tous les paramètres

### 3. Repository - PatientRepository.java

```java
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
  
    // Requête dérivée automatique
    List<Patient> findBySickness(boolean sickness);
  
    // Requête JPQL personnalisée
    @Query("SELECT p FROM Patient p WHERE p.score < :score")
    List<Patient> findPatientsByScoreLessThan(@Param("score") int score);
  
    // Requête SQL native
    @Query(value = "SELECT * FROM patients WHERE name LIKE %?1%", nativeQuery = true)
    List<Patient> findPatientsByNameContains(String name);
}
```

**Fonctionnalités Spring Data JPA :**

- `JpaRepository<Patient, Long>` : Fournit automatiquement les méthodes CRUD de base
- **Requêtes dérivées** : Spring génère automatiquement la requête à partir du nom de la méthode
- `@Query` : Permet d'écrire des requêtes JPQL ou SQL personnalisées
- `@Param` : Lie les paramètres de méthode aux paramètres de requête

## Installation et Exécution

### Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- MySQL 8.0
- Git

### Étapes d'installation

1. **Cloner le projet**

```bash
git clone [URL_DU_PROJET]
cd patient
```

2. **Configurer MySQL**

```sql
CREATE DATABASE patients_db;
```

3. **Compiler et exécuter**

```bash
# Compilation
mvn clean compile

# Exécution
mvn spring-boot:run
```

4. **Vérifier le démarrage**
   L'application démarre sur le port 8080 par défaut et exécute automatiquement les opérations de démonstration.

## Tests et Utilisation

L'application exécute automatiquement une série d'opérations CRUD pour démontrer les fonctionnalités :

### 1. Création de Patients (CREATE)

```java
// Création de patients de la famille Morsi (en bonne santé)
patientRepository.save(new Patient(null, "Hassan Morsi", new Date(), false, 85));
patientRepository.save(new Patient(null, "Ahmed Morsi", new Date(), false, 90));
patientRepository.save(new Patient(null, "Fatima Morsi", new Date(), false, 88));

// Création de patients de la famille Nouri (malades)
patientRepository.save(new Patient(null, "Omar Nouri", new Date(), true, 65));
patientRepository.save(new Patient(null, "Leila Nouri", new Date(), true, 70));
patientRepository.save(new Patient(null, "Youssef Nouri", new Date(), true, 60));
```

### 2. Lecture de Patients (READ)

```java
// Récupérer tous les patients
List<Patient> patients = patientRepository.findAll();

// Récupérer un patient par ID
Optional<Patient> patient = patientRepository.findById(1L);

// Requête personnalisée : patients malades
List<Patient> sickPatients = patientRepository.findBySickness(true);
```

### 3. Mise à Jour (UPDATE)

```java
// Modifier un patient existant
Patient patient = patientRepository.findById(2L).orElse(null);
if (patient != null) {
    patient.setName("Ahmed Morsi (Modifié)");
    patientRepository.save(patient);
}
```

### 4. Suppression (DELETE)

```java
// Supprimer un patient par ID
patientRepository.deleteById(1L);
```

## Concepts Clés

### 1. ORM (Object-Relational Mapping)

L'ORM permet de mapper les objets Java aux tables de base de données sans écrire de SQL explicite.

### 2. JPA (Java Persistence API)

JPA est une spécification Java qui définit une interface standard pour l'ORM.

### 3. Hibernate

Hibernate est l'implémentation JPA la plus populaire, utilisée comme provider par défaut dans Spring Boot.

### 4. Spring Data JPA

Spring Data JPA simplifie l'accès aux données en fournissant :

- Des repositories automatiques
- Des requêtes dérivées
- Une configuration minimale

### 5. Patterns Utilisés

**Repository Pattern :**
Encapsule la logique d'accès aux données et fournit une interface uniforme.

**Entity Pattern :**
Représente les objets métier qui correspondent aux tables de base de données.

**Dependency Injection :**
Spring injecte automatiquement les dépendances (comme PatientRepository) via l'annotation `@Autowired` ou les paramètres de constructeur.

