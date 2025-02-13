<template>
  <div class="container mt-4">
    <div>
      <h1>Welcome to the World Development Indicator</h1>
      <p class="store-text alert alert-primary" role="alert">{{ message }}</p>
    </div>
    <div
      id="carouselExampleInterval"
      class="carousel slide"
      data-bs-ride="carousel"
    >
      <div class="carousel-inner">
        <div class="carousel-item active" data-bs-interval="3000">
          <img
            height="500px"
            width="800px"
            src="https://static.vecteezy.com/system/resources/thumbnails/023/060/798/small_2x/farming-tractor-spraying-plants-in-a-field-photo.jpg"
            class="d-block w-100"
            alt="farm"
          />
        </div>
        <div class="carousel-item" data-bs-interval="3000">
          <img
            height="500px"
            src="https://images.pexels.com/photos/167676/pexels-photo-167676.jpeg?cs=srgb&dl=pexels-klaus-44936-167676.jpg&fm=jpg"
            class="d-block w-100"
            alt="port"
          />
        </div>
        <div class="carousel-item" data-bs-interval="3000">
          <img
            height="500px"
            src="https://www.undp.org/sites/g/files/zskgke326/files/styles/image_with_credit_caption_1392_x_651_/public/2021-11/tron-le-wUk2U5Wirxg-unsplash.jpg?itok=G5d_zP1Z"
            class="d-block w-100"
            alt="city"
          />
        </div>
      </div>
      <button
        class="carousel-control-prev"
        type="button"
        data-bs-target="#carouselExampleInterval"
        data-bs-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button
        class="carousel-control-next"
        type="button"
        data-bs-target="#carouselExampleInterval"
        data-bs-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
    <div class="paragraph-text">
      <p>
        The World Development Indicators are a collection of development metrics
        compiled by the World Bank. These metrics provide a detailed overview of
        a country's development progress, including economic growth, literacy
        rates, life expectancy, and more.
      </p>
      <p>
        Our mission is to make global development data accessible,
        understandable, and useful for researchers, analysts, and policy-makers
        around the world.
      </p>
    </div>
  </div>
  <footer>
    <div class="footer-copy-right">
      &copy; 2025 The World Bank Group, All Rights Reserved.
    </div>
  </footer>
</template>

<script lang="ts">
import { defineComponent, computed } from "vue";
import { useStore } from "vuex";

export default defineComponent({
  name: "HomeView",
  setup() {
    const store = useStore();
    const message = computed(() => store.getters.getMessage);
    return { message };
  },
});
</script>

<style>
#carouselExampleInterval {
  margin-bottom: 30px;
}
.paragraph-text {
  font-family: Arial, Helvetica, sans-serif;
  font-size: larger;
}
.footer-copy-right {
  margin-top: 40px;
}
</style>
