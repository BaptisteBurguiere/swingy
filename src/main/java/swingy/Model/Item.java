package swingy.Model;

public abstract class Item {
	public enum Type {
		WEAPON,
		ARMOR,
		HELM
	}

	public enum Rarity {
		COMMON,
		RARE,
		EPIC,
		LEGENDARY
	}

	protected String	_name;
	protected Type		_type;
	protected int		_stat;
	protected Rarity	_rarity;

	public Item(Type type, Rarity rarity, String name, int stat) {this._type = type; this._rarity = rarity; this._name = name; this._stat = stat;}

	public int ApplyEffect(int base_stat) {return this._stat + base_stat;}

	public String	GetName() {return this._name;}
	public Type		GetType() {return this._type;}
	public int		GetStat() {return this._stat;}
	public Rarity	GetRarity() {return this._rarity;}
}