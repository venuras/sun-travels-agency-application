package com.sun_travels.web_backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration
@ActiveProfiles(value = "test")
class WebBackendApplicationTests {

	@Test
	void contextLoads() {
		//Test to execute application and check whether it runs with the current configuration and resources
	}

}
