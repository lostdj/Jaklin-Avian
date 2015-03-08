package myavn.test.jbox2d;

public class gl
{
	public static final int GL_DEPTH_BUFFER_BIT = 0x00000100;
	public static final int GL_STENCIL_BUFFER_BIT = 0x00000400;
	public static final int GL_COLOR_BUFFER_BIT = 0x00004000;
	public static final int GL_FALSE = 0;
	public static final int GL_TRUE = 1;
	public static final int GL_POINTS = 0x0000;
	public static final int GL_LINES = 0x0001;
	public static final int GL_LINE_LOOP = 0x0002;
	public static final int GL_LINE_STRIP = 0x0003;
	public static final int GL_TRIANGLES = 0x0004;
	public static final int GL_TRIANGLE_STRIP = 0x0005;
	public static final int GL_TRIANGLE_FAN = 0x0006;
	public static final int GL_ZERO = 0;
	public static final int GL_ONE = 1;
	public static final int GL_SRC_COLOR = 0x0300;
	public static final int GL_ONE_MINUS_SRC_COLOR = 0x0301;
	public static final int GL_SRC_ALPHA = 0x0302;
	public static final int GL_ONE_MINUS_SRC_ALPHA = 0x0303;
	public static final int GL_DST_ALPHA = 0x0304;
	public static final int GL_ONE_MINUS_DST_ALPHA = 0x0305;
	public static final int GL_DST_COLOR = 0x0306;
	public static final int GL_ONE_MINUS_DST_COLOR = 0x0307;
	public static final int GL_SRC_ALPHA_SATURATE = 0x0308;
	public static final int GL_FUNC_ADD = 0x8006;
	public static final int GL_BLEND_EQUATION = 0x8009;
	public static final int GL_BLEND_EQUATION_RGB = 0x8009;
	public static final int GL_BLEND_EQUATION_ALPHA = 0x883D;
	public static final int GL_FUNC_SUBTRACT = 0x800A;
	public static final int GL_FUNC_REVERSE_SUBTRACT = 0x800B;
	public static final int GL_BLEND_DST_RGB = 0x80C8;
	public static final int GL_BLEND_SRC_RGB = 0x80C9;
	public static final int GL_BLEND_DST_ALPHA = 0x80CA;
	public static final int GL_BLEND_SRC_ALPHA = 0x80CB;
	public static final int GL_ARRAY_BUFFER = 0x8892;
	public static final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;
	public static final int GL_ARRAY_BUFFER_BINDING = 0x8894;
	public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;
	public static final int GL_STATIC_DRAW = 0x88E4;
	public static final int GL_DYNAMIC_DRAW = 0x88E8;
	public static final int GL_BUFFER_SIZE = 0x8764;
	public static final int GL_BUFFER_USAGE = 0x8765;
	public static final int GL_FRONT = 0x0404;
	public static final int GL_BACK = 0x0405;
	public static final int GL_FRONT_AND_BACK = 0x0408;
	public static final int GL_TEXTURE_2D = 0x0DE1;
	public static final int GL_CULL_FACE = 0x0B44;
	public static final int GL_BLEND = 0x0BE2;
	public static final int GL_DITHER = 0x0BD0;
	public static final int GL_STENCIL_TEST = 0x0B90;
	public static final int GL_DEPTH_TEST = 0x0B71;
	public static final int GL_SCISSOR_TEST = 0x0C11;
	public static final int GL_POLYGON_OFFSET_FILL = 0x8037;
	public static final int GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;
	public static final int GL_SAMPLE_COVERAGE = 0x80A0;
	public static final int GL_NO_ERROR = 0;
	public static final int GL_INVALID_ENUM = 0x0500;
	public static final int GL_INVALID_VALUE = 0x0501;
	public static final int GL_INVALID_OPERATION = 0x0502;
	public static final int GL_OUT_OF_MEMORY = 0x0505;
	public static final int GL_CW = 0x0900;
	public static final int GL_CCW = 0x0901;
	public static final int GL_LINE_WIDTH = 0x0B21;
	public static final int GL_ALIASED_POINT_SIZE_RANGE = 0x846D;
	public static final int GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;
	public static final int GL_CULL_FACE_MODE = 0x0B45;
	public static final int GL_FRONT_FACE = 0x0B46;
	public static final int GL_DEPTH_RANGE = 0x0B70;
	public static final int GL_DEPTH_WRITEMASK = 0x0B72;
	public static final int GL_DEPTH_CLEAR_VALUE = 0x0B73;
	public static final int GL_DEPTH_FUNC = 0x0B74;
	public static final int GL_STENCIL_CLEAR_VALUE = 0x0B91;
	public static final int GL_STENCIL_FUNC = 0x0B92;
	public static final int GL_STENCIL_FAIL = 0x0B94;
	public static final int GL_STENCIL_PASS_DEPTH_FAIL = 0x0B95;
	public static final int GL_STENCIL_PASS_DEPTH_PASS = 0x0B96;
	public static final int GL_STENCIL_REF = 0x0B97;
	public static final int GL_STENCIL_VALUE_MASK = 0x0B93;
	public static final int GL_STENCIL_WRITEMASK = 0x0B98;
	public static final int GL_VIEWPORT = 0x0BA2;
	public static final int GL_SCISSOR_BOX = 0x0C10;
	public static final int GL_COLOR_CLEAR_VALUE = 0x0C22;
	public static final int GL_COLOR_WRITEMASK = 0x0C23;
	public static final int GL_UNPACK_ALIGNMENT = 0x0CF5;
	public static final int GL_PACK_ALIGNMENT = 0x0D05;
	public static final int GL_MAX_TEXTURE_SIZE = 0x0D33;
	public static final int GL_MAX_VIEWPORT_DIMS = 0x0D3A;
	public static final int GL_SUBPIXEL_BITS = 0x0D50;
	public static final int GL_RED_BITS = 0x0D52;
	public static final int GL_GREEN_BITS = 0x0D53;
	public static final int GL_BLUE_BITS = 0x0D54;
	public static final int GL_ALPHA_BITS = 0x0D55;
	public static final int GL_DEPTH_BITS = 0x0D56;
	public static final int GL_STENCIL_BITS = 0x0D57;
	public static final int GL_POLYGON_OFFSET_UNITS = 0x2A00;
	public static final int GL_POLYGON_OFFSET_FACTOR = 0x8038;
	public static final int GL_TEXTURE_BINDING_2D = 0x8069;
	public static final int GL_SAMPLE_BUFFERS = 0x80A8;
	public static final int GL_SAMPLES = 0x80A9;
	public static final int GL_SAMPLE_COVERAGE_VALUE = 0x80AA;
	public static final int GL_SAMPLE_COVERAGE_INVERT = 0x80AB;
	public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;
	public static final int GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;
	public static final int GL_DONT_CARE = 0x1100;
	public static final int GL_FASTEST = 0x1101;
	public static final int GL_NICEST = 0x1102;
	public static final int GL_GENERATE_MIPMAP_HINT = 0x8192;
	public static final int GL_BYTE = 0x1400;
	public static final int GL_UNSIGNED_BYTE = 0x1401;
	public static final int GL_SHORT = 0x1402;
	public static final int GL_FLOAT = 0x1406;
	public static final int GL_FIXED = 0x140C;
	public static final int GL_ALPHA = 0x1906;
	public static final int GL_RGB = 0x1907;
	public static final int GL_RGBA = 0x1908;
	public static final int GL_LUMINANCE = 0x1909;
	public static final int GL_LUMINANCE_ALPHA = 0x190A;
	public static final int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;
	public static final int GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;
	public static final int GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
	public static final int GL_NEVER = 0x0200;
	public static final int GL_LESS = 0x0201;
	public static final int GL_EQUAL = 0x0202;
	public static final int GL_LEQUAL = 0x0203;
	public static final int GL_GREATER = 0x0204;
	public static final int GL_NOTEQUAL = 0x0205;
	public static final int GL_GEQUAL = 0x0206;
	public static final int GL_ALWAYS = 0x0207;
	public static final int GL_KEEP = 0x1E00;
	public static final int GL_REPLACE = 0x1E01;
	public static final int GL_INCR = 0x1E02;
	public static final int GL_DECR = 0x1E03;
	public static final int GL_INVERT = 0x150A;
	public static final int GL_INCR_WRAP = 0x8507;
	public static final int GL_DECR_WRAP = 0x8508;
	public static final int GL_VENDOR = 0x1F00;
	public static final int GL_RENDERER = 0x1F01;
	public static final int GL_VERSION = 0x1F02;
	public static final int GL_EXTENSIONS = 0x1F03;
	public static final int GL_NEAREST = 0x2600;
	public static final int GL_LINEAR = 0x2601;
	public static final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
	public static final int GL_LINEAR_MIPMAP_NEAREST = 0x2701;
	public static final int GL_NEAREST_MIPMAP_LINEAR = 0x2702;
	public static final int GL_LINEAR_MIPMAP_LINEAR = 0x2703;
	public static final int GL_TEXTURE_MAG_FILTER = 0x2800;
	public static final int GL_TEXTURE_MIN_FILTER = 0x2801;
	public static final int GL_TEXTURE_WRAP_S = 0x2802;
	public static final int GL_TEXTURE_WRAP_T = 0x2803;
	public static final int GL_TEXTURE = 0x1702;
	public static final int GL_TEXTURE_CUBE_MAP = 0x8513;
	public static final int GL_TEXTURE_BINDING_CUBE_MAP = 0x8514;
	public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515;
	public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516;
	public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517;
	public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518;
	public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519;
	public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A;
	public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 0x851C;
	public static final int GL_TEXTURE0 = 0x84C0;
	public static final int GL_TEXTURE1 = 0x84C1;
	public static final int GL_TEXTURE2 = 0x84C2;
	public static final int GL_TEXTURE3 = 0x84C3;
	public static final int GL_TEXTURE4 = 0x84C4;
	public static final int GL_TEXTURE5 = 0x84C5;
	public static final int GL_TEXTURE6 = 0x84C6;
	public static final int GL_TEXTURE7 = 0x84C7;
	public static final int GL_TEXTURE8 = 0x84C8;
	public static final int GL_TEXTURE9 = 0x84C9;
	public static final int GL_TEXTURE10 = 0x84CA;
	public static final int GL_TEXTURE11 = 0x84CB;
	public static final int GL_TEXTURE12 = 0x84CC;
	public static final int GL_TEXTURE13 = 0x84CD;
	public static final int GL_TEXTURE14 = 0x84CE;
	public static final int GL_TEXTURE15 = 0x84CF;
	public static final int GL_TEXTURE16 = 0x84D0;
	public static final int GL_TEXTURE17 = 0x84D1;
	public static final int GL_TEXTURE18 = 0x84D2;
	public static final int GL_TEXTURE19 = 0x84D3;
	public static final int GL_TEXTURE20 = 0x84D4;
	public static final int GL_TEXTURE21 = 0x84D5;
	public static final int GL_TEXTURE22 = 0x84D6;
	public static final int GL_TEXTURE23 = 0x84D7;
	public static final int GL_TEXTURE24 = 0x84D8;
	public static final int GL_TEXTURE25 = 0x84D9;
	public static final int GL_TEXTURE26 = 0x84DA;
	public static final int GL_TEXTURE27 = 0x84DB;
	public static final int GL_TEXTURE28 = 0x84DC;
	public static final int GL_TEXTURE29 = 0x84DD;
	public static final int GL_TEXTURE30 = 0x84DE;
	public static final int GL_TEXTURE31 = 0x84DF;
	public static final int GL_ACTIVE_TEXTURE = 0x84E0;
	public static final int GL_REPEAT = 0x2901;
	public static final int GL_CLAMP_TO_EDGE = 0x812F;
	public static final int GL_MIRRORED_REPEAT = 0x8370;
	public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE = 0x8B9A;
	public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 0x8B9B;
	public static final int GL_FRAMEBUFFER = 0x8D40;
	public static final int GL_RENDERBUFFER = 0x8D41;
	public static final int GL_RGBA4 = 0x8056;
	public static final int GL_RGB5_A1 = 0x8057;
	public static final int GL_RGB565 = 0x8D62;
	public static final int GL_STENCIL_INDEX8 = 0x8D48;
	public static final int GL_RENDERBUFFER_WIDTH = 0x8D42;
	public static final int GL_RENDERBUFFER_HEIGHT = 0x8D43;
	public static final int GL_RENDERBUFFER_INTERNAL_FORMAT = 0x8D44;
	public static final int GL_RENDERBUFFER_RED_SIZE = 0x8D50;
	public static final int GL_RENDERBUFFER_GREEN_SIZE = 0x8D51;
	public static final int GL_RENDERBUFFER_BLUE_SIZE = 0x8D52;
	public static final int GL_RENDERBUFFER_ALPHA_SIZE = 0x8D53;
	public static final int GL_RENDERBUFFER_DEPTH_SIZE = 0x8D54;
	public static final int GL_RENDERBUFFER_STENCIL_SIZE = 0x8D55;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 0x8CD0;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 0x8CD1;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 0x8CD2;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 0x8CD3;
	public static final int GL_COLOR_ATTACHMENT0 = 0x8CE0;
	public static final int GL_DEPTH_ATTACHMENT = 0x8D00;
	public static final int GL_STENCIL_ATTACHMENT = 0x8D20;
	public static final int GL_NONE = 0;
	public static final int GL_FRAMEBUFFER_COMPLETE = 0x8CD5;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 0x8CD6;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 0x8CD7;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS = 0x8CD9;
	public static final int GL_FRAMEBUFFER_INCOMPLETE_FORMATS = 0x8CDA;
	public static final int GL_FRAMEBUFFER_UNSUPPORTED = 0x8CDD;
	public static final int GL_FRAMEBUFFER_BINDING = 0x8CA6;
	public static final int GL_RENDERBUFFER_BINDING = 0x8CA7;
	public static final int GL_MAX_RENDERBUFFER_SIZE = 0x84E8;
	public static final int GL_INVALID_FRAMEBUFFER_OPERATION = 0x0506;
	public static final int GL_DEPTH_COMPONENT24 = 0x81A6;
	public static final int GL_DEPTH_COMPONENT32 = 0x81A7;
	public static final int GL_WRITE_ONLY = 0x88B9;
	public static final int GL_BUFFER_ACCESS = 0x88BB;
	public static final int GL_BUFFER_MAPPED = 0x88BC;
	public static final int GL_BUFFER_MAP_POINTER = 0x88BD;
	public static final int GL_DEPTH_STENCIL = 0x84F9;
	public static final int GL_UNSIGNED_INT_24_8 = 0x84FA;
	public static final int GL_DEPTH24_STENCIL8 = 0x88F0;
	public static final int GL_RGB8 = 0x8051;
	public static final int GL_RGBA8 = 0x8058;
	public static final int GL_STENCIL_INDEX1 = 0x8D46;
	public static final int GL_STENCIL_INDEX4 = 0x8D47;
	public static final int GL_BGRA = 0x80E1;
	public static final int GL_RGBA16F = 0x881A;
	public static final int GL_RGB16F = 0x881B;
	public static final int GL_MAP_READ_BIT = 0x0001;
	public static final int GL_MAP_WRITE_BIT = 0x0002;
	public static final int GL_MAP_INVALIDATE_RANGE_BIT = 0x0004;
	public static final int GL_MAP_INVALIDATE_BUFFER_BIT = 0x0008;
	public static final int GL_MAP_FLUSH_EXPLICIT_BIT = 0x0010;
	public static final int GL_MAP_UNSYNCHRONIZED_BIT = 0x0020;
	public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV_EXT = 0x8365;
	public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV_EXT = 0x8366;
	public static final int GL_GUILTY_CONTEXT_RESET = 0x8253;
	public static final int GL_INNOCENT_CONTEXT_RESET = 0x8254;
	public static final int GL_UNKNOWN_CONTEXT_RESET = 0x8255;
	public static final int GL_RESET_NOTIFICATION_STRATEGY = 0x8256;
	public static final int GL_LOSE_CONTEXT_ON_RESET = 0x8252;
	public static final int GL_NO_RESET_NOTIFICATION = 0x8261;
	public static final int GL_SRGB = 0x8C40;
	public static final int GL_SRGB_ALPHA = 0x8C42;
	public static final int GL_SRGB8_ALPHA8 = 0x8C43;
	public static final int GL_COMPRESSED_RGB_S3TC_DXT1_EXT = 0x83F0;
	public static final int GL_COMPRESSED_RGBA_S3TC_DXT1_EXT = 0x83F1;
	public static final int GL_TEXTURE_MAX_ANISOTROPY_EXT = 0x84FE;
	public static final int GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT = 0x84FF;
	public static final int GL_TEXTURE_IMMUTABLE_FORMAT = 0x912F;
	public static final int GL_ALPHA8 = 0x803C;
	public static final int GL_RGBA32F = 0x8814;
	public static final int GL_RGB32F = 0x8815;
	public static final int GL_ALPHA32F = 0x8816;
	public static final int GL_ALPHA16F = 0x881C;
	public static final int GL_RGB10_A2 = 0x8059;
	public static final int GL_RGB10 = 0x8052;
	public static final int GL_BGRA8 = 0x93A1;
	public static final int GL_COMPRESSED_RGBA_S3TC_DXT3_EXT = 0x83F2;
	public static final int GL_COMPRESSED_RGBA_S3TC_DXT5_EXT = 0x83F3;
	public static final int GL_TEXTURE_2D_ARRAY = 0x8C1A;
	public static final int GL_SAMPLER_2D_ARRAY = 0x8DC1;
	public static final int GL_TEXTURE_BINDING_2D_ARRAY = 0x8C1D;
	public static final int GL_MAX_ARRAY_TEXTURE_LAYERS = 0x88FF;
	public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 0x8CD4;
	public static final int GL_R11F_G11F_B10F = 0x8C3A;
	public static final int GL_UNSIGNED_INT_10F_11F_11F_REV = 0x8C3B;
	public static final int GL_RGBA_SIGNED_COMPONENTS = 0x8C3C;
	public static final int GL_MATRIX_MODE = 0x0BA0;
	public static final int GL_MODELVIEW = 0x1700;
	public static final int GL_PROJECTION = 0x1701;
	public static final int GL_MODELVIEW_MATRIX = 0x0BA6;
	public static final int GL_PROJECTION_MATRIX = 0x0BA7;
	public static final int GL_TEXTURE_MATRIX = 0x0BA8;


