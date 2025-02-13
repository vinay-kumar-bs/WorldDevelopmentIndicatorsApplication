import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import HomeView from "../views/HomeView.vue";
import NavigationBar from "../views/NavigationBar.vue";
import CreateData from "../views/CreateData.vue";
import DataManager from "../components/DataManager.vue";
import DisplayData from "../views/DisplayData.vue";
import RetrieveData from "../views/RetrieveData.vue";
import UpdateData from "@/views/UpdateData.vue";
import DeleteData from "@/views/DeleteData.vue";
import ChartsView from "@/views/ChartsView.vue";
import LineChart from "@/components/LineChart.vue";
import DownloadData from "@/views/DownloadData.vue";
import NotFound from "@/views/NotFound.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/Navigation",
    name: "navigationbar",
    component: NavigationBar,
  },
  {
    path: "/create",
    name: "CreateData",
    component: CreateData,
  },
  {
    path: "/retrieve",
    name: "RetrieveData",
    component: RetrieveData,
  },
  {
    path: "/display",
    name: "DisplayData",
    component: DisplayData,
  },
  {
    path: "/update",
    name: "UpdateData",
    component: UpdateData,
  },
  {
    path: "/delete",
    name: "DeleteData",
    component: DeleteData,
  },
  {
    path: "/charts",
    name: "ChartsView",
    component: ChartsView,
    children: [
      {
        path: "",
        name: "LineChart",
        component: LineChart,
      },
    ],
  },
  {
    path: "/download",
    name: "DownloadData",
    component: DownloadData,
  },
  { path: "/:pathMatch(.*)*", name: "NotFound", component: NotFound },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
