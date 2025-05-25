package com.example.aps.service;

import com.example.aps.dto.ApplicationRequest;
import com.example.aps.dto.ApplicationResponse;
import com.example.aps.entity.Application;
import com.example.aps.repository.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {

	private ApplicationRepository repository;
	private ApplicationService service;

	@BeforeEach
	void setUp() {
		repository = mock(ApplicationRepository.class);
		service = new ApplicationService(repository);
	}

	@Test
	void testSubmitApplication() {
		ApplicationRequest request = new ApplicationRequest();
		request.setApplicantName("John Doe");
		request.setMonthlyIncome(50000.0);
		request.setMonthlyExpense(20000.0);

		Application savedApp = new Application();
		savedApp.setApplicantName("John Doe");
		savedApp.setMonthlyIncome(50000.0);
		savedApp.setMonthlyExpense(20000.0);
		savedApp.setCreditCheckStatus("VERIFIED");
		savedApp.setRepaymentCapacity(30000.0);
		savedApp.setFinancialStatus("SUFFICIENT");

		when(repository.save(any(Application.class))).thenReturn(savedApp);

		ApplicationResponse response = service.submitApplication(request);

		assertNotNull(response);
		assertEquals("VERIFIED", response.getCreditCheckStatus());
		assertEquals(30000.0, response.getRepaymentCapacity());
		assertEquals("SUFFICIENT", response.getRepaymentCapacityStatus());
	}

	@Test
	void testGetApplication() {
		Application app = new Application();
		app.setId(1L);
		app.setApplicantName("Jane");

		when(repository.findById(1L)).thenReturn(Optional.of(app));

		Optional<Application> result = service.getApplication(1L);

		assertTrue(result.isPresent());
		assertEquals("Jane", result.get().getApplicantName());
	}

	@Test
	void testGetAllApplications() {
		when(repository.findAll()).thenReturn(List.of(new Application(), new Application()));

		List<Application> apps = service.getAllApplications();

		assertEquals(2, apps.size());
	}

	@Test
	void testSaveApplication() {
		Application app = new Application();
		app.setId(1L);
		app.setApplicantName("Updated Name");

		when(repository.save(app)).thenReturn(app);

		service.saveApplication(app);

		verify(repository, times(1)).save(app);
	}

	@Test
	void testDeleteApplicationSuccess() {
	    Long id = 1L;
	    when(repository.existsById(id)).thenReturn(true);

	    boolean result = service.deleteApplication(id);

	    assertTrue(result);
	    verify(repository, times(1)).deleteById(id);
	}

	@Test
	void testDeleteApplicationNotFound() {
	    Long id = 999L;
	    when(repository.existsById(id)).thenReturn(false);

	    boolean result = service.deleteApplication(id);

	    assertFalse(result);
	    verify(repository, never()).deleteById(anyLong());
	}
}
