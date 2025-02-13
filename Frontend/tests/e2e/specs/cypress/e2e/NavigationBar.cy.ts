describe("NavigationBar Component", () => {
  beforeEach(() => {
    cy.visit("http://localhost:8081");
  });

  it("Displays the navigation bar", () => {
    cy.get(".navbar").should("be.visible");
  });

  it("Navigates to Home page", () => {
    cy.get(".nav-link").contains("Home").click();
    cy.url().should("eq", "http://localhost:8081/");
  });

  it("Opens the Data dropdown and navigates to Create page", () => {
    cy.get(".nav-link").contains("Data").click();
    cy.get(".dropdown-menu").should("be.visible");
    cy.get(".nav-link").contains("Create").click();
    cy.url().should("include", "/create");
  });

  it("Opens the Data dropdown and navigates to Retrieve page", () => {
    cy.get(".nav-link").contains("Data").click();
    cy.get(".dropdown-menu").should("be.visible");
    cy.get(".nav-link").contains("Retrieve").click();
    cy.url().should("include", "/retrieve");
  });

  it("Navigates to Charts page", () => {
    cy.get(".nav-link").contains("Charts").click();
    cy.url().should("include", "/charts");
  });

  it("Navigates to Download page", () => {
    cy.get(".nav-link").contains("Download").click();
    cy.url().should("include", "/download");
  });
});
