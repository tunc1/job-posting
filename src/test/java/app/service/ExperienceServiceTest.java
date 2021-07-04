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

import app.entity.Experience;
import app.repository.ExperienceRepository;

@ExtendWith(MockitoExtension.class)
public class ExperienceServiceTest
{
    @Mock
    ExperienceRepository experienceRepository;
    ExperienceService experienceService;

    @BeforeEach
    void init()
    {
        experienceService=new ExperienceService(experienceRepository);
    }
    @Test
    void testDeleteById()
    {
        experienceService.deleteById(1L);
        Mockito.verify(experienceRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<Experience> companies=List.of(new Experience(1L,null,null,null,null));
        Mockito.when(experienceRepository.findAll()).thenAnswer(invocation->
        {
            return companies;
        });
        Assertions.assertEquals(companies,experienceService.findAll());
    }
    @Test
    void testFindById()
    {
        Experience experience=new Experience(1L,null,null,null,null);
        Mockito.when(experienceRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.of(experience);
        });
        Assertions.assertEquals(experience,experienceService.findById(1L));
    }
    @Test
    void testFindById_throwsException()
    {
        Mockito.when(experienceRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.empty();
        });
        Assertions.assertThrows(EntityNotFoundException.class,()->experienceService.findById(1L));
    }
    @Test
    void testSave()
    {
        Experience experience=new Experience(null,null,null,null);
        Mockito.when(experienceRepository.save(Mockito.any())).thenAnswer(i->
        {
            Experience input=i.getArgument(0,Experience.class);
            input.setId(1L);
            return input;
        });
        Assertions.assertEquals(experience,experienceService.save(experience));
    }
    @Test
    void testUpdate()
    {
        Experience experience=new Experience(1L,null,null,null,null);
        Mockito.when(experienceRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Experience.class);
        });
        Assertions.assertEquals(experience,experienceService.update(experience));
    }
}