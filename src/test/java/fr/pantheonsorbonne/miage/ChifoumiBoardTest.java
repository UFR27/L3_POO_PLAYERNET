package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.ChifoumiBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChifoumiBoardTest {

    @Test
    public void testWinner1() {
        ChifoumiBoard board = new ChifoumiBoard("XXXOXOXXO");
        assertEquals("XXX\nOXO\nXXO", board.toString());
        assertEquals('X', board.getWinner());
    }

    @Test
    public void testWinner2() {
        ChifoumiBoard board = new ChifoumiBoard("XXOOOOXOX");
        assertEquals("XXO\nOOO\nXOX", board.toString());
        assertEquals('O', board.getWinner());
    }

    @Test
    public void testWinner3() {
        ChifoumiBoard board = new ChifoumiBoard("XOOOXXOXX");
        assertEquals("XOO\nOXX\nOXX", board.toString());
        assertEquals('X', board.getWinner());
    }

    @Test
    public void testWinner4() {
        ChifoumiBoard board = new ChifoumiBoard("XOOXOXOXX");

        assertEquals('O', board.getWinner());
    }

    @Test
    public void testNextPlayer() {
        ChifoumiBoard board = new ChifoumiBoard("X...");

        assertEquals('O', board.nextPlayer());
    }

    @Test
    public void testNextPlayer2() {
        ChifoumiBoard board = new ChifoumiBoard("XXO.");

        assertEquals('O', board.nextPlayer());
    }


}