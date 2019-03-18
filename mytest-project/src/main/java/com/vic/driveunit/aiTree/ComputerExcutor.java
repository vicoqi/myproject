package com.vic.driveunit.aiTree;

import com.vic.driveunit.xmlFactory.NodeFactory;


public  class ComputerExcutor {
	private static Context global_context = new Context();
	public void start(){
		AbstractStateNode rootState = (AbstractStateNode) NodeFactory.getRootNode();
		Context current_context = new Context();  //根节点第一个执行所以需要初始化当前上下文
		rootState.setCurrent_context(current_context);
		rootState.run();
	}
}
