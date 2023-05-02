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
import ua.com.foxminded.Universitycms.models.Teacher;
import ua.com.foxminded.Universitycms.models.enums.Role;
import ua.com.foxminded.Universitycms.services.TeacherService;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(authorities = {"ADMIN"})
@Import(WebSecurityConfig.class)
@WebMvcTest(TeacherAdminController.class)
public class TeacherAdminControllerTest {
    @MockBean
    private TeacherService teacherService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailService userDetailsService;

    @Test
    public void whenPostAddTeacherShouldAddNewTeacherToDbThanReturnCorrectViewWithModels() throws Exception {
        Teacher teacher = new Teacher(2L, "Teacher Name");
        teacher.setEmail("test_test@gmail.com");
        teacher.setPassword("1234");
        teacher.setBirthdate(LocalDate.now());

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);

        mockMvc.perform(post("/admin/add_teacher")
                        .param("name", teacher.getName())
                        .param("email", teacher.getEmail())
                        .param("password", teacher.getPassword())
                        .param("birthdate", teacher.getBirthdate().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teachers"))
                .andExpect(flash().attribute("message", "Teacher '" + teacher.getName() + "' was added."));
        verify(teacherService).addOne(teacher);
    }

    @Test
    public void whenPostEditTeacherShouldUpdateTeacherInDbThanReturnCorrectViewWithModels() throws Exception {
        Teacher teacher = new Teacher(2L, "Teacher Name");
        teacher.setEmail("test_test@gmail.com");
        teacher.setPassword("1234");
        teacher.setBirthdate(LocalDate.now());

        when(teacherService.findById(teacher.getId())).thenReturn(teacher);

        mockMvc.perform(post("/admin/edit_teacher")
                        .param("id", teacher.getId().toString())
                        .param("name", teacher.getName())
                        .param("email", teacher.getEmail())
                        .param("password", teacher.getPassword())
                        .param("birthdate", teacher.getBirthdate().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/teachers/teacher?id=" + teacher.getId()))
                .andExpect(flash().attribute("message", "Teacher updated."))
                .andExpect(model().attributeExists("id"));
        verify(teacherService).update(teacher.getId(), teacher);
    }

    @Test
    public void whenGetDeleteTeacherShouldRemoveTeacherFromDBThenReturnCorrectView() throws Exception {
        when(teacherService.findById(1L)).thenReturn(new Teacher(1L, "Teacher Name", Set.of(Role.USER)));

        mockMvc.perform(get("/admin/delete_teacher")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/teachers"))
                .andExpect(model().attribute("message", "Teacher Teacher Name deleted"));
        verify(teacherService).deleteById(1L);
    }

    @Test
    public void whenGetEditTeacherShouldReturnCorrectViewWithModels() throws Exception {
        Teacher teacher = new Teacher();
        when(teacherService.findById(1L)).thenReturn(teacher);

        mockMvc.perform(get("/admin/edit_teacher")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("teachers_views/teacher_edit"))
                .andExpect(model().attribute("teacher", teacher))
                .andExpect(model().attributeExists("allRoles"));
    }

    @Test
    public void whenGetAddTeacherShouldReturnCorrectViewWithModels() throws Exception {
        Teacher teacher = new Teacher();
        mockMvc.perform(get("/admin/add_teacher"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("teachers_views/teacher_add"))
                .andExpect(model().attribute("teacher", teacher))
                .andExpect(model().attributeExists("allRoles"));
    }
}