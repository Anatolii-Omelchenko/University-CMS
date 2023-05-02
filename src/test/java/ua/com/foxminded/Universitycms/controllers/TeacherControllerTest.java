package ua.com.foxminded.Universitycms.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.Universitycms.models.Teacher;
import ua.com.foxminded.Universitycms.services.TeacherService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherController.class)
@WithMockUser
public class TeacherControllerTest {

    @MockBean
    private TeacherService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenTeachersWhenGetTeachersThenReturnCorrectView() throws Exception {
        when(service.getAll()).thenReturn(List.of(
                new Teacher(1L, "Teacher 1"),
                new Teacher(2L, "Teacher 2")
        ));

        mockMvc.perform(get("/teachers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("teachers_views/teachers"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(xpath("//div[@id='persons-list']/a").nodeCount(2));
    }

    @Test
    public void givenTeacherByIdWhenGetTeacherWithIdParamThenReturnCorrectView() throws Exception {
        when(service.findById(1L)).thenReturn(new Teacher(1L, "Test teacher 1"));

        mockMvc.perform(get("/teachers/teacher")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("teachers_views/teacher"))
                .andExpect(model().attributeExists("teacher"))
                .andExpect(xpath("//p[@id='value']").string("Test teacher 1"));
    }
}