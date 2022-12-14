package app.service;

import app.entity.Country;
import app.repository.CountryRepository;
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
class CountryServiceTest
{
    @Mock
    CountryRepository countryRepository;
    CountryService countryService;

    @BeforeEach
    void setUp()
    {
        countryService=new CountryService(countryRepository);
    }
    @Test
    void save()
    {
        Country country=new Country();
        Mockito.when(countryRepository.save(Mockito.any())).thenAnswer(e->e.getArgument(0,Country.class));
        Country saved=countryService.save(country);
        Assertions.assertEquals(saved,country);
    }
    @Test
    void update()
    {
        Country country=new Country();
        Mockito.when(countryRepository.save(Mockito.any())).thenAnswer(e->e.getArgument(0,Country.class));
        Country updated=countryService.update(country);
        Assertions.assertEquals(updated,country);
    }
    @Test
    void deleteById()
    {
        countryService.deleteById(1L);
        Mockito.verify(countryRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void findById_returnsCountry()
    {
        Country country=new Country();
        Mockito.when(countryRepository.findById(Mockito.any())).thenReturn(Optional.of(country));
        Country actual=countryService.findById(1L);
        Assertions.assertEquals(actual,country);
    }
    @Test
    void findById_throwsEntityNotFoundException()
    {
        Mockito.when(countryRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->countryService.findById(1L));
    }
    @Test
    void findAll()
    {
        List<Country> countries=List.of(new Country());
        Mockito.when(countryRepository.findAll()).thenReturn(countries);
        List<Country> actual=countryService.findAll();
        Assertions.assertEquals(actual,countries);
    }
}