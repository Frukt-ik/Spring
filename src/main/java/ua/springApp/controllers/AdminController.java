package ua.springApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.springApp.dao.PersonDAO;
import ua.springApp.models.Person;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private PersonDAO personDAO;

    @Autowired
    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String adminList(@ModelAttribute("person") Person person, Model model) {
        model.addAttribute("people", personDAO.getPeople());
        return "admin/list";
    }

    @PatchMapping("/add")
    public String add(@ModelAttribute("person") Person person) {
        System.out.println(person.getId());
        return "redirect:/people";
    }
}
