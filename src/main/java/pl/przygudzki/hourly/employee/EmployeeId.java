package pl.przygudzki.hourly.employee;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeId {

	private static long idSequenceCounter = 0;

	private Long id;

	private EmployeeId(Long id) {
		this.id = id;
	}

	static EmployeeId generate() {
		return new EmployeeId(++idSequenceCounter);
	}

	Long getId() {
		return id;
	}

}
