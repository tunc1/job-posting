package app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberTest
{
    Member member;
    @BeforeEach
    void init()
    {
        member=new Member();
        member.setId(1L);
    }
    @Test
    void testCompare_returnsTrue()
    {
        Member member2=new Member();
        member2.setId(1L);
        Assertions.assertTrue(member.equals(member2));
        Assertions.assertTrue(member2.equals(member));
    }
    @Test
    void testCompare_returnsFalse()
    {
        Member member2=new Member();
        member2.setId(2L);
        Assertions.assertFalse(member.equals(member2));
        Assertions.assertFalse(member2.equals(member));
    }
}