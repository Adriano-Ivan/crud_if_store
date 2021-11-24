package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connection.ConnectionFactory;
import exceptions.DatabaseException;
import models.Department;
import models.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection connection;
	
	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}
	public void insert(Seller seller) {
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
		PreparedStatement st = connection.prepareStatement(""
				+ "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)"
				+ "VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
		st.setString(1, seller.getName());
		st.setString(2, seller.getEmail());
		st.setDate(3, Date.valueOf(seller.getBirthdate()));
		st.setDouble(4, 10000);
		st.setInt(5, seller.getDepartment().getId());
		
		int rowsAffected = st.executeUpdate();
		
		System.out.println("linhas afetadas: " + rowsAffected);
		
		//Obter os ids ou id inseridos
		ResultSet ids = st.getGeneratedKeys();
		
		ids.next();
		Integer id = ids.getInt(1);
		
		System.out.println("O id inserido foi: " + id);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void update(Seller seller,Integer id) {
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			String sql = "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE id = ?";
			
			statement = connection.prepareStatement(sql);
			
			statement.setString(1, seller.getName());
			statement.setString(2,  seller.getEmail());
			statement.setDate(3, Date.valueOf(seller.getBirthdate()));
			statement.setDouble(4, seller.getBaseSalary());
			statement.setInt(5, seller.getDepartment().getId());
			
			statement.setInt(6, id);
			int rows = statement.executeUpdate();
			
			System.out.println("Linhas afetadas: "+ rows);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			String sql = "DELETE FROM seller WHERE id = ?";
			
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, id);
			
			int rows = statement.executeUpdate();
			
			System.out.println("Linhas afetadas: "+ rows);
		} catch(SQLException e){
			throw new DatabaseException(e.getMessage());
		} finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
			
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet result = null;	
		Seller seller = null;
		try {
			String sql = "SELECT seller.*,department.Name as DepName FROM seller " + 
					" INNER JOIN department ON seller.DepartmentId = department.Id " + 
					"WHERE seller.Id = ?";
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1,id);
			
			result=statement.executeQuery();
			
			if(result.next()) {
				
				Department department = new Department();
				
				department.setId(result.getInt("DepartmentId"));
				
				department.setName(result.getString("DepName"));
				
				seller=createSeller(result,department);
				
			}
		}catch(SQLException e) {
			throw new DatabaseException(e.getMessage()+"HEY");
		}finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
			
		}
		
		return seller;
	
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement statement = null;
		ResultSet result = null;	
		List<Seller> sellers = new ArrayList<Seller>();
		try {
			String sql = "SELECT seller.*,department.Name as DepName FROM seller " + 
					" INNER JOIN department ON seller.DepartmentId = department.Id " + 
					"";
			statement = connection.prepareStatement(sql);
			
			result=statement.executeQuery();
			
			while(result.next()) {
				
				Department department = createDepartment(result);
				
				Seller seller=createSeller(result,department);
				
				sellers.add(seller);
				
			}
		}catch(SQLException e) {
			throw new DatabaseException(e.getMessage()+"HEY");
		}finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
			
		}
		
		return sellers;
	}
	@Override
	public List<Seller> findByDepartment(Department dep) {
		PreparedStatement statement = null;
		ResultSet result = null;	
		List<Seller> sellers =new ArrayList<Seller>();
		try {
			String sql = "SELECT seller.*,department.Name as DepName FROM seller " + 
					"INNER JOIN department ON seller.DepartmentId = department.Id" + 
					" WHERE DepartmentId = ? ORDER BY Name";
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1,dep.getId());
			
			result=statement.executeQuery();
			
			Map<Integer,Department> map = new HashMap<Integer,Department>();
			
			//	Map<Integer, Department>
			
			while(result.next()) {
				Department department = map.get(result.getInt("DepartmentId"));
				if(department== null) {
					department = createDepartment(result);
					map.put(result.getInt("DepartmentId"),department);
				}
				
				Seller seller=createSeller(result,department);
				//System.out.println(department);
				sellers.add(seller);
				
			}
		}catch(SQLException e) {
			throw new DatabaseException(e.getMessage()+"HEY");
		}finally {
			ConnectionFactory.closeStatement(statement);
			ConnectionFactory.closeResultSet(result);
		
		}
		
		return sellers;
	
	}
	private Seller createSeller(ResultSet result,Department department) throws SQLException {
		Seller seller=new Seller();
		//System.out.println(result.getDate("BirthDate"));
		seller.setId(result.getInt("Id"));
		
		seller.setName(result.getString("Name"));
		
		seller.setEmail(result.getString("Email"));
		
		seller.setBirthdate(result.getDate("BirthDate").toLocalDate());
		
		seller.setBaseSalary(result.getDouble("BaseSalary"));
		
		seller.setDepartment(department);
		
		return seller;
	}
	private Department createDepartment(ResultSet result) throws SQLException {
		Department department= new Department();
		
		department.setId(result.getInt("DepartmentId"));
		
		department.setName(result.getString("DepName"));
		
		return department;
	}
}
