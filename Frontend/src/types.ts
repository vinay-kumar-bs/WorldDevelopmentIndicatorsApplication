export interface Data {
  countryName: string;
  countryCode: string;
  region: string;
  incomeGroup: string;
  year: number | null;
  birthRate: number | null;
  deathRate: number | null;
  electricPowerConsumption: number | null;
  gdp: number | null;
  gdpPerCapita: number | null;
  internetUsagePercent: number | null;
  mortalityRate: number | null;
  lifeExpectancy: number | null;
  populationDensity: number | null;
  unemploymentPercent: number | null;
}

export interface RootState {
  countryRecord: any[];
  countriesCount: number;
  message: string;
}

export type Mutations = {
  setCountriesCount(state: RootState, count: number): void;
  setMessage(state: RootState, message: string): void;
};
