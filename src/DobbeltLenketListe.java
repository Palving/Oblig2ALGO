/////////// DobbeltLenketListe ////////////////////////////////////
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DobbeltLenketListe<T> implements Liste<T>
{

    public static void main (String[] args) {
        //               0     1    2    3    4    5    6    7    8    9
       Character[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);

 /* System.out.println(liste);
  System.out.println(liste.fjern(0));
        System.out.println(liste);
        System.out.println(liste.fjern(8));
        System.out.println(liste);
        System.out.println(liste.fjern(4));
        System.out.println(liste);*/
   liste.fjern(new Character('A'));

  System.out.println("Fjern hode "+liste);
        liste.fjern(new Character('J'));
System.out.println("Fjern hale "+liste);
liste.fjern(new Character('D'));
System.out.println("Fjern midt i "+liste);


//liste.nullstill();
//System.out.println(liste);




    }

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

    // hjelpemetode
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
        Objects.requireNonNull(a);
        if (a.length==0){
         throw new NullPointerException(("Tabellen a er null!"));

        }
       // lager tom linket list
        // loop fram til første element som ikke er null
        int teller=0;
        while (a[teller]==null && teller<a.length){

            teller++;

        }
        // hode er da første element som ikke er null
       Node hode=new Node(a[teller]);

       Node hale=hode;
        this.hode=hode;
        this.hale=hode;
        antall=1;
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

            Node nyNode=new Node(a[i]);

            nyNode.forrige=forrigeNode;
            forrigeNode.neste=nyNode;

            forrigeNode=nyNode;
            hale= nyNode;
            this.antall++;

        }
       // this.antall=antall;
        hale.neste=null;
        this.hale=hale;
       // this.hale.verdi=(T)hale.verdi;

    }

    // subliste
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
            Node newNode=finnNode(i);
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

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi,"Verdi kan ikke være null");
        // tilfelle 1 tom liste
        if (hale==null && hode==null){

            Node nyNode=new Node(verdi);
            nyNode.forrige=null;
            nyNode.neste=null;
            hale=nyNode;
            hode=nyNode;
            antall++;
            return true;
        }



        // tilfelle 2
        Node nyNode=new Node(verdi);
        nyNode.forrige=hale;
        nyNode.neste=null;

        hale.neste=nyNode;
        hale=nyNode;
        antall++;

        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {

        if (indeks < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("indeks(" + indeks + ") er negativ!");

        if (indeks > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("indeks(" + indeks + ") > antall(" + antall + ")");

       // kontroll
        Objects.requireNonNull(verdi,"Verdi kan ikke være null");
        indeksKontroll(indeks,false);


        // kode

        // indeks er på hode
        if (indeks==(0)){
            Node nyNode=new Node(verdi);
            Node gamleHode=this.hode;

            nyNode.forrige=null;
            nyNode.neste=gamleHode.neste;
            hode=nyNode;


        }
        // indeks er på hale
        else if (indeks==antall-1){

            Node nyNode=new Node(verdi);
            Node gamleHale=this.hale;

            // funker men ja..
            gamleHale.forrige.neste=nyNode;
            nyNode.neste=null;


            hale=nyNode;


         }

        // indeks er mellom to noder
        else if(indeks>0 && indeks < antall-1){
            System.out.print("mellom");
            Node gammelNode=finnNode(indeks);
            Node forrige= gammelNode.forrige;
            Node neste = gammelNode.neste;

            Node nyNode=new Node(verdi);

            nyNode.forrige=forrige;
            nyNode.neste= neste;

            forrige.neste=nyNode;
            neste.forrige=nyNode;
        }
        endringer++;
    }

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

       if (hale.verdi.equals(verdi)){
           return (antall-1);
       }
       // peker på indeks
        int teller=0;
       while (current.neste!=null){
           if (current.verdi==verdi){
               return teller;
           }
           current=current.neste;
           teller++;
       }

    return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi,"Verdi kan ikke være null");
        if (nyverdi==null){
            return (T)"Ny verdi kan ikke være null";
        }

      indeksKontroll(indeks,false);
        T gammelVerdi=finnNode(indeks).verdi;
        finnNode(indeks).verdi=nyverdi;

      endringer++;

      return gammelVerdi;
    }



    @Override
    public boolean fjern(T verdi)
    {
        if (verdi.equals(null)){
            return false;
        }
        Node current=hode;
        // verdi er hode
        if (current.verdi.equals(verdi)){

            hode=hode.neste;
            hode.forrige=null;
            antall--;
            endringer++;
            return true;
        }

        while (current.neste!=null){
            if (current.verdi.equals(verdi)){
                // Node nodeToDelete=current;
            current.forrige.neste=current.neste;
            current.neste.forrige=current.forrige;
                current.neste=null;
                current.forrige=null;
                antall--;
                endringer++;
                return true;
            }
            current=current.neste;
        }
        // hale
        if (current.verdi.equals(hale.verdi)){

            hale=hale.forrige;
            hale.neste=null;
            antall--;
            endringer++;
        return true;
        }

        return false;

    }


    @Override
    public T fjern(int indeks)
    {
    indeksKontroll(indeks,false);

    // hode
        if (indeks==0){
            T verdi=hode.verdi;

            Node toDelete=hode;
            hode=toDelete.neste;

            toDelete.neste=null;
            hode.forrige=null;

            endringer++;
            antall--;

            return verdi;
        }
        else if (indeks==antall-1){

            T verdi=hale.verdi;
            Node toDelete=hale;

            hale= toDelete.forrige;
            toDelete.forrige=null;
            hale.neste=null;

            endringer++;
            antall--;
            antall--;
            return verdi;


        }



    Node q = finnNode(indeks);
        T verdi=(T)q.verdi;
    q.forrige.neste=q.neste;
    q.neste.forrige=q.forrige;
    antall--;
    endringer++;
    return verdi;


    }


    // Oppgave 7
    @Override
    public void nullstill()
    {
     Node current=hode;

   /*  while (current.neste!=null){
         current.verdi=null;
         current.forrige=null;
         current=current.neste;
         hode=current;
         antall--;

     }
     hale.forrige=null;
     hale.verdi=null;
     antall--;
*/
   System.out.println(antall);
     for (int i=0;i<antall-1;i++){
        // System.out.println("Fjern "+fjern(0));
        fjern(0);
     }
     hale.forrige=null;
     hale.verdi=null;
     antall--;


    }


    //TODO: Fiks output med [ og riktig , og mellomrom, syra
    @Override
    public String toString()
    {

        StringBuilder ut=new StringBuilder();
        Node current=hode;

        if (hode==null){
            return "";
        }
        if (current.neste==null){
            ut.append(hode.verdi);
          return ut.toString();
        }


        while (current.neste!=null){
        //    System.out.println("1");
        ut.append(current.verdi);
            current=current.neste;
        }
       if (!hode.equals(hale)){
           ut.append(current.verdi);
        }

        return ut.toString();
    }

    public String omvendtString()
    {
       StringBuilder ut=new StringBuilder();
        Node current=hale;

        // System.out.println(hode.verdi);
        //   ut+=""+current.verdi+" ";
        if (hale == null && hode==null){
            return "";
        }
        if (current.forrige==null){
            ut.append(hale.verdi);
            return ut.toString();
        }


        while (current.forrige!=null){
           ut.append(current.verdi);
            //System.out.println(current.verdi);
            current=current.forrige;
        }
        if (!hode.equals(hale)){
            ut.append(current.verdi);
        }

        return ut.toString();


    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

} // DobbeltLenketListe
