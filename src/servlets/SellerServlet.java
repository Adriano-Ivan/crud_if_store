package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.DepartmentDao;
import dao.SellerDao;
import models.Department;
import models.Seller;

@WebServlet(urlPatterns={"/sellers/listar","/sellers/listarPorId","/sellers/listarPorDep","/sellers/deletar",
		"/sellers/editar_campo", "/sellers/inserir_campo",
		"/sellers/editar",
		"/sellers/inserir"
})
public class SellerServlet extends HttpServlet{

	private SellerDao dao;
	private DepartmentDao daoDep;
	public SellerServlet() {
		this.dao = DaoFactory.createSellerDao();
		this.daoDep = DaoFactory.createDepartmentDao();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String funcionalidade = req.getServletPath().split("/")[2];
		System.out.println(req.getServletPath());
		System.out.println(funcionalidade);
		System.out.println(req.getServletPath());
		String str = "/seller.jsp";
		switch(funcionalidade.toLowerCase()) {
		case "listar":
			req.setAttribute("for_dep", this.daoDep.findAll());
			req.setAttribute("listaSellers", this.dao.findAll());
			break;
		case "listarporid":
			Integer id = Integer.parseInt(req.getParameter("id"));
			Seller s = dao.findById(id);
			List<Seller> seller = new ArrayList<Seller>();
			seller.add(s);
			req.setAttribute("for_dep", this.daoDep.findAll());
			
			if(seller.get(0) == null) {
				req.setAttribute("listaSellers", null);
			} else {
				req.setAttribute("listaSellers", seller);
			}
			
			break;
		case "listarpordep":
			Integer idD = Integer.parseInt(req.getParameter("id_dep"));
			Department dep = new Department(idD, null);
			req.setAttribute("for_dep", this.daoDep.findAll());
			req.setAttribute("listaSellers",this.dao.findByDepartment(dep));
			break;
		case "inserir_campo":
			req.setAttribute("for_dep", this.daoDep.findAll());
			str = "/seller_insercao.jsp";
			break;
		case "editar_campo":
			req.setAttribute("for_dep",this.daoDep.findAll());
			req.setAttribute("seller",this.dao.findById(Integer.parseInt(req.getParameter("id"))));
			str= "/seller_edicao.jsp";
			break;
		}
		
		RequestDispatcher rd = req.getRequestDispatcher(str);
		rd.forward(req, resp);
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//LocalDate dataAbertura = LocalDate.parse(dataEmpresa, formatter);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String funcionalidade = req.getServletPath().split("/")[2];
		System.out.println(funcionalidade);
		
		switch(funcionalidade.toLowerCase()) {
		case "inserir":
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String nome = req.getParameter("nome");
			String email = req.getParameter("email");
			Double salarioBase = 2.0;
			try {
				salarioBase = Double.parseDouble(req.getParameter("salarioBase"));
				
				LocalDate dataNascimento = LocalDate.parse(req.getParameter("dataNascimento"), formatter);
				Integer idDep = Integer.parseInt(req.getParameter("id_dep"));
				
				Department dep = new Department(idDep, null);
				
				Seller seller = new Seller(null, nome, email, dataNascimento,salarioBase,dep);
				this.dao.insert(seller);
			} catch(Exception e) {
				req.setAttribute("invalido", true);
				req.setAttribute("for_dep", this.daoDep.findAll());
				RequestDispatcher rd = req.getRequestDispatcher("/seller_insercao.jsp");
				rd.forward(req, resp);
			}
			
		
			break;
		case "deletar":
			Integer id = Integer.parseInt(req.getParameter("id"));
			this.dao.deleteById(id);;
			break;
		case "editar":
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String nome2 = req.getParameter("nome");
			String email2 = req.getParameter("email");
			Double salarioBase2 = 2.0;
			try {
				salarioBase2 = Double.parseDouble(req.getParameter("salarioBase"));
				
				
				LocalDate dataNascimento2 = LocalDate.parse(req.getParameter("dataNascimento"), formatter2);
				Integer idDep2 = Integer.parseInt(req.getParameter("id_dep"));
				
				Department dep2 = new Department(idDep2, null);
				Integer id2 = Integer.parseInt(req.getParameter("id"));
				Seller seller2 = new Seller(id2, nome2, email2, dataNascimento2,salarioBase2,dep2);
				this.dao.update(seller2,seller2.getId());
			} catch(Exception e) {
				req.setAttribute("invalido", true);
				req.setAttribute("for_dep", this.daoDep.findAll());
				RequestDispatcher rd = req.getRequestDispatcher("/seller_insercao.jsp");
				rd.forward(req, resp);
			}

			break;
		}
		resp.sendRedirect("listar");

	}
}

