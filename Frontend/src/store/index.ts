import { createStore } from "vuex";
import { Data, RootState } from "@/types";
import * as dataService from "@/services/dataService";

const store = createStore<RootState>({
  state: {
    countryRecord: [],
    countriesCount: 0,
    message: "",
  },

  getters: {
    getMessage: (state) => state.message,
    getRecord: (state) => state.countryRecord,
    getCountriesCount: (state) => state.countriesCount,
  },

  mutations: {
    setCountriesCount(state, count: number) {
      state.countriesCount = count;
      state.message = `Currently we have ${count} countries data with us.`;
    },

    setDataToRecord(state, data) {
      state.countryRecord = data;
    },
  },

  actions: {
    async fetchRecord({ commit }, { countryName, year }) {
      try {
        const data = await dataService.fetchRecord(countryName, year);
        commit("setDataToRecord", data);
      } catch (err) {
        console.log("Error fetching data", err);
      }
    },

    async addRecord({ commit, dispatch }, record) {
      try {
        const data = await dataService.addRecord(record);
        await dispatch("fetchCountriesCount");
      } catch (err) {
        console.log("Error adding record", err);
      }
    },

    async updateRecord({ commit }, record) {
      try {
        await dataService.updateRecord(record);
      } catch (err) {
        console.log("Error updating the data", err);
      }
    },

    async deleteRecord({ commit, dispatch }, { countryName, year }) {
      try {
        await dataService.deleteRecord(countryName, year);

        await dispatch("fetchCountriesCount");
      } catch (err) {
        console.log("Error deleting record", err);
      }
    },

    async fetchCountriesCount({ commit }) {
      try {
        const count = await dataService.fetchCountriesCount();
        commit("setCountriesCount", count);
      } catch (err) {
        console.log(err);
      }
    },
  },

  modules: {},
});

export default store;
