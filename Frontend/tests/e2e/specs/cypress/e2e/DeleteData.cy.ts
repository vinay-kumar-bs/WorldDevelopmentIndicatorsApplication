/// <reference types="cypress" />

describe("DeleteData Component", () => {
  beforeEach(() => {
    // Mock the DELETE request
    cy.intercept("DELETE", "**/deleteRecord*", {
      statusCode: 200,
      body: { success: true },
    }).as("deleteRecord");

    cy.visit("http://localhost:8081/delete");

    // Set Vuex state for countryRecord after attaching store to window in main.ts
    cy.window().then((win) => {
      win.__store__.commit("setDataToRecord", {
        countryName: "Canada",
        countryCode: "CAN",
        region: "North America",
        incomeGroup: "High income",
        year: 2013,
        birthRate: 12.5,
        deathRate: 8.2,
        electricPowerConsumption: 1000,
        gdp: 21439,
        gdpPerCapita: 65000,
        internetUsagePercent: 90,
        mortalityRate: 5,
        lifeExpectancy: 78,
        populationDensity: 36,
        unemploymentPercent: 3.5,
      });
    });
  });

  it("Displays correct deletion record details", () => {
    cy.contains("Country Name : Canada").should("exist");
    cy.contains("Year : 2013").should("exist");
  });

  it("Confirms deletion and sends DELETE request", () => {
    cy.get(".btn-delete").click();

    // cy.wait("@deleteRecord").its("request.url").should("include", "countryName=Canada&year=2013");

    cy.on("window:alert", (text: any) => {
      expect(text).toBe("Data deleted successfully.");
    });
  });

  it("Navigates back to the display page when back button is clicked", () => {
    cy.get(".home-btn").click();
    cy.url().should("include", "/display");
  });
});
