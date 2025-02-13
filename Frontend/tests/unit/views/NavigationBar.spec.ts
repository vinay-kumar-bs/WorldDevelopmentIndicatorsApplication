import { mount } from "@vue/test-utils";
import { createRouter, createWebHistory } from "vue-router";
import NavigationBar from "@/views/NavigationBar.vue"; // Adjust path if needed
import { defineComponent } from "vue";

describe("NavigationBar.vue", () => {
  let router: any;

  beforeEach(() => {
    router = createRouter({
      history: createWebHistory(),
      routes: [
        { path: "/", name: "Home", component: defineComponent({}) },
        { path: "/create", name: "Create", component: defineComponent({}) },
        { path: "/retrieve", name: "Retrieve", component: defineComponent({}) },
        { path: "/charts", name: "Charts", component: defineComponent({}) },
        { path: "/download", name: "Download", component: defineComponent({}) },
      ],
    });
  });

  it("renders the navbar correctly", () => {
    const wrapper = mount(NavigationBar, {
      global: {
        plugins: [router],
      },
    });

    expect(wrapper.find(".navbar").exists()).toBe(true);
  });

  it("contains all the expected navigation links", () => {
    const wrapper = mount(NavigationBar, {
      global: {
        plugins: [router],
      },
    });

    expect(wrapper.find('a.nav-link[href="/"]').text()).toBe("Home");
    expect(wrapper.find('a.nav-link[href="/charts"]').text()).toBe("Charts");
    expect(wrapper.find('a.nav-link[href="/download"]').text()).toBe(
      "Download"
    );
  });

  it("contains the dropdown with 'Create' and 'Retrieve' links", async () => {
    const wrapper = mount(NavigationBar, {
      global: {
        plugins: [router],
      },
    });

    expect(wrapper.find('a.nav-link[href="/create"]').text()).toBe("Create");
    expect(wrapper.find('a.nav-link[href="/retrieve"]').text()).toBe(
      "Retrieve"
    );
  });
});
