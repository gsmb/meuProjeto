package br.com.root.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InicioController {

	@RequestMapping("/")
	public String index() {
		return "Bem-vindo";
	}
}
