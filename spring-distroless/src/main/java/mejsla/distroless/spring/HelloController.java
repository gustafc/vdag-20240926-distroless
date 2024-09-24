package mejsla.distroless.spring;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	record HelloData(Instant time, String message, boolean distroless, long maxMemoryInMB) {
	}

	@GetMapping(path = "/", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public HelloData hello() {
		// http localhost:8080
		return new HelloData(Instant.now(), "Hello from Spring controller!", true, Runtime.getRuntime().maxMemory() / (1024 * 1024));
	}

	record ShellInjectionForm(@NonNull String injection) {
	}

	@PostMapping(path = "/shell", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public List<String> shellInjection(@RequestBody ShellInjectionForm shell) throws IOException {
		// http -v localhost:8080/shell injection='cat /etc/passwd'
		return Runtime.getRuntime()
				.exec(shell.injection())
				.inputReader()
				.lines()
				.collect(Collectors.toList());
	}

}
