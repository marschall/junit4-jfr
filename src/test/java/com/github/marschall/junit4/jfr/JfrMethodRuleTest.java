package com.github.marschall.junit4.jfr;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.Label;

public class JfrMethodRuleTest {

  @Rule
  public JfrMethodRule jfr = new JfrMethodRule();

  @BeforeClass
  public static void beforeAll1() {
    DemoEvent demoEvent = new DemoEvent();
    demoEvent.setName("@BeforeClass");
    demoEvent.commit();
  }

  @Before
  public void setUp() {
    DemoEvent demoEvent = new DemoEvent();
    demoEvent.setName("@Before");
    demoEvent.commit();
  }

  @Test
  public void test() {
    DemoEvent demoEvent = new DemoEvent();
    demoEvent.setName("test");
    demoEvent.commit();
  }

  @After
  public void tearDown() {
    DemoEvent demoEvent = new DemoEvent();
    demoEvent.setName("@After");
    demoEvent.commit();
  }

  @AfterClass
  public static void afterAll1() {
    DemoEvent demoEvent = new DemoEvent();
    demoEvent.setName("@AfterClass");
    demoEvent.commit();
  }

  @Category("Test")
  @Label("Demo Event")
  static class DemoEvent extends Event {

    @Label("Name")
    private String name;

    String getName() {
      return this.name;
    }

    void setName(String name) {
      this.name = name;
    }

  }

}
