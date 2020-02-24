package me.willhamon.webproxy;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Utils
{
	public static String encodeURI(String uri)
	{
		String encoded;

		try
		{
			encoded = URLEncoder.encode(uri, "UTF-8")
					.replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!")
					.replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(")
					.replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");

		} catch (UnsupportedEncodingException e) { encoded = uri; }

		return encoded;
	}

	public static String decodeURI(String uri)
	{
		String decoded;

		try
		{
			decoded = URLDecoder.decode(uri, "UTF-8");

		} catch (UnsupportedEncodingException e) { decoded = uri; }

		return decoded;
	}
}
