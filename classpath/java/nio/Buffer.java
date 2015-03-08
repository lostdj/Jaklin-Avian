/* Copyright (c) 2008-2014, Avian Contributors

   Permission to use, copy, modify, and/or distribute this software
   for any purpose with or without fee is hereby granted, provided
   that the above copyright notice and this permission notice appear
   in all copies.

   There is NO WARRANTY for this software.  See license.txt for
   details. */

package java.nio;

public abstract class Buffer {
	//mymod: added
	//+stolen ojdk
	private int mark = -1;
	//-stolen ojdk
  protected int capacity;
  protected int position;
  protected int limit;

  public final int limit() {
    return limit;
  }

  public final int remaining() {
    return limit-position;
  }

  public final int position() {
    return position;
  }

  public final int capacity() {
    return capacity;
  }

  public final Buffer limit(int newLimit) {
		//mymod: added
		//+stolen ojdk
		if ((newLimit > capacity) || (newLimit < 0))
			throw new IllegalArgumentException();
		//-stolen ojdk
    limit = newLimit;
		//mymod: added
		//+stolen ojdk
		if (position > limit) position = limit;
		if (mark > limit) mark = -1;
		//-stolen ojdk
    return this;
  }

  public final Buffer position(int newPosition) {
		//mymod: added
		//+stolen ojdk
		if ((newPosition > limit) || (newPosition < 0))
			throw new IllegalArgumentException();
		//-stolen ojdk
    position = newPosition;
		//mymod: added
		//+stolen ojdk
		if (mark > position) mark = -1;
		//-stolen ojdk
    return this;
  }

  public final boolean hasRemaining() {
    return remaining() > 0;
  }

  public final Buffer clear() {
    position = 0;
    limit = capacity;
		//mymod: added
		//+stolen ojdk
		mark = -1;
		//-stolen ojdk
    return this;
  }

  public final Buffer flip() {
    limit = position;
    position = 0;
		//mymod: added
		//+stolen ojdk
		mark = -1;
		//-stolen ojdk
    return this;
  }

  public final Buffer rewind() {
    position = 0;
		//mymod: added
		//+stolen ojdk
		mark = -1;
		//-stolen ojdk
    return this;
  }

	//mymod: added
	//+stolen ojdk
	public final Buffer mark() {
		mark = position;
		return this;
	}

	public final Buffer reset() {
		int m = mark;
		if (m < 0)
			throw new InvalidMarkException();
		position = m;
		return this;
	}
	//-stolen ojdk
}
