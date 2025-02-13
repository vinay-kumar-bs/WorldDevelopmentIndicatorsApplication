describe("Create Data Page", () => {
  beforeEach(() => {
    cy.visit("http://localhost:8081/create");
  });

  it("should create data successfully", () => {
    cy.intercept("POST", "http://localhost:8080/api/devIndicator", {
      statusCode: 200,
      body: { message: "Data created successfully." },
    }).as("addRecord");

    // Fill out the form
    cy.get("#countryName").type("USA");
    cy.get("#countryCode").type("US");
    cy.get("#region").type("North America");
    cy.get("#incomeGroup").type("High income");
    cy.get("#year").type("2024");
    cy.get("#birthRate").type("12.5");
    cy.get("#deathRate").type("8.2");
    cy.get("#electricPowerConsumption").type("1000");
    cy.get("#gdp").type("21439");
    cy.get("#gdpPerCapita").type("65000");
    cy.get("#internetUsagePercent").type("90");
    cy.get("#mortalityRate").type("5");
    cy.get("#lifeExpectancy").type("78");
    cy.get("#populationDensity").type("36");
    cy.get("#unemploymentPercent").type("3.5");

    // Submit the form
    cy.get("input[type='submit']").click();

    // Wait for API call
    cy.wait("@addRecord");

    // Check if success alert appears
    cy.on("window:alert", (text: any) => {
      expect(text).toBe("Data created successfully.");
    });
  });

  it("should show validation errors when required fields are empty", () => {
    cy.get("input[type='submit']").click();

    // Ensure a required field shows validation error
    cy.get("#countryCode:invalid").should("exist");
  });

  it("should show an error message if the API fails", () => {
    cy.intercept("POST", "http://localhost:8080/api/devIndicator", {
      statusCode: 500,
      body: { message: "Server error" },
    }).as("addRecordFail");

    cy.get("#countryCode").type("US");

    cy.get("input[type='submit']").click();

    // cy.wait("@addRecordFail");

    // Check if error alert appears
    cy.on("window:alert", (text: any) => {
      expect(text).toBe("Error connecting to the server.");
    });
  });
});
