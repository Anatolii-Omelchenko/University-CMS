package ua.com.foxminded.Universitycms.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.foxminded.Universitycms.models.Subject;
import ua.com.foxminded.Universitycms.services.SubjectService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubjectController.class)
@WithMockUser
public class SubjectControllerTest {

    @MockBean
    private SubjectService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenSubjectsWhenGetSubjectsThenReturnCorrectView() throws Exception {
        when(service.getAll()).thenReturn(List.of(
                new Subject("Test subject"),
                new Subject("Test subject 2")
        ));

        mockMvc.perform(get("/subjects"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("subjects_views/subjects"))
                .andExpect(model().attributeExists("subjects"))
                .andExpect(xpath("//div[@id='subjects-list']/a").nodeCount(2));
    }

    @Test
    public void givenSubjectByIdWhenGetSubjectWithIdParamThenReturnCorrectView() throws Exception {
        when(service.findById(1L)).thenReturn(new Subject("Test subject 1"));

        mockMvc.perform(get("/subjects/subject")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("subjects_views/subject"))
                .andExpect(model().attributeExists("subject"))
                .andExpect(xpath("//p[@id='value']").string("Test subject 1"));
    }
}