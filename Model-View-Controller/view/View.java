package com.javarush.test.level36.lesson04.big01.view;

import com.javarush.test.level36.lesson04.big01.bean.User;
import com.javarush.test.level36.lesson04.big01.controller.Controller;
import com.javarush.test.level36.lesson04.big01.model.ModelData;

import java.util.List;

/**
 * Created by CMI-USER on 1/21/2016.
 */
public interface View
{
    void refresh(ModelData modelData);
    void setController(Controller controller);
}
