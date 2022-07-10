package com.jpmc.market.domain;

public class GBCEResponse {
  private Double gbce;

  @Override
  public String toString() {
    return "GBCEResponse{" + "gbce=" + gbce + '}';
  }

  private GBCEResponse(GBCEResponseBuilder builder) {
    this.gbce = builder.gbce;
  }

  public Double getGbce() {
    return gbce;
  }

  public void setGbce(Double gbce) {
    this.gbce = gbce;
  }

  public static class GBCEResponseBuilder {
    private Double gbce;

    public GBCEResponseBuilder(){}
    public GBCEResponseBuilder gbce(final Double gbce) {
      this.gbce = gbce;
      return this;
    }

    public GBCEResponse build() {
      return new GBCEResponse(this);
    }
  }
}
