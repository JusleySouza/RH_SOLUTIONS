package com.rh.management.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.rh.management.model.Compensation;
import com.rh.management.model.Employee;
import com.rh.management.model.dto.CompensationDTO;
import com.rh.management.model.dto.CompensationDateDTO;
import com.rh.management.model.dto.CompensationDetailsDTO;
import com.rh.management.model.dto.CompensationSearchDTO;
import com.rh.management.model.dto.EmployeeDTO;
import com.rh.management.model.dto.EmployeeFullNameDTO;
import com.rh.management.model.dto.EmployeeSearchDTO;
import com.rh.management.services.CompensationServices;
import com.rh.management.services.EmployeeServices;

@Controller
@RequestMapping("/")
public class RhController {

	@Autowired
	EmployeeServices services;
	@Autowired
	CompensationServices servicesComp;

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
		if (response.getStatusCode() == HttpStatus.CREATED) {
			redirectAttributes.addFlashAttribute("message", "SUCESSO");
		} else if (response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
			redirectAttributes.addFlashAttribute("message", "EXISTENTE");
		} else {
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
	public RedirectView salvarEdicao(Employee employee, RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView();
		model.setViewName("update");
		ResponseEntity<Employee> response = services.update(employee);

		if (response.getStatusCode() == HttpStatus.CREATED) {
			redirectAttributes.addFlashAttribute("message", "SUCESSO");
		} else if (response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
			redirectAttributes.addFlashAttribute("message", "EXISTENTE");
		} else {
			redirectAttributes.addFlashAttribute("message", "ERRO");
		}
		model.addObject("actual", employee);
		return new RedirectView("/editar-cadastro/" + employee.getId(), true);
	}

	@GetMapping("/cadastro-compensacao/{id}")
	public ModelAndView adicionarCompensacao(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		EmployeeFullNameDTO employee = new EmployeeFullNameDTO(services.findById(id));
		Compensation compensation = new Compensation();
		model.setViewName("addiction");
		model.addObject("actual", employee);
		model.addObject("compensation", compensation);
		return model;
	}

	@PostMapping("/salvar-compensacao/{id}")
	public RedirectView salvarCompensacao(@PathVariable("id") Long id, CompensationDTO compensation,
			RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView();
		Compensation compensation1 = new Compensation();
		model.setViewName("addiction");
		model.addObject("actual", compensation1);
		ResponseEntity<Compensation> response = servicesComp.create(id, compensation);
		if (response.getStatusCode() == HttpStatus.CREATED) {
			redirectAttributes.addFlashAttribute("message", "SUCESSO");
		} else if (response.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
			redirectAttributes.addFlashAttribute("message", "EXISTENTE");
		} else {
			redirectAttributes.addFlashAttribute("message", "ERRO");
		}
		return new RedirectView("/cadastro-compensacao/" + id, true);
	}

	@GetMapping("/mostrar-compensacao/{id}")
	public ModelAndView mostrarCompensacao(@PathVariable("id") Long id) {
		CompensationSearchDTO compensationSearchDTO = new CompensationSearchDTO();
		ModelAndView model = new ModelAndView();
		model.setViewName("display");
		EmployeeFullNameDTO employee = new EmployeeFullNameDTO(services.findById(id));
		model.addObject("actual", employee);
		model.addObject("compensationSearch", compensationSearchDTO);
		return model;
	}

	@PostMapping("/resultado-pesquisa/{id}")
	public RedirectView exibir(@PathVariable("id") Long id, CompensationSearchDTO search,
			RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView();
		model.setViewName("display");
		List<CompensationDateDTO> response = servicesComp.display(id, search);
		redirectAttributes.addFlashAttribute("compensations", response);
		return new RedirectView("/mostrar-compensacao/" + id, true);
	}

	@GetMapping("/exibir-detalhes/{id}/{date}")
	public ModelAndView exibirDetalhes(@PathVariable("id") Long id, @PathVariable("date") String date) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy"); 
		Date data = formato.parse(date);
		CompensationDetailsDTO response = servicesComp.details(id, data);
		ModelAndView model = new ModelAndView();
		model.setViewName("details");
		model.addObject("detalhes", response);
		return model;
	}
	
	@GetMapping("/editar-compensacao/{id}")
	public ModelAndView editarCompensacao(@PathVariable("id") Long id) {
		Compensation compensacao = servicesComp.edit(id);
		EmployeeFullNameDTO employee = new EmployeeFullNameDTO(services.findById(compensacao.getEmployee().getId()));
		ModelAndView model = new ModelAndView();
		model.setViewName("addiction");
		model.addObject("actual", employee);
		model.addObject("compensation", compensacao);
		return model;
	}

}
