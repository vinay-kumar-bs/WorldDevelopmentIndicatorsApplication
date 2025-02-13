import { mount, flushPromises } from "@vue/test-utils";
import DeleteData from "@/views/DeleteData.vue";
import { createStore } from "vuex";

describe("DeleteData.vue", () => {
  let store: any;
  let actions: any;
  let getters: any;
  let wrapper: any;

  beforeEach(() => {
    actions = {
      deleteRecord: jest.fn(),
    };

    getters = {
      getMessage: () => "Record deleted successfully.",
      getRecord: () => ({
        countryName: "India",
        countryCode: "IND",
        region: "Middle asia",
        incomeGroup: "High income",
        year: 2020,
        birthRate: 56.598,
        deathRate: 5.943,
        electricPowerConsumption: 980.384,
        gdp: 83792000,
        gdpPerCapita: 870.4984,
        internetUsagePercent: 78.487,
        mortalityRate: 4.374,
        lifeExpectancy: 75.4983,
        populationDensity: 897.45,
        unemploymentPercent: 56.4379,
      }),
    };

    store = createStore({
      actions,
      getters,
    });

    wrapper = mount(DeleteData, {
      global: {
        plugins: [store],
      },
    });
  });

  it("renders the component properly", () => {
    expect(wrapper.exists()).toBe(true);
  });

  it("displays the correct deletion data", () => {
    expect(wrapper.text()).toContain("India");
    expect(wrapper.text()).toContain("2020");
  });

  it("calls deleteRecord action when delete button is clicked", async () => {
    window.alert = jest.fn(); // Mock window alert
    await wrapper.find(".btn-delete").trigger("click");
    await flushPromises();

    expect(actions.deleteRecord).toHaveBeenCalledWith(expect.anything(), {
      countryName: "India",
      year: 2020,
    });
    expect(window.alert).toHaveBeenCalledWith("Data deleted successfully.");
  });

  it("displays the message from the store", () => {
    expect(wrapper.text()).toContain("Record deleted successfully.");
  });
});
