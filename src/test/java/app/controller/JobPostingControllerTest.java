package app.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import app.entity.JobPosting;
import app.entity.Member;
import app.service.JobPostingService;

@ExtendWith(MockitoExtension.class)
public class JobPostingControllerTest
{
    @Mock
    JobPostingService jobPostingService;
    JobPostingController jobPostingController;
    Member member;
    Authentication authentication;
    @BeforeEach
    void init()
    {
        jobPostingController=new JobPostingController(jobPostingService);
        member=new Member();
        authentication=new UsernamePasswordAuthenticationToken(member,null);
    }
    @Test
    void testDeleteById()
    {
        jobPostingController.deleteById(1L,authentication);
        Mockito.verify(jobPostingService).deleteById(Mockito.anyLong(),Mockito.any());
    }
    @Test
    void testFindAll()
    {
        List<JobPosting> jobPostings=List.of(new JobPosting(1L,null,null,null, null, true));
        Mockito.when(jobPostingService.findAll()).thenReturn(jobPostings);
        Assertions.assertEquals(jobPostings,jobPostingController.findAll());
    }
    @Test
    void testFindById()
    {
        JobPosting jobPosting=new JobPosting(1L,null,null,null,null,true);
        Mockito.when(jobPostingService.findById(Mockito.anyLong())).thenReturn(jobPosting);
        Assertions.assertEquals(jobPosting,jobPostingController.findById(1L));
    }
    @Test
    void testSave()
    {
        JobPosting jobPosting=new JobPosting(null,null,null,null,true);
        Mockito.when(jobPostingService.save(Mockito.any(),Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        Assertions.assertEquals(jobPosting,jobPostingController.save(jobPosting,authentication));
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        JobPosting jobPosting=new JobPosting(null,null,null,null,true);
        Mockito.when(jobPostingService.update(Mockito.any(),Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        JobPosting actual=jobPostingController.update(jobPosting,id,authentication);
        Assertions.assertEquals(jobPosting,actual);
        Assertions.assertEquals(id,actual.getId());
    }
    @Test
    void testFindByCompanyId()
    {
        List<JobPosting> jobPostings=List.of(new JobPosting(1L,null,null,null, null, true));
        Mockito.when(jobPostingService.findByCompanyId(Mockito.anyLong())).thenReturn(jobPostings);
        Assertions.assertEquals(jobPostings,jobPostingController.findByCompanyId(1L));
    }
    @Test
    void testQuery()
    {
        List<JobPosting> jobPostings=List.of(new JobPosting(1L,null,null,null, null, true));
        Mockito.when(jobPostingService.query(Mockito.anyString())).thenReturn(jobPostings);
        Assertions.assertEquals(jobPostings,jobPostingController.query("query"));
    }
}