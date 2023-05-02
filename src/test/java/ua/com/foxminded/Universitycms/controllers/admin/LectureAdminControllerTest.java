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
import ua.com.foxminded.Universitycms.models.*;
import ua.com.foxminded.Universitycms.services.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(authorities = {"ADMIN"})
@Import(WebSecurityConfig.class)
@WebMvcTest(LectureAdminController.class)
public class LectureAdminControllerTest {
    @MockBean
    private GroupService groupService;
    @MockBean
    private LectureService lectureService;
    @MockBean
    private TeacherService teacherService;
    @MockBean
    private SubjectService subjectService;
    @MockBean
    private LectureRoomService lectureRoomService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailService userDetailsService;

    @Test
    public void whenGetAddLectureWithParamTeacherShouldReturnCorrectViewWithTeacherAttribute() throws Exception {
        Teacher teacher = new Teacher(1L, "Test teacher");
        when(teacherService.findById(1L)).thenReturn(teacher);

        Lecture lecture = new Lecture();
        lecture.setTeacher(teacher);

        mockMvc.perform(get("/admin/add_lecture")
                        .param("type", "teacher")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("lectures_views/lecture_add"))
                .andExpect(model().attribute("lecture", lecture))
                .andExpect(model().attributeExists("groups", "teachers", "rooms", "lecturesNumbers", "subjects", "type"));

        verify(teacherService).findById(1L);
    }

    @Test
    public void whenGetAddLectureWithParamGroupShouldReturnCorrectViewWithGroupAttribute() throws Exception {
        Group group = new Group(1L, "Test group");
        when(groupService.getOneGroup(1L)).thenReturn(group);

        Lecture lecture = new Lecture();
        lecture.setGroup(group);

        mockMvc.perform(get("/admin/add_lecture")
                        .param("type", "group")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("lectures_views/lecture_add"))
                .andExpect(model().attribute("lecture", lecture))
                .andExpect(model().attributeExists("groups", "teachers", "rooms", "lecturesNumbers", "subjects", "type"));

        verify(groupService).getOneGroup(1L);
    }

    @Test
    public void whenGetEditLectureShouldReturnCorrectViewWittAttributes() throws Exception {
        Lecture lecture = new Lecture();
        when(lectureService.findById(1L)).thenReturn(lecture);

        mockMvc.perform(get("/admin/edit_lecture")
                        .param("type", "none")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("lectures_views/lecture_edit"))
                .andExpect(model().attribute("lecture", lecture))
                .andExpect(model().attributeExists("groups", "teachers", "rooms", "lecturesNumbers", "subjects", "type"));

        verify(lectureService).findById(1L);
    }

    @Test
    public void whenGetDeleteLectureWithGroupParamShouldRemoveLectureFromDBThenReturnCorrectView() throws Exception {
        Group group = new Group(1L, "TEST GROUP");
        Lecture lecture = new Lecture();
        lecture.setGroup(group);
        when(lectureService.findById(1L)).thenReturn(lecture);
        when(groupService.getOneGroup(1L)).thenReturn(group);

        mockMvc.perform(get("/admin/delete_lecture")
                        .param("id", "1")
                        .param("type", "group"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/lectures/group"));
        verify(lectureService).deleteById(1L);
    }

    @Test
    public void whenGetDeleteLectureWithTeacherParamShouldRemoveLectureFromDBThenReturnCorrectView() throws Exception {
        Teacher teacher = new Teacher(1L, "TEST Teacher");
        Lecture lecture = new Lecture();
        lecture.setTeacher(teacher);
        when(lectureService.findById(1L)).thenReturn(lecture);
        when(teacherService.findById(1L)).thenReturn(teacher);


        mockMvc.perform(get("/admin/delete_lecture")
                        .param("id", "1")
                        .param("type", "teacher"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/lectures/teacher"));
        verify(lectureService).deleteById(1L);
    }

    @Test
    public void whenPostEditLectureWithGroupParamShouldUpdateLectureInDbThanReturnCorrectViewWithModels() throws Exception {
        Group testGroup = new Group(1L, "Test Group");

        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setGroup(testGroup);
        when(groupService.getOneGroup(1L)).thenReturn(testGroup);

        mockMvc.perform(post("/admin/edit_lecture")
                        .param("id", lecture.getId().toString())
                        .param("group.id", testGroup.getId().toString())
                        .param("group.groupName", testGroup.getGroupName())
                        .param("type", "group"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lectures/group?id=" + testGroup.getId() + "&days=30"))
                .andExpect(flash().attribute("message", "Lecture was updated."))
                .andExpect(model().attributeExists("id"));
        verify(lectureService).update(lecture.getId(), lecture);
    }

    @Test
    public void whenPostEditLectureWithTeacherParamShouldUpdateLectureInDbThanReturnCorrectViewWithModels() throws Exception {
        Teacher teacher = new Teacher(1L, "Test Teacher");

        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setTeacher(teacher);
        when(teacherService.findById(1L)).thenReturn(teacher);

        mockMvc.perform(post("/admin/edit_lecture")
                        .param("id", lecture.getId().toString())
                        .param("teacher.id", teacher.getId().toString())
                        .param("teacher.name", teacher.getName())
                        .param("type", "teacher"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lectures/teacher?id=" + teacher.getId() + "&days=30"))
                .andExpect(flash().attribute("message", "Lecture was updated."))
                .andExpect(model().attributeExists("id"));
        verify(lectureService).update(lecture.getId(), lecture);
    }

    @Test
    public void whenPostEditLectureShouldUpdateLectureInDbThanReturnCorrectViewWithModels() throws Exception {
        Lecture lecture = new Lecture();
        lecture.setId(1L);

        mockMvc.perform(post("/admin/edit_lecture")
                        .param("id", lecture.getId().toString())
                        .param("type", "none"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lectures/all?id=0&days=30"))
                .andExpect(flash().attribute("message", "Lecture was updated."))
                .andExpect(model().attributeExists("id"));
        verify(lectureService).update(lecture.getId(), lecture);
    }

    @Test
    public void whenPostAddLectureShouldAddLectureToDbThanReturnCorrectViewWithModels() throws Exception {
        Lecture lecture = new Lecture();
        lecture.setId(1L);

        mockMvc.perform(post("/admin/add_lecture")
                        .param("id", lecture.getId().toString())
                        .param("type", "none"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lectures/all?days=30&id=0"))
                .andExpect(flash().attribute("message", "Lecture was added."))
                .andExpect(model().attributeExists("id"));
        verify(lectureService).addOne(lecture);
    }
}