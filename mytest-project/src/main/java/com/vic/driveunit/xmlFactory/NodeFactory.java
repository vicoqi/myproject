package com.vic.driveunit.xmlFactory;

import com.vic.driveunit.aiTree.AbstractStateNode;
import com.vic.driveunit.aiTree.Context;
import com.vic.driveunit.aiTree.Node;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * @author wqp
 *
 */
public class NodeFactory {

    private static Node root = null;

	/**
	 * 解析xml为 节点对象
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		SAXReader reader = new SAXReader();
		Document document = null;
//		System.out.println(this.getClass().getResource(""));
		try {
			document = reader.read(new File("src/com/kuaicang/driveunit/config/computerTree.xml"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element rootElement = document.getRootElement();
		
		Node root = listNode(rootElement);

        this.root = root;
	}

	public static Node getRootNode(){
	    return root;
    }

	public Node listNode(Element node) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Node aiNode = Xml2Node(node);
		
		Iterator<Element> elementIterator = node.elementIterator();
		while (elementIterator.hasNext()) {
			Element next = elementIterator.next();
			aiNode.addSon(listNode(next));
		}
		return aiNode;
	}

	private Node Xml2Node(Element node) throws ClassNotFoundException, InstantiationException, 
	IllegalAccessException{
		StringBuffer className = new StringBuffer(64);
		className.append("com.kuaicang.driveunit.");
		className.append(node.getName());
		className.append(".");
		className.append(node.attribute("class").getValue());
		return createNodeByName(className.toString());
	}

	private Node createNodeByName (String nodeName) 
			throws ClassNotFoundException, 
			InstantiationException, 
			IllegalAccessException {
		if (nodeName == null || nodeName.trim().equals("")) {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		Class<? extends Node> node = 
				(Class<? extends Node>) Class.forName(nodeName);
		
		return node.newInstance();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		NodeFactory xml = new NodeFactory();
		xml.init();

		AbstractStateNode rootState = (AbstractStateNode) NodeFactory.getRootNode();
		Context current_context = new Context();  //根节点第一个执行所以需要初始化当前上下文
		rootState.setCurrent_context(current_context);
		rootState.run();
	}
}
