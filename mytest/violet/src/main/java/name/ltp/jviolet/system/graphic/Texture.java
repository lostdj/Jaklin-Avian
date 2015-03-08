package name.ltp.jviolet.system.graphic;

public class Texture
{
	public long m_textureId;
	public long m_type;
	public int m_width, m_height;

	Texture(int width, int height, long type, long filter)
	{
		m_type = type;
		m_width = width;
		m_height = height;

		// FIXME:
//		char* colorBits = new char[width * height * 3];
//
//		glGenTextures(1, &m_textureId);
//		glBindTexture(m_type, m_textureId);
//
//		glTexParameteri(m_type, GL_TEXTURE_MIN_FILTER, filter);
//		glTexParameteri(m_type, GL_TEXTURE_MAG_FILTER, filter);
//
//		glTexImage2D(m_type, 0, 3, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE,
//			colorBits);
//
//		delete[] colorBits;
	}

	public Texture(SDL_Surface *surface, /*GLenum*/long type, /*GLint*/int filter,
	boolean takeCareOfSurface)
	{
		m_type = type;
		m_width = surface->w;
		m_height = surface->h;

		GLint nOfColors;
		GLenum texture_format;

		nOfColors = surface->format->BytesPerPixel;
		if (nOfColors == 4) {
			if (surface->format ->Rmask == 0x000000ff) {
				texture_format = GL_RGBA;
			} else {
				texture_format = GL_BGRA;
			}
		} else if (nOfColors == 3) {
			if (surface->format->Rmask == 0x000000ff)
				texture_format = GL_RGB;
			else
				texture_format = GL_BGR;
		} else {
			std::cout << "Couldn't create GL texture from the SDL surface!" << std::endl;
			throw 1;
		}

		glGenTextures(1, &m_textureId);
		glBindTexture(m_type, m_textureId);

		glTexParameteri(m_type, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(m_type, GL_TEXTURE_MAG_FILTER, filter);

		glTexImage2D(m_type, 0, nOfColors, surface->w, surface->h, 0,
			texture_format, GL_UNSIGNED_BYTE, surface->pixels);

		if (surface && takeCareOfSurface) {
			SDL_FreeSurface(surface);
		}
	}

//	public ~Texture() {
//	glDeleteTextures(1, &m_textureId);
//}

	int getWidth() {
	return m_width;
}
	int getHeight() {
	return m_height;
}
	long getTextureId() {
	return m_textureId;
}
	long getType() {
	return m_type;
}
}
