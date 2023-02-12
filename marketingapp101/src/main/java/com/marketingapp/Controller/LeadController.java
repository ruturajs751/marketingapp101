package com.marketingapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketingapp.Services.LeadService;
import com.marketingapp.dto.LeadData;
import com.marketingapp.entities.Lead;
import com.marketingapp.util.EmailService;
import com.marketingapp.util.EmailServiceImpl;

@Controller
public class LeadController {
	@Autowired
	private EmailService emailService;
	
	
	@Autowired
	private LeadService LeadService;
	
	@RequestMapping("/viewLeadPage")
	public String viewCreateLeadPage() {
		return "create_lead";
	}
	@RequestMapping(value="/saveLead",method=RequestMethod.POST)
	public String saveLead(@ModelAttribute("lead") Lead l,ModelMap model) {
		model.addAttribute("msg", "LEad is saved");
		LeadService.saveOneLead(l);
		emailService.sendSimpleMail(l.getEmail(), "test", "mmmmm");
		return "create_lead";
		
	}
//	@RequestMapping(value="/saveLead",method=RequestMethod.POST)
//	public String saveLead(@RequestParam("name")String fname,@RequestParam("lastName")String lname,@RequestParam("email")String email,@RequestParam("mobile")String mobile) {
//	Lead lead=new Lead();
//	lead.setFirstName(fname);
//	lead.setLastName(lname);
//	lead.setEmail(email);
//	lead.setMobile(mobile);
//	LeadService.saveOneLead(lead);
//		return "create_lead";
//		
//	}
//	@RequestMapping(value="/saveLead",method=RequestMethod.POST)
//	public String saveLead(LeadData data) {
//		Lead l=new Lead();
//		l.setFirstName(data.getFirstName());
//		l.setLastName(data.getLastName());
//		l.setEmail(data.getEmail());
//		l.setMobile(data.getMobile());
//		LeadService.saveOneLead(l);
//		return "create_lead";
//		
//	}
	@RequestMapping("/listall")
	public String listAllLeads(ModelMap model) {
		List<Lead> leads = LeadService.listAllLeads();
		model.addAttribute("leads", leads);
		return "list_leads";
		
	}
	@RequestMapping("/deleteLead")
	public String deleteLead(@RequestParam("id")long id,ModelMap model) {
		LeadService.deleteOneLead(id);
		List<Lead> leads = LeadService.listAllLeads();
		model.addAttribute("leads", leads);
		return "list_leads";

	}
	@RequestMapping("/updateLead")
	public String updateLead(@RequestParam("id") long id,Model model) {
		Lead lead = LeadService.getById(id);
		model.addAttribute("lead", lead);
		return "update_lead";
		
	}
	@RequestMapping(value="/updateOneLead",method=RequestMethod.POST)
	public String updateOneLead(@ModelAttribute("lead") Lead l,Model model) {
		
		LeadService.saveOneLead(l);
		List<Lead> leads = LeadService.listAllLeads();
		model.addAttribute("leads", leads);
		return "list_leads";
		
	}
	

}
