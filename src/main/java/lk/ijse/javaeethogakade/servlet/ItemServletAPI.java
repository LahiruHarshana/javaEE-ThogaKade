package lk.ijse.javaeethogakade.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.javaeethogakade.db.DBConnection;
import lk.ijse.javaeethogakade.dto.ItemDTO;

@WebServlet(name = "itemServlet", value = "/item")
public class ItemServletAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Items");
            PrintWriter writer = resp.getWriter();
            ResultSet rst = stm.executeQuery();
            resp.addHeader("Content-Type", "application/json");
            resp.addHeader("Access-Control-Allow-Origin", "*");

            JsonArrayBuilder allItems = Json.createArrayBuilder();

            while (rst.next()) {
                String code = rst.getString(1);
                String description = rst.getString(2);
                double unitPrice = rst.getDouble(3);
                int qtyOnHand = rst.getInt(4);

                allItems.add(Json.createObjectBuilder()
                        .add("code", code)
                        .add("description", description)
                        .add("unitPrice", unitPrice)
                        .add("qtyOnHand", qtyOnHand)
                        .build());
            }
            writer.print(allItems.build());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDbConnection().getConnection()) {
            BufferedReader reader = req.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ItemDTO itemDTO = objectMapper.readValue(jsonInput.toString(), ItemDTO.class);

            PreparedStatement stm = connection.prepareStatement("INSERT INTO Items VALUES (?,?,?,?)");
            stm.setObject(1, itemDTO.getCode());
            stm.setObject(2, itemDTO.getDescription());
            stm.setObject(3, itemDTO.getUnitPrice());
            stm.setObject(4, itemDTO.getQtyOnHand());
            stm.executeUpdate();

            resp.getWriter().println("Item has been saved successfully");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDbConnection().getConnection()) {
            BufferedReader reader = req.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ItemDTO itemDTO = objectMapper.readValue(jsonInput.toString(), ItemDTO.class);

            PreparedStatement stm = connection.prepareStatement("UPDATE Items SET ItemName=?, ItemPrice=?, ItemQuantity=? WHERE ItemCode=?");
            stm.setObject(1, itemDTO.getDescription());
            stm.setObject(2, itemDTO.getUnitPrice());
            stm.setObject(3, itemDTO.getQtyOnHand());
            stm.setObject(4, itemDTO.getCode());
            stm.executeUpdate();

            resp.getWriter().println("Item has been updated successfully");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = DBConnection.getDbConnection().getConnection()) {
            BufferedReader reader = req.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ItemDTO itemDTO = objectMapper.readValue(jsonInput.toString(), ItemDTO.class);

            PreparedStatement stm = connection.prepareStatement("DELETE FROM Items WHERE ItemCode=?");
            stm.setObject(1, itemDTO.getCode());
            int affectedRows = stm.executeUpdate();

            resp.addHeader("Content-Type", "application/json");
            resp.addHeader("Access-Control-Allow-Origin", "*");

            if (affectedRows > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
