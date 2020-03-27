# Obligatorisk oppgave 3

## Deloppgave 1: Team og prosjekt

### Referat fra møter
Møtereferater er lagt ved i Wiki'en

### Roller i teamet
Vi beholder de samme rollene som i obligatoriske oppgave 2. Nå som vi har kommet lenger inn i prosjektet har alle lært hva som skal til i de forskjellige rollene og derfor har vi mindre fokus på det. Unntak til dette er Teamleader og Sekretær som har sine egne ansvarsområder.

### Nevnverdig prosjektmetodikk
En konsekvens av korona utbruddet har vært at vi ikke har muligheten å møte for å jobbe sammen. Korona utbruddet har fått oss til å hoppe over til en digital løsning der vi snakker over programmet Discord når vi jobber med prosjektet. Dette har vært en meget bra løsning, fordi Discord har alle verktøyene som vi trenger. Dette innebærer chat, voicechat og streaming muligheter av kode. Vi starter møtet med å ta opp issues og hva som skal jobbes med i løpet av møtet. Deretter er det fri bane for å spørre, diskutere og jobbe i par igjennom møtet. Vi deler møtet opp i flere voicechats, den ene er for arbeidsro og de andre er for diskusjoner.

### Retroperspektiv frem til nå
Frem til nå har vi fått til en bra flyt når det kommer til prosjektmetodikken vår. Et forbedringspunkt fra obligatoriske oppgave 2 var at vi skulle ha en jevn jobbflyt mellom møtene også. Med å følge opp på det punktet har vi klart å få mer gjort og møtene er også blitt mer effektive. Dette er fordi de blir brukt til mindre indviduell jobbing. som et resultat av dette er det da blitt mer spørring og gruppejobbing på møtene. I starten av obligatorisk oppgave tre så lagde vi en milestone, assignment 3 på github hvor vi la til alle issues som vi ønsket å få gjort til denne innleveringen, samt at vi assignet alle issuene til medlemmer på teamet. Dette har gitt oss et bra overblikk over hvem som skal fokusere på hva. Dessuten jevner vi ut forskjeller på commits i gruppen. 

### Forbedringspunkter
* Bli bedre på å dokumentere kode underveis.
* Forbedre brukerhistorier og arbeidskrav FØR vi begynner å jobbe med issue.

### Prioritering av oppgaver
Dette er linken til et bildet av projectboard:
https://scontent.fosl3-2.fna.fbcdn.net/v/t1.15752-9/90638515_215689749744447_7241243959735877632_n.png?_nc_cat=109&_nc_sid=b96e70&_nc_ohc=5xjAn1bEp4UAX93q8aq&_nc_ht=scontent.fosl3-2.fna&oh=a8af18718adb0afa5f2afa7fd90574e6&oe=5EA1E407

Implementert kort og interface, 
For å utvikle flere spillfunksjoner har vi prioritert kort og kortenes funksjoner på spillbrettet til denne innleveringen. For at spillerne etterhvert skal kunne bruke kortene, er det nødvendig at funksjonaliteten er utviklet. Derfor har vi til denne innleveringen fokusert på å lage ferdig objektfunksjoner, før det senere blir tilgjengelig for spilleren. Dette er nødvendig for at vi senere skal kunne bruke funksjonaliteten på spillere. 
 

### Gruppedynamikk og kommunikasjon
Gruppemedlemmer jobber individuelt med sine arbeidsoppgaver. Om gruppemedlemmer har spørsmål går involverte inn i en annen voicechat og diskuterer der.

Kommunikasjon på møter er meget bra. Vi sørger for at de som trenger arbeidsro har muligheten til å være i den rolige voicechaten og blir hentet hvis det er noe som må diskuteres. I tillegg jobber vi individuelt i fritiden og spør gjerne andre gruppemedlemmer i mellom møter om det trengs. 
Vi har to til tre møter i uken.


# Deloppgave 2: Krav
### Hvilke krav dere har prioritert

Til denne innleveringen har vi prioritert issues som er knyttet til Milestone - Assignment 3
Link til Milestone - Assignment 3: https://github.com/inf112-v20/factoryRobots/milestone/1?closed=1

Vi har prioritert å få inn mer grafikk til spillet. Dette inkluderer kort og spillmeny, samt funksjonalitet tilhørende. I tillegg har vi prioritert å ferdigstille objektfunksjonalitet slik at vi til neste obligatoriske oppgave kan ha et komplett spill. 
Vi arbeider også med å få ferdig rundesystemet til spillet, slik at vi kan utføre alle objektfunksjonene i bestemt rekkefølge.


### Brukerhistorier, arbeidsoppgaver og akseptansekrav
Vi har skrevet brukerhistorier, arbeidsoppgaver og akseptansekrav på vært enkelt issues knyttet til Assignment 3 i Milestone.
Under beskrives alle issues med brukerhistorie, akseptansekrav og arbeidsoppgaver for Assignment 3.

