package ua.com.foxminded.Universitycms.controllers.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.Universitycms.config.CustomUserDetailService;
import ua.com.foxminded.Universitycms.config.WebSecurityConfig;
import ua.com.foxminded.Universitycms.models.Group;
import ua.com.foxminded.Universitycms.services.GroupService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(authorities = {"ADMIN"})
@Import(WebSecurityConfig.class)
@WebMvcTest(GroupAdminController.class)
public class GroupAdminControllerTest {
    @MockBean
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailService userDetailsService;

    @Test
    public void whenPostAddGroupShouldAddNewGroupToDbThanReturnCorrectViewWithModels() throws Exception {
        Group group = new Group(2L, "GR-TT");

        when(groupService.getOneGroup(group.getId())).thenReturn(group);

        mockMvc.perform(post("/admin/add_group")
                        .param("id", group.getId().toString())
                        .param("groupName", group.getGroupName()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups"))
                .andExpect(flash().attribute("message", "Group '" + group.getGroupName() + "' was added."));
        verify(groupService).addOne(group);
    }

    @Test
    public void whenPostEditGroupShouldUpdateGroupInDbThanReturnCorrectViewWithModels() throws Exception {
        Group group = new Group(2L, "GR-TT");

        when(groupService.getOneGroup(group.getId())).thenReturn(group);

        mockMvc.perform(post("/admin/edit_group")
                        .param("id", group.getId().toString())
                        .param("groupName", group.getGroupName()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/groups/group?id=" + group.getId()))
                .andExpect(flash().attribute("message", "Group updated."))
                .andExpect(model().attributeExists("id"));
        verify(groupService).update(group.getId(), group);
    }

    @Test
    public void whenGetDeleteGroupShouldRemoveGroupFromDBThenReturnCorrectView() throws Exception {
        String groupName = "GR-01";
        when(groupService.getOneGroup(1L)).thenReturn(new Group(1L, groupName));

        mockMvc.perform(get("/admin/delete_group")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/groups"))
                .andExpect(model().attribute("message", "Group " + groupName + " deleted"));
        verify(groupService).deleteById(1L);
    }

    @Test
    public void whenGetEditGroupShouldReturnCorrectViewWithModels() throws Exception {
        Group group = new Group();
        when(groupService.getOneGroup(1L)).thenReturn(group);

        mockMvc.perform(get("/admin/edit_group")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("groups_views/group_edit"))
                .andExpect(model().attribute("group", group));
    }

    @Test
    public void whenGetAddGroupShouldReturnCorrectViewWithModels() throws Exception {
        Group group = new Group();
        mockMvc.perform(get("/admin/add_group"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("groups_views/group_add"))
                .andExpect(model().attribute("group", group));
    }
}