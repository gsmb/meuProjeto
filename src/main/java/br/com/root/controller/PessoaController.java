package br.com.root.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.root.exception.ResourceNotFoundException;
import br.com.root.model.PessoaModel;
import br.com.root.model.ResponseModel;
import br.com.root.repository.PessoaRepository;
import br.com.root.utils.CpfCnpjUtils;

@RestController
@RequestMapping("/api")
public class PessoaController {

	@Autowired
	PessoaRepository pessoaRepository;

    @CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/pessoas")
	public List<PessoaModel> getAllPessoas() {
		return pessoaRepository.findAll();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/pessoas")
	public ResponseModel criarPessoa(@RequestBody PessoaModel pessoa) throws ParseException {

		try {
			pessoa.formatarData(pessoa.getDataNascimento());
			CpfCnpjUtils.isValid(pessoa.getCpf());
			this.pessoaRepository.save(pessoa);
			return new ResponseModel(1, "Registro Salvo com Sucesso");
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
	}

	@GetMapping("/pessoas/{id}")
	public ResponseModel obterPessoa(@PathVariable(value = "id") Long pessoaId) {

		try {
				this.pessoaRepository.findById(pessoaId);
				return new ResponseModel(1, "Pessoa retornada com sucesso");
		}catch (Exception e) {
				return new ResponseModel(0, e.getMessage());
		}
	}

	@PutMapping("/pessoas/{id}")
	public ResponseModel atualizarPessoa(@PathVariable(value = "id") Long pessoaId, @Valid @RequestBody PessoaModel pessoaDetalhes) {

		try {
			this.pessoaRepository.save(pessoaDetalhes);	
			return new ResponseModel(1, "Pessoa retornada com sucesso");
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
		

	}

	@DeleteMapping("/pessoas/{id}")
	public ResponseModel apagandoPessoa(@PathVariable(value = "id") Long pessoaId) {
		
		try {
			PessoaModel pessoa = pessoaRepository.findById(pessoaId).orElseThrow(() ->
                    new ResourceNotFoundException("Pessoa", "id", pessoaId));
			pessoaRepository.delete(pessoa);
			return new ResponseModel(1, "Pessoa deletada com sucesso");
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
		
	}
}
