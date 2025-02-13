declare namespace Cypress {
  interface Chainable {
    intercept: typeof cy.intercept;
    on: typeof cy.on;
  }
}
