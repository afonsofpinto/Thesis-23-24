<template> 
    <div>
        <div v-if="waitResponse.response" class="pop-up">
            <i class="fa-solid fa-spinner fa-spin icon"></i>
            <p class="text-center mt-12"> {{ receivedResponse.response }}</p>
        </div>  
        <a-form :model="formState" name="login" autocomplete="off">
        <a-form-item
            label="Destination"
            name="destination"
            :rules="[{ required: true, message: 'Please insert!' }]"
        >
            <a-input v-model:value="formState.destination">
            <template #prefix>
            </template>
            </a-input>
        </a-form-item>

        <a-form-item
            label="Amount"
            name="amount"
            :rules="[{ required: true, message: 'Please insert!' }]"
        >
            <a-input v-model:value="formState.amount">
            <template #prefix>
            </template>
            </a-input>
        </a-form-item>

        <a-form-item class="place-items-end">
            <div class="flex justify-between">
            <a-button :disabled="disabled" @click="onClickSend" html-type="submit">Send</a-button>
            <a-button @click="onClickCancel" html-type="submit">Cancel</a-button>
            </div>
        </a-form-item>
        </a-form>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { computed, reactive } from 'vue';
  import { useRoute } from 'vue-router';
  import router from '../router';
  import axios from "axios";

  
const route = useRoute();
const source = route.query.value;

  const waitResponse = reactive({
     response: ''
  });

  const receivedResponse = reactive({
     response: ''
  });
  
  interface FormState {
    destination: string,
    amount: number
  }
  
  const formState = reactive<FormState>({
    destination: "",
    amount: null
  });
  
  
  const disabled = computed(() => {
    return  !formState.destination || !formState.amount;
  });
  
  const apiClient = axios.create({
    baseURL: "http://127.0.0.1:8080/",
    withCredentials: false,
    headers: {
      Accept: "text/plain",
      "Content-Type": "text/plain",
    },
  });
  
  const onClickSend = () => {
    console.log(formState.source);
    console.log(formState.destination);
    console.log(formState.amount);
    waitResponse.response = true;    
    apiClient.post(`/operations/${source}/${formState.destination}/${formState.amount}`, { source: formState.source })
      .then(response => {
        console.log(response.data);
        receivedResponse.response =  response.data;
        setTimeout(() => {
          router.push({ path: '/homepage', query: { value: source }});
        }, 3000);
      })
      .catch(error => {
        console.log(error);
      });
  };
  
  const onClickCancel = () => {
    router.push({ path: '/homepage', query: { value: formState.source }});
  };
  </script>


<style> 
.fa-solid, .fa-spinner, .fa-spin{
    font-size: 5rem;
    display: flex;
    justify-content: center;
    align-items: center;
    color: lightskyblue;
}
.pop-up{
    background-color: white;
    width: 20rem;
    height: 15rem;
    position: absolute;
    z-index: 1;
}

</style>
  