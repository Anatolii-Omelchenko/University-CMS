package ua.com.foxminded.Universitycms.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.Universitycms.models.Student;
import ua.com.foxminded.Universitycms.services.StudentService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@WithMockUser
public class StudentControllerTest {

    @MockBean
    private StudentService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenStudentsWhenGetStudentsThenReturnCorrectView() throws Exception {
        when(service.getAll()).thenReturn(List.of(
                new Student(1L, "Student 1"),
                new Student(2L, "Student 2")
        ));

        mockMvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("students_views/students"))
                .andExpect(model().attributeExists("students"))
                .andExpect(xpath("//div[@id='persons-list']/a").nodeCount(2));
    }

    @Test
    public void givenStudentByIdWhenGetStudentWithIdParamThenReturnCorrectView() throws Exception {
        when(service.findById(1L)).thenReturn(new Student(1L, "Test student 1"));

        mockMvc.perform(get("/students/student")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("students_views/student"))
                .andExpect(model().attributeExists("student"))
                .andExpect(xpath("//p[@id='value']").string("Test student 1"));
    }
}