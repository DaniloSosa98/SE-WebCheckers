package com.webcheckers.models;

import com.webcheckers.global.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Space model.
 * @author Danilo Sosa
 */

@Tag("Model-tier")
public class SpaceTest {

	// Helper field
	private int index;
	//Component under test
	private Space space, space2;

	@BeforeEach
	public void setup() {
		index = 0;
		space = new Space(true, index);
	}

	@Test
	public void spaceConstructorAndDuplicateGenerateNotNullSpace() {
		assertNotNull(space, "Space is null");
		new Space(space);
	}

	@Test
	public void testIfToStringReturnsTheExactStringFormat() {
		space.putRedPiece();
		assertEquals("R", space.toString());
	}

	@Test
	public void putRedPieceInSpaceAndCheckPieceColorAndIndex() {
		space.putRedPiece();
		assertEquals(Constants.Color.RED, space.getPiece().getColor());
		assertEquals(index, space.getPiece().getIndex());
	}

	@Test
	public void putWhitePieceInSpaceAndCheckPieceColorAndIndex() {
		space.putWhitePiece();
		assertEquals(Constants.Color.WHITE, space.getPiece().getColor());
		assertEquals(index, space.getPiece().getIndex());
	}

	@Test
	public void testIndexGetter() {
		assertEquals(index, space.getCellIdx());
	}

	@Test
	public void testIfSpaceIsWhiteAfterWhiteColorSetter() {
		space.makeSpaceWhite();
		assertEquals(Space.SpaceColor.WHITE, space.getColor());
	}

	@Test
	public void testColorGetter() {
		assertEquals(Space.SpaceColor.BLACK, space.getColor());
	}

	@Test
	public void testPutPiece() {
		Piece piece = new Piece(Constants.Color.RED, index);
		space.putPiece(piece);
		assertEquals(space.getPiece(), piece, "Expected true");
	}

	@Test
	public void testIfSpaceValidHasNoPiece() {
		//Is valid and has no piece
		assertTrue(space.isValid(), "Expected true");
	}

	@Test
	public void testIfSpaceNotValidHasNoPiece() {
		//Is not valid and has no piece
		space = new Space(false, index);
		assertFalse(space.isValid(), "Expected false");
	}

	@Test
	public void testIfSpaceValidHasWhitePiece() {
		//Is valid and has white piece
		space = new Space(true, index);
		space.putWhitePiece();
		assertFalse(space.isValid(), "Expected false");
	}

	@Test
	public void testIfSpaceValidHasRedPiece() {
		//Is valid and has red piece
		space = new Space(true, index);
		space.putRedPiece();
		assertFalse(space.isValid(), "Expected false");
	}

	@Test
	public void testIfSpaceNotValidHasWhitePiece() {
		//Is not valid and has white piece
		space = new Space(false, index);
		space.putWhitePiece();
		assertFalse(space.isValid(), "Expected false");
	}

	@Test
	public void testIfSpaceNotValidHasRedPiece() {
		//Is not valid and has red piece
		space = new Space(false, index);
		space.putRedPiece();
		assertFalse(space.isValid(), "Expected false");
	}

	@Test
	public void testSpace2EqualsSpace() {
		//Space2 == Space
		space2 = new Space(true, index);
		assertEquals(space, space2, "Expected true");
	}

	@Test
	public void testSpace2DiffIndexThanSpace() {
		//Space2 has a different index than Space
		space2 = new Space(true, index + 1);
		assertNotEquals(space, space2, "Expected false");
	}

	@Test
	public void testSpaceNotEqualPiece() {
		//Space != Piece
		Piece p = new Piece(Constants.Color.RED, index);
		assertNotEquals(space, p, "Expected false");
	}

	@Test
	public void testSpace2HasWhiteSpaceNot() {
		//Space2 has white piece and Space does not
		space2 = new Space(true, index);
		space2.putWhitePiece();
		assertNotEquals(space, space2, "Expected false");
	}

	@Test
	public void testSpace2HasRedSpaceNot() {
		//Space2 has a red piece and Space does not
		space2 = new Space(true, index);
		space2.putRedPiece();
		assertNotEquals(space, space2, "Expected false");
	}

	@Test
	public void testSpace2AndSpaceSamePiece() {
		//Space2 and Space have the same piece
		space2 = new Space(true, index);
		space.putPiece(space2.getPiece());
		assertEquals(space, space2, "Expected true");
	}

	@Test
	public void testSpace2WhiteSpaceBlack() {
		//Space2 is white and Space is black
		space = new Space(true, index);
		space2 = new Space(true, index);
		space2.makeSpaceWhite();
		assertNotEquals(space, space2, "Expected false");
	}

	@Test
	public void testSpace2InvalidSpaceValid() {
		//Space2 is not valid and Space is valid
		space = new Space(true, index);
		space2 = new Space(false, index);
		assertNotEquals(space, space2, "Expected false");
	}
}
