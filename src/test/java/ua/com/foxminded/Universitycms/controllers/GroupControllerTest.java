package ua.com.foxminded.Universitycms.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.Universitycms.models.Group;
import ua.com.foxminded.Universitycms.services.GroupService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupController.class)
@WithMockUser
public class GroupControllerTest {

    @MockBean
    private GroupService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenGroupsWhenGetGroupsThenReturnCorrectView() throws Exception {
        when(service.getAll()).thenReturn(List.of(
                new Group("Test group"),
                new Group("Test group 2")
        ));

        mockMvc.perform(get("/groups"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("groups_views/groups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(xpath("//div[@id='groups-list']/a").nodeCount(2));
    }

    @Test
    public void givenGroupByIdWhenGetGroupWithIdParamThenReturnCorrectView() throws Exception {
        when(service.getOneGroup(1L)).thenReturn(new Group("Test group 1"));

        mockMvc.perform(get("/groups/group")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("groups_views/group"))
                .andExpect(model().attributeExists("group"))
                .andExpect(xpath("//p[@id='value']").string("Test group 1"));
    }
}