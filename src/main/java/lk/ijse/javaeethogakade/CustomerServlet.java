package lk.ijse.javaeethogakade;

import lk.ijse.javaeethogakade.db.DBConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "customerServlet", value = "/customer")
public class CustomerServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = null;
        try {


            BufferedReader reader = request.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line = null;

            while ((line = reader.readLine())!=null){
                jsonInput.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();

            connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO customer (cusID, cusName, cusAddress,cusSalary) VALUES (?, ?, ?,?)");

            stm.setString(1, id);
            stm.setString(2, name);
            stm.setString(3, address);
            stm.setDouble(4, salary);

            stm.executeUpdate();

            response.getWriter().println("Customer has been saved successfully");


        }catch (SQLException | ClassNotFoundException e){

        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}