# Thovin
Application mobile de livraison en ligne de nourriture.

Projet effectué dans le cadre de l'année universitaire 2020/2021 à la Faculté des sciences de Montpellier.

- [Thovin](#thovin)
  - [Pré-requis](#pré-requis)
  - [Installation](#installation)
  - [Lancer](#lancer)
    - [Serveur](#serveur)
    - [Application mobile](#application-mobile)
  - [Comment trouver mon IP](#comment-trouver-mon-ip)
  - [Gestion d'erreurs](#gestion-derreurs)


## Pré-requis
* Serveur [AP-EAT](https://github.com/Dorpaxio/AP-EAT)
* Téléphone portable et/ou émulateur utilisant Android API >= 23.

## Installation
```bash
git clone git@github.com:GraciaMoulisKevin/Thovin.git
cd Thovin
```

## Lancer

###  Serveur

Pour initialiser et démarrer le serveur vous pouvez suivre les instructions fournis dans le README du dépôt git de l'API ([accéder au git](https://github.com/Dorpaxio/AP-EAT)).

### Application mobile

1. Ouvrir le projet avec Android Studio. En cas d'erreur voir [Gestion d'erreur](#gestion-derreurs).
2. Si vous souhaitez démarrer l'application via votre mobile personnel passez à l'étape suivante, sinon passez directement à l'étape 6.
3. Eteignez les données mobiles de votre téléphone (3g/4g) et connectez vous en Wifi.
4. Accéder au fichier [HttpClient.java](app/src/main/java/com/example/thovin/services/HttpClient.java) et modifiez l'attribut *BASE_URL* en décommentant la ligne associé et en commentant l'autre, comme ci-dessous.

Chemin du fichier: *app/src/main/java/com/example/thovin/services/HttpClient.java*

```java
private static final String BASE_URL = "http://192.168.1.15:29321/v1/"; // personal device
// private static final String BASE_URL = "http://10.0.2.2:29321/v1/"; // emulator
```

5. Modifier la ligne décommentez en y remplaçant l'IP présente par la votre (ici *192.168.1.15*). Pour trouver votre IP facilement voir [Comment trouver mon IP](#comment-trouver-mon-IP).
6. Lancer l'application en appuyant sur le bouton *run* ou en pressant *Maj+F10*

## Comment trouver mon IP

#### Windows

1. Ouvrir une invite de commandes.
2. Exécuter la commande suivante.
```bash
ipconfig #for Window users
ifconfig #for Mac/Linux users
```
3. Copier l'adresse IPv4 de la carte réseau utiliser par votre machine (ethernet ou sans-fil).

## Gestion d'erreurs

Voici la liste des potentielles que nous avons pus rencontrer et que vous êtes susceptible d'obtenir.

#### 📛 Android Support plugin cannot open this project.

<details> 
    <summary> Détails de l'erreur </summary>
    
    This version of the Android Support plugin for IntelliJ IDEA cannot open this project, please retry with version 4.2 or newer.
</details>

<details>
<summary> Résolutions possibles </summary>
<br/>

1. Accéder au fichier [build.gradle](https://github.com/GraciaMoulisKevin/Thovin/blob/main/build.gradle)

Chemin du fichier: */build.gradle*

2. Modifier la version de Gradle avec la version `4.1.3` ou `4.0.2`

```java
dependencies {
    classpath 'com.android.tools.build:gradle:4.1.3'
``` 
</details>



---

#### 📛 Connection/Inscription (téléphone personnel seulement).

<details> 
<summary> Détails de l'erreur </summary>
<br/>

Lorsque que vous essayer de vous connecter ou de vous inscrire le message suivant apparaît: "Connexion impossible" dans une SnackBar jaune.
</details>

<details> 
<summary> Résolutions possibles </summary>
<br/>

1. Vérifier que votre téléphone est connecté en Wifi avec les données mobiles désactivées.
2. Vérifier que l'adresse IP référencé dans [HttpClient.java](app/src/main/java/com/example/thovin/services/HttpClient.java) est bien celle de votre machine ou vous démarrer l'application.
3. Vérifier que le serveur Node, [AP-EAT](https://github.com/Dorpaxio/AP-EAT), est bien en marche.
4. Patientez quelque instants avant de refaire une requête, parfois cela fonctionnait après un certains temps.
</details>
