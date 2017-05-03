package server.domain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import shared.Action.FightAction;
import shared.GameCoordinate;
import shared.Player;
import shared.Unittype;
import shared.com.UpdateHealth;
import shared.com.UpdateHealthData;
import shared.com.UpdatePlacement;
import shared.game.GameReadOnly;
import shared.game.Unit;
import shared.gameround.FightDraw;
import shared.gameround.FightPhase;
import shared.gameround.GameRound;
import shared.gameround.MoveDraw;
import shared.gameround.MovePhase;
import shared.gameround.PlaceDraw;
import shared.gameround.PlacePhase;

public class Calculator {

	private GameReadOnly	game;

	public Calculator(GameReadOnly game) {
		this.game = game;
	}

	public UpdatePlacement sortPlaceDraws(GameRound<PlacePhase> gameRound) {
		UpdatePlacement update = new UpdatePlacement();

		Iterator<PlaceDraw> iteratorP1 = gameRound.getPhaseFirstPlayer().getDraws().iterator();
		Iterator<PlaceDraw> iteratorP2 = gameRound.getPhaseSecoundPlayer().getDraws().iterator();

		while (iteratorP1.hasNext()) {
			update.add(iteratorP1.next());

			if (iteratorP2.hasNext()) {
				update.add(iteratorP2.next());
			}
		}
		while (iteratorP2.hasNext()) {
			update.add(iteratorP2.next());
		}

		return update;
	}

	/**
	 * calcMove führt die von beiden Spielern in der aktuellen MovePhase
	 * erfassten MoveDraws aus. Berechnet das Ergebniss und speichert dieses in
	 * einem UpdatePosition.
	 */

	public List<MoveDraw> sortMoveDraws(GameRound<MovePhase> gameRound) {
		List<MoveDraw> sortedList = new LinkedList<MoveDraw>();

		Iterator<MoveDraw> iteratorP1 = gameRound.getPhaseFirstPlayer().getDraws().iterator();
		Iterator<MoveDraw> iteratorP2 = gameRound.getPhaseSecoundPlayer().getDraws().iterator();

		while (iteratorP1.hasNext()) {
			sortedList.add(iteratorP1.next());

			if (iteratorP2.hasNext()) {
				sortedList.add(iteratorP2.next());
			}
		}
		while (iteratorP2.hasNext()) {
			sortedList.add(iteratorP2.next());
		}

		return sortedList;
	}

	/**
	 * Helferklasse von calcMove. Erstellt einen neuen Zug. Berechnet und
	 * updated die Position der Unit.
	 */

	public MoveDraw calcMoveDraw(MoveDraw move) {
		GameCoordinate target = move.getTarget();

		while (!(game.getUnittype(target) == Unittype.EMPTY)) {
			target = getFieldBefore(move, target);
			if (target.equals(move.getSource())) {
				break;
			}
		}

		return new MoveDraw(move, target);
	}

	/**
	 * calcFight berechnet den Ausgang der aktuellen FightPhase. Speichert das
	 * Ergebniss in einem UpdateHealth.
	 */
	public UpdateHealth calcFight(GameRound<FightPhase> gameRound) {
		UpdateHealth updates = new UpdateHealth();

		LinkedList<FightDraw> attP1 = new LinkedList<>();
		LinkedList<FightDraw> attP2 = new LinkedList<>();
		LinkedList<FightDraw> deffending = new LinkedList<>();
		LinkedList<FightDraw> healing = new LinkedList<>();

		for (FightDraw fight : gameRound.getPhaseFirstPlayer().getDraws()) {
			addDrawToArray(attP1, deffending, healing, fight);
		}
		for (FightDraw fight : gameRound.getPhaseSecoundPlayer().getDraws()) {
			addDrawToArray(attP2, deffending, healing, fight);
		}

		for (FightDraw fight : healing) {
			Unittype unittype = game.getUnittype(fight.getSource());
			updates.add(new UpdateHealthData(fight.getSource(), fight.getSource(), FightAction.HEAL,
					unittype.getHealing()));
		}

		for (FightDraw fight : deffending) {
			updates.add(new UpdateHealthData(fight.getSource(), fight.getSource(), FightAction.DEFENCE, 0));
		}

		for (FightDraw fight : sortAttackDraws(attP1, attP2)) {
			updates.add(new UpdateHealthData(fight.getTarget(), fight.getSource(), FightAction.ATTACK, calcDamage(
					fight, game.getUnittype(fight.getTarget()))));
		}

		return updates;
	}

