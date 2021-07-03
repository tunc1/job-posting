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
        memberService.deleteById(1L);
        Mockito.verify(memberRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<Member> companies=List.of(new Member(1L,null,null,null,null,null,null,null,true,true,true,true));
        Mockito.when(memberRepository.findAll()).thenAnswer(invocation->
        {
            return companies;
        });
        Assertions.assertEquals(companies,memberService.findAll());
    }
    @Test
    void testFindById()
    {
        Member member=new Member(1L,null,null,null,null,null,null,null,true,true,true,true);
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.of(member);
        });
        Assertions.assertEquals(member,memberService.findById(1L));
    }
    @Test
    void testFindById_throwsException()
    {
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenAnswer(i->
        {
            return Optional.empty();
        });
        Assertions.assertThrows(EntityNotFoundException.class,()->memberService.findById(1L));
    }
    @Test
    void testSave()
    {
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenAnswer(i->
        {
            String password=i.getArgument(0,String.class);
            return password+"1";
        });
        String password="password";
        Member member=new Member(null,null,null,null,null,password,null,true,true,true,true);
        Mockito.when(memberRepository.save(Mockito.any())).thenAnswer(i->
        {
            Member input=i.getArgument(0,Member.class);
            input.setId(1L);
            return input;
        });
        Member savedMember=memberService.save(member);
        Assertions.assertEquals(member,savedMember);
        Assertions.assertNotEquals(password,savedMember.getPassword());
    }
    @Test
    void testUpdate()
    {
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenAnswer(i->
        {
            String password=i.getArgument(0,String.class);
            return password+"1";
        });
        String password="password";
        Member member=new Member(1L,null,null,null,null,null,password,null,true,true,true,true);
        Mockito.when(memberRepository.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Member.class);
        });
        Member updatedMember=memberService.update(member);
        Assertions.assertEquals(member,updatedMember);
        Assertions.assertNotEquals(password,updatedMember.getPassword());
    }
}