OpenKP
======

OpenKP jest systemem kadrowo-płacowym do obsługi małych przedsiębiorstw. 

Budowa za pomocą mavena i uruchomienie na wildfly
------
* Pobierz źródła https://github.com/openkp/openkp.git
* Zbuduj projekt 'mvn install'
* Skopiuj plik 'openkp.war' do katalogu 'deployments' Twojego serwera aplikacji

Uruchomienie za pomocą [Dockera](https://docker.io)
 # pobierz obraz 
    $ docker pull openkp/openkp
 # uruchom kontener(port 8080 z Wildfly zostanie zamapowany na lokalny port 80)
    $ docker run -it -p 80:8080 openkp
 # wpisz w przeglądarce adres
    http://localhost/openkp
