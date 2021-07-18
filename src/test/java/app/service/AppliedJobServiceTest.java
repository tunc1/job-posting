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
    void testDeleteById()
    {
        AppliedJobService appliedJobService2=Mockito.spy(appliedJobService);
        Mockito.doNothing().when(appliedJobService2).throwExceptionIfNotExists(Mockito.anyLong());
        Mockito.doNothing().when(appliedJobService2).throwExceptionIfNotSameMember(Mockito.anyLong(),Mockito.any());
        appliedJobService2.deleteById(1L,null);
        Mockito.verify(appliedJobRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindById()
    {
        AppliedJobService appliedJobService2=Mockito.spy(appliedJobService);
        Mockito.doNothing().when(appliedJobService2).throwExceptionIfNotExists(Mockito.anyLong());
        Mockito.doNothing().when(appliedJobService2).throwExceptionIfNotSameMember(Mockito.anyLong(),Mockito.any());
        AppliedJob appliedJob=new AppliedJob();
        Mockito.when(appliedJobRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(appliedJob));
        Assertions.assertEquals(appliedJob,appliedJobService2.findById(1L,new Member()));
    }
    @Test
    void testFindByMember()
    {
        List<AppliedJob> appliedJobs=List.of(new AppliedJob());
        Mockito.when(appliedJobRepository.findByMember(Mockito.any())).thenReturn(appliedJobs);
        Assertions.assertEquals(appliedJobs,appliedJobService.findByMember(new Member()));
    }
    @Test
    void testSave()
    {
        AppliedJob appliedJob=new AppliedJob();
        Mockito.when(appliedJobRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,AppliedJob.class);
        });
        Assertions.assertEquals(appliedJob,appliedJobService.save(appliedJob));
    }
    @Test
    void testThrowExceptionIfNotExists_exists_notThrows()
    {
        Mockito.when(appliedJobRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Assertions.assertDoesNotThrow(()->appliedJobService.throwExceptionIfNotExists(1L));
    }
    @Test
    void testThrowExceptionIfNotExists_notExists_throws()
    {
        Mockito.when(appliedJobRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Assertions.assertThrows(EntityNotFoundException.class,()->appliedJobService.throwExceptionIfNotExists(1L));
    }
    @Test
    void testThrowExceptionIfNotSameMember_sameMember_notThrows()
    {
        Member member=new Member();
        member.setId(1L);
        AppliedJob appliedJob=new AppliedJob();
        appliedJob.setMember(member);
        Mockito.when(appliedJobRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(appliedJob));
        Assertions.assertDoesNotThrow(()->appliedJobService.throwExceptionIfNotSameMember(1L,member));
    }
    @Test
    void testThrowExceptionIfNotSameMember_differentMember_throws()
    {
        Member member=new Member();
        member.setId(1L);
        AppliedJob appliedJob=new AppliedJob();
        Member member2=new Member();
        member2.setId(2L);
        appliedJob.setMember(member2);
        Mockito.when(appliedJobRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(appliedJob));
        Assertions.assertThrows(UnauthorizedException.class,()->appliedJobService.throwExceptionIfNotSameMember(1L,member));
    }
}
