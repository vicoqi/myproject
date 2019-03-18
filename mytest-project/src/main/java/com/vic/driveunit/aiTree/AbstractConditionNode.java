package com.vic.driveunit.aiTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2018/9/21 17:29
 * @Description:
 */
public abstract class  AbstractConditionNode implements Node{
    protected List<Node> subStateOrConditionNode = new ArrayList<>();

//    protected AIContext currentStateContext ;


    public  int run(Context currentStateContext){
//        this.currentStateContext = currentStateContext;
        int retCode = excute(currentStateContext);
        if(retCode!=0){  //条件执行不成功
//            this.currentStateContext = null;
            return retCode;
        }
        enterStateOrCondition(currentStateContext);
        return retCode;
    }

    public void enterStateOrCondition(Context currentStateContext){
        for(Node conditionOrState:subStateOrConditionNode){
            if(conditionOrState instanceof AbstractConditionNode){
                if(((AbstractConditionNode)conditionOrState).run(currentStateContext)==0){
//                    currentStateContext = null;
                    break;
                }
            }
            if(conditionOrState instanceof AbstractStateNode){
                ((AbstractStateNode)conditionOrState).run();
                report();
            }
        }
    }

    @Override
    public void addSon(Node son) {
        subStateOrConditionNode.add(son);
    }


    public void report(){
//        logger("..............")
        System.out.println("条件"+this.getClass().getSimpleName()+"这个节点执行完毕");
//        reportToRcs("..............")
    }


    public List<Node> getSubStateOrConditionNode() {
        return subStateOrConditionNode;
    }

    public void setSubStateOrConditionNode(List<Node> subStateOrConditionNode) {
        this.subStateOrConditionNode = subStateOrConditionNode;
    }
}
