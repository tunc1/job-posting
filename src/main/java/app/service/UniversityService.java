package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.University;
import app.repository.UniversityRepository;
import javax.persistence.EntityNotFoundException;

@Service
public class UniversityService
{
	private UniversityRepository universityRepository;
	public UniversityService(UniversityRepository universityRepository)
	{
		this.universityRepository=universityRepository;
	}
	public University save(University university)
	{
		return universityRepository.save(university);
	}
	public University update(University university)
	{
		return universityRepository.save(university);
	}
	public void deleteById(Long id)
	{
		universityRepository.deleteById(id);
	}
	public University findById(Long id)
	{
		return universityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<University> findAll()
	{
		return universityRepository.findAll();
	}
}