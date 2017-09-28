package pl.przygudzki.hourly.position;

import pl.przygudzki.hourly.position.dto.AddPositionCommand;
import pl.przygudzki.hourly.position.dto.EditPositionCommand;
import pl.przygudzki.hourly.position.dto.PositionDto;

public class PositionCommandPreparer {

	private static final PositionId VALID_POSITION_ID = PositionId.of(123L);
	private static final PositionId NON_EXISTENT_POSITION_ID = PositionId.of(-123L);

	public static PositionId validPositionId() {
		return VALID_POSITION_ID;
	}

	public static PositionId nonExistentPositionId() {
		return NON_EXISTENT_POSITION_ID;
	}

	AddPositionCommand validAddPositionCommand() {
		return createAddPositionCommand("Manager");
	}

	AddPositionCommand anotherValidAddPositionCommand() {
		return createAddPositionCommand("Copywriter");
	}

	EditPositionCommand validEditPositionCommand() {
		return createEditPositionCommand("Barista");
	}

	EditPositionCommand invalidEditPositionCommand() {
		return createEditPositionCommand("");
	}

	EditPositionCommand editPositionCommandFrom(PositionDto dto) {
		return createEditPositionCommand(dto.getTitle());
	}

	private AddPositionCommand createAddPositionCommand(String title) {
		AddPositionCommand command = new AddPositionCommand();
		command.setTitle(title);
		return command;
	}

	private EditPositionCommand createEditPositionCommand(String title) {
		EditPositionCommand command = new EditPositionCommand();
		command.setTitle(title);
		return command;
	}

}
