package app.service;

import app.entity.*;
import app.exception.UnauthorizedException;
import app.repository.ExperienceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ExperienceServiceTest
{
    @Mock
    ExperienceRepository experienceRepository;
    ExperienceService experienceService;
    @BeforeEach
    void setUp()
    {
        experienceService=new ExperienceService(experienceRepository);
    }
    @Test
    void save()
    {
        Mockito
                .when(experienceRepository.save(Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,Experience.class));
        Experience experience=new Experience();
        Experience saved=experienceService.save(experience);
        assertEquals(saved,experience);
    }
    @Test
    void update_throwsUnauthorizedException()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(2L);
        Experience experience=new Experience();
        experience.setMember(member);
        Mockito.when(experienceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(experience));
        Experience toUpdate=new Experience();
        toUpdate.setId(2L);
        assertThrows(UnauthorizedException.class,()->experienceService.update(toUpdate,member2));
    }
    @Test
    void update_updates()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(1L);
        Experience experience=new Experience();
        experience.setMember(member);
        Mockito.when(experienceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(experience));
        Mockito
                .when(experienceRepository.save(Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,Experience.class));
        Experience toUpdate=new Experience();
        toUpdate.setId(2L);
        Experience updated=experienceService.update(toUpdate,member2);
        assertEquals(updated,toUpdate);
    }
    @Test
    void deleteById_deletes()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(1L);
        Experience experience=new Experience();
        experience.setMember(member);
        Mockito.when(experienceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(experience));
        experienceService.deleteById(1L,member2);
        Mockito.verify(experienceRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void deleteById_throwsUnauthorizedException()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(2L);
        Experience experience=new Experience();
        experience.setMember(member);
        Mockito.when(experienceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(experience));
        assertThrows(UnauthorizedException.class,()->experienceService.deleteById(1L,member2));
    }
    @Test
    void findAllByMemberId_admin_returns()
    {
        List<Experience> experiences=List.of(new Experience());
        Mockito.when(experienceRepository.findAllByMemberId(Mockito.anyLong())).thenReturn(experiences);
        User user=new User();
        user.setRole(Role.ADMIN);
        Admin admin=new Admin();
        admin.setUser(user);
        List<Experience> actual=experienceService.findAllByMemberId(1L,admin);
        assertEquals(actual,experiences);
    }
    @Test
    void findAllByMemberId_manager_returns()
    {
        List<Experience> experiences=List.of(new Experience());
        Mockito.when(experienceRepository.findAllByMemberId(Mockito.anyLong())).thenReturn(experiences);
        User user=new User();
        user.setRole(Role.MANAGER);
        Manager manager=new Manager();
        manager.setUser(user);
        List<Experience> actual=experienceService.findAllByMemberId(1L,manager);
        assertEquals(actual,experiences);
    }
    @Test
    void findAllByMemberId_member_returns()
    {
        List<Experience> experiences=List.of(new Experience());
        Mockito.when(experienceRepository.findAllByMemberId(Mockito.anyLong())).thenReturn(experiences);
        long id=1L;
        User user=new User();
        user.setRole(Role.MEMBER);
        Member member=new Member();
        member.setUser(user);
        member.setId(id);
        List<Experience> actual=experienceService.findAllByMemberId(id,member);
        assertEquals(actual,experiences);
    }
    @Test
    void findAllByMemberId_differentMember_throwsUnauthorizedException()
    {
        User user=new User();
        user.setRole(Role.MEMBER);
        Member member=new Member();
        member.setUser(user);
        member.setId(1L);
        assertThrows(UnauthorizedException.class,()->experienceService.findAllByMemberId(2L,member));
    }
}