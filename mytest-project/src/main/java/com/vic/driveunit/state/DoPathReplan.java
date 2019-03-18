package com.vic.driveunit.state;

import com.vic.driveunit.aiTree.Context;
import com.vic.driveunit.aiTree.AbstractStateNode;

/**
 * @Date: 2018/9/24 15:42
 * @Description:
 */
public class DoPathReplan extends AbstractStateNode {

    @Override
    public void enter() {
        current_context = new Context();
    }

    @Override
    public int excute(Context context) {
        current_context.addProperty("a","a");
        return 0;
    }

    @Override
    public void exit() {
        current_context = null;
    }

}
