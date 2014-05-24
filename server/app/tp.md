# Getting started

Créer un dossier `libs` dans lequel vous ajouterez les différentes librairies que nous utiliserons.
Ajouter à ce dossier la librairie AngularJS.
Créer un fichier `index.html` contenant le minimum pour démarrer une application angularjs.
Créer un fichier `app.js` avec la déclaration de votre application.
Nous allons utiliser Twitter Bootstrap pour rendre notre application plus jolie.
Télécharger Twitter Bootstrap et ajouter le fichier CSS dans votre HTML.


# Page d'accueil

Compléter le body de la page pour afficher le nom du site (en titre, assez gros, centré).

Créer un premier controller `MainCtrl` responsable du `body`.
Ajouter une variable `users` sur le `$scope`, contenant pour l'instant un tableau de deux utilisateurs, par exemple :

    [{name:'Cedric'},{name:'JB'}]

Afficher cette liste d'utilisateurs sur la page d'accueil.


# Appel HTTP

Nous allons maintenant récupérer la liste des utilisateurs sur le serveur.
Pour cela, utiliser le service `$http` et faire un GET sur l'url `/users` et
mettre le résultat dans la variable `users` du `$scope`.


# Modules

Nous utiliserons le module de route d'AngularJS, `ngRoute` à inclure dans vos librairies
également et dans la déclaration de votre application.
De même avec le module `ngCookies`.


# Static server

Nous allons avoir besoin de servir notre webapp depuis un petit serveur. Pour cela, installer

    npm install http-server -g

Puis dans le répertoire de votre application, lancer :

    http-server


# Routes

Ajouter une `div` avec la directive `ngView` dans le `body`.
Placer le contenu actuel dans un template nommé `main.html` dans un dossier `views`.

Utiliser la méthode `config` sur votre application pour configurer les routes.
L'url racine devra renvoyer vers le template `views/main.html` et le controller `MainCtrl`.
Ajouter un bouton 'Register', renvoyant vers l'url '#register' et un bouton 'Login' renvoyant
vers l'url '#login'.

# Inscription

Nous voulons maintenant ajouter une route `/register` pour ajouter un nouvel utilisateur, qui
utilisera le nouveau controller `RegisterCtrl` et une nouvelle vue `views/register.html`.

Créer un formulaire contenant les champs :

- `name` pour le nom de l'utilisateur
- `login` pour son login (longueur maximum 10 caractères)
- `password` pour son mot de passe (longueur minimum 5 caractères)
- `age` pour son age (minimum 12 ans, maximum 99 ans)

Des messages d'avertissement doivent apparaître si les champs ne sont pas valides.

Ajouter un bouton d'enregistrement qui appelera la méthode `register` de notre controller (le bouton
  ne sera clickable que si le formulaire est valide).
Celle-ci devra appeler le serveur avec un POST sur `/users`. En cas de succès, l'application
devra rediriger vers la page d'accueil (utiliser `$location.path()`).

# Login

Nous voulons maintenant ajouter une route `/login` pour ajouter un nouvel utilisateur, qui
utilisera le nouveau controller `LoginCtrl` et une nouvelle vue `views/login.html`.

Cette vue contiendra deux champs :

- `login`
- `password`

et un bouton de login qui appelera la méthode `login` du controller. Celle-ci devra
appeler le serveur avec un POST sur `/login`. En cas de succès, stocker un cookie
nommé `login` avec le token secret renvoyé par le serveur.
Pour cela, utiliser le service `$cookieStore` dans votre controller.

Afin d'envoyer ce token au serveur pour les requêtes suivantes (et ainsi montrer que vous êtes bien authentifiés)
indiquer au service http ce token :

    $http.defaults.headers.common['token'] = data;

Pour éviter de se logger à chaque rechargement de l'application, indiquer à Angular de
remettre ce header à chaque démarrage avec :

    app.run(function($http, $cookieStore){
      $http.defaults.headers.common['token'] = $cookieStore.get('login');
    });


# Page d'accueil

Dans le controller `MainCtrl`, ajouter un attribut `isLogged` permettant de vérifier si
l'utilisateur est loggé ou non. Par souci de simplicité cette méthode ne fera que vérifier si un cookie nommé `login` est présent.

Dans le template de la page d'accueil, n'afficher alors les boutons de login et d'enregistrement
que si l'utilisateur n'est pas connecté.
Si il est connecté, afficher deux nouveaux boutons :

- "Top shows" qui renvoie sur `/#top`

# Top

Nous voulons afficher la liste des séries les plus aimées par les utilisateurs.
Pour cela créer une nouvelle route pour l'url `/top`, avec un controller `TopCtrl` et une vue
`views/top.html`. Le controller appelera le serveur avec un GET sur `/shows` pour récupérer
la liste des séries et leurs notes.
Afficher cette liste dans le template, ordonnée par leur note.
Chaque série est clickable et renvoie vers une url `/shows/id_du_show`.

Ajouter un filtre qui permet de rechercher un show par son nom.

# Détail d'une série

Créer une route pour les urls dynamiques `/shows/id_du_show`, ainsi qu'un controller
`ShowCtrl` et une vue `views/show.html`. Le controller récupère le détail de la série
auprès du serveur grâce à un GET sur `/show/id_du_show`. Pour récupérer l'id du show dans le
controller, utiliser le service `$routeParams`.
Dans le template, afficher le détail de la série et un bouton "I love this show".
Le bouton est actif si l'utilisateur n'a pas voté pour cette série (attribut
`userAlreadyVoted`). Un click sur le bouton appelle une méthode du controller chargée de faire un PUT auprès du
serveur. Si l'utilisateur a déjà voté, afficher un bouton "I don't like it anymore" qui lui
fait un DELETE auprès du serveur.
