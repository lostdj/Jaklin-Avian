package myavn.test.jbox2d;

import org.jbox2d.common.*;
import org.jbox2d.dynamics.DebugDraw;

public class glDebugDraw extends DebugDraw
{
	private static final int NUM_CIRCLE_POINTS = 13;

	private final float[] mat = new float[16];

	public glDebugDraw() {
		mat[8] = 0;
		mat[9] = 0;
		mat[2] = 0;
		mat[6] = 0;
		mat[10] = 1;
		mat[14] = 0;
		mat[3] = 0;
		mat[7] = 0;
		mat[11] = 0;
		mat[15] = 1;
	}

	@Override
	public void setViewportTransform(IViewportTransform viewportTransform) {
		viewportTransform.setYFlip(false);
		super.setViewportTransform(viewportTransform);
	}


	public void transformViewport(Vec2 center) {
		Vec2 e = viewportTransform.getExtents();
		Vec2 vc = viewportTransform.getCenter();
		Mat22 vt = viewportTransform.getMat22Representation();

		int f = viewportTransform.isYFlip() ? -1 : 1;
		mat[0] = vt.ex.x;
		mat[4] = vt.ey.x;
		// mat[8] = 0;
		mat[12] = e.x;
		mat[1] = f * vt.ex.y;
		mat[5] = f * vt.ey.y;
		// mat[9] = 0;
		mat[13] = e.y;
		// mat[2] = 0;
		// mat[6] = 0;
		// mat[10] = 1;
		// mat[14] = 0;
		// mat[3] = 0;
		// mat[7] = 0;
		// mat[11] = 0;
		// mat[15] = 1;

		gl.glMultMatrixf(mat);
		gl.glTranslatef(center.x - vc.x, center.y - vc.y, 0);
	}

	@Override
	public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f argColor) {
		Vec2 vec = getWorldToScreen(argPoint);
		gl.glPointSize(argRadiusOnScreen);
		gl.glBegin(gl.GL_POINTS);
		gl.glVertex2f(vec.x, vec.y);
		gl.glEnd();
	}

	private final Vec2 zero = new Vec2();

	@Override
	public void drawPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
		gl.glPushMatrix();
		transformViewport(zero);
		gl.glBegin(gl.GL_LINE_LOOP);
		gl.glColor4f(color.x, color.y, color.z, 1f);
		for (int i = 0; i < vertexCount; i++) {
			Vec2 v = vertices[i];
			gl.glVertex2f(v.x, v.y);
		}
		gl.glEnd();
		gl.glPopMatrix();
	}

	@Override
	public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
		gl.glPushMatrix();
		transformViewport(zero);
		gl.glBegin(gl.GL_TRIANGLE_FAN);
		gl.glColor4f(color.x, color.y, color.z, .4f);
		for (int i = 0; i < vertexCount; i++) {
			Vec2 v = vertices[i];
			gl.glVertex2f(v.x, v.y);
		}
		gl.glEnd();

		gl.glBegin(gl.GL_LINE_LOOP);
		gl.glColor4f(color.x, color.y, color.z, 1f);
		for (int i = 0; i < vertexCount; i++) {
			Vec2 v = vertices[i];
			gl.glVertex2f(v.x, v.y);
		}
		gl.glEnd();
		gl.glPopMatrix();
	}

	@Override
	public void drawCircle(Vec2 center, float radius, Color3f color) {
		gl.glPushMatrix();
		transformViewport(zero);
		float theta = 2 * MathUtils.PI / NUM_CIRCLE_POINTS;
		float c = MathUtils.cos(theta);
		float s = MathUtils.sin(theta);
		float x = radius;
		float y = 0;
		float cx = center.x;
		float cy = center.y;
		gl.glBegin(gl.GL_LINE_LOOP);
		gl.glColor4f(color.x, color.y, color.z, 1);
		for (int i = 0; i < NUM_CIRCLE_POINTS; i++) {
			gl.glVertex3f(x + cx, y + cy, 0);
			// apply the rotation matrix
			float temp = x;
			x = c * x - s * y;
			y = s * temp + c * y;
		}
		gl.glEnd();
		gl.glPopMatrix();
	}

