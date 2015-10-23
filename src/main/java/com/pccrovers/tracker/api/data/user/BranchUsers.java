package com.pccrovers.tracker.api.data.user;

import com.pccrovers.tracker.api.data.BaseBranch;

public class BranchUsers extends BaseBranch
{
    @Override
    public BaseBranch traverse(String path)
    {
        try
        {
            return new BranchUser(Long.valueOf(path));
        }
        catch(NumberFormatException e)
        {
            return null;
        }
    }


}
