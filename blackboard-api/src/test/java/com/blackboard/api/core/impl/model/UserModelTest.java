package com.blackboard.api.core.impl.model;

import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.core.model.Student;
import com.blackboard.api.core.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for helper methods within the User class and its child classes
 * <p>
 * Created by ChristopherLicata on 11/21/15.
 */
public class UserModelTest
{

    private String simplePass = "password";

    private String complexPass = "chris@BMx765_10~!";

    private User student;

    private User teacher;


    @Before
    public void setUp()
    {
        student = Student.createStudent("John", "Smith", "foo@bar.com", simplePass, 1, 4.0);
        teacher = Instructor.createInstructor("Chris", "Kringle", "santa@northpole.org", complexPass,
                                              1);
    }


    /**
     * Test method for "User.validatePassword()" expecting success
     */
    @Test
    public void testValidatePasswordSuccess()
    {
        assertTrue(User.validatePassword(simplePass, student));
        assertTrue(User.validatePassword(complexPass, teacher));

    }


    /**
     * Test method for "User.validatePassword()" expecting failure
     */
    @Test
    public void testValidatePasswordFailure()
    {
        assertFalse(User.validatePassword(simplePass + "!", student));
        assertFalse(User.validatePassword(complexPass + "!", teacher));
    }

}
