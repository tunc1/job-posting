package app.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.entity.Member;
import app.repository.MemberRepository;
import javax.persistence.EntityNotFoundException;

@Service
public class MemberService
{
	private MemberRepository memberRepository;
	private PasswordEncoder passwordEncoder;
	public MemberService(MemberRepository memberRepository,PasswordEncoder passwordEncoder)
	{
		this.memberRepository=memberRepository;
		this.passwordEncoder=passwordEncoder;
	}
	public Member save(Member member)
	{
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		return memberRepository.save(member);
	}
	public Member update(Member member)
	{
		member.setPassword(passwordEncoder.encode(member.getPassword()));
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