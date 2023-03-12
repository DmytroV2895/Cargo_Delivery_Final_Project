package com.varukha.webproject.command;

/**
 * Class Router used to routing between application pages.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */

public class Router {

	/**
	 * Enum describe all possibles router types.
	 */
	public enum Type{
		FORWARD,
		REDIRECT
	}
	private String pagePath;
	private Type type;
	
	/**
	 * Constructor set default route type: Forward 
	 */
	public Router() {
		this.type = Type.FORWARD;
	}
	
	/**
	 * Constructor set default route type(Forward) by necessary page path.
	 * @param pagePath path to the page
	 */
	public Router(String pagePath) {
		this.pagePath = pagePath;
		this.type = Type.FORWARD;
	}

	/**
	 * Constructor set route type by necessary page path.
	 * @param pagePath path to the page
	 * @param type {@link Type}
	 */
	public Router(String pagePath, Type type) {
		this.pagePath = pagePath;
		this.type = type;
	}

	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}	
}
