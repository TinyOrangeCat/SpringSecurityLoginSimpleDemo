//Create by : YueLi/xiroiyuki@hotmail.com 
//Create time: 2021/3/16 10:44

import Vue from 'vue'
import {defaultLanguage} from './language-constants'
import vueI18n from 'vue-i18n'
import elementEn from 'element-ui/lib/locale/lang/en'
import elementJa from 'element-ui/lib/locale/lang/ja'
import elementZh from 'element-ui/lib/locale/lang/zh-CN'
import uiTextEn from './langs/i18n_ui_text_en'
import uiTextJa from './langs/i18n_ui_text_ja'
import uiTextZh from './langs/i18n_ui_text_zh'
import sysMessageEn from './langs/i18n_sys_message_en'
import sysMessageJa from './langs/i18n_sys_message_ja'
import sysMessageZh from './langs/i18n_sys_message_zh'

Vue.use(vueI18n)

const i18n = new vueI18n({
  locale: window.sessionStorage.getItem('lang') || defaultLanguage,
  messages: {
    zh_CN: {...uiTextZh,...sysMessageZh,...elementZh},
    en_US: {...uiTextEn,...sysMessageEn,...elementEn},
    ja_JA: {...uiTextJa,...sysMessageJa,...elementJa},
  }
})

export default i18n