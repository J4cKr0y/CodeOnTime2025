# CodeOnTime2025

##Bilan
Fin du challenge Code On Time de chez Meritis ! 
Félicitations à sullyper pour sa victoire ;) 
Nanored, le vainqueur de l'année dernière, finit 4ème. 
Si je ne m'habuse, il devait y avoir à peu près 350 participants.

Pour ma part, j'ai finit 38ème, je trouve que c'est pas mal pour un petit nouveau qui cherche son entreprise pour une alternance :-P

PS: l'année dernière j'ai finit 157ème, c'est une belle progression, non?

##Détails
# CodeOnTime2025

Fin du challenge Code On Time de chez Meritis ! 
Félicitations à sullyper pour sa victoire 😉 
Nanored, le vainqueur de l'année dernière, finit 4ème. 
Si je ne m'habuse, il devait y avoir à peu près 350 participants.

Pour ma part, j'ai finit 38ème, je trouve que c'est pas mal pour un petit nouveau qui cherche son entreprise pour une alternance 😛

PS: l'année dernière j'ai finit 157ème, c'est une belle progression, non?

## Détails

- AnalyseDechets
-- Ce code Java lit la première ligne d'un fichier nommé dechets.txt, cette ligne contient une série de nombres séparés par des espaces, puis compte la fréquence de chaque nombre (déchet).
-- Il détermine ensuite le nombre (déchet) qui apparaît le plus souvent et l'affiche.
-- C'est une simple itération et comptage de fréquences pour trouver le mode (l'élément le plus fréquent) dans un ensemble de données.

