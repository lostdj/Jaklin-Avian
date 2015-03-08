package name.ltp.jviolet.system.graphic;

import name.ltp.jviolet.system.Obj;

public class StaticObject extends Obj
{
	public Texture m_texture;
	public boolean m_takeCareOfTexture;

	public StaticObject(float x, float y, int w, int h, Texture texture, boolean takeCareOfTexture)
	 {
		 super(x, y, w, h);
		m_texture = texture;
		m_takeCareOfTexture = takeCareOfTexture;
	}

	public long createComplexFace(int facesCount) {
	GLuint dListId = glGenLists(1);

	glNewList(dListId, GL_COMPILE);
	float faceWidth = (float) m_width / facesCount;
	float faceHeight = (float) m_height / facesCount;
	glBegin( GL_QUADS);
	for (int i = 0; i < facesCount; i++) {
		for (int j = 0; j < facesCount; j++) {
			float faceStartX = (float) i / facesCount;
			float faceStartY = (float) j / facesCount;
			float faceEndX = (float) (i + 1) / facesCount;
			float faceEndY = (float) (j + 1) / facesCount;

			glNormal3f(0.0f, 0.0f, 1.0f);
			glColor4f(RMask, GMask, BMask, AMask);

			glTexCoord2f(faceStartX, faceStartY);
			glVertex3f(m_left + faceWidth * i, m_top + faceHeight * j, 0);
			glTexCoord2f(faceEndX, faceStartY);
			glVertex3f(m_left + faceWidth * (i + 1), m_top + faceHeight * j, 0);
			glTexCoord2f(faceEndX, faceEndY);
			glVertex3f(m_left + faceWidth * (i + 1), m_top + faceHeight * (j
					+ 1), 0);
			glTexCoord2f(faceStartX, faceEndY);
			glVertex3f(m_left + faceWidth * i, m_top + faceHeight * (j + 1), 0);
		}
	}
	glEnd();
	glEndList();

	return dListId;
}

	public void draw(boolean hreflect, boolean vreflect, float x, float y,
	float angle, float scale) {
	GLfloat right = 1;
	GLfloat bottom = 1;

	if (m_texture->getType() == 0x84F5) {
		glEnable(0x84F5); //GL_TEXTURE_RECTANGLE_NV = GL_TEXTURE_RECTANGLE_ARB = 0x84F5
		right = m_texture->getWidth();
		bottom = m_texture->getHeight();
	}

	glBindTexture(m_texture->getType(), m_texture->getTextureId());

	glPushMatrix();

	glTranslatef(x, y, 0.0f);
	glRotatef(angle, 0.0f, 0.0f, 1.0f);
	glScalef(scale, scale, scale);

	glBegin( GL_QUADS);

	glNormal3f(0.0f, 0.0f, 1.0f);

	glColor4f(RMask, GMask, BMask, AMask);

	glTexCoord2f(hreflect ? right : 0, vreflect ? bottom : 0);
	glVertex3f(m_left, m_top, 0);
	glTexCoord2f(hreflect ? 0 : right, vreflect ? bottom : 0);
	glVertex3f(m_right, m_top, 0);
	glTexCoord2f(hreflect ? 0 : right, vreflect ? 0 : bottom);
	glVertex3f(m_right, m_bottom, 0);
	glTexCoord2f(hreflect ? right : 0, vreflect ? 0 : bottom);
	glVertex3f(m_left, m_bottom, 0);
	glEnd();

	glPopMatrix();

	if (m_texture->getType() == 0x84F5)
		glDisable(0x84F5); //GL_TEXTURE_RECTANGLE_NV = GL_TEXTURE_RECTANGLE_ARB = 0x84F5
}

	public void draw(boolean hreflect, boolean vreflect) {
	draw(hreflect, vreflect, X, Y, Angle, Scale);
}

	public void draw(/*GLuint*/long dListId) {
	glBindTexture(m_texture->getType(), m_texture->getTextureId());

	glPushMatrix();

	glTranslatef(X, Y, 0.0f);
	glRotatef(Angle, 0.0f, 0.0f, 1.0f);
	glScalef(Scale, Scale, Scale);

	glCallList(dListId);

	glPopMatrix();
}

	public void setTexture(Texture texture, boolean takeCareOfTexture) {
	if (m_takeCareOfTexture)
		delete m_texture;

	m_texture = texture;
	m_takeCareOfTexture = takeCareOfTexture;
}

//	StaticObject::~StaticObject() {
//	if (m_takeCareOfTexture)
//		delete m_texture;
//}

}
