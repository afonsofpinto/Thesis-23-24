import './style.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { DatePicker } from 'ant-design-vue';


const app = createApp(App)

app.use(router)
app.use(DatePicker);

app.mount('#app')
