package app.service;

import app.entity.Language;
import app.repository.LanguageRepository;
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
class LanguageServiceTest
{
    @Mock
    LanguageRepository languageRepository;
    LanguageService languageService;
    
    @BeforeEach
    void setUp()
    {
        languageService=new LanguageService(languageRepository);
    }
    @Test
    void save()
    {
        Language language=new Language();
        Mockito.when(languageRepository.save(Mockito.any())).thenAnswer(e->e.getArgument(0,Language.class));
        Language saved=languageService.save(language);
        Assertions.assertEquals(saved,language);
    }
    @Test
    void update()
    {
        Language language=new Language();
        Mockito.when(languageRepository.save(Mockito.any())).thenAnswer(e->e.getArgument(0,Language.class));
        Language updated=languageService.update(language);
        Assertions.assertEquals(updated,language);
    }
    @Test
    void deleteById()
    {
        languageService.deleteById(1L);
        Mockito.verify(languageRepository).deleteById(Mockito.anyLong());
    }
    @Test
    void findById_returnsLanguage()
    {
        Language language=new Language();
        Mockito.when(languageRepository.findById(Mockito.any())).thenReturn(Optional.of(language));
        Language actual=languageService.findById(1L);
        Assertions.assertEquals(actual,language);
    }
    @Test
    void findById_throwsEntityNotFoundException()
    {
        Mockito.when(languageRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()->languageService.findById(1L));
    }
    @Test
    void findAll()
    {
        List<Language> languages=List.of(new Language());
        Mockito.when(languageRepository.findAll()).thenReturn(languages);
        List<Language> actual=languageService.findAll();
        Assertions.assertEquals(actual,languages);
    }
}