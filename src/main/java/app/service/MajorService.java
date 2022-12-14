package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.Major;
import app.repository.MajorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MajorService
{
	private MajorRepository majorRepository;
	public MajorService(MajorRepository majorRepository)
	{
		this.majorRepository=majorRepository;
	}
	public Major save(Major major)
	{
		return majorRepository.save(major);
	}
	public Major update(Major major)
	{
		return majorRepository.save(major);
	}
	public void deleteById(Long id)
	{
		majorRepository.deleteById(id);
	}
	public Major findById(Long id)
	{
		return majorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<Major> findAll()
	{
		return majorRepository.findAll();
	}
}