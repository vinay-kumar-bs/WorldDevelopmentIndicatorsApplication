module.exports = {
  preset: "ts-jest",
  testEnvironment: "jsdom",
  moduleFileExtensions: ["js", "ts", "json", "vue"],
  transform: {
    "^.+\\.ts$": "ts-jest",
    "^.+\\.vue$": "@vue/vue3-jest",
    "^.+\\.js$": "babel-jest",
  },
  moduleNameMapper: {
    "^@/(.*)$": "<rootDir>/src/$1",
    "^axios$": "axios/dist/node/axios.cjs",
  },
  testMatch: ["**/tests/unit/**/*.spec.[jt]s?(x)"],
  setupFiles: ["jest-canvas-mock"],
};
