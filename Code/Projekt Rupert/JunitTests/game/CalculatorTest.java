package game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import server.domain.Calculator;
import shared.Color;
import shared.GameCoordinate;
import shared.Player;
import shared.PlayerPosition;
import shared.Unittype;
import shared.Action.FightAction;
import shared.Action.MoveAction;
import shared.Action.PlaceAction;
import shared.com.UpdateHealth;
import shared.com.UpdateHealthData;
import shared.com.UpdatePosition;
import shared.game.Game;
import shared.game.Unit;
import shared.gameround.FightDraw;
import shared.gameround.FightPhase;
import shared.gameround.GameRound;
import shared.gameround.MoveDraw;
import shared.gameround.MovePhase;
import shared.gameround.PlaceDraw;

public class CalculatorTest {

	Player			player1					= new Player("one", Color.RED, PlayerPosition.LEFT);
	Player			player2					= new Player("two", Color.BLUE, PlayerPosition.RIGHT);
	Game			testGame				= new Game(player1, player2);
	Calculator		calculatorTest			= new Calculator(testGame);
	GameCoordinate	cordinateArcher1Test	= new GameCoordinate(0, 5);
	GameCoordinate	testCordinateKnight1	= new GameCoordinate(0, 6);
	GameCoordinate	testCordinateSwordsman1	= new GameCoordinate(0, 7);
	GameCoordinate	testCordinateArcher2	= new GameCoordinate(19, 5);
	GameCoordinate	testCordinateSwordsman2	= new GameCoordinate(19, 7);
	MovePhase		movePhasePlayer1		= new MovePhase(player1);
	MovePhase		movePhasePlayer2		= new MovePhase(player2);
	MoveAction		moveAction				= MoveAction.MOVE;
	FightAction		attckAction;
	PlaceDraw		Archer1					= new PlaceDraw(cordinateArcher1Test, PlaceAction.PLACE, Unittype.ARCHER,
													player1);
	PlaceDraw		Knight1					= new PlaceDraw(testCordinateKnight1, PlaceAction.PLACE, Unittype.KNIGHT,
													player1);
	PlaceDraw		Swordsman1				= new PlaceDraw(testCordinateSwordsman1, PlaceAction.PLACE,
													Unittype.SWORDSMAN, player1);
	PlaceDraw		Archer2					= new PlaceDraw(testCordinateArcher2, PlaceAction.PLACE, Unittype.ARCHER,
													player2);
	PlaceDraw		Swordsman2				= new PlaceDraw(testCordinateSwordsman2, PlaceAction.PLACE,
													Unittype.SWORDSMAN, player2);
	MoveDraw		testDrawForPlayer1		= new MoveDraw(cordinateArcher1Test, testCordinateArcher2, moveAction);
	MoveDraw		testDrawForPlayer2		= new MoveDraw(testCordinateArcher2, cordinateArcher1Test, moveAction);
	FightDraw		testAttack1Draw1		= new FightDraw(cordinateArcher1Test, testCordinateArcher2,
													FightAction.ATTACK);
	FightDraw		testAttack1Draw2		= new FightDraw(testCordinateKnight1, testCordinateSwordsman2,
													FightAction.ATTACK);
	FightDraw		testAttack2Draw3		= new FightDraw(testCordinateArcher2, testCordinateKnight1,
													FightAction.ATTACK);
	FightDraw		testAttack2Draw4		= new FightDraw(testCordinateSwordsman2, testCordinateSwordsman2,
													FightAction.DEFENCE);
	FightDraw		testAttack1Draw5		= new FightDraw(testCordinateSwordsman1, testCordinateSwordsman1,
													FightAction.HEAL);

	@Before
	public void prepare() {
		testGame.initializeMap();
		Unittype.ARCHER.addAdventage(Unittype.KNIGHT, 40);
		Unittype.KNIGHT.addAdventage(Unittype.SWORDSMAN, 40);
		Unittype.SWORDSMAN.addAdventage(Unittype.ARCHER, 40);
	}

	@Test
	public void testIllegalPlaceing() {
		testGame.place(Archer1);
		assertFalse(testGame.place(new PlaceDraw(cordinateArcher1Test, PlaceAction.PLACE, Unittype.KNIGHT, player2)));
	}

	@Test
	public void testUpdatePosWithNoUnit() {
		movePhasePlayer1.addDraw(testDrawForPlayer1);
		movePhasePlayer2.addDraw(testDrawForPlayer2);
		GameRound<MovePhase> testarray = new GameRound<>();
		testarray.setPhaseFirstPlayer(movePhasePlayer1);
		testarray.setPhaseSecoundPlayer(movePhasePlayer2);
		List<MoveDraw> sortedMoveDraws = calculatorTest.sortMoveDraws(testarray);
		UpdatePosition y = new UpdatePosition();
		for (MoveDraw moveDraw : sortedMoveDraws) {
			MoveDraw move = calculatorTest.calcMoveDraw(moveDraw);
			y.add(move);
			assertFalse(testGame.move(move));
		}
	}

