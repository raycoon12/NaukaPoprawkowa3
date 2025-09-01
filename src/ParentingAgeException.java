public class ParentingAgeException extends Exception {
    public ParentingAgeException(String parentName, String childName, String reason) {
        super(String.format("Rodzic %s nie może mieć dziecka %s: %s",
                parentName, childName, reason));
    }
}