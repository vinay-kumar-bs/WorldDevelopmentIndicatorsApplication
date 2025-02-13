<template>
  <div class="delete-container">
    <p class="store-text alert alert-primary" role="alert">{{ message }}</p>
    <h1>Delete Record</h1>
    <p>Are you sure you want to delete the record for:</p>
    <ul>
      <li><strong>Country Name :</strong> {{ deletionData.countryName }}</li>
      <li><strong>Year :</strong> {{ deletionData.year }}</li>
    </ul>
    <div class="buttons">
      <button @click="submitDelete" class="btn-delete btn btn-danger">
        Confirm Delete
      </button>
      <router-link to="/display">
        <button class="btn btn-primary home-btn">Back</button>
      </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { ref, defineComponent, PropType, computed } from "vue";
import { useStore } from "vuex";

export default defineComponent({
  name: "DeleteData",
  // emits: ["submit-delete"], //define event to emit
  setup() {
    const store = useStore();
    const message = computed(() => store.getters.getMessage);

    const deletionData = computed(() => store.getters.getRecord);

    const submitDelete = async () => {
      await store.dispatch("deleteRecord", {
        countryName: deletionData.value.countryName,
        year: deletionData.value.year,
      });
      alert("Data deleted successfully.");
    };
    return { deletionData, submitDelete, message };
  },
});
</script>

<style>
.delete-container ul {
  list-style-type: none;
  padding: 0;
}
.delete-container li {
  margin-bottom: 10px;
}

.delete-container {
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

.delete-container h1 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
  color: #333;
  /* text-shadow: 0 10px 7px rgba(0, 0, 0, 0.4), 0 -10px 1px #fff; */
}
</style>
