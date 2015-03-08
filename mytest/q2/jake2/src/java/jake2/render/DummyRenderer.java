/*
 * DummyRenderer.java
 * Copyright (C) 2003
 *
 * $Id: DummyRenderer.java,v 1.2 2005/02/07 22:37:55 cawe Exp $
 */

package jake2.render;

import jake2.Defines;
import jake2.client.refdef_t;
import jake2.client.refexport_t;
import jake2.qcommon.xcommand_t;
import jake2.sys.KBD;

/**
 * DummyRenderer
 *
 * @author cwei
 */
public class DummyRenderer implements refexport_t, Ref {

	static {
		Renderer.register(new DummyRenderer());
	};

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#Init(int, int)
	 */
	public boolean Init(int vid_xpos, int vid_ypos) {
		return true;
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#Shutdown()
	 */
	public void Shutdown() {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#BeginRegistration(java.lang.String)
	 */
	public void BeginRegistration(String map) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#RegisterModel(java.lang.String)
	 */
	public model_t RegisterModel(String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#RegisterSkin(java.lang.String)
	 */
	public image_t RegisterSkin(String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#RegisterPic(java.lang.String)
	 */
	public image_t RegisterPic(String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#SetSky(java.lang.String, float, float[])
	 */
	public void SetSky(String name, float rotate, float[] axis) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#EndRegistration()
	 */
	public void EndRegistration() {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#RenderFrame(jake2.client.refdef_t)
	 */
	public void RenderFrame(refdef_t fd) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#DrawGetPicSize(Dimension, java.lang.String)
	 */
	public void DrawGetPicSize(huyava.awt.Dimension dim, String name) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#DrawPic(int, int, java.lang.String)
	 */
	public void DrawPic(int x, int y, String name) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#DrawStretchPic(int, int, int, int, java.lang.String)
	 */
	public void DrawStretchPic(int x, int y, int w, int h, String name) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#DrawChar(int, int, int)
	 */
	public void DrawChar(int x, int y, int num) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#DrawTileClear(int, int, int, int, java.lang.String)
	 */
	public void DrawTileClear(int x, int y, int w, int h, String name) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#DrawFill(int, int, int, int, int)
	 */
	public void DrawFill(int x, int y, int w, int h, int c) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#DrawFadeScreen()
	 */
	public void DrawFadeScreen() {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#DrawStretchRaw(int, int, int, int, int, int, byte[])
	 */
	public void DrawStretchRaw(int x, int y, int w, int h, int cols, int rows, byte[] data) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#CinematicSetPalette(byte[])
	 */
	public void CinematicSetPalette(byte[] palette) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#BeginFrame(float)
	 */
	public void BeginFrame(float camera_separation) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#EndFrame()
	 */
	public void EndFrame() {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#AppActivate(boolean)
	 */
	public void AppActivate(boolean activate) {
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#updateScreen(jake2.qcommon.xcommand_t)
	 */
	public void updateScreen(xcommand_t callback) {
	    callback.execute();
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#apiVersion()
	 */
	public int apiVersion() {
		return Defines.API_VERSION;
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#getModeList()
	 */
	public huyava.awt.DisplayMode[] getModeList() {
		return null;
	}

	/* (non-Javadoc)
	 * @see jake2.client.refexport_t#getKeyboardHandler()
	 */
	public KBD getKeyboardHandler() {
		return new KBD()
		{
			@Override
			public void Init()
			{

			}
			@Override
			public void Update()
			{

			}
			@Override
			public void Close()
			{

			}
			@Override
			public void Do_Key_Event(int key, boolean down)
			{

			}
			@Override
			public void installGrabs()
			{

			}
			@Override
			public void uninstallGrabs()
			{

			}
		};
	}

	@Override
	public refexport_t GetRefAPI()
	{
		return this;
	}
	@Override
	public String getName()
	{
		return "Dummy";
	}
}
