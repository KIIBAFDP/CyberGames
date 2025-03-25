# CyberGames

## Description

CyberGames est une application Java pour la gestion des forfaits de réservation dans un cybercafé. Elle permet aux utilisateurs de se connecter, de visualiser leurs forfaits et de suivre le temps restant pour chaque session de réservation.

## Fonctionnalités

- Connexion des utilisateurs avec vérification des identifiants.
- Affichage des forfaits de réservation pour l'utilisateur connecté.
- Suivi du temps restant pour chaque forfait de réservation.
- Mise à jour du temps restant dans la base de données.

## Structure du Projet

Le projet est structuré comme suit :

CyberGames/ ├── src/ │ ├── main/ │ │ ├── java/ │ │ │ ├── DbConnect.java │ │ │ ├── ForfaitManager.java │ │ │ ├── ForfaitWindow.java │ │ │ ├── Login.java │ │ │ ├── Main.java │ │ │ └── Window.java ├── lib/ │ ├── jbcrypt-0.4.jar │ └── mysql-connector-java-8.0.25.jar ├── target/ │ ├── classes/ │ ├── maven-archiver/ │ └── cybergames-1.0-SNAPSHOT.jar ├── .idea/ ├── .gitignore ├── pom.xml └── README.md

### Prérequis

- Java 17 ou supérieur
- Maven
- MySQL

### Installation

1. Clonez le dépôt :
    ```sh
    git clone <URL_DU_DEPOT>
    cd CyberGames
    ```

2. Configurez la base de données MySQL :
    - Créez une base de données nommée `cyber_games_arras`.
    - Ajoutez les tables nécessaires (`user`, `booking`, `reservation`, `forfait`).

3. Modifiez les informations de connexion à la base de données dans `DbConnect.java` :
    ```java
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/cyber_games_arras?serverVersion=8.0.40&charset=utf8mb4";
    private static final String USER = "localhost"; // Remplacez par votre nom d'utilisateur réel
    private static final String MOT_DE_PASSE = "Doliprane8%"; // Vérifiez votre mot de passe
    ```

4. Compilez et packagez l'application avec Maven :
    ```sh
    mvn clean package
    ```

## Utilisation

### Lancer l'application

Pour lancer l'application, exécutez la commande suivante :
```sh
java -jar [cybergames-1.0-SNAPSHOT-jar-with-dependencies.jar](http://_vscodecontentref_/10)

Connexion
Entrez votre nom d'utilisateur et votre mot de passe dans la fenêtre de connexion.
Cliquez sur le bouton "Connexion".
Affichage des forfaits
Après une connexion réussie, une nouvelle fenêtre s'ouvre affichant les forfaits de réservation de l'utilisateur connecté. Chaque forfait affiche le temps restant pour la session.

Code Source
Main.java
Le point d'entrée de l'application. Affiche un message "Hello, World!" (peut être modifié ou supprimé).

Login.java
Gère la vérification des identifiants de connexion des utilisateurs.

ForfaitWindow.java
Affiche les forfaits de réservation de l'utilisateur connecté et gère le suivi du temps restant pour chaque forfait.

ForfaitManager.java
Gère le temps restant pour les forfaits de réservation et met à jour la base de données.

DbConnect.java
Gère la connexion à la base de données MySQL.

Window.java
Affiche la fenêtre de connexion et gère les interactions de l'utilisateur pour la connexion.

Dépendances
mysql-connector-java : Connecteur JDBC pour MySQL.
jbcrypt : Bibliothèque pour le hachage des mots de passe.



Assurez-vous de remplacer `<URL_DU_DEPOT>` par l'URL réelle de votre dépôt Git. Vous pouvez également ajouter des sections supplémentaires si nécessaire, telles que des instructions de déploiement ou des informations sur les contributions.