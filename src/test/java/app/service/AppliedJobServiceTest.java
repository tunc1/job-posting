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

import app.entity.AppliedJob;
import app.repository.AppliedJobRepository;

@ExtendWith(MockitoExtension.class)
public class AppliedJobServiceTest
{
    @Mock
    AppliedJobRepository appliedJobRepository;
    AppliedJobService appliedJobService;

    @BeforeEach
    void init()
    {
        appliedJobService=new AppliedJobService(appliedJobRepository);
    }
    @Test
    void testDeleteById()
    {
        appliedJobService.deleteById(1L);
        Mockito.verify(appliedJobRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<AppliedJob> appliedJobs=List.of(new AppliedJob(1L, null, null, null));
        Mockito.when(appliedJobRepository.findAll()).thenAnswer(invocation->
        {
            return appliedJobs;
        });
        Assertions.assertEquals(appliedJobs,appliedJobService.findAll());
    }
    @Test
    void testFindById()
    {
        AppliedJob appliedJob=new AppliedJob(1L,null,null,null);
        Mockito.when(appliedJobRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.of(appliedJob);
        });
        Assertions.assertEquals(appliedJob,appliedJobService.findById(1L));
    }
    @Test
    void testFindById_throwsException()
    {
        Mockito.when(appliedJobRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.empty();
        });
        Assertions.assertThrows(EntityNotFoundException.class,()->appliedJobService.findById(1L));
    }
    @Test
    void testSave()
    {
        AppliedJob appliedJob=new AppliedJob(null,null,null);
        Mockito.when(appliedJobRepository.save(Mockito.any())).thenAnswer(i->
        {
            AppliedJob input=i.getArgument(0,AppliedJob.class);
            input.setId(1L);
            return input;
        });
        Assertions.assertEquals(appliedJob,appliedJobService.update(appliedJob));
    }
    @Test
    void testUpdate()
    {
        AppliedJob appliedJob=new AppliedJob(1L,null,null,null);
        Mockito.when(appliedJobRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,AppliedJob.class);
        });
        Assertions.assertEquals(appliedJob,appliedJobService.update(appliedJob));
    }
}
