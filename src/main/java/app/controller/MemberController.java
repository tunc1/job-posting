package app.controller;

import app.entity.Member;
import app.exception.ConflictException;
import app.exception.ExceptionMessage;
import app.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/member")
public class MemberController
{
	private MemberService memberService;
	public MemberController(MemberService memberService)
	{
		this.memberService=memberService;
	}
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Member member)
	{
		try
		{
			return new ResponseEntity<>(memberService.save(member),HttpStatus.CREATED);
		}
		catch (ConflictException e)
		{
			return new ResponseEntity<>(new ExceptionMessage(e.getMessage()),HttpStatus.CONFLICT);
		}
	}
	@PutMapping
	public ResponseEntity<Object> update(@RequestBody Member member,Authentication authentication)
	{
		try
		{
			Member member2=(Member)authentication.getPrincipal();
			member.setId(member2.getId());
			member.getUser().setPassword(member2.getUser().getPassword());
			return new ResponseEntity<>(memberService.update(member),HttpStatus.OK);
		}
		catch (ConflictException e)
		{
			return new ResponseEntity<>(new ExceptionMessage(e.getMessage()),HttpStatus.CONFLICT);
		}
	}
	@GetMapping("/{id}")
	public Member findById(@PathVariable Long id)
	{
		return memberService.findById(id);
	}
	@GetMapping
	public List<Member> findAll()
	{
		return memberService.findAll();
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		memberService.deleteById(id,member);
	}
}