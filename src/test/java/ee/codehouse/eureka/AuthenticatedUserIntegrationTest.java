package ee.codehouse.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class AuthenticatedUserIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldSeeLastNRegistrations() throws Exception {
		mockMvc.perform(get("/lastn"))
				.andExpect(status().isOk());
	}
}
