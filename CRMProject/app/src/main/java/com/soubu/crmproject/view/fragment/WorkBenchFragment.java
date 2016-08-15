package com.soubu.crmproject.view.fragment;

import com.soubu.crmproject.base.mvp.presenter.FragmentPresenter;
import com.soubu.crmproject.delegate.WorkBenchFragmentDelegate;

public class WorkBenchFragment extends FragmentPresenter<WorkBenchFragmentDelegate> {
    @Override
    protected Class<WorkBenchFragmentDelegate> getDelegateClass() {
        return WorkBenchFragmentDelegate.class;
    }
}
