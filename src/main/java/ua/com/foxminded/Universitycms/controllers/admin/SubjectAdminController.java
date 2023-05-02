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
import ua.com.foxminded.Universitycms.models.Subject;
import ua.com.foxminded.Universitycms.services.SubjectService;

@Controller
@RequestMapping("/admin")
public class SubjectAdminController {
    private final SubjectService subjectService;

    public SubjectAdminController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/add_subject")
    public String addSubject(Model model) {
        model.addAttribute("subject", new Subject());
        return "subjects_views/subject_add";
    }

    @GetMapping("/edit_subject")
    public String editSubject(@RequestParam Long id, Model model) {
        Subject subject = subjectService.findById(id);
        model.addAttribute("subject", subject);

        return "subjects_views/subject_edit";
    }

    @GetMapping("/delete_subject")
    public ModelAndView deleteSubject(@RequestParam Long id, ModelMap model) {
        Subject subject = subjectService.findById(id);
        subjectService.deleteById(id);
        model.addAttribute("message", "Subject " + subject.getSubjectName() + " deleted");

        return new ModelAndView("forward:/subjects", model);
    }

    @PostMapping("/edit_subject")
    public ModelAndView updateSubject(@Valid Subject subject, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String subjectName = subjectService.findById(subject.getId()).getSubjectName();

        if (subjectService.findByName(subject.getSubjectName()).isPresent() && !subjectName.equals(subject.getSubjectName())) {
            bindingResult.rejectValue("subjectName", "error.subject", "Subject name is already taken!");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("subjects_views/subject_edit");
        }

        subjectService.update(subject.getId(), subject);
        redirectAttributes.addFlashAttribute("message", "Subject updated.");
        redirectAttributes.addAttribute("id", subject.getId());

        return new ModelAndView("redirect:/subjects/subject");
    }

    @PostMapping("/add_subject")
    public ModelAndView saveSubject(@Valid Subject subject, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (subjectService.findByName(subject.getSubjectName()).isPresent()) {
            bindingResult.rejectValue("subjectName", "error.subject", "Subject name is already taken!");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("subjects_views/subject_add");
        }

        subjectService.addOne(subject);
        redirectAttributes.addFlashAttribute("message", "Subject '" + subject.getSubjectName() + "' was added.");
        return new ModelAndView("redirect:/subjects");
    }
}