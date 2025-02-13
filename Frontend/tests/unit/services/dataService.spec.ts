import axios from "axios";
import MockAdapter from "axios-mock-adapter";
import {
  fetchCountriesCount,
  fetchRecord,
  addRecord,
  updateRecord,
  deleteRecord,
} from "@/services/dataService";
import { Data } from "@/types";

const API_URL = "http://localhost:8080/api/devIndicator";

describe("dataService.ts", () => {
  let mock: MockAdapter;

  beforeEach(() => {
    mock = new MockAdapter(axios);
  });

  afterEach(() => {
    mock.restore();
  });

  it("fetchCountriesCount should return correct count", async () => {
    mock.onGet(`${API_URL}/countries`).reply(200, [{}, {}, {}]); // 3 countries

    const count = await fetchCountriesCount();
    expect(count).toBe(3);
  });

  it("fetchRecord should return the expected data", async () => {
    const mockData = {
      countryName: "India",
      countryCode: "IND",
      region: "Middle Asia",
      incomeGroup: "Middle income",
      year: 2020,
      birthRate: 76.58,
      deathRate: 23.943,
      electricPowerConsumption: 78.484,
      gdp: 647382800,
      gdpPerCapita: 658.383,
      internetUsagePercent: 59.783,
      mortalityRate: 23.84,
      lifeExpectancy: 67.397,
      populationDensity: 42.839,
      unemploymentPercent: 34.578,
    };

    mock.onGet(`${API_URL}?countryName=India&year=2020`).reply(200, mockData);

    const result = await fetchRecord("India", 2020);
    expect(result).toEqual(mockData);
  });

  it("addRecord should successfully add a record", async () => {
    const newData: Data = {
      countryName: "India",
      countryCode: "IND",
      region: "Middle Asia",
      incomeGroup: "Middle income",
      year: 2020,
      birthRate: 76.58,
      deathRate: 23.943,
      electricPowerConsumption: 78.484,
      gdp: 647382800,
      gdpPerCapita: 658.383,
      internetUsagePercent: 59.783,
      mortalityRate: 23.84,
      lifeExpectancy: 67.397,
      populationDensity: 42.839,
      unemploymentPercent: 34.578,
    };

    mock.onPost(API_URL, newData).reply(201, newData);

    const result = await addRecord(newData);
    expect(result).toEqual(newData);
  });

  it("updateRecord should successfully update a record", async () => {
    const updatedData: Data = {
      countryName: "India",
      countryCode: "IND",
      region: "Middle Asia",
      incomeGroup: "Middle income",
      year: 2020,
      birthRate: 76.58,
      deathRate: 23.943,
      electricPowerConsumption: 78.484,
      gdp: 647382800,
      gdpPerCapita: 658.383,
      internetUsagePercent: 59.783,
      mortalityRate: 23.84,
      lifeExpectancy: 67.397,
      populationDensity: 42.839,
      unemploymentPercent: 34.578,
    };

    mock.onPut(API_URL, updatedData).reply(200, updatedData);

    const result = await updateRecord(updatedData);
    expect(result).toEqual(updatedData);
  });

  it("deleteRecord should successfully delete a record", async () => {
    const countryName = "India";
    const year = 2021;

    mock
      .onDelete(`${API_URL}?countryName=${countryName}&year=${year}`)
      .reply(200);

    const result = await deleteRecord(countryName, year);
    expect(result).toEqual({ countryName, year });
  });

  it("fetchCountriesCount should handle errors gracefully", async () => {
    mock.onGet(`${API_URL}/countries`).reply(500);

    const count = await fetchCountriesCount();
    expect(count).toBeUndefined(); // Error case
  });

  it("fetchRecord should handle errors gracefully", async () => {
    mock.onGet(`${API_URL}?countryName=India&year=2020`).reply(404);

    const result = await fetchRecord("India", 2020);
    expect(result).toBeUndefined(); // Error case
  });

  it("addRecord should handle errors gracefully", async () => {
    const newData: Data = {
      countryName: "India",
      countryCode: "IND",
      region: "Middle Asia",
      incomeGroup: "Middle income",
      year: 2020,
      birthRate: 76.58,
      deathRate: 23.943,
      electricPowerConsumption: 78.484,
      gdp: 647382800,
      gdpPerCapita: 658.383,
      internetUsagePercent: 59.783,
      mortalityRate: 23.84,
      lifeExpectancy: 67.397,
      populationDensity: 42.839,
      unemploymentPercent: 34.578,
    };

    mock.onPost(API_URL, newData).reply(400);

    const result = await addRecord(newData);
    expect(result).toBeUndefined(); // Error case
  });

  it("updateRecord should handle errors gracefully", async () => {
    const updatedData: Data = {
      countryName: "India",
      countryCode: "IND",
      region: "Middle Asia",
      incomeGroup: "Middle income",
      year: 2020,
      birthRate: 76.58,
      deathRate: 23.943,
      electricPowerConsumption: 78.484,
      gdp: 647382800,
      gdpPerCapita: 658.383,
      internetUsagePercent: 59.783,
      mortalityRate: 23.84,
      lifeExpectancy: 67.397,
      populationDensity: 42.839,
      unemploymentPercent: 34.578,
    };

    mock.onPut(API_URL, updatedData).reply(500);

    const result = await updateRecord(updatedData);
    expect(result).toBeUndefined(); // Error case
  });

  it("deleteRecord should handle errors gracefully", async () => {
    mock.onDelete(`${API_URL}?countryName=India&year=2021`).reply(500);

    const result = await deleteRecord("India", 2021);
    expect(result).toBeUndefined(); // Error case
  });
});
