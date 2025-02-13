// /// <reference types="cypress" />

describe("RetrieveData Component", () => {
  const apiBaseUrl = "http://localhost:8080/api/devIndicator";
  beforeEach(() => {
    cy.intercept("GET", `${apiBaseUrl}/countries`, {
      body: ["USA", "India", "Canada"],
    }).as("getCountries");

    cy.intercept("GET", `${apiBaseUrl}/years`, {
      body: [2013, 2014, 2015],
    }).as("getYears");

    cy.intercept("POST", "**/fetchRecord", {
      statusCode: 200,
      body: {
        countryName: "Canada",
        countryCode: "CAN",
        region: "North America",
        incomeGroup: "High income",
        year: 2014,
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
      },
    }).as("fetchRecord");

    cy.visit("http://localhost:8081/retrieve");
  });

  it("Loads dropdowns with API data", () => {
    cy.wait("@getCountries");
    cy.wait("@getYears");

    cy.get("select").first().should("contain", "USA");
    cy.get("select").first().should("contain", "India");
    cy.get("select").first().should("contain", "Canada");

    cy.get("select").last().should("contain", "2013");
    cy.get("select").last().should("contain", "2014");
    cy.get("select").last().should("contain", "2015");
  });

  it("Submits the form, fetches data, and navigates to DisplayData", () => {
    cy.get("select").first().select("Canada");
    cy.get("select").last().select("2014");

    cy.get("input[type=submit]").click();

    // cy.wait("@fetchRecord");

    cy.url().should("include", "/display");

    cy.contains("Canada").should("be.visible");
    cy.contains("CAN").should("be.visible");
    cy.contains("North America").should("be.visible");
    cy.contains("High income").should("be.visible");
    cy.contains(2014).should("be.visible");
  });

  it("Prevents submission if fields are empty", () => {
    cy.get("input[type=submit]").click();
    cy.url().should("include", "/retrieve"); // Should stay on same page
  });
});
