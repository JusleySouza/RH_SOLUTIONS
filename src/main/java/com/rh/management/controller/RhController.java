package com.rh.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.rh.management.model.Employee;
import com.rh.management.model.dto.EmployeeDTO;
import com.rh.management.model.dto.EmployeeSearchDTO;
import com.rh.management.services.EmployeeServices;

@Controller
@RequestMapping("/")
public class RhController {

	
	@Autowired
	EmployeeServices services;
	
	@GetMapping("/home")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	}
	
	@GetMapping("/formulario-cadastro")
	public ModelAndView create() {
		ModelAndView model = new ModelAndView();
		Employee employee = new Employee();
		model.setViewName("registration");
		model.addObject("actual", employee);
//		model.addObject("message", null);
		return model;
	}
	
	@PostMapping("/save")
	public RedirectView salvar(EmployeeDTO employee, RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView();
		Employee employe = new Employee();
		model.setViewName("registration");
		model.addObject("actual", employe);
		ResponseEntity<Employee> response = services.create(employee);
		if(response.getStatusCode() == HttpStatus.CREATED) {
			redirectAttributes.addFlashAttribute("message", "SUCESSO");
		}
		else if(response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
			redirectAttributes.addFlashAttribute("message", "EXISTENTE");
		}
		else {
			redirectAttributes.addFlashAttribute("message", "ERRO");			
		}
		return new RedirectView("/formulario-cadastro", true);
	}
	
	@GetMapping("/busca")
	public ModelAndView buscar() {
		Employee employe = new Employee();
		ModelAndView model = new ModelAndView();
		model.setViewName("search");
		model.addObject("actual", employe);
		return model;
		
	}
	
	@PostMapping("/pesquisa")
	public RedirectView pesquisar(EmployeeSearchDTO search, RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView();
		model.setViewName("search");
		List<Employee> result = services.search(search);
		redirectAttributes.addFlashAttribute("employyers", result);
		return new RedirectView("/busca", true);
	}
	
	@GetMapping("/editar-cadastro/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		Employee employee = services.edit(id);
		model.setViewName("update");
		model.addObject("actual", employee);
		return model;
	}
	
	@PostMapping("/save-edit")
	public RedirectView salvarEdicao( Employee employee, RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView();
		model.setViewName("update");
		ResponseEntity<Employee> response = services.update(employee);
		
		if(response.getStatusCode() == HttpStatus.CREATED) {
			redirectAttributes.addFlashAttribute("message", "SUCESSO");
		}
		else if(response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
			redirectAttributes.addFlashAttribute("message", "EXISTENTE");
		}
		else {
			redirectAttributes.addFlashAttribute("message", "ERRO");			
		}
		model.addObject("actual", employee);
		return new RedirectView("/editar-cadastro/"+ employee.getId(), true);
	}
	
	@GetMapping("/cadastro-compensacao")
	public ModelAndView adicionarCompensacao() {
		ModelAndView model = new ModelAndView();
		Employee employee = new Employee();
		model.setViewName("addiction");
		model.addObject("actual", employee);
//		model.addObject("message", null);
		return model;
	}
}
