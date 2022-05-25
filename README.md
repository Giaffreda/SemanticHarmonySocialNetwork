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
