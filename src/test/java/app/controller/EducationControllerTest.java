package app.controller;

import app.entity.Member;
import app.entity.Education;
import app.service.EducationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EducationControllerTest
{
    @Mock
    EducationService educationService;
    EducationController educationController;
    Member member;
    Authentication authentication;
    @BeforeEach
    void setUp()
    {
        educationController=new EducationController(educationService);
        member=new Member();
        authentication=new UsernamePasswordAuthenticationToken(member,null);
    }
    @Test
    void save()
    {
        Mockito
                .when(educationService.save(Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,Education.class));
        Education education=new Education();
        Education saved=educationController.save(education,authentication);
        assertEquals(saved,education);
        assertEquals(saved.getMember(),member);
    }
    @Test
    void update()
    {
        Mockito
                .when(educationService.update(Mockito.any(),Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,Education.class));
        long id=1L;
        Education education=new Education();
        Education updated=educationController.update(education,id,authentication);
        assertEquals(updated,education);
        assertEquals(updated.getId(),id);
    }
    @Test
    void findAllByMemberId()
    {
        List<Education> educations=List.of(new Education());
        Mockito.when(educationService.findAllByMemberId(Mockito.anyLong(),Mockito.any())).thenReturn(educations);
        List<Education> actual=educationController.findAllByMemberId(1L,authentication);
        assertEquals(actual,educations);
    }
    @Test
    void deleteById()
    {
        educationController.deleteById(1L,authentication);
        Mockito.verify(educationService).deleteById(Mockito.anyLong(),Mockito.any());
    }
}