package name.ltp.jviolet.game.bullets;

import name.ltp.jviolet.M;
import name.ltp.jviolet.system.Obj;
import name.ltp.jviolet.system.graphic.StaticObject;
import name.ltp.jviolet.system.graphic.Texture;

public class Flame extends Bullet
{
	public StaticObject m_img;

	public Flame(float x, float y, Texture tex)
	{
		super(x, y, x, y, BulletType.BULLET_FLAME);

		m_img = new StaticObject(x, y, 128, 128, tex, false);
//		m_img->RMask = 1.0f;
//		m_img->GMask = (M.random() % 50) / 100 + 0.4f;
//		m_img->BMask = 0.3f;
		m_img->Scale = 0.001f;
	}

	public void process(int deltaTime) {
	float dist = m_range / MaxRange;

	if (m_active && dist >= 0.7)
		m_active = false;

	if (dist >= 1.0)
		m_readyToRemove = true;

	float relSpeed = (1.0f - dist) * Speed;

	X -= (float)M.cos((Angle + 90) * M.PI / 180) * deltaTime * relSpeed;
	Y -= (float)M.sin((Angle + 90) * M.PI / 180) * deltaTime * relSpeed;

	m_img->X = X;
	m_img->Y = Y;

	m_range += relSpeed * deltaTime;

	m_img->Scale = 2.5f * dist;

	if (dist < 0.5)
		m_img->AMask = dist;
	else
		m_img->AMask = 1.0f - dist;
}

	public void draw() {
	m_img->draw(false, false);
}

	public boolean checkHit(Obj objRef) {
	boolean hit = m_active && objRef.detectCollide(m_img);
	return hit;
}

//	Flame::~Flame() {
//	delete m_img;
//}
}

