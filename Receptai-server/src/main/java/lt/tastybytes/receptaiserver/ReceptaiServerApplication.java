package lt.tastybytes.receptaiserver;

import lt.tastybytes.receptaiserver.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import static lt.tastybytes.receptaiserver.model.RolesKt.ROLE_ADMIN;

@SpringBootApplication
public class ReceptaiServerApplication {

	final UserService userService;

	public ReceptaiServerApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ReceptaiServerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void setupAdminAccountOnStart() {
		var user = userService.findUserById(1);
		if (user.isEmpty()) {
			var newUser = userService.createUser("admin", "admin@localhost", "admin");
			userService.addRoleToUser(newUser, ROLE_ADMIN);
		} else {
			for (var role: user.get().getRoles()) {
				if (role.getName().equals(ROLE_ADMIN)) {
					return;
				}
			}
			userService.addRoleToUser(user.get(), ROLE_ADMIN);
			System.out.println("Added ROLE_ADMIN for user of ID 1");
		}
	}
}
