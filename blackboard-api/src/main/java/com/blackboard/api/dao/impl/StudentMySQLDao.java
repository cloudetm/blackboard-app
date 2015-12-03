package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Student;
import com.blackboard.api.dao.impl.interfaces.StudentDao;
import com.blackboard.api.dao.util.MySQLDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blackboard.api.dao.util.MySQLDao.printSQLException;

/**
 * The MySQL implementation of Create and Retrieve functions on the StudentDao Objects.
 * <p>
 * Created by ChristopherLicata on 11/19/15.
 */
public class StudentMySQLDao
        implements StudentDao
{
    private MySQLDao dao;


    public StudentMySQLDao(MySQLDao dao)
    {
        this.dao = dao;
    }


    /**
     * Creates Student in database without the Student's gpa.
     *
     * @param student The student object that will be created in the DB
     *
     * @return student  The model object representation of student that was created in the DB
     */
    @Override
    public Student createStudent(Student student)
    {
        String query = new StringBuilder()
                .append("INSERT INTO users(fname, lname, email, password, school_id, is_student) VALUES")
                .append("(?, ?, ?, ?, ?, ?)").toString();
        String fname = student.getFirstName();
        String lname = student.getLastName();
        String email = student.getEmail();
        String pw = student.getPassword();
        int school_id = student.getSchoolId();
        Optional<ResultSet> studentId = dao.update(query, fname, lname, email, pw, school_id, 1);
        try
        {
            if (studentId.get().next())
            {
                // Assign the row id to the assignmentId for easy access
                student.setUserId(studentId.get().getInt(1));
                // Closing result set for good form.
                studentId.get().close();
            }

        }
        catch (SQLException e)
        {
            printSQLException(e);

        }
        return student;
    }


    /**
     * Finds individual Student in database whose email matches that of the argument passed to the WHERE
     * clause of the MySQL query.
     *
     * @param studentEmail The email of the student for whom it is searching.
     *
     * @return new Student(...) the Student found in the database
     */
    @Override
    public Optional<Student> findStudentByEmail(String studentEmail)
    {
        String q = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id, gpa FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();
        return dao.query(q, studentEmail, 1).flatMap(result -> {
            try
            {
                if (result.next())
                {
                    int userId = result.getInt("userID");
                    String fname = result.getString("fname");
                    String lname = result.getString("lname");
                    String email = result.getString("email");
                    String pw = result.getString("password");
                    int schoolId = result.getInt("school_id");
                    double gpa = result.getDouble("gpa");
                    return Optional.of(new Student(userId, fname, lname, email, pw, schoolId, gpa));
                }
                else
                    return Optional.empty();
            }
            catch (SQLException e)
            {
                printSQLException(e);
                return Optional.empty();
            }
        });
    }


    /**
     * Find all Students in database.
     *
     * @return students  List of Students that are present in the users table of the database.
     */
    @Override
    public List<Student> findAllStudents()
    {
        String q = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id, gpa FROM users ")
                .append("WHERE is_student = ?")
                .toString();
        try
        {
            ResultSet result = dao.query(q, 1).get();

            ArrayList<Student> students = new ArrayList<>();
            while (result.next())
            {
                int userId = result.getInt("userID");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String email = result.getString("email");
                String pw = result.getString("password");
                int schoolId = result.getInt("school_id");
                double gpa = result.getDouble("gpa");
                students.add(new Student(userId, fname, lname, email, pw, schoolId, gpa));
            }
            return students;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }
    }


    @Override
    public List<Student> findStudentsByCourseId(int courseId)
    {

        String q = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, gpa, users.school_id FROM users")
                .append(" JOIN transcripts ON users.email = transcripts.student_email JOIN courses ON ")
                .append("transcripts.course_id = courses.course_id WHERE transcripts.course_id = ?")
                .toString();
        try
        {
            ResultSet result = dao.query(q, courseId).get();

            ArrayList<Student> students = new ArrayList<>();
            while (result.next())
            {
                int userId = result.getInt("userID");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String email = result.getString("email");
                String pw = result.getString("password");
                int schoolId = result.getInt("school_id");
                double gpa = result.getDouble("gpa");
                students.add(new Student(userId, fname, lname, email, pw, schoolId, gpa));
            }
            return students;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }
    }

}






























