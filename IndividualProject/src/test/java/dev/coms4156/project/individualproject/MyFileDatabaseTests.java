package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains the unit tests for the MyFileDatabase class.
 */
@SpringBootTest
@ContextConfiguration
public class MyFileDatabaseTests {
  public static MyFileDatabase testDatabase;
  public static MyFileDatabase testDatabase2;

  @BeforeAll
  public static void setupDbForTesting() {
    testDatabase = new MyFileDatabase(0, "./src/test/resources/data_db_test.txt");
    testDatabase2 = new MyFileDatabase(0, "./src/test/resources/data_db_test.txt");
  }

  @Test
  public void dbTest() {
    HashMap<String, Department> departmentMapping = testDatabase.getDepartmentMapping();
    departmentMapping.remove("ECON");
    departmentMapping.remove("IEOR");
    departmentMapping.remove("CHEM");
    departmentMapping.remove("PHYS");
    departmentMapping.remove("ELEN");
    departmentMapping.remove("PSYC");
    testDatabase2.setMapping(departmentMapping);
    testDatabase2.saveContentsToFile();

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
    assertEquals("For the COMS department: \n" + expectedResult, testDatabase.toString());
  }
}
