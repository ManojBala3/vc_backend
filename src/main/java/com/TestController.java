package com;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@CrossOrigin
public class TestController {

	@GetMapping("/")
	public String saveairline()
	{
		System.out.println("coming inside");
		return "Working";
	}
}
