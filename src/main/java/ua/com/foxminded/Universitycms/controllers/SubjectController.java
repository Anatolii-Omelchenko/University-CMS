package ua.com.foxminded.Universitycms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.Universitycms.services.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String getAllGroups(Model model) {
        model.addAttribute("subjects", subjectService.getAll());
        return "subjects_views/subjects";
    }

    @GetMapping("/subject")
    public String getOneStudent(@RequestParam Long id, Model model) {
        model.addAttribute("subject", subjectService.findById(id));
        return "subjects_views/subject";
    }
}