package app.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import app.entity.Company;
import app.entity.Manager;
import app.exception.UnauthorizedException;
import app.repository.CompanyRepository;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest
{
    @Mock
    CompanyRepository companyRepository;
    CompanyService companyService;

    @BeforeEach
    void init()
    {
        companyService=new CompanyService(companyRepository);
    }
    @Test
    void testFindAll()
    {
        List<Company> companies=List.of(new Company());
        Mockito.when(companyRepository.findAll()).thenReturn(companies);
        Assertions.assertEquals(companies,companyService.findAll());
    }
    @Test
    void testFindById()
    {
        Company company=new Company();
        Mockito.when(companyRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(company));
        Assertions.assertEquals(company,companyService.findById(1L));
    }
    @Test
    void testFindById_throwsException()
    {
        Mockito.when(companyRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->companyService.findById(1L));
    }
    @Test
    void testThrowExceptionIfNotSameManager_sameManager_notThrows()
    {
        Manager manager=new Manager();
        manager.setId(1L);
        Company company=new Company();
        CompanyService companyService2=Mockito.spy(companyService);
        Mockito.doReturn(company).when(companyService2).findById(Mockito.anyLong());
        company.setManager(manager);
        Assertions.assertDoesNotThrow(()->companyService2.throwExceptionIfNotSameManager(1L,manager));
    }
    @Test
    void testThrowExceptionIfNotSameManager_differentManager_throws()
    {
        Manager manager=new Manager();
        manager.setId(1L);
        Manager manager2=new Manager();
        manager.setId(2L);
        Company company=new Company();
        CompanyService companyService2=Mockito.spy(companyService);
        Mockito.doReturn(company).when(companyService2).findById(Mockito.anyLong());
        company.setManager(manager2);
        Assertions.assertThrows(UnauthorizedException.class,()->companyService2.throwExceptionIfNotSameManager(1L,manager));
    }
    @Test
    void testDeleteById()
    {
        companyService.deleteById(1L);
        Mockito.verify(companyRepository,Mockito.times(1)).deleteById(Mockito.anyLong());
    }
    @Test
    void testSave()
    {
        Company company=new Company();
        Mockito.when(companyRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Company.class);
        });
        Company actual=companyService.save(company);
        Assertions.assertEquals(actual,company);
    }
    @Test
    void testUpdate()
    {
        Company company=new Company();
        Mockito.when(companyRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Company.class);
        });
        Company actual=companyService.update(company);
        Assertions.assertEquals(actual,company);
    }
}