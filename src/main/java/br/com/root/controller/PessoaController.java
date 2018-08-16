package br.com.root.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
import br.com.root.model.PessoaModel;
import br.com.root.model.ResponseModel;
import br.com.root.repository.PessoaRepository;

@RestController
@RequestMapping("/api")
public class PessoaController {

	@Autowired
	PessoaRepository pessoaRepository;

	@GetMapping("/pessoas")
	public List<PessoaModel> getAllPessoas() {
		return pessoaRepository.findAll();
	}

	@PostMapping("/pessoas")
	public ResponseModel criarPessoa(@RequestBody PessoaModel pessoa) throws ParseException {
		
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			cal.setTime(sdf.parse(pessoa.getDataNascimento()));// all done
			this.pessoaRepository.save(pessoa);
			return new ResponseModel(1, "Resgistro Salvo com Sucesso");
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		} 
	}

	@GetMapping("/pessoas/{id}")
	public PessoaModel obterPessoa(@PathVariable(value = "id") Long pessoaId) {
		return pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa", "id", pessoaId));
	}

	@PutMapping("/pessoas/{id}")
	public PessoaModel atualizarPessoa(@PathVariable(value = "id") Long pessoaId,
			@Valid @RequestBody PessoaModel pessoaDetalhes) {

		PessoaModel pessoa = pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa", "id", pessoaId));

		PessoaModel pessoaAtualizada = pessoaRepository.save(pessoaDetalhes);
		return pessoaAtualizada;

	}

	@DeleteMapping("/pessoas/{id}")
	public ResponseEntity<?> apagandoPessoa(@PathVariable(value = "id") Long pessoaId) {
		PessoaModel pessoa = pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa", "id", pessoaId));
		pessoaRepository.delete(pessoa);
		return ResponseEntity.ok().build();
	}
}
