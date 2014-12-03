package org.cabi.ofra.dataload.util;

/**
 * Created by equiros on 11/26/2014.
 */
public class Triplet<A, B, C> {
  private A first;
  private B second;
  private C third;

  public Triplet(A first, B second, C third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }

  public A getFirst() {
    return first;
  }

  public B getSecond() {
    return second;
  }

  public C getThird() {
    return third;
  }
}
