package dev.coms4156.project.individualproject;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * This class contains the unit tests for the RouteController class.
 */
@WebMvcTest(RouteController.class)
public class RouteControllerTests {
  public static HashMap<String, Department> departmentMapping;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MyFileDatabase myFileDatabase;

  @BeforeAll
  public static void setupCourseForTesting() {
    MyFileDatabase db = new MyFileDatabase(0, "./src/test/resources/data.txt");
    departmentMapping = db.getDepartmentMapping();
  }

  @Test
  public void indexTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(containsString(
            "Welcome")));
  }

  @Test
  public void retrieveDeptTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    String expectedResult = "COMS 3827: \n"
        + "Instructor: Daniel Rubenstein; Location: 207 Math; Time: 10:10-11:25\n"
        + "COMS 1004: \n"
        + "Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:55\n"
        + "COMS 3203: \n"
        + "Instructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25\n"
        + "COMS 4156: \n"
        + "Instructor: Gail Kaiser; Location: 501 NWC; Time: 10:10-11:25\n"
        + "COMS 3157: \n"
        + "Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25\n"
        + "COMS 3134: \n"
        + "Instructor: Brian Borowski; Location: 301 URIS; Time: 4:10-5:25\n"
        + "COMS 3251: \n"
        + "Instructor: Tony Dear; Location: 402 CHANDLER; Time: 1:10-3:40\n"
        + "COMS 3261: \n"
        + "Instructor: Josh Alman; Location: 417 IAB; Time: 2:40-3:55\n";
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveDept?deptCode=COMS")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(expectedResult));
  }

  @Test
  public void retrieveDeptNotFoundTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveDept?deptCode=CS")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void retrieveCourseTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveCourse?deptCode=COMS&courseCode=3157")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string("\n" + "Instructor: Jae Lee; Location: 417 IAB; Time: 4:10-5:25"));
  }

  @Test
  public void retrieveCourseFailedTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveCourse?deptCode=CS&courseCode=3157")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.get("/retrieveCourse?deptCode=COMS&courseCode=31570")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void getMajorCountFromDeptTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.get("/getMajorCountFromDept?deptCode=COMS")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "2700")));
  }

  @Test
  public void identifyDeptChairTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.get("/idDeptChair?deptCode=COMS")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "Luca Carlon")));
  }

  @Test
  public void findCourseLocationTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.get("/findCourseLocation?deptCode=COMS&courseCode=3157")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "417 IAB")));
  }

  @Test
  public void findCourseInstructorTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.get(
        "/findCourseInstructor?deptCode=COMS&courseCode=3157")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "Jae Lee")));
  }

  @Test
  public void findCourseTimeTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.get("/findCourseTime?deptCode=COMS&courseCode=3157")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "4:10-5:25")));
  }

  @Test
  public void addMajorToDepTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.patch("/addMajorToDept?deptCode=COMS")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "Attribute was updated successfully")));
  }

  @Test
  public void removeMajorToDepTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.patch("/removeMajorFromDept?deptCode=ECON")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "Attribute was updated or is at minimum")));
  }

  @Test
  public void dropStudentFromCourseTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.patch(
        "/dropStudentFromCourse?deptCode=COMS&courseCode=3157")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "Student has been dropped")));
  }

  @Test
  public void setEnrollmentCountTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.patch(
        "/setEnrollmentCount?deptCode=COMS&courseCode=1004&count=200")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "Attributed was updated successfully")));
  }

  @Test
  public void changeCourseTimeTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.patch(
        "/changeCourseTime?deptCode=IEOR&courseCode=2500&time=9:30-10:45")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "Attributed was updated successfully")));
  }

  @Test
  public void changeCourseTeacherTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc.perform(MockMvcRequestBuilders.patch(
        "/changeCourseTeacher?deptCode=IEOR&courseCode=2500&teacher=hello")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "Attributed was updated successfully")));
  }

  @Test
  public void changeCourseLocationTest() throws Exception {
    when(myFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);
    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(
                "/changeCourseLocation?deptCode=IEOR&courseCode=2500&location=new_location")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content()
            .string(containsString(
                "Attributed was updated successfully")));
  }

}
