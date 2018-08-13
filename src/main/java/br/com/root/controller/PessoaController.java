package br.com.root.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.root.exception.ResourceNotFoundException;
import br.com.root.model.Pessoa;
import br.com.root.repository.PessoaRepository;

@RestController
@RequestMapping("/api")
public class PessoaController {

	@Autowired
	PessoaRepository pessoaRepository;

	@GetMapping("/pessoas")
	public List<Pessoa> getAllPessoas() {
		return pessoaRepository.findAll();
	}

	@PostMapping("pessoas")
	public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	@GetMapping("/pessoas/{id}")
	public Pessoa obterPessoa(@PathVariable(value = "id") Long pessoaId) {
		return pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa", "id", pessoaId));
	}

	@PutMapping("/pessoas/{id}")
	public Pessoa atualizarPessoa(@PathVariable(value = "id") Long pessoaId,
			@Valid @RequestBody Pessoa pessoaDetalhes) {

		Pessoa pessoa = pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa", "id", pessoaId));

		Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);
		return pessoaAtualizada;

	}

	@DeleteMapping("/pessoas/{id}")
	public ResponseEntity<?> apagandoPessoa(@PathVariable(value = "id") Long pessoaId) {
		Pessoa pessoa = pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa", "id", pessoaId));
		pessoaRepository.delete(pessoa);
		return ResponseEntity.ok().build();
	}
}
