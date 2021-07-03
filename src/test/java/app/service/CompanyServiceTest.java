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
    void testDeleteById()
    {
        companyService.deleteById(1L);
        Mockito.verify(companyRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<Company> companies=List.of(new Company(1L,"name"));
        Mockito.when(companyRepository.findAll()).thenAnswer(invocation->
        {
            return companies;
        });
        Assertions.assertEquals(companies,companyService.findAll());
    }
    @Test
    void testFindById()
    {
        Company company=new Company(1L,"name");
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
    @Test
    void testSave()
    {
        Company company=new Company("name");
        Mockito.when(companyRepository.save(Mockito.any())).thenAnswer(i->
        {
            Company input=i.getArgument(0,Company.class);
            input.setId(1L);
            return input;
        });
        Assertions.assertEquals(company,companyService.update(company));
    }
    @Test
    void testUpdate()
    {
        Company company=new Company(1L,"name");
        Mockito.when(companyRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Company.class);
        });
        Assertions.assertEquals(company,companyService.update(company));
    }
}