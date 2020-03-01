## Architecture multi-tiers

L'architecture multi-tiers est une architecture client-serveur dans laquelle une application est exécutée par plusieurs composants logiciels distincts, ou couches applicatives.  
Chaque couche applicative est indépendante et n'appel que la couche immédiatement en dessous d'elle, et n'a aucune connaissance des couches supérieures.

![Architecture multi-tiers](images/architecture_multi_tiers.png)

| Couche applicative | Responsabilité |
| --- | --- |
| Controller | Responsable de l'implémentation du traitement des requêtes HTTP. |
| Business | Responsable de l'implémentation des règles de gestion fonctionnelles. |
| Model | Responsable de l'implémentation du domaine fonctionnelle et des entitées JPA. |
| Configuration | Responsable de l'implémentation de la configuration de l'application (base de données, sécurité, controller, messages, ...). |
| Repository | Responsable de l'implémentation des communications avec la base de données. |