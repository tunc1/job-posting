package app.service;

import app.entity.University;
import app.repository.UniversityRepository;
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
public class UniversityServiceTest
{
    @Mock
    UniversityRepository universityRepository;
    UniversityService universityService;
    @BeforeEach
    void init()
    {
        universityService=new UniversityService(universityRepository);
    }
    @Test
    void testDeleteById()
    {
        universityService.deleteById(1L);
        Mockito.verify(universityRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void testFindAll()
    {
        List<University> universities=List.of(new University());
        Mockito.when(universityRepository.findAll()).thenReturn(universities);
        List<University> actual=universityService.findAll();
        Assertions.assertEquals(universities,actual);
    }
    @Test
    void testFindById_returnsUniversity()
    {
        University university=new University();
        Mockito.when(universityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(university));
        University actual=universityService.findById(1L);
        Assertions.assertEquals(university,actual);
    }
    @Test
    void testFindById_throwsEntityNotFoundException()
    {
        Mockito.when(universityRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->universityService.findById(1L));
    }
    @Test
    void testSave()
    {
        University university=new University();
        Mockito.when(universityRepository.save(Mockito.any())).thenAnswer(i->i.getArgument(0,University.class));
        Assertions.assertEquals(university,universityService.save(university));
    }
    @Test
    void testUpdate()
    {
        University university=new University();
        Mockito.when(universityRepository.save(Mockito.any())).thenAnswer(i->i.getArgument(0,University.class));
        Assertions.assertEquals(university,universityService.update(university));
    }
}