	@Test
	public void testCalcMove_UpdatePosition() {
		testGame.place(Archer1);
		testGame.place(Archer2);
		movePhasePlayer1.addDraw(testDrawForPlayer1);
		movePhasePlayer2.addDraw(testDrawForPlayer2);
		GameRound<MovePhase> testarray = new GameRound<>();
		testarray.setPhaseFirstPlayer(movePhasePlayer1);
		testarray.setPhaseSecoundPlayer(movePhasePlayer2);
		List<MoveDraw> sortedMoveDraws = calculatorTest.sortMoveDraws(testarray);
		UpdatePosition y = new UpdatePosition();
		for (MoveDraw moveDraw : sortedMoveDraws) {
			MoveDraw move = calculatorTest.calcMoveDraw(moveDraw);
			y.add(move);
			testGame.move(move);
		}

		assertFalse(y.getUpdate().isEmpty());
		UpdatePosition ref = new UpdatePosition();
		ref.add(new MoveDraw(testDrawForPlayer1, new GameCoordinate(18, 5)));
		ref.add(testDrawForPlayer2);
		assertEquals(ref.toString(), y.toString());
	}

	@Test
	public void testCalcFight() {
		testGame.place(Archer1);
		testGame.place(Knight1);
		testGame.place(Swordsman1);
		testGame.place(Archer2);
		testGame.place(Swordsman2);

		FightPhase attackPhasePlayer1 = new FightPhase(player1);
		FightPhase attackPhasePlayer2 = new FightPhase(player2);
		attackPhasePlayer1.addDraw(testAttack1Draw1);
		attackPhasePlayer1.addDraw(testAttack1Draw2);
		attackPhasePlayer2.addDraw(testAttack2Draw3);
		attackPhasePlayer2.addDraw(testAttack2Draw4);
		attackPhasePlayer1.addDraw(testAttack1Draw5);
		attackPhasePlayer1.addDraw(testAttack1Draw1);
		
		GameRound<FightPhase> fightPhases = new GameRound<>();
		fightPhases.setPhaseFirstPlayer(attackPhasePlayer1);
		fightPhases.setPhaseSecoundPlayer(attackPhasePlayer2);
		UpdateHealth calcFight = calculatorTest.calcFight(fightPhases);
		
		UpdateHealth ref = new UpdateHealth();
		ref.add(new UpdateHealthData(testCordinateSwordsman1, testCordinateSwordsman1, FightAction.HEAL,
				Unittype.SWORDSMAN.getHealing()));
		ref.add(new UpdateHealthData(testCordinateSwordsman2, testCordinateSwordsman2, FightAction.DEFENCE, 0));
		ref.add(new UpdateHealthData(testCordinateArcher2, cordinateArcher1Test, FightAction.ATTACK,
				(int) (Unittype.ARCHER.getAttackDamage() * Unittype.ARCHER.getAdventage(Unittype.ARCHER))));
		ref.add(new UpdateHealthData(testCordinateKnight1, testCordinateArcher2, FightAction.ATTACK, 56));
		ref.add(new UpdateHealthData(testCordinateSwordsman2, testCordinateKnight1, FightAction.ATTACK,
				(int) (Unittype.KNIGHT.getAttackDamage() * Unittype.KNIGHT.getAdventage(Unittype.SWORDSMAN))));
		List<UpdateHealthData> refDatas = ref.getUpdate();
		List<UpdateHealthData> updateHealthDatas = calcFight.getUpdate();
		for (int i = 0; i < refDatas.size() || i < updateHealthDatas.size(); i++) {
			assertEquals("TestFight" + (i + 1), refDatas.get(i).toString(), updateHealthDatas.get(i).toString());
		}
	}

	@Test
	public void testIsGameFinished() {
		testGame.place(Archer1);
		testGame.place(Archer2);
		Unit testUnit = testGame.getUnits().get(0);
		testUnit.hurt(999999);
		if (testUnit.getPlayer().equals(player1)) {
			assertEquals(calculatorTest.isGameFinished(), player2);
		} else {
			assertEquals(calculatorTest.isGameFinished(), player1);
		}
	}

	@Test
	public void testIsGamenotFinished() {
		testGame.place(Archer1);
		testGame.place(Archer2);
		assertTrue(calculatorTest.isGameFinished() == null);
	}

	@Test
	public void testgetFieldBefore() {
		GameCoordinate beforeCordianteTest = new GameCoordinate(18, 5);
		assertEquals(calculatorTest.getFieldBefore(testDrawForPlayer1, testCordinateArcher2), beforeCordianteTest);
	}
}