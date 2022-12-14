package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.Language;
import app.repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LanguageService
{
	private LanguageRepository languageRepository;
	public LanguageService(LanguageRepository languageRepository)
	{
		this.languageRepository=languageRepository;
	}
	public Language save(Language language)
	{
		return languageRepository.save(language);
	}
	public Language update(Language language)
	{
		return languageRepository.save(language);
	}
	public void deleteById(Long id)
	{
		languageRepository.deleteById(id);
	}
	public Language findById(Long id)
	{
		return languageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<Language> findAll()
	{
		return languageRepository.findAll();
	}
}