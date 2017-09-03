package pl.przygudzki.hourly.employee;

import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.employee.dto.AddPositionCommand;
import pl.przygudzki.hourly.employee.dto.EditPositionCommand;
import pl.przygudzki.hourly.employee.dto.PositionDto;
import pl.przygudzki.hourly.employee.dto.PositionNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

class StandardPositionManager implements PositionManager {

	private PositionRepository positionRepository;

	StandardPositionManager(PositionRepository positionRepository) {
		this.positionRepository = positionRepository;
	}

	@Override
	public void addPosition(AddPositionCommand command) {
		validateCommand(command);
		Position position = Position.create(command);
		positionRepository.put(position);
	}

	@Override
	public Position getPositionOrThrow(Long positionId) {
		return positionRepository.get(PositionId.of(positionId))
				.filter(Position::isAvailable)
				.orElseThrow(() -> new PositionNotFoundException(positionId));
	}

	@Override
	public void editPosition(EditPositionCommand command, Long id) {
		Position position = getPositionOrThrow(id);
		position.edit(command);
	}

	@Override
	public void removePosition(Long id) {
		Position position = getPositionOrThrow(id);
		position.remove();
	}

	@Override
	public Collection<PositionDto> listPositions() {
		final PositionDtoBuilder dtoBuilder = new PositionDtoBuilder();
		return positionRepository.getAll().stream()
				.filter(Position::isAvailable)
				.map(position -> {
					position.export(dtoBuilder);
					return dtoBuilder.build();
				})
				.collect(Collectors.toList());
	}

	private void validateCommand(Validatable command) {
		Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
		command.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);
	}

}
