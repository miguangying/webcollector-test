package com.mifuns.webcollector.learn;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by miguangying on 2016/12/25.
 */
public class Meituan extends BreadthCrawler{

    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     * information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     * links which match regex rules from pag
     */
    public Meituan(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        /*种子页面*/
        this.addSeed("http://waimai.meituan.com/home/wx4gdbnswb58");

        /*正则规则设置*/
        /*爬取符合 http://news.hfut.edu.cn/show-xxxxxxhtml的URL*/
        this.addRegex("http://waimai.meituan.com/restaurant/.*");
        /*不要爬取 jpg|png|gif*/
        this.addRegex("-.*\\.(jpg|png|gif).*");
        /*不要爬取包含 # 的URL*/
        this.addRegex("-.*#.*");
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.getUrl();
        //System.out.println(url);
        /*判断是否为新闻页，通过正则可以轻松判断*/
        if (page.matchUrl("http://waimai.meituan.com/restaurant/.*")) {
            /*we use jsoup to parse page*/
            Document doc = page.getDoc();

            /*extract title and content of news by css selector*/
            String title = page.select("div[class=na]>a>span").text();
            Elements elements = page.select("div[class=j-pic-food pic-food]");
            System.out.println("店家:" + title);
            System.out.println("地址:" + url);
            for (Element element: elements) {
                String caiming= element.select("span[class=name fl]").text();
                System.out.println("菜名："+caiming);
                String danjia =element.select("div[class=only]").text();
                System.out.println("价格："+danjia);
                String yueshou =element.select("span[class=sold-count ct-lightgrey]>span").text();
                System.out.println("月售："+yueshou);
            }



            /*如果你想添加新的爬取任务，可以向next中添加爬取任务，
               这就是上文中提到的手动解析*/
            /*WebCollector会自动去掉重复的任务(通过任务的key，默认是URL)，
              因此在编写爬虫时不需要考虑去重问题，加入重复的URL不会导致重复爬取*/
            /*如果autoParse是true(构造函数的第二个参数)，爬虫会自动抽取网页中符合正则规则的URL，
              作为后续任务，当然，爬虫会去掉重复的URL，不会爬取历史中爬取过的URL。
              autoParse为true即开启自动解析机制*/
            //next.add("http://xxxxxx.com");
        }
    }


    public static void main(String[] args) throws Exception {
        Meituan crawler = new Meituan("crawl", true);
        /*线程数*/
        crawler.setThreads(50);
        /*设置每次迭代中爬取数量的上限*/
        crawler.setTopN(5000);
        /*设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
           如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
           续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到
           死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
           的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务
           进行爬取。断点爬取默认为false*/
        //crawler.setResumable(true);
        /*开始深度为4的爬取，这里深度和网站的拓扑结构没有任何关系
            可以将深度理解为迭代次数，往往迭代次数越多，爬取的数据越多*/
        crawler.start(8);
    }

}