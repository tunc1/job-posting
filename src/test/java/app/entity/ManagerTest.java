package app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManagerTest
{
    Manager manager;
    @BeforeEach
    void init()
    {
        manager=new Manager();
        manager.setId(1L);
    }
    @Test
    void testEquals_returnsTrue()
    {
        Manager manager2=new Manager();
        manager2.setId(1L);
        Assertions.assertTrue(manager.equals(manager2));
        Assertions.assertTrue(manager2.equals(manager));
    }
    @Test
    void testEquals_returnsFalse()
    {
        Manager manager2=new Manager();
        manager2.setId(2L);
        Assertions.assertFalse(manager.equals(manager2));
        Assertions.assertFalse(manager2.equals(manager));
    }
}
