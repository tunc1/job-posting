package app.controller;

import app.entity.Language;
import app.service.LanguageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class LanguageControllerTest
{
    @Mock
    LanguageService languageService;
    LanguageController languageController;

    @BeforeEach
    void setUp()
    {
        languageController=new LanguageController(languageService);
    }
    @Test
    void save()
    {
        Language language=new Language();
        Mockito.when(languageService.save(Mockito.any())).thenAnswer(e->e.getArgument(0,Language.class));
        Language saved=languageController.save(language);
        Assertions.assertEquals(saved,language);
    }
    @Test
    void update()
    {
        long id=1L;
        Language language=new Language();
        Mockito.when(languageService.update(Mockito.any())).thenAnswer(e->e.getArgument(0,Language.class));
        Language updated=languageController.update(language,id);
        Assertions.assertEquals(updated,language);
        Assertions.assertEquals(updated.getId(),id);
    }
    @Test
    void deleteById()
    {
        languageController.deleteById(1L);
        Mockito.verify(languageService).deleteById(Mockito.anyLong());
    }
    @Test
    void findById_returnsLanguage()
    {
        Language language=new Language();
        Mockito.when(languageService.findById(Mockito.any())).thenReturn(language);
        Language actual=languageController.findById(1L);
        Assertions.assertEquals(actual,language);
    }
    @Test
    void findAll()
    {
        List<Language> languages=List.of(new Language());
        Mockito.when(languageService.findAll()).thenReturn(languages);
        List<Language> actual=languageController.findAll();
        Assertions.assertEquals(actual,languages);
    }
}