#### Robot må kunne dø 
**Brukerhistorie**:
Jeg ønsker at roboter skal kunne dø, slik at jeg eller andre spillere kan eliminere motspillere.

**Akseptansekrav**:

Robot dør og går ut av spillet etter å ha mistet 3 liv ("respawner" ikke)
Hvis robot mottar 9 damage tokens mister den et liv
Når roboten mister et liv, "respawner" den på forrige checkpoint
Etterhvert som roboten går på checkpoints, oppdateres posisjon for "respawn"
Robot "spawner" på riktig checkpoint

**Arbeidsoppgaver**:

 * Holde styr på hvor mange damage tokens roboten har
 * Holde styr på hvor mange liv roboten har igjen
 * Respawne robot på riktig sted når robot har 9 damage tokens
 * Fjerne robot fra spill når den er tom for liv
 * Sette checkpoint som "spawn" når robot besøker det

#### Implementere runder
**Brukerhistorie:**
Spiller ønsker å spille rundebasert Robo Rally for å lage strategi og planlegge bevegelser.

**Akseptansekrav:**
Rundene må følge runde strukturen til Robo Rally.

**Arbeidsoppgaver:**

* Hver fase skal gå i rett rekkefølge
* Initiering av hver fase og runde skal ferdigstilles riktig måte
* Implementere powerdown
* Få powerdown til å tre i kraft på riktig tidspunkt i runden
* At utdeling og programmering av kort blir gjort riktig

#### Lage spillmeny
**Brukerhistorie:**
Spiller ønsker å se en meny for å velge bane og flerspiller valg

**Akseptansekrav:**
Programkort må være synlig for spilleren
Gitt en knapp vil brukeren bli sendt til en ny menypage, avslutte eller starte spillet

**Arbeidsoppgaver:**
* Representere spillet
* Gi alternativer til lyd
* Gi alternativer til antall spillere
* Gi alternativer til baner
* Starte spillet fra spillmeny
* Avslutte spillet

#### Reparere skade
**Brukerhistorie:**
Som spiller ønsker jeg å reparere roboten min om jeg har mange damage-tokens.


**Akseptansekrav:**
Gitt at robot er i powerdown eller på repairstation, vil roboten fjerne damage-tokens.

**Arbeidsoppgaver:**
* Om robot har vært i powerdown forsvinner alle damage tokens
* Fjerne damage-tokens hvis du er på repairstation
* Etter en runde legger alle roboter fra seg ett damage kort

#### Ta skade
**Brukerhistorie:**
Som spiller ønsker jeg at robot skal ta skade av laser eller hull.

**Akseptansekrav:**
Gitt at robot blir truffet av laser eller faller i et hull, vil robot få x antall damage-tokens

**Arbeidsoppgaver:**
* Få forventa mengde damage tokens fra laser
* Miste liv ved 10 damage tokens
* Miste liv når robot detter ut av brettet eller ned i ett hull

#### Roboter stopper lasere
**Brukerhistorie:**
Som spiller ønsker jeg at laseren stopper på den første roboten den treffer, slik at jeg kan gjemme meg bak andre roboter, for å unngå å ta skade fra laser.

**Akseptansekrav:**
Laserstråle stopper på første robot den treffer.
Bare denne første roboten tar skade

**Arbeidsoppgaver:**
* Bestemme stien laseren skal følge og sørge for at denne stopper når den skal.
* Finne ut om det er en robot i enden av laserstrålen.
* Dersom det er en robot i enden, gi denne roboten skade.
* Tegne laserstrålens sti grafisk.

#### Vegger stopper roboter og lasere
**Brukerhistorie:**
Som spiller ønsker jeg at vegger stoppe roboter og lasere fra å passere veggen

**Akseptansekrav:**
Gitt at vegg ligger i posisjon foran en robot eller laser, skal robot og laser bli stoppet fra å gå videre.
Hvis det er en laser, skal den bli slettet.

**Arbeidsoppgaver:**
* Roboter går ikkje gjennom vegger, sjølv om kortet tilsier at dei skal bevega seg i den retningen
* Om lasere treffer vegger går dei ikkje gjennom.

#### Fyre av laser
**Brukerhistorie:**
Som spiller ønsker jeg at laser skal fyres av i en gitt retning etter bestemte faser.

**Akseptansekrav:**
Gitt det er riktig fase, vil laserkanon skyte av laser i en bestemt retning, den vil da gå til den treffer en vegg eller robot.

**Arbeidsoppgaver**
* Laser blir stoppet av robot
* Laser blir stoppet av vegg
* Går i forventa retning

