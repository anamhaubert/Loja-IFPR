package br.edu.ifpr.foz.ifprstore.controllers;

import br.edu.ifpr.foz.ifprstore.repositories.SellerRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sellers/delete")
public class SellersDeleteController extends HttpServlet {

    SellerRepository repository = new SellerRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getParameter("id");
        int id = Integer.valueOf(req.getParameter("id"));

        repository.delete(id);

        resp.sendRedirect("http://localhost:8080/IFPRStore/sellers");
    }
}
