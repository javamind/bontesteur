bontesteur
==========

Comme nous avons eu de bons retours (**Agnes Crepet** et **Guillaume EHRET**) sur notre présentation à Devoxx 2014  <a href="http://cfp.devoxx.fr/devoxxfr2014/talk/BBV-277/Le%20bon%20testeur%20il%20teste....%20et%20le%20mauvais%20testeur%20il%20teste%20aussi...">Le bon testeur il teste, le mauvais testeur il teste..."</a> nous avons choisi de redonner ce talk. Le projet a donc été quelque peu raffraîchi.

<img src="https://github.com/javamind/bontesteurweb/blob/master/app/images/us.jpg?raw=true"/>

Nous avons deux modules

* un module web avec une interface en Angular JS
* une partie serveur fournissant une API restfull

_Ce repository est dédié à la partie serveur._

Fonctionnellement, cette petite application simule une gestion des conférences de développeurs se déroulant en France et dans le monde. Le modèle de données est le suivant

* conférence
* talk : une conférence à x talks. Un talk a x talks
* speaker : une conférence à x speakers. Un speaker a x speakers
* pays : une conférence est liée à un pays, un speaker est lié à un pays

Au niveau de la stack technique nous utilisons

* postgresql 9.1
* spring core, test et webmvc : 4.0.6.RELEASE
* spring data jpa : 1.5.0.RELEASE
* hibernate 4.3.6.Final
* javax.servlet-api : 3.0.1
* ...

Mais aussi des librairies pour vous aider dans vos tests

* DbSetup 1.3.0
* AssertJ 1.6.1
* JUnitParams 1.0.3
* Mockito 1.9.5
* ...

Pour la base de données nous utilisons Flyway. Si vous voulez l'initialiser vous devrez lancer le goal maven

mvn flyway:clean flyway:migrate

