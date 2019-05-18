package com.eclipsesource.glsp.api.types;

public class BorderPlacement {

	private String side = "left";
	private Double position = 0.5d;
	private Double lineOffset = 0.5d;

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public Double getPosition() {
		return position;
	}

	public void setPosition(Double position) {
		this.position = position;
	}

	public Double getLineOffset() {
		return lineOffset;
	}

	public void setLineOffset(Double lineOffset) {
		this.lineOffset = lineOffset;
	}

}
