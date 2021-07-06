package app.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import app.entity.Company;
import app.service.CompanyService;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest
{
    @Mock
    CompanyService companyService;
    CompanyController companyController;
    @BeforeEach
    void init()
    {
        companyController=new CompanyController(companyService);
    }
    @Test
    void testFindAll()
    {
        List<Company> companies=List.of(new Company(1L,"name",null));
        Mockito.when(companyService.findAll()).thenAnswer(invocation->
        {
            return companies;
        });
        Assertions.assertEquals(companies,companyController.findAll());
    }
    @Test
    void testFindById()
    {
        Company company=new Company(1L,"name",null);
        Mockito.when(companyService.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return company;
        });
        Assertions.assertEquals(company,companyController.findById(1L));
    }
}
