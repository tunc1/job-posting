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
	public Member findByUsername(String username)
	{
		return memberRepository.findByUsername(username);
	}
	public void throwExceptionIfUsernameConflicts(String username)
	{
		if(existsByUsername(username))
			throw new ConflictException("Another user uses this username");
	}
	public void throwExceptionIfEmailConflicts(String email)
	{
		if(memberRepository.existsByEmail(email))
			throw new ConflictException("Another user uses this email");
	}
	public Member save(Member member)
	{
		throwExceptionIfUsernameConflicts(member.getUsername());
		throwExceptionIfEmailConflicts(member.getEmail());
		member.setCredentialsNonExpired(true);
		member.setAccountNonLocked(true);
		member.setAccountNonExpired(true);
		member.setEnabled(true);
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		return memberRepository.save(member);
	}
	public Member update(Member member)
	{
		throwExceptionIfUsernameConflicts(member.getUsername());
		throwExceptionIfEmailConflicts(member.getEmail());
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