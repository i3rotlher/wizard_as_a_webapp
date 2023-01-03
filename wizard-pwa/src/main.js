import { createApp } from 'vue'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import './assets/css/globalstyle.css';
import 'bootstrap-icons/font/bootstrap-icons.css'
import './registerServiceWorker'
<link rel="manifest" href="manifest.json" 
crossorigin="use-credentials"/>
createApp(App).mount('#app') 