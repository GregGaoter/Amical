# Application web Amical

## Projet

### Présentation

"*Les amis de l'escalade*", une association qui réunit les passionnés d’escalade dans toute la France, cherche un développeur Java EE pour créer un site communautaire autour de cette discipline.

### Contexte

Avec l'objectif de fédérer les licenciés, l'association "*Les amis de l'escalade*" souhaite développer sa présence en ligne. À ce titre, plusieurs axes d'amélioration ont été identifiés dont la création d'un site communautaire.

### Objectif

L'objectif de ce projet est de développer une application web communautaire pour la gestion des activités de l'association "*Les amis de l'escalade*".

## Installation

### Prérequis

| Description | Technologie | Version |
| --- | --- | --- |
| Langage | Java | 12 |
| Production logicielle | Maven | 3.6.1 |
| Code versionning | Git | 2.22.0 |
| Base de données | PostgreSQL | 11.4.3 |
| Serveur de ressources statiques | Nginx | 1.17.8 |

### Application

1.	Installer Git 2.22+ sur votre machine.
1.  Depuis le terminal `Git Bash` de Git, se placer dans le répertoire où vous souhaitez cloner le projet, par exemple :  
    `cd /c/Users/gregg/oc/projets`
1.  Cloner le dépôt GitHub du projet :  
    `git clone https://github.com/GregGaoter/Amical.git`

### Base de données

1.	Installer PostgreSQL 11.4+ sur votre machine.
1.  Depuis un terminal Windows, se placer à la racine du répertoire `bin` de PostgreSQL :  
    `cd "C:\Program Files\PostgreSQL\11\bin"`
1.  Entrer dans le terminal `psql` de PostgreSQL :  
    `psql -U postgres`
1.  Créer la base de données :  
    `CREATE DATABASE db_amical ENCODING UTF8;`
1.  Vérifier que la base de données à bien été créée en affichant la liste des bases de données :  
    `\l`
1.  Quitter le terminal `psql` :  
    `\q`
1.  Restaurer le dump `db_amical.sql` dans la base de données `db_amical` qui vient d'être créée. Le dump se trouve dans le répertoire `doc/scripts` du projet :  
    `psql -U postgres db_amical < [PROJECT_PATH]\doc\scripts\db_amical.sql`
1.  Entrer dans le terminal `psql` de PostgreSQL :  
    `psql -U postgres`
1.  Se connecter à la base de données `db_amical` :  
    `\c db_amical`
1.  Vérifier que les tables ont bien été créées :  
    `\dt`

### Serveur de ressources statiques

Nginx fournit à l'application les images des plans des différentes topographies (spot, secteur, voie et longueur).

1.	Installer Nginx 1.17+ sur votre machine.
1.  Copier les images du répertoire `Amical/plan` du projet.
1.  Depuis un terminal Windows, se placer à la racine de Nginx :  
    `cd [NGINX_PATH]`
1.  Créer un répertoire plan dans Nginx :  
    `mkdir html\img\plan`
1.  Coller les images des plans du projet dans le répertoire `html\img\plan` de Nginx.
1.  Lancer Nginx : double cliquez sur l'exécutable `nginx.exe`.
1.  Vérifier que Nginx soit bien installé. Taper dans votre navigateur Web l'URL :  
    `localhost:80`  
    Si vous voyez la page "Welcome to nginx!", Nginx est bien installé.

## Exécution pour le développement

### Prérequis

Activer le profile `dev` de l'application :
1.  Ouvrir le fichier `application.properties` à la racine de `src/main/resources`.
2.  La propriété `spring.profiles.active=dev` doit être active.  
    La propriété `#spring.profiles.active=prod` doit être en commentaire.

Lancer `Nginx` :
1.  Aller dans le répertoire d'installation de `Nginx`.
2.  Lancer `nginx.exe`.

### Exécution depuis un IDE

1.  Importer le projet Maven. Par exemple pour Eclipse :  
    `File -> Import... -> Maven -> Existing Maven Projects`
2.  Exécuter l'application :  
    `Amical/src/main/java/app/gaugiciel/amical`  
    `Click droit sur AmicalApplication.java -> Run As -> Java Application`
3.  Depuis un navigateur Web, accéder à la page d'accueil :  
    `localhost:8080/`

### Exécution en tant que package JAR

1.  Depuis un terminal Windows, se placer à la racine du projet :  
    `cd [PROJECT_PATH]`
1.  Packager l'application dans un JAR :  
    `mvn clean package`
1.  Exécuter l'application :  
    `java -jar target/amical-0.0.1-SNAPSHOT.jar.jar`
1.  Depuis un navigateur Web, accéder à la page d'accueil :  
    `localhost:8080/`

### Exécution en utilisant le plugin Maven

1.  Depuis un terminal Windows, se placer à la racine du projet :  
    `cd [PROJECT_PATH]`
1.  Exécuter l'application :  
    `mvn spring-boot:run`
1.  Depuis un navigateur Web, accéder à la page d'accueil :  
    `localhost:8080/`

## Exécution pour la production

### Page d'accueil de l'application

[https://amical-quick-kob-am.cfapps.io](https://amical-quick-kob-am.cfapps.io)  

![Page accueil Amical](https://github.com/GregGaoter/Amical/blob/master/doc/capture_ecran/page_accueil.png)

### Comptes utilisateurs

Les comptes utilisateurs suivants sont fournis pour tester l'application avec différents rôles :

| Adresse email | Mot de passe | Rôle |
| --- | --- | --- |
| fabien.poulin52@protonmail.fr | ZnkVRvZVe | Ami |
| segolene.deniau6@lycos.fr | dIcgxCy59CNVEsLW | Administrateur |

## Site de documentation

Le site de documentation technique du projet est accessible à cette adresse :  

[https://amical-doc-shiny-raven-eu.cfapps.io](https://amical-doc-shiny-raven-eu.cfapps.io)  

![Page accueil site documentation](https://github.com/GregGaoter/Amical/blob/master/doc/capture_ecran/page_accueil_doc.png)