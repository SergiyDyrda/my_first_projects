package com.javarush.test.level36.lesson04.big01.view;

import com.javarush.test.level36.lesson04.big01.bean.User;
import com.javarush.test.level36.lesson04.big01.controller.Controller;
import com.javarush.test.level36.lesson04.big01.model.ModelData;

/**
 * Created by CMI-USER on 1/21/2016.
 */
public class UsersView implements View
{
    private Controller controller;
    private EditUserView editUserView;

    public EditUserView getEditUserView()
    {
        return editUserView;
    }

    public void setEditUserView(EditUserView editUserView)
    {
        this.editUserView = editUserView;
    }

    @Override
    public void refresh(ModelData modelData)
    {
        if (modelData.isDisplayDeletedUserList())
            System.out.println("All deleted users:");
        else
            System.out.println("All users:");
        for (User user : modelData.getUsers())
            System.out.println(user);
        System.out.println("===================================================");
    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void fireEventShowAllUsers()
    {
        controller.onShowAllUsers();
    }

    public void fireEventShowDeletedUsers() {
        controller.onShowAllDeletedUsers();
    }

    public void fireEventOpenUserEditForm(long id) {
        controller.onOpenUserEditForm(id);
    }

}
