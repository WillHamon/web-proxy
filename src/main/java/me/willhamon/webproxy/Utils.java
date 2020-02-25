package me.willhamon.webproxy;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Utils
{
	public static String encodeURL(String url)
	{
		String encoded;

		try
		{
			encoded = URLEncoder.encode(url, "UTF-8")
					.replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!")
					.replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(")
					.replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");

		} catch (UnsupportedEncodingException e) { encoded = url; }

		return encoded;
	}

	public static String decodeURL(String url)
	{
		String decoded;

		try
		{
			decoded = URLDecoder.decode(url, "UTF-8");

		} catch (UnsupportedEncodingException e) { decoded = url; }

		return decoded;
	}
}
