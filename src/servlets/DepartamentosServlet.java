package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

@WebServlet(urlPatterns={"/departamentos/listar","/departamentos/listarPorId","/departamentos/deletar","/departamentos/editar_campo",
		"/departamentos/editar",
		"/departamentos/inserir"
})
public class DepartamentosServlet extends HttpServlet{

	private DepartmentDao dao;
	public DepartamentosServlet() {
		this.dao = DaoFactory.createDepartmentDao();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String funcionalidade = req.getServletPath().split("/")[2];
		System.out.println(funcionalidade);
		
		switch(funcionalidade.toLowerCase()) {
		case "listar":
			req.setAttribute("listaDepartment", this.dao.findAll());
			break;
		case "listarporid":
			System.out.println("entrou");
			List<Department> dep = new ArrayList<Department>();
			Integer id = Integer.parseInt(req.getParameter("id"));
			dep.add(this.dao.findById(id));
			if(dep.get(0)==null) {
				System.out.println("ENTROU");
				System.out.println(this.dao.findById(id));
				req.setAttribute("listaDepartment", null);
			}else {
				System.out.println(this.dao.findById(id));
				req.setAttribute("listaDepartment", dep);
			}
			
			break;
		case "editar_campo":
			Integer id_d = Integer.parseInt(req.getParameter("id"));
			String nome = req.getParameter("nome");
			req.setAttribute("id", id_d);
			req.setAttribute("nomeDepartamento", nome);
			req.setAttribute("desejaEditar", true);
		}
		
		
		RequestDispatcher rd = req.getRequestDispatcher("/department.jsp");
		
		rd.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String funcionalidade = req.getServletPath().split("/")[2];
		System.out.println(funcionalidade);
		
		switch(funcionalidade.toLowerCase()) {
		case "inserir":
			Department d = new Department(null, req.getParameter("departamento"));
			this.dao.insert(d);
			break;
		case "deletar":
			Integer id = Integer.parseInt(req.getParameter("id"));
			this.dao.deleteById(id);;
			break;
		case "editar":
			Integer id_d = Integer.parseInt(req.getParameter("id"));
			String nome = req.getParameter("nome");
			Department d2 = new Department(id_d, nome);
			
			this.dao.update(d2);
		}
		resp.sendRedirect("listar");

	}
}
