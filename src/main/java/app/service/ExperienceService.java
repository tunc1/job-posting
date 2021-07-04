package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import app.entity.Experience;
import app.entity.Member;
import app.exception.UnauthorizedException;
import app.repository.ExperienceRepository;

@Service
public class ExperienceService
{
	private ExperienceRepository experienceRepository;
	public ExperienceService(ExperienceRepository experienceRepository)
	{
		this.experienceRepository=experienceRepository;
	}
	public List<Experience> findByMember(Member member)
	{
		return experienceRepository.findByMember(member);
	}
	public Experience save(Experience experience)
	{
		return experienceRepository.save(experience);
	}
	public boolean existsById(Long id)
	{
		if(experienceRepository.existsById(id))
			return true;
		throw new EntityNotFoundException();
	}
	public boolean belongsTo(Long id,Member member)
	{
		Experience experience=experienceRepository.findById(id).get();
		if(experience.getMember().equals(member))
			return true;
		throw new UnauthorizedException();
	}
	public Experience update(Experience experience,Member member)
	{
		if(existsById(experience.getId()))
		{
			if(belongsTo(experience.getId(),member))
			{
				experience.setMember(member);
				return experienceRepository.save(experience);
			}
		}
		return null;
	}
	public void deleteById(Long id,Member member)
	{
		if(existsById(id))
		{
			if(belongsTo(id,member))
				experienceRepository.deleteById(id);
		}
	}
	public Experience findById(Long id,Member member)
	{
		if(existsById(id))
		{
			if(belongsTo(id,member))
				return experienceRepository.findById(id).get();
		}
		return null;
	}
}