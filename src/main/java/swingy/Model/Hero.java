package swingy.Model;

public class Hero extends Entity {
	public enum Class {
		KNIGHT,

	}

	private Class	_class;
	private int		_level;
	private int		_experience;
	private int		_attack;
	private int		_defense;
	private int		_max_health;
	private int		_health;
	private Weapon	_weapon;
	private Armor	_armor;
	private Helm	_helm;

	public Hero(String name, Class hero_class, int max_health, int attack, int defense) {
		super(name);
		this._class = hero_class;
		this._max_health = max_health;
		this._health = this._max_health;
		this._attack = attack;
		this._defense = defense;
	}

	public Class	GetClass() {return this._class;}
	public int		GetLevel() {return this._level;}
	public int		GetExperience() {return this._experience;}
	public int		GetAttackPower() {return this._attack;}
	public int		GetDefense() {return this._defense;}
	public int		GetHealth() {return this._health;}
	public Weapon	GetWeapon() {return this._weapon;}
	public Armor	GetArmor() {return this._armor;}
	public Helm		GetHelm() {return this._helm;}

	public void TakeDamage(int damage) {this._health -= damage;}
	public void DealDamage(Entity entity, int damage) {entity.TakeDamage(damage);}

	public void EquipItem(Item item) {
		switch (item.GetType()) {
			case WEAPON:
				this._weapon = (Weapon)item;
				break;

			case ARMOR:
				this._armor = (Armor)item;
				break;
			
			case HELM:
				this._helm = (Helm)item;
				break;
		}
	}
}
