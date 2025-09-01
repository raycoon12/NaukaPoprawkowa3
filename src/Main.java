import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args){
        String path = "family.csv";
        List<Person> people = Person.fromCsv(path);

        for (Person p : people) {
            System.out.println(p.toString());
        }
    }
}