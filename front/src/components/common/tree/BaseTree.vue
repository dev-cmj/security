<script setup>
import { ref } from "vue";
import Tree from "primevue/tree";

const data = ref([
  {
    key: "0",
    label: "Documents",
    data: "Documents Folder",
    icon: "folder",
    children: [
      {
        key: "0-0",
        label: "Work",
        data: "Work Folder",
        icon: "folder",
        children: [
          { key: "0-0-0", label: "Expenses.doc", icon: "folder", data: "Expenses Document" },
          { key: "0-0-1", label: "Resume.doc", icon: "folder", data: "Resume Document" },
        ],
      },
      {
        key: "0-1",
        label: "Home",
        data: "Home Folder",
        icon: "folder",
        children: [{ key: "0-1-0", label: "Invoices.txt", icon: "folder", data: "Invoices for this month" }],
      },
    ],
  },
  {
    key: "1",
    label: "Events",
    data: "Events Folder",
    icon: "folder",
    children: [
      { key: "1-0", label: "Meeting", icon: "folder", data: "Meeting" },
      { key: "1-1", label: "Product Launch", icon: "folder", data: "Product Launch" },
      { key: "1-2", label: "Report Review", icon: "folder", data: "Report Review" },
    ],
  },
  {
    key: "2",
    label: "Movies",
    data: "Movies Folder",

    children: [
      {
        key: "2-0",

        label: "Al Pacino",
        data: "Pacino Movies",
        children: [
          { key: "2-0-0", label: "Scarface", data: "Scarface Movie" },
          { key: "2-0-1", label: "Serpico", data: "Serpico Movie" },
        ],
      },
      {
        key: "2-1",
        label: "Robert De Niro",

        data: "De Niro Movies",
        children: [
          { key: "2-1-0", label: "Goodfellas", data: "Goodfellas Movie" },
          { key: "2-1-1", label: "Untouchables", data: "Untouchables Movie" },
        ],
      },
    ],
  },
]);

const selectedKey = ref("");

const onNodeSelect = node => {
  console.log(node.label);
  console.log(node.data);
  console.log(node.key);
  console.log(node);
};
</script>
<template>
  <Tree
    class="w-full md:w-[30rem] base-tree"
    style="width: 200px"
    :value="data"
    v-model:selectionKeys="selectedKey"
    :gap="2"
    selectionMode="single"
    @nodeSelect="onNodeSelect"
  >
  </Tree>
</template>

<style></style>
