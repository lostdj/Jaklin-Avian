package myavn.test.jbox2d;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.DebugDraw;
import org.jbox2d.testbed.AbstractExample;
import org.jbox2d.testbed.TestSettings;
import org.jbox2d.testbed.TestbedMain_;
import org.jbox2d.testbed.tests.*;

import java.util.ArrayList;
import java.util.Random;

public class Main implements TestbedMain_
{
	public static Main tb;

	public Random rnd = new Random();
	@Override
	public float random(float f, float t)
	{
		return (float)(rnd.nextDouble() * (f - t) + f);
	}

	public ArrayList<AbstractExample> tests = new ArrayList<AbstractExample>(0);
	public AbstractExample currentTest = null;
	protected int currentTestIndex = 0;
	protected boolean handleOptions = false;
	final static float targetFPS = 60.0f;
	final int fpsAverageCount = 100;
	long[] nanos;
	long nanoStart; //
	long frameCount = 0;
	public DebugDraw g;
	@Override
	public DebugDraw g() {return g;}

	@Override
	public boolean shiftKey() {return false;}

	public Main() {
		setup();
	}

	public static int width = 640, height = 480, bpp = 32;

	public void setup() {
		g = new glDebugDraw();

		// Simple functionality examples
//    	registerExample(new SpriteBinding(this));

    	registerExample(new Pulleys(this));
    	registerExample(new Overhang(this));
    	registerExample(new VaryingRestitution(this));
    	registerExample(new VaryingFriction(this));
    	registerExample(new MotorsAndLimits(this));
    	registerExample(new VerticalStack(this));
    	registerExample(new Domino(this));
    	registerExample(new CompoundShapes(this));
    	registerExample(new Chain(this));
    	registerExample(new Bridge(this));
    	registerExample(new Gears(this));

		// Stress tests
    	registerExample(new Pyramid(this));
    	registerExample(new DominoTower(this));
		registerExample(new Circles(this));

		// Bug tests
		registerExample(new CCDTest(this));
		registerExample(new DistanceTest(this));

		//registerExample(new BugTest(this));

    	/* Set up the timers for FPS reporting */
		nanos = new long[fpsAverageCount];
		long nanosPerFrameGuess = (long)(1000000000.0 / targetFPS);
		nanos[fpsAverageCount-1] = System.nanoTime();
		for (int i=fpsAverageCount-2; i>=0; --i) {
			nanos[i] = nanos[i+1] - nanosPerFrameGuess;
		}
		nanoStart = System.nanoTime();

		gl.window(width, height, bpp);

		init();
		reshape();
	}

	public void init() {
		gl.glLineWidth(1f);
		gl.glEnable(gl.GL_BLEND);
		gl.glBlendFunc(gl.GL_SRC_ALPHA, gl.GL_ONE_MINUS_SRC_ALPHA);
	}

//	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	public void reshape() {
		gl.glMatrixMode(gl.GL_PROJECTION);
		gl.glLoadIdentity();

		// coordinate system origin at lower left with width and height same as the window
		gl.gluOrtho2D(0.0f, width, 0.0f, height);

		gl.glMatrixMode(gl.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glViewport(0, 0, width, height);
	}

	public void update() {
		gl.glClear(gl.GL_COLOR_BUFFER_BIT/* | gl.GL_DEPTH_BUFFER_BIT*/);
		draw();
		gl.glFlush();
	}

	long last;
	public void draw() {
		if (handleOptions) {
//    		options.handleOptions();
		} else{
//    		background(0);
			Vec2.creationCount = 0;

			if(gl.key())
			{
    		++currentTestIndex;
    		if (currentTestIndex >= tests.size()) currentTestIndex = 0;
    		//System.out.println(currentTestIndex);
    		currentTest = tests.get(currentTestIndex);
    		currentTest.needsReset = true;
			}

    		/* Make sure we've got a valid test to run and reset it if needed */
			if (currentTest == null) {
				currentTestIndex = 0;
				currentTest = tests.get(currentTestIndex);
				nanoStart = System.nanoTime();
				frameCount = 0;
			}
			if (currentTest.needsReset) {
				//System.out.println("Resetting "+currentTest.getName());
				TestSettings s = currentTest.settings; //copy settings
				currentTest.initialize();
				if (s != null) currentTest.settings = s;
				nanoStart = System.nanoTime();
				frameCount = 0;
			}
			currentTest.m_textLine = AbstractExample.textLineHeight;
			g.drawString(5, currentTest.m_textLine, currentTest.getName(),AbstractExample.white);
			currentTest.m_textLine += 2*AbstractExample.textLineHeight;

    		/* Take our time step (drawing is done here, too) */
			currentTest.step();

    		/* If the user wants to move the canvas, do it */
//    		handleCanvasDrag();


    		/* ==== Vec2 creation and FPS reporting ==== */
			if (currentTest.settings.drawStats) {
				g.drawString(5, currentTest.m_textLine, "Vec2 creations/frame: "+Vec2.creationCount, AbstractExample.white);
				currentTest.m_textLine += AbstractExample.textLineHeight;
			}

			for (int i=0; i<fpsAverageCount-1; ++i) {
				nanos[i] = nanos[i+1];
			}
			nanos[fpsAverageCount-1] = System.nanoTime();
			float averagedFPS = (float) ( (fpsAverageCount-1) * 1000000000.0 / (nanos[fpsAverageCount-1]-nanos[0]));
			++frameCount;
			float totalFPS = (float) (frameCount * 1000000000 / (1.0*(System.nanoTime()-nanoStart)));
			if (currentTest.settings.drawStats) {
				g.drawString(5, currentTest.m_textLine, "Average FPS ("+fpsAverageCount+" frames): "+averagedFPS, AbstractExample.white);
				currentTest.m_textLine += AbstractExample.textLineHeight;
				g.drawString(5, currentTest.m_textLine, "Average FPS (entire test): "+totalFPS, AbstractExample.white);
				currentTest.m_textLine += AbstractExample.textLineHeight;
			}

			if((System.currentTimeMillis() - last) >= 1000)
			{
				System.out.println("Average FPS (" + fpsAverageCount + " frames): " + averagedFPS);
				System.out.println("Average FPS (entire test): " + totalFPS);
				System.out.println();

				last = System.currentTimeMillis();
			}
		}
	}

	public void registerExample(AbstractExample test) {
		tests.add(test);
	}

	public static void tick() throws Exception
	{
		tb.update();
//		Thread.sleep(1);
	}

	double start = 0, diff, wait;
	void capFrameRate(double fps) throws Exception
	{
		wait = 1 / fps;
		diff = System.currentTimeMillis() - start;
		if (diff < wait) {
			Thread.sleep((long)(wait - diff));
		}
		start = System.currentTimeMillis();
	}

	static {/*gl._load();*/ tb = new Main();}

	int i = 1337;
//	public native int ntest();

	static public void main(String args[]) throws Exception
	{
		tb.start = System.currentTimeMillis();
		System.out.println("---"+tb.i+"");
//		System.out.println("---"+tb.ntest()+"");
		for(;;)
		{
			tb.update();
//			tb.capFrameRate(30);
//			Thread.sleep(1);
		}
	}
}

