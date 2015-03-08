package name.ltp.jviolet.system;

import name.ltp.jviolet.M;

public class Obj
{
	public int m_width, m_height;
	public float m_left, m_right, m_top, m_bottom;
	public float X, Y;
	public float Scale;
	public float Angle;
	public float RMask, GMask, BMask, AMask;
	public float HitR;
	public float Acceleration, Speed;

	public Obj(float x, float y, int width, int height)
	{
		X = x;
		Y = y;
		m_width = width;
		m_height = height;
		m_left = -m_width / 2;
		m_right = m_width / 2;
		m_top = -m_height / 2;
		m_bottom = m_height / 2;
		Scale = 1.0f;
		Angle = 0.0f;
		RMask = BMask = GMask = AMask = 1.0f;
		HitR = 1.0f;
		Acceleration = 0.1f;
		Speed = 0.0f;
	}

	public float fixAngle(float angle)
	{
		if(angle < 0)
			angle += 360;
		else if(angle > 360)
			angle -= 360;

		return angle;
	}

	public void turn(float targetAngle, float angleSpeed, int deltaTime)
	{
		float arch1;
		float arch2;

		if(Angle > targetAngle)
		{
			arch1 = Angle - targetAngle;
			arch2 = 360 - arch1;
		}
		else
		{
			arch2 = targetAngle - Angle;
			arch1 = 360 - arch2;
		}

		float delta = deltaTime * angleSpeed;
		if(arch1 < arch2)
			if(arch1 < delta)
				Angle = targetAngle;
			else
				Angle -= delta;
		else if(arch2 < delta)
			Angle = targetAngle;
		else
			Angle += delta;
	}

	public float calc_angle(float x1, float y1, float x2, float y2)
	{
		return 180.0f / M.PI * M.atan2(x2 - x1, -y2 + y1);
	}

	public float calc_dist(float x1, float y1, float x2, float y2)
	{
		return M.hypot(x2 - x1, -y2 + y1); // FIXME:
	}

	public void move(int deltaTime)
	{
		X -= M.cos((Angle + 90) * M.PI / 180) * deltaTime * Speed;
		Y -= M.sin((Angle + 90) * M.PI / 180) * deltaTime * Speed;
	}

	public float getWidth()
	{
		return m_width;
	}
	public float getHeight()
	{
		return m_height;
	}
	public float getLeft()
	{
		return X + m_left * Scale;
	}
	public float getRight()
	{
		return X + m_right * Scale;
	}
	public float getTop()
	{
		return Y + m_top * Scale;
	}
	public float getBottom()
	{
		return Y + m_bottom * Scale;
	}

	public boolean detectCollide(Obj refObj)
	{
		return calc_dist(X, Y, refObj.X, refObj.Y) < HitR * Scale * m_width
			+ refObj.HitR * refObj.Scale * refObj.getWidth();
	}

	public boolean detectCollide(float x, float y)
	{
		return calc_dist(X, Y, x, y) < HitR * Scale * m_width;
	}

	public static class h
	{
		public float x, y;

		public h()
		{
			;
		}
	} public static final h h = new h();

	public boolean detectCollide(float x1, float y1, float x2, float y2)
	{
		return detectCollide(x1, y1, x2, y2, null);
	}

	public boolean detectCollide(float x1, float y1, float x2, float y2, h h)
	{
		float k = (y1 - y2) / (x1 - x2);
		float b = y1 - k * x1;
		float r = HitR * Scale * m_width;

		float d = (M.pow((2 * k * b - 2 * X - 2 * Y * k), 2) - (4 + 4 * M.pow(k, 2))
			* (M.pow(b, 2) - M.pow(r, 2) + M.pow(X, 2) + M.pow(Y, 2) - 2 * Y * b));

		if(d < 0)
		{
			return false;
		}

		float resX1 = (-(2 * k * b - 2 * X - 2 * Y * k) - M.sqrt(d)) / (2 + 2 * M.pow(
			k, 2));
		//	float resX2 = ((-(2* k * b - 2* targetX - 2* targetY * k) + sqrt(d)) / (2
		//			+ 2* k * k));

		float resY1 = k * resX1 + b;

		if(M.abs(
			M.abs(x1 - resX1) + M.abs(x2 - resX1)
				- M.abs(x2 - x1)) < 1)
		{
			if(h != null)
			{
				h.x = resX1;
				h.y = resY1;
			}
//			*ix = resX1;
//			*iy = resY1;

			return true;
		}

		//	if (resX1 == resX2) {
		//	one intersection at resX1, y1
		//		return true;
		//	}

		// two intersections: resX1, y1 и resX2, y2
		// float y1 = k * resX1 + b;
		// float y2 = k * resX2 + b;

		return false;
	}

	public void setMask(float _RMask, float _GMask, float _BMask, float _AMask)
	{
		RMask = _RMask;
		GMask = _GMask;
		BMask = _BMask;
		AMask = _AMask;
	}
}