	private List<FightDraw> sortAttackDraws(LinkedList<FightDraw> attP1, LinkedList<FightDraw> attP2) {
		LinkedList<FightDraw> sortedList = new LinkedList<>();

		Iterator<FightDraw> iteratorP1 = attP1.iterator();
		Iterator<FightDraw> iteratorP2 = attP2.iterator();

		while (iteratorP1.hasNext()) {
			sortedList.add(iteratorP1.next());

			if (iteratorP2.hasNext()) {
				sortedList.add(iteratorP2.next());
			}
		}
		while (iteratorP2.hasNext()) {
			sortedList.add(iteratorP2.next());
		}

		return sortedList;
	}

	private void addDrawToArray(LinkedList<FightDraw> att, LinkedList<FightDraw> deff, LinkedList<FightDraw> healing,
			FightDraw fightDraw) {
		switch (fightDraw.getAction()) {
		case DEFENCE:
			deff.add(fightDraw);
			break;
		case ATTACK:
			att.add(fightDraw);
			break;
		case HEAL:
			healing.add(fightDraw);
			break;
		default:
			// TODO Error --> finish
			System.err.println("undefinierte Action im Calculator - calcFight - addDrawToArray");
			break;
		}
	}

	private int calcDamage(FightDraw fight, Unittype targetUnittype) {
		Unittype unittype = game.getUnittype(fight.getSource());
		return (int) (unittype.getAttackDamage() * unittype.getAdventage(targetUnittype));
	}

	/**
	 * Prüft, ob das Spiel zu Ende ist. Dies ist der Fall, wenn nur noch ein
	 * Player lebende Units auf dem Spielfeld hat.
	 */
	public Player isGameFinished() {
		Player player = null;
		for (Unit unit : game.getUnits()) {
			if (!unit.isDead()) {
				if (player == null) {
					player = unit.getPlayer();
				} else {
					if (!player.equals(unit.getPlayer())) {
						return null;
					}
				}
			}
		}
		return player;
	}

	/**
	 * Wird aufgerufen, wenn das Ziel einer Unit bei einem MoveDraw bereits
	 * belegt ist. Es berechnet das Feld, auf welches die Unit "ausweicht". Im
	 * schlimmsten Fall bleibt sie stehen.
	 */
	public GameCoordinate getFieldBefore(MoveDraw move, GameCoordinate target) {
		GameCoordinate source = move.getSource();
		GameCoordinate realTarget = move.getTarget();
		int a = realTarget.x - source.x;
		int b = realTarget.y - source.y;

		int absA = Math.abs(a);
		int absB = Math.abs(b);

		int fA = 1;
		int fB = 1;

		if (absA == 0) {
			fA = 0;
		} else {
			fA = a / absA;
		}

		if (absB == 0) {
			fB = 0;
		} else {
			fB = b / absB;
		}

		if (absA > absB) {
			absB++;
			GameCoordinate gameCoordinate = searchFieldBefor(target, realTarget, absA, absB, fA, fB, false);
			return new GameCoordinate(realTarget.x - (gameCoordinate.x * fA), realTarget.y - (gameCoordinate.y * fB));
		} else {
			absA++;
			GameCoordinate gameCoordinate = searchFieldBefor(target, realTarget, absB, absA, fA, fB, true);
			return new GameCoordinate(realTarget.x - (gameCoordinate.y * fA), realTarget.y - (gameCoordinate.x * fB));
		}
	}

	/**
	 * Helferklasse für getFieldBefore.
	 */
	private GameCoordinate searchFieldBefor(GameCoordinate target, GameCoordinate realTarget, int absA, int absB,
			int fA, int fB, boolean switchXY) {
		int anz = (int) ((absA + absB) / absB);
		int rest = absA + absB - anz * (absB);
		int x = 0;
		boolean found = false;

		for (int y = 0; y < absB; y++) {
			int tempAnz = anz;
			if ((absB - rest) / 2 < y && (absB + rest) / 2 >= y) {
				tempAnz++;
			}

			for (int j = 0; j < tempAnz; j++) {
				if (found) {
					return new GameCoordinate(x, y);
				}
				if (switchXY) {
					if (target.equals(new GameCoordinate(realTarget.x - (y * fA), realTarget.y - (x * fB)))) {
						found = true;
					}
				} else {
					if (target.equals(new GameCoordinate(realTarget.x - (x * fA), realTarget.y - (y * fB)))) {
						found = true;
					}
				}
				x++;
			}
			x--;
		}
		return null;
	}
}
