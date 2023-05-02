package ua.com.foxminded.Universitycms.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.foxminded.Universitycms.models.Lecture;
import ua.com.foxminded.Universitycms.models.enums.LectureNumber;
import ua.com.foxminded.Universitycms.services.*;

import java.util.Arrays;
import java.util.Map;

import static ua.com.foxminded.Universitycms.services.SupportService.getRedirectInfo;

@Controller
@RequestMapping("/admin")
public class LectureAdminController {
    private final LectureService lectureService;
    private final GroupService groupService;
    private final TeacherService teacherService;
    private final LectureRoomService lectureRoomService;
    private final SubjectService subjectService;

    public LectureAdminController(LectureService lectureService, GroupService groupService, TeacherService teacherService, LectureRoomService lectureRoomService, SubjectService subjectService) {
        this.lectureService = lectureService;
        this.groupService = groupService;
        this.teacherService = teacherService;
        this.lectureRoomService = lectureRoomService;
        this.subjectService = subjectService;
    }

    @GetMapping("/delete_lecture")
    public ModelAndView deleteLecture(@RequestParam Long id, @RequestParam String type, RedirectAttributes redirectAttributes) {
        Lecture lecture = lectureService.findById(id);
        lectureService.deleteById(id);

        Map<String, String> redirectInfo = getRedirectInfo(lecture, type, groupService, teacherService);
        redirectAttributes.addFlashAttribute("message", "Lecture was deleted.");
        redirectAttributes.addAttribute("id", redirectInfo.get("id"));
        redirectAttributes.addAttribute("days", 30);

        return new ModelAndView(redirectInfo.get("url"));
    }

    @GetMapping("/edit_lecture")
    public String editLecture(@RequestParam Long id, @RequestParam String type, Model model) {
        Lecture lecture = lectureService.findById(id);
        model.addAttribute("lecture", lecture);
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("rooms", lectureRoomService.getAll());
        model.addAttribute("lecturesNumbers", Arrays.asList(LectureNumber.values()));
        model.addAttribute("subjects", subjectService.getAll());
        model.addAttribute("type", type);

        return "lectures_views/lecture_edit";
    }

    @GetMapping("/add_lecture")
    public String addLecture(Model model, @RequestParam String type, @RequestParam(defaultValue = "0") Long id) {
        Lecture lecture = new Lecture();
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("teachers", teacherService.getAll());
        model.addAttribute("rooms", lectureRoomService.getAll());
        model.addAttribute("lecturesNumbers", Arrays.asList(LectureNumber.values()));
        model.addAttribute("subjects", subjectService.getAll());
        model.addAttribute("type", type);

        if (type.equals("group")) {
            lecture.setGroup(groupService.getOneGroup(id));
        } else if (type.equals("teacher")) {
            lecture.setTeacher(teacherService.findById(id));
        }
        model.addAttribute("lecture", lecture);

        return "lectures_views/lecture_add";
    }

    @PostMapping("/add_lecture")
    public ModelAndView saveLecture(Lecture lecture, String type, RedirectAttributes redirectAttributes) {
        lectureService.addOne(lecture);
        Map<String, String> redirectInfo = getRedirectInfo(lecture, type, groupService, teacherService);
        redirectAttributes.addFlashAttribute("message", "Lecture was added.");
        redirectAttributes.addAttribute("days", 30);
        redirectAttributes.addAttribute("id", redirectInfo.get("id"));

        return new ModelAndView(redirectInfo.get("url"));
    }

    @PostMapping("/edit_lecture")
    public ModelAndView updateLecture(Lecture lecture, String type, RedirectAttributes redirectAttributes) {
        lectureService.update(lecture.getId(), lecture);
        Map<String, String> redirectInfo = getRedirectInfo(lecture, type, groupService, teacherService);
        redirectAttributes.addFlashAttribute("message", "Lecture was updated.");
        redirectAttributes.addAttribute("id", redirectInfo.get("id"));
        redirectAttributes.addAttribute("days", 30);

        return new ModelAndView(redirectInfo.get("url"));
    }
}