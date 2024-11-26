package ua.springApp.dao;

import org.springframework.stereotype.Component;
import ua.springApp.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
private static int COUNT;

private static final String URL="jdbc:postgresql://localhost:5432/first_db";
private static final String USER="postgres";
private static final String PASSWORD="admin";

private static Connection connection;

static {
    try {
        Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    try {
        connection = DriverManager.getConnection(URL,USER,PASSWORD);
    } catch (SQLException e) {
        e.printStackTrace();
    }

}

    public List<Person> getPeople() {

        List<Person> people = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
    }

    public Person getPerson(int id) {
//        return people.stream().filter(person -> person.getId() == id).findFirst().orElse(null);
        return null;
    }

    public void addPerson(Person person) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO person VALUES (" + 1 + ",'" + person.getName()+"'," + person.getAge() + ",'" + person.getEmail() + "')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePerson(int id, Person person) {
//        Person oldPerson = getPerson(id);
//        oldPerson.setName(person.getName());
//        oldPerson.setAge(person.getAge());
//        oldPerson.setEmail(person.getEmail());
    }

    public void deletePerson(int id) {
//        Person person = getPerson(id);
//        people.remove(person);
    }

}
