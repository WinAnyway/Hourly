package pl.przygudzki.hourly.position;

import pl.przygudzki.hourly.commons.commands.InvalidCommandException;
import pl.przygudzki.hourly.commons.commands.Validatable;
import pl.przygudzki.hourly.position.dto.AddPositionCommand;
import pl.przygudzki.hourly.position.dto.EditPositionCommand;
import pl.przygudzki.hourly.position.dto.PositionDto;
import pl.przygudzki.hourly.position.dto.PositionNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

class StandardPositionManager implements PositionManager {

	private PositionRepository positionRepository;
	private PositionDtoBuilder dtoBuilder = new PositionDtoBuilder();

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
	public PositionDto getPosition(PositionId positionId) {
		Position position = getPositionOrThrow(positionId);
		return buildPositionDto(position);
	}

	@Override
	public Collection<PositionDto> listPositions() {
		return positionRepository.getAll().stream()
				.filter(Position::isAvailable)
				.map(this::buildPositionDto)
				.collect(Collectors.toList());
	}

	@Override
	public void editPosition(PositionId id, EditPositionCommand command) {
		validateCommand(command);
		Position position = getPositionOrThrow(id);
		position.edit(command);
	}

	@Override
	public void removePosition(PositionId id) {
		Position position = getPositionOrThrow(id);
		position.remove();
	}

	@Override
	public boolean isAvailable(PositionId positionId) {
		Position position = getPositionOrThrow(positionId);
		return position.isAvailable();
	}

	private Position getPositionOrThrow(PositionId id) {
		return positionRepository.get(id)
				.orElseThrow(() -> new PositionNotFoundException(id));
	}

	private PositionDto buildPositionDto(Position position) {
		position.export(dtoBuilder);
		return dtoBuilder.build();
	}

	private void validateCommand(Validatable command) {
		Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
		command.validate(errors);
		if (!errors.isValid())
			throw new InvalidCommandException(errors);
	}

}
