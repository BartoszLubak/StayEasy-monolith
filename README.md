**StayEasy to aplikacja webowa do zarządzania rezerwacjami hotelowymi. Aplikacja wykorzystuje następujące technologie:**

**Spring Boot** - framework do tworzenia aplikacji webowych w języku Java

**Spring Security** - moduł Spring Boot do uwierzytelniania i autoryzacji użytkowników

**Spring Data** - moduł Spring Boot do dostępu do baz danych

**Hibernate** - framework do mapowania obiektów na bazy danych

**Thymeleaf** - silnik szablonów do generowania widoków HTML

**Docker** - technologia do tworzenia i uruchamiania kontenerów


**Funkcjonalność**

Aplikacja umożliwia następujące operacje:

Rejestracja użytkowników

Logowanie użytkowników

Wyszukiwanie hoteli

Wyświetlanie szczegółów hotelu

Rezerwacja pokoju w krokach

Anulowanie rezerwacji

**Aby zainstalować aplikację, wykonaj następujące kroki:**

Wymagania:

Java 17
MySQL 8.0

Instrukcje:

Stwórz bazę danych MySQL o nazwie stayeasy.

Importuj dane z pliku data.sql do bazy danych stayeasy.

Skompiluj aplikację za pomocą następującego polecenia:

```mvn clean install```

Uruchom aplikację za pomocą następującego polecenia:

```java -jar target/StayEasy-monolith-0.0.1-SNAPSHOT.jar```

**Użycie**

Aby użyć aplikacji, otwórz przeglądarkę internetową i przejdź do adresu http://localhost:8080.

**Kontakt**

Jeśli masz jakieś pytania lub uwagi, skontaktuj się ze mną za pośrednictwem GitHuba.


**Dodatkowe informacje**

```To-do
Funkcjonalności w trakcie opracowania
Dodać obsługę płatności
Dodać obsługę recenzji hoteli
Dodać obsługę edycji rezerwacji
Dodać weryfikację dodawanych hoteli z wykorzystanie API Google Places
```
