package app.controller;

import app.entity.City;
import app.service.CityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CityControllerTest
{
    @Mock
    CityService cityService;
    CityController cityController;

    @BeforeEach
    void setUp()
    {
        cityController=new CityController(cityService);
    }
    @Test
    void save()
    {
        City city=new City();
        Mockito.when(cityService.save(Mockito.any())).thenAnswer(e->e.getArgument(0,City.class));
        City saved=cityController.save(city);
        Assertions.assertEquals(saved,city);
    }
    @Test
    void update()
    {
        long id=1L;
        City city=new City();
        Mockito.when(cityService.update(Mockito.any())).thenAnswer(e->e.getArgument(0,City.class));
        City updated=cityController.update(city,id);
        Assertions.assertEquals(updated,city);
        Assertions.assertEquals(updated.getId(),id);
    }
    @Test
    void deleteById()
    {
        cityController.deleteById(1L);
        Mockito.verify(cityService).deleteById(Mockito.anyLong());
    }
    @Test
    void findById_returnsCity()
    {
        City city=new City();
        Mockito.when(cityService.findById(Mockito.any())).thenReturn(city);
        City actual=cityController.findById(1L);
        Assertions.assertEquals(actual,city);
    }
    @Test
    void findAll()
    {
        List<City> cities=List.of(new City());
        Mockito.when(cityService.findAll()).thenReturn(cities);
        List<City> actual=cityController.findAll();
        Assertions.assertEquals(actual,cities);
    }
}