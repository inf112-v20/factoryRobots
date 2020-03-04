# Obligatorisk oppgave 2

## Deloppgave 1: Prosjekt og Prosjektstruktur
### Roller i teamet
Vi har valgt å beholdt de samme rollene som før. Rollen som har vært mest relevant i dette prosjektet frem til nå, er teamleader og sekretær. Rollene generelt har fungert bra uten at vi har merket noe behov for å gjøre endring.

### Andre roller
Rollene vi har tildelt fra obligatorisk oppgave 1 fungerer bra nok og vi har derfor ikke trengt å dele ut nye ansvarsområder. Vi som team er heller fleksible etter behov, enn å ha rigide rammer rundt hva vi kan gjøre. Et eksempel på hva en rolle inneholder er at teamleder setter opp møter og leier rom. Ellers innebærer rollene det samme som er beskrevet i oblig1

### Prosjektmetodikk å nevne

Frem til nå har vi delt ut arbeidsoppgaver til vært medlem. Gjennom arbeidsprosessen holder vi god kommunikasjon og spør hverandre om hjelp hvis vi sitter fast. Denne metodikken har fungert bra. Ved hjelp av Project Board på Git unngår vi misforståelser og forvirring i tillegg til at vi får rask progresjon. Når vi tenker på måter å implementere nye funksjoner er det ofte diskusjon på hva som hadde vært den beste løsningen. Dette har vi hatt suksess med frem til nå, da vi som regel kommer frem til en løsning og jobber videre derfra. I dette prosjektet bruker vi kanban som basis for metodikken vår.

### Gruppedynamikk
Gruppemedlemmer jobber med hver sin arbeidsoppgave og tilfeller der arbeidsoppgavene er mer utfordrende så jobber flere med samme arbeidsoppgave.

### Kommunikasjon i gruppen
Kommunikasjonen på møtene er god. Hvert gruppemedlem tar ansvar og gir en innsats for å fullføre sin arbeidsoppgave. Vi stiller spørsmål, får tilbakemeldinger og hjelper hverandre for være produktive som mulig. Om problemer kommer opp, blir de tatt opp til diskusjon. Generelt så jobber vi med prosjektet i møter.

### Retroperspektiv frem til nå
Rollene vi har delt ut har vært viktig for at prosjektstrukturen vår har hatt en jevn flyt. Derfor har vi startet hvert møte med å bli enig om hva vi ønsker å få ut av møtet og at alle har hatt arbeidsoppgaver å jobbe med. 
Selv om kompetansen varierer i teamet, prøver vi å gi ut varierende arbeidsoppgaver der vi ønsker å utvide kompetansen til alle på teamet og samarbeider for å komme videre i prosjektet. Fra start av ønsket vi å drive testdrevet utviktlig og par-programmering. I ettertid har vi funnet ut at dette ikke er den beste metoden for oss å arbeide på. Dette er fordi det er vanskelig å drive tester når vi jobber med GUI og et ukjent rammeverk. Vi har som tidligere nevnt basert oss på kanban, men tilpasser oss til hvilken fase i prosjektet vi er i.

 - Et punkt vi kan forbedre er hvordan kommunikasjonen er på Slack mellom møtene. Selv om vi etter hvert møte blir enig om hva vi skal gjøre til neste gang er det ikke mye kommunikasjon ellers. Dette er noe kan bli flinkere på som for eksempel å stille hverandre spørsmål eller spørre hvis vi lurer på noe.
 - I tillegg ønsker vi å bli bedre på å arbeide jevnt mellom møtene. Det holdes ikke nok møter til at vi kommer i mål om vi bare skal jobbe der. 

### Vektlegging på commit i gruppen
Som tidligere nevnt er det forskjellig kompetanse på teamet. Per nå, har vi flere brancher med større funksjonalitet i spillet som ikke er klar til å bli merget inn i master enda. Bidragene målt på GitHub er ikke representative per nå. Medlemmer som har mindre erfaring med programmering har til nå bidratt i større grad til skriving av obligatoriske oppgaver og referater. Utover prosjektet regner vi med at arbeidsmengden skal jevne seg ut, da flere blir kjent med kodebasen og biblioteket. Det blir også lettere å bidra når issuene blir mer konkrete og rammeverket er på plass. 

### Referater 
Møtereferat er lagt ved på repositoriets Wiki. Hvis ikke annet er nevnt, var alle tilstede på møtet.

### Tre forbedringspunkter
 - Vi ønsker bedre kommunikasjon mellom møtene.
 - Bli flinkere til å legge ved label på projectboardet.
 - Å arbeide mer jevnt mellom møtene

## Deloppgave 2: Krav

Noen av disse kravene er veldig små endringer og ikke spesifike brukerhistorier. Vi ønsker å vise alle punktene men vil spesifisere hva som er viktigere enn andre. De som vi har startet på mangler commit.

### Impementer kortstokk
 - Brukerhistorie: For at spilleren skal kunne velge videre trekk.
 - Akseptansekriterier: Lag kortstokken, rett mengde kort av hver type og må trekke tilfeldig kort.
 - Referanse commit: 8509bac45f97315e85536077769cad411ae6fbcc

