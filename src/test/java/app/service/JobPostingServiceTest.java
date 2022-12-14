package app.service;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import app.criteria.JobPostingCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import app.entity.Company;
import app.entity.JobPosting;
import app.repository.JobPostingRepository;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
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
        Mockito.doNothing().when(companyService).throwExceptionIfNotSameManager(Mockito.anyLong(),Mockito.any());
        Mockito.when(jobPostingRepository.save(Mockito.any(JobPosting.class))).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        JobPosting returned=jobPostingService.save(jobPosting,null);
        Assertions.assertEquals(jobPosting,returned);
    }
    @Test
    void testUpdate()
    {
        JobPosting jobPosting=new JobPosting();
        Company company=new Company();
        jobPosting.setCompany(company);
        company.setId(1L);
        Mockito.doNothing().when(companyService).throwExceptionIfNotSameManager(Mockito.anyLong(),Mockito.any());
        Mockito.when(jobPostingRepository.save(Mockito.any(JobPosting.class))).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        JobPosting returned=jobPostingService.update(jobPosting,null);
        Assertions.assertEquals(jobPosting,returned);
    }
    @Test
    void testDeleteById()
    {
        JobPosting jobPosting=new JobPosting();
        Company company=new Company();
        jobPosting.setCompany(company);
        company.setId(1L);
        JobPostingService jobPostingService2=Mockito.spy(jobPostingService);
        Mockito.doNothing().when(companyService).throwExceptionIfNotSameManager(Mockito.anyLong(),Mockito.any());
        Mockito.doReturn(jobPosting).when(jobPostingService2).findById(1L);
        jobPostingService2.deleteById(1L,null);
        Mockito.verify(jobPostingRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        JobPostingCriteria criteria=new JobPostingCriteria();
        Pageable pageable=PageRequest.of(0,5,Sort.by("id"));
        Page<JobPosting> page=new PageImpl<>(List.of(new JobPosting()));
        JobPostingService jobPostingService2=Mockito.spy(jobPostingService);
        Specification<JobPosting> specification=(root,query,criteriaBuilder)->null;
        Mockito.doReturn(specification).when(jobPostingService2).createSpecification(Mockito.any());
        Mockito.when(jobPostingRepository.findAll(Mockito.any(Specification.class),Mockito.any(Pageable.class))).thenReturn(page);
        Page<JobPosting> actual=jobPostingService2.findAll(criteria,pageable);
        Assertions.assertEquals(actual,page);
    }
    @Test
    void testFindById()
    {
        JobPosting jobPosting=new JobPosting();
        Mockito.when(jobPostingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(jobPosting));
        Assertions.assertEquals(jobPosting,jobPostingService.findById(1L));
    }
    @Test
    void testFindById_throwsException()
    {
        Mockito.when(jobPostingRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->jobPostingService.findById(1L));
    }
    @Test
    void testCreateSpecification_allPresent()
    {
        JobPostingCriteria criteria=new JobPostingCriteria();
        criteria.setCompany(1);
        criteria.setCity(2);
        criteria.setTitle("title");
        Root<JobPosting> root=Mockito.mock(Root.class);
        Path<JobPosting> path=Mockito.mock(Path.class);
        Path<JobPosting> path2=Mockito.mock(Path.class);
        Mockito.doReturn(path).when(root).get(Mockito.anyString());
        Mockito.doReturn(path2).when(path).get(Mockito.anyString());
        CriteriaQuery<Object> query=Mockito.mock(CriteriaQuery.class);
        CriteriaBuilder criteriaBuilder=Mockito.mock(CriteriaBuilder.class);
        Specification<JobPosting> specification=jobPostingService.createSpecification(criteria);
        specification.toPredicate(root, query, criteriaBuilder);
        Mockito.verify(criteriaBuilder).equal(Mockito.any(),Mockito.eq(criteria.getCity()));
        Mockito.verify(criteriaBuilder).equal(Mockito.any(),Mockito.eq(criteria.getCompany()));
        Mockito.verify(criteriaBuilder).like(Mockito.any(),Mockito.eq("%"+criteria.getTitle()+"%"));
    }
    @Test
    void testCreateSpecification_nothingPresent()
    {
        JobPostingCriteria criteria=new JobPostingCriteria();
        Root<JobPosting> root=Mockito.mock(Root.class);
        CriteriaQuery<Object> query=Mockito.mock(CriteriaQuery.class);
        CriteriaBuilder criteriaBuilder=Mockito.mock(CriteriaBuilder.class);
        Specification<JobPosting> specification=jobPostingService.createSpecification(criteria);
        specification.toPredicate(root, query, criteriaBuilder);
        Mockito.verify(criteriaBuilder,Mockito.times(0)).equal(Mockito.any(),Mockito.anyLong());
        Mockito.verify(criteriaBuilder,Mockito.times(0)).like(Mockito.any(),Mockito.anyString());
    }
}