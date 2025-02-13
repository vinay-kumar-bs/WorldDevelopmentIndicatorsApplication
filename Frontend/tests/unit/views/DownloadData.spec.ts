import { mount } from "@vue/test-utils";
import DownloadData from "@/views/DownloadData.vue"; // Update the path if necessary

describe("DownloadData.vue", () => {
  let wrapper: any;

  beforeEach(() => {
    wrapper = mount(DownloadData);
  });

  it("renders the download message", () => {
    expect(wrapper.text()).toContain(
      "Click here to download the data of countries."
    );
  });

  it("contains an anchor tag for downloading the file", () => {
    const link = wrapper.find("a");

    expect(link.exists()).toBe(true);
    expect(link.attributes("href")).toBe(
      "/WorldDevelopmentIndicatorData/WorldBank-Development-Indicators.csv"
    );
    expect(link.attributes("target")).toBe("_blank");
    expect(link.attributes("download")).toBe(
      "WorldBank-Development-Indicators.csv"
    );
  });
});
