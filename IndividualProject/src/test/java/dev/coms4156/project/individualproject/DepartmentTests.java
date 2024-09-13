package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains the unit tests for the Department class.
 */
@SpringBootTest
@ContextConfiguration
public class DepartmentTests {
  /**
   * Sets up the test environment for course class related tests.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    MyFileDatabase db = new MyFileDatabase(0, "./src/test/resources/data.txt");
    Map<String, Department> departmentMapping = db.getDepartmentMapping();
    testDepartment = departmentMapping.get("COMS");
    testDepartment2 = departmentMapping.get("ECON");
  }

  @Test
  public void toStringTest() {
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
    assertEquals(expectedResult, testDepartment.toString());
  }

  @Test
  public void numberOfMajorsTest() {
    assertEquals(2700, testDepartment.getNumberOfMajors());
  }

  @Test
  public void numberOfMajorsCalculationTest() {
    for (int i = 0; i < 2345; i++) {
      testDepartment2.dropPersonFromMajor();
    }
    testDepartment2.dropPersonFromMajor();
    testDepartment2.addPersonToMajor();
    assertEquals(1, testDepartment2.getNumberOfMajors());
  }

  @Test
  public void departmentChairTest() {
    assertEquals("Luca Carloni", testDepartment.getDepartmentChair());
  }

  @Test
  public void addCourseTest() {
    Course testCourse = new Course("test course", "417 IAB", "11:40-12:55", 250);
    testDepartment2.addCourse("test", testCourse);
    assertEquals(testCourse, testDepartment2.getCourseSelection().get("test"));
  }

  /** The test course instance used for testing. */
  public static Department testDepartment;
  public static Department testDepartment2;
}
