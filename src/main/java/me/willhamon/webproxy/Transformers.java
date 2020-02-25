package me.willhamon.webproxy;

public class Transformers
{
	public static byte[] html(String uri, byte[] bytes)
	{
		String encodedUri = Utils.encodeURL(uri) + "/";

		String string = new String(bytes);

		//gotta do some fixing here
		string = string.replaceAll(encodedUri, "");
		string = string.replaceAll("src=\"", "src=\"" + encodedUri);
		string = string.replaceAll("href=\"", "href=\"" + encodedUri);

		return string.getBytes();
	}
}
