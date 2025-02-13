import { mount } from "@vue/test-utils";
import RetrieveData from "@/views/RetrieveData.vue"; // Adjust the path if needed
import { createStore } from "vuex";
import { createRouter, createWebHistory } from "vue-router";
import axios from "axios";
import { defineComponent } from "vue";

// Mock axios
jest.mock("axios");
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe("Retrieve.vue", () => {
  let store: any;
  let router: any;
  let actions: any;
  let getters: any;

  beforeEach(() => {
    actions = {
      fetchRecord: jest.fn(),
    };

    getters = {
      getMessage: () => "Welcome to Retrieve Page!",
    };

    store = createStore({
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

  it("renders correctly", async () => {
    // Mock API responses
    mockedAxios.get
      .mockResolvedValueOnce({ data: ["USA", "India", "Germany"] }) // Countries API response
      .mockResolvedValueOnce({ data: [2020, 2021, 2022] }); // Years API response

    const wrapper = mount(RetrieveData, {
      global: {
        plugins: [store, router],
      },
    });

    // Wait for the API call to resolve
    await new Promise((resolve) => setTimeout(resolve, 0));

    expect(wrapper.find("h1").text()).toBe("Retrieve Data");
    expect(wrapper.find(".store-text").text()).toBe(
      "Welcome to Retrieve Page!"
    );
    expect(wrapper.findAll("option").length).toBe(6); // 3 countries + 3 years
  });

  it("selects country, year and submits the form", async () => {
    // Mock API responses
    mockedAxios.get
      .mockResolvedValueOnce({ data: ["USA", "India", "Germany"] }) // Countries API response
      .mockResolvedValueOnce({ data: [2020, 2021, 2022] }); // Years API response

    const wrapper = mount(RetrieveData, {
      global: {
        plugins: [store, router],
      },
    });

    await new Promise((resolve) => setTimeout(resolve, 0));

    // Select country
    await wrapper.find("select").setValue("USA");

    // Select year
    await wrapper.findAll("select")[1].setValue("2021");

    // Submit form
    await wrapper.find("form").trigger("submit.prevent");

    expect(actions.fetchRecord).toHaveBeenCalledWith(expect.anything(), {
      countryName: "USA",
      year: 2021,
    });
  });
});
