package ua.com.foxminded.Universitycms.services;

import ua.com.foxminded.Universitycms.models.Lecture;
import ua.com.foxminded.Universitycms.models.Person;
import ua.com.foxminded.Universitycms.models.enums.Role;

import java.util.HashMap;
import java.util.Map;

public class SupportService {

    public static boolean isAdmin(Person person) {
        return person.getRoles().contains(Role.ADMIN);
    }

    public static Map<String, String> getRedirectInfo(Lecture lecture, String type, GroupService groupService, TeacherService teacherService) {
        Map<String, String> redirectInfo = new HashMap<>();

        String redirectUrl = "redirect:/lectures/all";
        Long redirectId = 0L;

        if (type.equals("group")) {
            redirectId = groupService.getOneGroup(lecture.getGroup().getId()).getId();
            redirectUrl = "redirect:/lectures/group";
        } else if (type.equals("teacher")) {
            redirectId = teacherService.findById(lecture.getTeacher().getId()).getId();
            redirectUrl = "redirect:/lectures/teacher";
        }

        redirectInfo.put("url", redirectUrl);
        redirectInfo.put("id", String.valueOf(redirectId));

        return redirectInfo;
    }
}