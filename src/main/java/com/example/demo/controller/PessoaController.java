package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.Pessoa;
import com.example.demo.repository.PessoaRepository;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository repo;

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(Pessoa pessoa) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Pessoa/cadastrar");
		mv.addObject("pessoa", new Pessoa());
		return mv;
	}

	@PostMapping("/insertPessoa")
	public ModelAndView insertPessoa(@Valid Pessoa pessoa, BindingResult br) {
		ModelAndView mv = new ModelAndView();
		if (br.hasErrors()) {
			mv.setViewName("Pessoa/cadastrar");
			mv.addObject("pessoa");
		} else {
			mv.setViewName("redirect:/pessoas-adicionadas");
			repo.save(pessoa);
		}

		return mv;
	}

	@GetMapping("/pessoas-adicionadas")
	public ModelAndView listPessoas() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Pessoa/listPessoas");
		mv.addObject("listPessoas", repo.findAll());
		return mv;
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar (@PathVariable ("id")Long id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Pessoa/alterar");
		Pessoa pessoa = repo.getOne(id);
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	
	@PostMapping("/alterar")
	public ModelAndView alterar (Pessoa pessoa) {
		ModelAndView mv = new ModelAndView();
		repo.save(pessoa);
		mv.setViewName("redirect:/pessoas-adicionadas");
		return mv;
	}	
	
	@GetMapping("/excluir/{id}")
	public String excluirPessoa (@PathVariable ("id")Long id) {
		repo.deleteById(id);
		return "redirect:/pessoas-adicionadas";
	}
	
}