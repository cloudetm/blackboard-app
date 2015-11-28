package com.blackboard.api.core.model;

/**
 * This is the Instructor Model Class that corresponds to the Instructor State of the Users table in the
 * database.
 * <p/>
 * Created by ChristopherLicata on 11/14/15.
 */
public class Instructor
        extends User
{

    /**
     * The Full constructor of the Instructor Class.
     *
     * @param firstName The instructor's first name
     * @param lastName  The instructor's last name
     * @param email     The instructor's email/username
     * @param password  The instructor's unencrypted password
     * @param schoolId  The instructor's home institution
     */
    public Instructor(String firstName, String lastName, String email, String password, int schoolId)
    {
        super(firstName, lastName, email, password, schoolId);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setSchoolId(schoolId);
    }


    public Instructor(int userId, String fname, String lname, String email, String pw, int schoolId)
    {
        super(fname,lname,email,pw,schoolId);
        this.setUserId(userId);
    }


    static public Instructor createInstructor(
            String firstName, String lastName, String email, String pw, int
            schoolId)
    {
        return new Instructor(firstName, lastName, email, encryptPassword(pw), schoolId);
    }

}
