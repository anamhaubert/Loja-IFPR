package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.connection.ConnectionFactory;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseException;
import br.edu.ifpr.foz.ifprstore.models.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {
    Connection connection;

    public DepartmentRepository(){
        connection = ConnectionFactory.getConnection();
    }

    public void insert(Department department){
        String sql = "INSERT INTO department (Name) VALUES (?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,department.getName());
            int rowInserted = statement.executeUpdate();

            if(rowInserted > 0){
                ResultSet id = statement.getGeneratedKeys();
                System.out.println("Row Inserted : " + rowInserted);
                id.next();
                department.setId(id.getInt(1));
            }

        }catch (Exception e){
            throw new DatabaseException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
    }

    public void updateName(Integer departmentId, String name){
        String sql = "UPDATE department SET Name = ? WHERE Id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setInt(2,departmentId);

            int updateRow = statement.executeUpdate();
            if(updateRow > 0){
                System.out.println("Row Updated : " + updateRow);
            }

        }catch(Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
    }

    public void delete(int id){
        String sql = "DELETE FROM department WHERE Id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
    }

    public List<Department> getAll(){
        List<Department> departments = new ArrayList<Department>();
        ResultSet resultSet;
        Department department;
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM department");
            while(resultSet.next()){
                department = new Department();
                department.setId(resultSet.getInt("Id"));
                department.setName(resultSet.getString("Name"));
                departments.add(department);
            }
            resultSet.close();
            return departments;

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
    }

    public Department getDeparmentById(int id){
        Department department;
        String sql= "SELECT * FROM department WHERE Id = ? ";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                department = instantiateDepartment(resultSet);
            }else{
                throw new DatabaseException("O departamento não foi encontrado");
            }

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return department;
    }

    public List<Department> getDepartmentByNameText(String name){
        Department department;
        ResultSet resultSet;
        List<Department> departmentList = new ArrayList<Department>();
        String sql= "SELECT * FROM department WHERE name LIKE ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%"+name+"%");

            resultSet = statement.executeQuery();
            while(resultSet.next()){
                department = new Department();
                department.setId(resultSet.getInt("Id"));
                department.setName(resultSet.getString("Name"));
                departmentList.add(department);
            }

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return departmentList;
    }

    private static Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();

        department.setName(resultSet.getString("Name"));
        department.setId(resultSet.getInt("Id"));

        return department;
    }

    public List<Department> getDepartmentsWithNoSellers(){
        Department department;
        ResultSet resultSet;
        List<Department> departmentList = new ArrayList<Department>();
        String sql= "SELECT department.id, department.name FROM department LEFT JOIN seller\n"+
                "ON seller.DepartmentId = department.Id WHERE seller.id IS NULL";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);

            resultSet = statement.executeQuery();
            while(resultSet.next()){
                department = instantiateDepartment(resultSet);
                departmentList.add(department);
            }

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return departmentList;
    }
}