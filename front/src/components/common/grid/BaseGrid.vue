<script setup>
import { AgGridVue } from "ag-grid-vue3";
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-alpine.css";
import { defineModel, onBeforeMount, ref, watch } from "vue";
import axios from "axios";
import { routerPushSearchParam } from "@/common/common.js";

const props = defineProps({
  id: String,
  class: String,
  rowData: Array,
  colDefs: Array,
  rowHeight: String,
  gridOption: Object,
  height: {
    default: "500px",
  },
  multiple: {
    type: Boolean,
    default: false,
  },
  infinite: {
    type: Boolean,
    default: false,
  },
  fetch: { type: Function, default: null },
  apiUrl: { type: String, default: null },
  searchConditions: {
    type: Object,
    default: () => {},
  },
  modal: { type: Boolean, default: false },
});
const emit = defineEmits([
  "rowClicked",
  "changeSelected",
  "rowSelected",
  "reloadGrid",
  "rowDoubleClicked",
  "updateTotalElements",
]);
let gridApi = defineModel("gridApi", { default: null });
let reload = defineModel("reload", { default: false });
const tooltipShowDelay = ref(null);
const style = `
  height: ${props.height}
`;

const rowSelectOption = () => {
  if (props.multiple) {
    return {
      rowSelection: "multiple",
      rowMultiSelectWithClick: true,
    };
  }
  return {
    rowSelection: "single",
  };
};
const infiniteOption = () => {
  if (props.infinite) {
    return {
      rowModelType: "infinite",
      cacheBlockSize: 10,
      maxBlocksInCache: 100,
    };
  }
};

const dataSource = {
  getRows: async params => {
    const { startRow, endRow } = params;
    const size = endRow - startRow;
    const page = Math.floor(startRow / size);
    const { order, sortField } = getSortParams(params);
    try {
      const response = await axios.get(props.apiUrl, {
        params: { page, size, order, sortField, ...props.searchConditions },
      });
      const { content, totalElements } = response.data;
      const lastRow = totalElements <= endRow ? totalElements : -1;
      params.successCallback(content, lastRow);
      if (!props.modal) await routerPushSearchParam(props.searchConditions);
      if (!gridApi.value.isDestroyed()) {
        gridApi.value.hideOverlay();
      }

      emit("updateTotalElements", totalElements);
    } catch (e) {
      params.failCallback();
      gridApi.value.showNoRowsOverlay();
    }
  },
};

const getSortParams = params => {
  if (params.sortModel.length === 0) {
    return { order: null, sortField: null };
  }
  const { sort: order, colId: sortField } = params.sortModel[0];
  return { order, sortField };
};

const gridOptions = {
  onSelectionChanged: event => {
    emit("changeSelected", event);
  },
  onRowClicked: event => {
    emit("rowClicked", event);
  },
  onRowDoubleClicked: event => {
    emit("rowDoubleClicked", event);
  },
  onRowSelected: event => {
    emit("rowSelected", event);
  },
  onGridReady: params => {
    gridApi.value = params.api;
    if (props.infinite) {
      params.api.setGridOption("datasource", dataSource);
    }
  },
  ...props.gridOption,
  ...rowSelectOption(),
  ...infiniteOption(),
};

const resetSelection = () => {
  if (gridApi.value) {
    gridApi.value.deselectAll();
  }
};

watch(
  () => reload.value,
  () => {
    if (gridApi.value && props.infinite) {
      gridApi.value.setGridOption("datasource", dataSource);
    }
  },
  { immediate: true },
);

onBeforeMount(() => {
  tooltipShowDelay.value = 500;
});

defineExpose({ resetSelection });
</script>

<template>
  <div class="grid">
    <AgGridVue
      :id="id"
      :rowData="rowData"
      :gridOptions="gridOptions"
      :columnDefs="colDefs"
      :style="style"
      :rowHeight="rowHeight"
      :tooltipShowDelay="tooltipShowDelay"
      class="ag-theme-alpine"
    >
    </AgGridVue>
  </div>
</template>

<style scoped></style>
