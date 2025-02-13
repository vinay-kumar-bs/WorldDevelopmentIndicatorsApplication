import { mount } from "@vue/test-utils";
import UpdateData from "@/views/UpdateData.vue"; // Adjust the path if needed
import { createStore } from "vuex";
import { createRouter, createWebHistory } from "vue-router";
import { defineComponent } from "vue";

describe("Update.vue", () => {
  let store: any;
  let actions: any;
  let getters: any;
  let state: any;
  let router: any;

  beforeEach(() => {
    actions = {
      updateRecord: jest.fn(),
    };

    getters = {
      getMessage: () => "Update the data below.",
    };

    state = {
      countryRecord: {
        countryName: "USA",
        countryCode: "US",
        region: "North America",
        incomeGroup: "High income",
        year: 2023,
        birthRate: 10.5,
        deathRate: 8.2,
        electricPowerConsumption: 5000,
        gdp: 20000,
        gdpPerCapita: 60000,
        internetUsagePercent: 90.5,
        mortalityRate: 5.2,
        lifeExpectancy: 78.9,
        populationDensity: 35.4,
        unemploymentPercent: 4.5,
      },
    };

    store = createStore({
      state,
      actions,
      getters,
    });

    router = createRouter({
      history: createWebHistory(),
      routes: [
        {
          path: "/display",
          name: "DisplayData",
          component: defineComponent({}),
        },
      ],
    });
  });

  it("renders correctly and displays store message", () => {
    const wrapper = mount(UpdateData, {
      global: {
        plugins: [store, router],
      },
    });

    expect(wrapper.find("h1").text()).toBe("Update Data");
    expect(wrapper.find(".store-text").text()).toBe("Update the data below.");
  });

  it("populates form fields from Vuex store", () => {
    const wrapper = mount(UpdateData, {
      global: {
        plugins: [store, router],
      },
    });

    expect(
      (wrapper.find("input[type='text']").element as HTMLInputElement).value
    ).toBe("USA");
    expect(
      (wrapper.find("input[type='number']").element as HTMLInputElement).value
    ).toBe("2023");
  });

  it("dispatches updateRecord action on form submit", async () => {
    const wrapper = mount(UpdateData, {
      global: {
        plugins: [store, router],
      },
    });

    window.alert = jest.fn();

    await wrapper.find("form").trigger("submit.prevent");

    expect(actions.updateRecord).toHaveBeenCalledWith(
      expect.anything(),
      state.countryRecord
    );
  });
});
