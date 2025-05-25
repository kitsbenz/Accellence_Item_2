package com.example.aps.controller;

import com.example.aps.dto.ApplicationRequest;
import com.example.aps.dto.ApplicationResponse;
import com.example.aps.entity.Application;
import com.example.aps.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationControllerTest {

	private ApplicationService service;
	private ApplicationController controller;

	@BeforeEach
	void setUp() {
		service = mock(ApplicationService.class);
		controller = new ApplicationController(service);
	}

	@Test
	void testSubmitApplication() {
		ApplicationRequest request = new ApplicationRequest();
		request.setApplicantName("John");
		request.setMonthlyIncome(60000.0);
		request.setMonthlyExpense(20000.0);

		ApplicationResponse response = new ApplicationResponse();
		response.setCreditCheckStatus("VERIFIED");
		response.setRepaymentCapacity(40000.0);
		response.setRepaymentCapacityStatus("SUFFICIENT");

		when(service.submitApplication(request)).thenReturn(response);

		ResponseEntity<ApplicationResponse> result = controller.submitApplication(request);

		assertEquals(200, result.getStatusCodeValue());
		assertEquals("VERIFIED", result.getBody().getCreditCheckStatus());
		assertEquals("SUFFICIENT", result.getBody().getRepaymentCapacityStatus());
	}

	@Test
	void testGetApplicationFound() {
		Application app = new Application();
		app.setId(1L);
		app.setApplicantName("Jane");

		when(service.getApplication(1L)).thenReturn(Optional.of(app));

		ResponseEntity<Application> result = controller.getApplication(1L);

		assertEquals(200, result.getStatusCodeValue());
		assertEquals("Jane", result.getBody().getApplicantName());
	}

	@Test
	void testGetApplicationNotFound() {
		when(service.getApplication(99L)).thenReturn(Optional.empty());

		ResponseEntity<Application> result = controller.getApplication(99L);

		assertEquals(404, result.getStatusCodeValue());
	}

	@Test
	void testGetAllApplications() {
		when(service.getAllApplications()).thenReturn(List.of(new Application(), new Application()));

		ResponseEntity<List<Application>> result = controller.getAllApplications();

		assertEquals(200, result.getStatusCodeValue());
		assertEquals(2, result.getBody().size());
	}

	@Test
	void testUpdateCreditCheckStatusFound() {
		Application app = new Application();
		app.setId(1L);
		app.setCreditCheckStatus("VERIFIED");

		when(service.getApplication(1L)).thenReturn(Optional.of(app));
		when(service.saveApplication(any(Application.class))).thenReturn(app);

		ResponseEntity<String> result = controller.updateCreditCheckStatus(1L, "rejected");

		assertEquals(200, result.getStatusCodeValue());
		assertEquals("Credit check status updated to rejected", result.getBody());

		verify(service, times(1)).saveApplication(any(Application.class));
	}

	@Test
	void testUpdateCreditCheckStatusNotFound() {
		when(service.getApplication(999L)).thenReturn(Optional.empty());

		ResponseEntity<String> result = controller.updateCreditCheckStatus(999L, "approved");

		assertEquals(404, result.getStatusCodeValue());
	}

	@Test
	void testDeleteApplicationFound() {
	    when(service.deleteApplication(1L)).thenReturn(true);

	    ResponseEntity<Void> result = controller.deleteApplication(1L);

	    assertEquals(204, result.getStatusCodeValue());
	    verify(service, times(1)).deleteApplication(1L);
	}

	@Test
	void testDeleteApplicationNotFound() {
	    when(service.deleteApplication(999L)).thenReturn(false);

	    ResponseEntity<Void> result = controller.deleteApplication(999L);

	    assertEquals(404, result.getStatusCodeValue());
	    verify(service, times(1)).deleteApplication(999L);
	}
}
