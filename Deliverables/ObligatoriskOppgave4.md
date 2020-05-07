### Del 1: Team og prosjekt

## Referat fra møter
Møtereferatene er lagt ved i Wiki'en

## Roller i teamet
Det er ikke gjort noen endringer i rollene på teamet. Alle er inneforstått med sin rolle og hva som forventes at en skal bidra med.

## Nevneverdig prosjektmetodikk
Etter nedstengingen fant vi oss en hverdag som fungerte godt for utviklingen og fremgangen i prosjektet vårt. Det har vært enklere å organisere møter hvor alle har hatt mulighet til å være med, noe som har gjort at vi har fått til flere møter enn vi gjorde når vi fysisk møttes. I tillegg har vi kunnet ha lenger møter, da vi er mer fleksible etter nedstengingen av Universitetet. Av den grunn har vi kunnet diskutere mer for å finne gode løsninger. Vi har opprettholdt samme prosjektmetodikk, altså å bruke Project Board på Github, parprogrammering med skjermdeling og Discord som kommunikasjonskanal.

## Retroperspektivt frem til nå
Som vi tidligere har vært inne på, har forbedringspotensialene for oss hovedsakelig vært det individuelle arbeidet som ble gjort mellom møtene, samt hvor ofte vi organiserte møter. Dette har som sagt utviklet seg positivt i takt med frister som nærmer seg, men ikke minst en mer fleksibel hverdag som gjør at hyppigheten på møtene og fremgangen har blitt mye bedre. Sett tilbake til start, har vi hatt godt utbytte av milestones, fordi det har tillatt oss å organisere hva vi ønsker å få gjort til neste innlevering, fordele hva vi forventer at hver av oss skal gjøre både mellom hvert møte og gjennom milestonen. Derfor har vi opprettholdt et godt overblikk og kontroll på hvordan prosjektet tar form. Likevel har vi sett at mange flere issues oppstår underveis som vi jobber, så vi har også måtte tilpasse oss underveis. 

## Project board
Dette er linken til et bilde av projectboard: https://scontent.xx.fbcdn.net/v/t1.15752-0/p280x280/96427397_238870280524151_1595107246931443712_n.png?_nc_cat=101&_nc_sid=b96e70&_nc_ohc=czw9tp_w5TEAX9xTUux&_nc_ad=z-m&_nc_cid=0&_nc_zor=9&_nc_ht=scontent.xx&oh=8c9d66a70b1e0b38c9c6eed7db0b1240&oe=5EDB6A2E

## Gruppedynamikk og kommunikasjon
Vi har tidligere i oppgaven gitt uttrykk for hvordan nedstengigen påvirket oss. I korte trekk handler det om at vi har fått til flere møter, bedre arbeidsflyt og en god kommunikasjon hovedsakelig gjennom Discord. Etterhvert som teamet har lært hverandre bedre å kjenne, har det bidratt til at vi bedre kjenner hverandre styrker, at vi derfor vet hvem vi skal spørre om hva og hvem som kan ta hvilke oppgaver.

# Del 2: Krav 

## Prioriterte krav 
Til denne innleveringen har vi prioritert å få programmet til MVP. Disse kravene kommer frem i Milestones - Assignment 4.
Link til Milestone - Assignment 4: https://github.com/inf112-v20/factoryRobots/milestone/2

Her handler det hovedsakelig om krav og prioriteringer som gjør at vi skal får et ferdig produkt. Det innebærer at spillet har alle funksjonaliteter som kreves for å spille Roborally og minimalt med bugs, så langt som vi klarer å teste. 

## Brukerhistorier, arbeidsoppgaver og akseptansekrav
Vi har skrevet brukerhistorier, arbeidsoppgaver og akseptansekrav på vært enkelt issues knyttet til Assignment 4 i Milestone. Under beskrives alle issues med brukerhistorie, akseptansekrav og arbeidsoppgaver for Assignment 4.

