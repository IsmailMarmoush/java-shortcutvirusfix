package com.mamroush.fix.virus.shortcut;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunMe
{
    public static void main(String args[])
    {
	File path = new File("G:\\");
	RunMe r = new RunMe();
	System.out.println(r.pathInformation(path));
	// r.start(path);
    }

    public String pathInformation(File path)
    {
	File[] files = path.listFiles();
	Arrays.sort(files);
	int i = 0;
	for (File file : files)
	{
	    if (file.isDirectory())
	    {
		System.out.println(file.toString());
		i++;
	    }
	}
	StringBuilder str = new StringBuilder();
	str.append("Number of Directories in Path: " + i);
	return str.toString();
    }

    public RunMe()
    {

    }

    public void start(File path)
    {
	List<File> folders = getFolders(path);
	List<File> tempFolders = this.createTempFolders(folders);
	for (int i = 0; i < folders.size(); i++)
	{
	    this.moveFiles(folders.get(i), tempFolders.get(i));
	}
	System.out.println("Files were Moved to Temp Folders");

	deleteFiles(this.getShortcutFiles(path));
	deleteFolders(folders);

	for (File file : tempFolders)
	{
	    file.renameTo(removeEnd(file, "-tmp"));
	    System.out.println("Temporary Folder was renamed back to its original name");
	}
    }

    public List<File> createTempFolders(List<File> folders)
    {
	List<File> temp = new ArrayList<File>(folders.size());
	for (int i = 0; i < folders.size(); i++)
	{
	    new File(folders.get(i) + "-tmp").mkdir();
	    String destFolder = folders.get(i).toString() + "-tmp";
	    temp.add(new File(destFolder));
	}
	return temp;
    }

    public boolean deleteFiles(List<File> files)
    {
	boolean result = true;
	for (File fod : files)
	{
	    if (fod.isFile())
	    {
		fod.delete();
	    }
	}
	return result;
    }

    public boolean deleteFolders(List<File> folders)
    {
	boolean result = true;
	for (File fod : folders)
	{
	    if (fod.isDirectory() && fod.list().length == 0)
	    {
		fod.delete();
	    }
	}
	return result;
    }

    public List<File> getFolders(File path)
    {
	List<File> folders = Arrays.asList(path.listFiles(new FileFilter()
	{
	    @Override
	    public boolean accept(File pathname)
	    {
		if (pathname.getName().equals("System Volume Information"))
		{
		    return false;
		}
		return pathname.isDirectory();
	    }
	}));
	return folders;
    }

    public List<File> getShortcutFiles(File path)
    {
	List<File> links =
	    Arrays.asList(path.listFiles(new ShortcutVirusFileFilter()));
	return links;
    }

    public void moveFiles(File source, File destination)
    {
	File[] filesInside = source.listFiles();
	for (int j = 0; j < filesInside.length; j++)
	{
	    File destFile =
		new File(destination.toString() + "\\"
			+ filesInside[j].getName());
	    System.out.println(destFile);
	    boolean result = filesInside[j].renameTo(destFile);
	    if (result)
	    {
		System.out.println("File: " + filesInside[j] + " was Moved to "
			+ destFile);
	    } else
	    {
		System.out.println("File: " + filesInside[j]
			+ " couldn't be Moved to " + destFile);
	    }
	}
    }

    public File removeEnd(File filePath, String end)
    {
	String str = filePath.toString();
	return new File(removeEnd(str, end));
    }

    public String removeEnd(String str, String end)
    {
	while (str.endsWith(end))
	{
	    int endLength = end.length();
	    str = str.substring(0, str.length() - endLength);
	}
	return str;
    }
}
