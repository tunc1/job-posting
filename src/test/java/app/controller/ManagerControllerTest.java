package app.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import app.entity.Manager;
import app.service.ManagerService;

@ExtendWith(MockitoExtension.class)
public class ManagerControllerTest
{
    @Mock
    ManagerService managerService;
    ManagerController managerController;
    @BeforeEach
    void init()
    {
        managerController=new ManagerController(managerService);
    }
    @Test
    void testDeleteById()
    {
        managerController.deleteById(1L);
        Mockito.verify(managerService,Mockito.times(1)).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<Manager> managers=List.of(new Manager());
        Mockito.when(managerService.findAll()).thenReturn(managers);
        Assertions.assertEquals(managers,managerController.findAll());
    }
    @Test
    void testFindById()
    {
        Manager manager=new Manager();
        Mockito.when(managerService.findById(Mockito.anyLong())).thenReturn(manager);
        Assertions.assertEquals(manager,managerController.findById(1L));
    }
    @Test
    void testSave()
    {
        Manager manager=new Manager();
        Mockito.when(managerService.save(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Manager.class);
        });
        Manager actual=managerController.save(manager);
        Assertions.assertEquals(actual,manager);
    }
    @Test
    void testUpdate()
    {
        Manager manager=new Manager();
        long id=1L;
        Mockito.when(managerService.update(Mockito.any())).thenAnswer(i->
        {
            return i.getArgument(0,Manager.class);
        });
        Manager actual=managerController.update(manager,id);
        Assertions.assertEquals(actual,manager);
        Assertions.assertEquals(actual.getId(),id);
    }
}