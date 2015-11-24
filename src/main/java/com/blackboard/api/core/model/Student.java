package com.blackboard.api.core.model;

/**
 * The Student Model Class.
 * <p/>
 * To simplify DB structure this class extends the User class so that, using polymorphism, we can
 * <p/>
 * Created by ChristopherLicata on 11/14/15.
 */
public class Student
        extends User
{
    private double gpa;


    /**
     * The partial  constructor of the Student model Class.
     *
     * @param firstName The student's first name
     * @param lastName  The student's last name
     * @param email     The student's email/username
     * @param password  The student's unencrypted password
     * @param schoolId  The student's home institution
     */
    public Student(String firstName, String lastName, String email, String password, int schoolId)
    {
        super(firstName, lastName, email, password, schoolId);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setSchoolId(schoolId);
    }


    /**
     * The partial  constructor of the Student model Class.
     *
     * @param firstName The student's first name
     * @param lastName  The student's last name
     * @param email     The student's email/username
     * @param password  The student's unencrypted password
     * @param schoolId  The student's home institution
     * @param gpa       The student's grade point average
     */
    public Student(String firstName, String lastName, String email, String password, int schoolId, double gpa)
    {
        super(firstName, lastName, email, password, schoolId);

        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setSchoolId(schoolId);
        this.setGpa(gpa);
    }


    public double getGpa()
    {
        return gpa;
    }


    public void setGpa(double gpa)
    {
        this.gpa = gpa;
    }


    static public Student createStudent(
            String firstName, String lastName, String email, String pw, int
            schoolId, double gpa)
    {
        return new Student(firstName, lastName, email, encryptPassword(pw), schoolId, gpa);
    }

}
