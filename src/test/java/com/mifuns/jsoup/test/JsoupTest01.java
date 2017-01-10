package com.mifuns.jsoup.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by miguangying on 2016/12/28.
 */
public class JsoupTest01 {


    @Test
    public void test01(){
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        Document doc = Jsoup.parse(html);




    }


    @Test
    public void test02(){
        String url = "http://111.206.162.250/h5/customer/order/place/orderPre";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(3000).post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element element = doc.getElementsByTag("body").get(0);
        Elements links = element.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
        }

        Elements pngs = doc.select("img[src$=.jpg]");
        for (Element link : links) {
            String linkHref = link.attr("src");
            String linkText = link.text();
        }
    }

}
