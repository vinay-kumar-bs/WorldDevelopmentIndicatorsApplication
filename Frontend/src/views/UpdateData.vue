<template>
  <div class="update-container">
    <p class="store-text alert alert-primary" role="alert">{{ message }}</p>
    <h1>Update Data</h1>
    <form @submit.prevent="submitUpdate">
      <div class="form-row">
        <label>
          Country Name:
          <input v-model="localFormData.countryName" type="text" />
        </label>
        <label>
          Country Code:
          <input v-model="localFormData.countryCode" required type="text" />
        </label>
      </div>
      <div class="form-row">
        <label>
          Region:
          <input v-model="localFormData.region" type="text" />
        </label>
        <label>
          Income Group:
          <input v-model="localFormData.incomeGroup" required type="text" />
        </label>
      </div>
      <div class="form-row">
        <label>
          Year:
          <input v-model="localFormData.year" type="number" />
        </label>
        <label>
          Birth Rate:
          <input
            v-model="localFormData.birthRate"
            required
            type="number"
            step="any"
          />
        </label>
        <div class="form-row">
          <label>
            Death Rate:
            <input v-model="localFormData.deathRate" type="number" step="any" />
          </label>
          <label>
            Electric Power Consumption:
            <input
              v-model="localFormData.electricPowerConsumption"
              required
              type="number"
              step="any"
            />
          </label>
        </div>
        <div class="form-row">
          <label>
            GDP:
            <input v-model="localFormData.gdp" type="number" step="any" />
          </label>
          <label>
            GDP Per Capita:
            <input
              v-model="localFormData.gdpPerCapita"
              required
              type="number"
              step="any"
            />
          </label>
        </div>
        <div class="form-row">
          <label>
            Internet Usage Percent:
            <input
              v-model="localFormData.internetUsagePercent"
              type="number"
              step="any"
            />
          </label>
          <label>
            Mortality Rate:
            <input
              v-model="localFormData.mortalityRate"
              required
              type="number"
              step="any"
            />
          </label>
        </div>
        <div class="form-row">
          <label>
            Life Expectancy:
            <input
              v-model="localFormData.lifeExpectancy"
              type="number"
              step="any"
            />
          </label>
          <label>
            Population Density:
            <input
              v-model="localFormData.populationDensity"
              required
              type="number"
              step="any"
            />
          </label>
        </div>
        <div class="form-row">
          <label>
            Unemployment Percent:
            <input
              v-model="localFormData.unemploymentPercent"
              type="number"
              step="any"
            />
          </label>
        </div>
      </div>

      <div class="buttons">
        <button type="submit" class="btn btn-primary submit-btn">Submit</button>
        <router-link to="/display">
          <button class="btn btn-primary home-btn">Back</button>
        </router-link>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType, computed } from "vue";
import { useStore } from "vuex";

export default defineComponent({
  name: "UpdateData",
  setup() {
    const localFormData = computed(() => {
      return store.state.countryRecord;
    });

    const submitUpdate = async () => {
      // emit("submit-update", localFormData.value);
      await store.dispatch("updateRecord", localFormData.value);
      alert("Data updated successfully.");
    };

    const store = useStore();
    const message = computed(() => store.getters.getMessage);

    return { localFormData, submitUpdate, message };
  },
});
</script>

<style scoped>
.update-container {
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

.update-container h1 {
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

.submit-btn {
  margin-right: 10px;
}

.back-btn {
  margin-left: 10px;
}
</style>
