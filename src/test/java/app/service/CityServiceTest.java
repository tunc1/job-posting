package app.service;

import app.entity.City;
import app.repository.CityRepository;
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
class CityServiceTest
{
    @Mock
    CityRepository cityRepository;
    CityService cityService;

    @BeforeEach
    void setUp()
    {
        cityService=new CityService(cityRepository);
    }
    @Test
    void save()
    {
        City city=new City();
        Mockito.when(cityRepository.save(Mockito.any())).thenAnswer(e->e.getArgument(0,City.class));
        City saved=cityService.save(city);
        Assertions.assertEquals(saved,city);
    }
    @Test
    void update()
    {
        City city=new City();
        Mockito.when(cityRepository.save(Mockito.any())).thenAnswer(e->e.getArgument(0,City.class));
        City updated=cityService.update(city);
        Assertions.assertEquals(updated,city);
    }
    @Test
    void deleteById()
    {
        cityService.deleteById(1L);
        Mockito.verify(cityRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void findById_returnsCity()
    {
        City city=new City();
        Mockito.when(cityRepository.findById(Mockito.any())).thenReturn(Optional.of(city));
        City actual=cityService.findById(1L);
        Assertions.assertEquals(actual,city);
    }
    @Test
    void findById_throwsEntityNotFoundException()
    {
        Mockito.when(cityRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->cityService.findById(1L));
    }
    @Test
    void findAll()
    {
        List<City> cities=List.of(new City());
        Mockito.when(cityRepository.findAll()).thenReturn(cities);
        List<City> actual=cityService.findAll();
        Assertions.assertEquals(actual,cities);
    }
}