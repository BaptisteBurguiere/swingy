package swingy.Model;

import java.util.EnumMap;
import java.util.Map;

public class StatisticTemplate
{
	public enum Type
	{
		HEALTH,
		ATTACK,
		DEFENSE,
		SPEED,
		EVASION,
		ACCURACY,
		CRIT_CHANCE,
		CRIT_DAMAGE,
		LUCK
	}

	private final Type		_type;
	private final String	_name;
	private final String	_description;

	public	StatisticTemplate(Type type, String name, String description)
	{
		this._type = type;
		this._name = name;
		this._description = description;
	}

	public	Type	GetType() {return this._type;}
	public	String	GetName() {return this._name;}
	public	String	GetDescription() {return this._description;}

	private static final Map<Type, StatisticTemplate> _templates = new EnumMap<>(Type.class);

	static
	{
		_templates.put(Type.HEALTH, new StatisticTemplate(Type.HEALTH, "Health", "Determines total health points"));
		_templates.put(Type.ATTACK, new StatisticTemplate(Type.ATTACK, "Attack", "Determines damage dealt"));
		_templates.put(Type.DEFENSE, new StatisticTemplate(Type.DEFENSE, "Defense", "Reduces incoming damage, increases parry chance when blocking"));
		_templates.put(Type.SPEED, new StatisticTemplate(Type.SPEED, "Speed", "Determines turn order"));
		_templates.put(Type.EVASION, new StatisticTemplate(Type.EVASION, "Evasion", "Chance to dodge attacks"));
		_templates.put(Type.ACCURACY, new StatisticTemplate(Type.ACCURACY, "Accuracy", "Reduces chance to miss an attack"));
		_templates.put(Type.CRIT_CHANCE, new StatisticTemplate(Type.CRIT_CHANCE, "Critical Chance", "Chance to land a critical hit"));
		_templates.put(Type.CRIT_DAMAGE, new StatisticTemplate(Type.CRIT_DAMAGE, "Critical Damage", "Extra damage dealt on critical hit"));
		_templates.put(Type.LUCK, new StatisticTemplate(Type.LUCK, "Luck", "Affects drops, crits, evades, parries"));
	}

	public StatisticTemplate Get(Type type) {return _templates.get(type);}
}