Vi har fokusert på å legge inn brukerhistorier, akseptansekrav og arbeidskrav til issues knyttet til assignment 4, og derfor ikke lagt ved de vi har beskrevet tidligere. 

## Iverksette powerdown

### Brukerhistorie: 
Spiller skal kunna ta et taktisk valg om å gå inn i powerdown, for så å gå inn i powerdown runden etter. 

### Akseptansekrav:
Robot blir satt i powerdown om spilleren runden før annonserte powerdown. Da skal alle damagetokens bli kastet og roboten blir ikke tildelt kort.  

### Arbeidsoppgaver: 
* Må hver runde se hvem som runden før annonserte powerdown 
* Om de runden før annonserte powerdown, blir de satt i powerdown og ikke tildelt kort 
* Om de runden før annonserte powerdown, blir alle damage tokens kastet

## Lage flere alternativ til spillbrett

### Brukerhistorie: 
Spilleren skal selv få mulighet til å velge hvilket brett som skal bli spilt på.

### Akseptansekrav: 
Spilleren skal før spillet begynner ha muligheten til å gå gjennom og velge hvilket brett som skal bli spilt.

### Arbeidsoppgaver:
* Lage nye brett
* Legg til meny for å kunne velge hvilket brett som skal brukes.

## Timer ved programmering

### Brukerhistorie: 
Spillet skal følge reglene til Roborally. 

### Akseptansekrav:
Når spilleren sin robot er den siste som ikke har godkjendt programmeringen sin, så vil det vere en timer som teller ned fra 30 sek. Om denne timeren går helt ned før siste spiller godkjenner programmering så blir tilfeldige kort valgt fra spillerens kortstokk.

### Arbeidsoppgaver:
* Etter at nest siste robot har lagt ned alle kort, begynner nedtelling 
* Nedtelling varer i 30 sek
* Blir valgt tilfeldige kort om siste robot ikkje blir ferdigprogrammerte i tide

## Godkjenne programmering

### Brukerhistorie: 
Spiller vil kunne godkjenne programmering for å signalisere når programmering er ferdig, dette er også nødvendig for at timer skal virke som planlagt.

### Akseptansekrav: 
Når en spiller er ferdig å programmere sin robot skal han kunne låse sine kort klare for runden. Etter dette er det ikke mulig å endre på kortene. 

### Arbeidsoppgaver: 
* Spiller må kunne annonsere når han er ferdig å programmere 
* Etter programmeringen er godkjent blir kortene låst

## Mulig å vinne spillet

### Brukerhistorie:
Spiller vil kunne spille ferdig spillet og at en vinner er kåret.

### Akseptansekrav:
Når en spiller har besøkt alle flag på brettet i riktig rekkefølge er spillet ferdig og spilleren har vunnet.

### Arbeidsoppgaver:
* Må holde styr på alle flaggene på brettet og flaggene roboten har besøkt
* Når roboten har besøkt alle flaggene i riktig rekkefølge blir dette registrert som at roboten har vunnet.

## Healthlights

### Brukerhistorie:   
Spilleren ønsker å ha kontroll på hvor mange liv han har igjen. 

### Akseptansekrav: 
Hver gang robot får 9 damagetokens, lander på et hull, eller går ut av brettet vil dette bli registrert og vises på grafikken for healtlight.

### Arbeidsoppgaver: 
* Lage grafikk som viser tre lys.
* Ha to tilfeller for lyset (Grønt/rødt) 
* Tilfellet skifter når robot mister et liv.

## Funksjonalitet for spawnpoints/checkpoints:

### Brukerhistorie: 
Robot skal restarte på rett sted på brettet når roboten går på et hull, ut av brettet eller mister et liv.

### Akseptansekrav: 
Roboten sitt spawnpoint skal bli oppdatert hver gang roboten går på eit flagg, eller en repairstation.

