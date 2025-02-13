<template>
  <div class="retrieve-container">
    <p class="store-text alert alert-primary" role="alert">{{ message }}</p>
    <h1>Retrieve Data</h1>
    <form @submit.prevent="emitFetchData">
      <!-- Country Dropdown -->
      <div class="form-drop">
        <label>
          Select Country <br />
          <select v-model="selectedCountry" required>
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

      <!-- Year Dropdown -->
      <div class="form-drop">
        <label>
          Select Year <br />
          <select v-model="selectedYear" required>
            <option v-for="year in years" :key="year" :value="year">
              {{ year }}
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
</template>

<script lang="ts">
import { ref, onMounted, defineComponent, computed } from "vue";
import axios from "axios";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "RetrieveData",
  setup() {
    const countries = ref<string[]>([]);
    const years = ref<number[]>([]);
    const selectedCountry = ref<string>("");
    const selectedYear = ref<number | null>(null);

    const API_URL = process.env.VUE_APP_API_BASE_URL;

    const fetchDropDownData = async (): Promise<void> => {
      try {
        const [countryResponse, yearResponse] = await Promise.all([
          axios.get<string[]>(`${API_URL}/countries`),
          axios.get<number[]>(`${API_URL}/years`),
        ]);
        countries.value = countryResponse.data;
        years.value = yearResponse.data;
      } catch (err) {
        console.error("Error fetching dropdown data.", err);
      }
    };

    const store = useStore();
    const message = computed(() => store.getters.getMessage);
    const router = useRouter();

    const fetchData = async (countryName: string, year: number) => {
      await store.dispatch("fetchRecord", { countryName, year });

      router.push({ name: "DisplayData" });
    };

    const emitFetchData = () => {
      if (selectedCountry.value && selectedYear.value !== null) {
        fetchData(selectedCountry.value, selectedYear.value);
      } else {
        console.warn("Please select both country and year.");
      }
    };
    onMounted(fetchDropDownData);

    return {
      countries,
      years,
      selectedCountry,
      selectedYear,
      emitFetchData,
      message,
    };
  },
});
</script>

<style scoped>
.retrieve-container {
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

.retrieve-container h1 {
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
</style>
