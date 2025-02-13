import { mount } from "@vue/test-utils";
import { createStore } from "vuex";
import HomeView from "@/views/HomeView.vue"; // Adjust path if needed

describe("HomeView.vue", () => {
  let store: any;

  beforeEach(() => {
    store = createStore({
      getters: {
        getMessage: () => "Currently we have 200 country data.",
      },
    });
  });

  it("renders the welcome message", () => {
    const wrapper = mount(HomeView, {
      global: {
        plugins: [store],
      },
    });

    expect(wrapper.find("h1").text()).toBe(
      "Welcome to the World Development Indicator"
    );
  });

  it("displays the store message inside the alert", () => {
    const wrapper = mount(HomeView, {
      global: {
        plugins: [store],
      },
    });

    expect(wrapper.find(".alert-primary").text()).toBe(
      "Currently we have 200 country data."
    );
  });

  it("renders the carousel with images", () => {
    const wrapper = mount(HomeView, {
      global: {
        plugins: [store],
      },
    });

    const carousel = wrapper.find("#carouselExampleInterval");
    expect(carousel.exists()).toBe(true);

    const images = carousel.findAll("img");
    expect(images.length).toBe(3); // Ensuring there are 3 images in the carousel
    expect(images[0].attributes("alt")).toBe("farm");
    expect(images[1].attributes("alt")).toBe("port");
    expect(images[2].attributes("alt")).toBe("city");
  });

  it("checks the footer contains copyright text", () => {
    const wrapper = mount(HomeView, {
      global: {
        plugins: [store],
      },
    });

    expect(wrapper.find(".footer-copy-right").text()).toBe(
      "© 2025 The World Bank Group, All Rights Reserved."
    );
  });
});
