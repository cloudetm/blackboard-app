package com.blackboard.api.core.model;

import org.mindrot.jbcrypt.BCrypt;

import java.security.Principal;

/**
 * This is the User Model Class that maps to the users table in the database.
 * <p>
 * Created by ChristopherLicata on 11/14/15.
 */
public class User
        implements Principal
{
    private int userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private int schoolId;


    public User(String firstName, String lastName, String email, String password, int schoolId)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.schoolId = schoolId;
    }


    public User(int userId, String fname, String lname, String email, String encryptedPassword, int schoolId)
    {
        this.userId = userId;
        firstName = fname;
        lastName = lname;
        this.email = email;
        password = encryptedPassword;
        this.schoolId = schoolId;
    }


    public String getFirstName()
    {
        return firstName;
    }


    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }


    public String getLastName()
    {
        return lastName;
    }


    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }


    public String getEmail()
    {
        return email;
    }


    public void setEmail(String email)
    {
        this.email = email;
    }


    public String getPassword()
    {
        return password;
    }


    public void setPassword(String password)
    {
        this.password = password;
    }


    public int getSchoolId()
    {
        return schoolId;
    }


    public void setSchoolId(int schoolId)
    {
        this.schoolId = schoolId;
    }


    public int getUserId()
    {
        return userId;
    }


    public void setUserId(int userId)
    {
        this.userId = userId;
    }


    /**
     * If you were to simply encrypt passwords, a breach of security of your application could allow a
     * malicious user to trivially learn all user passwords. If you hash (or, like I did here, salt and hash)
     * passwords, the user needs to crack passwords (which is computationally expensive on bcrypt) to gain
     * that knowledge.
     *
     * @param pw The plaintext password to hash and salt
     *
     * @return The hashed and salted password
     */
    static public String encryptPassword(String pw)
    {
        return BCrypt.hashpw(pw, BCrypt.gensalt());
    }


    /**
     * Check that a plaintext password matches a previously hashed one.  This may be used to validate
     * passwords if you wanted to reset your password on the client side
     *
     * @param plaintext The plaintext password to verify
     * @param user      The User Object containing the previously-hashed password
     *
     * @return True if the passwords match, false otherwise
     */
    static public boolean validatePassword(String plaintext, User user)
    {
        return BCrypt.checkpw(plaintext, user.getPassword());
    }


    public String getRole()
    {

        return email.equals("cmlicata@gwmail.gwu.edu") ? "ADMIN" : "USER";
    }


    @Override
    public String getName()
    {
        return email;
    }

}
