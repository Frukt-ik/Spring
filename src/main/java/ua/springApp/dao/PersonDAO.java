package ua.springApp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.springApp.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> getPeople() {

        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id);

    }

    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES (1, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail() );
    }

    public void updatePerson(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name = ?, age = ?, email = ? WHERE id = ? ", person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

}
