package br.com.root.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.root.model.PessoaModel;
import br.com.root.model.ResponseModel;
import br.com.root.repository.PessoaRepository;

@RestController
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseModel salvar(@RequestBody PessoaModel pessoa) {
		try {
			this.pessoaRepository.save(pessoa);
			return new ResponseModel(1, "Resgistro Salvo com Sucesso");
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
	}
	
	
	@PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseModel atualizar(@RequestBody PessoaModel pessoa) {
		try {
			this.pessoaRepository.save(pessoa);
			return new ResponseModel(1, "Registro atualizado com Sucesso");
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
		
	}
	
	
	public List<PessoaModel> consultar () {
		return this.pessoaRepository.findAll();
	}
	
	
	
	@DeleteMapping("/{id}")
    public ResponseModel delete(@PathVariable Long id) {
        Optional<PessoaModel> pessoaModel = pessoaRepository.findById(id);
        try {
			pessoaRepository.deleteById(pessoaModel.get().getId());
			return new ResponseModel(1, "Registro deletado com sucesso");
		} catch (Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
    }
}