	//


	public static native void glEnable(int cap);

	public static native void glBlendFunc(int sfactor, int dfactor);

	public static native void glClear(int mask);

	public static native void glFlush();

	public static native void glLineWidth(float width);

	public static native void glLoadIdentity();

	public static native void glMatrixMode(int mode) ;

	public static native void glMultMatrixf(float[] m);

	public static native void glTranslatef(float x, float y, float z);

	public static native void glPointSize(float size);

	public static native void glBegin(int mode);

	public static native void glEnd();

	public static native void glVertex2f(float x, float y);

	public static native void glPushMatrix();

	public static native void glPopMatrix();

	public static native void glVertex3f(float x, float y, float z);

	public static native void glColor3f(float red, float green, float blue);

	public static native void glColor4f(float red, float green, float blue, float alpha);

	public static native void glViewport(int x, int y, int width, int height);

	public static native void glOrthof(float left, float right, float bottom, float top, float zNear, float zFar);

	public static void gluOrtho2D(float left, float right, float bottom, float top)
	{
		glOrthof(left, right, bottom, top, -1, 1);
	}

	public static native boolean window(int width, int height, int bpp);

	public static native boolean key();

	public static void _load()
	{
//		if(System.getProperty("java.vm.name").toLowerCase().contains("hotspot"))
			System.load("/mnt/zfs/levault/dev/p/jaklintop/myavn/mytest/jbox2d/libjbox2d.so");
	}
}

