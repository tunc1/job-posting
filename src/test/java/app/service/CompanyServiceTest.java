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
        List<Company> companies=List.of(new Company(1L,"name",null));
        Mockito.when(companyRepository.findAll()).thenAnswer(invocation->
        {
            return companies;
        });
        Assertions.assertEquals(companies,companyService.findAll());
    }
    @Test
    void testFindById()
    {
        Company company=new Company(1L,"name",null);
        Mockito.when(companyRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.of(company);
        });
        Assertions.assertEquals(company,companyService.findById(1L));
    }
    @Test
    void testFindById_throwsException()
    {
        Mockito.when(companyRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.empty();
        });
        Assertions.assertThrows(EntityNotFoundException.class,()->companyService.findById(1L));
    }
}