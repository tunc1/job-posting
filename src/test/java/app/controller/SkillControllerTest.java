package app.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import app.entity.Skill;
import app.service.SkillService;

@ExtendWith(MockitoExtension.class)
public class SkillControllerTest
{
    @Mock
    SkillService skillService;
    SkillController skillController;
    @BeforeEach
    void init()
    {
        skillController=new SkillController(skillService);
    }
    @Test
    void testDeleteById()
    {
        skillController.deleteById(1L);
        Mockito.verify(skillService).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<Skill> skills=List.of(new Skill());
        Mockito.when(skillService.findAll()).thenReturn(skills);
        List<Skill> actual=skillController.findAll();
        Assertions.assertEquals(skills,actual);
    }
    @Test
    void testFindById()
    {
        Skill skill=new Skill();
        Mockito.when(skillService.findById(Mockito.anyLong())).thenReturn(skill);
        Skill actual=skillController.findById(1L);
        Assertions.assertEquals(skill,actual);
    }
    @Test
    void testSave()
    {
        Skill skill=new Skill();
        Mockito.when(skillService.save(Mockito.any())).thenAnswer(i->i.getArgument(0,Skill.class));
        Assertions.assertEquals(skill,skillController.save(skill));
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        Skill skill=new Skill();
        Mockito.when(skillService.update(Mockito.any())).thenAnswer(i->i.getArgument(0,Skill.class));
        Skill updated=skillController.update(skill,id);
        Assertions.assertEquals(updated.getId(),id);
        Assertions.assertEquals(skill,updated);
    }
}