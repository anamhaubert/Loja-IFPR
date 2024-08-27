package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.models.Department;
import br.edu.ifpr.foz.ifprstore.models.Seller;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class DepartmentRepositoryTest {

    @Test
    public void deveInserirUmRegistroNaTabelaDepartment(){
        DepartmentRepository repository = new DepartmentRepository();

        Department departmentFake = new Department();
        departmentFake.setName("Comunications");

        repository.insert(departmentFake);

    }

    @Test
    public void deveAtualizarONomeDeUmDepartamento(){

        DepartmentRepository repository = new DepartmentRepository();

        repository.updateName(5,"IT Suport");

    }

    @Test
    public void deveDeletarUmDepartment(){

        DepartmentRepository repository = new DepartmentRepository();
        repository.delete(5);

    }

    @Test
    public void deveExibirUmaListaDeDepartaments(){

        DepartmentRepository repository = new DepartmentRepository();

        List<Department> departments = repository.getAll();

        for (Department d: departments) {
            System.out.println(d);
        }
    }

    @Test
    public void deveRetornarUmDeparmentPeloId(){

        DepartmentRepository repository = new DepartmentRepository();
        Department department = repository.getDeparmentById(1);

        System.out.println(department);
        System.out.println("Departamento: --------");
        System.out.println(department.getName());

    }

    @Test
    public void deveRetornarDepartmentsComOMesmoTextoNoNome(){
        DepartmentRepository repository = new DepartmentRepository();

        List<Department> departments = repository.getDepartmentByNameText("Com");
        System.out.println(departments);
    }

    @Test
    public void deveRetornarUmaListaDeDepartamentsSemNenhumSellerAssociado(){
        DepartmentRepository repository = new DepartmentRepository();
        List<Department> departments = repository.getDepartmentsWithNoSellers();
        System.out.println(departments);
    }
}
