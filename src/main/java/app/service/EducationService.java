package app.service;

import java.util.List;

import app.entity.HasUser;
import app.entity.Member;
import app.entity.Role;
import app.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import app.entity.Education;
import app.repository.EducationRepository;

@Service
public class EducationService
{
	private EducationRepository educationRepository;
	public EducationService(EducationRepository educationRepository)
	{
		this.educationRepository=educationRepository;
	}
	public Education save(Education education)
	{
		return educationRepository.save(education);
	}
	public Education update(Education education,Member member)
	{
		if(educationRepository.findById(education.getId()).get().getMember().equals(member))
			return educationRepository.save(education);
		else
			throw new UnauthorizedException();
	}
	public void deleteById(Long id,Member member)
	{
		if(educationRepository.findById(id).get().getMember().equals(member))
			educationRepository.deleteById(id);
		else
			throw new UnauthorizedException();
	}
	public List<Education> findAllByMemberId(Long memberId,HasUser hasUser)
	{
		if(hasUser.getUser().getRole().equals(Role.ADMIN)||hasUser.getUser().getRole().equals(Role.MANAGER))
			return educationRepository.findAllByMemberId(memberId);
		else
		{
			Member member=(Member)hasUser;
			if(member.getId().equals(memberId))
				return educationRepository.findAllByMemberId(memberId);
		}
		throw new UnauthorizedException();
	}
}