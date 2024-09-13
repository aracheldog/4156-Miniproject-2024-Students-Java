package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains the unit tests for the Course class.
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void courseFullTest() {
    testCourse.setEnrolledStudentCount(250);
    assertEquals(true, testCourse.isCourseFull());
  }

  @Test
  public void courseNotFullTest() {
    testCourse.setEnrolledStudentCount(120);
    assertEquals(false, testCourse.isCourseFull());
  }

  @Test
  public void enrollmentTest() {
    testCourse.setEnrolledStudentCount(0);
    assertEquals(true, testCourse.enrollStudent());
  }

  @Test
  public void enrollmentTestFalse() {
    testCourse.setEnrolledStudentCount(500);
    assertEquals(false, testCourse.enrollStudent());
  }

  @Test
  public void dropStudentTest() {
    testCourse.setEnrolledStudentCount(100);
    assertEquals(true, testCourse.dropStudent());
  }

  @Test
  public void dropStudentFailedTest() {
    testCourse.setEnrolledStudentCount(0);
    assertEquals(false, testCourse.dropStudent());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}
