package com.mamroush.fix.virus.shortcut;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;

public class ShortcutVirusFileFilter implements FileFilter
{

    protected String description;

    protected ArrayList<String> exts = new ArrayList<String>();

    public ShortcutVirusFileFilter()
    {
	this.addType("lnk");
    }

    @Override
    public boolean accept(File f)
    {
	if (f.isDirectory())
	{
	    return false;

	} else if (f.isFile())
	{
	    Iterator<String> it = exts.iterator();
	    while (it.hasNext())
	    {
		if (f.getName().endsWith((String) it.next()))
		    return true;
	    }
	}

	// A file that didn't match, or a weirdo (e.g. UNIX device file?).
	return false;
    }

    public void addType(String s)
    {
	exts.add(s);
    }

    /** Return the printable description of this filter. */
    public String getDescription()
    {
	return description;
    }

    /** Set the printable description of this filter. */
    public void setDescription(String s)
    {
	description = s;
    }
}
