package ua.springApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.springApp.dao.PersonDAO;
import ua.springApp.models.Person;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
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

}
