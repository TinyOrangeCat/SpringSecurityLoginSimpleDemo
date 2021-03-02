import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './themes/green_theme/index.css'
import ElementUI from 'element-ui'
import { MessageBox } from 'element-ui'
import { Message } from 'element-ui'
import { postRequest } from './utils/api'
import { putRequest } from "./utils/api";
import { getRequest } from './utils/api'
import { deleteRequest } from './utils/api'
import { postKeyValueRequest } from './utils/api'
import  qs from 'qs'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.prototype.$messageBox = MessageBox;
Vue.prototype.$message = Message;

Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.$qs = qs;

new Vue({
  router,
  store,
  render: function (h) { return h(App) }
}).$mount('#app')
