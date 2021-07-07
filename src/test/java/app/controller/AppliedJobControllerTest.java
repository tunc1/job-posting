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

import app.entity.AppliedJob;
import app.entity.Member;
import app.service.AppliedJobService;

@ExtendWith(MockitoExtension.class)
public class AppliedJobControllerTest
{
    @Mock
    AppliedJobService appliedJobService;
    AppliedJobController appliedJobController;
    Authentication authentication;
    Member member;
    @BeforeEach
    void init()
    {
        appliedJobController=new AppliedJobController(appliedJobService);
        member=new Member();
        authentication=new UsernamePasswordAuthenticationToken(member,null);
    }
    @Test
    void testDeleteById()
    {
        appliedJobController.deleteById(1L,authentication);
        Mockito.verify(appliedJobService).deleteById(Mockito.any(),Mockito.any());
    }
    @Test
    void testFindByMember()
    {
        List<AppliedJob> appliedJobs=List.of(new AppliedJob(1L, null, null, null));
        Mockito.when(appliedJobService.findByMember(Mockito.any())).thenReturn(appliedJobs);
        Assertions.assertEquals(appliedJobs,appliedJobController.findByMember(authentication));
    }
    @Test
    void testFindById()
    {
        AppliedJob appliedJob=new AppliedJob(1L,null,null,null);
        Mockito.when(appliedJobService.findById(Mockito.anyLong(),Mockito.any())).thenReturn(appliedJob);
        Assertions.assertEquals(appliedJob,appliedJobController.findById(1L,authentication));
    }
    @Test
    void testSave()
    {
        AppliedJob appliedJob=new AppliedJob(null,null,null);
        Mockito.when(appliedJobService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,AppliedJob.class);
        });
        Assertions.assertEquals(appliedJob,appliedJobController.save(appliedJob,authentication));
        Assertions.assertEquals(appliedJob.getMember(),member);
    }
}
