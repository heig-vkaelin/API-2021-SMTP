# Rapport Labo SMTP - API HEIG
Auteurs : Alexandre Jaquier, Valentin Kaelin

## Description

Ce répertoire GitHub contient la réalisation du laboratoire numéro 4 du cours API de l'HEIG. Ce laboratoire a pour but de mettre en pratique l'utilisation du protocole SMTP. Ce projet permet de créer des compagnes de "pranks". L'utilisateur peut définir une liste d'emails de victimes ainsi que les messages de farce à envoyer. Par la suite, le programme s'occupera de créer des groupes et enverra aléatoirement un des messages à chaque groupe.

Il est également possible de tester l'envoi des emails sans embêter de vraies personnes en utilisant l'outil MockMock permettant de créer un serveur SMTP local. Une configuration docker est présente afin de faciliter son utilisation.

## Installation

### Besoins techiques
La liste suivante doit être installée sur votre machine afin de pouvoir utiliser correctement ce projet:

* Java avec >= jdk11
* Maven
* Docker

### Projet Java

Le projet dans le dossier `code` est un projet Maven classique. Il est donc très simple de créer son exécutable et le lancer via les commandes suivantes (depuis le dossier `code`):

```bash
mvn clean package
cd target
java -jar LaboSMTP-1.0-SNAPSHOT.jar
```

### Serveur SMTP

Afin de pouvoir tester l'application, il est possible de mettre en place un serveur SMTP local grâce à l'outil [MockMock](https://github.com/tweakers/MockMock). Ce serveur aura pour but de récupèrer tous les mails envoyés et de les afficher de façon graphique grâce à une interface web.  
L'exécutable est déjà présent dans le dossier `docker`. Nous avons utilisé une [version modifiée](https://github.com/HEIGVD-Course-API/MockMock) du lociel afin de régler des soucis présents dans la version officielle.

Afin de lancer le programme, il est nécessaire de lancer les scripts dans le dossier `docker`, dans cet ordre-ci:

```bash
./build-image.sh
./run-container.sh
```

Une fois ces commandes effectuées, un containeur docker contenant l'application MockMock tournera en fond sur votre ordinateur. Celle-ci contient deux points d'entrées:
* Un serveur SMTP sur le port 25
* L'interface web sur le port 8282

Il vous est donc possible d'accéder à l'interface en vous rendant sur la page suivante sur un navigateur: [localhost:8282](http://localhost:8282/). Elle devrait être vide pour le moment. Rendez-vous dans la section suivante afin d'apprendre à réaliser une compagne de pranks.

## Configuration et exécution

Après avoir exécuter la commande ``mvn clean package``, il vous est possible de configurer votre compagne de pranks grâce aux fichier se trouvant dans le dossier `target/config`. Voici le but des 3 fichiers différents:

* **config.properties**: Fichier de configuration principal, il vous est possible de changer l'adresse et le port du serveur SMTP (à laisser par défaut si vous utilisez MockMock), le nombre de groupes et l'adresse qui sera en copie câchée de chaque mail afin de surveiller que la compagne s'est bien déroulée. 
* **messages.utf8**: Fichier contenant les différents messages de pranks, il vous est possible d'en ajouter autant que vous le souhaiter. La syntaxe des messages est la suivante:
```java
Subject: SUJET ICI

CONTENU DU MAIL ICI
SUITE DU CONTENU
ETC
== // Séparation des messages
Subject: SUJET ICI
...
```
* **victims.utf8**: Fichier contenant la liste des victimes à cibler, chaque ligne doit contenir une adresse email valide. A noter que la taille de chaque groupe doit être de 3 au minimum. Il faut donc avoir un nombre de victimes suffisants en comparaison au nombre de groupes définis dans le fichier `config.properties`.

Après avoir fini votre configuration, il est possible d'exécuter l'application en se rendans dans le dossier `target` et en lançant la commande suivante:

```bash
java -jar LaboSMTP-1.0-SNAPSHOT.jar
```
Un affichage vous explicant le déroulé du programme sera affiché dans la console et il vous sera possible d'aller vérifier que les mails aient bien été envoyés en retournant sur l'interface de MockMock sur le site [localhost:8282](http://localhost:8282/).

## Implémentation

TODO
* **A description of your implementation**: document the key aspects of your code. It is probably a good idea to start with a class diagram. Decide which classes you want to show (focus on the important ones) and describe their responsibilities in text. It is also certainly a good idea to include examples of dialogues between your client and an SMTP server (maybe you also want to include some screenshots here).
