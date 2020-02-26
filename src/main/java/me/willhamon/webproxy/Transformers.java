package me.willhamon.webproxy;

import org.jsoup.nodes.Document;

public class Transformers
{
	public static void html(Document document)
	{
		// transform hrefs
		document.getElementsByAttribute("href").forEach(element ->
				element.attr("href", Utils.encodeURL(element.attr("abs:href"))));

		// transform srcs
		document.getElementsByAttribute("src").forEach(element ->
				element.attr("src", Utils.encodeURL(element.attr("abs:src"))));
	}
}
