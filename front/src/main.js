import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from "element-plus"
import ko from "element-plus/es/locale/lang/ko"
import BootstrapVue3 from 'bootstrap-vue-3';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

createApp(App)
.use(store)
.use(router)
.use(BootstrapVue3)
.use(ElementPlus, {locale: ko})
.mount('#app')
