package lt.tastybytes.receptaiserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestDatabaseConfig.class)
class ReceptaiServerApplicationTests {

	@Test
	void contextLoads() {
	}
}
