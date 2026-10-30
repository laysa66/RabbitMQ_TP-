# TP RabbitMQ - kit étudiant

Choisissez UN dossier : python, node ou java. Le PDF contient le parcours commun et les fiches de commandes. Travaillez dans le dossier choisi.

Trois zones seulement sont à compléter : H (publication Hello World), T1 (binding topic), T2 (publication topic). Remplacez l'instruction qui lève une erreur TODO par votre code. Ces erreurs sont volontaires, ce n'est pas un problème d'installation. Aucun corrigé ni réponse aux questions n'est inclus.

Les workers et les connexions sont fournis pour consacrer la séance aux expériences. Le worker Node impose un traitement à la fois, comme les autres parcours. En Java, AUTO signifie que Spring acquitte après le traitement ; ce n'est pas le noAck/auto_ack du protocole.

Durée : 1 h hors installation ; trois bonus facultatifs hors durée. Ne lancez qu'un seul broker et choisissez un langage par binôme. Utiliser un environnement local de TP.

Les connexions Python et Node peuvent utiliser AMQP_URL. En Java, adapter application.properties. Les exemples visent localhost:5672 et guest/guest. Aucun secret ni environnement distant n'est nécessaire.

Les versions de dépendances servent de base pédagogique ; faire maintenir et valider le kit par l'enseignant avant diffusion. Voir les ressources officielles du PDF.

Depuis ce dossier racine, démarrez le broker avec `docker compose up -d`. Pour le reste, ouvrez les terminaux dans le dossier de votre langage.
