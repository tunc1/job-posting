package app.controller;

import app.entity.IUser;
import app.entity.Member;
import app.entity.MemberLanguage;
import app.service.MemberLanguageService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/member/{memberId}/language")
public class MemberLanguageController
{
	private MemberLanguageService memberLanguageService;
	public MemberLanguageController(MemberLanguageService memberLanguageService)
	{
		this.memberLanguageService=memberLanguageService;
	}
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public MemberLanguage save(@RequestBody MemberLanguage memberLanguage,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		memberLanguage.setMember(member);
		return memberLanguageService.save(memberLanguage);
	}
	@PutMapping("/{id}")
	public MemberLanguage update(@RequestBody MemberLanguage memberLanguage,@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		memberLanguage.setId(id);
		return memberLanguageService.update(memberLanguage,member);
	}
	@GetMapping
	public List<MemberLanguage> findAllByMemberId(Authentication authentication,@PathVariable long memberId)
	{
		IUser user=(IUser)authentication.getPrincipal();
		return memberLanguageService.findAllByMemberId(memberId,user);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id,Authentication authentication)
	{
		Member member=(Member)authentication.getPrincipal();
		memberLanguageService.deleteById(id,member);
	}
}