import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from 'pinia'

const pinia = createPinia();
const app = createApp(App);
app.use(ElementPlus, {locale: zhCn}).use(router).use(pinia)
app.mount('#app')
