package app.controller;

import app.entity.Country;
import app.service.CountryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest
{
    @Mock
    CountryService countryService;
    CountryController countryController;

    @BeforeEach
    void setUp()
    {
        countryController=new CountryController(countryService);
    }
    @Test
    void save()
    {
        Country country=new Country();
        Mockito.when(countryService.save(Mockito.any())).thenAnswer(e->e.getArgument(0,Country.class));
        Country saved=countryController.save(country);
        Assertions.assertEquals(saved,country);
    }
    @Test
    void update()
    {
        long id=1L;
        Country country=new Country();
        Mockito.when(countryService.update(Mockito.any())).thenAnswer(e->e.getArgument(0,Country.class));
        Country updated=countryController.update(country,id);
        Assertions.assertEquals(updated,country);
        Assertions.assertEquals(updated.getId(),id);
    }
    @Test
    void deleteById()
    {
        countryController.deleteById(1L);
        Mockito.verify(countryService).deleteById(Mockito.anyLong());
    }
    @Test
    void findById_returnsCountry()
    {
        Country country=new Country();
        Mockito.when(countryService.findById(Mockito.any())).thenReturn(country);
        Country actual=countryController.findById(1L);
        Assertions.assertEquals(actual,country);
    }
    @Test
    void findAll()
    {
        List<Country> countries=List.of(new Country());
        Mockito.when(countryService.findAll()).thenReturn(countries);
        List<Country> actual=countryController.findAll();
        Assertions.assertEquals(actual,countries);
    }
}