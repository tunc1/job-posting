package app.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import app.entity.AppliedJob;
import app.service.AppliedJobService;

@ExtendWith(MockitoExtension.class)
public class AppliedJobControllerTest
{
    @Mock
    AppliedJobService appliedJobService;
    AppliedJobController appliedJobController;
    @BeforeEach
    void init()
    {
        appliedJobController=new AppliedJobController(appliedJobService);
    }
    @Test
    void testDeleteById()
    {
        appliedJobController.deleteById(1L);
        Mockito.verify(appliedJobService).deleteById(Mockito.any());
    }
    @Test
    void testFindAll()
    {
        List<AppliedJob> appliedJobs=List.of(new AppliedJob(1L, null, null, null));
        Mockito.when(appliedJobService.findAll()).thenAnswer(invocation->
        {
            return appliedJobs;
        });
        Assertions.assertEquals(appliedJobs,appliedJobController.findAll());
    }
    @Test
    void testFindById()
    {
        AppliedJob appliedJob=new AppliedJob(1L,null,null,null);
        Mockito.when(appliedJobService.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return appliedJob;
        });
        Assertions.assertEquals(appliedJob,appliedJobController.findById(1L));
    }
    @Test
    void testSave()
    {
        AppliedJob appliedJob=new AppliedJob(null,null,null);
        Mockito.when(appliedJobService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,AppliedJob.class);
        });
        Assertions.assertEquals(appliedJob,appliedJobController.save(appliedJob));
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        AppliedJob appliedJob=new AppliedJob(null,null,null);
        Mockito.when(appliedJobService.update(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,AppliedJob.class);
        });
        AppliedJob actual=appliedJobController.update(appliedJob,id);
        Assertions.assertEquals(appliedJob,actual);
        Assertions.assertEquals(id,actual.getId());
    }
}
