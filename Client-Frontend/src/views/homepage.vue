<template>
  <div class="flex justify-between max-w-[52rem]">
    <div class="flex flex-col gap-10">
      <div v-for="item in options.data" :key="item">
        <h1 class="text-lg text-blue-200">{{ item.msg }}</h1>
        <h1 class="text-2xl text-blue-950 font-bold">{{ item.value }}</h1>
      </div>
    </div>
    <div class="grid sm:grid-cols-1 gap-8 w-8/12 max-w-[30rem]">
      <div v-for="option in options.items" :key="option" 
        class="h-[4rem] bg-blue-400 flex align-middle justify-center items-center rounded-xl overflow-hidden">
        <button @click="handleButtonClick(option)" class="hover:bg-blue-600 text-white font-normal text-xl w-full h-full">
          {{ option }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import router from '../router';
import axios from "axios";


const route = useRoute();
const value = route.query.value;


onMounted(() => {
  apiClient.get(`/operations/${value}`)
    .then(response => {
        console.log(response.data);
        const new_balance = response.data;
        options.data[1].value = new_balance + " $";
    })
    .catch(error => {
        console.log(error);
    });
});


const apiClient = axios.create({
    baseURL: "http://127.0.0.1:8080/",
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
    },
});


const options = reactive({
  items: ['Send', 'Check Balance'], 
  data: [
    {
      msg: 'Welcome,',
      value: `${value}`,
    },
    {
      msg: 'Account Balance:',
      value: '0 $',
    },
  ]
});


const handleButtonClick = (option) => {
  if (option === 'Send') {
    router.push({ path: '/homepage/send', query: { value: options.data[0].value }});
  }
  if (option === 'Check Balance'){
    apiClient.get(`/operations/${options.data[0].value}`)
    .then(response => {
        console.log(response.data);
        const new_balance = response.data;
        options.data[1].value = new_balance + " $";
    })
    .catch(error => {
        console.log(error);
    });
  }
}

</script>
