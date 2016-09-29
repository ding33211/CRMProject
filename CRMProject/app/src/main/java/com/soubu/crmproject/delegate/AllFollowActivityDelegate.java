package com.soubu.crmproject.delegate;

/**
 * Created by dingsigang on 16-9-28.
 */
public class AllFollowActivityDelegate extends BaseRecyclerViewActivityDelegate {

    @Override
    public boolean ifNeedUseSwipeRefresh() {
        return true;
    }

    @Override
    public boolean ifNeedFilterOrSorter() {
        return true;
    }
}
