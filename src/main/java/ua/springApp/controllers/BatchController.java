package ua.springApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.springApp.dao.PersonDAO;

@Controller
@RequestMapping("/batch")
public class BatchController {

    private final PersonDAO personDAO;

    @Autowired
    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("")
    public String batch() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String without() {
        personDAO.doMultipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String with() {
        personDAO.doBatchUpdate();
        return "redirect:/people";
    }

}
