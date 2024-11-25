package ua.springApp.dao;

import org.springframework.stereotype.Component;
import ua.springApp.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final List<Person> people;
    private static int COUNT;


    {
        people = new ArrayList<>();
        people.add(new Person(++COUNT,"Mashido","ponich@gmail.com", 20));
        people.add(new Person(++COUNT,"Pashark","pashark@gmail.com", 25));
        people.add(new Person(++COUNT,"Butkich","butkich@gmail.com", 30));
        people.add(new Person(++COUNT,"Yamaho","yamaho@gmail.com", 40));
    }

    public List<Person> getPeople() {
        return people;
    }

    public Person getPerson(int id) {
        return people.stream().filter(person -> person.getId() == id).findFirst().orElse(null);
    }

    public void addPerson(Person person) {
        person.setId(++COUNT);
        people.add(person);
    }

    public void updatePerson(int id, Person person) {
        Person oldPerson = getPerson(id);
        oldPerson.setName(person.getName());
        oldPerson.setAge(person.getAge());
        oldPerson.setEmail(person.getEmail());
    }

    public void deletePerson(int id) {
        Person person = getPerson(id);
        people.remove(person);
    }

}
