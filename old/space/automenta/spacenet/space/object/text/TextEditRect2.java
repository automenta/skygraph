package automenta.spacenet.space.object.text;

import automenta.spacenet.space.control.Pointer;
import automenta.spacenet.space.control.keyboard.Keyboard;
import automenta.spacenet.space.control.keyboard.Keyboard.IfKeyboardChanges;
import automenta.spacenet.space.geom2.Rect;
import automenta.spacenet.space.object.widget.Focusable;
import automenta.spacenet.space.object.widget.panel.Panel;
import automenta.spacenet.space.object.widget.text.TextContainer;
import automenta.spacenet.space.object.widget.text.TextEditCursor;
import automenta.spacenet.space.Color;
import automenta.spacenet.var.number.IntegerVar;
import automenta.spacenet.var.string.StringVar;

/** experimental, not fully functioning */
public class TextEditRect2 extends Panel implements TextContainer, Focusable {
	private Keyboard keyboard;
	IntegerVar selectStart=new IntegerVar(0), selectEnd=new IntegerVar(0);
	TextRect2 textMatrix;
	private StringVar text;	

	private boolean editable = true;

	private IfKeyboardChanges keyChange;

	private Rect cursor;


	public TextEditRect2(StringVar text) {
		super();
		this.text = text;
		tangible(true);		
	}
	

	public synchronized void setEditable(boolean b) {
		if (this.editable == b)
			return;
		
		this.editable = b;
		
		if (b) {
			if (keyChange == null) {
				keyChange = add(new IfKeyboardChanges() {
					private char nextChar;
		
					@Override public void onKey(char character, int code, boolean pressed) {
						if (pressed) {
							nextChar = character;
							if (code == Keyboard.KEY_BACK) {
								backspace();
							}
							else if (code == Keyboard.KEY_DELETE) {
								delete();
							}
							else if (code == Keyboard.KEY_LEFT) {		left();			}
							else if (code == Keyboard.KEY_RIGHT) {		right();		}
							else if (code == Keyboard.KEY_UP) {			up();			}
							else if (code == Keyboard.KEY_DOWN) {		down();			}
							else if ((code == Keyboard.KEY_RETURN) || (code == Keyboard.KEY_NUMPADENTER)) {
								enter();
							}
							else if ((code == Keyboard.KEY_LSHIFT) || (code == Keyboard.KEY_RSHIFT)) {
								//...
							}
							else if (Keyboard.isNumPad(code)) {
								
							}
							else if ((code == Keyboard.KEY_LCONTROL) || (code == Keyboard.KEY_RCONTROL)) {
								
							}
							else if (code == Keyboard.KEY_ESCAPE) {
								
							}
							else {
								if (Character.isDefined(nextChar))
									insert(nextChar);
								//logger.info(" -> " + getText());
							}
							nextChar = 0;
						}
						else {
							//nextChar = character;
						}
					}			
				});
			}

			if (cursor == null) {
				cursor = textMatrix.getContent().add(new TextEditCursor(this, selectStart, selectEnd).color(textEditCursorColor));
			}
			
			keyEnd();
		}
		else {
			if (keyChange!=null) {
				remove(keyChange);
				keyChange = null;
			}
			
			if (cursor!=null) {
				remove(cursor);
				cursor = null;
			}
		}
		
	}

	protected void down() {
		// TODO Auto-generated method stub
		
	}

	protected void up() {
		// TODO Auto-generated method stub
		
	}

	private synchronized void home() {
		setCursor(0);
	}

	private synchronized void keyEnd() {
		setCursor(getTextLength());
	}
	

	@Override	public void start() {
		super.start();
		
		textMatrix = add(new TextRect2(getText()));

		requestFocus();
		
//		TextEditRectDecorator dec = getThe(TextEditRectDecorator.class);
//		if (dec!=null) {
//			dec.decorateTextEditRect(this);
//		}
	}


	private Color getTextColor() {
		return textEditColor ;
	}

	
	public StringVar getText() {
		return text;
	}


	protected void insert(char character) {
		getText().insert(getCursor(), character);
		
		moveCursor(1);
	}

	public int getTextLength() {
		return getText().s().length();
	}
	

	protected synchronized void delete() {
		if (getText().length() > 0) {
			if (getCursor() < getTextLength()) {
				StringBuffer sb = new StringBuffer(getText().s());
				sb.deleteCharAt(getCursor());

				moveCursor(0);
				getText().set(sb.toString());				
			}
		}
		
	}

	protected void backspace() {
		if (getText().length() > 0) {
			if (getCursor() > 0) {			
				StringBuffer sb = new StringBuffer(getText().s());
				sb.deleteCharAt(getCursor()-1);
	
				moveCursor(-1);
				getText().set(sb.toString());
			}
		}
		
	}

	private synchronized void moveCursor(int dx) {
		setCursor(getCursor() + dx);
	}
	private synchronized void setCursor(int c) {
		if (c < 0) c = 0;
		if (c > getText().length()) c = getTextLength();

		selectStart.set(c);
		selectEnd.set(c);		
	}

	protected synchronized void left() {
		moveCursor(-1);
	}
	protected synchronized void right() {
		moveCursor(1);		
	}
	
	protected synchronized void enter() {
		insert('\n');
	}

	public int getCursor() {
		int s = Math.min(selectStart.i(), getTextLength());
		return s;
	}


	@Override
	public void stop() {
		
	}

	
//	abstract public BooleanVar getIsEditable();
//
//	abstract public DoubleVar getFontScale();
	
//	@Override protected boolean hasBack() {
//		return false;
//	}

	@Override public void onFocusChange(boolean focused) {
		setEditable(focused);
	}

	@Override
	public void pressStopped(Pointer c) {
		super.pressStopped(c);
		
		requestFocus();
	}

	
	public int getNumChars() {
		return getText().length();
	}

	@Override public Rect getCharacterRect(int start) {
		return textMatrix.getCharacterRect(start);
	}
	
}
