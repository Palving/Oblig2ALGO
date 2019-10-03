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

        System.out.println(liste.subliste(3,8));
        System.out.println(liste.subliste(5,5));
        System.out.println(liste.subliste(8,liste.antall()));

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

        if (a.length==0){
           throw new NullPointerException(("kAN IKKE VÆRE TOM"));


        }
       // lager tom linket list
        // loop fram til første element som ikke er null
        int teller=0;
        while (a[teller]==null && teller<a.length){

            teller++;

        }
        // hode er da første element som ikke er null
       Node hode=new Node(a[teller]);
        int antall=1;
       Node hale=hode;
        this.hode=hode;
        this.hale=hode;
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
            antall++;

        }
        this.antall=antall;
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
        Objects.requireNonNull(verdi);
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
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean inneholder(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
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
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        Objects.requireNonNull(nyverdi);
        if (nyverdi==null){
            return (T)"Ny verdi kan ikke være null";
        }

      indeksKontroll(indeks,false);
      Node gammelNode=finnNode(indeks);
      Node nyNode=new Node(nyverdi);
      nyNode.forrige=gammelNode.forrige;
      nyNode.neste=gammelNode.neste;

      endringer++;

      return (T)gammelNode.verdi;
    }

    @Override
    public boolean fjern(T verdi)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill()
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }


    //TODO: Fiks output med [ og riktig , og mellomrom, syra
    @Override
    public String toString()
    {
        //String ut=" ";
        StringBuilder ut=new StringBuilder();
        Node current=hode;
       // System.out.println(hode.verdi);
        ut.append("[");
     //   ut+=""+current.verdi+" ";
        if (hode==null){
            return "";
        }
        if (current.neste==null){
            ut.append(hode.verdi+"]");
          return ut.toString();
        }


        while (current.neste!=null){
        ut.append(current.verdi+" ");
            //System.out.println(current.verdi);
            current=current.neste;
        }
        if (!hode.equals(hale)){
            ut.append(","+current.verdi);
        }

        return ut.toString()+"]";
    }

    public String omvendtString()
    {
       StringBuilder ut=new StringBuilder();
        Node current=hale;
        ut.append("[");
        // System.out.println(hode.verdi);
        //   ut+=""+current.verdi+" ";
        if (hale == null){
            return "";
        }
        if (current.forrige==null){
            ut.append(hale.verdi+"]");
            return ut.toString();
        }


        while (current.forrige!=null){
            ut.append(current.verdi+"");
            //System.out.println(current.verdi);
            current=current.forrige;
        }
        if (!hode.equals(hale)){
            ut.append(", "+current.verdi);
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
