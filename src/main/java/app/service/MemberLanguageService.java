package app.service;

import java.util.List;

import app.entity.HasUser;
import app.entity.Member;
import app.consts.Role;
import app.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import app.entity.MemberLanguage;
import app.repository.MemberLanguageRepository;

@Service
public class MemberLanguageService
{
	private MemberLanguageRepository memberLanguageRepository;
	public MemberLanguageService(MemberLanguageRepository memberLanguageRepository)
	{
		this.memberLanguageRepository=memberLanguageRepository;
	}
	public MemberLanguage save(MemberLanguage memberLanguage)
	{
		return memberLanguageRepository.save(memberLanguage);
	}
	public MemberLanguage update(MemberLanguage memberLanguage,Member member)
	{
		if(memberLanguageRepository.findById(memberLanguage.getId()).get().getMember().equals(member))
			return memberLanguageRepository.save(memberLanguage);
		else
			throw new UnauthorizedException();
	}
	public void deleteById(Long id,Member member)
	{
		if(memberLanguageRepository.findById(id).get().getMember().equals(member))
			memberLanguageRepository.deleteById(id);
		else
			throw new UnauthorizedException();
	}
	public List<MemberLanguage> findAllByMemberId(long id,HasUser hasUser)
	{
		if(hasUser.getUser().getRole().equals(Role.ADMIN)||hasUser.getUser().getRole().equals(Role.MANAGER))
			return memberLanguageRepository.findAllByMemberId(id);
		else
		{
			Member member=(Member)hasUser;
			if(member.getId().equals(id))
				return memberLanguageRepository.findAllByMemberId(id);
		}
		throw new UnauthorizedException();
	}
}