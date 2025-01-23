package com.oplusz.festgo.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oplusz.festgo.domain.Members;
import com.oplusz.festgo.dto.MembersSignInDto;
import com.oplusz.festgo.service.MembersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {
	private final MembersService membersService;
	
	@PostMapping("/signin")
	public ResponseEntity<Members> signIn(@RequestBody MembersSignInDto dto) {
		
		log.debug("signIn(dto={})", dto);
		
		Members members = membersService.read(dto);
		
		return ResponseEntity.ok(members);
	}
}
