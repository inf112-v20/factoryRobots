# Obligatorisk oppgave 1
## Lagets kompetanse
#### Halvor
Mye erfaring med java. God peiling på data og diverse programmeringsverktøy
Har også en del erfaring med git.

#### Stian
Java: ok Git erfaring: ok

#### Ragnild
Går bachelor i anvendt matematikk 2. året. Har erfaring i Java og Python fra inf100 og inf102.

#### Steffen
Java, python: ok Lærer om lan relevant materiale(INF142) Github erfaring: ok

#### Håvard
Går poststudier etter bachelor i Kognitiv Vitenskap. Har mye erfaring med java, python og haskell, samt litt med prolog, c#, R og html/css/javascript. Har jobbet med animasjoner og lydintegrering i java tidligere. Tar INF142 dette semesteret, som kan være relevant for flerspillerfunksjonaliteten.

## Organisering av laget
Vi besluttet at laget navn blir Factory Robots, og delte laget inn i fem roller:
De fleste rollene ble bestemt ut fra hvilke områder av prosjektet vi ser det er viktig med en overordnet ansvarlig som sørger for at ting blir gjort skikkelig.
Dokumentasjon, git og testing er slike områder. Sekretær er også viktig da loggføring er en stor del av oppgaven. Det er også naturlig å ha en person med ansvar for prosjektet som helhet.

#### Leder: Håvard
Ansvarlig for å planlegge møter, ha overordnet kontroll, og sjekke at alt er klart til innlevering.

#### Sekretær: Steffen
Skriver referat fra møter og gruppetimer, fører notater fra diskusjoner og har ansvar for besvarelsen av obligatoriske oppgaver.

#### Dokumentasjonsansvarlig: Ragnild
Ansvar for javadoc og at bestemt kodestil blir fulgt.

#### Gitansvarlig: Stian
Overordnet ansvar for merging av forskjellige brancher. Sørger for presise commits og oppfølging av issues.

#### Kundekontakt og testansvarlig: Halvor
Møte foreleser og TA for møter. Ansvarlig for at de nødvendige testene er implementert, og kvalitetssikring av disse.

I tillegg har vi bestemt oss for å bruke github project board, med kanban(ish) metodikk.
Altså kanban med kolonnene backlog, to do, in progress, code review og done.

## Overordnet mål applikasjonen:
Programmet skal la deg spille Roborally, dvs. du skal kunne styre bevegelsene til en robot på et brett vha. valg av programkort i et grafisk grensesnitt. Roboten må navigere rundt hull i bakken, lasere og andre roboter uten å dø, slik at den får besøkt alle flaggene i stigende rekkefølge. Roboten som besøker alle flaggene først i riktig rekkefølge har vunnet.

## Krav til systemet:
* Vise spillebrettet.
* Vise spillere.
* Vinne spillet.
* Avslutte spillet.
* Vise og bruke kortstokk.
* Robot må kunne dø.
* Spiller må kunne dø.
* Utføre lovlige trekk.
* Besøke flagg.
* Ta skade.
* Forhindre ulovlige trekk.
* Reparere skade.
* Ta backup.
* Velge brett.
* Fyre av laser.
* Timer ved programmering.
* Programmere robot.
* Godkjenne programmering.
* Robot dør hvis den havner i et hull/utenfor brettet/ tar nok skade.
* Antall tildelte kort justeres ut ifra skade.
* Spille en runde.
* Vegger stopper roboter og lasere.
* Robot stopper laser.
* Dytte andre roboter.
* Annonsere powerdown.
* Iverksette powerdown.
* Reaktiveres fra powerdown.
* Samlebånd beveger robotene.
* Gjennomføre en fase.
* Tannhjul roterer robot.
* Få og bruke option kort.

#### Til første iterasjon:
##### Vise spillbrett:
###### Brukerhistorie: 
Spilleren har behovet for å vise spillbrettet for å se status til spillet
###### Akseptanstkrav: 
Må være et brett med forventet størrelse i x og y akse
Må vise forventede element på brettet 
Ha forventet antall og størrelse ruter
##### Vise en robot: 
###### Brukerhistorie: 
Har behov for å vise spiller for å kunne planlegge neste fase og trekk i spillet. 
###### Akseptansekrav: 
Vise roboten på forventet posisjon i brettet. 
Vise retning roboten står mot.  
##### Bevege robot: 
###### Brukerhistorie: 
Spiller har behov for å bevege robot for å kunne gjennomføre trekk. Ved dette også gjennomføre et spill. 
###### Akseptansekrav: 
Robot beveger seg i rett retning på rett impuls (beveger seg opp på brettet når pil opp blir trykka på) 
Ender på forventet posisjon i brettet etter bevegelse. 

##### Avslutte spillet: 
###### Brukerhistorie: 
Spilleren må kunne avslutte spillet for å gå ut av spillet når spilleren ikke ønsker å spille mer, eller når spillet er ferdig. 
###### Akseptansekrav: 
Programmet kan lukkes uten feilmelding.



## Metodikk
Vi ønsker å drive testdrevet utvikling og parprogrammering etter behov. Altså om vi sitter fast på noe.
Som nevnt tidligere bruker vi en form for kanban, hvor vi legger vekt på å ha klare og tydelige krav. Vi tenker at laget fritt forsyner seg med oppgaver etter hva de selv føler seg kvalifiserte til, men at alle bidrar med et likt antall. Vi ønsker å opprettholde god kommunikasjon om hvem som jobber med hva, hvordan det går og eventuelle ting vi må ta tak i. Dette for å unngå konflikter om hvem som jobber med hva. I tillegg kommer de ulike rollene til å følge opp hver sine ansvarsområder.

Vi vurderer behovet for ekstra møter fortløpende, men i utgangspunktet planlegger vi å ha et ekstra møte i uken (i tillegg til gruppetimen), eventuelt flere etter behov. Det kommuniseres i tillegg fortløpende over slack og messenger. Referater og notater deles på wikien.

## Til neste møte
Lære reglene til Roborally Vi skal ha en plan for hva vi skal ha til neste møte vi skal ha referat til neste møte Vi skal ha en plan for hva vi skal gjøre til neste møte
## Gjenstående
* Filtrere kravlisten
* Fylle inn kompetanse
* Skrive oppsummering
* Arbeid med brukerhistoriene
