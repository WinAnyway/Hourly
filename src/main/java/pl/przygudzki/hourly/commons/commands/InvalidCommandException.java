package pl.przygudzki.hourly.commons.commands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InvalidCommandException extends RuntimeException {

	private final Validatable.ValidationErrors errors;

}
