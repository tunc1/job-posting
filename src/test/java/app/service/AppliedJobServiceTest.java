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
import app.entity.Member;
import app.exception.UnauthorizedException;
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
    void testBelongsTo_true()
    {
        Member member=new Member();
        member.setId(1L);
        AppliedJob appliedJob=new AppliedJob();
        appliedJob.setMember(member);
        Mockito.when(appliedJobRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(appliedJob));
        Assertions.assertTrue(appliedJobService.belongsTo(1L,member));
    }
    @Test
    void testBelongsTo_throwsUnauthorizedException()
    {
        Member member=new Member();
        member.setId(1L);
        AppliedJob appliedJob=new AppliedJob();
        Member member2=new Member();
        member2.setId(2L);
        appliedJob.setMember(member2);
        Mockito.when(appliedJobRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(appliedJob));
        Assertions.assertThrows(UnauthorizedException.class,()->appliedJobService.belongsTo(1L,member));
    }
    @Test
    void testExistsById_returnsTrue()
    {
        Mockito.when(appliedJobRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Assertions.assertTrue(appliedJobService.existsById(1L));
    }
    @Test
    void testExistsById_throwsEntityNotFoundException()
    {
        Mockito.when(appliedJobRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Assertions.assertThrows(EntityNotFoundException.class,()->appliedJobService.existsById(1L));
    }
    @Test
    void testDeleteById()
    {
        AppliedJobService appliedJobService2=Mockito.spy(appliedJobService);
        Mockito.doReturn(true).when(appliedJobService2).existsById(Mockito.anyLong());
        Mockito.doReturn(true).when(appliedJobService2).belongsTo(Mockito.anyLong(),Mockito.any());
        appliedJobService2.deleteById(1L,null);
        Mockito.verify(appliedJobRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindByMember()
    {
        List<AppliedJob> appliedJobs=List.of(new AppliedJob(1L, null, null, null));
        Mockito.when(appliedJobRepository.findByMember(Mockito.any())).thenAnswer(invocation->
        {
            return appliedJobs;
        });
        Assertions.assertEquals(appliedJobs,appliedJobService.findByMember(null));
    }
    @Test
    void testFindById()
    {
        AppliedJobService appliedJobService2=Mockito.spy(appliedJobService);
        Mockito.doReturn(true).when(appliedJobService2).existsById(Mockito.anyLong());
        Mockito.doReturn(true).when(appliedJobService2).belongsTo(Mockito.anyLong(),Mockito.any());
        AppliedJob appliedJob=new AppliedJob(1L,null,null,null);
        Mockito.when(appliedJobRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.of(appliedJob);
        });
        Assertions.assertEquals(appliedJob,appliedJobService2.findById(1L,null));
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
        Assertions.assertEquals(appliedJob,appliedJobService.save(appliedJob));
    }
}
