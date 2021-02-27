package com.webcheckers.models;

import com.webcheckers.global.Constants;
import com.webcheckers.models.Piece.Type;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  The unit test for the {@link Piece} component
 *  Basic test to ensure the constructor functions properly
 *  and all elements of the Piece class are accessible.
 *
 * @author Donald Craig
 * @author Elizabeth Sherrock
 */

@Tag("Model-tier")
class PieceTest {
	private static final Constants.Color redColor = Constants.Color.RED;
	private static final Constants.Color whiteColor = Constants.Color.WHITE;
	private static final Type type = Type.SINGLE;
	private static final int index = 0;

	/**
	 * Testing constructor for a single red piece
	 */
	@Test
	void testRedPiece() {
		final Piece CuT = new Piece(redColor, index);
		assertEquals(Constants.Color.RED, CuT.getColor());
		assertEquals(index, CuT.getIndex());
		assertEquals(type, CuT.getType());
	}

	/**
	 * Testing constructor for a single white piece
	 */
	@Test
	void testWhitePiece() {
		final Piece CuT = new Piece(whiteColor, index);
		assertEquals(Constants.Color.WHITE, CuT.getColor());
		assertEquals(index, CuT.getIndex());
		assertEquals(type, CuT.getType());
	}

	/**
	 * Testing the .equals method of Piece to check it outputs True
	 * when two Piece objects are in fact the same
	 */
	@Test
	void testEqualsTrue() {
		final Piece CuT = new Piece(redColor, index);
		final Piece CuT2 = new Piece(redColor, index);
		assertEquals(CuT, CuT2);
	}

	/**
	 * Testing the .equals method of Piece to check it outputs False
	 * when two Piece objects are different
	 */
	@Test
	void testEqualsFalse() {
		final Piece CuT = new Piece(redColor, index);
		final Piece CuT2 = new Piece(whiteColor, 1);
		assertNotEquals(CuT, CuT2);
		assertNotNull(CuT);
	}

	/**
	 * Tests the toString method of Piece for a red piece
	 */
	@Test
	void testRedToString() {
		Piece redPiece = new Piece(redColor, index);
		assertEquals("R", redPiece.toString());
	}

	/**
	 * Tests the toString method of Piece for a white piece
	 */
	@Test
	void testWhiteToString() {
		Piece whitePiece = new Piece(whiteColor, index);
		assertEquals("W", whitePiece.toString());
	}
}
