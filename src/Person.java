import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Person implements Comparable<Person>, Serializable {
    String fname, lname;
    LocalDate birthDate;
    LocalDate deathDate;
    Person father;
    Person mother;
    Set<Person> children;
    public boolean adopt(Person child){
        return children.add(child);
    }

    public Person(String fname, String lname, LocalDate birthDate, LocalDate deathDate) {
        this.fname = fname;
        this.lname = lname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.children = new TreeSet<>();
    }
    public Person(String fname, String lname, LocalDate birthDate) {
        this.fname = fname;
        this.lname = lname;
        this.birthDate = birthDate;
        this.children = new TreeSet<>();
    }

    public int getAge() {
        if (birthDate == null) return 0;
        LocalDate endDate = (deathDate != null) ? deathDate : LocalDate.now();
        int age = endDate.getYear() - birthDate.getYear();
        if (endDate.getDayOfYear() < birthDate.getDayOfYear()) age--;
        return age;
    }

    public Person getYoungestChild(){
//        if(children.isEmpty())
//            return null;
        LocalDate youngestChildAge = LocalDate.MIN;
        Person youngestChild = null;
        for (Person child : children){
            if(child.birthDate.isAfter(youngestChildAge)) {
                youngestChildAge = child.birthDate;
                youngestChild = child;
            }
        }
        return youngestChild;
    }
    public List<Person> getChildren(){
        return List.copyOf(children);
    }

    @Override
    public int compareTo(Person o) {
        return this.birthDate.compareTo(o.birthDate);
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public static Person fromCsvLine(String line) throws NegativeLifespanException {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] columns = line.split(",");
        if (columns.length == 0 || !isNotEmpty(columns[0])) {
            return null;
        }

        String[] flname = columns[0].trim().split(" ");
        String fname = flname[0];
        String lname = flname.length > 1 ? flname[1] : "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = null;
        LocalDate deathDate = null;

        if (columns.length > 1 && isNotEmpty(columns[1])) {
            birthDate = LocalDate.parse(columns[1].trim(), formatter);
        }

        if (columns.length > 2 && isNotEmpty(columns[2])) {
            deathDate = LocalDate.parse(columns[2].trim(), formatter);
            if (birthDate != null && deathDate.isBefore(birthDate)) {
                throw new NegativeLifespanException(fname, lname);
            }
        }

        return new Person(fname, lname, birthDate, deathDate);
    }

    public static List<Person> fromCsv(String path) {
        List<Person> people = new ArrayList<>();
        Map<String, Person> personMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();

            List<String[]> rawLines = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                Person p = fromCsvLine(line);
                if (p == null) continue;

                String key = p.fname + " " + p.lname;

                if (personMap.containsKey(key)) {
                    throw new AmbiguousPersonException(p.fname, p.lname);
                }
                personMap.put(key, p);
                people.add(p);
                rawLines.add(line.split(","));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            for (String[] cols : rawLines) {
                String[] fl = cols[0].split(" ");
                String fname = fl[0];
                String lname = fl.length > 1 ? fl[1] : "";
                Person child = personMap.get(fname + " " + lname);

                if (cols.length > 3 && isNotEmpty(cols[3])) {
                    Person parent = personMap.get(cols[3]);
                    if (parent != null) {
                        try {
                            if (parent.birthDate != null && child.birthDate != null) {
                                int ageAtBirth = child.birthDate.getYear() - parent.birthDate.getYear();
                                if (ageAtBirth < 15) {
                                    throw new ParentingAgeException(
                                            parent.fname + " " + parent.lname,
                                            child.fname + " " + child.lname,
                                            "rodzic miał mniej niż 15 lat w chwili narodzin dziecka"
                                    );
                                }
                            }

                            if (parent.deathDate != null && child.birthDate != null &&
                                    parent.deathDate.isBefore(child.birthDate)) {
                                throw new ParentingAgeException(
                                        parent.fname + " " + parent.lname,
                                        child.fname + " " + child.lname,
                                        "rodzic zmarł przed narodzinami dziecka"
                                );
                            }

                            parent.adopt(child);
                            child.father = parent;

                        } catch (ParentingAgeException e) {
                            System.err.println(e.getMessage());
                            System.out.print("Czy zaakceptować ten przypadek? (Y/N): ");
                            Scanner sc = new Scanner(System.in);
                            if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
                                parent.adopt(child);
                                child.father = parent;
                            }
                        }
                    }
                }
                if (cols.length > 3 && isNotEmpty(cols[3])) {
                    Person parent = personMap.get(cols[3]);
                    if (parent != null) {
                        try {
                            if (parent.birthDate != null && child.birthDate != null) {
                                int ageAtBirth = child.birthDate.getYear() - parent.birthDate.getYear();
                                if (ageAtBirth < 15) {
                                    throw new ParentingAgeException(
                                            parent.fname + " " + parent.lname,
                                            child.fname + " " + child.lname,
                                            "rodzic miał mniej niż 15 lat w chwili narodzin dziecka"
                                    );
                                }
                            }

                            if (parent.deathDate != null && child.birthDate != null &&
                                    parent.deathDate.isBefore(child.birthDate)) {
                                throw new ParentingAgeException(
                                        parent.fname + " " + parent.lname,
                                        child.fname + " " + child.lname,
                                        "rodzic zmarł przed narodzinami dziecka"
                                );
                            }

                            parent.adopt(child);
                            child.mother = parent;

                        } catch (ParentingAgeException e) {
                            System.err.println(e.getMessage());
                            System.out.print("Czy zaakceptować ten przypadek? (Y/N): ");
                            Scanner sc = new Scanner(System.in);
                            if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
                                parent.adopt(child);
                                child.mother = parent;
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Plik nie istnieje: " + path);
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
        } catch (NegativeLifespanException | AmbiguousPersonException e) {
            System.err.println(e.getMessage());
        }

        return people;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static void toBinaryFile(String path, List<Person> people) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(people);
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku binarnego: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Person> fromBinaryFile(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (List<Person>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd odczytu z pliku binarnego: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return fname  + " " + lname + ", " + birthDate +
                ", " + deathDate + ", " + getAge();
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
