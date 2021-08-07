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
        List<Company> companies=List.of(new Company());
        Mockito.when(companyService.findAll()).thenReturn(companies);
        Assertions.assertEquals(companies,companyController.findAll());
    }
    @Test
    void testFindById()
    {
        Company company=new Company();
        Mockito.when(companyService.findById(Mockito.anyLong())).thenReturn(company);
        Assertions.assertEquals(company,companyController.findById(1L));
    }
    @Test
    void testDeleteById()
    {
        companyController.deleteById(1L);
        Mockito.verify(companyService,Mockito.times(1)).deleteById(Mockito.anyLong());
    }
    @Test
    void testSave()
    {
        Company company=new Company();
        Mockito.when(companyService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Company.class);
        });
        Company actual=companyController.save(company);
        Assertions.assertEquals(actual,company);
    }
    @Test
    void testUpdate()
    {
        Company company=new Company();
        long id=1L;
        Mockito.when(companyService.update(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Company.class);
        });
        Company actual=companyController.update(company,id);
        Assertions.assertEquals(actual,company);
        Assertions.assertEquals(actual.getId(),id);
    }
}
