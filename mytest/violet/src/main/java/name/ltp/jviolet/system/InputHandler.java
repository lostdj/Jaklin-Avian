package name.ltp.jviolet.system;

public class InputHandler
{
	public static final int MAX_CHARACTERS = 20;

//	public static String[] m_eventNames = new String[GameInputEvents.GameInputEventsCount.ordinal()];
//	public static String[] m_eventIdentifiers = new String[GameInputEvents.GameInputEventsCount.ordinal()];

	public int mouseX, mouseY;
	public boolean[] m_event = new boolean[GameInputEvents.GameInputEventsCount.ordinal()];
	public InputMode m_mode;
	boolean m_textValidated;
	String m_textContent;
	int m_curTextPos;
	Binding[] m_binding;

	public static enum BindingType
	{
		Keyboard, Mouse
	}

	;

	public static class Binding
	{
		public int Value;
		public BindingType Type;

		public Binding()
		{
			Type = BindingType.Keyboard;
		}
	}

	public static enum GameInputEvents
	{
		Restart,
		Exit,
		Menu,
		MenuClickA,
		MenuClickB,
		ToggleLight,
		ToggleLaser,
		ShowChar,
		Pause,
		MoveLeft,
		MoveRight,
		MoveUp,
		MoveDown,
		Help,
		Pickup,
		ThrowGrenade,
		Fire,
		Reload,
		Teleport,
		GameInputEventsCount
	}

	;

	public static enum InputMode
	{
		Direct, Text, TextMandatory
	}

	;

	public InputHandler(Binding[] binding)
	{
		for(int i = 0; i < GameInputEvents.GameInputEventsCount.ordinal(); i++)
		{
			m_event[i] = false;
		}

		m_binding = binding;

		mouseX = mouseY = 0;

		m_mode = InputMode.Direct;
		m_textValidated = false;
		m_textContent = "";
		m_curTextPos = 0;
	}

	public void setInputMode(InputMode mode)
	{
		if(m_mode == mode)
			return;

		if(mode == InputMode.Text || mode == InputMode.TextMandatory)
		{
			m_textValidated = false;
			m_textContent = "";
			m_curTextPos = 0;
		}

		m_mode = mode;
	}

	public void setInputModeText(boolean mandatory, String text)
	{
		if((!mandatory && m_mode == InputMode.Text)
			|| (mandatory && m_mode == InputMode.TextMandatory))
			return;

		if(mandatory)
			setInputMode(InputMode.TextMandatory);
		else
			setInputMode(InputMode.Text);

		m_textContent = text;
		m_curTextPos = m_textContent.length();
	}

	public boolean getPressInput(GameInputEvents evnt)
	{
		if(m_event[evnt.ordinal()])
		{
			m_event[evnt.ordinal()] = false;
			return true;
		}
		else
		{
			return false;
		}
	}

	public void resetMouseButtons()
	{
		processEvent(BindingType.Mouse, false, SDL_BUTTON_LEFT); // FIXME:
		processEvent(BindingType.Mouse, false, SDL_BUTTON_RIGHT);
		processEvent(BindingType.Mouse, false, SDL_BUTTON_MIDDLE);
	}

	public void processEvent(BindingType type, boolean down, int value)
	{
		for(int i = 0; i < GameInputEvents.GameInputEventsCount.ordinal(); i++)
		{
			if(m_binding[i].Type == type && m_binding[i].Value == value)
				m_event[i] = down;
		}
	}

	public void processTextInput(SDL_Event sdlEvent)
	{ // FIXME:
		switch(sdlEvent.key.keysym.sym)
		{
			case SDLK_ESCAPE:
				setInputMode(Direct);
				break;
			case SDLK_BACKSPACE:
				if(m_curTextPos > 0)
					m_textContent =
						m_textContent.substring(0, --m_curTextPos)
							+ m_textContent.substring(m_curTextPos);
//				m_textContent.erase(--m_curTextPos, 1);
				break;
			case SDLK_RETURN:
				if((m_mode == InputMode.TextMandatory && m_textContent.length() >= 1) || m_mode
					== InputMode.TextMandatory)
				{
					m_textValidated = true;
				}
				break;
			default:
				if(sdlEvent.key.keysym.unicode < 127 && m_curTextPos < MAX_CHARACTERS)
				{
					char c = sdlEvent.key.keysym.unicode;
					if(' ' <= c && c <= '~')
						m_textContent =
							m_textContent.substring(0, m_curTextPos++)
								+ c
								+ m_textContent.substring(m_curTextPos);
//					m_textContent.insert(m_curTextPos++, 1, c);
				}
				break;
		}
	}

	public void process()
	{
		SDL_Event sdlEvent;

		while(SDL_PollEvent(sdlEvent))
		{
			switch(sdlEvent.type)
			{
				case SDL_KEYDOWN:
					if(m_mode == InputMode.Direct)
						processEvent(BindingType.Keyboard, true, sdlEvent.key.keysym.sym);

					if(m_mode == InputMode.Text || m_mode == InputMode.TextMandatory)
						processTextInput(sdlEvent);

					break;
				case SDL_KEYUP:
					processEvent(BindingType.Keyboard, false, sdlEvent.key.keysym.sym);

					break;
				case SDL_MOUSEBUTTONDOWN:
					processEvent(BindingType.Mouse, true, sdlEvent.button.button);
					break;
				case SDL_MOUSEBUTTONUP:
					processEvent(BindingType.Mouse, false, sdlEvent.button.button);
					break;
				case SDL_MOUSEMOTION:
					mouseX = sdlEvent.motion.x;
					mouseY = sdlEvent.motion.y;
					break;
				case SDL_QUIT:
					m_event[GameInputEvents.Exit.ordinal()] = true;
					break;
			}
		}
	}

	public String getEventName(int eventNumber) throws Exception
	{
		if(eventNumber > GameInputEvents.GameInputEventsCount.ordinal())
			throw new Exception();
		else
			return GameInputEvents.values()[eventNumber].name();
	}

	public String getEventIdentifier(int eventNumber) throws Exception
	{
		if(eventNumber > GameInputEvents.GameInputEventsCount.ordinal())
			throw new Exception();
		else
			return GameInputEvents.values()[eventNumber].name();
	}

	public int getEventNumber(String eventIdentifier)
	{
		return GameInputEvents.valueOf(eventIdentifier).ordinal();
	}

	String getKeyName(Binding bind)
	{
		if(bind.Type == BindingType.Keyboard)
		{
			String keyName = SDL_GetKeyName(SDLKey(bind.Value));
			return keyName;
		}
		else
		{
			if(bind.Type == BindingType.Mouse)
			{
				switch(bind.Value)
				{
					default:
					case SDL_BUTTON_LEFT:
						return "left mouse";
					case SDL_BUTTON_RIGHT:
						return "right mouse";
					case SDL_BUTTON_MIDDLE:
						return "middle mouse";
				}
			}
		}

		return null;
	}
}

