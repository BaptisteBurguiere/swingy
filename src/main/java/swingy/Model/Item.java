package swingy.Model;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class Item
{
	public enum Type
	{
		WEAPON,
		ARMOR,
		HELMET,
		RELIC,
	}

	public enum Rarity
	{
		COMMON,
		RARE,
		EPIC,
		LEGENDARY
	}

	private final Type		_type;
	private final Rarity	_rarity;
	private final String	_name;
	private final String	_description;
	private final Map<StatisticTemplate.Type, Double>	_statistics;

	public Item(Type type, Rarity rarity, String name, String description, Map<StatisticTemplate.Type, Double> statistics)
	{
		this._type = type;
		this._rarity = rarity;
		this._name = name;
		this._description = description;
		this._statistics = new EnumMap<>(statistics);
	}

	public Type									GetType() {return this._type;}
	public Rarity								GetRarity() {return this._rarity;}
	public String								GetName() {return this._name;}
	public String								GetDescription() {return this._description;}
	public Map<StatisticTemplate.Type, Double>	GetStatistics() {return Collections.unmodifiableMap(this._statistics);}
}