package com.wapwag.woss.modules.monitor.pumpNode;

import java.util.List;

/**
 * @author zx
 * @since 2018/5/22
 */
public class PhNode {
	private String nodeCode;
	private String nodeName;
	private Integer nodeType;
	private List<DevicePoint> points;

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public List<DevicePoint> getPoints() {
		return points;
	}

	public void setPoints(List<DevicePoint> points) {
		this.points = points;
	}

	public PhNode() {
	}

	public PhNode(String nodeCode, String nodeName, Integer nodeType, List<DevicePoint> points) {
		this.nodeCode = nodeCode;
		this.nodeName = nodeName;
		this.nodeType = nodeType;
		this.points = points;
	}
}
