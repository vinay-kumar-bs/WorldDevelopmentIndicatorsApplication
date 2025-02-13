/// <reference types="cypress" />

describe("ChartsView Component", () => {
  const apiBaseUrl = "http://localhost:8080/api/devIndicator";
    beforeEach(() => {
      cy.intercept("GET", `${apiBaseUrl}/countries`, {
        body: ["USA", "India", "Canada"],
      }).as("getCountries");
  
      cy.intercept("GET", /api\/devIndicator\/country\?countryName=.*/, (req:any) => {
        const country = req.query.countryName;
        if (country === "Canada") {
          req.reply({
            statusCode: 200,
            body: [
                { year: 2011, birthRate: 13.65 },
                { year: 2012, birthRate: 15.95 },
              { year: 2013, birthRate: 18.05 },
              { year: 2014, birthRate: 24.70 },
              { year: 2015, birthRate: 29.52 },
              { year: 2016, birthRate: 23.95 },
              { year: 2017, birthRate: 45.85 },
              { year: 2018, birthRate: 56.95 },
            ],
          });
        } else {
          req.reply({ statusCode: 404, body: [] });
        }
      }).as("getChartData");
  
      cy.visit("http://localhost:8081/charts");
    });
  
    it("Loads country dropdown with API data", () => {
      cy.wait("@getCountries");
  
      cy.get("select").first().should("contain", "USA");
      cy.get("select").first().should("contain", "India");
      cy.get("select").first().should("contain", "Canada");
    });
  
    it("Submits form and displays chart data", () => {
      cy.wait("@getCountries");
  
      cy.get("select").first().select("Canada");
      cy.get("select").last().select("birthRate");
  
      cy.get("input[type=submit]").click();
  
      cy.wait("@getChartData");
  
      cy.contains("Line chart for birthRate in Canada").should("be.visible");
    });
  
    it("Shows error alert if data fetch fails", () => {
      cy.wait("@getCountries");
  
      cy.get("select").first().select("USA");
      cy.get("select").last().select("birthRate");
  
      cy.get("input[type=submit]").click();
  
      cy.on("window:alert", (txt:any) => {
        expect(txt).toBe("Failed to fetch chart data.");
      });
    });
  
    it("Prevents submission if fields are empty", () => {
      cy.get("input[type=submit]").click();
  
      cy.on("window:alert", (txt:any) => {
        expect(txt).toBe("Please select both a country and an attribute.");
      });
    });
  });
  