package com.soubu.crmproject.view.fragment;

import com.soubu.crmproject.base.mvp.presenter.FragmentPresenter;
import com.soubu.crmproject.delegate.ProfileFragmentDelegate;

public class ProfileFragment extends FragmentPresenter<ProfileFragmentDelegate> {
    @Override
    protected Class<ProfileFragmentDelegate> getDelegateClass() {
        return ProfileFragmentDelegate.class;
    }
}
