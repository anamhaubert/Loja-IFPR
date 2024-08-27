package br.edu.ifpr.foz.ifprstore.controllers;

import br.edu.ifpr.foz.ifprstore.models.Department;
import br.edu.ifpr.foz.ifprstore.models.Seller;
import br.edu.ifpr.foz.ifprstore.repositories.DepartmentRepository;
import br.edu.ifpr.foz.ifprstore.repositories.SellerRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/sellers/create")
public class SellersCreateController extends HttpServlet {
    DepartmentRepository departmentRepository = new DepartmentRepository();
    SellerRepository repository = new SellerRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Department> departmentList = departmentRepository.getAll();

        req.setAttribute("departments", departmentList);

        req.getRequestDispatcher("/sellers-create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("field_name");
        String email = req.getParameter("field_email");
        LocalDate birthDate = LocalDate.parse(req.getParameter("field_birthDate"));
        Double salary = Double.valueOf(req.getParameter("field_salary"));
        Integer departmentId = Integer.valueOf(req.getParameter("field_department"));

        Department department = new Department();
        department.setId(departmentId);

        Seller seller = new Seller();
        seller.setName(name);
        seller.setEmail(email);
        seller.setBirthDate(birthDate);
        seller.setBaseSalary(salary);
        seller.setDepartment(department);

        repository.insert(seller);

        resp.sendRedirect("http://localhost:8080/IFPRStore/sellers");
    }
}