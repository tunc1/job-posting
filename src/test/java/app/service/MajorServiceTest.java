package app.service;

import app.entity.Major;
import app.repository.MajorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MajorServiceTest
{
    @Mock
    MajorRepository majorRepository;
    MajorService majorService;
    @BeforeEach
    void init()
    {
        majorService=new MajorService(majorRepository);
    }
    @Test
    void testDeleteById()
    {
        majorService.deleteById(1L);
        Mockito.verify(majorRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<Major> majors=List.of(new Major());
        Mockito.when(majorRepository.findAll()).thenReturn(majors);
        List<Major> actual=majorService.findAll();
        Assertions.assertEquals(majors,actual);
    }
    @Test
    void testFindById_returnsMajor()
    {
        Major major=new Major();
        Mockito.when(majorRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(major));
        Major actual=majorService.findById(1L);
        Assertions.assertEquals(major,actual);
    }
    @Test
    void testFindById_throwsEntityNotFoundException()
    {
        Mockito.when(majorRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->majorService.findById(1L));
    }
    @Test
    void testSave()
    {
        Major major=new Major();
        Mockito.when(majorRepository.save(Mockito.any())).thenAnswer(i->i.getArgument(0,Major.class));
        Assertions.assertEquals(major,majorService.save(major));
    }
    @Test
    void testUpdate()
    {
        Major major=new Major();
        Mockito.when(majorRepository.save(Mockito.any())).thenAnswer(i->i.getArgument(0,Major.class));
        Assertions.assertEquals(major,majorService.update(major));
    }
}