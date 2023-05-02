package ua.com.foxminded.Universitycms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.Universitycms.services.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getAllTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAll());
        return "teachers_views/teachers";
    }

    @GetMapping("/teacher")
    public String getOneTeacher(@RequestParam Long id, Model model) {
        model.addAttribute("teacher", teacherService.findById(id));
        return "teachers_views/teacher";
    }
}