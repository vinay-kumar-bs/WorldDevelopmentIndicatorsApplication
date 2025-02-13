/// <reference types="cypress" />

describe("UpdateData Component", () => {
  const apiBaseUrl = "http://localhost:8080/api/devIndicator";
  beforeEach(() => {
    cy.visit("http://localhost:8081/update");

    // Mock the PUT request for updateRecord API
    cy.intercept("PUT", apiBaseUrl, {
      statusCode: 200,
      body: { message: "Data updated successfully." },
    }).as("updateRecord");
  });

  it("Checks if the page contains a form", () => {
    cy.get("form").should("exist");
  });

  it("Fills the form with data and submits successfully", () => {
    // Fill out the form (clearing inputs first)
    const testData = {
      countryName: "USA",
      countryCode: "US",
      region: "North America",
      incomeGroup: "High income",
      year: "2024",
      birthRate: "12.5",
      deathRate: "8.2",
      electricPowerConsumption: "1000",
      gdp: "21439",
      gdpPerCapita: "65000",
      internetUsagePercent: "90",
      mortalityRate: "5",
      lifeExpectancy: "78",
      populationDensity: "36",
      unemploymentPercent: "3.5",
    };

    cy.get("input").eq(0).clear().type(testData.countryName);
    cy.get("input").eq(1).clear().type(testData.countryCode);
    cy.get("input").eq(2).clear().type(testData.region);
    cy.get("input").eq(3).clear().type(testData.incomeGroup);
    cy.get("input").eq(4).clear().type(testData.year);
    cy.get("input").eq(5).clear().type(testData.birthRate);
    cy.get("input").eq(6).clear().type(testData.deathRate);
    cy.get("input").eq(7).clear().type(testData.electricPowerConsumption);
    cy.get("input").eq(8).clear().type(testData.gdp);
    cy.get("input").eq(9).clear().type(testData.gdpPerCapita);
    cy.get("input").eq(10).clear().type(testData.internetUsagePercent);
    cy.get("input").eq(11).clear().type(testData.mortalityRate);
    cy.get("input").eq(12).clear().type(testData.lifeExpectancy);
    cy.get("input").eq(13).clear().type(testData.populationDensity);
    cy.get("input").eq(14).clear().type(testData.unemploymentPercent);

    // Submit
    cy.get("button.submit-btn").click();

    // Wait for API call
    cy.wait("@updateRecord");

    // Check if success alert appears
    cy.on("window:alert", (text: any) => {
      expect(text).toBe("Data updated successfully.");
    });
  });
});
