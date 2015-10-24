package com.pccrovers.tracker.api;

import com.pccrovers.tracker.api.data.BaseBranch;
import com.pccrovers.tracker.api.data.user.BranchUsers;

public class UserApiServlet extends BaseApiServlet
{
    @Override
    protected BaseBranch getStartingBranch()
    {
        return new BranchUsers();
    }
}
