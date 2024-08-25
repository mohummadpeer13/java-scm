# Scm
Spring Boot - Spring MVC - Spring Security - Oauth Google - Spring Data JPA - Thymeleaf - TailwindCSS - Mysql - Java Mail - Kafka - Github Actions - Docker - Kubernetes - Kvm

# Intro

## Dans ce projet, nous utiliserons les technologies suivantes pour créer une application Spring Boot complète et robuste.

### HEBERGEMENT

- Le projet est déployé sur un cluster Kubernetes.
- J'utilise un ordinateur Intel i7 avec 32 Go de RAM, transformé en hyperviseur de type 1.
- Le système d'exploitation est Ubuntu 22.04, avec KVM et la bibliothèque Libvirt pour la gestion de la virtualisation.

### KAFKA

- Création de microservice avec Spring Boot qui utilise Apache Kafka pour produire et consommer des événements d'email.
- Cela garantit une communication asynchrone et une haute fiabilité pour les envois d'emails.

### BACKEND

- Java : Langage principal pour le développement de l'application.

- Spring Boot : Framework principal pour simplifier le développement de l'application avec des configurations par défaut et des starters.

- Spring Data JPA : Pour l'interaction avec la base de données en utilisant des entités et des référentiels.

- Hibernate : Implémentation de JPA pour la gestion des entités et des transactions avec la base de données.

- Spring Validation : Framework puissant pour valider des données dans des applications web et des services REST. Il utilise la spécification Bean Validation (JSR 380) pour appliquer des contraintes sur les propriétés des beans, ce qui simplifie le processus de validation et garantit que les données respectent des règles définies.

- Spring MVC (Model-View-Controller) : Framework au sein du projet Spring qui facilite le développement d'applications web en utilisant le modèle de conception MVC. Ce modèle divise l'application en trois composants principaux : le modèle, la vue et le contrôleur.

- Spring Security : Pour la gestion de la sécurité de l'application, y compris l'authentification et l'autorisation.

- OAuth2 Social Login : Permet aux utilisateurs de se connecter à votre application en utilisant des comptes sociaux comme Google, Facebook, ou GitHub. Dans cet exemple, nous allons configurer une connexion OAuth2 avec Google en utilisant Spring Boot et Spring Security.

- JavaMail : L’envoi d'e-mails est une fonctionnalité courante dans de nombreuses applications. Spring Boot simplifie l'intégration de la bibliothèque JavaMail pour envoyer des e-mails.

- Cloudinary : Service de gestion de médias basé sur le cloud qui permet de stocker, transformer et livrer des images et des vidéos de manière efficace.
Intégrer Cloudinary dans un projet Spring Boot vous permet de gérer facilement les fichiers multimédias.

### BASE DE DONNEES

- MySQL : Système de gestion de base de données relationnelle pour stocker les données de l'application.

### FRONTEND

- Thymeleaf : Moteur de template côté serveur pour rendre des pages HTML dynamiques.

- Tailwind CSS : Framework CSS utilitaire qui vous permet de concevoir rapidement des interfaces en utilisant des classes CSS prêtes à l'emploi directement dans votre HTML.

- Flowbite : Bibliothèque de composants d'interface utilisateur basée sur Tailwind CSS. Elle propose des composants prêts à l'emploi comme des boutons, des modals, des barres de navigation, etc., ce qui permet de construire des interfaces utilisateurs de manière rapide et efficace.

### OUTIL DE DEVELOPPEMENT

- Maven : Outils de gestion des dépendances et de construction de projet.

- Visual Studio Code : Environnements de développement intégrés (IDE) pour écrire et déboguer le code.

- Docker : Pour conteneuriser l'application et faciliter le déploiement.

- Kubernetes : Système open-source permettant d'automatiser le déploiement, la mise à l'échelle et la gestion des applications conteneurisées.