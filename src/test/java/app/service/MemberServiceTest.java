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

import app.entity.Member;
import app.entity.Role;
import app.entity.User;
import app.exception.ConflictException;
import app.exception.UnauthorizedException;
import app.repository.MemberRepository;
import app.util.UserUtil;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest
{
    @Mock
    MemberRepository memberRepository;
    @Mock
    UserUtil userUtil;
    MemberService memberService;

    @BeforeEach
    void init()
    {
        memberService=new MemberService(memberRepository,userUtil);
    }
    @Test
    void testDeleteById()
    {
        Member member=new Member();
        member.setId(1L);
        memberService.deleteById(1L,member);
        Mockito.verify(memberRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testDeleteById_throwsUnauthorizedException()
    {
        Member member=new Member();
        member.setId(2L);
        Assertions.assertThrows(UnauthorizedException.class,()->memberService.deleteById(1L,member));
    }
    @Test
    void testFindAll()
    {
        Member member=new Member();
        member.setId(1L);
        List<Member> companies=List.of(member);
        Mockito.when(memberRepository.findAll()).thenReturn(companies);
        Assertions.assertEquals(companies,memberService.findAll());
    }
    @Test
    void testFindById()
    {
        Member member=new Member();
        member.setId(1L);
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
        Assertions.assertEquals(member,memberService.findById(1L));
    }
    @Test
    void testFindById_throwsException()
    {
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->memberService.findById(1L));
    }
    @Test
    void testSave()
    {
        MemberService memberService2=Mockito.spy(memberService);
        Mockito.doNothing().when(memberService2).throwExceptionIfEmailConflicts(Mockito.anyString());
        Mockito.doNothing().when(userUtil).throwExceptionIfUsernameConflicts(Mockito.anyString());
        Mockito.when(userUtil.encodePassword(Mockito.anyString())).thenAnswer(i->
        {
            String password=i.getArgument(0,String.class);
            return password+"1";
        });
        String password="password";
        Member member=new Member();
        User user=new User();
        member.setUser(user);
        user.setPassword(password);
        user.setUsername("username");
        member.setEmail("email");
        Mockito.when(memberRepository.save(Mockito.any())).thenAnswer(i->i.getArgument(0,Member.class));
        Member savedMember=memberService2.save(member);
        Assertions.assertEquals(member,savedMember);
        Assertions.assertNotEquals(password,savedMember.getUser().getPassword());
        Assertions.assertTrue(savedMember.getUser().getRole().equals(Role.MEMBER));
        Assertions.assertTrue(savedMember.getUser().isAccountNonExpired());
        Assertions.assertTrue(savedMember.getUser().isAccountNonLocked());
        Assertions.assertTrue(savedMember.getUser().isCredentialsNonExpired());
        Assertions.assertTrue(savedMember.getUser().isEnabled());
    }
    @Test
    void testUpdate_updatingEmail()
    {
        Member member2=new Member();
        member2.setEmail("email");
        MemberService memberService2=Mockito.spy(memberService);
        Mockito.doNothing().when(memberService2).throwExceptionIfEmailConflicts(Mockito.anyString());
        Mockito.doReturn(member2).when(memberService2).findById(Mockito.anyLong());
        Member member=new Member();
        User user=new User();
        member.setUser(user);
        user.setUsername("username");
        member.setEmail("email2");
        member.setId(1L);
        Mockito.when(memberRepository.save(Mockito.any())).thenAnswer(i->i.getArgument(0,Member.class));
        Member updatedMember=memberService2.update(member);
        Assertions.assertEquals(member,updatedMember);
        Mockito.verify(memberService2,Mockito.times(1)).throwExceptionIfEmailConflicts(Mockito.anyString());
    }
    @Test
    void testUpdate_notUpdatingEmail()
    {
        String email="email";
        Member member2=new Member();
        member2.setEmail(email);
        MemberService memberService2=Mockito.spy(memberService);
        Mockito.doReturn(member2).when(memberService2).findById(Mockito.anyLong());
        Member member=new Member();
        User user=new User();
        member.setUser(user);
        user.setUsername("username");
        member.setEmail(email);
        member.setId(1L);
        Mockito.when(memberRepository.save(Mockito.any())).thenAnswer(i->i.getArgument(0,Member.class));
        Member updatedMember=memberService2.update(member);
        Assertions.assertEquals(member,updatedMember);
        Mockito.verify(memberService2,Mockito.times(0)).throwExceptionIfEmailConflicts(Mockito.anyString());
    }
    @Test
    void testThrowExceptionIfEmailConflicts_emailNotConflicts_notThrows()
    {
        Mockito.when(memberRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        Assertions.assertDoesNotThrow(()->memberService.throwExceptionIfEmailConflicts("email"));
    }
    @Test
    void testThrowExceptionIfEmailConflicts_emailConflicts_throws()
    {
        Mockito.when(memberRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        Assertions.assertThrows(ConflictException.class,()->memberService.throwExceptionIfEmailConflicts("email"));
    }
}