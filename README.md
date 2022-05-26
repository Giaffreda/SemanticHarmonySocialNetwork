<h1> Architetture distribuite per il Cloud</h1>
<h2> Semantic Harmony Social Network </h2>
<h3> Soluzione proposta</h3>
Per costruire un immagine del Semantic harmony social network <pre><code>docker build --no-cache -t socialNetwork .</pre></code> dopo "-t" invece di socialNetwork si può usare qualsiasi nome generico.
Dopo aver creato l'immagine sarà possibile creare i container per creare il primo container va inserito il codice <pre><code> docker run -i -name MASTER-PEER -e ADRESS='127.0.0.1' -e ID=0 socialNetwork</pre></code> dando per scontato che sia quello il nome utilizzato
In questo modo viene generato il Master Peer che deve essere generato sempre prima degli altri per il funzionamento del programma. In seguito è possibile avviare gli altri peer.
Per avviare gli altri peer il codice necessario è <pre><code> docker run -i -name PEER-1 -e ADRESS='172.17.0.2' -e ID=1 socialNetwork</pre></code>
<h2> Funzionalità</h2>
Una volta avviato, l'utente dovrà inserire il proprio nickname  e rispondere alle domande in base ai propri gusti. L'utente fornirà una risposta con una valutazione da 0 a 3, in base alle risposte verrà generata la chiave. L'utente si collega alla dht una volta inserito il nickname, ma effettua l'operazione join solo dopo aver ricevuto la chiave. Dopo aver effettuato l'operazione di join, all'utente sarà mostaro un menu con le operazioni aggiuntive che potrà effettuare.
<h3> exit </h3>
Effettua la leave, il peer lascia la dht,avverte gli amici all'interno della lista
<h3> message </h3>
Invia un messaggio, il messaggio può essere inviato sia agli amci che in una chat di gruppo. Prima di inviare il messaggio viene offerta all'utente la possibilità di visualizzare la lista degli amici per decidere a chi invare il messaggio
<h3> group chat </h3>
Genera un gruppo dove è possibile effettuare la chat di gruppo. Prima di generare viene offerta all'utente la possibilità di visualizzare la lista degli amici per decidere a chi invitare. L'utente seleziona anche il nome del gruppo. Gli utenti inviati vengono inseriti automaticamente nella chat 
<h3> change key </h3>
L'utente può cambiare la propria chiave, vengono riproposte le domande agli utenti, dopo aver generato la chiave, viene sostituita alla chiave precedente e si notificano gli utenti all'interno della vecchia lista di amici. In seguito si comporta come nella join nella fase della ricerca degli amici
<h3> see spam friends </h3>
L'utente può visualizzare la lista degli amici contrassegnati come spam, tali amici non sanno di essere nella lista degli spam pertanto potrebbero inviare i messaggi. Tali messaggi non saranno trattati come i messaggi normali. Inoltra i messaggi all'interno di un gruppo saranno comunque letti anche se chi li ha inviati è un amico contrassegnato come spam
<h3> add spam friend </h3>
Viene aggiunto un amico alla lista degli spam
<h3> remove spam friend </h3>
Viene rimosso un amico alla lista degli spam
<h3> see spam message </h3>
L'utente può visualizzare gli ultimi messaggi spam, una volta visualizzati vengono cancellati completamente
<h3> see list freinds </h3>
L'utente può visualizzare la lista degli amici, e anche i vari gruppi
<h3>Classe user </h3>
Tra il main e la dht si frappone la classe User che gestisce i vari dati in input e chiama le funzione della dht.
Tra le varie operazioni ricava le domande dalla dht con il metodo <b> getUserProfileQuestions()</b> della dht e le pone all'utente. Dopo aver risposto utilizza il metodo <b> createAuserProfileKey</b>  per generare la chiave che viene utilizzata per la join. Queste ioerazioni sono automatiche quando l'utente entra nel sistema, defferentemente da quelle del menu che vengono chiamate secondo richieste di quest'ultimo. La classe user richiede l'input e fornisce i dati per le opreazione della dht
<h2>test di unità</h2>
I test sono effettuati con junit, la classe che effettua i test è denominata "app test", che si trova all'interno del package "Social.LucaGiaffreda", all'interno della cartella "src/test/java"
I test effettuati sono 9 essi vengono effettuati in successione uno dopo l'altro. L'orine è deciso dal tag inserito sopra al metodo. I test sono:
<h3><pre><code>testCaseGeneratePeers()</h3></pre></code>
questo test genera 4 peer, con i nomi gli id e gli indirizzi. questo test viene effettuato prima di tutti gli altri
<h3><pre><code>testCaseConnect()</h3></pre></code>
In qeusto test vengono testate le operazioni "join", ogni peer genera la propria chiave, ed effettua la join. In automatico vengono create le amicizia, in seguito viene testato se le amicizie generate sono corrette 
<h3><pre><code>testCaseSend()</h3></pre></code>
in questo caso vengono inviati i messaggi, vengono inviati 3 messaggi. mentre i primi due arravano a destinazione il terzo nnon viene inviato, siccome viene richiesto un amico non presente nella lista
<h3><pre><code>testCaseGroupChat()</h3></pre></code>
In questo test viene generata una chat di gruppo, che però ha un amico insesitente quindi non viene generata. Viene eliminato l'amico inesistente e viene generata la chat di gruppo infine viene inviato un messaggio alla chat.
<h3><pre><code>testCaseChangekey()</h3></pre></code>
<h3><pre><code>testCaseChangeExit()</h3></pre></code>
<h3><pre><code>testCaseGetQuestion()</h3></pre></code>
<h3><pre><code>testCaseAddSpam()</h3></pre></code>
<h3><pre><code>testCaseSeeSpamMessages()</h3></pre></code>
<h3><pre><code>testCaseRemoveSpam()</h3></pre></code>
