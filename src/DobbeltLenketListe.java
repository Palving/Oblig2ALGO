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
      // DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);

DobbeltLenketListe l1=new DobbeltLenketListe(new Integer[]{1});
System.out.println(l1);
        DobbeltLenketListe l2=new DobbeltLenketListe(new Integer[]{null});
        System.out.println(l2);
  /*liste.fjern(new Character('A'));

    System.out.println("Fjern hode "+liste);
   liste.fjern(new Character('J'));
System.out.println("Fjern hale "+liste);
liste.fjern(new Character('D'));
System.out.println("Fjern midt i "+liste);

        System.out.println(liste);
        liste.fjern(0);
        System.out.println("Hode fjernet med inseks"+liste);
        liste.fjern(liste.antall-1);
        System.out.println("HALE fjernet med inseks"+liste);
        liste.fjern(3);
        System.out.println("MIDT I fjernet med inseks"+liste);*/

   //liste.nullstill();
  // System.out.println("Liste etter nullstill"+liste);


        String[] navn = {"Lars","Anders","Bodil","Kari","Per","Berit"};
        Liste<String> liste1 = new DobbeltLenketListe<>(navn);
        DobbeltLenketListe.sorter(liste1, Comparator.naturalOrder());
        System.out.println(liste1);
       // DobbeltLenketListe.sorter(liste2, Comparator.naturalOrder());
       // DobbeltLenketListe.sorter(liste3, Comparator.naturalOrder());
        // Utskrift:
        // Lars Anders Bodil Kari Per Berit
        // Lars Anders Bodil Kari Per Berit


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
        Objects.requireNonNull(verdi);
        indeksKontroll(indeks,false);


        // kode

        // indeks er på hode
        if (indeks==(0)){
            Node<T> nyNode=new Node(verdi);
            Node gamleHode=this.hode;

            nyNode.forrige=null;
            nyNode.neste=gamleHode.neste;
            hode=nyNode;

        }
        // indeks er på hale
        else if (indeks==antall-1){

            Node<T> nyNode=new Node(verdi);
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

            Node<T> nyNode=new Node(verdi);

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
           return antall-1;
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



    @Override
    public boolean fjern(T verdi)
    {

        if (verdi.equals(null)){
            return false;
        }
     Node current=hode;

        // verdi er hode
        if (current.verdi.equals(verdi)){
            // remove hode

                hode=hode.neste;
                hode.forrige=null;

            antall--;
            endringer++;
            return true;
        }

        while (current.neste!=null){
            if (current.verdi.equals(verdi)){

             current.forrige.neste=current.neste;
             current.neste.forrige=current.forrige;

                antall--;
                endringer++;

                return true;
            }
            current=current.neste;
        }

     if (hale.verdi.equals(verdi)){
        hale=hale.forrige;
        hale.neste=null;
        antall--;
        endringer++;
        return true;
    }
        return false;
    }

    // TODO: Skjønner ikke, skal verdien fjernes eller hele noden? Gjerne slett hele innholdet i metoden under her
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

    @Override
    public void nullstill()
    {


        int a=antall;
        for (int i=0;i<a;i++){
            fjern(0);
        }


    }


    //TODO: Fiks output med [ og riktig , og mellomrom, syra
    @Override
    public String toString()
    {

        StringBuilder ut=new StringBuilder();
        Node<T> current=hode;

        if (hode==null){
            return "[]";
        }
        if (current.neste==null){
            ut.append(hode.verdi);
          return ut.toString();
        }


        while (current.neste!=null){

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
        if (hale == null){
            return "[]";
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
        if (liste.antall() != 1){

        for (int j = liste.antall(); j > 0; j--){
            Iterator<T> iterator = liste.iterator();    //Oppretter ny iterator hver iterasjon
            int midlertidigMinste = 0;                  //Setter midlertidigMinste til 0.
            T minsteVerdi = iterator.next();               //Setter midlertidig minsteverdi til første verdi i lenken.
            for (int i = 1; i < j; i++){                //Itterer gjennom lenken frem til n
                T verdi = iterator.next();         //Setter verdi lik neste verdi i lenken
                if (c.compare(verdi,minsteVerdi) < 0){     //Sammenligner minsteverdi med verdi for å se om verdi er mindre
                    midlertidigMinste = i;
                    minsteVerdi = verdi;                   //Dersom verdi er mindre blir minsteverdi oppdatert
                }
            }
            liste.leggInn(liste.fjern(midlertidigMinste));  //Fjerner minste verdien fra lista og legger den til bakerst.
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

        }
    } // DobbeltLenketListeIterator

} // DobbeltLenketListe
