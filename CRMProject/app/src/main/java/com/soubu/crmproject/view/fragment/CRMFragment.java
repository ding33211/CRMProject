package com.soubu.crmproject.view.fragment;

import com.soubu.crmproject.base.mvp.presenter.FragmentPresenter;
import com.soubu.crmproject.delegate.CRMFragmentDelegate;

public class CRMFragment extends FragmentPresenter<CRMFragmentDelegate> {

    @Override
    protected Class<CRMFragmentDelegate> getDelegateClass() {
        return CRMFragmentDelegate.class;
    }
}
