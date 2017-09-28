package pl.przygudzki.hourly.shift;

import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.shift.dto.*;

import java.util.Collection;
import java.util.stream.Collectors;

class StandardShiftManager implements ShiftManager {

	private ShiftRepository shiftRepository;
	private ShiftDtoBuilder dtoBuilder = new ShiftDtoBuilder();

	StandardShiftManager(ShiftRepository shiftRepository) {
		this.shiftRepository = shiftRepository;
	}

	@Override
	public void addShift(AddShiftCommand command) {
		validateCommand(command);
		Shift shift = Shift.create(command);
		shiftRepository.put(shift);
	}

	@Override
	public Collection<ShiftDto> listShifts() {
		return shiftRepository.getAll().stream()
				.filter(Shift::isAvailable)
				.map(this::buildShiftDto)
				.collect(Collectors.toList());
	}

	@Override
	public ShiftDto getShift(ShiftId shiftId) {
		Shift shift = getOrThrow(shiftId);
		return buildShiftDto(shift);
	}

	@Override
	public void editShift(ShiftId shiftId, EditShiftCommand command) {
		validateCommand(command);
		Shift shift = getOrThrow(shiftId);
		shift.edit(command);
	}

	@Override
	public void remove(ShiftId shiftId) {
		Shift shift = getOrThrow(shiftId);
		shift.remove();
	}

	private Shift getOrThrow(ShiftId shiftId) {
		return shiftRepository.get(shiftId)
				.orElseThrow(() -> new ShiftNotFoundException(shiftId));
	}

	private ShiftDto buildShiftDto(Shift shift) {
		shift.export(dtoBuilder);
		return dtoBuilder.build();
	}

	private void validateCommand(Validatable command) {
		Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
		command.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);
	}

}
