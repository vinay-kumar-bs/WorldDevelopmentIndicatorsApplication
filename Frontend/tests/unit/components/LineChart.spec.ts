import { shallowMount } from "@vue/test-utils";
import LineChart from "@/components/LineChart.vue";
import { Chart } from "chart.js/auto";
import "jest-canvas-mock";

// Mock Chart.js constructor and methods
jest.mock("chart.js/auto", () => {
  return {
    Chart: jest.fn().mockImplementation(() => ({
      destroy: jest.fn(),
      update: jest.fn(),
    })),
  };
});

describe("LineChart.vue", () => {
  let props: any;

  beforeEach(() => {
    props = {
      labels: [2000, 2001, 2002],
      data: [3548462, 2762882, 87362727],
      attribute: "GDP",
    };
    jest.clearAllMocks(); // Clears previous mock calls
  });

  it("renders a canvas element", () => {
    const wrapper = shallowMount(LineChart, { props });
    expect(wrapper.find("canvas").exists()).toBe(true);
  });

  it("creates a Chart instance on mount", () => {
    shallowMount(LineChart, { props });
    expect(Chart).toHaveBeenCalledTimes(1); // Ensures Chart.js initializes
  });

  it("updates the chart when props change", async () => {
    const wrapper = shallowMount(LineChart, { props });

    await wrapper.setProps({
      labels: [2000, 2001, 2002, 2003],
      data: [377686, 28746736, 23674628, 2680948],
    });

    expect(Chart).toHaveBeenCalledTimes(2); // Ensures reactivity
  });
});
