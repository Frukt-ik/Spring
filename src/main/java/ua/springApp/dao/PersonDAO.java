package ua.springApp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.springApp.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> getPeople() {

        return jdbcTemplate.query("SELECT * FROM person2", new BeanPropertyRowMapper<>(Person.class));
    }


    public Optional<Person> getPerson(String email, int id) {
        return jdbcTemplate.query("SELECT * FROM person2 WHERE email = ? AND id != ?", new BeanPropertyRowMapper<>(Person.class), email, id).stream().findAny();
    }

    public Person getPerson(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person2 WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id);

    }

    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO person2(name,age,email) VALUES ( ?, ?, ?)", person.getName(), person.getAge(), person.getEmail() );
    }

    public void updatePerson(int id, Person person) {
        jdbcTemplate.update("UPDATE person2 SET name = ?, age = ?, email = ? WHERE id = ? ", person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM person2 WHERE id = ?", id);
    }
    /////////////////////////////////////////////////////////
    //////////////  Batch and Multiple update
    ////////////////////////////////////////////////////////

    public void doMultipleUpdate(){
        List<Person> people = generatePeople();
        double start = System.currentTimeMillis();

        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person2(name,age,email) VALUES (?, ?, ?)",
                    person.getName(), person.getAge(), person.getEmail());
        }

        double end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));
    }

    public void doBatchUpdate(){
        List<Person> people = generatePeople();
        double start = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO person2(name,age,email) VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, people.get(i).getName());
                ps.setInt(2, people.get(i).getAge());
                ps.setString(3, people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });


        double end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));

    }

    private List<Person> generatePeople() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Person person = new Person(i, "Name"+i,"test"+i+"@gmail.com", 30 );
            people.add(person);
        }
        return people;
    }
}
