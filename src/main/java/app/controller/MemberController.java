package app.controller;

import app.entity.Member;
import app.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

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
	@ResponseStatus(code=HttpStatus.CREATED)
	public Member save(@RequestBody Member member)
	{
		return memberService.save(member);
	}
	@PutMapping("/{id}")
	public Member update(@RequestBody Member member,@PathVariable Long id)
	{
		member.setId(id);
		return memberService.update(member);
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
	public void deleteById(@PathVariable Long id)
	{
		memberService.deleteById(id);
	}
}