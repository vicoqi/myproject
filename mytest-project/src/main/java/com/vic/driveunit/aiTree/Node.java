package com.vic.driveunit.aiTree;

public interface Node {
	
//	List<Node> sonNode = new ArrayList<>();
	
	/***/
	public int excute(Context context);

	public void addSon(Node son);
}
