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
import ua.com.foxminded.Universitycms.models.Group;
import ua.com.foxminded.Universitycms.services.GroupService;

@Controller
@RequestMapping("/admin")
public class GroupAdminController {
    private final GroupService groupService;

    public GroupAdminController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/delete_group")
    public ModelAndView deleteGroup(@RequestParam Long id, ModelMap model) {
        Group group = groupService.getOneGroup(id);
        groupService.deleteById(id);
        model.addAttribute("message", "Group " + group.getGroupName() + " deleted");

        return new ModelAndView("forward:/groups", model);
    }

    @GetMapping("/edit_group")
    public String editGroup(@RequestParam Long id, Model model) {
        Group group = groupService.getOneGroup(id);
        model.addAttribute("group", group);

        return "groups_views/group_edit";
    }

    @PostMapping("/edit_group")
    public ModelAndView updateGroup(@Valid Group group, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String groupName = groupService.getOneGroup(group.getId()).getGroupName();

        if (groupService.getGroupByName(group.getGroupName()).isPresent() && !groupName.equals(group.getGroupName())) {
            bindingResult.rejectValue("groupName", "error.group", "Group name is already taken!");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("groups_views/group_edit");
        }

        groupService.update(group.getId(), group);
        redirectAttributes.addFlashAttribute("message", "Group updated.");
        redirectAttributes.addAttribute("id", group.getId());

        return new ModelAndView("redirect:/groups/group");
    }

    @GetMapping("/add_group")
    public String addGroup(Model model) {
        model.addAttribute("group", new Group());
        return "groups_views/group_add";
    }

    @PostMapping("/add_group")
    public ModelAndView saveGroup(@Valid Group group, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (groupService.getGroupByName(group.getGroupName()).isPresent()) {
            bindingResult.rejectValue("groupName", "error.group", "Group name is already taken!");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("groups_views/group_add");
        }

        groupService.addOne(group);
        redirectAttributes.addFlashAttribute("message", "Group '" + group.getGroupName() + "' was added.");
        return new ModelAndView("redirect:/groups");
    }
}