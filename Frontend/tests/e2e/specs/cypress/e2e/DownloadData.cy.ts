describe("DownloadData Component", () => {
  beforeEach(() => {
    cy.visit("http://localhost:8081/download");
  });

  it("Displays the download link", () => {
    cy.get(".download-file a")
      .should("be.visible")
      .and(
        "have.attr",
        "href",
        "/WorldDevelopmentIndicatorData/WorldBank-Development-Indicators.csv"
      )
      .and("have.attr", "download", "WorldBank-Development-Indicators.csv");
  });

  it("Checks if the download link works when clicked", () => {
    cy.intercept("GET", "**/WorldBank-Development-Indicators.csv").as(
      "downloadFile"
    );

    cy.get(".download-file a").click();

    cy.wait("@downloadFile").its("response.statusCode").should("eq", 200);
  });
});
