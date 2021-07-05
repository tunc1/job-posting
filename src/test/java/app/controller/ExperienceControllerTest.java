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

import app.entity.Experience;
import app.entity.Member;
import app.service.ExperienceService;

@ExtendWith(MockitoExtension.class)
public class ExperienceControllerTest
{
    @Mock
    ExperienceService experienceService;
    ExperienceController experienceController;
    Authentication authentication;
    Member member;
    @BeforeEach
    void init()
    {
        experienceController=new ExperienceController(experienceService);
        member=new Member();
        authentication=new UsernamePasswordAuthenticationToken(member,null);
    }
    @Test
    void testDeleteById()
    {
        experienceController.deleteById(1L,authentication);
        Mockito.verify(experienceService).deleteById(Mockito.anyLong(),Mockito.any());
    }
    @Test
    void testFindByMember()
    {
        List<Experience> experiences=List.of(new Experience(1L,null,null, null, null));
        Mockito.when(experienceService.findByMember(Mockito.any())).thenAnswer(invocation->
        {
            return experiences;
        });
        Assertions.assertEquals(experiences,experienceController.findByMember(authentication));
    }
    @Test
    void testFindById()
    {
        Experience experience=new Experience(1L,null,null,null,null);
        Mockito.when(experienceService.findById(Mockito.anyLong(),Mockito.any())).thenAnswer(i->
        {
            return experience;
        });
        Assertions.assertEquals(experience,experienceController.findById(1L,authentication));
    }
    @Test
    void testSave()
    {
        Experience experience=new Experience(null,null,null,null);
        Mockito.when(experienceService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Experience.class);
        });
        Assertions.assertEquals(experience,experienceController.save(experience,authentication));
        Assertions.assertEquals(experience.getMember(),member);
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        Experience experience=new Experience(null,null,null,null);
        Mockito.when(experienceService.update(Mockito.any(),Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Experience.class);
        });
        Experience actual=experienceController.update(experience,id,authentication);
        Assertions.assertEquals(experience,actual);
        Assertions.assertEquals(id,actual.getId());
    }
}
