package com.example.springbootlab1;

import com.example.springbootlab1.service.CoordinatesRepositoryImplementor;
import com.example.springbootlab1.service.CoordinatesRepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringBootTutorialApplicationTests {



	@Test
	void contextLoads() {
		assertTrue(true, "This will always pass");
	}

}
