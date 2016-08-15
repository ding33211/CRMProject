package com.soubu.crmproject.view.fragment;

import com.soubu.crmproject.base.mvp.presenter.FragmentPresenter;
import com.soubu.crmproject.delegate.TodoFragmentDelegate;

public class TodoFragment extends FragmentPresenter<TodoFragmentDelegate> {
    @Override
    protected Class<TodoFragmentDelegate> getDelegateClass() {
        return TodoFragmentDelegate.class;
    }
}
