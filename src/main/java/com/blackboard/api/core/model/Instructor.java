package com.blackboard.api.core.model;

/**
 * This is the Instructor Model Class that corresponds to the Instructor State of the Users table in the database.
 * <p/>
 * Created by ChristopherLicata on 11/14/15.
 */
public class Instructor
        extends User
{

    /**
     * The Full constructor of the Instructor Class.
     *
     * @param firstName  The instructor's first name
     * @param lastName   The instructor's last name
     * @param email      The instructor's email/username
     * @param password   The instructor's unencrypted password
     * @param schoolName The instructor's home institution
     * @implNote This constructor sets the isStudent variable to false in its parent class {@link com.blackboard.api.core.model.User}
     */
    public Instructor(String firstName, String lastName, String email, String password, String schoolName)
    {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setSchoolName(schoolName);
    }

}
