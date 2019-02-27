package com.github.jartisan.latest.global.enums;

/***
 * 编程语言
 * @author ejb3
 *
 */
public enum Language {
	/***
	 * Java
	 */
	JAVA("Java"),
	/***
	 * PHP
	 */
	PHP("PHP"),
	/***
	 * Python
	 */
	PYTHON("Python"),
	/***
	 * C++
	 */
	CPLUS("C++"),
	/***
	 * c
	 */
	C("C"),
	/***
	 * Scala
	 */
	SCALA("Scala"),
	/***
	 * Ruby
	 */
	RUBY("Ruby"),
	/***
	 * JavaScript
	 */
	JAVASCRIPT("JavaScript"),
	/***
	 * TypeScript
	 */
	TYPESCRIPT("TypeScript"),
	/***
	 * Groovy
	 */
	GROOVY("Groovy"),
	/***
	 * Erlang
	 */
	ERLANG("Erlang"),
	
	/***
	 * CSS
	 */
	CSS("CSS"),
	/***
	 * Objective-C
	 */
	OBJECTIVEC("Objective-C"),
	
	/***
	 * Swift
	 */
	SWIFT("Swift"),
	/***
	 * Go
	 */
	GO("Go"),
	
	/***
	 * Dart
	 */
	DART("Dart");

	
	private String name;

	private Language(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}  
	
	
}
