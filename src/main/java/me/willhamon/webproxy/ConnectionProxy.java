package me.willhamon.webproxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ConnectionProxy
{
	private byte[] bytes;
	private Map<String, List<String>> headers;

	public ConnectionProxy(String url) throws IOException
	{
		// open url connection
		HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
		connection.setConnectTimeout(5000);
		connection.connect();

		// wait for connection?
		// need to move this out of constructor
		if(connection.getResponseCode() != 200)
		{
			bytes = "Error Connecting".getBytes();
			headers = null;
			return;
		}

		// get headers
		headers = connection.getHeaderFields();

		// get input stream from url
		InputStream in = connection.getInputStream();

		// copy bytes from input stream into byte array
		bytes = new byte[in.available()];
		in.read(bytes, 0, in.available());

		// close input stream
		in.close();

		// if page is html, open in jsoup
		String html = new String(bytes);
		if(html.contains("<html>"))
		{
			// parse html
			Document document = Jsoup.parse(html);

			//set base uri for transformations
			document.setBaseUri(url);

			// transform document
			html(document);

			// copy bytes to array
			bytes = document.html().getBytes();
		}

		connection.disconnect();
	}

	public Map<String, List<String>> getHeaders()
	{
		return headers;
	}

	public int getBodyLength()
	{
		return bytes.length;
	}

	public byte[] getBody()
	{
		return bytes;
	}

	/* ----------------  TRANSFORMERS ----------------  */

	private static void html(Document document)
	{
		// transform hrefs
		document.getElementsByAttribute("href").forEach(element ->
				element.attr("href", Utils.encodeURL(element.attr("abs:href"))));

		// transform srcs
		document.getElementsByAttribute("src").forEach(element ->
				element.attr("src", Utils.encodeURL(element.attr("abs:src"))));
	}
}
