package name.ltp.jviolet.system;

import name.ltp.jviolet.system.utility.FileUtility;

public class Configuration
{
	public FileUtility m_fileUtility;
	public VideoMode Screen;
	public int FrameDelay;
	public boolean ShowFps;
	public boolean AutoReload;
	public int SoundVolume;
	public int MusicVolume;
	public int MonstersAtStart;
	public int AimColorA, AimColorB;
	public boolean AutoWeaponPickup;
	public boolean FriendlyFire;
	InputHandler.Binding[] PlayerInputBinding
		= new InputHandler.Binding[InputHandler.GameInputEvents.GameInputEventsCount.ordinal()];

	Configuration(FileUtility fileUtility)
	{
		m_fileUtility = fileUtility;

		Screen.Width = 800;
		Screen.Height = 600;
		Screen.Color = 16;
		Screen.Full = false;

		FrameDelay = 10;
		ShowFps = false;
		AutoReload = true;
		SoundVolume = 4;
		MusicVolume = 4;
		MonstersAtStart = 12;
		AimColorA = 0x000000;
		AimColorB = 0xFFFFFF;
		AutoWeaponPickup = true;
		FriendlyFire = false;

		PlayerInputBinding[InputHandler.GameInputEvents.Teleport.ordinal()].Value = SDLK_q; // FIXME:
		PlayerInputBinding[InputHandler.GameInputEvents.MoveLeft.ordinal()].Value = SDLK_a;
		PlayerInputBinding[InputHandler.GameInputEvents.MoveUp.ordinal()].Value = SDLK_w;
		PlayerInputBinding[InputHandler.GameInputEvents.MoveRight.ordinal()].Value = SDLK_d;
		PlayerInputBinding[InputHandler.GameInputEvents.MoveDown.ordinal()].Value = SDLK_s;
		PlayerInputBinding[InputHandler.GameInputEvents.Restart.ordinal()].Value = SDLK_RETURN;
		PlayerInputBinding[InputHandler.GameInputEvents.Menu.ordinal()].Value = SDLK_ESCAPE;
		PlayerInputBinding[InputHandler.GameInputEvents.MenuClickA.ordinal()].Type = InputHandler.BindingType.Mouse;
		PlayerInputBinding[InputHandler.GameInputEvents.MenuClickA.ordinal()].Value = SDL_BUTTON_LEFT;
		PlayerInputBinding[InputHandler.GameInputEvents.MenuClickB.ordinal()].Type = InputHandler.BindingType.Mouse;
		PlayerInputBinding[InputHandler.GameInputEvents.MenuClickB.ordinal()].Value = SDL_BUTTON_RIGHT;
		PlayerInputBinding[InputHandler.GameInputEvents.Exit.ordinal()].Value = SDLK_F12;
		PlayerInputBinding[InputHandler.GameInputEvents.ToggleLight.ordinal()].Value = SDLK_f;
		PlayerInputBinding[InputHandler.GameInputEvents.ToggleLaser.ordinal()].Value = SDLK_g;
		PlayerInputBinding[InputHandler.GameInputEvents.Pause.ordinal()].Value = SDLK_p;
		PlayerInputBinding[InputHandler.GameInputEvents.ShowChar.ordinal()].Value = SDLK_c;
		PlayerInputBinding[InputHandler.GameInputEvents.Help.ordinal()].Value = SDLK_F1;
		PlayerInputBinding[InputHandler.GameInputEvents.Pickup.ordinal()].Value = SDLK_e;
		PlayerInputBinding[InputHandler.GameInputEvents.ThrowGrenade.ordinal()].Value = SDLK_SPACE;
		PlayerInputBinding[InputHandler.GameInputEvents.Fire.ordinal()].Value = SDL_BUTTON_LEFT;
		PlayerInputBinding[InputHandler.GameInputEvents.Fire.ordinal()].Type = InputHandler.BindingType.Mouse;
		PlayerInputBinding[InputHandler.GameInputEvents.Reload.ordinal()].Value = SDL_BUTTON_RIGHT;
		PlayerInputBinding[InputHandler.GameInputEvents.Reload.ordinal()].Type = InputHandler.BindingType.Mouse;
	}

	public void read()
	{
		try
		{
			ConfigFile cFile
				= new ConfigFile(m_fileUtility.getFullPath(FileUtility.PathType.user, "config"));

//		cFile.readInto(Screen.Width, "screenWidth");
//		cFile.readInto(Screen.Height, "screenHeight");
//		cFile.readInto(Screen.Color, "screenColor");
//		cFile.readInto(Screen.Full, "fullScreen");
//		cFile.readInto(FrameDelay, "frameDelay");
//		cFile.readInto(ShowFps, "showFps");
//		cFile.readInto(AutoReload, "autoReload");
//		cFile.readInto(SoundVolume, "soundVolume");
//		cFile.readInto(MusicVolume, "musicVolume");
//		cFile.readInto(AimColorA, "aimColorA");
//		cFile.readInto(AimColorB, "aimColorB");
//		cFile.readInto(AutoWeaponPickup, "autoWeaponPickup");
//		cFile.readInto(FriendlyFire, "friendlyFire");
//
//		for (int i = 0; i < InputHandler::GameInputEventsCount; i++) {
//			ReadPlayerBinding(&cFile, &PlayerInputBinding[i],
//				InputHandler::getEventIdentifier(i));
//		}

		}
		catch(Exception e)
		{
			System.err.println("Can't open config file.");
		}
	}

	public void ReadPlayerBinding(ConfigFile cFile,
		InputHandler.Binding binding, String eventIdentifier)
	{
		int type;
		String keyType = "playerInputBinding_" + eventIdentifier + "Type";
		String keyValue = "playerInputBinding_" + eventIdentifier + "Value";

//	if (cFile.keyExists(keyType)) {
//		cFile->readInto(type, keyType);
//		binding->Type = (InputHandler::BindingType) type;
//		cFile->readInto(binding->Value, keyValue);
//	}
	}
}

