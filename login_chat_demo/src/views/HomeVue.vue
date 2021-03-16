<template>
  <div id="homeVue">
    <div id="headLink">
      <el-link type="primary" href="/managerLoginVue">{{ $t('uiText.managerLoginTitle') }}</el-link>&emsp;&emsp;
      <el-link type="primary" href="/userLoginVue">{{ $t('uiText.userLoginTitle') }}</el-link>
    </div>
    <div id="i18nTest">
      <h4>Element UI i18n</h4>
      <p>{{ $t('el.datepicker.now') }}</p>
    </div>
  </div>
</template>

<script>
import {defaultLanguage} from '../i18n/language-constants.js'

export default {
  name: "HomeVue",
  created() {
    console.log('HomeVue '+this.$route.name)
    this.$emit('fromRouterView',this.$route.name)
    if(!window.sessionStorage.getItem('lang') || window.sessionStorage.getItem('lang') === ''){
      window.sessionStorage.setItem('lang',defaultLanguage);
      this.$i18n.locale = defaultLanguage;
    }else {
      this.$i18n.locale = window.sessionStorage.getItem('lang');
    }
  }
}
</script>

<style scoped>
 #headLink{
   padding: 300px 0px 0px 400px;
 }
 #i18nTest{
   width: 60%;
   margin: 50px auto 20px auto;
 }
</style>