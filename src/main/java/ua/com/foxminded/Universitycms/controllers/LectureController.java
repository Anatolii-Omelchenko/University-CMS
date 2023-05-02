package ua.com.foxminded.Universitycms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.Universitycms.services.GroupService;
import ua.com.foxminded.Universitycms.services.LectureService;
import ua.com.foxminded.Universitycms.services.TeacherService;

import java.time.LocalDate;

@Controller
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;
    private final TeacherService teacherService;
    private final GroupService groupService;

    public LectureController(LectureService lectureService, TeacherService teacherService, GroupService groupService) {
        this.lectureService = lectureService;
        this.teacherService = teacherService;
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public String getAllLectures(@RequestParam Long days, Model model) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(days);

        model.addAttribute("days", days);
        model.addAttribute("lectures", lectureService.findAllByDate(start, end));
        return "lectures_views/lectures";
    }

    @GetMapping("/group")
    public String getGroupLectures(@RequestParam Long id, @RequestParam Long days, Model model) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(days);

        model.addAttribute("groupId", id);
        model.addAttribute("groupName", groupService.getOneGroup(id).getGroupName());
        model.addAttribute("days", days);
        model.addAttribute("lectures", lectureService.findByGroupIdAndDate(id, start, end));
        return "lectures_views/lectures";
    }

    @GetMapping("/teacher")
    public String getTeacherLectures(@RequestParam Long id, @RequestParam Long days, Model model) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(days);

        model.addAttribute("teacherId", id);
        model.addAttribute("teacherName", teacherService.findById(id).getName());
        model.addAttribute("days", days);
        model.addAttribute("lectures", lectureService.findByTeacherIdAndDate(id, start, end));
        return "lectures_views/lectures";
    }
}