package shared.gameround;

import java.util.ArrayList;

import shared.Player;

public class PlacePhase extends Phase<PlaceDraw> {

	private static final long	serialVersionUID	= 7147046423143702559L;

	public PlacePhase(Player player) {
		this(player, new ArrayList<PlaceDraw>());
	}

	public PlacePhase(Player player, ArrayList<PlaceDraw> draws) {
		super(player);
		this.draws = draws;
	}
}
