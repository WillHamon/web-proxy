package me.willhamon.webproxy;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			new WebProxy();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("ERROR Proxy Failed to load!");
			return;
		}
		System.out.println("Proxy loaded successfully!");
	}
}
