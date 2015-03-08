package name.ltp.jviolet.game.bullets;

import name.ltp.jviolet.system.Obj;

public class Bullet extends Obj
{
	public boolean m_active, m_readyToRemove;
	public float startX, startY;
	public float dStartX, dStartY;
	public float m_range;
	public float Damage;
	public float MaxRange;
	public BulletType Type;
	public String OwnerId;

	public boolean Poisoned;
	public boolean BigCalibre;
	public boolean Penetrating;

	public Bullet(float x, float y, float dX, float dY, BulletType type)
	{
		super(x, y, 1, 1);

		startX = x;
		startY = y;
		dStartX = dX;
		dStartY = dY;
		m_active = true;
		m_readyToRemove = false;
		Type = type;
		m_range = 0;
		Poisoned = false;
		BigCalibre = false;
		Penetrating = false;
	}

	public void process(int deltaTime)
	{
		//nothing
	}

	public void draw()
	{
		//nothing
	}

	public boolean checkHit(Obj objRef)
	{
		return false;
	}

	public boolean isActive()
	{
		return m_active;
	}

	public boolean isReadyToRemove()
	{
		return m_readyToRemove;
	}

	public void deactivate()
	{
		m_active = false;
	}
}

