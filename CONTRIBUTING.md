# Uczestnictwo w OpenKP

Jeżeli chciałbyś współuczestniczyć w tworzeniu OpenKP możesz to zrobić w prosty sposób tworząc konto na GitHub i robiąc forka repozytorium OpenKP a następnie wysyłając pull request z Twoimi zmianami.

Poniżej opiszę jak wykonać przykładową zmianę w projekcie wykorzystując środowisko Eclipse.

### Zrobienie kopii źródeł:
* Załóż konto na [GitHub](https://github.com/)
* Zaloguj się na swoje konto w [GitHub](https://github.com/)
* Wejdź na repozytorium [OpenKP](https://github.com/openkp/openkp) 
* Kliknij przycisk `Fork` znajdujący się po prawej stronie u góry (w tym momencie do twojego konta zostanie dodana kopia repozytorium openkp)
* Wejdź na stronę główną swojego profilu [GitHub](https://github.com/) kliknij w zakładkę `Repositories` powinieneś widzieć linka `openkp` i pod nim napis `forked from openkp/openkp`
* Kliknij w linka `openkp` to cię przeniesie do twojej kopii repozytorium
* Z prawej strony powinno być pole `HTTPS clone URL` a w nim link do źródeł twojej kopii repozytorium. Skopiuj go.

### Konfiguracja środowiska deweloperskiego
* Zainstaluj [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* Ściągnij i rozpakuj [Eclipse IDE for Java EE Developers](https://www.eclipse.org/downloads/)
* Ściągnij i rozpakuj serwer aplikacji [Wildfly](http://wildfly.org/downloads/)
* Uruchom Eclipse(jeżeli masz więcej zainstalowanych JRE to najlepiej jak uruchomisz z zainstalowanego wcześniej JDK 8)
* Jak się uruchomi Eclipse to kliknij w menu `Help->Eclipse Marketplace...`
* W polu `Find` wpisz `JBoss Tools` zainstaluj zgodne z twoją wersją Eclipse np. u mnie na Eclipse Luna zainstalowałem `JBoss Tools (Luna) 4.2.2.Final`
* Znowu wejdź do `Eclipse Marketplace` tym razem wpisz w polu wyszukiwania `EGit` i zainstaluj `EGit - Git Team Provider`
* Z menu Eclipse wybierz `File->New->Other...` i wpisz `Server` w polu wyszukiwania
* Zaznacz `Server` kliknij `Next`
* Wybierz `WildFly 8.x` lub inny zgodny z wersją `Wildfly`, którą ściągnąłeś
* Na zakładce `JBoss Runtime` wpisz katalog do którego rozpakowałeś `Wildfly` w polu `Home Directory` 
* Wybierz Javę 8(jeżeli takiej tutaj nie ma to możesz dodać przez kliknięcie `Installed JREs...`)
* Klikasz `Finish`. W dolnym panelu Eclipse na zakładce `Servers` powinien pojawić się nowo dodany przez ciebie serwer `WildFly`. Jeżeli klikniesz go prawym przyciskiem myszy i wybierzesz `Start` to powinien się uruchomić.

### Konfiguracja projektu
* Z menu Eclipse wybierz `File->New->Other...` i wpisz `Maven` w polu wyszukiwania
* Wybierz `Check out Maven Projects from SCM` i kliknij `Next`
* W polu `SCM URL` wybierz `git` i obok wklej skopiowany wcześniej adres do źródeł twojego repozytorium
* Kliknij `Finish`. 
* W Eclipse na zakładce `Servers` kliknij prawym przyciskiem myszy i wybierz `Add and Remove...`
* W okienku, które się pojawi zaznacz `openkp` i kliknij przycisk `Add >` a następnie `Finish`
* Uruchom serwer
* Wpisz w przeglądarce [http://localhost:8080/openkp/app.html](http://localhost:8080/openkp/app.html) 



 

## Licencja

Wszystkie pliki w projekcie są na licencji Apache 2.0

Jeżeli tworzysz nowy plik to powinieneś na początku dodać taki nagłówek:

```
/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```
