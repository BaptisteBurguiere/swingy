package swingy.Model;

public class Statistic
{
	private final StatisticTemplate	_template;
	private double 					_value;

	public Statistic(StatisticTemplate template, double value)
	{
		this._template = template;
		this._value = value;
	}

	public Statistic(StatisticTemplate.Type type, double value)
	{
		this._template = StatisticTemplate.Get(type);
		this._value = value;
	}

	public StatisticTemplate.Type	GetType() {return this._template.GetType();}
	public String					GetName() {return this._template.GetName();}
	public String					GetDescription() {return this._template.GetDescription();}
	public double					GetValue() {return this._value;}
	
	public void		SetValue(double new_value) {this._value = new_value;}
	public void		Increase(double amount) {this._value += amount;}
	public void		Decrease(double amount) {this._value -= amount;}
}
