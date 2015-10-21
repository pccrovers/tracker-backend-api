package com.pccrovers.tracker.api;

import com.pccrovers.tracker.api.data.BaseBranch;
import com.pccrovers.tracker.api.data.group.BranchGroups;

public class GroupApiServlet extends BaseApiServlet
{
    @Override
    protected BaseBranch getStartingBranch()
    {
        return new BranchGroups();
    }
}
