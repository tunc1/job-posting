package app.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import app.entity.Member;
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
        memberController.deleteById(1L);
        Mockito.verify(memberService).deleteById(Mockito.any());
    }
    @Test
    void testFindAll()
    {
        List<Member> members=List.of(new Member(1L,null,null,null,null,null,true,true,true,true));
        Mockito.when(memberService.findAll()).thenReturn(members);
        Assertions.assertEquals(members,memberController.findAll());
    }
    @Test
    void testFindById()
    {
        Member member=new Member(1L,null,null,null,null,null,true,true,true,true);
        Mockito.when(memberService.findById(Mockito.anyLong())).thenReturn(member);
        Assertions.assertEquals(member,memberController.findById(1L));
    }
    @Test
    void testSave()
    {
        Member member=new Member(null,null,null,null,null,true,true,true,true);
        Mockito.when(memberService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Member.class);
        });
        Assertions.assertEquals(member,memberController.save(member));
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        Member member=new Member(null,null,null,null,null,true,true,true,true);
        Mockito.when(memberService.update(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Member.class);
        });
        Member actual=memberController.update(member,id);
        Assertions.assertEquals(member,actual);
        Assertions.assertEquals(id,actual.getId());
    }
}