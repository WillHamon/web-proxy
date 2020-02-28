package me.willhamon.webproxy;

import com.sun.net.httpserver.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ConnectionProxy
{
	private String baseUrl;
	private HttpURLConnection connection;
	private byte[] bytes;

	public ConnectionProxy(String url) throws IOException
	{
		this.baseUrl = url;

		// open url connection
		connection = (HttpURLConnection)new URL(url).openConnection();
		connection.setConnectTimeout(5000);
	}

	public void setHeaders(Headers headers)
	{
		headers.add("Content-Type", connection.getHeaderField("Content-Type"));

		// this doesn't work for some reason :(
		//connection.getHeaderFields().keySet().forEach(header -> headers.add(header, connection.getHeaderField(header)));
	}

	public int getBodyLength()
	{
		return bytes.length;
	}

	public byte[] getBody()
	{
		return bytes;
	}

	public void connect() throws IOException
	{
		connection.connect();

		// wait for connection?
		if(connection.getResponseCode() != 200)
		{
			System.out.println("Error proxying connection: " + baseUrl + ":" + connection.getResponseCode());
			return;
		}

		// get buffered reader from url
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		// write bytes from buffered reader into string builder into byte array
		StringBuilder sb = new StringBuilder();
		br.lines().forEach(s -> sb.append(s).append("\n"));
		br.close();
		bytes = sb.toString().getBytes();

		// if page is html, modify specifics
		if(connection.getHeaderField("Content-Type").contains("text/html")) html();

		connection.disconnect();
	}

	/* ----------------  DOCUMENT-SPECIFIC MODIFIERS ----------------  */

	private void html()
	{
		// parse html
		Document document = Jsoup.parse(new String(bytes));

		//set base uri for transformations
		document.setBaseUri(baseUrl);

		// transform hrefs
		document.getElementsByAttribute("href").forEach(element ->
				element.attr("href", Utils.encodeURL(element.attr("abs:href"))));

		// transform srcs
		document.getElementsByAttribute("src").forEach(element ->
				element.attr("src", Utils.encodeURL(element.attr("abs:src"))));

		// copy bytes to array
		bytes = document.html().getBytes();
	}
}
