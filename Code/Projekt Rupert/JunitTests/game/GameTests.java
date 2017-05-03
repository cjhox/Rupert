package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import shared.Color;
import shared.GameCoordinate;
import shared.Player;
import shared.PlayerPosition;
import shared.Unittype;
import shared.Action.FightAction;
import shared.Action.MoveAction;
import shared.Action.PlaceAction;
import shared.game.Unit;
import shared.gameround.FightDraw;
import shared.gameround.FightPhase;
import shared.gameround.GameRound;
import shared.gameround.MoveDraw;
import shared.gameround.MovePhase;
import shared.gameround.PlaceDraw;
import shared.gameround.PlacePhase;

public class GameTests {

	Player			player1;
	Player			player2;
	FightAction		attckAction;
	MoveAction		moveAction			= MoveAction.MOVE;
	PlaceAction		placeAction;
	GameCoordinate	sourceCordinateTest	= new GameCoordinate(1, 1);
	GameCoordinate	targetCordianteTest	= new GameCoordinate(2, 2);
	MovePhase		movePhasePlayer1	= new MovePhase(player1);
	MovePhase		movePhasePlayer2	= new MovePhase(player2);
	Unit			testArcher			= new Unit(1, Unittype.ARCHER, player1);
	PlacePhase		placePhasePlayer1	= new PlacePhase(player1);
	PlacePhase		placePhasePlayer2	= new PlacePhase(player2);
	MoveDraw		testDrawForPlayer1	= new MoveDraw(sourceCordinateTest, targetCordianteTest, moveAction);

	@Before
	public void setUp() {
		player1 = new Player("one", Color.RED, PlayerPosition.LEFT);
		player2 = new Player("two", Color.BLUE, PlayerPosition.RIGHT);
		attckAction = FightAction.ATTACK;
		placeAction = PlaceAction.PLACE;
	}

	// ---------------------------MovePhase------------------------------------

	@Test
	public void testOfMovePhaseAllowedAction() {
		assertTrue(movePhasePlayer1.addDraw(testDrawForPlayer1));
	}

	@Test
	public void testOfMovePhaseremoveAction() {
		MoveDraw testDrawForPlayer1 = new MoveDraw(sourceCordinateTest, targetCordianteTest, moveAction);
		movePhasePlayer1.addDraw(testDrawForPlayer1);
		assertFalse(movePhasePlayer1.getDraws().isEmpty());
		movePhasePlayer1.removeDraw(testDrawForPlayer1);
		assertTrue(movePhasePlayer1.getDraws().isEmpty());
	}

	// ----------------------------PlacePhase-----------------------------------------

	@Test
	public void testofPlacePhase() {
		PlaceDraw testPlayer1PlaceDraw = new PlaceDraw(sourceCordinateTest, placeAction, Unittype.ARCHER, player1);
		assertTrue(placePhasePlayer1.addDraw(testPlayer1PlaceDraw));
	}

	@Test
	public void testofPlacePhaseRemoveDraw() {
		PlaceDraw testPlayer1PlaceDraw = new PlaceDraw(sourceCordinateTest, placeAction, Unittype.ARCHER, player1);
		placePhasePlayer1.addDraw(testPlayer1PlaceDraw);
		assertFalse(placePhasePlayer1.getDraws().isEmpty());
		placePhasePlayer1.removeDraw(testPlayer1PlaceDraw);
		assertTrue(placePhasePlayer1.getDraws().isEmpty());
	}

	// ----------------------------FightPhase-----------------------------------------

	@Test
	public void testOfFightPhaseAllowedAction() {
		FightPhase attackPhasePlayer1 = new FightPhase(player1);
		FightDraw testDrawForPlayer1 = new FightDraw(sourceCordinateTest, targetCordianteTest, attckAction);
		assertTrue(attackPhasePlayer1.addDraw(testDrawForPlayer1));
	}

	@Test
	public void testOfFightPhaseremoveAction() {
		FightPhase attackPhasePlayer1 = new FightPhase(player1);
		FightDraw testDrawForPlayer1 = new FightDraw(sourceCordinateTest, targetCordianteTest, attckAction);
		attackPhasePlayer1.addDraw(testDrawForPlayer1);
		assertFalse(attackPhasePlayer1.getDraws().isEmpty());
		attackPhasePlayer1.removeDraw(testDrawForPlayer1);
		assertTrue(attackPhasePlayer1.getDraws().isEmpty());
	}

	// ------------------------------------GameRound---------------------------

	@Test
	public void testOfGameRound() {
		FightPhase attackPhasePlayer1 = new FightPhase(player1);
		FightPhase attackPhasePlayer2 = new FightPhase(player2);
		GameRound<MovePhase> testRound1 = new GameRound<MovePhase>();
		testRound1.setPhaseFirstPlayer(movePhasePlayer1);
		testRound1.setPhaseSecoundPlayer(movePhasePlayer2);
		GameRound<FightPhase> testRound2 = new GameRound<FightPhase>();
		testRound2.setPhaseFirstPlayer(attackPhasePlayer1);
		testRound2.setPhaseSecoundPlayer(attackPhasePlayer2);
		assertEquals(testRound1.getPhaseFirstPlayer(), movePhasePlayer1);
		assertEquals(testRound1.getPhaseSecoundPlayer(), movePhasePlayer2);
		assertEquals(testRound2.getPhaseFirstPlayer(), attackPhasePlayer1);
		assertEquals(testRound2.getPhaseSecoundPlayer(), attackPhasePlayer2);
	}

	// ---------------------------Startround---------------------------------------------

	// @Test
	// public void testOfStartRound() {
	// StartRound testStartRound = new StartRound(1);
	// testStartRound.setFirst(placePhasePlayer1);
	// testStartRound.setSecound(placePhasePlayer2);
	// assertEquals((testStartRound.getPhases())[0], placePhasePlayer1);
	// assertEquals((testStartRound.getPhases())[1], placePhasePlayer2);
	// }

	// -----------------------------------Draw----------------------------------------------

	@Test
	public void testOfDrawGetSource() {
		assertEquals(testDrawForPlayer1.getSource(), sourceCordinateTest);
	}

	@Test
	public void testOfDrawGetTarget() {
		assertEquals(testDrawForPlayer1.getTarget(), targetCordianteTest);

	}

	@Test
	public void testOfDrawGetAction() {
		assertEquals(testDrawForPlayer1.getAction(), moveAction);

	}
}
