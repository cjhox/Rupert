package client.resources;

import shared.Player;
import shared.Unittype;

public interface UnitListenerFactoryInterface {

	public GameListener getInstance(Unittype unittype, Player player);

}