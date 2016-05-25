package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serj on 29.12.2015.
 */
public class MoikrugStrategy implements Strategy
{
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; rv:38.0) Gecko/20100101 Firefox/38.0";

    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> vacancies = new ArrayList<>();
        try
        {
            Document document;
            int pageCounter = 1;
            while(true)
            {
                document = getDocument(searchString, pageCounter++);
                if(document == null) break;
                Elements elements = document.getElementsByClass("job");
                if(elements.size() == 0) break;
                for(Element element : elements)
                {
                    String title = "";
                    String salary = "";
                    String city = "";
                    String company = "";
                    String siteName = "https://moikrug.ru";
                    String url = "";
                    Vacancy vacancy = new Vacancy();
                    Element titleElem = element.getElementsByClass("title").first();
                    if(titleElem != null)
                    {
                        title = titleElem.select("a").first().text();
                        url = siteName + titleElem.select("a").attr("href");
                    }
                    Element salaryElem = element.getElementsByClass("count").first();
                    if(salaryElem != null)
                    {
                        salary = salaryElem.text();
                    }
                    Element cityElem = element.getElementsByClass("location").first();
                    if(cityElem != null)
                    {
                        city = cityElem.text();
                    }
                    Element companyElem = element.getElementsByClass("company_name").first();
                    if(companyElem != null)
                    {
                        company = companyElem.select("a[href]").text();
                    }
                    vacancy.setTitle(title);
                    vacancy.setSalary(salary);
                    vacancy.setCity(city);
                    vacancy.setCompanyName(company);
                    vacancy.setSiteName(siteName);
                    vacancy.setUrl(url);
                    vacancies.add(vacancy);
                }
            }
        }
        catch (Exception e)
        {
        }
        return vacancies;
    }

    private Document getDocument(String searchString, int i) throws IOException
    {
        return Jsoup.connect(String.format(URL_FORMAT, searchString, i)).userAgent(USER_AGENT).get();
    }
}
