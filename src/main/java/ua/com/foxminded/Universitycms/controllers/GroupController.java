package ua.com.foxminded.Universitycms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.Universitycms.models.Group;
import ua.com.foxminded.Universitycms.services.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String getAllGroups(Model model) {
        model.addAttribute("groups", groupService.getAll());
        return "groups_views/groups";
    }

    @GetMapping("/group")
    public String getOneStudent(@RequestParam Long id, Model model) {
        Group group = groupService.getOneGroup(id);
        model.addAttribute("group", group);
        return "groups_views/group";
    }
}