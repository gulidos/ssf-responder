package ru.in.ssf.resp.json.primitives;
public class Value {
	private  int value;
	
	public Value () {}

	public Value (int value) {
		this.value = value;
	}
	
	public void setValue(int value) {this.value = value;	}
	public int getValue() {	return value;}

	@Override
	public String toString() {
		return  String.valueOf(value);
	}
	
	
}