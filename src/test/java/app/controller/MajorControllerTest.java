package app.controller;

import app.entity.Major;
import app.service.MajorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MajorControllerTest
{
    @Mock
    MajorService majorService;
    MajorController majorController;
    @BeforeEach
    void init()
    {
        majorController=new MajorController(majorService);
    }
    @Test
    void testDeleteById()
    {
        majorController.deleteById(1L);
        Mockito.verify(majorService).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<Major> majors=List.of(new Major());
        Mockito.when(majorService.findAll()).thenReturn(majors);
        List<Major> actual=majorController.findAll();
        Assertions.assertEquals(majors,actual);
    }
    @Test
    void testFindById()
    {
        Major major=new Major();
        Mockito.when(majorService.findById(Mockito.anyLong())).thenReturn(major);
        Major actual=majorController.findById(1L);
        Assertions.assertEquals(major,actual);
    }
    @Test
    void testSave()
    {
        Major major=new Major();
        Mockito.when(majorService.save(Mockito.any())).thenAnswer(i->i.getArgument(0,Major.class));
        Assertions.assertEquals(major,majorController.save(major));
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        Major major=new Major();
        Mockito.when(majorService.update(Mockito.any())).thenAnswer(i->i.getArgument(0,Major.class));
        Major updated=majorController.update(major,id);
        Assertions.assertEquals(updated.getId(),id);
        Assertions.assertEquals(major,updated);
    }
}