//  @Override
//  public void drawCircle(Vec2 center, float radius, Vec2 axis, Color3f color) {
//    GL2 gl = panel.getGL().getGL2();
//    gl.glPushMatrix();
//    transformViewport(gl, zero);
//    float theta = 2 * MathUtils.PI / NUM_CIRCLE_POINTS;
//    float c = MathUtils.cos(theta);
//    float s = MathUtils.sin(theta);
//    float x = radius;
//    float y = 0;
//    float cx = center.x;
//    float cy = center.y;
//    gl.glBegin(GL2.GL_LINE_LOOP);
//    gl.glColor4f(color.x, color.y, color.z, 1);
//    for (int i = 0; i < NUM_CIRCLE_POINTS; i++) {
//      gl.glVertex3f(x + cx, y + cy, 0);
//      // apply the rotation matrix
//      float temp = x;
//      x = c * x - s * y;
//      y = s * temp + c * y;
//    }
//    gl.glEnd();
//    gl.glBegin(GL2.GL_LINES);
//    gl.glVertex3f(cx, cy, 0);
//    gl.glVertex3f(cx + axis.x * radius, cy + axis.y * radius, 0);
//    gl.glEnd();
//    gl.glPopMatrix();
//  }

	@Override
	public void drawSolidCircle(Vec2 center, float radius, Vec2 axis, Color3f color) {
		gl.glPushMatrix();
		transformViewport(zero);
		float theta = 2 * MathUtils.PI / NUM_CIRCLE_POINTS;
		float c = MathUtils.cos(theta);
		float s = MathUtils.sin(theta);
		float x = radius;
		float y = 0;
		float cx = center.x;
		float cy = center.y;
		gl.glBegin(gl.GL_TRIANGLE_FAN);
		gl.glColor4f(color.x, color.y, color.z, .4f);
		for (int i = 0; i < NUM_CIRCLE_POINTS; i++) {
			gl.glVertex3f(x + cx, y + cy, 0);
			// apply the rotation matrix
			float temp = x;
			x = c * x - s * y;
			y = s * temp + c * y;
		}
		gl.glEnd();
		gl.glBegin(gl.GL_LINE_LOOP);
		gl.glColor4f(color.x, color.y, color.z, 1);
		for (int i = 0; i < NUM_CIRCLE_POINTS; i++) {
			gl.glVertex3f(x + cx, y + cy, 0);
			// apply the rotation matrix
			float temp = x;
			x = c * x - s * y;
			y = s * temp + c * y;
		}
		gl.glEnd();
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(cx, cy, 0);
		gl.glVertex3f(cx + axis.x * radius, cy + axis.y * radius, 0);
		gl.glEnd();
		gl.glPopMatrix();
	}

	@Override
	public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
		gl.glPushMatrix();
		transformViewport(zero);
		gl.glBegin(gl.GL_LINES);
		gl.glColor3f(color.x, color.y, color.z);
		gl.glVertex3f(p1.x, p1.y, 0);
		gl.glVertex3f(p2.x, p2.y, 0);
		gl.glEnd();
		gl.glPopMatrix();
	}

	@Override
	public void drawXForm(XForm xf)
	{
		; // TODO:
	}

//  @Override
//  public void drawParticles(Vec2[] centers, float radius, ParticleColor[] colors, int count) {
//    GL2 gl = panel.getGL().getGL2();
//    gl.glPushMatrix();
//    transformViewport(gl, zero);
//
//    float theta = 2 * MathUtils.PI / NUM_CIRCLE_POINTS;
//    float c = MathUtils.cos(theta);
//    float s = MathUtils.sin(theta);
//
//    float x = radius;
//    float y = 0;
//
//    for (int i = 0; i < count; i++) {
//      Vec2 center = centers[i];
//      float cx = center.x;
//      float cy = center.y;
//      gl.glBegin(GL2.GL_TRIANGLE_FAN);
//      if (colors == null) {
//        gl.glColor4f(1, 1, 1, .4f);
//      } else {
//        ParticleColor color = colors[i];
//        gl.glColor4b(color.r, color.g, color.b, color.a);
//      }
//      for (int j = 0; j < NUM_CIRCLE_POINTS; j++) {
//        gl.glVertex3f(x + cx, y + cy, 0);
//        float temp = x;
//        x = c * x - s * y;
//        y = s * temp + c * y;
//      }
//      gl.glEnd();
//    }
//    gl.glPopMatrix();
//  }


//  @Override
//  public void drawParticlesWireframe(Vec2[] centers, float radius, ParticleColor[] colors, int count) {
//    GL2 gl = panel.getGL().getGL2();
//    gl.glPushMatrix();
//    transformViewport(gl, zero);
//
//    float theta = 2 * MathUtils.PI / NUM_CIRCLE_POINTS;
//    float c = MathUtils.cos(theta);
//    float s = MathUtils.sin(theta);
//
//    float x = radius;
//    float y = 0;
//
//    for (int i = 0; i < count; i++) {
//      Vec2 center = centers[i];
//      float cx = center.x;
//      float cy = center.y;
//      gl.glBegin(GL2.GL_LINE_LOOP);
//      if (colors == null) {
//        gl.glColor4f(1, 1, 1, 1);
//      } else {
//        ParticleColor color = colors[i];
//        gl.glColor4b(color.r, color.g, color.b, (byte) 127);
//      }
//      for (int j = 0; j < NUM_CIRCLE_POINTS; j++) {
//        gl.glVertex3f(x + cx, y + cy, 0);
//        float temp = x;
//        x = c * x - s * y;
//        y = s * temp + c * y;
//      }
//      gl.glEnd();
//    }
//    gl.glPopMatrix();
//  }

	private final Vec2 temp = new Vec2();
	private final Vec2 temp2 = new Vec2();

//  @Override
//  public void drawTransform(Transform xf) {
//    GL2 gl = panel.getGL().getGL2();
//    getWorldToScreenToOut(xf.p, temp);
//    temp2.setZero();
//    float k_axisScale = 0.4f;
//
//    gl.glBegin(GL2.GL_LINES);
//    gl.glColor3f(1, 0, 0);
//
//    temp2.x = xf.p.x + k_axisScale * xf.q.c;
//    temp2.y = xf.p.y + k_axisScale * xf.q.s;
//    getWorldToScreenToOut(temp2, temp2);
//    gl.glVertex2f(temp.x, temp.y);
//    gl.glVertex2f(temp2.x, temp2.y);
//
//    gl.glColor3f(0, 1, 0);
//    temp2.x = xf.p.x + -k_axisScale * xf.q.s;
//    temp2.y = xf.p.y + k_axisScale * xf.q.c;
//    getWorldToScreenToOut(temp2, temp2);
//    gl.glVertex2f(temp.x, temp.y);
//    gl.glVertex2f(temp2.x, temp2.y);
//    gl.glEnd();
//  }

	@Override
	public void drawString(float x, float y, String s, Color3f color) {
		;
//    text.beginRendering(panel.getWidth(), panel.getHeight());
//    text.setColor(color.x, color.y, color.z, 1);
//    text.draw(s, (int) x, panel.getHeight() - (int) y);
//    text.endRendering();
	}
}
