package app.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import app.entity.Member;
import app.entity.User;
import app.exception.ConflictException;
import app.exception.ExceptionMessage;
import app.service.MemberService;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest
{
    @Mock
    MemberService memberService;
    MemberController memberController;
    @BeforeEach
    void init()
    {
        memberController=new MemberController(memberService);
    }
    @Test
    void testDeleteById()
    {
        Member member=new Member();
        Authentication authentication=new UsernamePasswordAuthenticationToken(member,null);
        memberController.deleteById(1L,authentication);
        Mockito.verify(memberService).deleteById(Mockito.anyLong(),Mockito.any());
    }
    @Test
    void testFindAll()
    {
        Member member=new Member();
        member.setId(1L);
        List<Member> members=List.of(member);
        Mockito.when(memberService.findAll()).thenReturn(members);
        Assertions.assertEquals(members,memberController.findAll());
    }
    @Test
    void testFindById()
    {
        Member member=new Member();
        member.setId(1L);
        Mockito.when(memberService.findById(Mockito.anyLong())).thenReturn(member);
        Assertions.assertEquals(member,memberController.findById(1L));
    }
    @Test
    void testSave_returnsMember()
    {
        Member member=new Member();
        Mockito.when(memberService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Member.class);
        });
        ResponseEntity<Object> responseEntity=memberController.save(member);
        Assertions.assertEquals(responseEntity.getBody(),member);
        Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.CREATED);
    }
    @Test
    void testSave_returnsExceptionMessage()
    {
        String message="message";
        Member member=new Member();
        Mockito.when(memberService.save(Mockito.any())).thenThrow(new ConflictException(message));
        ResponseEntity<Object> responseEntity=memberController.save(member);
        Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.CONFLICT);
        Assertions.assertTrue(((ExceptionMessage)responseEntity.getBody()).getMessage().equals(message));
    }
    @Test
    void testUpdate_returnsMember()
    {
        Member member=new Member();
        member.setId(1L);
        member.setUser(new User());
        Authentication authentication=new UsernamePasswordAuthenticationToken(member,null);
        Mockito.when(memberService.update(Mockito.any())).thenAnswer(i->i.getArgument(0,Member.class));
        ResponseEntity<Object> actual=memberController.update(member,authentication);
        Assertions.assertEquals(member,actual.getBody());
        Assertions.assertEquals(member.getId(),((Member)actual.getBody()).getId());
    }
    @Test
    void testUpdate_returnsExceptionMessage()
    {
        Member member=new Member();
        member.setUser(new User());
        Authentication authentication=new UsernamePasswordAuthenticationToken(member,null);
        String message="exception message";
        Mockito.when(memberService.update(Mockito.any())).thenThrow(new ConflictException(message));
        ResponseEntity<Object> responseEntity=memberController.update(member,authentication);
        Assertions.assertEquals(responseEntity.getStatusCode(),HttpStatus.CONFLICT);
        Assertions.assertTrue(((ExceptionMessage)responseEntity.getBody()).getMessage().equals(message));
    }
}