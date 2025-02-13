import { mount } from "@vue/test-utils";
import { createRouter, createWebHistory } from "vue-router";
import NotFound from "@/views/NotFound.vue"; // Adjust path if needed
import { defineComponent } from "vue";

describe("NotFound.vue", () => {
  let router: any;

  beforeEach(() => {
    router = createRouter({
      history: createWebHistory(),
      routes: [{ path: "/", name: "Home", component: defineComponent({}) }],
    });
  });

  it("renders the 404 page correctly", () => {
    const wrapper = mount(NotFound, {
      global: {
        plugins: [router],
      },
    });

    expect(wrapper.find("h1").text()).toBe("404 - Page Not Found");
    expect(wrapper.find("p").text()).toBe(
      "Oops! The page you are looking for does not exist."
    );
  });

  it("contains a 'Go Back Home' link that navigates to '/'", () => {
    const wrapper = mount(NotFound, {
      global: {
        plugins: [router],
      },
    });

    const homeLink = wrapper.find("a");
    expect(homeLink.exists()).toBe(true);
    expect(homeLink.text()).toBe("Go Back Home");
    expect(homeLink.attributes("href")).toBe("/");
  });
});
