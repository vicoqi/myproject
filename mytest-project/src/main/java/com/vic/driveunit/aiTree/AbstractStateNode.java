package com.vic.driveunit.aiTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2018/9/21 13:54
 * @Description:
 */
public abstract class AbstractStateNode implements Node{

    protected Context current_context;

    protected List<Node> subConditionNode = new ArrayList<>();  //状态的子节点 只能是condition条件

    public  void run(){
//        current_context = new AIContext();
        enter();
        excute(current_context);
        excuteCondition();
    }

    public void excuteCondition(){
        if(subConditionNode!=null) {
            for (Node condition : subConditionNode) {
                if (((AbstractConditionNode)condition).run(current_context) == 0) {
//                current_context = null;
                    report();
                    exit();
                    break;
                }
            }
        }
    }

    public void report(){
//        logger("..............")
        System.out.println(this.getClass().getSimpleName()+"这个节点下面的所有子节点执行完毕");
//        reportToRcs("..............")
    }

    @Override
    public void addSon(Node son) {
        subConditionNode.add(son);
    }

    public abstract void enter();

    public abstract void exit();

//    public List<AbstractConditionNode> getSubConditionNode() {
//        return subConditionNode;
//    }
//
//    public void setSubConditionNode(List<AbstractConditionNode> subConditionNode) {
//        this.subConditionNode = subConditionNode;
//    }

    public Context getCurrent_context() {
        return current_context;
    }

    public void setCurrent_context(Context current_context) {
        this.current_context = current_context;
    }
}
