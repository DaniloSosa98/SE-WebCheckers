package com.webcheckers.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the Position component.
 *
 * @author Elizabeth Sherrock
 */
@Tag("Model-tier")
public class PositionTest {
	private int rowInRange, cellInRange, rowOutOfRange, cellOutOfRange;
	//component under test
	private Position position;

	/**
	 * Assign values to the test row and cell variables.
	 */
	@BeforeEach
	public void setup() {
		rowInRange = 4;
		cellInRange = 4;
		rowOutOfRange = 10;
		cellOutOfRange = 10;
	}

	/**
	 * Tests that the component will be initialized correctly.
	 */
	@Test
	public void testPositionConstructor() {
		position = new Position(rowInRange, cellInRange);
		// I know tests need to be independent but not sure how else to do this
		assertEquals(rowInRange, position.getRow());
		assertEquals(cellInRange, position.getCell());
	}
}
