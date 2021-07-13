package app.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.entity.Member;
import app.exception.ConflictException;
import app.exception.UnauthorizedException;
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
	public boolean existsByUsername(String username)
	{
		return memberRepository.existsByUsername(username);
	}
	public boolean existsByEmail(String email)
	{
		return memberRepository.existsByEmail(email);
	}
	public Member findByUsername(String username)
	{
		return memberRepository.findByUsername(username);
	}
	public Member save(Member member)
	{
		if(existsByUsername(member.getUsername()))
			throw new ConflictException("Another user uses this username");
		else if(existsByEmail(member.getEmail()))
			throw new ConflictException("Another user uses this email");
		else
		{
			member.setCredentialsNonExpired(true);
			member.setAccountNonLocked(true);
			member.setAccountNonExpired(true);
			member.setEnabled(true);
			member.setPassword(passwordEncoder.encode(member.getPassword()));
			return memberRepository.save(member);
		}
	}
	public Member update(Member member)
	{
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		return memberRepository.save(member);
	}
	public void deleteById(Long id,Member member)
	{
		if(member.getId()==id)
			memberRepository.deleteById(id);
		else
			throw new UnauthorizedException();
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