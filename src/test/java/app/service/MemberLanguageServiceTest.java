package app.service;

import app.consts.Role;
import app.entity.*;
import app.exception.UnauthorizedException;
import app.repository.MemberLanguageRepository;
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
class MemberLanguageServiceTest
{
    @Mock
    MemberLanguageRepository memberLanguageRepository;
    MemberLanguageService memberLanguageService;
    @BeforeEach
    void setUp()
    {
        memberLanguageService=new MemberLanguageService(memberLanguageRepository);
    }
    @Test
    void save()
    {
        Mockito
                .when(memberLanguageRepository.save(Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,MemberLanguage.class));
        MemberLanguage memberLanguage=new MemberLanguage();
        MemberLanguage saved=memberLanguageService.save(memberLanguage);
        assertEquals(saved,memberLanguage);
    }
    @Test
    void update_throwsUnauthorizedException()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(2L);
        MemberLanguage memberLanguage=new MemberLanguage();
        memberLanguage.setMember(member);
        Mockito.when(memberLanguageRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(memberLanguage));
        MemberLanguage toUpdate=new MemberLanguage();
        toUpdate.setId(2L);
        assertThrows(UnauthorizedException.class,()->memberLanguageService.update(toUpdate,member2));
    }
    @Test
    void update_updates()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(1L);
        MemberLanguage memberLanguage=new MemberLanguage();
        memberLanguage.setMember(member);
        Mockito.when(memberLanguageRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(memberLanguage));
        Mockito
                .when(memberLanguageRepository.save(Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,MemberLanguage.class));
        MemberLanguage toUpdate=new MemberLanguage();
        toUpdate.setId(2L);
        MemberLanguage updated=memberLanguageService.update(toUpdate,member2);
        assertEquals(updated,toUpdate);
    }
    @Test
    void deleteById_deletes()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(1L);
        MemberLanguage memberLanguage=new MemberLanguage();
        memberLanguage.setMember(member);
        Mockito.when(memberLanguageRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(memberLanguage));
        memberLanguageService.deleteById(1L,member2);
        Mockito.verify(memberLanguageRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void deleteById_throwsUnauthorizedException()
    {
        Member member=new Member();
        member.setId(1L);
        Member member2=new Member();
        member2.setId(2L);
        MemberLanguage memberLanguage=new MemberLanguage();
        memberLanguage.setMember(member);
        Mockito.when(memberLanguageRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(memberLanguage));
        assertThrows(UnauthorizedException.class,()->memberLanguageService.deleteById(1L,member2));
    }
    @Test
    void findAllByMemberId_admin_returns()
    {
        List<MemberLanguage> memberLanguages=List.of(new MemberLanguage());
        Mockito.when(memberLanguageRepository.findAllByMemberId(Mockito.anyLong())).thenReturn(memberLanguages);
        User user=new User();
        user.setRole(Role.ADMIN);
        Admin admin=new Admin();
        admin.setUser(user);
        List<MemberLanguage> actual=memberLanguageService.findAllByMemberId(1L,admin);
        assertEquals(actual,memberLanguages);
    }
    @Test
    void findAllByMemberId_manager_returns()
    {
        List<MemberLanguage> memberLanguages=List.of(new MemberLanguage());
        Mockito.when(memberLanguageRepository.findAllByMemberId(Mockito.anyLong())).thenReturn(memberLanguages);
        User user=new User();
        user.setRole(Role.MANAGER);
        Manager manager=new Manager();
        manager.setUser(user);
        List<MemberLanguage> actual=memberLanguageService.findAllByMemberId(1L,manager);
        assertEquals(actual,memberLanguages);
    }
    @Test
    void findAllByMemberId_member_returns()
    {
        List<MemberLanguage> memberLanguages=List.of(new MemberLanguage());
        Mockito.when(memberLanguageRepository.findAllByMemberId(Mockito.anyLong())).thenReturn(memberLanguages);
        long id=1L;
        User user=new User();
        user.setRole(Role.MEMBER);
        Member member=new Member();
        member.setUser(user);
        member.setId(id);
        List<MemberLanguage> actual=memberLanguageService.findAllByMemberId(id,member);
        assertEquals(actual,memberLanguages);
    }
    @Test
    void findAllByMemberId_differentMember_throwsUnauthorizedException()
    {
        User user=new User();
        user.setRole(Role.MEMBER);
        Member member=new Member();
        member.setUser(user);
        member.setId(1L);
        assertThrows(UnauthorizedException.class,()->memberLanguageService.findAllByMemberId(2L,member));
    }
}