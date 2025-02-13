<template>
  <!-- <div class="store-text">
  </div> -->
  <div class="create-container">
    <p class="store-text alert alert-primary" role="alert">{{ message }}</p>
    <h1>Create New Data</h1>
    <form @submit.prevent="submitData">
      <div class="form-row">
        <label
          >Country Name
          <input id="countryName" v-model="data.countryName" type="text" />
        </label>

        <label
          >Country Code
          <input
            id="countryCode"
            v-model="data.countryCode"
            required
            type="text"
          />
        </label>
      </div>

      <div class="form-row">
        <label
          >Region
          <input id="region" v-model="data.region" required type="text" />
        </label>

        <label
          >Income Group
          <input
            id="incomeGroup"
            v-model="data.incomeGroup"
            required
            type="text"
          />
        </label>
      </div>

      <div class="form-row">
        <label
          >Year
          <input id="year" v-model.number="data.year" required type="number" />
        </label>

        <label
          >Birth Rate
          <input
            id="birthRate"
            v-model.number="data.birthRate"
            required
            type="number"
            step="any"
          />
        </label>
      </div>

      <div class="form-row">
        <label
          >Death Rate
          <input
            id="deathRate"
            v-model.number="data.deathRate"
            required
            type="number"
            step="any"
          />
        </label>

        <label
          >Electric Power Consumption
          <input
            id="electricPowerConsumption"
            v-model.number="data.electricPowerConsumption"
            required
            type="number"
            step="any"
          />
        </label>
      </div>

      <div class="form-row">
        <label
          >GDP
          <input
            id="gdp"
            v-model.number="data.gdp"
            required
            type="number"
            step="any"
          />
        </label>

        <label
          >GDP Per Capita
          <input
            id="gdpPerCapita"
            v-model.number="data.gdpPerCapita"
            required
            type="number"
            step="any"
          />
        </label>
      </div>

      <div class="form-row">
        <label
          >Internet Usage Percent
          <input
            id="internetUsagePercent"
            v-model.number="data.internetUsagePercent"
            required
            type="number"
            step="any"
          />
        </label>

        <label
          >Mortality Rate
          <input
            id="mortalityRate"
            v-model.number="data.mortalityRate"
            required
            type="number"
            step="any"
          />
        </label>
      </div>

      <div class="form-row">
        <label
          >Life Expectancy
          <input
            id="lifeExpectancy"
            v-model.number="data.lifeExpectancy"
            required
            type="number"
            step="any"
          />
        </label>

        <label
          >Population Density
          <input
            id="populationDensity"
            v-model.number="data.populationDensity"
            required
            type="number"
            step="any"
          />
        </label>

        <label
          >Unemployment Percent
          <input
            id="unemploymentPercent"
            v-model.number="data.unemploymentPercent"
            required
            type="number"
            step="any"
          />
        </label>
      </div>

      <div class="buttons">
        <input class="btn btn-primary" type="submit" value="Submit" />
        <router-link to="/">
          <button class="btn btn-primary home-btn">Back</button>
        </router-link>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import { ref, defineComponent, computed } from "vue";
import { useStore } from "vuex";
import { Data } from "@/types";

export default defineComponent({
  name: "CreateData",
  setup() {
    // Initialize the data with typed ref
    const data = ref<Data>({
      countryName: "",
      countryCode: "",
      region: "",
      incomeGroup: "",
      year: null,
      birthRate: null,
      deathRate: null,
      electricPowerConsumption: null,
      gdp: null,
      gdpPerCapita: null,
      internetUsagePercent: null,
      mortalityRate: null,
      lifeExpectancy: null,
      populationDensity: null,
      unemploymentPercent: null,
    });

    const store = useStore();
    const message = computed(() => store.getters.getMessage);

    // submit function
    const submitData = async () => {
      try {
        await store.dispatch("addRecord", data.value);
        alert("Data created successfully.");
      } catch (err) {
        console.error(err);
        alert("Error connecting to the server.");
      }
    };

    return { data, submitData, message };
  },
});
</script>

<style>
.create-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: Arial, Helvetica, sans-serif;
  margin: 0 auto;
  width: 80%;
  max-width: 700px;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 15px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin-top: 20px;
}

.create-container h1 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
  color: #333;
}

.form-row label {
  margin-bottom: 1rem;
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  font-size: 0.9rem;
  text-align: center;
}

.form-row input {
  margin-bottom: 1rem;
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
  border: none;
  border-bottom: 2px solid #ccc;
  text-align: center;
  outline: none;
  transition: border-color 0.3s ease-in-out;
}

.form-row input:focus {
  border-bottom: 2px solid #007bff;
}
.home-btn {
  margin-left: 20px;
}
</style>
