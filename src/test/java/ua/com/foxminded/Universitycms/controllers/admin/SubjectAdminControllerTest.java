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

import ua.com.foxminded.Universitycms.models.Subject;
import ua.com.foxminded.Universitycms.services.SubjectService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

@WithMockUser(authorities = {"ADMIN"})
@Import(WebSecurityConfig.class)
@WebMvcTest(SubjectAdminController.class)
public class SubjectAdminControllerTest {
    @MockBean
    private SubjectService subjectService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailService userDetailsService;

    @Test
    public void whenGetAddSubjectShouldReturnCorrectViewWithModels() throws Exception {
        Subject subject = new Subject();
        mockMvc.perform(get("/admin/add_subject"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("subjects_views/subject_add"))
                .andExpect(model().attribute("subject", subject));
    }

    @Test
    public void whenGetEditSubjectShouldReturnCorrectViewWithModels() throws Exception {
        Subject subject = new Subject();
        when(subjectService.findById(1L)).thenReturn(subject);

        mockMvc.perform(get("/admin/edit_subject")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("subjects_views/subject_edit"))
                .andExpect(model().attribute("subject", subject));
    }

    @Test
    public void whenGetDeleteSubjectShouldRemoveSubjectFromDBThenReturnCorrectView() throws Exception {
        String subjectName = "Test subject";
        when(subjectService.findById(1L)).thenReturn(new Subject(1L, subjectName));

        mockMvc.perform(get("/admin/delete_subject")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/subjects"))
                .andExpect(model().attribute("message", "Subject " + subjectName + " deleted"));
        verify(subjectService).deleteById(1L);
    }

    @Test
    public void whenPostEditSubjectShouldUpdateSubjectInDbThanReturnCorrectViewWithModels() throws Exception {
        Subject subject = new Subject(2L, "Test subject");

        when(subjectService.findById(subject.getId())).thenReturn(subject);

        mockMvc.perform(post("/admin/edit_subject")
                        .param("id", subject.getId().toString())
                        .param("subjectName", subject.getSubjectName())
                        .param("description", subject.getDescription()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subjects/subject?id=" + subject.getId()))
                .andExpect(flash().attribute("message", "Subject updated."))
                .andExpect(model().attributeExists("id"));
        verify(subjectService).update(subject.getId(), subject);
    }

    @Test
    public void whenPostAddSubjectShouldAddNewSubjectToDbThanReturnCorrectViewWithModels() throws Exception {
        Subject subject = new Subject(2L, "Test subject");

        when(subjectService.findById(subject.getId())).thenReturn(subject);

        mockMvc.perform(post("/admin/add_subject")
                        .param("id", subject.getId().toString())
                        .param("subjectName", subject.getSubjectName())
                        .param("description", subject.getDescription()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/subjects"))
                .andExpect(flash().attribute("message", "Subject '" + subject.getSubjectName() + "' was added."));
        verify(subjectService).addOne(subject);
    }
}