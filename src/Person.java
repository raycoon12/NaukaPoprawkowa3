import java.time.LocalDate;
import java.util.*;

public class Person {
    private String first;
    private String last;
    private LocalDate birthdate;
    private LocalDate deathdate;
    private Set<Person> children = new HashSet<>();

    public Person(String first, String last, LocalDate birthdate, LocalDate deathdate) {
        this.first = first;
        this.last = last;
        this.birthdate = birthdate;
        this.deathdate = deathdate;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public LocalDate getDate() {
        return birthdate;
    }
    public List<Person> getChildren() {
        List<Person> sortedChildren = new ArrayList<>(children);
        sortedChildren.sort(Comparator.comparing(Person::getDate));
        return sortedChildren;
    }


    public boolean adopt(Person child){
        return children.add(child);
    }
    public Person getYoungestChild(){
        if(children.isEmpty())
            return null;
        LocalDate youngestChildAge = LocalDate.MIN;
        Person youngestChild = null;
        for (Person child : children){
            if(child.birthdate.isAfter(youngestChildAge)) {
                youngestChildAge = child.birthdate;
                youngestChild = child;
            }
        }
        return youngestChild;
    }

    public Person getOldestChild(){
        if(children.isEmpty())
            return null;
        LocalDate oldestChildAge = LocalDate.MAX;
        Person oldestChild = null;
        for (Person child : children){
            if(child.birthdate.isBefore(oldestChildAge)) {
                oldestChildAge = child.birthdate;
                oldestChild = child;
            }
        }
        return oldestChild;
    }

//    public Person getYoungestChild(){
//        if(children.isEmpty()) {
//            System.out.println("This person doesn't have kids");
//            return null;
//        }
//        Person youngest = null;
//        for (Person child : children) {
//            if(youngest == null || child.getDate().isAfter(youngest.getDate())){
//                youngest = child;
//            }
//        }
//        return  youngest;
//    }
//
//    public Person getOldestChild(){
//        if(children.isEmpty()) {
//            System.out.println("This person doesn't have kids");
//            return null;
//        }
//        Person oldest = null;
//        for (Person child : children) {
//            if(oldest == null || child.getDate().isBefore(oldest.getDate())){
//                oldest = child;
//            }
//        }
//        return  oldest;
//    }
}
