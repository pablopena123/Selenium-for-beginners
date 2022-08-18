package com.herokuapp.theinternet.base;

import org.testng.annotations.DataProvider;

public class TestUtilities extends BaseTest{

	//STATIC SLEEP
	public void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@DataProvider(name="files")
	protected static Object[][] files() {
		return new Object[][] {
			{1,"index.html"},
			{2,"logo.png"},
			{3,"text.txt"}
		};
	}
}
