TETRIS PROJECT
Une implémentation Java du jeu classique
Anas Ouzine
Aya Laaouine
Ihssane Moulabbi
Kawtar Boutaskiouin
Encadré par Monsieur Lahcen Moumoun

School Logo
University Logo
Découvrir
Présentation du Projet
Histoire
Notre projet consiste en une implémentation complète du jeu Tetris, développé en Java. Ce jeu de puzzle classique, créé par Alexey Pajitnov en 1984, met le joueur au défi de manipuler des pièces géométriques (tetrominos)qui tombent pour former des lignes horizontales complètes.

Objectif
L'objectif principal était de créer une version du jeu fidèle à l'original, tout en appliquant les principes de conception orientée objet et l'architecture MVC (Modèle-Vue-Contrôleur) pour assurer une base de code maintenable et extensible.

Fonctionnalités
•
Génération aléatoire des pièces (tetrominos)
•
Rotation des pièces (0°, 90°, 180°, 270°)
•
Déplacement latéral des pièces
•
Détection et suppression des lignes complètes
•
Système de score basé sur le nombre de lignes effacées et le niveau
•
Augmentation progressive de la difficulté (vitesse)
•
Affichage de la pièce suivante
•
Affichage du score et du niveau actuel
Technologies
Java SE
Swing pour l'interface graphique
Architecture MVC
Collections Java
Diagramme de Classes UML
Comprendre l'Architecture du Jeu
Le diagramme de classes UML ci-dessous offre une vue d'ensemble structurée de l'architecture de notre application Tetris. Il détaille les classes principales, leurs attributs, méthodes et les relations qui les unissent, illustrant ainsi comment les différents composants interagissent pour former le jeu complet.

Cette conception orientée objet assure une modularité, une maintenabilité et une extensibilité accrues du code.

Diagramme de Classes UML du Jeu Tetris
Architecture MVC
Notre projet suit le pattern MVC pour une séparation claire des responsabilités

Modèle
Contrôleur
Vue
Modèle

📁 manager/
•
PlayManager - logique principale
•
DatabaseManager - gestion BDD
📁 mino/
•
Block - unité de base
•
Mino - classe abstraite des pièces
•
7 types de Tetrominos (I, L, T, Z, O...)
Gère les données et la logique métier du jeu Tetris.

Vue

🖥️ Composants Visuels
•
GamePanel - panneau principal de jeu
•
Sound - gestion audio du jeu
🎨
Interface utilisateur & rendu graphique
Gère l'affichage graphique et l'interface utilisateur.

Contrôleur

🎮 Contrôle & Coordination
•
Main - point d'entrée de l'application
•
KeyHandler - gestion des entrées clavier
⚡
Coordination Modèle ↔ Vue
Traite les entrées utilisateur et coordonne le système.

Flux de données & Interactions
👤 Utilisateur
Entrées clavier
Input
🎮 Contrôleur
KeyHandler → Main
Update
🧠 Modèle
PlayManager + Minos
Render
🖥️ Vue
GamePanel + Sound
Avantages de notre Architecture
🔄
Séparation des Responsabilités
Chaque couche a un rôle bien défini
🧩
Modularité
7 types de Tetrominos séparés et extensibles
🔧
Maintenance Facile
PlayManager centralise la logique de jeu
🚀
Évolutivité
Facile d'ajouter de nouvelles fonctionnalités
📁 Structure du Code
controller/
2 classes
→
model/
11 classes
→
view/
2 classes
Diagramme d'États du Jeu
Le diagramme d'états représente les différentes transitions possibles entre les états du jeu.

Menu Principal
Jeu en cours
Pause
Game Over
Diagramme d'États
Démonstration
Démonstration du jeu Tetris
Points clés de la démonstration
🎮
Interface du jeu complète avec représentation graphique
🔄
Mouvements et rotations fluides des pièces
✨
Effacement des lignes complètes avec animation
📊
Système de score et de niveau dynamique
🔄
Gestion du game over et redémarrage
Démonstration en direct disponible lors de la présentation
Code Source
Découvrez notre implémentation complète sur GitHub avec documentation détaillée, tests unitaires et architecture modulaire.

Repository GitHub
Code source complet avec architecture MVC, documentation JavaDoc, et exemples d'utilisation. Projet organisé en modules pour une maintenance optimale.

Java
Swing
MVC
OOP
Voir le Code Source
Terminal
$ git clone https://github.com/your-repo/tetris-java.git $ cd tetris-java $ javac -d bin src/**/*.java $ java -cp bin Main 🎮 Tetris Game Started! 
Conclusion
✓
Réalisations
✓
Implémentation complète du jeu Tetris en Java
✓
Architecture MVC robuste, claire et évolutive
✓
Interface graphique intuitive et réactive
✓
Intégration d'une base de données via XAMPP pour enregistrer les meilleurs scores
✓
Respect des règles et du fonctionnement du jeu Tetris original
⚠
Difficultés rencontrées
•
Problème avec SQLite : fichier .db en binaire, illisible → passage à XAMPP avec MySQL
•
Rotation des pièces : gestion des collisions aux bords parfois complexe
•
Problèmes de synchronisation entre le rendu graphique et la logique de jeu
•
Difficultés pour fermer la boîte de dialogue "Entrez votre nom" après validation
•
Déclenchement du son du Game Over instable selon les cas
•
Optimisation des performances pour garantir une bonne fluidité
🚀
Perspectives d'amélioration
Système de high scores en ligne ou global
Serveur centralisé pour comparaisons mondiales

Ajout de niveaux de difficulté progressifs
Vitesse adaptative et défis spéciaux

Intégration de sons et musiques immersifs
Ambiance sonore complète et effets audio

Développement d'un mode multijoueur local ou en réseau
Compétition temps réel entre joueurs

💡
Conclusion
Ce projet nous a permis d'appliquer les principes de programmation orientée objet et de conception logicielle dans un contexte interactif.

Nous avons surmonté plusieurs défis techniques, ce qui a renforcé notre autonomie, notre rigueur, et notre capacité à concevoir une application complète.

Les compétences acquises pourront être réutilisées dans des projets plus complexes à l'avenir.

📝
5
Réalisations clés
⚠️
6
Défis surmontés
🚀
4
Améliorations futures
💡
1
Projet réussi
Notre Équipe
Un projet entièrement collaboratif développé par une équipe unie

Travail d'équipe
Anas Ouzine
Aya Laaouine
Ihssane Moulabbi
Kawtar Boutaskiouin
🎓
Remerciements
Nous tenons à remercier notre encadrant du module JAVA

Monsieur Lahcen Momoun

Pour ses précieux conseils, son accompagnement et son expertise qui ont contribué à la réussite de ce projet collaboratif.

Contact
Vous souhaitez en savoir plus sur notre projet ou collaborer sur des projets similaires ? N'hésitez pas à nous contacter !

Documentation
GitHub
Merci pour votre attention !
🎮
🏆
✨
Notre présentation du projet Tetris se termine ici.

Nous espérons que vous avez apprécié notre démonstration et notre approche collaborative.

© 2025 Tetris Project - Développé en équipe

Projet réalisé dans le cadre du module JAVA
