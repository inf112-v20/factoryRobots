# Obligatorisk oppgave 1
## Lagets kompetanse
### Halvor
Mye erfaring med java. God peiling på data og diverse programmeringsverktøy
Har også en del erfaring med git.

### Stian
2. året på informasjon- og kommunikasjonsteknologi. Har ikke erfaring fra programmering utover de obligatoriske fagene, samt litt HTML. 

### Ragnild
Går bachelor i anvendt matematikk 2. året. Har erfaring i Java og Python fra inf100 og inf102.

### Steffen
Går studieretning IKT(Informasjon og KommunikasjonsTeknologi). Har grei peiling på java og python, men må jobbe litt med hvordan Github systemet fungerer. I Tillegg tar Steffen opp relevant fag(INF142) angående lan funksjonen som skal tas i bruk i løpet av prosjektet.

### Håvard
Går poststudier etter bachelor i Kognitiv Vitenskap. Har mye erfaring med java, python og haskell, samt litt med prolog, c#, R og html/css/javascript. Har jobbet med animasjoner og lydintegrering i java tidligere. Tar INF142 dette semesteret, som kan være relevant for flerspillerfunksjonaliteten.

## Organisering av laget
Vi besluttet at laget navn blir Factory Robots, og delte laget inn i fem roller:
De fleste rollene ble bestemt ut fra hvilke områder av prosjektet vi ser det er viktig med en overordnet ansvarlig som sørger for at ting blir gjort skikkelig.
Dokumentasjon, git og testing er slike områder. Sekretær er også viktig da loggføring er en stor del av oppgaven. Det er også naturlig å ha en person med ansvar for prosjektet som helhet. Vi har derfor kommet frem til følgende viktige roller...

### Leder: Håvard
Ansvarlig for å planlegge møter, ha overordnet kontroll, og sjekke at alt er klart til innlevering.

### Sekretær: Steffen
Skriver referat fra møter og gruppetimer, fører notater fra diskusjoner og har ansvar for besvarelsen av obligatoriske oppgaver.

### Dokumentasjonsansvarlig: Ragnild
Ansvar for javadoc og at bestemt kodestil blir fulgt.

### Gitansvarlig: Stian
Overordnet ansvar for merging av forskjellige brancher. Sørger for presise commits og oppfølging av issues.

### Kundekontakt og testansvarlig: Halvor
Møte foreleser og TA for møter. Ansvarlig for at de nødvendige testene er implementert, og kvalitetssikring av disse.

I tillegg har vi bestemt oss for å bruke github project board, med kanban(ish) metodikk.
Altså kanban med kolonnene backlog, to do, in progress, code review og done.

## Overordnet mål applikasjonen:
Programmet skal la deg spille Roborally, dvs. du skal kunne styre bevegelsene til en robot på et brett vha. valg av programkort i et grafisk grensesnitt. Roboten må navigere rundt hull i bakken, lasere og andre roboter uten å dø, slik at den får besøkt alle flaggene i stigende rekkefølge. Roboten som besøker alle flaggene først i riktig rekkefølge har vunnet.

## Krav til systemet:
* Vise spillebrettet.
* Vise spillere.
*   Vinne spillet.
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

## Til første iterasjon:
Viser både hvilke funksjoner vi ønsker å ha med til første iterasjon samt brukerhistorier og akseptansekrav. 
### Vise spillbrett:
### Brukerhistorie: 
Spilleren har behovet for å vise spillbrettet for å se status til spillet
### Akseptanstkrav: 
Må være et brett med forventet størrelse i x og y akse
Må vise forventede element på brettet 
Ha forventet antall og størrelse ruter
### Vise en robot: 
### Brukerhistorie: 
Har behov for å vise spiller for å kunne planlegge neste fase og trekk i spillet. 
### Akseptansekrav: 
Vise roboten på forventet posisjon i brettet. 
Vise retning roboten står mot.  
### Bevege robot: 
### Brukerhistorie: 
Spiller har behov for å bevege robot for å kunne gjennomføre trekk. Ved dette også gjennomføre et spill. 
### Akseptansekrav: 
Robot beveger seg i rett retning på rett impuls (beveger seg opp på brettet når pil opp blir trykka på) 
Ender på forventet posisjon i brettet etter bevegelse. 

### Avslutte spillet: 
### Brukerhistorie: 
Spilleren må kunne avslutte spillet for å gå ut av spillet når spilleren ikke ønsker å spille mer, eller når spillet er ferdig. 
###### Akseptansekrav: 
Programmet kan lukkes uten feilmelding.



## Metodikk
Vi ønsker å drive testdrevet utvikling og parprogrammering etter behov. Altså om vi sitter fast på noe.
Som nevnt tidligere bruker vi en form for kanban, hvor vi legger vekt på å ha klare og tydelige krav. Vi tenker at laget fritt forsyner seg med oppgaver etter hva de selv føler seg kvalifiserte til, men at alle bidrar med et likt antall. Vi ønsker å opprettholde god kommunikasjon om hvem som jobber med hva, hvordan det går og eventuelle ting vi må ta tak i. Dette for å unngå konflikter om hvem som jobber med hva. I tillegg kommer de ulike rollene til å følge opp hver sine ansvarsområder.

Vi vurderer behovet for ekstra møter fortløpende, men i utgangspunktet planlegger vi å ha et ekstra møte i uken (i tillegg til gruppetimen), eventuelt flere etter behov. Det kommuniseres i tillegg fortløpende over slack og messenger. Referater og notater deles på wikien.


## Oppsummering
Vi synes at vi startet veldig sterkt med å sette opp teamet og arbeidsoppgaver. I tillegg gikk det veldig bra å få oversikt over det forventede produktet. Dette er grunnet at vi har fått mye bra info fra forelseningene når systemkrav var tema. Git tok tid å lære seg. Det var mange funksjoner som vi ikke var kjent til og det tok litt trial and error for å lære seg proper push and pull, merge, etc.
Vi hadde et veldig bra system og samarbeid til første obligatoriske oppgave og må derfor vurdere den nye oppgaven før vi ønsker store forandringer.

Selv om vi ikke kjente hverandre på forhånd, har kommunikasjon og rollene vi har blitt tildelt i teamet fungert bra. Vi har vært flink til å lære av av hverandre hvor våre områder av kompetanse har blitt delt med teamet slik at vi har fått godt struktur og forståelse for hvordan vi går frem med å utvikle programmet. Kommunikasjonen har foregått hovedsaklig på møtene, men også via slack.

### Github oppsett
Vi har automatisert projektboard til å automatisk legge nye issues i kolonnen "Backlog". Når issues blir lukket vil de automatisk bli flyttet til project board.
Vi har implementert Codacy og Travis-CI inn i Github for å gjøre prosessen og samarbeid lettere.

### Til neste obligatoriske innlevering ønsker vi å...
* Legge til labels i project board.
* Lage ny branch og derretter lage pull request på github hvor vi refererer til relatert issue. Deretter skal gitansvarlig forsikre om at koden er bra nok og derretter merge inn i master. Noe vi har testet til denne innleveringen.
* Vi ønsker å fokusere i større grad på å lage issues til forskjellige krav til spillet.
* Bli flinkere til å kommentere hver eneste commit selv om det store eller små changes.
