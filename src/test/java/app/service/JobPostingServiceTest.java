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

import app.entity.JobPosting;
import app.repository.JobPostingRepository;

@ExtendWith(MockitoExtension.class)
public class JobPostingServiceTest
{
    @Mock
    JobPostingRepository jobPostingRepository;
    JobPostingService jobPostingService;

    @BeforeEach
    void init()
    {
        jobPostingService=new JobPostingService(jobPostingRepository);
    }
    @Test
    void testDeleteById()
    {
        jobPostingService.deleteById(1L);
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
    void testSave()
    {
        JobPosting jobPosting=new JobPosting(null,null,null,null,true);
        Mockito.when(jobPostingRepository.save(Mockito.any())).thenAnswer(i->
        {
            JobPosting input=i.getArgument(0,JobPosting.class);
            input.setId(1L);
            return input;
        });
        Assertions.assertEquals(jobPosting,jobPostingService.update(jobPosting));
    }
    @Test
    void testUpdate()
    {
        JobPosting jobPosting=new JobPosting(1L,null,null,null,null,true);
        Mockito.when(jobPostingRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,JobPosting.class);
        });
        Assertions.assertEquals(jobPosting,jobPostingService.update(jobPosting));
    }
}