package com.javarush.test.level28.lesson15.big01;

import com.javarush.test.level28.lesson15.big01.model.HHStrategy;
import com.javarush.test.level28.lesson15.big01.model.Model;
import com.javarush.test.level28.lesson15.big01.model.MoikrugStrategy;
import com.javarush.test.level28.lesson15.big01.model.Provider;
import com.javarush.test.level28.lesson15.big01.view.HtmlView;


/**
 * Created by Serj on 23.12.2015.
 */
public class Aggregator
{
    public static void main(String[] args)
    {
        HtmlView view = new HtmlView();
        Provider providerHH = new Provider(new HHStrategy());
        Provider providerMoikrug = new Provider(new MoikrugStrategy());
        Model model = new Model(view, new Provider[] {providerHH, providerMoikrug});
        view.setController(new Controller(model));
        view.userCitySelectEmulationMethod();


    }
}
