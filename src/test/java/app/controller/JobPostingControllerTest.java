package app.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import app.entity.JobPosting;
import app.service.JobPostingService;

@ExtendWith(MockitoExtension.class)
public class JobPostingControllerTest
{
    @Mock
    JobPostingService jobPostingService;
    JobPostingController jobPostingController;
    @BeforeEach
    void init()
    {
        jobPostingController=new JobPostingController(jobPostingService);
    }
    @Test
    void testDeleteById()
    {
        jobPostingController.deleteById(1L);
        Mockito.verify(jobPostingService).deleteById(Mockito.any());
    }
    @Test
    void testFindAll()
    {
        List<JobPosting> jobPostings=List.of(new JobPosting(1L,null,null,null, null, true));
        Mockito.when(jobPostingService.findAll()).thenAnswer(invocation->
        {
            return jobPostings;
        });
        Assertions.assertEquals(jobPostings,jobPostingController.findAll());
    }
    @Test
    void testFindById()
    {
        JobPosting jobPosting=new JobPosting(1L,null,null,null,null,true);
        Mockito.when(jobPostingService.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return jobPosting;
        });
        Assertions.assertEquals(jobPosting,jobPostingController.findById(1L));
    }
    @Test
    void testSave()
    {
        JobPosting jobPosting=new JobPosting(null,null,null,null,true);
        Mockito.when(jobPostingService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        Assertions.assertEquals(jobPosting,jobPostingController.save(jobPosting));
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        JobPosting jobPosting=new JobPosting(null,null,null,null,true);
        Mockito.when(jobPostingService.update(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        JobPosting actual=jobPostingController.update(jobPosting,id);
        Assertions.assertEquals(jobPosting,actual);
        Assertions.assertEquals(id,actual.getId());
    }
}