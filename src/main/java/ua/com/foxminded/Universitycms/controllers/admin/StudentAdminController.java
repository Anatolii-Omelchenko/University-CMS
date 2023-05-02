package ua.com.foxminded.Universitycms.controllers.admin;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.foxminded.Universitycms.models.Student;
import ua.com.foxminded.Universitycms.models.enums.Role;
import ua.com.foxminded.Universitycms.services.GroupService;
import ua.com.foxminded.Universitycms.services.StudentService;

import java.util.Arrays;

import static ua.com.foxminded.Universitycms.services.SupportService.isAdmin;

@Controller
@RequestMapping("/admin")
public class StudentAdminController {

    private final StudentService studentService;
    private final GroupService groupService;

    public StudentAdminController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping("/delete_student")
    public ModelAndView deleteStudent(@RequestParam Long id, ModelMap model) {
        Student student = studentService.findById(id);
        if (isAdmin(student)) {
            model.addAttribute("error", "You cant delete ADMIN!");
        } else {
            studentService.deleteById(id);
            model.addAttribute("message", "Student " + student.getName() + " deleted");
        }
        return new ModelAndView("forward:/students", model);
    }

    @GetMapping("/edit_student")
    public String editStudent(@RequestParam Long id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        model.addAttribute("allRoles", Arrays.asList(Role.values()));
        model.addAttribute("groups", groupService.getAll());

        return "students_views/student_edit";
    }

    @PostMapping("/edit_student")
    public ModelAndView updateStudent(@Valid Student student, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String email = studentService.findById(student.getId()).getEmail();

        if (studentService.findByEmail(student.getEmail()).isPresent() && !email.equals(student.getEmail())) {
            bindingResult.rejectValue("email", "error.student", "Email is already taken!");
        }

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("students_views/student_edit");
            modelAndView.addObject("allRoles", Arrays.asList(Role.values()));
            return modelAndView;
        }

        studentService.update(student.getId(), student);
        redirectAttributes.addFlashAttribute("message", "Student updated.");
        redirectAttributes.addAttribute("id", student.getId());

        return new ModelAndView("redirect:/students/student");
    }

    @GetMapping("/add_student")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("allRoles", Arrays.asList(Role.values()));

        return "students_views/student_add";
    }

    @PostMapping("/add_student")
    public ModelAndView saveStudent(@Valid Student student, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (studentService.findByEmail(student.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.student", "Email is already taken!");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("students_views/student_add");
        }

        studentService.addOne(student);
        redirectAttributes.addFlashAttribute("message", "Student '" + student.getName() + "' was added.");
        return new ModelAndView("redirect:/students");
    }
}