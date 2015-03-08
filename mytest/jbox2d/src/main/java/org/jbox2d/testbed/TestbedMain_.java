package org.jbox2d.testbed;

import org.jbox2d.dynamics.DebugDraw;

public interface TestbedMain_
{
	DebugDraw g();
	public float random(float f, float t);
	boolean shiftKey();
}
