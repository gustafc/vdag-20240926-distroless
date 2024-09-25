# Vässardag 2024-09-26: Distroless Java

Tre exempel på hur man bygger distrolösa images:
- `simple-native`: "Hello World" i C som körs i en distrolös container
- `simple-java`: "Hello World" i Java som körs på Java-vänlig distrolös bas-image, naivt exempel
- `spring-distroless`: Mer realistisk webapp med Spring Boot som körs i en selektivt konstruerad distrolös image

Samtliga går att bygga genom att gå in i katalogen och köra `docker build .`

[Se slides för presentationen](https://docs.google.com/presentation/d/14Olw2dDD_0WNVHKXvYIDsUBeCIEi8wKomTyAGprRA3A/edit?usp=sharing)