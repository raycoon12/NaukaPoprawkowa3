public class AmbiguousPersonException extends Exception {
    public AmbiguousPersonException(String fname, String lname) {
        super(String.format("Osoba %s %s pojawia się wielokrotnie w pliku", fname, lname));
    }
}
