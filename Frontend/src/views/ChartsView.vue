<template>
  <div class="charts-container">
    <h1>Country Data Charts</h1>
    <form @submit.prevent="fetchChartData">
      <!-- Country Dropdown -->
      <div class="form-drop">
        <label>
          Select Country <br />
          <select v-model="selectedCountry" required>
            <!-- <option value="" disabled>select one</option> -->
            <option
              v-for="country in countries"
              :key="country"
              :value="country"
            >
              {{ country }}
            </option>
          </select>
        </label>
      </div>

      <!-- column Dropdown -->
      <div class="form-drop">
        <label>
          Select Data <br />
          <select v-model="selectedData" required>
            <!-- <option value="" disabled>select one</option> -->
            <option
              v-for="attribute in attributes"
              :key="attribute"
              :value="attribute"
            >
              {{ attribute }}
            </option>
          </select>
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

  <!-- chart section for loading the chart -->
  <div v-if="chartData.length" class="chart-heading">
    <h3>Line chart for {{ selectedData }} in {{ selectedCountry }}</h3>
    <LineChart :labels="years" :data="chartData" :attribute="selectedData" />
  </div>
</template>

<script lang="ts">
import { ref, onMounted, defineComponent } from "vue";
import axios from "axios";
import LineChart from "@/components/LineChart.vue";

export default defineComponent({
  name: "ChartsView",
  components: {
    LineChart,
  },

  setup() {
    const countries = ref<string[]>([]);
    const selectedCountry = ref<string>("");
    const selectedData = ref<string>("");
    const attributes = ref<string[]>([
      "birthRate",
      "deathRate",
      "electricPowerConsumption",
      "gdp",
      "gdpPerCapita",
      "internetUsagePercent",
      "mortalityRate",
      "lifeExpectancy",
      "populationDensity",
      "unemploymentPercent",
    ]);
    const chartData = ref<number[]>([]); //for y axis
    const years = ref<number[]>([]); //for x axis

    onMounted(async (): Promise<void> => {
      try {
        const [countryResponse] = await Promise.all([
          axios.get<string[]>(
            "http://localhost:8080/api/devIndicator/countries"
          ),
        ]);
        countries.value = countryResponse.data;
      } catch (err) {
        console.error("Error fetching dropdown data.", err);
      }
    });

    const fetchChartData = async () => {
      if (!selectedCountry.value || !selectedData.value) {
        alert("Please select both a country and an attribute.");
        return;
      }
      try {
        const response = await axios.get(
          `http://localhost:8080/api/devIndicator/country?countryName=${selectedCountry.value}`
        );

        const sortedData = response.data.sort(
          (a: any, b: any) => a.year - b.year
        );

        chartData.value = sortedData.map((row: any) => row[selectedData.value]);
        years.value = response.data.map((row: any) => row.year);
      } catch (error) {
        console.error("Error fetching chart data:", error);
        alert("Failed to fetch chart data.");
      }
    };

    return {
      countries,
      selectedCountry,
      selectedData,
      attributes,
      chartData,
      years,
      fetchChartData,
    };
  },
});
</script>

<style>
.charts-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: "Arial", sans-serif;
  margin: 0 auto;
  width: 80%;
  max-width: 700px;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 15px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin-top: 20px;
}

.charts-container h1 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
  color: #333;
  /* text-shadow: 0 10px 7px rgba(0, 0, 0, 0.4), 0 -10px 1px #fff; */
}

form {
  display: flex;
  flex-direction: column;
}

.form-drop {
  margin-bottom: 5px;
}

.form-drop label {
  flex: 1;
  font-size: 1rem;
  font-weight: bold;
  color: #555;
  text-align: center;
  margin-right: 15px;
  margin-bottom: 8px;
}

.form-drop select {
  flex: 2;
  padding: 10px 15px;
  font-size: 1rem;
  color: #333;
  background-color: transparent;
  /* border: none; */
  border-bottom: 2px solid #ccc;
  outline: none;
  text-align: center;
  transition: border-color 0.3s ease-in-out;
  margin-bottom: 5px;
  border-radius: 8px;
}

.form-drop select:focus {
  border-bottom: 2px solid #007bff;
}
.home-btn {
  margin-left: 20px;
}
.chart-heading {
  margin-top: 20px;
}
</style>
