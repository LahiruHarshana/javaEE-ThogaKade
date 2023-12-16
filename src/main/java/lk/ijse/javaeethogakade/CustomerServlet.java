package lk.ijse.javaeethogakade;

import lk.ijse.javaeethogakade.db.DBConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "customerServlet", value = "/customer")
public class CustomerServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = null;
        try {
            String id = request.getParameter("cusID");
            String name = request.getParameter("cusName");
            String address = request.getParameter("cusAddress");
            String salary = request.getParameter("cusSalary");

            connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO customer (cusID, cusName, cusAddress,cusSalary) VALUES (?, ?, ?,?)");

            stm.setString(1, id);


        }catch (SQLException | ClassNotFoundException e){

        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Handle GET request logic here
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }
}