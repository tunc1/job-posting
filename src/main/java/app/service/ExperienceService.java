package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.Experience;
import app.repository.ExperienceRepository;

@Service
public class ExperienceService
{
	private ExperienceRepository experienceRepository;
	public ExperienceService(ExperienceRepository experienceRepository)
	{
		this.experienceRepository=experienceRepository;
	}
	public Experience save(Experience experience)
	{
		return experienceRepository.save(experience);
	}
	public Experience update(Experience experience)
	{
		return experienceRepository.save(experience);
	}
	public void deleteById(Long id)
	{
		experienceRepository.deleteById(id);
	}
	public Experience findById(Long id)
	{
		return experienceRepository.findById(id).orElse(null);
	}
	public List<Experience> findAll()
	{
		return experienceRepository.findAll();
	}
}