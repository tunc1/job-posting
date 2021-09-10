package app.controller;

import app.entity.University;
import app.service.UniversityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UniversityControllerTest
{
    @Mock
    UniversityService universityService;
    UniversityController universityController;
    @BeforeEach
    void init()
    {
        universityController=new UniversityController(universityService);
    }
    @Test
    void testDeleteById()
    {
        universityController.deleteById(1L);
        Mockito.verify(universityService).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<University> universities=List.of(new University());
        Mockito.when(universityService.findAll()).thenReturn(universities);
        List<University> actual=universityController.findAll();
        Assertions.assertEquals(universities,actual);
    }
    @Test
    void testFindById()
    {
        University university=new University();
        Mockito.when(universityService.findById(Mockito.anyLong())).thenReturn(university);
        University actual=universityController.findById(1L);
        Assertions.assertEquals(university,actual);
    }
    @Test
    void testSave()
    {
        University university=new University();
        Mockito.when(universityService.save(Mockito.any())).thenAnswer(i->i.getArgument(0,University.class));
        Assertions.assertEquals(university,universityController.save(university));
    }
    @Test
    void testUpdate()
    {
        long id=1L;
        University university=new University();
        Mockito.when(universityService.update(Mockito.any())).thenAnswer(i->i.getArgument(0,University.class));
        University updated=universityController.update(university,id);
        Assertions.assertEquals(updated.getId(),id);
        Assertions.assertEquals(university,updated);
    }
}