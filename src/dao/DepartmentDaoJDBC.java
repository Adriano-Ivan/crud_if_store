package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import exceptions.DatabaseException;
import models.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection connection;
	
	public DepartmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}
	public void insert(Department department) {
		PreparedStatement statement = null;
		ResultSet result = null;	
		//Department department= null;
		try {
			String sql = "INSERT INTO department(name) VALUES(?);";
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1,department.getName());
			
			int rowsAffected = statement.executeUpdate();
			
			System.out.println("linhas afetadas: " + rowsAffected);
			
			//Obter os ids ou id inseridos
			ResultSet ids = statement.getGeneratedKeys();
			
			ids.next();
			Integer id = ids.getInt(1);
			
			System.out.println("O id inserido foi: " + id);

		}catch(SQLException e) {
			throw new DatabaseException(e.getMessage()+"HEY");
		}finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement statement = null;
		ResultSet result = null;	
		//Department department= null;
		try {
			String sql = "UPDATE department SET name = ? WHERE id = ?";
			statement = connection.prepareStatement(sql);
			
			statement.setString(1,department.getName());
			statement.setInt(2, department.getId());
			
			statement.executeUpdate();

		}catch(SQLException e) {
			throw new DatabaseException(e.getMessage()+"HEY");
		}finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement statement = null;
		ResultSet result = null;	
		//Department department= null;
		try {
			String sql = "DELETE FROM department WHERE id = ?";
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1,id);
			
			statement.executeUpdate();

		}catch(SQLException e) {
			throw new DatabaseException(e.getMessage()+"HEY");
		}finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
		}
		
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet result = null;	
		Department department= null;
		try {
			String sql = "SELECT * FROM department WHERE id = ?";
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1,id);
			
			result=statement.executeQuery();
			
			if(result.next()) {
				department= new Department();
				
				department.setId(result.getInt("id"));
				
				department.setName(result.getString("name"));
				
			}
		}catch(SQLException e) {
			throw new DatabaseException(e.getMessage()+"HEY");
		}finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
		}
		
		return department;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement statement = null;
		ResultSet result = null;	
		List<Department> departments= new ArrayList<Department>();
		try {
			String sql = "SELECT * FROM department";
			statement = connection.prepareStatement(sql);
			
			result=statement.executeQuery();
			
			while(result.next()) {
				Department department= new Department();
				
				department.setId(result.getInt("id"));
				
				department.setName(result.getString("name"));
				
				departments.add(department);
				
			}
		}catch(SQLException e) {
			throw new DatabaseException(e.getMessage()+"HEY");
		}finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
		}
		
		return departments;
	}

}
