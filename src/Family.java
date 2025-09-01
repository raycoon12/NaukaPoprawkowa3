import java.util.HashMap;
import java.util.Map;

public class Family {
    Map<String, Person> family = new HashMap<>();
    public void add(Person... peopleToAdd){
        for(Person p : peopleToAdd)
            add(p);
    }
    public void add(Person personToAdd){
        String newKey = personToAdd.getFname() + " " + personToAdd.getLname();
        if (family.containsKey(newKey))
            return;
        family.put(newKey, personToAdd);
    }
    public Person get(String key){
        return family.get(key);
    }
}
