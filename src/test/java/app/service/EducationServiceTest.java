package app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import app.entity.Education;
import app.entity.Member;
import app.exception.UnauthorizedException;
import app.repository.EducationRepository;

@ExtendWith(MockitoExtension.class)
public class EducationServiceTest
{
    @Mock
    EducationRepository educationRepository;
    EducationService educationService;

    @BeforeEach
    void init()
    {
        educationService=new EducationService(educationRepository);
    }
    @Test
    void testDeleteById()
    {
        EducationService educationService2=Mockito.spy(educationService);
        Mockito.doNothing().when(educationService2).throwExceptionIfNotExists(Mockito.anyLong());
        Mockito.doNothing().when(educationService2).throwExceptionIfDifferentMember(Mockito.anyLong(),Mockito.any());
        educationService2.deleteById(1L,new Member());
        Mockito.verify(educationRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindById()
    {
        EducationService educationService2=Mockito.spy(educationService);
        Mockito.doNothing().when(educationService2).throwExceptionIfNotExists(Mockito.anyLong());
        Education education=new Education();
        Mockito.when(educationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(education));
        Education actual=educationService2.findById(1L);
        Assertions.assertEquals(education,actual);
    }
    @Test
    void testFindByMember()
    {
        List<Education> educations=List.of(new Education());
        Mockito.when(educationRepository.findByMember(Mockito.any())).thenReturn(educations);
        List<Education> actual=educationService.findByMember(new Member());
        Assertions.assertEquals(educations,actual);
    }
    @Test
    void testSave()
    {
        EducationService educationService2=Mockito.spy(educationService);
        Mockito.doNothing().when(educationService2).throwExceptionIfDatesInvalid(Mockito.any());
        Education education=new Education();
        Mockito.when(educationRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Education.class);
        });
        Education actual=educationService2.save(education);
        Assertions.assertEquals(education,actual);
    }
    @Test
    void testThrowExceptionIfDatesInvalid_notThrows_FinishGreaterThanStart() throws ParseException
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date startedAt=dateFormat.parse("2020-01-01");
        Date finishedAt=dateFormat.parse("2024-01-01");
        Education education=new Education();
        education.setFinishedAt(finishedAt);
        education.setStartedAt(startedAt);
        Assertions.assertDoesNotThrow(()->educationService.throwExceptionIfDatesInvalid(education));
    }
    @Test
    void testThrowExceptionIfDatesInvalid_throws_SameDates() throws ParseException
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date startedAt=dateFormat.parse("2024-01-01");
        Date finishedAt=dateFormat.parse("2020-01-01");
        Education education=new Education();
        education.setFinishedAt(finishedAt);
        education.setStartedAt(startedAt);
        Exception exception=Assertions.assertThrows(IllegalArgumentException.class,()->educationService.throwExceptionIfDatesInvalid(education));
        Assertions.assertTrue(exception.getMessage().equals("Start date can not be greater than finish date"));
    }
    @Test
    void testThrowExceptionIfDatesInvalid_throws_StartGreaterThanFinish() throws ParseException
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date startedAt=dateFormat.parse("2024-01-01");
        Date finishedAt=dateFormat.parse("2024-01-01");
        Education education=new Education();
        education.setFinishedAt(finishedAt);
        education.setStartedAt(startedAt);
        Exception exception=Assertions.assertThrows(IllegalArgumentException.class,()->educationService.throwExceptionIfDatesInvalid(education));
        Assertions.assertTrue(exception.getMessage().equals("Start and finish dates can not be same"));
    }
    @Test
    void testThrowExceptionIfDifferentMember_throwsException_differentMember()
    {
        Long id=1L;
        Member member=new Member();
        member.setId(2L);
        Mockito.when(educationRepository.getMemberId(Mockito.anyLong())).thenReturn(id);
        Assertions.assertThrows(UnauthorizedException.class,()->educationService.throwExceptionIfDifferentMember(id,member));
    }
    @Test
    void testThrowExceptionIfDifferentMember_notThrowsException_sameMember()
    {
        Long id=1L;
        Member member=new Member();
        member.setId(id);
        Mockito.when(educationRepository.getMemberId(Mockito.anyLong())).thenReturn(id);
        Assertions.assertDoesNotThrow(()->educationService.throwExceptionIfDifferentMember(id,member));
    }
    @Test
    void testThrowExceptionIfNotExists_throwsException_idNotExists()
    {
        Mockito.when(educationRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Assertions.assertThrows(EntityNotFoundException.class,()->educationService.throwExceptionIfNotExists(1L));
    }
    @Test
    void testThrowExceptionIfNotExists_notThrowsException_idExists()
    {
        Mockito.when(educationRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Assertions.assertDoesNotThrow(()->educationService.throwExceptionIfNotExists(1L));
    }
    @Test
    void testUpdate()
    {
        EducationService educationService2=Mockito.spy(educationService);
        Mockito.doNothing().when(educationService2).throwExceptionIfDifferentMember(Mockito.anyLong(),Mockito.any());
        Mockito.doNothing().when(educationService2).throwExceptionIfDatesInvalid(Mockito.any());
        Education education=new Education();
        education.setId(1L);
        Mockito.when(educationRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Education.class);
        });
        Education actual=educationService2.update(education,new Member());
        Assertions.assertEquals(education,actual);
    }
}