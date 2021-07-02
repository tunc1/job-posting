package app.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import app.entity.Experience;
import app.service.ExperienceService;

@ExtendWith(MockitoExtension.class)
public class ExperienceControllerTest
{
    @Mock
    ExperienceService experienceService;
    ExperienceController experienceController;
    @BeforeEach
    void init()
    {
        experienceController=new ExperienceController(experienceService);
    }
    @Test
    void testDeleteById()
    {
        experienceController.deleteById(1L);
        Mockito.verify(experienceService).deleteById(Mockito.any());
    }
    @Test
    void testFindAll()
    {
        List<Experience> experiences=List.of(new Experience(1L,null,null, null, null));
        Mockito.when(experienceService.findAll()).thenAnswer(invocation->
        {
            return experiences;
        });
        Assertions.assertEquals(experiences,experienceController.findAll());
    }
    @Test
    void testFindById()
    {
        Experience experience=new Experience(1L,null,null,null,null);
        Mockito.when(experienceService.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return experience;
        });
        Assertions.assertEquals(experience,experienceController.findById(1L));
    }
    @Test
    void testSave()
    {
        Experience experience=new Experience(null,null,null,null);
        Mockito.when(experienceService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Experience.class);
        });
        Assertions.assertEquals(experience,experienceController.save(experience));
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        Experience experience=new Experience(null,null,null,null);
        Mockito.when(experienceService.update(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Experience.class);
        });
        Experience actual=experienceController.update(experience,id);
        Assertions.assertEquals(experience,actual);
        Assertions.assertEquals(id,actual.getId());
    }
}
