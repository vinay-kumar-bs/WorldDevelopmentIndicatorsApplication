import { mount, flushPromises } from "@vue/test-utils";
import ChartsView from "@/views/ChartsView.vue";
import axios from "axios";

jest.mock("axios"); // Mock axios globally

describe("ChartsView.vue", () => {
  let wrapper: any;

  beforeEach(async () => {
    jest.clearAllMocks();

    // Mock axios before mounting
    (axios.get as jest.Mock).mockResolvedValueOnce({
      data: ["India", "USA", "Germany"],
    });

    wrapper = mount(ChartsView);

    await flushPromises(); // Wait for all promises to resolve after mounting
  });

  it("renders the component properly", () => {
    expect(wrapper.exists()).toBe(true);
  });

  it("fetches country list on mount", async () => {
    expect(axios.get).toHaveBeenCalledWith(
      "http://localhost:8080/api/devIndicator/countries"
    );
    await flushPromises();
    expect(wrapper.vm.countries).toEqual(["India", "USA", "Germany"]);
  });

  it("handles API error for country list", async () => {
    jest.clearAllMocks();
    const consoleErrorSpy = jest
      .spyOn(console, "error")
      .mockImplementation(() => {});

    (axios.get as jest.Mock).mockRejectedValueOnce(new Error("API Error"));

    wrapper = mount(ChartsView); // Re-mount with failed request

    await flushPromises();

    expect(wrapper.vm.countries).toEqual([]); // Should remain empty
    expect(consoleErrorSpy).toHaveBeenCalled();

    consoleErrorSpy.mockRestore();
  });

  it("updates selected country and attribute", async () => {
    wrapper.vm.selectedCountry = "India";
    wrapper.vm.selectedData = "gdp";

    await flushPromises();

    expect(wrapper.vm.selectedCountry).toBe("India");
    expect(wrapper.vm.selectedData).toBe("gdp");
  });

  it("fetches chart data when form is submitted", async () => {
    const mockChartData = [
      { year: 2000, gdp: 537473900 },
      { year: 2005, gdp: 652727600 },
      { year: 2010, gdp: 766378200 },
    ];

    wrapper.vm.selectedCountry = "India";
    wrapper.vm.selectedData = "gdp";

    (axios.get as jest.Mock).mockResolvedValueOnce({ data: mockChartData });

    await wrapper.find("form").trigger("submit.prevent");

    await flushPromises();

    expect(wrapper.vm.chartData).toEqual([537473900, 652727600, 766378200]);
    expect(wrapper.vm.years).toEqual([2000, 2005, 2010]);
    expect(axios.get).toHaveBeenCalledWith(
      "http://localhost:8080/api/devIndicator/country?countryName=India"
    );
  });

  it("handles API error when fetching chart data", async () => {
    const consoleErrorSpy = jest
      .spyOn(console, "error")
      .mockImplementation(() => {});

    wrapper.vm.selectedCountry = "India";
    wrapper.vm.selectedData = "gdp";

    (axios.get as jest.Mock).mockRejectedValueOnce(new Error("API Error"));

    await wrapper.find("form").trigger("submit.prevent");

    await flushPromises();

    expect(wrapper.vm.chartData).toEqual([]);
    expect(wrapper.vm.years).toEqual([]);
    expect(consoleErrorSpy).toHaveBeenCalled();

    consoleErrorSpy.mockRestore();
  });

  it("prevents fetching chart data if no selection is made", async () => {
    window.alert = jest.fn();

    await wrapper.find("form").trigger("submit.prevent");

    expect(window.alert).toHaveBeenCalledWith(
      "Please select both a country and an attribute."
    );
  });
});
