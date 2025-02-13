describe("DisplayData Component", () => {
  beforeEach(() => {
    cy.visit("http://localhost:8081/display");
  });

  it("Displays the data table", () => {
    cy.get(".data-table").should("be.visible");
    cy.get("tbody tr").should("exist");
  });

  it("Navigates to Update page", () => {
    cy.get("button.btn-primary")
      .contains("Update")
      .should("be.visible")
      .click();
    cy.url().should("include", "/update");
  });

  it("Navigates to Delete page", () => {
    cy.get("button.btn-danger").contains("Delete").should("be.visible").click();
    cy.url().should("include", "/delete");
  });

  it("Back button navigates to Retrieve page", () => {
    cy.get("button.home-btn").should("be.visible").click();
    cy.url().should("include", "/retrieve");
  });
});
