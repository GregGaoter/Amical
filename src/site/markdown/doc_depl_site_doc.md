## Site de documentation

### Cloud Foundry

Le site de documentation de l'application "Amical" a été déployée sur [Cloud Foundry](https://www.cloudfoundry.org).  
Cloud Foundry utilise [NGINX](https://www.nginx.com) comme serveur de ressources statiques.
Cloud Foundry est un PaaS (Platform as a Service) open source qui permet de créer, de déployer, d'exécuter et de faire évoluer des applications sur des modèles de Cloud public.  
Cloud Foundry est un fournisseur de services qui permet de rendre des ressources, telles que des applications et du stockage, accessibles au grand public via Internet.

### Prérequis

Le déploiement de ressources statiques sur Cloud Foundry nécessite les prérequis suivants :  
1. Créer un compte sur [Pivotal Cloud Foundry](https://account.run.pivotal.io/z/uaa/sign-up).  
2. Installer le [Cloud Foundry Command Line Interface (CLI)](https://github.com/cloudfoundry/cli#downloads).

### Déploiement

Les points suivants décrivent les étapes qui ont été réalisées pour le déploiement du site de documentation sur Cloud Foundry :

#### 1. Répertoire de travail
Se placer à la racine du projet Spring Boot :  
`cd [PROJECT_PATH]`  

#### 3. Manifest
"manifest.yml" est un fichier qui permet de paramétrer le déploiement des ressources statiques de l'application sur Cloud Foundry.  
Créer un fichier "manifest.yml" à la racine du projet.  
Entrer dans ce fichier les paramètres de déploiement suivants (pour l'application, les ressources statiques et le site de documentation) :  

	---
	applications:
	- name: amical
	  random-route: true
	  buildpacks:
	    - https://github.com/cloudfoundry/java-buildpack.git
	  path: target\amical-0.0.1-SNAPSHOT.jar
	  env:
	    JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 12.+ } }'
	- name: amical-plan
	  memory: 64M
	  random-route: true
	  buildpacks:
	    - https://github.com/cloudfoundry/staticfile-buildpack.git
	  path: plan
	- name: amical-doc
	  memory: 64M
	  random-route: true
	  buildpacks:
	    - https://github.com/cloudfoundry/staticfile-buildpack.git
	  path: target\site

#### 4. Login
Se connecter à Pivotal Cloud Foundry avec le CLI :  

	cf login
	API endpoint: https://api.run.pivotal.io
	Email: [Votre email de connexion]
	Password: [Votre password de connexion]
	
#### 5. Push
Déployer le site de documentation dans Cloud Foundry :  
`cf push amical-doc`

#### 6. Run
Une fois le site de documentation déployé, le terminal nous fournis l'adresse HTTP :  
`https://amical-doc-shiny-raven-eu.cfapps.io`