import axios from "axios";
import { Data } from "@/types";

// Base API URL
const API_URL = "http://localhost:8080/api/devIndicator";
export const fetchCountriesCount = async () => {
  try {
    const countryCountResponse = await axios.get(`${API_URL}/countries`);
    return countryCountResponse.data.length;
  } catch (error) {
    console.log("Error fetching the country count:", error);
  }
};

export const fetchRecord = async (countryName: string, year: number) => {
  try {
    const response = await axios.get(
      `${API_URL}?countryName=${countryName}&year=${year}`
    );
    return response.data;
  } catch (error) {
    console.log("Error fetching records:", error);
  }
};

export const addRecord = async (data: Data) => {
  try {
    const response = await axios.post(API_URL, data);
    return response.data;
  } catch (error) {
    console.log("Error adding record:", error);
  }
};

export const updateRecord = async (updatedData: Data) => {
  try {
    const response = await axios.put(API_URL, updatedData);
    return updatedData; // Return updated record
  } catch (error) {
    console.log("Error updating record:", error);
  }
};

export const deleteRecord = async (countryName: string, year: number) => {
  try {
    await axios.delete(`${API_URL}?countryName=${countryName}&year=${year}`);
    return { countryName, year };
  } catch (error) {
    console.log("Error deleting record:", error);
  }
};
