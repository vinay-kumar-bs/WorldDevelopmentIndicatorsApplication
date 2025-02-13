<!-- <template>
  <div>
    Conditional Rendering of Components
    <RetrieveData
      v-if="currentView === 'retrieve'"
      @fetch-data="handleFetchData"
    />
    <DisplayData
      v-if="currentView === 'display'"
      :data="retrievedData"
      @update-data="openUpdateData"
      @delete-data="openDeleteData"
    />
    <UpdateData
      v-if="currentView === 'update' && dataToUpdate"
      :data="dataToUpdate"
      @submit-update="submitUpdate"
    />
    <DeleteData
      v-if="currentView === 'delete' && dataToDelete"
      :data="dataToDelete"
      @submit-delete="submitDelete"
    />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import axios from "axios";
import RetrieveData from "../views/RetrieveData.vue";
import DisplayData from "../views/DisplayData.vue";
import UpdateData from "../views/UpdateData.vue";
import DeleteData from "../views/DeleteData.vue";
import { Data } from "../types";

export default defineComponent({
  name: "DataManager",
  components: {
    RetrieveData,
    DisplayData,
    UpdateData,
    DeleteData,
  },
  setup() {
    const currentView = ref("retrieve"); // Track the current view state
    const retrievedData = ref<Data | null>(null);
    const dataToUpdate = ref<Data | null>(null);
    const dataToDelete = ref<Data | null>(null);

    // Handle the data fetch and switch to the display view
    const handleFetchData = async (countryName: string, year: number) => {
      try {
        // Fetch data based on country and year
        const response = await axios.get(
          `http://localhost:8080/api/devIndicator?countryName=${countryName}&year=${year}`
        );
        // Set the retrieved data
        retrievedData.value = response.data;

        // Switch to the display view
        currentView.value = "display";
      } catch (error) {
        console.error("Error fetching data:", error);
        alert("Failed to retrieve data. Please try again.");
      }
    };

    const openUpdateData = (retrievedData: Data) => {
      dataToUpdate.value = retrievedData; // Store the data to be updated
      currentView.value = "update"; // Show the UpdateData component
    };

    const submitUpdate = (updatedData: Data) => {
      axios
        .put("http://localhost:8080/api/devIndicator", updatedData)
        .then(() => {
          alert("Data updated successfully.");
        })
        .catch((error) => {
          console.log("Error updating the data:", error);
          alert("Failed to update data");
        });
    };

    const openDeleteData = (retrievedData: Data) => {
      dataToDelete.value = retrievedData;
      currentView.value = "delete"; //show the deleteData component
    };

    const submitDelete = (DeletionData: Data) => {
      axios
        .delete(
          `http://localhost:8080/api/devIndicator?countryName=${DeletionData.countryName}&year=${DeletionData.year}`
        )
        .then(() => {
          alert("Data deleted successfully.");
        })
        .catch((error) => {
          console.log("Error deleting the data:", error);
          alert("Failed to delete data.");
        });
    };

    return {
      currentView,
      retrievedData,
      handleFetchData,
      dataToUpdate,
      openUpdateData,
      submitUpdate,
      dataToDelete,
      openDeleteData,
      submitDelete,
    };
  },
});
</script> -->
