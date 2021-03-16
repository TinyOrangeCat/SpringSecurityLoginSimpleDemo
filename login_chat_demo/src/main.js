import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//import './themes/green_theme/index.css'
//require('./themes/green_theme/index.css')
import NavigationComponent from "./components/NavigationComponent";
import ElementUI from 'element-ui'
import {defaultLanguage} from './i18n/language-constants'
import vueI18n from 'vue-i18n'
import elementEn from 'element-ui/lib/locale/lang/en'
import elementJa from 'element-ui/lib/locale/lang/ja'
import elementZh from 'element-ui/lib/locale/lang/zh-CN'
import uiTextEn from './i18n/langs/i18n_ui_text_en'
import uiTextJa from './i18n/langs/i18n_ui_text_ja'
import uiTextZh from './i18n/langs/i18n_ui_text_zh'
import sysMessageEn from './i18n/langs/i18n_sys_message_en'
import sysMessageJa from './i18n/langs/i18n_sys_message_ja'
import sysMessageZh from './i18n/langs/i18n_sys_message_zh'
//import i18n from "./i18n/i18n.config"
import { MessageBox } from 'element-ui'
import { Message } from 'element-ui'
import { postRequest } from './utils/api'
import { putRequest } from "./utils/api"
import { getRequest } from './utils/api'
import { deleteRequest } from './utils/api'
import { postKeyValueRequest } from './utils/api'
import  qs from 'qs'

Vue.config.productionTip = false

//安装组件
Vue.component('NavigationComponent',NavigationComponent)

Vue.use(ElementUI)
Vue.use(vueI18n)

Vue.prototype.$messageBox = MessageBox;
Vue.prototype.$message = Message;

Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.$qs = qs;

//https://blog.csdn.net/Kobeloveu/article/details/99291685
//locale: localStorage.getItem('lang')||'zh',
/*messages:{
  zh: {uiTextZh,sysMessageZh,...elementZh},
  en: {uiTextEn,sysMessageEn,...elementEn},
  ja: {uiTextJa,sysMessageJa,...elementJa},
}
messages:{
    zh: require('./i18n/i18n_ui_text_zh'),
    en: require('./i18n/i18n_ui_text_en'),
    ja: require('./i18n/i18n_ui_text_ja'),
  }
*/
//let locale = window.sessionStorage.getItem('lang') || 'zh_CN'
const i18n = new vueI18n({
  locale: window.sessionStorage.getItem('lang') || defaultLanguage,
  messages: {
    zh_CN: {...uiTextZh,...sysMessageZh,...elementZh},
    en_US: {...uiTextEn,...sysMessageEn,...elementEn},
    ja_JA: {...uiTextJa,...sysMessageJa,...elementJa},
  }
})
//ElementLocale.i18n((key, value) => i18n.t(key, value))
export function $t(args){
  return i18n.t(args)
}

export function $tc(args,lue){
  return i18n.tc(args)
  //return i18n.tc.call(i18n, args);
}
export let currentLocale = ()=>{
  return i18n.locale;
}

new Vue({
  router,
  store,
  i18n,
  render: function (h) { return h(App) },
}).$mount('#app')