#### Dytte andre roboter
**Brukerhistorie:**
Jeg vil kunne dytte andre roboter slik at jeg kan sabotere andre spillere på brettet.

**Akseptansekrav:**
Om en robot er i veien for en annen robots trekk, vil denne roboten bli dyttet fremfor roboten som beveger seg
Om det er enda en robot foran denne, blir denne også dyttet, osv.
Roboten blir ikke dyttet om den er blokkert av en vegg, eller kanten av kartet

**Arbeidsoppgaver:**

* Sjekke om det er en robot i feltet hvor roboten er på vei
* Sjekke hva som finnes bak denne roboten
* Og eventuelt bak andre roboter bak den igjen
* Gjennomføre trekket dersom roboten(e) som dyttes ikke er blokkert av kanten av brettet eller en vegg

#### Lage klasse for conveyerbelt
**Brukerhistorie:**
Som spiller ønsker jeg at transportbåndene flytter roboten min som forventet, slik at jeg kan bruke disse til å flytte meg effektivt rundt på brettet.

**Akseptansekrav:**
Båndet flytter robot i forventet retning
Robot roteres riktig retning når båndet svinger
Bånd med dobbel hastighet flytter roboter dobbelt så fort.

**Arbeidsoppgaver:**

* Finne ut hastigheten på båndet
* Finne ut hvilken retning båndet svinger i forhold til retningen roboten kommer inn på båndet fra
* Rotere robot i riktig retning når det er passende
* Flytte roboten riktig antal skritt frem
* Om robot blokkeres av en annen robot eller vegg som ikke står på båndet skal ikke båndet flytte roboten

#### Programmere robot
**Brukerhistorie:**
Som spiller ønsker jeg at roboten skal reagere etter kortene sin funksjonalitet

**Akseptansekrav:**
Gitt at robot tar opp et kort, skal robot utføre den bestemte handlingen til kortet.

**Arbeidsoppgaver:**
* Roboten må bevege seg rett etter kortene som blir lagt ned
* Må ta opp ett og ett kort i riktig rekkefølge

#### Besøke flagg
**Brukerhistorie:**
Som spiller ønsker jeg at når jeg besøker riktig flagg i stigende rekkefølge vil jeg få nytt checkpoint og muligheten til å besøke nytt flagg.

**Akseptansekrav:**
Gitt at spilleren er på riktig flagg, vil roboten sitt checkpoint bli oppdatert.

**Arbeidsoppgaver:**
* Når robot kommer på rett flagg (riktig rekkefølge) blir dette registrert

#### Lage grafikk for programkort
**Brukerhistorie:**
Spiller ønsker å se programkort for å spille Robo Rally.

**Akseptansekrav:**
Programkort må være synlig for spilleren.

**Arbeidsoppgaver:**
* Implementere grafikk for programkort
* Implementere ramme rundt spillebrettet
* OnClick funksjonalitet

#### Robot respawner på checkpoint
**Brukerhistorie:**
Som spiller ønsker jeg at når roboten til spilleren dør, bli plassert tilbake på siste checkpoint.

**Akseptansekrav:**
Gitt at robot dør skal robot bli plassert på sitt siste checkpoint. Dette er enten startposisjon, repairstation eller forrige besøkte flagg.

**Arbeidsoppgaver:**
* Oppdaterer hvilket checkpoint roboten har
* Spawner på riktig checkpoint
* Spawner om ikke om ikke mer liv

#### Kortstokk funksjonalitet
**Brukerhistorie:**
Som spiller ønsker jeg å velge kort for å bestemme robotens videre trekk

**Akseptansekrav:**
Gitt et bestemt kort vil roboten gjøre en handling som samsvarer med hva funksjonaliteten til kortet sier.

**Arbeidsoppgaver:**
* Kort gjør hva det forventes
* Ha rett antall kort av forskjellige typer 
* Riktig antall prioritetspoeng
* Skade vil gjøre at spilleren blir gitt færre kort

#### Få spilleranimasjon til å rotere
**Brukerhistorie:**
Som spiller ønsker jeg å se at spilleranimasjonen roterer utifra det logiske objektets orientering.

**Akseptansekrav:**
Gitt at programkort, tannhjul eller transportbånd roterer robot, skal spilleranimasjonen rotere i samme retning

**Arbeidsoppgaver:**
* Høyre/venstre roterer, opp fremover
* Spilleranimasjonen korresponderer til det logiske objektets orientering
* Spilleranimasjonen skal rotere i retningen gitt av programkort
* Spilleranimasjonen skal rotere i retningen gitt av transportbånd
* Spilleranimasjonen skal rotere i retningen gitt av tannhjul

#### Lage klasse for vegg
**Brukerhistorie:**
Som spiller ønsker jeg vegger blokkerer lasere og hvor jeg kan gå, slik at jeg kan bruke dette til min fordel. 

