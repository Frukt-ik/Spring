package ua.springApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.springApp.dao.PersonDAO;
import ua.springApp.models.Person;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
//        return Person.class.isAssignableFrom(clazz);
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personDAO.getPerson(person.getEmail(),person.getId()).isPresent()){
            errors.rejectValue("email", "person.email.exists","Email already exists");
        }

        if(person.getAge()==null){
            errors.rejectValue("age", "person.age.invalid", "Age must be a valid number");
        }

    }
}
