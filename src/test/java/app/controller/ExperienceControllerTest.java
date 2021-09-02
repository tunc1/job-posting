package app.controller;

import app.entity.Experience;
import app.entity.Member;
import app.service.ExperienceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExperienceControllerTest
{
    @Mock
    ExperienceService experienceService;
    ExperienceController experienceController;
    Member member;
    Authentication authentication;
    @BeforeEach
    void setUp()
    {
        experienceController=new ExperienceController(experienceService);
        member=new Member();
        authentication=new UsernamePasswordAuthenticationToken(member,null);
    }
    @Test
    void save()
    {
        Mockito
                .when(experienceService.save(Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,Experience.class));
        Experience experience=new Experience();
        Experience saved=experienceController.save(experience,authentication);
        assertEquals(saved,experience);
        assertEquals(saved.getMember(),member);
    }
    @Test
    void update()
    {
        Mockito
                .when(experienceService.update(Mockito.any(),Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,Experience.class));
        long id=1L;
        Experience experience=new Experience();
        Experience updated=experienceController.update(experience,id,authentication);
        assertEquals(updated,experience);
        assertEquals(updated.getId(),id);
    }
    @Test
    void findAllByMemberId()
    {
        List<Experience> experiences=List.of(new Experience());
        Mockito.when(experienceService.findAllByMemberId(Mockito.anyLong(),Mockito.any())).thenReturn(experiences);
        List<Experience> actual=experienceController.findAllByMemberId(1L,authentication);
        assertEquals(actual,experiences);
    }
    @Test
    void deleteById()
    {
        experienceController.deleteById(1L,authentication);
        Mockito.verify(experienceService).deleteById(Mockito.anyLong(),Mockito.any());
    }
}