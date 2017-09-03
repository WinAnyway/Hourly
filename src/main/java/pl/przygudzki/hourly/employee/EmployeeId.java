package pl.przygudzki.hourly.employee;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeId {

	private static long idSequenceCounter = 0;

	private Long id;

	static EmployeeId generate() {
		return new EmployeeId(++idSequenceCounter);
	}

	Long getId() {
		return id;
	}

}
