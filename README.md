# TP RabbitMQ — Corrigé

Cette branche accompagne le [TP étudiant disponible sur `main`](https://github.com/laysa66/RabbitMQ_TP-/tree/main).

## Consulter le corrigé

- **[Corrigé complet en PDF](docs/TP-RabbitMQ-correction.pdf)** — réponses aux questions, résultats attendus, explications et solutions des TODO dans les trois langages.
- [Source modifiable du corrigé](docs/TP-RabbitMQ-correction.md).
- [Support étudiant en PDF](docs/TP-RabbitMQ-etudiant.pdf).

Le corrigé correspond à la version du support étudiant en **12 pages** et reprend ses repères **Q1 à Q7**, **H**, **T1** et **T2**.

## Contenu

1. Lecture, remise en queue et acquittement dans l’interface.
2. Premier producer et premier consumer.
3. Work queue, prefetch et reprise après interruption.
4. Routage topic et tableau des destinataires.
5. Réponses possibles au mini rendu et critères de compréhension.
6. Compléments de code Python, Node.js et Java / Spring AMQP.
7. Bonus : comparaison du prefetch, durabilité, DLQ et TTL.
8. Points de vigilance pour interpréter les expériences.

Le dossier [`kit/`](kit/) conserve les squelettes étudiants. Pour refaire le TP avec les solutions, appliquer les blocs H, T1 et T2 du corrigé aux fichiers correspondants, puis suivre les commandes du support étudiant. Après une modification Java, recompiler le jar.

## Vérifications

Les solutions H, T1 et T2 ont été exécutées contre RabbitMQ 4.4.0 dans les trois langages : Hello World, création des bindings, routage des événements et publication avec les consumers arrêtés. Les workers et senders de tâches fournis ont également été vérifiés. Le parcours Python a servi à valider les expériences de panne, de redémarrage et de DLQ/TTL.

La distribution précise des tâches entre deux workers peut varier. Les compteurs web sont actualisés avec un délai ; les interprétations et les limites sont expliquées dans le PDF.

## Supports étudiants

Pour distribuer les consignes et le kit sans les solutions, utiliser la branche [`main`](https://github.com/laysa66/RabbitMQ_TP-/tree/main).
