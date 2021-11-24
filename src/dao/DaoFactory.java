package dao;

import connection.ConnectionFactory;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		SellerDao dao = new SellerDaoJDBC(ConnectionFactory.getConnection());
		
		return dao;
	}
	
	public static DepartmentDao createDepartmentDao() {
		DepartmentDao dao2 = new DepartmentDaoJDBC(ConnectionFactory.getConnection());
		
		return dao2;
	}
}
