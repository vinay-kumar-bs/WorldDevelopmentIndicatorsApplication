import { Store } from "vuex";
import { State } from "./store"; // Adjust the path to your Vuex State type

declare global {
  interface Window {
    __store__: Store<State>;
    Cypress?: boolean;
  }
}