### Iverksette powerdown
 - Brukerhistorier: For at spilleren skal få en mulighet til å ta et strategisk valg til å regenerere roboten sin.
 - Akseptansekriterier: Alle andre skal få se at spilleren velger å sette seg i powerdown og at alle skal få muligheten til å sette seg i powerdown i rett rekkefølge. Spiller mister kortfunksjon for neste runde og fjerner alle damagetokens du hadde før runden. 

### Ha fungerende lasere
 - Brukerhistorie: At robot og vegger skal ha mulighet til å fyre av lasere.
 - Akseptansekrav: Blir stoppet av første objekt den treffer. Gir ett damagetokens og blir avfyrt etter hver fase.

### Abstrahere kart funksjonalitet
 - Arbeidsoppgave i prosessen for å implementere objektenes funksjonalitet og å ha et verktøy for å holde styr på spillets tilstand
 - Akseptansekriterier: Få funksjonalitet til elementene på kartet
 - Referanse commit: 42afc72366a50dc879a3751759031287d0da0f27
 - Referanse commit: 1e4570ddf0214eeb6c2823e8199211ce01430bfc

### Implementere retning
 - Brukerhistorier: Vil vite hvilken retning objektet peker i, slik at en kan planlegge neste trekk.
 - Akseptansekriterier: Holder styr på objektets pekende retning.
 - Referanse commit: f6a73e7ae9b0dfb794c4ee4cbd6b7bbebc5ef8da

### Legge til innhold, cellestruktur og posisjon
 - Arbeidsoppgave i prosessen for å implementere objektenes funksjonalitet
 - Akseptansekriterier: Vi har en struktur som holder alle objekt på kartet. 
 - Referanse commit: 6fcd5664d8e50ee5e19c043bf1862a0ff57ad896

### Fabrikkfunksjon for å lage objekter i kartet
 - Arbeidsoppgave i prosessen for å implementere objektenes funksjonalitet
 - Akseptansekriterier: Ha en funksjon som leser kartet og lager objekter utifra innholdet.
 - Referanse commit: 6fcd5664d8e50ee5e19c043bf1862a0ff57ad896
 - Referanse commit: 686db56fa26dbeccf5fab93a3083cca990db2cf0

### Lage enum for å identifiere celler 
 - Arbeidsoppgave i prosessen for å implementere objektenes funksjonalitet
 - Akseptansekriterier: Lage enum som mapper objektets navn til nummeret objektet har i tile-settet.
 - Referanse commit: 1b03cf4ece885b6b8f3a098e9bb20cd3e3c97182


### Forhindre ulovlige trekk
- Brukerhistorier: For at en skal kunne spille spillet etter reglene.
- Akseptansekriterier: Roboten blir stoppet av vegger. Roboten blir stoppet på kanten av kartet.
 - Referanse commit: 273d4d94996812a8130a6b1f0f4278b4ca757772

## Ferdig med krav


### Prioriteringer fremover
Vi ønsker å fokusere på å utvikle funksjonene på de ulike objektene på kartet. Eksempler på dette er laser, belt og logikken bak spillkortene. Inkludert i dette er det å importere objekt fra Tiled-kartet som Java-objekter. Dette er viktig å få unnagjort fordi det er greit å ha på plass før vi går videre med utviklingen av spillet. 

### Hvordan vi har prioritert oppgaver i prosessen

### Deler av MVP og hvorfor
Krav som vi ser på som en del av MVP funksjonene er de som vi fikk gjort i den første oppgaven. Dette inneholder funksjonene kart, spillere, bevegelse og avslutte spillet. Dette vil si at det er mulig å spille spillet, men det er ingen brett-funksjoner som vil gjøre spillet interessant.
 - Kart er viktig fordi vi trenger et kart å bevege oss rundt på.
 - Spiller er viktig fordi vi trenger spillere for å spille spillet.
 - Bevegelse er viktig fordi vi trenger å gå rundt på brettet.
 - Avslutte spillet er viktig fordi vi trenger muligheten til å slutte å spille når vi ikke ønsker å spille.

 Vi har til nå fulgt rekkefølgen fra kunden og hatt fokus på hovedfunksjonaliteten i spillet. Vi ønsker å fortsette ut fra kjernefunksjonaliteten. Der kjernen er det som er nevnt over, etterfulgt av objektfunksjonalitet som for eksempel at en laser fyrer av eller at man spinner på et tannhjul.


### Krav vi har prioritert
Vi har prioritert å å lage kartet og funksjonaltet til celler slik at funksjonen til hvert objekt kan bli implementert i fremtiden. Funksjoner vi har implementert er at spiller ikke kan gå gjennom vegger eller forlate kartet. Vi har generert og instansiert objekter på kartet. Videre er det da viktig for oss å implementere objektenes funksjoner og kortstokk-funksjonalitet.

### Bugs i krav
Vi har passet på å ikke merge brancher om vi finner bugs. Derfor har vi ikke opplevd noen feilfunksjoner opp til nå. 

## Deloppgave 3: Kode

### Bygge, teste og kjøre prosjekt
 - finner du i README.md

### Kodekvalitet og testdekning
 - Manuelle tester finner du i Deliverables/manualTests.md

### Lever potensiell klassediagram(om det er mange klasser)
 - Finner du i Deliverables/UML



