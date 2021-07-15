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

import app.entity.Education;
import app.entity.Member;
import app.service.EducationService;

@ExtendWith(MockitoExtension.class)
public class EducationControllerTest
{
    @Mock
    EducationService educationService;
    EducationController educationController;
    Authentication authentication;
    Member member;

    @BeforeEach
    void init()
    {
        educationController=new EducationController(educationService);
        member=new Member();
        member.setId(1L);
        authentication=new UsernamePasswordAuthenticationToken(member,null);
    }
    @Test
    void testDeleteById()
    {
        educationController.deleteById(1L,authentication);
        Mockito.verify(educationService).deleteById(Mockito.anyLong(),Mockito.any());
    }
    @Test
    void testFindById()
    {
        Education education=new Education();
        Mockito.when(educationService.findById(Mockito.anyLong())).thenReturn(education);
        Education actual=educationController.findById(1L);
        Assertions.assertEquals(education,actual);
    }
    @Test
    void testFindByMember()
    {
        List<Education> educations=List.of(new Education());
        Mockito.when(educationService.findByMember(Mockito.any())).thenReturn(educations);
        List<Education> actual=educationController.findByMember(authentication);
        Assertions.assertEquals(educations,actual);
    }
    @Test
    void testSave()
    {
        Education education=new Education();
        Mockito.when(educationService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Education.class);
        });
        Education actual=educationController.save(education,authentication);
        Assertions.assertEquals(education,actual);
        Assertions.assertEquals(actual.getMember(),member);
    }
    @Test
    void testUpdate()
    {
        Long id=1L;
        Education education=new Education();
        Mockito.when(educationService.update(Mockito.any(),Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Education.class);
        });
        Education actual=educationController.update(education,id,authentication);
        Assertions.assertEquals(education,actual);
        Assertions.assertEquals(actual.getId(),id);
    }
}