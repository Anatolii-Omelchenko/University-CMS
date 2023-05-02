package ua.com.foxminded.Universitycms.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.Universitycms.models.Group;
import ua.com.foxminded.Universitycms.models.Teacher;
import ua.com.foxminded.Universitycms.services.GroupService;
import ua.com.foxminded.Universitycms.services.LectureService;
import ua.com.foxminded.Universitycms.services.TeacherService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LectureController.class)
@WithMockUser
public class LectureControllerTest {

    @MockBean
    private GroupService groupService;
    @MockBean
    private TeacherService teacherService;
    @MockBean
    private LectureService lectureService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllLecturesShouldReturnLecturesViewWithLecturesAttribute() throws Exception {
        mockMvc.perform(get("/lectures/all")
                        .param("days", "7"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("days", "lectures"))
                .andExpect(view().name("lectures_views/lectures"));
    }

    @Test
    void getGroupLecturesShouldReturnLecturesViewWithGroupsAttributes() throws Exception {
        Group group = new Group(1L, "Test group 1");
        when(groupService.getOneGroup(1L)).thenReturn(group);

        mockMvc.perform(get("/lectures/group")
                        .param("id", "1")
                        .param("days", "7"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("days", "lectures"))
                .andExpect(model().attribute("groupName", group.getGroupName()))
                .andExpect(model().attribute("groupId", group.getId()))
                .andExpect(view().name("lectures_views/lectures"));
    }

    @Test
    void getTeacherLecturesShouldReturnLecturesViewWithTeachersAttributes() throws Exception {
        Teacher teacher = new Teacher(1L, "Test teacher");
        when(teacherService.findById(1L)).thenReturn(teacher);

        mockMvc.perform(get("/lectures/teacher")
                        .param("id", "1")
                        .param("days", "7"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("days", "lectures"))
                .andExpect(model().attribute("teacherName", teacher.getName()))
                .andExpect(model().attribute("teacherId", teacher.getId()))
                .andExpect(view().name("lectures_views/lectures"));
    }
}