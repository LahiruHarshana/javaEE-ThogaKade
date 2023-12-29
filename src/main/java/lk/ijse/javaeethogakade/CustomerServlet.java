package lk.ijse.javaeethogakade;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import lk.ijse.javaeethogakade.db.DBConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.javaeethogakade.dto.CustomerDto;

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

            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }


            ObjectMapper objectMapper = new ObjectMapper();
            CustomerDto customerDto = objectMapper.readValue(jsonInput.toString(), CustomerDto.class);

            connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO customer (cusID, cusName, cusAddress,cusSalary) VALUES (?, ?, ?,?)");

            stm.setString(1, customerDto.getId());
            stm.setString(2, customerDto.getName());
            stm.setString(3, customerDto.getAddress());
            stm.setDouble(4, customerDto.getSalary());

            stm.executeUpdate();

            response.getWriter().println("Customer has been saved successfully");


        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        List<CustomerDto> customers = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                CustomerDto customer = new CustomerDto(
                        resultSet.getString("cusID"),
                        resultSet.getString("cusName"),
                        resultSet.getString("cusAddress"),
                        resultSet.getDouble("cusSalary")
                );
                customers.add(customer);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(customers);

        PrintWriter out = response.getWriter();
        out.println(json);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = null;
        try {
            BufferedReader reader = request.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }


            ObjectMapper objectMapper = new ObjectMapper();
            CustomerDto customerDto = objectMapper.readValue(jsonInput.toString(), CustomerDto.class);

            connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("UPDATE customer SET cusName=?, cusAddress=?, cusSalary=? WHERE cusID=?");

            stm.setString(1, customerDto.getName());
            stm.setString(2, customerDto.getAddress());
            stm.setDouble(3, customerDto.getSalary());
            stm.setString(4, customerDto.getId());

            stm.executeUpdate();

            response.getWriter().println("Customer has been updated successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            String customerId = request.getParameter("cusID");

            connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("DELETE FROM customer WHERE cusID=?");

            stm.setString(1, customerId);

            int affectedRows = stm.executeUpdate();

            if (affectedRows > 0) {
                response.getWriter().println("Customer has been deleted successfully");
            } else {
                response.getWriter().println("Customer not found or could not be deleted");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error in doDelete method", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}