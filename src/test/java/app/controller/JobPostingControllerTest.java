package app.controller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    void testFindById()
    {
        JobPosting jobPosting=new JobPosting();
        Mockito.when(jobPostingService.findById(Mockito.anyLong())).thenReturn(jobPosting);
        Assertions.assertEquals(jobPosting,jobPostingController.findById(1L));
    }
    @Test
    void testSave()
    {
        JobPosting jobPosting=new JobPosting();
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
        JobPosting jobPosting=new JobPosting();
        Mockito.when(jobPostingService.update(Mockito.any(),Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        JobPosting actual=jobPostingController.update(jobPosting,id,authentication);
        Assertions.assertEquals(jobPosting,actual);
        Assertions.assertEquals(id,actual.getId());
    }
    @Test
    void testFindAll()
    {
        Page<JobPosting> page=new PageImpl<>(List.of(new JobPosting()));
        Mockito.when(jobPostingService.findAll(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(page);
        Page<JobPosting> actual=jobPostingController.findAll(Optional.of(1L),Optional.of(1L),Optional.of(1L),Optional.of("title"),0,"id");
        Assertions.assertEquals(page,actual);
    }
}