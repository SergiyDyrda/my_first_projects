package com.javarush.test.level28.lesson15.big01.model;

import com.javarush.test.level28.lesson15.big01.view.View;
import com.javarush.test.level28.lesson15.big01.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serj on 29.12.2015.
 */
public class Model
{
    private View view;
    private Provider[] providers;

    public Model(View view, Provider[] providers)
    {
        if (providers == null || providers.length == 0 || view == null) throw new IllegalArgumentException();
        this.providers = providers;
        this.view = view;
    }

    public void selectCity(String city) {
        List<Vacancy> vacancies = new ArrayList<>();
        for (Provider prov : providers)
        {
            vacancies.addAll(prov.getJavaVacancies(city));
        }

        view.update(vacancies);
    }
}
