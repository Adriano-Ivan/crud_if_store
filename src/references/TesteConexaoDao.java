package references;

import java.util.List;

import dao.DaoFactory;
import dao.DepartmentDao;
import dao.SellerDao;
import models.Department;
import models.Seller;

public class TesteConexaoDao {

	public static void main(String[] args) {
		System.out.println("================ TESTE 1 ==================");
		
		SellerDao dao = DaoFactory.createSellerDao();

		dao.deleteById(6);
		
		Seller seller = dao.findById(1);
		List<Seller> sellers = dao.findAll();
		for(Seller s:sellers) {
			System.out.println(s);
		}
		//System.out.println(seller);
		
		System.out.println("================ TESTE 2 ==================");
		
		List<Seller> sellers2 = dao.findByDepartment(new Department(1,null));
		
		
		for(Seller s:sellers2) {
			System.out.println(s);
		}
	}
}