**Akseptansekrav:**
Vegger blokkerer roboter og lasere. Veggene med lasere på seg kan også fyre av disse.

**Arbeidsoppgaver:**
* Når en robot ønsker å gjøre en bevegelse, kalkulere om dette er gyldig mht. retningen veggen blokkerer.
* Representere om veggen har laser, og evt. om denne er dobbel. 
* Lage metode for å trigge laser.

#### Lage klasse for laser
**Brukerhistorie:**
Som spiller ønsker jeg at lasere oppfører seg som forventet, slik at jeg kan forutsi hvilken strategi jeg skal bruke.

**Akseptansekrav:**
Lasere kan fyres av, strålen går i forventet retning og stopper ved første robot eller vegg, evt. kanten av banen.

**Arbeidsoppgaver:**
* Lage klasse for å representere laser
* Lage metode for å finne stien laseren skal gå
* Sørge for at laseren gir skade til eventuelle roboter den treffer.
* Vise laserstrålen grafisk når den fyres av.

#### Lage klasse for tannhjul
**Brukerhistorie:**
Som spiller ønsker jeg at tannhjulet oppfører seg som forventet, slik at jeg kan bruke det til å forflytte meg på kartet.

**Akseptansekrav:**
Gitt at robot står på et tannhjul, skal roboten rotere i retningen bestemt på tannhjulet.

**Arbeidsoppgaver:**
* Lage klasse for å representere tannhjul
* Sørge for at spilleren roteres i passende retning når spilleren står på tannhjulet

### Hovedkrav og MVP
Vi følger fortsatt opprinnelig plan og rekkefølge for hvordan vi skal utvikle spillet. Etter å ha fått på plass minstekravene for at spillet skal fungere har vi nå satt oss nye krav og MVP for videre utvikling.
Krav som vi ser på som MVP funksjonene ved denne innleveringen er de vi mener er viktigst for å få gjennomføre en fase. Derfor har vi lagt vekt på kort og kortfunksjonalitet, samt rundesystemet. Gi Robotens funksjonalitet på objekter på brettet.

* Kortfunksjonalitet, fordi bevegelsen til roboten i en fase avhenger av kortene og dens funksjonalitet

* Rundesystemet, alle spillfunksjoner blir kalt inn i rundesystemet for å utføre fasen i riktig rekkefølge

* Robotens funksjonalitet, for at kort og brettobjekter fungerer på roboten i en fase

Det er verdt å nevne at i utviklingen har vi hatt høy fokus på å få frem roboten sin back end funksjonalitet der det i senere tid vil være mulig å vise fysisk i spillet på et senere tidspunkt. Vi har valgt å ha høy fokus på kort funksjonalitet, fordi det er viktig å få på plass dette for å også kunne bruke rundesystemet effektivt. Dette har vi klart bra på en visuell måte og funksjonell måte der det vil bli tatt i bruk når rundesystemet er ferdig utvikliet.

### Bugs
Vi prøver å ikke merge brancher som inneholder bugs, samtidig hjelper det at vi har mange tester til spillfunksjonene. Vi har likevel noen små bugs når det gjelder resizing av skjerm og skjermer med for lav oppløsning.

### Ubrukte funksjoner og krav
Frem til nå har vi hatt høy fokus på back end utvikling med unntak av menu og kort. En konsekvens av dette er at ikke alle funksjoner i klasser er tatt i bruk ennå. Det er også visse superklasser og interface som krever at visse funksjoner er implementert selv om de ikke brukes, så disse kan ikke utelates uten kompileringsfeil. Det er også verdt å nevne at dette ikke er et ferdig produkt når det slippes, så det vil være naturlig at det er ubrukt kode visse steder.


# Deloppgave 3: Produktleveranse og kodekvalitet

### Bygge, teste og kjøre prosjekt
Finner du i README.md. Legg merke til at du trenger et ekstra argument om du kjører Mac OS. 

### Klassediagram
Klassediagram representerer utvalgte metoder vi anser som viktigst for å presentere spillet. Det finner du i Deliverables/UML.

### Tester
Manuelle tester ligger i Deliverables/ManualTests2.md

### Vektleggelse i kodebasen
Det er verdt å nevne at noen gruppemedlemmer har større issues som vil ta lenger tid. I tillegg har vi fremdeles roller på teamet og derfor forskjellige ansvarsområder som ikke innebærer å commite til prosjektet i like stor grad. Som vi har nevnt tidligere så har en i gruppen hatt problemer med at commits som ikke ble registrert på github og kan derfor være ukorrekt antall commits som er representert. Det er verdt å nevne at vi i starten av prosjektet assignet likt antall issues til hvert gruppemedlem og på grunn av størrelse på issues vil folk bli ferdig på forksjellige tidspunkt. Derfor er det blitt omfordeling av issues etter behov.


