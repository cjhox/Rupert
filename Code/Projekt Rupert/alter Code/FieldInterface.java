package shared.game;

import java.io.Serializable;

import shared.resource.Fieldtype;


public interface FieldInterface extends Serializable {

	public void setSelection(boolean select);

	public boolean getSelection();
	
	public UnitInterface getUnit();

	public Fieldtype getFieldtype();

	public GameCoordinate getGameCoordinate();

	public String toString();
}