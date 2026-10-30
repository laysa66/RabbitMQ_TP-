# TP RabbitMQ — Messagerie asynchrone

Un TP guidé pour découvrir RabbitMQ à travers le scénario d’une boutique en ligne : envoi d’e-mails, partage des tâches et diffusion d’événements vers plusieurs services.

**Public :** étudiants IWA / AWI · **Organisation :** en binôme · **Durée :** 1 h, hors installation et bonus.

## Commencer

1. Lire le [TP étudiant en PDF](docs/TP-RabbitMQ-etudiant.pdf).
2. Télécharger et décompresser le [kit étudiant](TP-RabbitMQ-kit-etudiant.zip), ou utiliser le dossier [`kit/`](kit/) après avoir cloné le dépôt.
3. Choisir **un seul parcours** : Python, Node.js ou Java / Spring AMQP.
4. Préparer le poste avant la séance, puis suivre les étapes du PDF.

Le code de connexion et les workers sont fournis. Trois zones sont à compléter : **H** pour le premier envoi, **T1** pour les bindings et **T2** pour la publication d’événements. Les erreurs signalant ces TODO sont volontaires. Cette branche ne contient pas les solutions.

## Objectifs

- Publier et consommer des messages.
- Comprendre les compteurs `Ready` et `Unacked` et le rôle des acquittements.
- Partager une queue entre plusieurs workers.
- Observer une redélivrance après interruption.
- Router des événements avec un exchange `topic`.

## Préparer le poste

Docker Desktop ou OrbStack doit être démarré. Installer ensuite les dépendances de **son** parcours, en suivant la fiche du PDF :

| Parcours | Prérequis | Dossier |
| --- | --- | --- |
| Python | Python 3.10+ et pip | [`kit/python/`](kit/python/) |
| Node.js | Node.js 22+ et npm | [`kit/node/`](kit/node/) |
| Java | JDK 17+ et Maven | [`kit/java/`](kit/java/) |

Depuis le dossier `kit/`, démarrer RabbitMQ :

```sh
docker compose up -d
```

Ouvrir ensuite [l’interface de gestion](http://localhost:15672) : identifiant `guest`, mot de passe `guest`. Les programmes utilisent le port **5672** ; l’interface utilise **15672**. La configuration fournie est destinée à un environnement local de TP.

Avec le kit ZIP, exécuter la commande dans le dossier décompressé qui contient `compose.yaml`. Sous Windows, utiliser PowerShell pour les commandes du support.

## Parcours de la séance

| Activité | Durée |
| --- | --- |
| Scénario, vocabulaire et rôles | 5 min |
| Interface : publier, lire et acquitter | 8 min |
| Premier producer et premier consumer | 10 min |
| Deux workers et reprise après une panne | 15 min |
| Routage topic | 17 min |
| Mini rendu du binôme | 5 min |

Les bonus permettent d’explorer le prefetch, la durabilité et les dead letter queues. Ils ne font pas partie des 60 minutes.

## Contenu du dépôt

```text
docs/
  TP-RabbitMQ-etudiant.pdf     # Support à distribuer
  TP-RabbitMQ-etudiant.md      # Source modifiable
kit/
  compose.yaml                # RabbitMQ local
  python/                     # Squelettes et programmes fournis
  node/
  java/
TP-RabbitMQ-kit-etudiant.zip   # Kit prêt à télécharger
```

## Conseils pendant le TP

- Faire une prédiction avant chaque expérience, puis noter le résultat.
- Attendre le message d’attente des consumers avant les publications demandées.
- Tenir compte du délai d’actualisation des compteurs de l’interface.
- Terminer un lot et arrêter les anciens workers avant de recommencer.
- Suivre la fiche de son langage : le mode `AUTO` de Spring ne correspond pas à un acquittement immédiat du protocole.

À la fin de la séance :

```sh
docker stop tp-rabbitmq
```

Le conteneur est conservé et peut être relancé avec `docker start tp-rabbitmq`.

## Ressources

- [Tutoriels officiels RabbitMQ](https://www.rabbitmq.com/tutorials)
- [Acquittements et confirmations](https://www.rabbitmq.com/docs/confirms)
- [Documentation Spring AMQP](https://docs.spring.io/spring-amqp/reference/)
