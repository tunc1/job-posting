package app.service;

import app.entity.*;
import app.exception.UnauthorizedException;
import app.repository.EducationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EducationServiceTest
{
    @Mock
    EducationRepository educationRepository;
    EducationService educationService;
    @BeforeEach
    void setUp()
    {
        educationService=new EducationService(educationRepository);
    }
    @Test
    void save()
    {
        Mockito
                .when(educationRepository.save(Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,Education.class));
        Education education=new Education();
        Education saved=educationService.save(education);
        assertEquals(saved,education);
    }
    @Test
    void update_throwsUnauthorizedException()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(2L);
        Education education=new Education();
        education.setMember(member);
        Mockito.when(educationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(education));
        Education toUpdate=new Education();
        toUpdate.setId(2L);
        assertThrows(UnauthorizedException.class,()->educationService.update(toUpdate,member2));
    }
    @Test
    void update_updates()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(1L);
        Education education=new Education();
        education.setMember(member);
        Mockito.when(educationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(education));
        Mockito
                .when(educationRepository.save(Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,Education.class));
        Education toUpdate=new Education();
        toUpdate.setId(2L);
        Education updated=educationService.update(toUpdate,member2);
        assertEquals(updated,toUpdate);
    }
    @Test
    void deleteById_deletes()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(1L);
        Education education=new Education();
        education.setMember(member);
        Mockito.when(educationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(education));
        educationService.deleteById(1L,member2);
        Mockito.verify(educationRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void deleteById_throwsUnauthorizedException()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(2L);
        Education education=new Education();
        education.setMember(member);
        Mockito.when(educationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(education));
        assertThrows(UnauthorizedException.class,()->educationService.deleteById(1L,member2));
    }
    @Test
    void findAllByMemberId_admin_returns()
    {
        List<Education> educations=List.of(new Education());
        Mockito.when(educationRepository.findAllByMemberId(Mockito.anyLong())).thenReturn(educations);
        User user=new User();
        user.setRole(Role.ADMIN);
        Admin admin=new Admin();
        admin.setUser(user);
        List<Education> actual=educationService.findAllByMemberId(1L,admin);
        assertEquals(actual,educations);
    }
    @Test
    void findAllByMemberId_manager_returns()
    {
        List<Education> educations=List.of(new Education());
        Mockito.when(educationRepository.findAllByMemberId(Mockito.anyLong())).thenReturn(educations);
        User user=new User();
        user.setRole(Role.MANAGER);
        Manager manager=new Manager();
        manager.setUser(user);
        List<Education> actual=educationService.findAllByMemberId(1L,manager);
        assertEquals(actual,educations);
    }
    @Test
    void findAllByMemberId_member_returns()
    {
        List<Education> educations=List.of(new Education());
        Mockito.when(educationRepository.findAllByMemberId(Mockito.anyLong())).thenReturn(educations);
        long id=1L;
        User user=new User();
        user.setRole(Role.MEMBER);
        Member member=new Member();
        member.setUser(user);
        member.setId(id);
        List<Education> actual=educationService.findAllByMemberId(id,member);
        assertEquals(actual,educations);
    }
    @Test
    void findAllByMemberId_differentMember_throwsUnauthorizedException()
    {
        User user=new User();
        user.setRole(Role.MEMBER);
        Member member=new Member();
        member.setUser(user);
        member.setId(1L);
        assertThrows(UnauthorizedException.class,()->educationService.findAllByMemberId(2L,member));
    }
}