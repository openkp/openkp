# OpenKP

OpenKP jest systemem kadrowo-płacowym do obsługi małych przedsiębiorstw. 

### Budowa za pomocą mavena i uruchomienie na wildfly
* Pobierz źródła https://github.com/openkp/openkp.git
* Zbuduj projekt `mvn install`
* Skopiuj plik `openkp.war` do katalogu `deployments` Twojego serwera aplikacji

### Uruchomienie za pomocą [Dockera](https://docker.io)
* pobierz obraz 
`docker pull openkp/openkp`
* uruchom kontener(port 8080 z Wildfly zostanie zamapowany na lokalny port 80)
`docker run -it -p 80:8080 openkp`
* wpisz w przeglądarce adres
 [http://localhost/openkp](http://localhost/openkp)

### Demo aplikacji
* Demo dostępne jest pod adresem [http://demo-openkp.rhcloud.com/](http://demo-openkp.rhcloud.com/)

### Uczestnictwo w projekcie
* Jeżeli chcesz uczestniczyć w tworzeniu systemu OpenKP zapoznaj się z treścią pliku [CONTRIBUTING.md](https://github.com/andrzejszywala/openkp/blob/master/CONTRIBUTING.md)