//public class ListarTodosOsDepartamentos {
//	public static void main(String[] args) throws IOException {
//		 BufferedReader reader;
//		 reader = new BufferedReader(new InputStreamReader(System.in));
//		 SellerDao dao = DaoFactory.createSellerDao();
//		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		 LocalDate dataAbertura = LocalDate.parse("2010-08-15", formatter);
//		 int op = 1;
//		 while(op!=0) {
//			 System.out.println("DESEJA: ");
//			 System.out.println("1) LER");
//			 System.out.println("2) INSERIR");
//			 System.out.println("3) ATUALIZAR");
//			 System.out.println("4) APAGAR");
//			 System.out.println("5) LER POR ID");
//			 System.out.println("6) POR DEPARTAMENTO");
//			 System.out.println("OPÇÃO: ");
//			 op = Integer.parseInt(reader.readLine());
//			 if(op == 1) {
//				 List<Seller> sellers = dao.findAll();
//				 for(Seller s:sellers) {
//					 System.out.println(s);
//				 }
//			 }
//			 if(op == 2) {
//				 System.out.println("NOME: ");
//				 String nome = reader.readLine();
//				 System.out.println("EMAIL: ");
//				 String email = reader.readLine();
//				 System.out.println("SALÁRIO BASE: ");
//				 Double sb = Double.parseDouble(reader.readLine());
//				 System.out.println("NOME DO DEPARTAMENTO: ");
//				 String nd = reader.readLine().toLowerCase();
//				 int i = 0;
//				 if(nd.equals("computers")) {
//					 i = 1;
//				 }
//				 if(nd.equals("electronics")) {
//					 i = 2;
//				 }
//				 if(nd.equals("fashion")) {
//					 i = 3;
//				 }
//				 if(nd.equals("books")) {
//					 i = 4;
//				 }
//				 Department dep = new Department(i, nd);
//				 Seller seller = new Seller();
//				 seller.setBaseSalary(sb);
//				 seller.setBirthdate(dataAbertura);
//				 seller.setEmail(email);
//				 seller.setDepartment(dep);
//				 seller.setName(nome);
//				 
//				 dao.insert(seller);
//			 }
//			 if(op == 3) {
//				 
//				 System.out.println("ID:");
//				 int id  =Integer.parseInt(reader.readLine());
//				 System.out.println(dao.findById(id));
//				 System.out.println("NOME: ");
//				 String nome = reader.readLine();
//				 System.out.println("EMAIL: ");
//				 String email = reader.readLine();
//				 System.out.println("SALÁRIO BASE: ");
//				 Double sb = Double.parseDouble(reader.readLine());
//				 System.out.println("NOME DO DEPARTAMENTO: ");
//				 String nd = reader.readLine().toLowerCase();
//				 int i = 0;
//				 if(nd.equals("computers")) {
//					 i = 1;
//				 }
//				 if(nd.equals("electronics")) {
//					 i = 2;
//				 }
//				 if(nd.equals("fashion")) {
//					 i = 3;
//				 }
//				 if(nd.equals("books")) {
//					 i = 4;
//				 }
//				 Department dep = new Department(i, nd);
//				 Seller seller = new Seller();
//				 seller.setBaseSalary(sb);
//				 seller.setBirthdate(dataAbertura);
//				 seller.setEmail(email);
//				 seller.setDepartment(dep);
//				 seller.setName(nome);
//				 
//				 dao.update(seller, id);
//			 }
//			 if(op == 4) {
//				 System.out.println("ID");
//				 int id = Integer.parseInt(reader.readLine());
//				 dao.deleteById(id);
//			 }
//			 if(op == 5) {
//				 System.out.println("ID:");
//				 int id = Integer.parseInt(reader.readLine());
//				 Seller s = dao.findById(id);
//				 System.out.println(s);
//			 }
//			 if(op == 6) {
//				
//				 System.out.println("NOME DO DEPARTAMENTO: ");
//				 String nd = reader.readLine().toLowerCase();
//				 int i = 0;
//				 if(nd.equals("computers")) {
//					 i = 1;
//				 }
//				 if(nd.equals("electronics")) {
//					 i = 2;
//				 }
//				 if(nd.equals("fashion")) {
//					 i = 3;
//				 }
//				 if(nd.equals("books")) {
//					 i = 4;
//				 }
//				 Department dep = new Department(i, nd);
//				 List<Seller> s = dao.findByDepartment(dep);
//				 for(Seller sl:s) {
//					 System.out.println(sl);
//				 }
//				 
//			 }
//		 }
//		
//		System.out.println("exit");
//		//System.out.println(dao.findById(1));
//	}
//}