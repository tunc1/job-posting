package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import app.entity.Member;
import app.repository.MemberRepository;
import javax.persistence.EntityNotFoundException;

@Service
public class MemberService
{
	private MemberRepository memberRepository;
	public MemberService(MemberRepository memberRepository)
	{
		this.memberRepository=memberRepository;
	}
	public Member save(Member member)
	{
		return memberRepository.save(member);
	}
	public Member update(Member member)
	{
		return memberRepository.save(member);
	}
	public void deleteById(Long id)
	{
		memberRepository.deleteById(id);
	}
	public Member findById(Long id)
	{
		return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
	public List<Member> findAll()
	{
		return memberRepository.findAll();
	}
}