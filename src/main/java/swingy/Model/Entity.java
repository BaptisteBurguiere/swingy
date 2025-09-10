package swingy.Model;

public abstract class Entity {
	protected String	_name;

	public Entity() {this._name = "Default";}
	public Entity(String name) {this._name = name;}

	public abstract void	TakeDamage(int damage);
	public abstract void	DealDamage(Entity entity, int damage);

	public String	GetName() {return this._name;}
	public void		SetName(String new_name) {this._name = new_name;}
}
