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
import org.springframework.security.crypto.password.PasswordEncoder;

import app.entity.Member;
import app.exception.ConflictException;
import app.exception.UnauthorizedException;
import app.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest
{
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    MemberService memberService;

    @BeforeEach
    void init()
    {
        memberService=new MemberService(memberRepository,passwordEncoder);
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
    void testDeleteById_throwsUnauthorizedExcpetion()
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
    void testSave_successful()
    {
        MemberService memberService2=Mockito.spy(memberService);
        Mockito.doReturn(false).when(memberService2).existsByUsername(Mockito.anyString());
        Mockito.doReturn(false).when(memberService2).existsByEmail(Mockito.anyString());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenAnswer(i->
        {
            String password=i.getArgument(0,String.class);
            return password+"1";
        });
        String password="password";
        Member member=new Member();
        member.setPassword(password);
        member.setUsername("username");
        member.setEmail("email");
        Mockito.when(memberRepository.save(Mockito.any())).thenAnswer(i->
        {
            Member input=i.getArgument(0,Member.class);
            input.setId(1L);
            return input;
        });
        Member savedMember=memberService2.save(member);
        Assertions.assertEquals(member,savedMember);
        Assertions.assertNotEquals(password,savedMember.getPassword());
        Assertions.assertTrue(savedMember.isAccountNonExpired());
        Assertions.assertTrue(savedMember.isAccountNonLocked());
        Assertions.assertTrue(savedMember.isCredentialsNonExpired());
        Assertions.assertTrue(savedMember.isEnabled());
    }
    @Test
    void testSave_anotherUserUsesSameUsername_throwsConflictException()
    {
        Member member=new Member();
        member.setUsername("username");
        MemberService memberService2=Mockito.spy(memberService);
        Mockito.doReturn(true).when(memberService2).existsByUsername(Mockito.anyString());
        ConflictException exception=Assertions.assertThrows(ConflictException.class,()->memberService2.save(member));
        Assertions.assertTrue(exception.getMessage().equals("Another user uses this username"));
    }
    @Test
    void testSave_anotherUserUsesSameEmail_throwsConflictException()
    {
        Member member=new Member();
        member.setUsername("username");
        member.setEmail("email");
        MemberService memberService2=Mockito.spy(memberService);
        Mockito.doReturn(false).when(memberService2).existsByUsername(Mockito.anyString());
        Mockito.doReturn(true).when(memberService2).existsByEmail(Mockito.anyString());
        ConflictException exception=Assertions.assertThrows(ConflictException.class,()->memberService2.save(member));
        Assertions.assertTrue(exception.getMessage().equals("Another user uses this email"));
    }
    @Test
    void testUpdate()
    {
        Member member=new Member();
        member.setId(1L);
        Mockito.when(memberRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Member.class);
        });
        Member updatedMember=memberService.update(member);
        Assertions.assertEquals(member,updatedMember);
    }
    @Test
    void testExistsByEmail()
    {
        boolean exists=true;
        Mockito.when(memberRepository.existsByEmail(Mockito.anyString())).thenReturn(exists);
        Assertions.assertEquals(exists,memberService.existsByEmail("email"));
    }
    @Test
    void testExistsByUsername()
    {
        boolean exists=true;
        Mockito.when(memberRepository.existsByUsername(Mockito.anyString())).thenReturn(exists);
        Assertions.assertEquals(exists,memberService.existsByUsername("email"));
    }
    @Test
    void testFindByUsername()
    {
        Member member=new Member();
        Mockito.when(memberRepository.findByUsername(Mockito.anyString())).thenReturn(member);
        Assertions.assertEquals(member,memberService.findByUsername("username"));
    }
}