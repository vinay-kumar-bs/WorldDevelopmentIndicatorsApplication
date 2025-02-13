import { mount, flushPromises } from "@vue/test-utils";
import { createStore, Store } from "vuex";
import { createRouter, createWebHistory, Router } from "vue-router";
import DisplayData from "@/views/DisplayData.vue";
import { defineComponent } from "vue";

describe("DisplayData.vue", () => {
  let store: Store<any>;
  let actions: { fetchRecord: jest.Mock };
  let state: any;
  let router: Router;

  beforeEach(() => {
    actions = {
      fetchRecord: jest.fn(),
    };

    state = {
      countryRecord: {
        countryName: "Testland",
        countryCode: "TL",
        region: "Test Region",
        incomeGroup: "High",
        year: 2023,
        birthRate: 12.5,
        deathRate: 7.8,
        electricPowerConsumption: 1500.2,
        gdp: 3000000,
        gdpPerCapita: 45000,
        internetUsagePercent: 85.4,
        mortalityRate: 4.5,
        lifeExpectancy: 75.3,
        populationDensity: 200,
        unemploymentPercent: 5.2,
      },
    };

    store = createStore({
      state,
      actions,
      getters: {
        getMessage: () => "Data retrieved successfully!",
      },
    });

    router = createRouter({
      history: createWebHistory(),
      routes: [
        { path: "/update", name: "UpdateData", component: defineComponent({}) },
        { path: "/delete", name: "DeleteData", component: defineComponent({}) },
      ],
    });
  });

  it("renders data correctly when available", async () => {
    const wrapper = mount(DisplayData, {
      global: {
        plugins: [store, router],
      },
    });

    await flushPromises(); // Ensures Vue updates are processed

    expect(wrapper.text()).toContain("Data Display");
    expect(wrapper.text()).toContain("Testland");
    expect(wrapper.text()).toContain("High");
    expect(wrapper.find("table").exists()).toBe(true);
  });

  it('shows "No data found" message when no data is available', async () => {
    store.replaceState({ countryRecord: null });

    const wrapper = mount(DisplayData, {
      global: {
        plugins: [store, router],
      },
    });

    await flushPromises();

    expect(wrapper.text()).toContain(
      "No data found for the selected country and year."
    );
  });

  it("dispatches fetchRecord action when no data is found on mount", async () => {
    store.replaceState({ countryRecord: null });

    mount(DisplayData, {
      global: {
        plugins: [store, router],
      },
    });

    expect(actions.fetchRecord).toHaveBeenCalled();
  });

  it("navigates to UpdateData page when update button is clicked", async () => {
    const wrapper = mount(DisplayData, {
      global: {
        plugins: [store, router],
      },
    });

    await wrapper.find(".btn-primary").trigger("click");
    await flushPromises();

    expect(wrapper.vm.$router.currentRoute.value.name).toBe("UpdateData");
  });

  it("navigates to DeleteData page when delete button is clicked", async () => {
    const wrapper = mount(DisplayData, {
      global: {
        plugins: [store, router],
      },
    });

    await wrapper.find(".btn-danger").trigger("click");
    await flushPromises();

    expect(wrapper.vm.$router.currentRoute.value.name).toBe("DeleteData");
  });
});
