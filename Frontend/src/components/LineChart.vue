<template>
  <div>
    <canvas ref="lineChartCanvas"></canvas>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, watch, PropType } from "vue";
import { Chart } from "chart.js/auto";

export default defineComponent({
  name: "LineChart",
  props: {
    labels: {
      type: Array as PropType<number[]>,
      required: true,
    },
    data: {
      type: Array as PropType<number[]>,
      required: true,
    },
    attribute: {
      type: String,
      required: true,
    },
  },
  setup(props) {
    const lineChartCanvas = ref<HTMLCanvasElement | null>(null);
    let chartInstance: Chart | null = null;

    const renderChart = () => {
      if (chartInstance) chartInstance.destroy(); //destroying old chart instance
      if (lineChartCanvas.value) {
        chartInstance = new Chart(lineChartCanvas.value, {
          type: "line",
          data: {
            labels: props.labels,
            datasets: [
              {
                label: `Values of ${props.attribute}`,
                data: props.data,
                borderColor: "blue",
                backgroundColor: "rgba(173, 216, 230, 0.5)",
                fill: true,
              },
            ],
          },
          options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
              x: { title: { display: true, text: "Year" } },
              y: { title: { display: true, text: props.attribute } },
            },
          },
        });
      }
    };
    watch([() => props.labels, () => props.data], renderChart, { deep: true });

    onMounted(renderChart);

    return { lineChartCanvas };
  },
});
</script>

<style>
canvas {
  max-width: 100%;
  height: 400px;
}
</style>
