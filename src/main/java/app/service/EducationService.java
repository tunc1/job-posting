package app.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import app.entity.Education;
import app.entity.Member;
import app.exception.UnauthorizedException;
import app.repository.EducationRepository;

@Service
public class EducationService
{
	private EducationRepository educationRepository;
	public EducationService(EducationRepository educationRepository)
	{
		this.educationRepository=educationRepository;
	}
	public void throwExceptionIfDatesInvalid(Education education)
	{
		int compare=education.getStartedAt().compareTo(education.getFinishedAt());
		if(compare==0)
			throw new IllegalArgumentException("Start and finish dates can not be same");
		else if(compare==1)
			throw new IllegalArgumentException("Start date can not be greater than finish date");
	}
	public void throwExceptionIfDifferentMember(Long id,Member member)
	{
		Long memberId=educationRepository.getMemberId(id);
		if(memberId!=member.getId())
			throw new UnauthorizedException();
	}
	public void throwExceptionIfNotExists(Long id)
	{
		if(!educationRepository.existsById(id))
			throw new EntityNotFoundException();
	} 
	public Education save(Education education)
	{
		throwExceptionIfDatesInvalid(education);
		return educationRepository.save(education);
	}
	public Education update(Education education,Member member)
	{
		throwExceptionIfDifferentMember(education.getId(),member);
		throwExceptionIfDatesInvalid(education);
		return educationRepository.save(education);
	}
	public void deleteById(Long id,Member member)
	{
		throwExceptionIfNotExists(id);
		throwExceptionIfDifferentMember(id,member);
		educationRepository.deleteById(id);
	}
	public Education findById(Long id)
	{
		throwExceptionIfNotExists(id);
		return educationRepository.findById(id).get();
	}
	public List<Education> findByMember(Member member)
	{
		return educationRepository.findByMember(member);
	}
}