package com.jacekg.teamfinder.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFindByUsername() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByUserId() {
		fail("Not yet implemented");
	}

}
