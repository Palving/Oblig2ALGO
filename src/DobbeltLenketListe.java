/////////// DobbeltLenketListe ////////////////////////////////////
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

// Magnus Palving Christiansen - s326302
// Mats Grøsvik - s331405
// Jon Rafoss - s331379


public class DobbeltLenketListe<T> implements Liste<T>
{

    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node



    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // Oppgave 3//////
    private Node<T> finnNode(int indeks){

        Node current=hode;
        int teller=0;

        if (indeks==antall-1){
            return this.hale;
        }


        while (current.neste!=null){
            if (teller==indeks){

                return current;
            }

            current=current.neste;
            teller++;

        }

        // TODO: Surr med skilleverdi

       /* int skille=antall/2;

        if (indeks < skille) {

            Node current=hode;

            int teller=0;


            while (current.neste!=null){
                if (teller==indeks){
                    System.out.println("Indeks:"+indeks+" Verdi:"+current.verdi);
                    return current;
                }
                current=current.neste;
                teller++;


            }


        }
        else if(indeks >= skille ){

            Node current=hale;
            int teller=antall;

            while (current.forrige!=null){
                if (teller==indeks){

                    System.out.println("Indeks:"+indeks+" Verdi:"+current.verdi);
                    return current;
                }
                current=current.forrige;
                teller--;

            }
        }*/

        return null;
    }

    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a)
    {
        antall=0;
        endringer=0;
        Objects.requireNonNull(a, "Tabellen A er null!");

        if (a.length==0){

        hode=hale=null;
            return;
        }

        // bare ett element

        if (a.length==1 && a[0]!=null){

            hode=new Node(a[0]);
            hale=hode;
            antall++;
            return;

        }
        // loop fram til første element som ikke er null
        int teller=0;
        while (a[teller]==null && teller<a.length-1){

            teller++;

        }

        // hvis bare null lag tom liste
        if (teller==a.length-1){
         Node hode=new Node(null);
         hale=hode;
         return;

        }


        // hode er da første element som ikke er null
      hode=new Node(a[teller]);
      hale=hode;
        antall++;
       // lag peker og sett verdi
        Node forrigeNode= hode;
        forrigeNode.verdi=a[teller];
        this.hale.verdi=a[teller];

        for (int i=teller+1; i<a.length;i++){
            while(a[i]==null && i<a.length-1){
                i++;
            }
            if (i==a.length-1 && a[i]==null){
                break;
            }

            Node<T> nyNode=new Node(a[i]);

            nyNode.forrige=forrigeNode;
            forrigeNode.neste=nyNode;

            forrigeNode=nyNode;
            hale= nyNode;
            antall++;

        }

        hale.neste=null;

       // this.hale.verdi=(T)hale.verdi;

    }

    // OPPGAVE 3 /////
    public Liste<T> subliste(int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");


        // lag tom liste
        DobbeltLenketListe<T> liste=new DobbeltLenketListe<>();
        for (int i=fra;i<til;i++){
            Node<T> newNode=finnNode(i);
            liste.leggInn((T)newNode.verdi);


        }

        return liste;
    }

    @Override
    public int antall()
    {
        return antall;

    }

    @Override
    public boolean tom()
    {
        if (antall==0){
            return true;
        }
        return false;
    }

    ///// OPPGAVE 2 /////////////
    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi);
        // tilfelle 1 tom liste
        if (hale==null && hode==null){

            Node<T> nyNode=new Node(verdi);
            nyNode.forrige=null;
            nyNode.neste=null;
            hale=nyNode;
            hode=nyNode;
            antall++;

            return true;
        }



        // tilfelle 2
        Node<T> nyNode=new Node(verdi);
        nyNode.forrige=hale;
        nyNode.neste=null;

        hale.neste=nyNode;
        hale=nyNode;
        antall++;
        endringer++;
        return true;
    }

    //////// OPPGAVE 5 ////////////

    @Override
    public void leggInn(int indeks, T verdi)
    {

      if (indeks < 0 || indeks>antall) throw new IndexOutOfBoundsException("Ugyldig indeks");
       // kontroll
        Objects.requireNonNull(verdi, "Verdi kan ikke være null");
     // indeksKontroll(indeks,false);

        // tom liste
        if (antall==0){
            Node nyNode=new Node(verdi);
            hode=hale=nyNode;
            antall++;
            endringer++;
        }
        // hode
        else if (antall>0 && indeks==0){
            Node nyNode=new Node(verdi);
            Node gamleHode=hode;
            hode=nyNode;
            hode.neste=gamleHode;
            gamleHode.forrige=hode;
            hode.forrige=null;

            antall++;
            endringer++;
        }
        else if (antall>0 && indeks==antall){
            Node nyNode=new Node(verdi);
            Node gammelHale=hale;
            hale=nyNode;
            hale.forrige=gammelHale;
            gammelHale.neste=hale;
            hale.neste=null;
            antall++;
            endringer++;

        }

        // mellom
        else if (antall>1 && indeks>0 && indeks<antall){
            Node nyNode=new Node(verdi);
            Node gammelNode=finnNode(indeks);

            gammelNode.forrige.neste=nyNode;
            nyNode.forrige=gammelNode.forrige;

            gammelNode.forrige=nyNode;
            nyNode.neste=gammelNode;
            antall++;
            endringer++;

        }

    }

    /////// OPPGAVE 4 ////////
    @Override
    public boolean inneholder(T verdi)
    {

        if (indeksTil(verdi)!=-1){
            return true;
        }
        return false;

    }



    @Override
    public T hent(int indeks)
    {
      indeksKontroll(indeks,false);
      return finnNode(indeks).verdi;
    }

    ///// OPPPGAVE 4 ///////
    @Override
    public int indeksTil(T verdi)
    {
        if (verdi==null){
            return -1;
        }
        if (antall==0){
            return -1;
        }

       Node current=hode;



       // peker på indeks
        int teller=0;
       while (current.neste!=null){
           if (current.verdi==verdi){
               return teller;
           }
           current=current.neste;
           teller++;
       }

        if (hale.verdi.equals(verdi)){
            return antall-1;
        }
    return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi);
        if (nyverdi==null){
            return (T)"Ny verdi kan ikke være null";
        }

      indeksKontroll(indeks,false);
        T gammelVerdi=finnNode(indeks).verdi;
        finnNode(indeks).verdi=nyverdi;


      endringer++;

      return gammelVerdi;
    }



    /////////// OPPGAVE 6 ////////
    @Override
    public boolean fjern(T verdi)
    {
        if (verdi == null) return false;

        Node<T> current = hode;

        while (current != null) {
            if (current.verdi.equals(verdi)) {
                break;
            }

            current = current.neste;
        }

        if (current == null) return false;

        if (current == hode) { // Første node
            hode = hode.neste;

            if (hode != null) {
                hode.forrige = null;
            } else {
                hale = null;
            }
        } else if (current == hale) { // Siste node
            hale = hale.forrige;
            hale.neste = null;
        } else {
            current.forrige.neste = current.neste;
            current.neste.forrige = current.forrige;
        }

        current.verdi = null;
        current.forrige = current.neste = null;

        antall--;
        endringer++;

        return true;

    }


   /////////// OPPGAVE 6 ///////
    @Override
    public T fjern(int indeks)
    {


      indeksKontroll(indeks,false);


    // ett element
       if (hode.equals(hale)){
            T verdi=hale.verdi;
            hode=hale=null;
            antall--;
            endringer++;
            return verdi;
        }

       // hode
        if (indeks==0){
            T verdi=hode.verdi;
           hode=hode.neste;
            hode.forrige=null;
            endringer++;
            antall--;

            return verdi;
        }
        // hale
        else if (indeks==antall-1){

            T verdi=hale.verdi;
            hale=hale.forrige;
            hale.neste=null;
            endringer++;
            antall--;
            return verdi;
        }

    Node<T> q = finnNode(indeks);
        T verdi=(T)q.verdi;
    q.forrige.neste=q.neste;
    q.neste.forrige=q.forrige;
    antall--;
    endringer++;
    return verdi;


    }

    ////////////////////// OPPGAVE 7 /////////////////
    @Override
    public void nullstill()
    {



       Node current=hode;


       while (current.neste!=null){

            hode = hode.neste;
            current.forrige= null;


            current = hode;
            antall--;
            endringer++;

        }

        endringer++;
        antall--;
        hale.neste=null;
        hode.forrige=null;
        hode=hale=null;



    }


    // oppgave 2

    @Override
    public String toString()
    {
        StringBuilder ut=new StringBuilder();
        Node current=hode;
        ut.append("[");
        if (hode ==null){
            return "[]";
        }
        if (current.neste==null){
            ut.append(hode.verdi);
            ut.append("]");
            return ut.toString();
        }
        while (current.neste!=null){
            ut.append(current.verdi);
            ut.append(", ");
            current=current.neste;
        }
        if (!hode.equals(hale)){
            ut.append(current.verdi);
        }
        ut.append("]");
        return ut.toString();
    }

    // oppgave 2
    public String omvendtString()
    {
        StringBuilder ut=new StringBuilder();
        Node current=hale;
        ut.append("[");

        // tom
        if (hale ==null){
            return "[]";
        }
        // bare en verdi
        if (current.forrige==null){
            ut.append(hale.verdi);
            ut.append("]");
            return ut.toString();
        }
        while (current.forrige!=null){
            if (current.verdi!=null && current!=hode ){
                ut.append(current.verdi);
                ut.append(", ");
            }

            current=current.forrige;
        }
        if (hode!=null){
            ut.append(hode.verdi);
        }
     //   if (!hale.equals(hode)){
         //   if (current.verdi!=null) ut.append(current.verdi);

       // }
        ut.append("]");
        return ut.toString();
    }


    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        if (liste.antall() != 1){

        for (int j = liste.antall(); j > 0; j--){

            Iterator<T> iterator = liste.iterator();
            int midlertidigMinste = 0;
            T minsteVerdi = iterator.next();


            for (int i = 1; i < j; i++){
                T verdi = iterator.next();
                if (c.compare(verdi,minsteVerdi) < 0){
                    midlertidigMinste = i;
                    minsteVerdi = verdi;
                }
            }
            liste.leggInn(liste.fjern(midlertidigMinste));
        }
    }
    }

    @Override
    public Iterator<T> iterator()
    {
     return new DobbeltLenketListeIterator();

    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks,false);
       return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            denne=finnNode(indeks);
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
            if (iteratorendringer!=endringer){
                throw new ConcurrentModificationException();
            }
            if (!hasNext()){
                throw new NoSuchElementException("Ikke fler igjen i listen");
            }
            fjernOK=true;
            T verdi=(T)this.denne.verdi;
            denne=denne.neste;
            return verdi;
        }

        @Override
        public void remove() {
            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");
            if (endringer != iteratorendringer) throw new ConcurrentModificationException();

            fjernOK=false;

            Node<T> q=hode;

            if (hode.neste== denne){
                hode=hode.neste;
                {
                    if (denne == null) hale =null;
                }
            }
            else
            {
                Node<T> r=hode;

                while (r.neste.neste !=denne){
                    r=r.neste;
                }
                q = r.neste;
                r.neste=denne;
                if (denne==null) hale=r;

            }

            q.verdi=null;
            q.neste=null;

            antall--;
            endringer++;
            iteratorendringer++;

        }
    } // DobbeltLenketListeIterator

} // DobbeltLenketListe
