package pl.przygudzki.hourly.employee;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EmployeeId {

	private static long idSequenceCounter = 0;

	private Long id;

	static EmployeeId generate() {
		return new EmployeeId(++idSequenceCounter);
	}

	static EmployeeId of(Long id) {
		return new EmployeeId(id);
	}

	Long getId() {
		return id;
	}

}
