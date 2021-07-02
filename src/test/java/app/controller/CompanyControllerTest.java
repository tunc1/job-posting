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
    void testDeleteById()
    {
        companyController.deleteById(1L);
        Mockito.verify(companyService).deleteById(Mockito.any());
    }
    @Test
    void testFindAll()
    {
        List<Company> companies=List.of(new Company(1L,"name"));
        Mockito.when(companyService.findAll()).thenAnswer(invocation->
        {
            return companies;
        });
        Assertions.assertEquals(companies,companyController.findAll());
    }
    @Test
    void testFindById()
    {
        Company company=new Company(1L,"name");
        Mockito.when(companyService.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return company;
        });
        Assertions.assertEquals(company,companyController.findById(1L));
    }
    @Test
    void testSave()
    {
        Company company=new Company("name");
        Mockito.when(companyService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Company.class);
        });
        Assertions.assertEquals(company,companyController.save(company));
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        Company company=new Company("name");
        Mockito.when(companyService.update(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Company.class);
        });
        Company actual=companyController.update(company,id);
        Assertions.assertEquals(company,actual);
        Assertions.assertEquals(id,actual.getId());
    }
}
