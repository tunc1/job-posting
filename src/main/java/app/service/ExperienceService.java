package app.service;

import app.entity.Experience;
import app.entity.IUser;
import app.entity.Member;
import app.consts.Role;
import app.exception.UnauthorizedException;
import app.repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public Experience update(Experience experience,Member member)
	{
		if(experienceRepository.findById(experience.getId()).get().getMember().equals(member))
			return experienceRepository.save(experience);
		else
			throw new UnauthorizedException();
	}
	public void deleteById(Long id,Member member)
	{
		if(experienceRepository.findById(id).get().getMember().equals(member))
			experienceRepository.deleteById(id);
		else
			throw new UnauthorizedException();
	}
	public List<Experience> findAllByMemberId(Long memberId,IUser user)
	{
		if(user.getUser().getRole().equals(Role.ADMIN)||user.getUser().getRole().equals(Role.MANAGER))
			return experienceRepository.findAllByMemberId(memberId);
		else
		{
			Member member=(Member)user;
			if(member.getId().equals(memberId))
				return experienceRepository.findAllByMemberId(memberId);
		}
		throw new UnauthorizedException();
	}
}