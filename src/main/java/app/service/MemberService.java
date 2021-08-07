package app.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import app.entity.Member;
import app.entity.Role;
import app.exception.ConflictException;
import app.exception.UnauthorizedException;
import app.repository.MemberRepository;
import app.util.UserUtil;

import javax.persistence.EntityNotFoundException;

@Service
public class MemberService
{
	private MemberRepository memberRepository;
	private PasswordEncoder passwordEncoder;
	private UserUtil userUtil;
	public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, UserUtil userUtil)
	{
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.userUtil = userUtil;
	}
	public void throwExceptionIfEmailConflicts(String email)
	{
		if(memberRepository.existsByEmail(email))
			throw new ConflictException("Another user uses this email");
	}
	public Member save(Member member)
	{
		userUtil.throwExceptionIfUsernameConflicts(member.getUser().getUsername());
		throwExceptionIfEmailConflicts(member.getEmail());
		member.getUser().setRole(Role.MEMBER);
		member.getUser().setCredentialsNonExpired(true);
		member.getUser().setAccountNonLocked(true);
		member.getUser().setAccountNonExpired(true);
		member.getUser().setEnabled(true);
		member.getUser().setPassword(passwordEncoder.encode(member.getUser().getPassword()));
		return memberRepository.save(member);
	}
	public Member update(Member member)
	{
		userUtil.throwExceptionIfUsernameConflicts(member.getUser().getUsername());
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
    public Member findByUserUsername(String username)
    {
        return memberRepository.findByUserUsername(username);
    }
}