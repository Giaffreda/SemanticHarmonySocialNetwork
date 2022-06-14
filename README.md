<h1> Architetture distribuite per il Cloud</h1>
<h2> Semantic Harmony Social Network </h2>
Stringa originale= lucagiaffreda-01 <br/>
Stringa md5=28FCBAFED18FDE95F3F5296791D86B05<br/>
Homework= Semantic Harmony Social Network
<h3> Traccia</h3>
Progettare e sviluppare un social network basato sugli interessi dell'utente che sfrutti una rete P2P. Il sistema raccoglie i profili degli utenti e crea automaticamente amicizie secondo una strategia di abbinamento. Gli utenti possono vedere i loro amici nel tempo e vengono automaticamente informati quando un utente entra nel social network e diventa un nuovo potenziale amico. Il sistema definisce una serie di domande, ad esempio, se all'utente piace o meno una serie di foto, una serie di hashtag o, più accurato, come Big Five Personality Test. A questo punto, il sistema può calcolare il punteggio dell'utente in base alle risposte. Questo punteggio è elaborato da una strategia di abbinamento che scopre automaticamente gli amici. Si consideri, ad esempio, un vettore di risposte binarie; un processo di corrispondenza dovrebbe essere la differenza di 0 e 1, o la distanza di Hamming e così via. Il sistema consente agli utenti di vedere le domande del social network, creare un punteggio del profilo in base alla risposta, unirsi alla rete utilizzando un nickname ed eventualmente vedere tutti gli amici degli utenti.

<h3> Soluzione proposta</h3>
Per costruire un immagine del Semantic harmony social network <pre><code>docker build --no-cache -t socialnetwork .</pre></code> dopo "-t" invece di socialnetwork si può usare qualsiasi nome generico.
Dopo aver creato l'immagine sarà possibile creare i container per creare il primo container va inserito il codice <pre><code> docker run -i --name MASTER-PEER -e ADRESS='127.0.0.1' -e ID=0 socialnetwork</pre></code> dando per scontato che sia quello il nome utilizzato
In questo modo viene generato il Master Peer che deve essere generato sempre prima degli altri per il funzionamento del programma. In seguito è possibile avviare gli altri peer.
Per avviare gli altri peer il codice necessario è <pre><code> docker run -i --name PEER-1 -e ADRESS='172.17.0.2' -e ID=1 socialnetwork</pre></code>
Bisogna cambiare sia il valore dell'id che il valore sotto il parametro "--name".
<h2> Funzionalità</h2>
Una volta avviato, l'utente dovrà inserire il proprio nickname  e rispondere alle domande in base ai propri gusti. L'utente fornirà una risposta con una valutazione da 0 a 3, in base alle risposte verrà generata la chiave. L'utente si collega alla dht una volta inserito il nickname, ma effettua l'operazione join solo dopo aver ricevuto la chiave. Dopo aver effettuato l'operazione di join, all'utente sarà mostaro un menu con le operazioni aggiuntive che potrà effettuare.
<h3> esci </h3>
Effettua la leave, il peer lascia la dht,avverte gli amici all'interno della lista
<h3> invia messaggio </h3>
Invia un messaggio, il messaggio può essere inviato sia agli amci che in una chat di gruppo. Prima di inviare il messaggio viene offerta all'utente la possibilità di visualizzare la lista degli amici per decidere a chi invare il messaggio
<h3> chat di gruppo </h3>
Genera un gruppo dove è possibile effettuare la chat di gruppo. Prima di generare viene offerta all'utente la possibilità di visualizzare la lista degli amici per decidere a chi invitare. L'utente seleziona anche il nome del gruppo. Gli utenti inviati vengono inseriti automaticamente nella chat 
<h3> cambia chiave </h3>
L'utente può cambiare la propria chiave, vengono riproposte le domande agli utenti, dopo aver generato la chiave, viene sostituita alla chiave precedente e si notificano gli utenti all'interno della vecchia lista di amici. In seguito si comporta come nella join nella fase della ricerca degli amici
<h3> vedi amici in spam </h3>
L'utente può visualizzare la lista degli amici contrassegnati come spam, tali amici non sanno di essere nella lista degli spam pertanto potrebbero inviare i messaggi. Tali messaggi non saranno trattati come i messaggi normali. Inoltra i messaggi all'interno di un gruppo saranno comunque letti anche se chi li ha inviati è un amico contrassegnato come spam
<h3> aggiungi amici in spam </h3>
Viene aggiunto un amico alla lista degli spam
<h3> rimuovi amici in spam </h3>
Viene rimosso un amico alla lista degli spam
<h3> vedi spam message</h3>
L'utente può visualizzare gli ultimi messaggi spam, una volta visualizzati vengono cancellati completamente
<h3> vedi lista amici </h3>
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
In questo test l'utente genera una nuova chiave, e poi chiama la funzione che per il cambio di chiave. Alla fine dell'operazione si controlla la nuova lista di amici
<h3><pre><code>testCaseChangeExit()</h3></pre></code>
In questo test un peer esce dalla rete. Un altro peer controlla la propria lista di amici, dove il peer uscito non dovrebbe essere presente
<h3><pre><code>testCaseGetQuestion()</h3></pre></code>
In questo test vengono richieste le domande e si controlla se esse siano quelle corrette
<h3><pre><code>testCaseAddSpam()</h3></pre></code>
In questo test si inserisce un amico nella lista degli spam e si controlla se la lista è stato inserito correttamente nella lista
<h3><pre><code>testCaseCreateAuserProfileKey()</h3></pre></code>
Viene testato la creazione della chiave,in primo luogo si generano due stringhe che sono una chiave corretta e una sbagliata, in seguito si crea l'input per la funzione createUserProfileKey. Viene chiamata la funzione prima su un arrayList e poi su un altro in modo da generare le due chiavi. Si effettua il confronto tra le chiavi e le stringhe generate la prima risulta uguale e la seconda invece no. Inseguito si corregge la seconda e si rieffettua il controllo dove questa volta il risultato del confronto da che le due stringhe sono uguali
<h3><pre><code>testCaseRemoveSpam()</h3></pre></code>
In questo test si rimuove un amico nella lista degli spam e si controlla se la lista è corretta, cioè se non è più presente nella stessa.
