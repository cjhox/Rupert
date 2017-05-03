package shared.game;

import java.io.Serializable;

/**
 * Die Klasse Health wird auf jeder Unit Instanziert und wird für das Nachführen
 * des Lebenspunkte jeder Unit benötigt.
 */

public class Health implements Serializable {

	// inv: health >= 0
	// inv: !(!isDead() && old isDead()) -> keine Wiederbelebung

	private static final long	serialVersionUID	= -3490219132965534582L;

	private int					health;
	private int	maxHealth;

	// pre: health >= 0
	public Health(int health) {
		super();
		maxHealth = health;
		this.health = health;
	}

	// pure
	public int getHealth() {
		return health;
	}

	// pre: damage >= 0
	// post: health <= old health
	public void hurt(int damage) {
		health -= damage;
		if (health < 0) {
			health = 0;
		}
	}

	// pure
	public boolean isDead() {
		return health == 0;
	}

	public void heal(int healing) {
		if (!isDead()) {
			health += healing;
			if (health > maxHealth) {
				health = maxHealth;
			}
		}
	}

	@Override
	public String toString() {
		return "Health [health=" + health + ", maxHealth=" + maxHealth + "]";
	}
}
