package com.napier.sem;

import java.sql.*;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);

                // Connect to database
                con = DriverManager.getConnection(
                        "jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false",
                        "root",
                        "example");

                System.out.println("Successfully connected");

                break;
            }
            catch (SQLException sqle)
            {
                System.out.println(
                        "Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public Employee getEmployee(int ID)
    {
        try
        {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT * FROM employees WHERE emp_no = " + ID;

            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");


                return emp;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Get an employee
        Employee emp = a.getEmployee(10001);

        // Display employee
        System.out.println(
                emp.emp_no + " " +
                        emp.first_name + " " +
                        emp.last_name
        );

        // Disconnect from database
        a.disconnect();
    }
}
