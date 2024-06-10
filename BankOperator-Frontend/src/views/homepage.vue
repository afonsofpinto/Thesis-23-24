<template>
  <div class="flex flex-col items-center">
    <h1 class="text-2xl font-semibold text-gray-700 mb-6">Analytics and Reporting</h1>
    <div class="w-full border-b-2 border-gray-300 mb-6"></div>
  </div>
  
  <div class="dashboard-container">
    <div class="dashboard-item">
      <img class="icon" src="../../public/transaction.png" alt="Icon 1">
      <h2 class="custom-button-title">Transactions</h2>
      <button @click="onClickTransaction"class="custom-button">View</button>
    </div>
    <div class="dashboard-item">
      <img class="icon" src="../../public/client.png" alt="Icon 1">
      <h2 class="custom-button-title">Clients</h2>
      <button @click="onClickClient" class="custom-button">View</button>
    </div>
    <div class="dashboard-item">
      <img class="icon" src="../../public/time.png" alt="Icon 1">
      <h2 class="custom-button-title">Time</h2>
      <button @click="onClickTime" class="custom-button">View</button>
    </div>
    <div class="dashboard-item">
      <img class="icon" src="../../public/smart_contract2.png" alt="Icon 1">
      <h2 class="custom-button-title">Smart Contract</h2>
      <button @click="onClickContract"class="custom-button">View</button>
    </div>
  </div>
  <div v-if="show_dashboard_time" class="mt-8 w-full">
    <div class="overflow-x-auto">
      <table class="w-full mx-auto">
        <thead class="bg-gray-50">
          <tr>
            <th colspan="4" class="px-2 py-2 text-left text-2xl font-semibold text-yellow-500 mb-6">Time Dashboard</th>
          </tr>
          <tr>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Movimented Money</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="(dataItem, index) in timeData" :key="index">
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[0] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[1] }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div v-if="show_dashboard_client" class="mt-8 w-full">
    <div class="overflow-x-auto">
      <table class="w-full mx-auto">
        <thead class="bg-gray-50">
          <tr>
            <th colspan="4" class="px-2 py-2 text-left text-2xl font-semibold text-yellow-500 mb-6">Client Dashboard</th>
          </tr>
          <tr>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Client ID</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date Birth</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Contact</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">District</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Municipality</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Movimented Money</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="(dataItem, index) in clientData" :key="index">
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[0] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[1] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[2] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[3] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[4] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[5] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[6] }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div v-if="show_dashboard_contract" class="mt-8 w-full">
    <div class="overflow-x-auto">
      <table class="w-full mx-auto">
        <thead class="bg-gray-50">
          <tr>
            <th colspan="4" class="px-2 py-2 text-left text-2xl font-semibold text-yellow-500 mb-6">Smart Contract Dashboard</th>
          </tr>
          <tr>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Contract ID</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Type</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Owner</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Creation Date</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Expire Date</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Movimented Money</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="(dataItem, index) in contractData" :key="index">
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[0] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[1] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[2] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[3] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[4] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[5] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[6] }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div v-if="show_dashboard_transaction" class="mt-8 w-full">
    <div class="overflow-x-auto">
      <table class="w-full mx-auto">
        <thead class="bg-gray-50">
          <tr>
            <th colspan="4" class="px-2 py-2 text-left text-2xl font-semibold text-yellow-500 mb-6">Transactions Dashboard</th>
          </tr>
          <tr>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Sender ID</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Sender Name</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Receiver ID</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Receiver Name</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Contract ID</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Contract Type</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Contract Owner</th>
            <th scope="col" class="px-2 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Movimented Money</th>
          </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
          <tr v-for="(dataItem, index) in transactionData" :key="index">
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[0] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[1] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[2] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[3] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[4] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[5] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[6] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[7] }}</td>
            <td class="px-2 py-1 whitespace-nowrap text-xs text-gray-900">{{ dataItem[8] }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from "axios";

const timeData = ref(null);
const show_dashboard_time = ref(false); 

const clientData = ref(null);
const show_dashboard_client = ref(false); 

const contractData = ref(null);
const show_dashboard_contract = ref(false); 

const transactionData = ref(null);
const show_dashboard_transaction = ref(false); 

async function getInstanceAddress() {
  try {
    const response = await fetch('../../public/flask_instance.tmp'); // Adjust the path according to where your file is located in the public directory
    const instanceAddress = await response.text();
    return instanceAddress.trim(); // Remove any leading or trailing whitespace
  } catch (error) {
    console.error('Failed to fetch the file:', error);
    throw error;
  }
}

// Create Axios instance with dynamic baseURL
const apiClient = axios.create({
  baseURL: '',
  withCredentials: false,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
});

// Set baseURL dynamically
getInstanceAddress().then(instanceAddress => {
  apiClient.defaults.baseURL = `http://${instanceAddress}:5000`;
});


const onClickTime = () => {
  apiClient.get('/time')
    .then(response => {
      show_dashboard_client.value=false;
      show_dashboard_contract.value=false;
      show_dashboard_transaction.value=false;
      timeData.value = response.data;
      show_dashboard_time.value = true;
      
    })
    .catch(error => {
      console.error('Error fetching time data:', error);
    });
};

const onClickClient = () => {
  apiClient.get('/client')
    .then(response => {
      show_dashboard_time.value=false;
      show_dashboard_contract.value=false;
      show_dashboard_transaction.value=false;
      clientData.value = response.data;
      show_dashboard_client.value = true;
    })
    .catch(error => {
      console.error('Error fetching time data:', error);
    });
};

const onClickContract = () => {
  apiClient.get('/smartContract')
    .then(response => {
      show_dashboard_time.value=false;
      show_dashboard_client.value=false;
      show_dashboard_transaction.value=false;
      contractData.value = response.data;
      show_dashboard_contract.value = true;
      
    })
    .catch(error => {
      console.error('Error fetching time data:', error);
    });
};

const onClickTransaction = () => {
  apiClient.get('/transactions')
    .then(response => {
      show_dashboard_time.value=false;
      show_dashboard_client.value=false;
      show_dashboard_contract.value=false;
      transactionData.value = response.data;
      show_dashboard_transaction.value=true;
    })
    .catch(error => {
      console.error('Error fetching time data:', error);
    });
};

</script>

<style>
.dashboard-container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-wrap: wrap;
  gap: 20px;
  margin-top: 20px;
}

.dashboard-item {
  width: 200px;
  height: 110px;
  padding: 15px;
  border: 1px solid #ccc;
  text-align: center;
  background-color: #f8f9fa;
  position: relative;
}

.icon {
  width: 40px;
  height: 40px;
  position: relative;
  background-color: transparent;
  top: -10px; 
  left: 65px;
}

.custom-button-title {
  font-family: "Calibri", sans-serif;
  font-size: 14px;
  font-weight: 100;
  color: #615C5C;
  margin-top: -8px;
  text-align: center;
}

.custom-button {
  font-family: "Calibri", sans-serif;
  font-size: 12px;
  font-weight: 200;
  color: #4CAF50;
  background-color: white;
  padding: 2px 10px;
  border: 3px solid #4CAF50;
  border-radius: 5px;
  cursor: pointer;
  margin-top: -50px;
}

</style>
