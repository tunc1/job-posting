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

import app.entity.Skill;
import app.repository.SkillRepository;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest
{
    @Mock
    SkillRepository skillRepository;
    SkillService skillService;
    @BeforeEach
    void init()
    {
        skillService=new SkillService(skillRepository);
    }
    @Test
    void testDeleteById()
    {
        skillService.deleteById(1L);
        Mockito.verify(skillRepository,Mockito.times(1)).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<Skill> skills=List.of(new Skill());
        Mockito.when(skillRepository.findAll()).thenReturn(skills);
        List<Skill> actual=skillService.findAll();
        Assertions.assertEquals(skills,actual);
    }
    @Test
    void testFindById_returnsSkill()
    {
        Skill skill=new Skill();
        Mockito.when(skillRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(skill));
        Skill actual=skillService.findById(1L);
        Assertions.assertEquals(skill,actual);
    }
    @Test
    void testFindById_throwsEntityNotFoundException()
    {
        Mockito.when(skillRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->skillService.findById(1L));
    }
    @Test
    void testSave()
    {
        Skill skill=new Skill();
        Mockito.when(skillRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Skill.class);
        });
        Assertions.assertEquals(skill,skillService.save(skill));
    }
    @Test
    void testUpdate()
    {
        Skill skill=new Skill();
        Mockito.when(skillRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Skill.class);
        });
        Assertions.assertEquals(skill,skillService.update(skill));
    }
}