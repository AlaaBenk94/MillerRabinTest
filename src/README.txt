DEVOIR MAISON
Inrodution a la cryptographie
=============================
Ce projet est une implémentation du test de primalité d'un nombre de MILLER RABIN.

Auteurs
=======
Ce projet a été réalisé par :
	- BENKARRAD Alaa Eddine
	- CHERIFI Abdelaziz

Listings des fichiers
=====================
Rapport.pdf : les réponses au questions de l'enoncé.
test.txt : le log des differents tests 
MillerRabin.java : le code source du projet.
MillerRabin.class : l'executable du programme.
makefile : le makefile du programme qui rend la compilation facile
README.txt : guide d'utilisation du programme.


COMPILATION & EXECUTION
=======================
1. pour compiler le programme veuillez executer la commande suivante :
$	javac MillerRabin.java
ou a l'aide de makefile executer :
$	make
le resultat de cette commande est l'executable de programme

2. pour lancer le programme executer une des commandes suivantes :
**************************************************
	le fonction decomp 
	$	java MillerRabin decomp <n> <cpt>

	avec :
	n      le nombre entier a tester en Hexadecimal, mettez 0 pour qu'il sera choisi au hasard.
	rpt    nombre de répétition de la methode.

	exemples :
	$	java MillerRabin decomp 15 1
	$	java MillerRabin decomp 0 100
**************************************************
	la fonction d'exponentiation modulaire
	$	java MillerRabin expmod <a> <t> <n>
	$	java MillerRabin expmod <rpt>

	avec :
	a      la base en Hexadecimal
	t      l'exposant en Hexadecimal
	n      le modulo en Hexadecimal
	rpt    nombre de répétition de expmod, si vous choisissez cette expression les parametres a, t et n seront choisis aléatoirement

	exemples :
	$	java MillerRabin expmod 1211215 6581 34674
	$	java MillerRabin expmod 10000
**************************************************
	le test de miller rabin
	$	java MillerRabin miller <n> <cpt> [<rpt>]

	avec :
	n      le nombre entier a tester en Hexadecimal
	cpt    compteur d'algorithme de miller rabin
	rpt    nombre de répétition de test Miller Rabin, si vous choisissez cette expression le nombre n sera choisi aléatoirement

	exemples :
	$	java MillerRabin miller 13216498413348741 20
	$	java MillerRabin miller 10 20 100
**************************************************
	la fonction eval 
	$	java MillerRabin eval <bits> <cpt> <rpt>

	avec : 
	bits   taille en bits de nombre entier
	cpt    compteur d'algorithme de miller rabin
	rpt    nombre de répétition de la fonction eval()

	exemples :
	$	java MillerRabin eval 256 20 10
**************************************************

3. pour affcher l'aide du programme lancez :
$ java MillerRabin

4. pour afficher l'aide d'une fonction (ou connaitre ses parametres) lancez :
$ java MillerRabin <nom de la fonction>

Test des 3 nombres
==================
Pour tester les nombres donnée dans l'enoncé executez :
$	java MillerRabin miller FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A63A3620FFFFFFFFFFFFFFFF 20

$	java MillerRabin miller FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEC4FFFFFDAF0000000000000000000000000000000000000000000000000000000000000000000000000000000000000002D9AB 20

$	java MillerRabin miller FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381FFFFFFFFFFFFFFFF 20