- CSVAnalyzerReleves
-- Ce code Java lit un fichier CSV nommé fichier_releves.csv, en ignorant la première ligne (l'en-tête). Pour chaque ligne suivante, il extrait un identifiant (ID) de la première colonne et trois valeurs numériques (taux) des troisième, quatrième et cinquième colonnes, les valeurs étant séparées par des points-virgules.
-- Il calcule la moyenne de ces trois taux pour chaque ID. Ensuite, il identifie l'ID dont la moyenne est la plus élevée et affiche cet ID ainsi que sa moyenne correspondante.
-- Il s'agit d'une lecture séquentielle du fichier et d'un calcul de moyenne simple par enregistrement. Il utilise une approche de recherche du maximum pour trouver l'ID avec la moyenne la plus élevée.

- CrabeTracker
-- Première version qui ne donnait pas la bonne réponse. 
-- Les commandes de mouvement sont interprétées comme des directions cardinales fixes. C'est une approche valide si c'est ce que la simulation est censée représenter. Mais il était nécessaire de prendre en compte l'orientation du crabe. 

- CrabeTracker2
-- Ce code Java simule le déplacement de "crabes" sur une grille et visualise leurs positions.
-- Il lit une série de "mouvements" à partir d'un fichier texte nommé mouvements.txt. Chaque ligne du fichier représente une séquence de mouvements ('D' pour droite, 'G' pour gauche, 'H' pour avancer, 'B' pour reculer) pour un crabe, en partant du centre d'une grille de 1000x1000. Pour chaque séquence, il calcule la position finale et l'orientation du crabe.
-- Ensuite, le programme crée une interface graphique (avec Swing). Sur cette interface, il dessine une grille. Il représente les positions finales des crabes en colorant les cellules : plus il y a de crabes à une position donnée, plus la couleur (bleue) de la cellule est intense. Si cinq crabes ou moins se trouvent à une position, de petites flèches rouges sont également dessinées pour indiquer leurs orientations individuelles.
-- Il utilise une simulation de mouvement pas à pas pour chaque crabe basée sur une orientation cardinale (droite, bas, gauche, haut). Pour l'affichage, il emploie une technique de visualisation de données par densité (intensité de couleur en fonction du nombre de crabes par cellule) et une représentation graphique des orientations individuelles.

- MaritimeCleanup
-- Ce code a pour but de générer une "solution" (une grille de caractères) basée sur des dimensions lues depuis un fichier d'entrée, puis d'écrire cette solution dans un fichier de sortie.
-- Il lit les dimensions (M et N) et un entier K depuis la première ligne du fichier input.txt.
-- Il ignore les lignes suivantes du fichier d'entrée (qui sont censées représenter une "carte" mais ne sont pas utilisées pour la logique de génération).
-- Il construit une grille de caractères (M lignes par N colonnes) en utilisant des modèles de caractères prédéfinis (S, <, O, v, ^, >, <).
-- Ces modèles sont appliqués différemment à la première ligne, aux lignes du milieu (alternant deux patterns), à l'avant-dernière ligne et à la dernière ligne.
-- Enfin, il écrit la grille de caractères générée dans le fichier output.txt.
-- La méthode/l'algorithme utilisé est une simple génération de pattern basée sur des règles fixes.

- OptimisationMoteur
-- Ce code vise à trouver la séquence optimale de "régimes" d'un moteur sur une période de temps donnée, en maximisant un score d'efficacité total tout en respectant une contrainte sur le nombre de changements de régime.
-- Lecture des données : Il lit un fichier nommé regimes.txt qui contient une matrice d'efficacités. Cette matrice a 10 lignes (représentant 10 régimes différents) et 250 colonnes (représentant 250 instants dans le temps). Chaque valeur efficacites[regime][instant] indique l'efficacité du moteur à un régime donné à un instant précis.
-- Recherche de chemin optimal : La méthode findOptimalPath utilise une recherche de chemin dans un graphe implicite. Elle explore les chemins possibles de régimes à travers les 250 instants.
-- À chaque pas de temps, elle considère tous les régimes possibles.
-- Elle maintient un historique des WINDOW_SIZE (5) derniers régimes pour chaque chemin.
-- Elle applique une contrainte : le nombre de changements de régime au cours des WINDOW_SIZE derniers instants ne doit pas dépasser MAX_CHANGES (2). Si cette contrainte est violée, le chemin n'est pas poursuivi.
-- Les chemins sont explorés en utilisant une file de priorité (PriorityQueue) qui donne la préférence aux chemins ayant le score d'efficacité cumulé le plus élevé (score).
C'est une approche similaire à l'algorithme de Dijkstra ou à une recherche gloutonne avec élagage, mais elle est adaptée pour trouver un chemin maximisant le score sous contrainte.
-- Résultat : Une fois le chemin parcouru sur les 250 instants, le code retourne le chemin (séquence de régimes) qui a obtenu le score d'efficacité total le plus élevé tout en respectant la contrainte de changement de régime. Le résultat est ensuite affiché sur la console.
En bref, c'est un programme d'optimisation de séquence qui trouve la meilleure stratégie pour faire fonctionner un moteur sur 250 instants, en équilibrant efficacité et stabilité (peu de changements de régime).

- OrdreLectureManuel
-- Ce code a pour objectif de déterminer un ordre de lecture pour des pages de manuel en se basant sur leurs références croisées, et d'afficher cet ordre.
-- Lecture des références : Il lit un fichier nommé references.txt. Chaque ligne de ce fichier contient deux numéros de page séparés par un espace, indiquant qu'une page référence une autre. Ces références sont stockées pour former un graphe dirigé, où les pages sont les nœuds et les références sont les arêtes.
-- Construction du graphe : Il construit une représentation de ce graphe où chaque page est associée à une liste des pages qu'elle référence. Il garde également une trace de toutes les pages mentionnées.
-- Tri topologique : utilisation de l'algorithme de Kahn.
-- Il calcule le degré entrant (indegree) pour chaque page, c'est-à-dire le nombre de pages qui la référencent.
-- Il commence avec une file d'attente contenant toutes les pages dont le degré entrant est de zéro (les pages qui ne sont référencées par aucune autre page, et qui peuvent donc être lues en premier).
-- Il traite les pages de la file d'attente une par une, les ajoute à la liste de l'ordre de lecture, puis diminue le degré entrant de toutes les pages qu'elles référencent. Si le degré entrant d'une page devient zéro, elle est ajoutée à la file d'attente.
-- Affichage de l'ordre : Enfin, il affiche la liste des pages dans l'ordre de lecture déterminé par le tri topologique.
-- En résumé, ce programme détermine une séquence de lecture des pages d'un manuel en respectant les dépendances entre elles, en utilisant un algorithme de tri topologique.

- Planches
-- Ce code lit les longueurs de planches à partir d'un fichier texte (planches.txt), puis détermine la taille de coupe maximale possible pour obtenir un certain type de découpe.
-- Lecture des données : Il lit des nombres entiers longs (longueurs de planches) depuis le fichier planches.txt.
-- Recherche de la plus petite planche : Il parcourt toutes les longueurs lues pour trouver la longueur de la plus petite planche.
-- Calcul de la taille de coupe maximale : Il calcule ensuite une "taille de coupe maximale possible" en divisant la longueur de la plus petite planche par 100. -Affichage : Enfin, il imprime le nombre total de planches lues, la longueur de la plus petite planche et la taille de coupe maximale calculée.
-- L'algorithme principal utilisé est une simple recherche du minimum dans une collection de données.

- TrouverNavire
-- Ce code lit des informations sur des navires à partir d'un fichier texte, puis identifie et affiche les identifiants des navires qui correspondent à un ensemble de critères spécifiques.
-- Il lit le fichier "navires.txt". Chaque ligne du fichier est censée représenter un navire, avec un identifiant (une chaîne de caractères) suivi de huit caractéristiques numériques. Le code stocke ces informations en créant des objets Navire.
-- Ensuite, il parcourt tous les navires lus et applique une série de conditions aux caractéristiques de chaque navire. Si un navire satisfait toutes les conditions suivantes :
1.  La première caractéristique est égale à 3.
2.  La deuxième caractéristique est différente de la première.
3.  La troisième caractéristique est égale à la cinquième.
4.  La quatrième caractéristique est égale à 2.
5.  La cinquième caractéristique est différente de la huitième.
6.  La sixième caractéristique est égale à la somme de la deuxième et de la septième.
Alors, l'identifiant de ce navire est affiché sur la console.
-- La méthode utilisée est un simple filtrage de données ou une recherche conditionnelle. 
Le code parcourt une liste d'éléments et applique une série de règles logiques à chaque élément pour déterminer s'il correspond aux critères de sélection.

- Radar*
-- Ces 3 codes cherchaient à répondre au même problème (calculer un rayon minimal pour la couverture de radars), mais de manière différente.
-- RadarCalibration.java : Calcule le rayon minimal en se basant sur la plus grande distance euclidienne entre n'importe quelle paire de radars. 
Le rayon nécessaire serait la moitié de cette distance maximale pour s'assurer que tous les radars sont à portée les uns des autres si un point central imaginaire existait.
-- RadarCalibrationVerticale.java : Se concentre sur la couverture verticale. Il regroupe les radars par leur coordonnée X et trouve la plus grande distance entre deux radars situés sur la même coordonnée X. 
Le rayon minimal est alors calculé comme la moitié de cette distance Y maximale.
-- RadarCoverage.java : Utilise une recherche binaire (calculerRayonMinimal) pour trouver le rayon le plus petit qui permet de couvrir l'intégralité d'une grille de taille GRID_SIZE. 
La fonction peutCouvrir vérifie si, pour un rayon donné, un chemin continu de couverture existe de gauche à droite sur toute la largeur de la grille, en tenant compte des zones de couverture de chaque radar.
-- Aucune de ces approches n'a donné le bon résultat! X-D Donc si vous avez une idée de comment j'aurais dû m'y prendre, je serais ravi d'en apprendre plus :)