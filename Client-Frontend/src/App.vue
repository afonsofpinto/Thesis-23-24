<script setup>
import { RouterLink, RouterView } from 'vue-router'


</script>

<template>
  
  <main class="bg-white h-screen">
    <nav class="flex p-4">
      <img src="./assets/mbanco.png" alt="banco" width="90">
      <h1 class="font-semibold text-blue-900"> MultiBanco </h1>
    </nav>
    <RouterView class="flex justify-center p-[3rem]" />
  </main>

  

</template>

<style scoped>

</style>
