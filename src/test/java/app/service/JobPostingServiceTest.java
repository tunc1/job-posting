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
import app.entity.JobPosting;
import app.repository.JobPostingRepository;

@ExtendWith(MockitoExtension.class)
public class JobPostingServiceTest
{
    @Mock
    JobPostingRepository jobPostingRepository;
    @Mock
    CompanyService companyService;
    JobPostingService jobPostingService;

    @BeforeEach
    void init()
    {
        jobPostingService=new JobPostingService(jobPostingRepository,companyService);
    }
    @Test
    void testSave()
    {
        JobPosting jobPosting=new JobPosting();
        Company company=new Company();
        jobPosting.setCompany(company);
        company.setId(1L);
        Mockito.when(companyService.belongsTo(Mockito.anyLong(),Mockito.any())).thenReturn(true);
        Mockito.when(jobPostingRepository.save(Mockito.any(JobPosting.class))).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        JobPosting returned=jobPostingService.save(jobPosting,null);
        Assertions.assertEquals(jobPosting, returned);
    }
    @Test
    void testUpdate()
    {
        JobPosting jobPosting=new JobPosting();
        Company company=new Company();
        jobPosting.setCompany(company);
        company.setId(1L);
        Mockito.when(companyService.belongsTo(Mockito.anyLong(),Mockito.any())).thenReturn(true);
        Mockito.when(jobPostingRepository.save(Mockito.any(JobPosting.class))).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        JobPosting returned=jobPostingService.update(jobPosting,null);
        Assertions.assertEquals(jobPosting, returned);
    }
    @Test
    void testDeleteById()
    {
        JobPosting jobPosting=new JobPosting();
        Company company=new Company();
        jobPosting.setCompany(company);
        company.setId(1L);
        JobPostingService jobPostingService2=Mockito.spy(jobPostingService);
        Mockito.when(companyService.belongsTo(Mockito.anyLong(),Mockito.any())).thenReturn(true);
        Mockito.doReturn(jobPosting).when(jobPostingService2).findById(1L);
        jobPostingService2.deleteById(1L,null);
        Mockito.verify(jobPostingRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<JobPosting> companies=List.of(new JobPosting(1L,null,null,null,null,true));
        Mockito.when(jobPostingRepository.findAll()).thenAnswer(invocation->
        {
            return companies;
        });
        Assertions.assertEquals(companies,jobPostingService.findAll());
    }
    @Test
    void testFindById()
    {
        JobPosting jobPosting=new JobPosting(1L,null,null,null,null,true);
        Mockito.when(jobPostingRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.of(jobPosting);
        });
        Assertions.assertEquals(jobPosting,jobPostingService.findById(1L));
    }
    @Test
    void testFindById_throwsException()
    {
        Mockito.when(jobPostingRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.empty();
        });
        Assertions.assertThrows(EntityNotFoundException.class,()->jobPostingService.findById(1L));
    }
    @Test
    void testFindByCompanyId()
    {
        List<JobPosting> companies=List.of(new JobPosting(1L,null,null,null,null,true));
        Mockito.when(jobPostingRepository.findByCompanyId(Mockito.anyLong())).thenAnswer(invocation->
        {
            return companies;
        });
        Assertions.assertEquals(companies,jobPostingService.findByCompanyId(1L));
    }
    @Test
    void testQuery()
    {
        List<JobPosting> companies=List.of(new JobPosting(1L,null,null,null,null,true));
        Mockito.when(jobPostingRepository.findByTitleContaining(Mockito.anyString())).thenAnswer(invocation->
        {
            return companies;
        });
        Assertions.assertEquals(companies,jobPostingService.query("query"));
    }
}