import { mount, flushPromises } from "@vue/test-utils";
import CreateData from "@/views/CreateData.vue";
import { createStore } from "vuex";

// Mock Vuex store
const actions = {
  addRecord: jest.fn(() => Promise.resolve()),
};

const store = createStore({
  actions,
  getters: {
    getMessage: () => "Test message",
  },
});

describe("CreateData.vue", () => {
  let wrapper: any;

  beforeEach(() => {
    jest.clearAllMocks();
    wrapper = mount(CreateData, {
      global: {
        plugins: [store],
      },
    });
  });

  it("renders the component properly", () => {
    expect(wrapper.exists()).toBe(true);
    expect(wrapper.find("h1").text()).toBe("Create New Data");
  });

  it("binds input values to data model", async () => {
    const countryNameInput = wrapper.find("#countryName");
    await countryNameInput.setValue("India");
    expect(wrapper.vm.data.countryName).toBe("India");
  });

  it("dispatches addRecord action when form is submitted", async () => {
    wrapper.vm.data = {
      countryName: "India",
      countryCode: "IND",
      region: "Middle asia",
      incomeGroup: "High income",
      year: 2022,
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
    };

    await wrapper.find("form").trigger("submit.prevent");
    await flushPromises();

    expect(actions.addRecord).toHaveBeenCalledWith(
      expect.any(Object),
      wrapper.vm.data
    );
  });

  it("displays an alert when form is submitted successfully", async () => {
    window.alert = jest.fn();

    await wrapper.find("form").trigger("submit.prevent");
    await flushPromises();

    expect(window.alert).toHaveBeenCalledWith("Data created successfully.");
  });

  it("handles errors when form submission fails", async () => {
    actions.addRecord.mockRejectedValueOnce(new Error("API Error"));
    window.alert = jest.fn();
    console.error = jest.fn();

    await wrapper.find("form").trigger("submit.prevent");
    await flushPromises();

    expect(window.alert).toHaveBeenCalledWith(
      "Error connecting to the server."
    );
    expect(console.error).toHaveBeenCalled();
  });
});
