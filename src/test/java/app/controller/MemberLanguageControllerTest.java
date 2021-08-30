package app.controller;

import app.entity.Member;
import app.entity.MemberLanguage;
import app.service.MemberLanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberLanguageControllerTest
{
    @Mock
    MemberLanguageService memberLanguageService;
    MemberLanguageController memberLanguageController;
    Member member;
    Authentication authentication;
    @BeforeEach
    void setUp()
    {
        memberLanguageController=new MemberLanguageController(memberLanguageService);
        member=new Member();
        authentication=new UsernamePasswordAuthenticationToken(member,null);
    }
    @Test
    void save()
    {
        Mockito
                .when(memberLanguageService.save(Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,MemberLanguage.class));
        MemberLanguage memberLanguage=new MemberLanguage();
        MemberLanguage saved=memberLanguageController.save(memberLanguage,authentication);
        assertEquals(saved,memberLanguage);
        assertEquals(saved.getMember(),member);
    }
    @Test
    void update()
    {
        Mockito
                .when(memberLanguageService.update(Mockito.any(),Mockito.any()))
                .thenAnswer(invocationOnMock->invocationOnMock.getArgument(0,MemberLanguage.class));
        long id=1L;
        MemberLanguage memberLanguage=new MemberLanguage();
        MemberLanguage updated=memberLanguageController.update(memberLanguage,id,authentication);
        assertEquals(updated,memberLanguage);
        assertEquals(updated.getId(),id);
    }
    @Test
    void findAllByMemberId()
    {
        List<MemberLanguage> memberLanguages=List.of(new MemberLanguage());
        Mockito.when(memberLanguageService.findAllByMemberId(Mockito.anyLong(),Mockito.any())).thenReturn(memberLanguages);
        List<MemberLanguage> actual=memberLanguageController.findAllByMemberId(authentication,1L);
        assertEquals(actual,memberLanguages);
    }
    @Test
    void deleteById()
    {
        memberLanguageController.deleteById(1L,authentication);
        Mockito.verify(memberLanguageService).deleteById(Mockito.anyLong(),Mockito.any());
    }
}