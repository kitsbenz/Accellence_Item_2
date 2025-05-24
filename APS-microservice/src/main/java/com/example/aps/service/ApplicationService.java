package com.example.aps.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.aps.dto.ApplicationRequest;
import com.example.aps.dto.ApplicationResponse;
import com.example.aps.entity.Application;
import com.example.aps.repository.ApplicationRepository;

@Service
public class ApplicationService {

	private final ApplicationRepository repository;
	private final RestTemplate restTemplate = new RestTemplate();

	public ApplicationService(ApplicationRepository repository) {
		this.repository = repository;
	}

	public ApplicationResponse submitApplication(ApplicationRequest request) {
		Double income = request.getMonthlyIncome();
		Double expense = request.getMonthlyExpense();
		double repaymentCapacity = income - expense;

		Application app = new Application();
		app.setApplicantName(request.getApplicantName());
		app.setMonthlyIncome(income);
		app.setMonthlyExpense(expense);
		app.setRepaymentCapacity(repaymentCapacity);
		app.setCreatedAt(LocalDateTime.now());
		app.setUpdatedAt(LocalDateTime.now());

		app.setFinancialStatus(repaymentCapacity > 0 ? "SUFFICIENT" : "INSUFFICIENT");

		// Call mock credit verification microservice (simulate)
		boolean creditOk = callCreditVerificationMicroservice(request);
		app.setCreditCheckStatus(creditOk ? "VERIFIED" : "REJECTED");

		// Save to DB
		Application saved = repository.save(app);

		// Prepare response
		ApplicationResponse response = new ApplicationResponse();
		response.setId(saved.getId());
		response.setRepaymentCapacity(saved.getRepaymentCapacity());
		response.setCreditCheckStatus(saved.getCreditCheckStatus());
		response.setRepaymentCapacityStatus(saved.getFinancialStatus());

		return response;
	}

	private boolean callCreditVerificationMicroservice(ApplicationRequest request) {
		// Simulate always OK
		return true;
	}

	public Optional<Application> getApplication(Long id) {
		return repository.findById(id);
	}

	public void saveApplication(Application app) {
		repository.save(app);
	}

	public List<Application> getAllApplications() {
		return repository.findAll();
	}

}
