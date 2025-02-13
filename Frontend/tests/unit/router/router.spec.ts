import {
  createRouter,
  createWebHistory,
  RouteRecordRaw,
  Router,
} from "vue-router";
import { mount } from "@vue/test-utils";
import routerConfig from "@/router";
import HomeView from "@/views/HomeView.vue";
import { createStore } from "vuex";

describe("Vue Router Configuration", () => {
  let router: Router; // Explicitly define the router type

  const store = createStore({
    state: {
      message: "We have 200 country records.",
    },
    getters: {
      getMessage: (state) => state.message,
    },
  });

  beforeEach(() => {
    router = createRouter({
      history: createWebHistory(),
      routes: routerConfig.options.routes as RouteRecordRaw[],
    });
  });

  it("should contain the correct routes", () => {
    const routeNames = router.getRoutes().map((route) => route.name);
    expect(routeNames).toContain("home");
    expect(routeNames).toContain("navigationbar");
    expect(routeNames).toContain("CreateData");
    expect(routeNames).toContain("RetrieveData");
    expect(routeNames).toContain("UpdateData");
    expect(routeNames).toContain("DeleteData");
    expect(routeNames).toContain("ChartsView");
    expect(routeNames).toContain("DownloadData");
  });

  it("should navigate to HomeView correctly", async () => {
    router.push("/");
    await router.isReady();
    expect(router.currentRoute.value.name).toBe("home");
  });

  it("should navigate to CreateData correctly", async () => {
    router.push("/create");
    await router.isReady();
    expect(router.currentRoute.value.name).toBe("CreateData");
  });

  it("should navigate to RetrieveData correctly", async () => {
    router.push("/retrieve");
    await router.isReady();
    expect(router.currentRoute.value.name).toBe("RetrieveData");
  });

  it("should navigate to DisplayData correctly", async () => {
    router.push("/display");
    await router.isReady();
    expect(router.currentRoute.value.name).toBe("DisplayData");
  });

  it("should navigate to UpdateData correctly", async () => {
    router.push("/update");
    await router.isReady();
    expect(router.currentRoute.value.name).toBe("UpdateData");
  });

  it("should navigate to DeleteData correctly", async () => {
    router.push("/delete");
    await router.isReady();
    expect(router.currentRoute.value.name).toBe("DeleteData");
  });

  it("should navigate to ChartsView and load LineChart as a child route", async () => {
    router.push("/charts");
    await router.isReady();
    expect(router.currentRoute.value.name).toBe("LineChart");
  });

  it("should navigate to DownloadData correctly", async () => {
    router.push("/download");
    await router.isReady();
    expect(router.currentRoute.value.name).toBe("DownloadData");
  });

  it("should return 404 for an unknown route", async () => {
    router.push("/unknown");
    await router.isReady();
    expect(router.currentRoute.value.name).toBe("NotFound");
  });

  it("should mount a component and render it when routed", async () => {
    const wrapper = mount(HomeView, {
      global: { plugins: [router, store] },
    });

    router.push("/");
    await router.isReady();
    expect(wrapper.exists()).toBe(true);
  });
});
