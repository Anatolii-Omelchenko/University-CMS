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
import ua.com.foxminded.Universitycms.models.Student;

import ua.com.foxminded.Universitycms.models.enums.Role;
import ua.com.foxminded.Universitycms.services.GroupService;
import ua.com.foxminded.Universitycms.services.StudentService;

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
@WebMvcTest(StudentAdminController.class)
public class StudentAdminControllerTest {
    @MockBean
    private StudentService studentService;

    @MockBean
    private GroupService groupService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailService userDetailsService;

    @Test
    public void whenGetDeleteStudentShouldRemoveStudentFromDBThenReturnCorrectView() throws Exception {
        when(studentService.findById(1L)).thenReturn(new Student(1L, "Student Name", Set.of(Role.USER)));

        mockMvc.perform(get("/admin/delete_student")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/students"))
                .andExpect(model().attribute("message", "Student Student Name deleted"));
        verify(studentService).deleteById(1L);
    }

    @Test
    public void whenGetEditStudentShouldReturnCorrectViewWithModels() throws Exception {
        Student student = new Student();
        when(studentService.findById(1L)).thenReturn(student);

        mockMvc.perform(get("/admin/edit_student")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("students_views/student_edit"))
                .andExpect(model().attribute("student", student))
                .andExpect(model().attributeExists("allRoles"));
    }

    @Test
    public void whenPostEditStudentShouldUpdateStudentInDbThanReturnCorrectViewWithModels() throws Exception {
        Student student = new Student(2L, "Student Name");
        student.setEmail("test_test@gmail.com");
        student.setPassword("1234");
        student.setBirthdate(LocalDate.now());

        when(studentService.findById(student.getId())).thenReturn(student);

        mockMvc.perform(post("/admin/edit_student")
                        .param("id", student.getId().toString())
                        .param("name", student.getName())
                        .param("email", student.getEmail())
                        .param("password", student.getPassword())
                        .param("birthdate", student.getBirthdate().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students/student?id=" + student.getId()))
                .andExpect(flash().attribute("message", "Student updated."))
                .andExpect(model().attributeExists("id"));
        verify(studentService).update(student.getId(), student);
    }

    @Test
    public void whenPostAddStudentShouldAddNewStudentToDbThanReturnCorrectViewWithModels() throws Exception {
        Student student = new Student(2L, "Student Name");
        student.setEmail("test_test@gmail.com");
        student.setPassword("1234");
        student.setBirthdate(LocalDate.now());

        when(studentService.findById(student.getId())).thenReturn(student);

        mockMvc.perform(post("/admin/add_student")
                        .param("name", student.getName())
                        .param("email", student.getEmail())
                        .param("password", student.getPassword())
                        .param("birthdate", student.getBirthdate().toString())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attribute("message", "Student '" + student.getName() + "' was added."));
        verify(studentService).addOne(student);
    }

    @Test
    public void whenGetAddStudentShouldReturnCorrectViewWithModels() throws Exception {
        Student student = new Student();
        mockMvc.perform(get("/admin/add_student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("students_views/student_add"))
                .andExpect(model().attribute("student", student))
                .andExpect(model().attributeExists("allRoles"));
    }
}