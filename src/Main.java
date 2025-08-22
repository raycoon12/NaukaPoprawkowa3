import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Person> people = new ArrayList<>();
        Person person1 =
                new Person("Jan", "Kowalski", LocalDate.of(1980, 5, 12));
        Person person2 =
                new Person("Anna", "Nowak", LocalDate.of(1985, 8, 22));
        Person person3 =
                new Person("Piotr", "Kowalski", LocalDate.of(2000, 1, 15));
        Person person4 =
                new Person("Agata", "Wiśniewska", LocalDate.of(2006, 1, 14));

        people.add(person1);
        people.add(person2);
        people.add(person3);
        people.add(person4);

//        for (Person p : people) {
//            System.out.println(p);
//        }

        boolean adopted1 = person1.adopt(person3);
        boolean adopted2 = person1.adopt(person4);

//        System.out.println(person1.getYoungestChild().getFirst()
//                + " " + person1.getYoungestChild().getLast() + " is "
//                + person1.getFirst() + " " + person1.getLast() + "'s youngest child");
//
//        System.out.println(person1.getOldestChild().getFirst()
//                + " " + person1.getOldestChild().getLast() + " is "
//                + person1.getFirst() + " " + person1.getLast() + "'s oldest child");

        System.out.println(person1.getChildren());
    }
}