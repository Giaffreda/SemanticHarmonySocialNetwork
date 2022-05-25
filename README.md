<h1> Architetture distribuite per il Cloud</h1>
<h2> Semantic Harmony Social Network </h2>
<h3> Soluzione proposta</h3>
Per costruire un immagine del Semantic harmony social network <pre><code>docker build --no-cache -t socialNetwork .</pre></code> dopo "-t" invece di socialNetwork si può usare qualsiasi nome generico.
Dopo aver creato l'immagine sarà possibile creare i container per creare il primo container va inserito il codice <pre><code> docker run -i -name MASTER-PEER -e ADRESS='127.0.0.1' -e ID=0 socialNetwork</pre></code> dando per scontato che sia quello il nome utilizzato
In questo modo viene generato il Master Peer che deve essere generato sempre prima degli altri per il funzionamento del programma. In seguito è possibile avviare gli altri peer.
Per avviare gli altri peer il codice necessario è <pre><code> docker run -i -name PEER-1 -e ADRESS='172.17.0.2' -e ID=0 socialNetwork</pre></code>
