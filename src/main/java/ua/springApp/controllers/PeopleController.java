package ua.springApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.springApp.dao.PersonDAO;
import ua.springApp.models.Person;
import ua.springApp.util.PersonValidator;

import java.beans.PropertyEditorSupport;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping("")
    public String peopleList(Model model){
        model.addAttribute("people", personDAO.getPeople());
        return "peopleList";
    }

    @GetMapping("/{id}")
    public String peopleDetail(@PathVariable("id") int id,
                               Model model){
        model.addAttribute("person", personDAO.getPerson(id));
        return "personDetail";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "newPerson";
    }

    @PostMapping("")
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "newPerson";
        }
        personDAO.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id,
                             Model model){
        model.addAttribute("person", personDAO.getPerson(id));
        return "editPerson";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id){

        if (person.getAge() == null) {
            bindingResult.rejectValue("age", "person.age.invalid", "Age must be a valid number");
        }

        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "editPerson";
        }
        personDAO.updatePerson(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }


//    /////////////////////////////////////////////////////////////////////////////////////////////////////
//    Цей метод додає кастомний редактор властивостей (PropertyEditorSupport) для полів типу Integer.
//    Якщо користувач вводить некоректні дані (наприклад, "sss"), значення age буде встановлено як null, а не спричинить виняток.
//    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null); // Дозволяємо null для порожніх рядків
                } else {
                    try {
                        setValue(Integer.parseInt(text)); // Пробуємо конвертувати
                    } catch (NumberFormatException e) {
                        setValue(null); // Якщо конверсія не вдалася, залишаємо поле null
                    }
                }
            }
        });
    }
}
