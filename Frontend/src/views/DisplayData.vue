<template>
  <div class="display-container">
    <p class="store-text alert alert-primary" role="alert">{{ message }}</p>
    <h1>Data Display</h1>
    <div v-if="data">
      <table class="data-table">
        <thead>
          <tr>
            <th>Country Name</th>
            <th>Country Code</th>
            <th>Region</th>
            <th>Income Group</th>
            <th>Year</th>
            <th>Birth Rate</th>
            <th>Death Rate</th>
            <th>Electric Power Consumption</th>
            <th>GDP</th>
            <th>GDP Per Capita</th>
            <th>Internet Usage Percent</th>
            <th>Mortality Rate</th>
            <th>Life Expectancy</th>
            <th>Population Density</th>
            <th>Unemployment Percent</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>{{ data.countryName }}</td>
            <td>{{ data.countryCode }}</td>
            <td>{{ data.region }}</td>
            <td>{{ data.incomeGroup }}</td>
            <td>{{ data.year }}</td>
            <td>{{ data.birthRate }}</td>
            <td>{{ data.deathRate }}</td>
            <td>{{ data.electricPowerConsumption }}</td>
            <td>{{ data.gdp }}</td>
            <td>{{ data.gdpPerCapita }}</td>
            <td>{{ data.internetUsagePercent }}</td>
            <td>{{ data.mortalityRate }}</td>
            <td>{{ data.lifeExpectancy }}</td>
            <td>{{ data.populationDensity }}</td>
            <td>{{ data.unemploymentPercent }}</td>
          </tr>
        </tbody>
      </table>
      <button @click="navigateToUpdate" class="btn btn-primary">Update</button>
      <button @click="navigateToDelete" class="btn btn-danger">Delete</button>
      <router-link to="/retrieve">
        <button class="btn btn-primary home-btn">Back</button>
      </router-link>
    </div>
    <div v-else>
      <p>No data found for the selected country and year.</p>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "DisplayData",
  // emits: ["update-data", "delete-data"], //define event which need to be emitted
  setup() {
    const store = useStore();
    const message = computed(() => store.getters.getMessage);
    const router = useRouter();

    const data = computed(() => store.state.countryRecord);

    onMounted(() => {
      if (!data.value || Object.keys(data.value).length === 0) {
        store.dispatch("fetchRecord");
      }
    });

    const navigateToUpdate = () => {
      router.push({ name: "UpdateData" });
    };

    const navigateToDelete = () => {
      router.push({ name: "DeleteData" });
    };

    return {
      navigateToUpdate,
      navigateToDelete,
      message,
      data,
    };
  },
});
</script>

<style>
.display-container {
  padding: 20px;
  max-width: fit-content;
  margin: 0 auto;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
  margin-top: 20px;
}

.display-container h1 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

table th,
table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}
.btn-primary {
  margin-right: 10px;
  margin-left: 10px;
}
.btn-danger {
  margin-left: 10px;
  /* margin-right: 10px; */
}
</style>
