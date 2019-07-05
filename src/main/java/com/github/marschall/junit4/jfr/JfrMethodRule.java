package com.github.marschall.junit4.jfr;

import java.lang.reflect.Method;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.StackTrace;

/**
 * A method rule that causes a JUnit test to generate Flight Recorder events.
 */
public final class JfrMethodRule implements MethodRule {

  @Override
  public Statement apply(Statement base, FrameworkMethod method, Object target) {
    return new Statement() {

      @Override
      public void evaluate() throws Throwable {
        TestExecutionEvent event = new TestExecutionEvent();
        event.setTestClass(method.getDeclaringClass());
        event.setTestMethod(getMethodName(method.getMethod()));
        event.begin();
        try {
          base.evaluate();
        } finally {
          event.end();
          event.commit();
        }

      }
    };
  }

  private static String getMethodName(Method method) {
    boolean isVoid = method.getReturnType() == Void.TYPE;
    boolean hasParameters = method.getParameterCount() == 0;
    StringBuilder buffer = new StringBuilder();
    if (!isVoid) {
      buffer.append(method.getReturnType().getSimpleName());
      buffer.append(' ');
    }
    buffer.append(method.getName());
    if (hasParameters) {
      buffer.append('(');
      boolean first = true;
      for (Class<?> parameterType : method.getParameterTypes()) {
        if (!first) {
          buffer.append(", ");
        }
        buffer.append(parameterType.getSimpleName());
        first = false;
      }
      buffer.append(')');
    } else {
      buffer.append("()");
    }
    return buffer.toString();
  }



  @Category("JUnit")
  @StackTrace(false)
  @Label("@Test")
  @Description("execution of a test without @BeforeAll and @AfterAll methods")
  static class TestExecutionEvent extends Event {

    @Label("Test Method")
    @Description("The method associated with the test, if available")
    private String testMethod;

    @Label("Test Class")
    @Description("The class associated with the test, if available")
    private Class<?> testClass;

    String getTestMethod() {
      return this.testMethod;
    }

    void setTestMethod(String query) {
      this.testMethod = query;
    }

    Class<?> getTestClass() {
      return this.testClass;
    }

    void setTestClass(Class<?> testClass) {
      this.testClass = testClass;
    }

  }

}