### Arbeidsoppgaver: 
* Initiell spawnpoint blir lagd basert på kartet objectfactory får som input
* Map må hente disse når robotene skal lages
* Spawnpoint blir oppdatert hver gang roboten lander på et flag eller en repairstation
* Når roboten mister et liv, lander på hull eller går ut av brettet blir roboten oppdatert til den oppdaterte spawnpoint posisjonen

## LAN-funksjonalitet:

### Brukerhistorie: 
Jeg ønsker å kunne koble meg til eller hoste en server slik at jeg kan spille RoboRally sammen med vennene mine som sitter på andre datamaskiner  

### Akseptansekrav:
Bruker skal enkelt kunne sette opp server
Andre spillere skal enkelt kunne slutte seg til serveren
Spillet er synkronisert over alle maskinene

### Arbeidsoppgaver: 
* Lage serverklasse med nødvendige felt og metoder
* Lage klientklasse med nødvendige felt og metoder
* Utarbeide en protokol for kommunikasjon mellom server og klient
* Avgjøre hvordan arbeidsoppgaver skal fordeles mellom server og klient

## Lyd

### Brukerhistorie: 
Spiller vil ha rå bakgrunnsmusikk og artige lydeffekter, mens han spiller.

### Akseptansekrav: 
Når spiller går inn i begynner programmer, kommer det bakgrunnsmusikk, denne kan mutes om ønskelig. Det kommer også lydeffekter når ting  blir trigget i spillet.    

### Arbeidsoppgaver: 
* Lage klasser for lyd. 
* Finne lydeffektene for forskjellige gamefunctions og soundtrack for meny og ingame. 
* Legge inn knapp for å kunne mute både i meny og in game.
   

## Hovedkrav og MVP
Den opprinnelige planen for hvordan vi ønsker å utvikle og ferdigstille spillet fortsetter som tidligere avtalt.
I forhold til de orginale kravene fra kunde har vi tilfredstilt disse. I tillegg har vi lagt til powerdown for å reparere skade og singelplayer som ekstra funksjonalitet. Da kravene ble endret etter corona-utbruddet valgte vi å holde oss til de orginale MVP-kravene som var satt, der nettverk var en del av MVP. Så la vi heller til en dum AI i singelplayer som ekstra funksjonalitet. 

Dette er hovedkravene vi har fokusert på til assignment 4. 

* Multiplayer, at en skal ha mulighet til å spille over nettverk.
* Spille er spillbart, der en kan vinne og tape.
* At rundeforløpet og faseforløpet går som forventet.  

I tillegg til å fullføre MVP,  har vi hatt fokus på å rette opp i bugs

## Bugs
Vi prøver fortsatt å ikke merge brancher som inneholder bugs. I tillegg til mange tester gjør det at det som lastes opp i master forhåpentligvis ikke inneholder bugs. Vi bruker fortsatt Travis og codacy for å opprettholde kodekvaliteten. Dersom dette hadde vært et reelt produkt, ville vi nå ha startet beta-testing for å forsikre oss om at programmet inneholder minimalt med bugs før en release. 


# Deloppgave 3: Produktleveranse og kodekvalitet
## Bygge, teste og kjøre prosjekt
Finner du i README.md. Legg merke til at du trenger et ekstra argument om du kjører Mac OS. 

## Klassediagram
Klassediagram representerer klasser vi har for i prosjektet vårt. Filen heter Assignment4UML og ligger i Deliverables/UML/assignment4UML.png

## Tester
Testene for manuelle tester heter ManualTests2
Den ligger i Deliverables/ManualTests2.md.

## Vektleggelse i kodebasen
Det er verdt å nevne at noen gruppemedlemmer har større issues som vil ta lenger tid. I tillegg har vi fremdeles roller på teamet og derfor forskjellige ansvarsområder som ikke innebærer å commite til prosjektet i like stor grad. Ettersom dette er siste innlevering er det flere arbeidsoppgaver som ikke er kode relatert som utgjør alt som skal gjøres. Derfor er det ikke slik at ikke alle har like mange commits.
