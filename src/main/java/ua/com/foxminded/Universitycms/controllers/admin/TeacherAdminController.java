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
import ua.com.foxminded.Universitycms.models.Teacher;
import ua.com.foxminded.Universitycms.models.enums.Role;
import ua.com.foxminded.Universitycms.services.TeacherService;

import java.util.Arrays;

import static ua.com.foxminded.Universitycms.services.SupportService.isAdmin;

@Controller
@RequestMapping("/admin")
public class TeacherAdminController {

    private final TeacherService teacherService;

    public TeacherAdminController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/edit_teacher")
    public String editTeacher(@RequestParam Long id, Model model) {
        Teacher teacher = teacherService.findById(id);
        model.addAttribute("teacher", teacher);
        model.addAttribute("allRoles", Arrays.asList(Role.values()));

        return "teachers_views/teacher_edit";
    }

    @PostMapping("/edit_teacher")
    public ModelAndView updateTeacher(@Valid Teacher teacher, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String email = teacherService.findById(teacher.getId()).getEmail();

        if (teacherService.findByEmail(teacher.getEmail()).isPresent() && !email.equals(teacher.getEmail())) {
            bindingResult.rejectValue("email", "error.teacher", "Email is already taken!");
        }

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("teachers_views/teacher_edit");
            modelAndView.addObject("allRoles", Arrays.asList(Role.values()));
            return modelAndView;
        }

        teacherService.update(teacher.getId(), teacher);
        redirectAttributes.addFlashAttribute("message", "Teacher updated.");
        redirectAttributes.addAttribute("id", teacher.getId());

        return new ModelAndView("redirect:/teachers/teacher");
    }

    @GetMapping("/delete_teacher")
    public ModelAndView deleteTeacher(@RequestParam Long id, ModelMap model) {
        Teacher teacher = teacherService.findById(id);
        if (isAdmin(teacher)) {
            model.addAttribute("error", "You cant delete ADMIN!");
        } else {
            teacherService.deleteById(id);
            model.addAttribute("message", "Teacher " + teacher.getName() + " deleted");
        }
        return new ModelAndView("forward:/teachers", model);
    }

    @GetMapping("/add_teacher")
    public String addTeacher(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("allRoles", Arrays.asList(Role.values()));

        return "teachers_views/teacher_add";
    }

    @PostMapping("/add_teacher")
    public ModelAndView saveTeacher(@Valid Teacher teacher, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (teacherService.findByEmail(teacher.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.teacher", "Email is already taken!");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("teachers_views/teacher_add");
        }

        teacherService.addOne(teacher);
        redirectAttributes.addFlashAttribute("message", "Teacher '" + teacher.getName() + "' was added.");
        return new ModelAndView("redirect:/teachers");
    }
}