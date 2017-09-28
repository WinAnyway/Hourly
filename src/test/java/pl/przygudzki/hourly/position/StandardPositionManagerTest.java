package pl.przygudzki.hourly.position;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.przygudzki.hourly.position.dto.AddPositionCommand;
import pl.przygudzki.hourly.position.dto.EditPositionCommand;
import pl.przygudzki.hourly.position.dto.PositionNotFoundException;
import pl.przygudzki.hourly.position.*;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StandardPositionManagerTest {

	@Mock
	private PositionRepository positionRepository;

	private PositionManager positionManager;

	@Mock
	private AddPositionCommand addCommand;

	@Mock
	private EditPositionCommand editCommand;

	@Mock
	private Position position;

	private PositionId positionId = PositionId.generate();
	private PositionId nonexistentPositionId = PositionId.generate();

	@Before
	public void setUp() throws Exception {
		positionManager = new StandardPositionManager(positionRepository);

		when(positionRepository.get(positionId)).thenReturn(Optional.of(position));
		when(positionRepository.get(nonexistentPositionId)).thenReturn(Optional.empty());
		when(positionRepository.getAll()).thenReturn(Collections.singletonList(position));
	}

	@Test
	public void shouldValidateCommandOnAdd() {
		positionManager.addPosition(addCommand);

		verify(addCommand).validate(any());
	}

	@Test
	public void shouldCreateAndPersistPositionOnAdd() {
		positionManager.addPosition(addCommand);

		verify(positionRepository).put(any(Position.class));
	}

	@Test
	public void shouldQueryPositionOnGet() {
		positionManager.getPosition(positionId);

		verify(positionRepository).get(positionId);
	}

	@Test
	public void shouldThrownIfPositionNotFound() {
		Throwable thrown = catchThrowable(() -> positionManager.getPosition(nonexistentPositionId));

		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldQueryPositionsOnList() {
		positionManager.listPositions();

		verify(positionRepository).getAll();
	}

	@Test
	public void shouldFilterOutUnavailablePositionsOnList() {
		positionManager.listPositions();

		verify(position).isAvailable();
	}

	@Test
	public void shouldValidateCommandOnEdit() {
		positionManager.editPosition(positionId, editCommand);

		verify(editCommand).validate(any());
	}

	@Test
	public void shouldFetchPositionOnEdit() {
		positionManager.editPosition(positionId, editCommand);

		verify(positionRepository).get(positionId);
	}

	@Test
	public void shouldThrowWhenEditingNonexistentPosition() {
		Throwable thrown = catchThrowable(() -> positionManager.editPosition(nonexistentPositionId, editCommand));

		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldEditPositionOnEdit() {
		positionManager.editPosition(positionId, editCommand);

		verify(position).edit(editCommand);
	}

	@Test
	public void shouldFetchPositionOnRemove() {
		positionManager.removePosition(positionId);

		verify(positionRepository).get(positionId);
	}

	@Test
	public void shouldThrowWhenRemovingNonexistentPosition() {
		Throwable thrown = catchThrowable(() -> positionManager.removePosition(nonexistentPositionId));

		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldRemovePositionOnRemove() {
		positionManager.removePosition(positionId);

		verify(position).remove();
	}

	@Test
	public void shouldFetchPositionOnIsAvailable() {
		positionManager.isAvailable(positionId);

		verify(positionRepository).get(positionId);
	}

	@Test
	public void shouldThrowWhenCheckingIfIsAvailableOnNonexistentPosition() {
		Throwable thrown = catchThrowable(() -> positionManager.isAvailable(nonexistentPositionId));

		assertThat(thrown).isInstanceOf(PositionNotFoundException.class);
	}

	@Test
	public void shouldCheckIfPositionIsAvailableOnIsAvailable() {
		positionManager.isAvailable(positionId);

		verify(position).isAvailable();
	}

}
