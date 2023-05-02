package ua.com.foxminded.Universitycms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.Universitycms.services.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getAllStudents(Model model) {
        model.addAttribute("students", studentService.getAll());
        return "students_views/students";
    }

    @GetMapping("/student")
    public String getOneStudent(@RequestParam Long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "students_views/student";
    }
}