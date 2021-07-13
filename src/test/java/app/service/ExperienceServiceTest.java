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
import app.entity.Member;
import app.exception.UnauthorizedException;
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
    void testBelongsTo_true()
    {
        Member member=new Member();
        member.setId(1L);
        Experience experience=new Experience();
        experience.setMember(member);
        Mockito.when(experienceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(experience));
        Assertions.assertTrue(experienceService.belongsTo(1L,member));
    }
    @Test
    void testBelongsTo_throwsUnauthorizedException()
    {
        Member member=new Member();
        member.setId(1L);
        Experience experience=new Experience();
        Member member2=new Member();
        member2.setId(2L);
        experience.setMember(member2);
        Mockito.when(experienceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(experience));
        Assertions.assertThrows(UnauthorizedException.class,()->experienceService.belongsTo(1L,member));
    }
    @Test
    void testExistsById_returnsTrue()
    {
        Mockito.when(experienceRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Assertions.assertTrue(experienceService.existsById(1L));
    }
    @Test
    void testExistsById_throwsEntityNotFoundException()
    {
        Mockito.when(experienceRepository.existsById(Mockito.anyLong())).thenReturn(false);
        Assertions.assertThrows(EntityNotFoundException.class,()->experienceService.existsById(1L));
    }
    @Test
    void testDeleteById()
    {
        ExperienceService experienceService2=Mockito.spy(experienceService);
        Mockito.doReturn(true).when(experienceService2).existsById(Mockito.anyLong());
        Mockito.doReturn(true).when(experienceService2).belongsTo(Mockito.anyLong(),Mockito.any());
        experienceService2.deleteById(1L,null);
        Mockito.verify(experienceRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindByMember()
    {
        Experience experience=new Experience();
        experience.setId(1L);
        List<Experience> experiences=List.of(experience);
        Mockito.when(experienceRepository.findByMember(Mockito.any())).thenReturn(experiences);
        Assertions.assertEquals(experiences,experienceService.findByMember(null));
    }
    @Test
    void testFindById()
    {
        ExperienceService experienceService2=Mockito.spy(experienceService);
        Mockito.doReturn(true).when(experienceService2).existsById(Mockito.anyLong());
        Mockito.doReturn(true).when(experienceService2).belongsTo(Mockito.anyLong(),Mockito.any());
        Experience experience=new Experience();
        experience.setId(1L);
        Mockito.when(experienceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(experience));
        Assertions.assertEquals(experience,experienceService2.findById(1L,null));
    }
    @Test
    void testSave()
    {
        Experience experience=new Experience();
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
        Member member=new Member();
        member.setId(1L);
        Experience experience=new Experience();
        experience.setId(1L);
        ExperienceService experienceService2=Mockito.spy(experienceService);
        Mockito.doReturn(true).when(experienceService2).existsById(Mockito.anyLong());
        Mockito.doReturn(true).when(experienceService2).belongsTo(Mockito.anyLong(),Mockito.any());
        Mockito.when(experienceRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Experience.class);
        });
        Experience actual=experienceService2.update(experience,member);
        Assertions.assertEquals(experience,actual);
        Assertions.assertNotNull(actual.getMember());
    }
}