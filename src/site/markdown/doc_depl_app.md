## Déploiement de l'application

### Cloud Foundry

L'application "Amical" a été déployée sur [Cloud Foundry](https://www.cloudfoundry.org).  
Cloud Foundry est un PaaS (Platform as a Service) open source qui permet de créer, de déployer, d'exécuter et de faire évoluer des applications sur des modèles de Cloud public.  
Cloud Foundry est un fournisseur de services qui permet de rendre des ressources, telles que des applications et du stockage, accessibles au grand public via Internet.

### Prérequis

Le déploiement d'une application sur Cloud Foundry nécessite les prérequis suivants :  
1. Créer un compte sur [Pivotal Cloud Foundry](https://account.run.pivotal.io/z/uaa/sign-up).  
2. Installer le [Cloud Foundry Command Line Interface (CLI)](https://github.com/cloudfoundry/cli#downloads).

### Déploiement

Les points suivants décrivent les étapes qui ont été réalisées pour le déploiement de l'application sur Cloud Foundry :

#### 1. Répertoire de travail
Se placer à la racine du projet Spring Boot :  
`cd [PROJECT_PATH]`  

#### 2. Maven
Packager l'application Spring Boot avec Maven (JAR) :  
`mvn clean package`  

#### 3. Manifest
"manifest.yml" est un fichier qui permet de paramétrer le déploiement de l'application sur Cloud Foundry.  
Créer un fichier "manifest.yml" à la racine du projet.  
Entrer dans ce fichier les paramètres de déploiement suivants :  

	---
	applications:
	- name: amical
	  random-route: true
	  buildpacks:
	    - https://github.com/cloudfoundry/java-buildpack.git
	  path: target\amical-0.0.1-SNAPSHOT.jar
	  env:
	    JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 12.+ } }'

#### 4. Login
Se connecter à Pivotal Cloud Foundry avec le CLI :  

	cf login
	API endpoint: https://api.run.pivotal.io
	Email: [Votre email de connexion]
	Password: [Votre password de connexion]
	
#### 5. Push
Déployer le JAR Spring Boot dans Cloud Foundry :  
`cf push`

#### 6. Run
Une fois l'application déployée, le terminal nous fournis l'adresse HTTP de l'application :  
`https://amical-quick-kob-am.cfapps.io`